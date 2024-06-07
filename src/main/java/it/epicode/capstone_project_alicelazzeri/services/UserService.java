package it.epicode.capstone_project_alicelazzeri.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import it.epicode.capstone_project_alicelazzeri.entities.User;
import it.epicode.capstone_project_alicelazzeri.entities.enums.Role;
import it.epicode.capstone_project_alicelazzeri.exceptions.BadRequestException;
import it.epicode.capstone_project_alicelazzeri.exceptions.NotFoundException;
import it.epicode.capstone_project_alicelazzeri.payloads.UserRegisterRequestDTO;
import it.epicode.capstone_project_alicelazzeri.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

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

    private void sendRegistrationEmail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Welcome to The Plant Based Hub! 🌱♥️");

        String emailContent = "Hello and Welcome to 🌱 The Plant Based Hub! 🌱 \n\n" +
                "We are delighted to have you join our community of plant-based enthusiasts. Your registration has been successful 🎉, " +
                "and you are now part of a growing hub where you can discover and share delicious and nutritious vegan recipes. ✨ \n\n" +
                "Here at The Plant Based Hub, we believe in the power of plant-based living to transform our health and our planet. With our platform, you can:\n" +
                "- 🔍 **Find Recipes by Available Ingredients**: Input the ingredients you have at home and get tailored vegan recipes that make the most of them.\n" +
                "- 🥗 **Nutritional Filtering**: Select recipes based on your dietary needs, focusing on proteins, carbohydrates, fibers, and vitamins for well-balanced meals.\n" +
                "- 📝 **Generate Shopping Lists**: Choose your favorite recipes and automatically create precise shopping lists to avoid food waste and save money.\n\n" +
                "To get started, log in to your account and explore the wide variety of vegan recipes waiting for you." +
                "Remember, every meal you create helps reduce food waste and protect our environment, but most importantly save animals. 🐾\n\n" +
                "❓ If you have any questions or need assistance, please don't hesitate to reach out to our support team.\n\n" +
                "Happy Cooking! 💖\n" +
                "🌱 The Plant Based Hub Team 🌱";

        message.setText(emailContent);
        javaMailSender.send(message);
    }

    // GET all users

    @Transactional (readOnly = true)
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    // GET user by ID

    @Transactional(readOnly = true)
    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    // POST saving user

    @Transactional
    public String saveUser(UserRegisterRequestDTO userPayload){

        Optional<User> existingUser = userRepository.findByEmail(userPayload.email());
        if (existingUser.isPresent()) {
            throw new BadRequestException("User with this email already exists");
        }
        User user = new User();
        user.setFirstName(userPayload.firstName());
        user.setLastName(userPayload.lastName());
        user.setEmail(userPayload.email());
        user.setRole(Role.USER);
        user.setPassword(bcrypt.encode(userPayload.password()));
        sendRegistrationEmail(userPayload.email());
        userRepository.save(user);

        return "User with id: " + user.getId() + " saved correctly";
    }

    // POST saving user with admin role

    @Transactional
    public String saveUserAdmin(UserRegisterRequestDTO userPayload){

        Optional<User> existingUser = userRepository.findByEmail(userPayload.email());
        if (existingUser.isPresent()) {
            throw new BadRequestException("User with this email already exists");
        }
        User user = new User();
        user.setFirstName(userPayload.firstName());
        user.setLastName(userPayload.lastName());
        user.setEmail(userPayload.email());
        user.setRole(Role.ADMIN);
        user.setPassword(bcrypt.encode(userPayload.password()));
        sendRegistrationEmail(userPayload.email());
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
