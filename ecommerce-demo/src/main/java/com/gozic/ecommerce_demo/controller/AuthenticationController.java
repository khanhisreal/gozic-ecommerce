package com.gozic.ecommerce_demo.controller;

import com.gozic.ecommerce_demo.dto.UserRegistrationDTO;
import com.gozic.ecommerce_demo.entity.Cart;
import com.gozic.ecommerce_demo.entity.CartItem;
import com.gozic.ecommerce_demo.entity.Product;
import com.gozic.ecommerce_demo.entity.User;
import com.gozic.ecommerce_demo.repository.*;
import com.gozic.ecommerce_demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AuthenticationController {

    UserService userService;
    UserRepository userRepository;
    CategoryRepository categoryRepository;
    ProductRepository productRepository;
    CartRepository cartRepository;
    CartItemRepository cartItemRepository;

    @Autowired
    public AuthenticationController(UserService userService, UserRepository userRepository, CategoryRepository categoryRepository, ProductRepository productRepository, CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
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

    //show homepage
    @GetMapping("/home")
    public String showHome(Model theModel, HttpSession session, Principal principal) {

        //add username of the current user to the session
        if (principal != null && session.getAttribute("username") == null) {
            session.setAttribute("username", principal.getName());
        }

        //fetch db data
        List<Product> prodForMen = productRepository.findProductsByCategoryId(1);
        List<Product> prodForWomen = productRepository.findProductsByCategoryId(2);
        List<Product> prodForBoys = productRepository.findProductsByCategoryId(3);
        List<Product> prodForGirls = productRepository.findProductsByCategoryId(4);

        //send to the view layer
        theModel.addAttribute("menProds", prodForMen);
        theModel.addAttribute("womenProds", prodForWomen);
        theModel.addAttribute("boysProds", prodForBoys);
        theModel.addAttribute("girlsProds", prodForGirls);

        //fetch current cart items of users who are logged in
        if (principal != null) {
            // Get the logged-in user by username
            User currentUser = userRepository.findByUsername(principal.getName());
            if (currentUser != null) {
                Cart userCart = cartRepository.findByUser(currentUser).get();
                if (userCart != null) {
                    List<CartItem> cartItems = cartItemRepository.findByCart(userCart);
                    theModel.addAttribute("cartItems", cartItems);
                }
            }
        } else {
            theModel.addAttribute("cartItems", new ArrayList<>()); // user not logged in
        }


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
