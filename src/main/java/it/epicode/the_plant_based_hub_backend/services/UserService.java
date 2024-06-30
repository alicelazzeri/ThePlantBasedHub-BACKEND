package it.epicode.the_plant_based_hub_backend.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import it.epicode.the_plant_based_hub_backend.entities.User;
import it.epicode.the_plant_based_hub_backend.entities.enums.Role;
import it.epicode.the_plant_based_hub_backend.exceptions.BadRequestException;
import it.epicode.the_plant_based_hub_backend.exceptions.FileSizeExceededException;
import it.epicode.the_plant_based_hub_backend.exceptions.NotFoundException;
import it.epicode.the_plant_based_hub_backend.payloads.auth.UserRegisterRequestDTO;
import it.epicode.the_plant_based_hub_backend.repositories.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Cloudinary cloudinaryUploader;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    public long getMaxFileSizeInBytes() {
        String[] parts = maxFileSize.split("(?i)(?<=[0-9])(?=[a-z])");
        long size = Long.parseLong(parts[0]);
        String unit = parts[1].toUpperCase();
        switch (unit) {
            case "KB":
                size *= 1024;
                break;
            case "MB":
                size *= 1024 * 1024;
                break;
            case "GB":
                size *= 1024 * 1024 * 1024;
                break;
        }
        return size;
    }

    public void sendRegistrationEmail(String email, String recipientName) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(email);
            helper.setFrom("pbhcustomerservice@gmail.com", "The Plant Based Hub");
            helper.setSubject("Welcome to The Plant Based Hub! 🌱♥️");
            String emailContent = "<html>" +
                    "<head>" +
                    "<style>" +
                    "@import url('https://fonts.googleapis.com/css2?family=Forum&display=swap');" +
                    "@import url('https://fonts.googleapis.com/css2?family=Tenor+Sans&display=swap');" +
                    "body { font-family: 'Tenor Sans', Arial, sans-serif; font-weight: 400 }" +
                    ".email-container { padding: 20px; }" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<div class=\"email-container\">" +
                    "<div>" +
                    "<p style=\"font-size: 18px;\">Hello <span style=\"color: green;\"><strong>" + recipientName + "</strong></span>, and welcome to 🌱 <strong><span { font-family: 'Forum', Arial, sans-serif; }>The Plant Based Hub</span></strong>! 🌱</p>" +
                    "<p style=\"font-size: 14px;\">We are delighted to have you join our community of plant-based enthusiasts.</p>" +
                    "<p style=\"font-size: 14px;\">Your registration has been successful 🎉, and you are now part of a growing hub where you can discover and share delicious and nutritious vegan recipes. ✨</p>" +
                    "<p style=\"font-size: 14px;\">Here at The Plant Based Hub, we believe in the power of plant-based living to transform our health and our planet. With our platform, you can:</p>" +
                    "<ul>" +
                    "<li style=\"font-size: 14px;\">🔍 <strong>Find Recipes by Available Ingredients</strong>: Input the ingredients you have at home and get tailored vegan recipes that make the most of them.</li>" +
                    "<li style=\"font-size: 14px;\">🥗 <strong>Nutritional Filtering</strong>: Select recipes based on your dietary needs, focusing on proteins, carbohydrates, fibers, and vitamins for well-balanced meals.</li>" +
                    "<li style=\"font-size: 14px;\">📝 <strong>Generate Shopping Lists</strong>: Choose your favorite recipes and automatically create precise shopping lists to avoid food waste and save money.</li>" +
                    "</ul>" +
                    "<p style=\"font-size: 14px;\">To get started, log in to your account and explore the wide variety of vegan recipes waiting for you. " +
                    "Remember, every meal you create helps reduce food waste and protect our environment, but most importantly save animals. 🐾</p>" +
                    "<p style=\"font-size: 14px;\">❓ If you have any questions or need assistance, please don't hesitate to reach out to our support team via email at <a href='mailto:pbhcustomerservice@gmail.com'>pbhcustomerservice@gmail.com</a>.</p>" +
                    "<p style=\"font-size: 14px;\">Happy Cooking! 💖</p>" +
                    "<p style=\"font-size: 14px;\">🌱 The Plant Based Hub Team 🌱</p>" +
                    "</div>" +
                    "</div>" +
                    "</body>" +
                    "</html>";

            helper.setText(emailContent, true);
            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    // GET all users

    @Transactional (readOnly = true)
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    // GET user by ID

    @Transactional(readOnly = true)
    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    // POST saving user

    @Transactional
    public User saveUser(UserRegisterRequestDTO userPayload){

        Optional<User> existingUser = userRepository.findByEmail(userPayload.email());
        if (existingUser.isPresent()) {
            throw new BadRequestException("User with this email: " + userPayload.email() + " already exists");
        }
        User user = new User();
        user.setFirstName(userPayload.firstName());
        user.setLastName(userPayload.lastName());
        user.setEmail(userPayload.email());
        user.setRole(Role.USER);
        user.setPassword(bcrypt.encode(userPayload.password()));
        sendRegistrationEmail(userPayload.email(), userPayload.firstName());
        userRepository.save(user);

        return user;
    }

    // POST saving user with admin role

    @Transactional
    public User saveUserAdmin(UserRegisterRequestDTO userPayload){

        Optional<User> existingUser = userRepository.findByEmail(userPayload.email());
        if (existingUser.isPresent()) {
            throw new BadRequestException("User with this email: " + userPayload.email() + " already exists");
        }
        User user = new User();
        user.setFirstName(userPayload.firstName());
        user.setLastName(userPayload.lastName());
        user.setEmail(userPayload.email());
        user.setRole(Role.USER);
        user.setRole(Role.ADMIN);
        user.setPassword(bcrypt.encode(userPayload.password()));
        sendRegistrationEmail(userPayload.email(), userPayload.firstName());
        userRepository.save(user);
        System.out.println("--------------password saved ------------------------");

        return user;
    }

    // PUT updating existing user

    @Transactional
    public User updateUser(long id, UserRegisterRequestDTO userPayload) {
        Optional<User> userOptional = getUserById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setFirstName(userPayload.firstName());
            user.setLastName(userPayload.lastName());
            user.setEmail(userPayload.email());
            user.setPassword(bcrypt.encode(userPayload.password()));

            if (userPayload.role() != null) {
                user.setRole(userPayload.role());
            }
            System.out.println("------------ Password was updated -------------------");
            System.out.println(user.getPassword());

            return userRepository.save(user);
        } else {
            throw new NotFoundException("User with id: " + id + " not found");
        }
    }

    // DELETE user by ID

    @Transactional
    public String deleteUser(long id) {
        Optional<User> userOptional = getUserById(id);

        if(userOptional.isPresent()){
            userRepository.deleteById(id);
            return "User with id: " + id + " deleted correctly";
        }
        else{
            throw new NotFoundException("User with id: " + id + " not found");
        }
    }

    // GET find user by email

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()){
            return userOptional.get();
        }
        else {
            throw new NotFoundException("User with email: " + email + " not found");
        }
    }

    // POST upload cloudinary file with public_id

    @Transactional
    public String uploadAvatar(Long id, MultipartFile image) throws IOException {

        long maxFileSize = getMaxFileSizeInBytes();
        if (image.getSize() > maxFileSize) {
            throw new FileSizeExceededException("File size exceeds the maximum allowed size");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id: " + id + " not found"));

        String existingPublicId = user.getAvatarUrl();
        if (existingPublicId != null && !existingPublicId.isEmpty()) {
            cloudinaryUploader.uploader().destroy(existingPublicId, ObjectUtils.emptyMap());
        }

        Map<String, Object> uploadResult = cloudinaryUploader.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
        String publicId = (String) uploadResult.get("public_id");
        String url = (String) uploadResult.get("url");

        user.setAvatarUrl(publicId);
        userRepository.save(user);

        return url;
    }

    // DELETE delete cloudinary file

    @Transactional
    public String deleteAvatar(Long id, String publicId) throws IOException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id: " + id + " not found"));

        if (publicId != null && !publicId.isEmpty()) {
            cloudinaryUploader.uploader().destroy(publicId, ObjectUtils.emptyMap());
            user.setAvatarUrl(null);
            userRepository.save(user);
            return "Avatar deleted successfully";
        } else {
            return "No avatar found for deletion";
        }
    }

    // PUT update cloudinary file

    @Transactional
    public String updateImage(long id, String publicId, MultipartFile updatedImage) throws IOException {
        if (publicId != null && !publicId.isEmpty()) {
            deleteAvatar(id, publicId);
        }

        long maxFileSize = getMaxFileSizeInBytes();
        if (updatedImage.getSize() > maxFileSize) {
            throw new FileSizeExceededException("File size exceeds the maximum allowed size");
        }
        return uploadAvatar(id, updatedImage);
    }

    // helper method to convert from Optional<User> to User

    public User getUserByIdOrThrow(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id: " + id + " not found."));
    }
}
