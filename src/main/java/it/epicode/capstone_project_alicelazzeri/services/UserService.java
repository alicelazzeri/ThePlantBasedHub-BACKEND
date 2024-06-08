package it.epicode.capstone_project_alicelazzeri.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import it.epicode.capstone_project_alicelazzeri.entities.User;
import it.epicode.capstone_project_alicelazzeri.entities.enums.Role;
import it.epicode.capstone_project_alicelazzeri.exceptions.BadRequestException;
import it.epicode.capstone_project_alicelazzeri.exceptions.NotFoundException;
import it.epicode.capstone_project_alicelazzeri.payloads.UserRegisterRequestDTO;
import it.epicode.capstone_project_alicelazzeri.repositories.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
                    "@import url('https://fonts.googleapis.com/css2?family=Quicksand:wght@300..700&display=swap');" +
                    "body { font-family: 'Quicksand', Arial, sans-serif; font-weight: 400 }" +
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
    public String saveUser(UserRegisterRequestDTO userPayload){

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

        return "User with id: " + user.getId() + " saved correctly";
    }

    // POST saving user with admin role

    @Transactional
    public String saveUserAdmin(UserRegisterRequestDTO userPayload){

        Optional<User> existingUser = userRepository.findByEmail(userPayload.email());
        if (existingUser.isPresent()) {
            throw new BadRequestException("User with this email: " + userPayload.email() + " already exists");
        }
        User user = new User();
        user.setFirstName(userPayload.firstName());
        user.setLastName(userPayload.lastName());
        user.setEmail(userPayload.email());
        user.setRole(Role.ADMIN);
        user.setPassword(bcrypt.encode(userPayload.password()));
        sendRegistrationEmail(userPayload.email(), userPayload.firstName());
        userRepository.save(user);

        return "Admin with id: " + user.getId() + " saved correctly";
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








    public String uploadImage(MultipartFile image) throws IOException {
        String url = (String) cloudinaryUploader.uploader().upload(image.getBytes(), ObjectUtils.emptyMap()).get("url");
        return url;
    }
}
