package com.gozic.ecommerce_demo.controller;

import com.gozic.ecommerce_demo.dto.UserRegistrationDTO;
import com.gozic.ecommerce_demo.dto.UserSigninDTO;
import com.gozic.ecommerce_demo.entity.User;
import com.gozic.ecommerce_demo.repository.UserRepository;
import com.gozic.ecommerce_demo.service.UserService;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthenticationController {

    UserService userService;
    UserRepository userRepository;

    @Autowired
    public AuthenticationController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    //default request mapping
    @GetMapping("/")
    public String defaultMapping() {
        return "redirect:/signIn";
    }

    //show sign-in form
    @GetMapping("/signIn")
    public String showForm() {
        return "sign-in";
    }

    @PostMapping("/processSignIn")
    public String processSignIn(@ModelAttribute UserSigninDTO userSigninDTO, Model model) {

        //fetch user from db
        User userDb = new User();

        try {
            userDb = userRepository.findByUsername(userSigninDTO.getUsername());
        } catch (NoResultException e) {
            model.addAttribute("errorMessage", "Wrong credential information");
            return "sign-in";
        }

        //check for password
        if(!userDb.getPassword().equals(userSigninDTO.getPassword())) {
            model.addAttribute("errorMessage", "Wrong credential information");
            return "sign-in";
        }

        return "redirect:/home";
    }

    //show homepage
    @GetMapping("/home")
    public String showHome() {
        return "home";
    }

    // show sign-up form
    @GetMapping("/signUp")
    public String showSignUpForm() {
        return "sign-up";
    }

    // process sign-up form submission
    //@ModelAttribute: data binding that maps data of the form to the DTO object
    //Model: data container used to pass data from controller to view
    @PostMapping("/processSignUp")
    public String processSignUp(@ModelAttribute UserRegistrationDTO userRegistrationDTO, Model model) {

        //temp - redirect to the same page if wrong confirmation
        if (!userRegistrationDTO.getPassword().equals(userRegistrationDTO.getConfirmPassword())) {
            model.addAttribute("errorMessage", "Passwords do not match.");
            return "sign-up";
        }

        //server-side validation
        if (userService.existsByUsername(userRegistrationDTO.getUsername())) {
            model.addAttribute("errorMessage", "Username is already taken.");
            return "sign-up";
        }

        if (userService.existsByEmail(userRegistrationDTO.getEmail())) {
            model.addAttribute("errorMessage", "Email is already in use.");
            return "sign-up";
        }

        //Map DTO en Entity
        User newUser = new User();
        newUser.setUsername(userRegistrationDTO.getUsername());
        newUser.setEmail(userRegistrationDTO.getEmail());
        newUser.setPassword(userRegistrationDTO.getPassword());

        //save
        userService.saveUsersWithCart(newUser);

        return "redirect:/signIn";
    }

    //show forget password form
    @GetMapping("/forgetPassword")
    public String showForgetPasswordForm() {
        return "forget-password";
    }

    // process forget-password form submission
    @PostMapping("/processForgetPassword")
    public String processForgetPassword(@RequestParam String emailOrPhone) {
        // check user existence and reset password logic
        return "redirect:/showForm";
    }

}
