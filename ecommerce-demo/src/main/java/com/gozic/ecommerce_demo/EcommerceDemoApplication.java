package com.gozic.ecommerce_demo;

import com.gozic.ecommerce_demo.entity.Category;
import com.gozic.ecommerce_demo.entity.Product;
import com.gozic.ecommerce_demo.entity.User;
import com.gozic.ecommerce_demo.service.CategoryService;
import com.gozic.ecommerce_demo.service.ProductService;
import com.gozic.ecommerce_demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EcommerceDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceDemoApplication.class, args);
    }

    @Bean
    //This bean is initialized after the program is executed
    public CommandLineRunner commandLineRunner(UserService userService, CategoryService categoryService, ProductService productService) {

        return args -> {
//            deleteAllCategories(categoryService);
//            addCategories(categoryService);
//            createUser(userService);
//            findAllUser(userService);
            createProductWithCategories(productService, categoryService);
        };

    }

    private void createProductWithCategories(ProductService productService, CategoryService categoryService) {
        //Create a new product
        Product product = new Product();
        product.setName("Nike Vomero 18");
        product.setImageUrl("https://static.nike.com/a/images/t_PDP_1728_v1/f_auto,q_auto:eco/cc4f9228-c2a3-4df7-91d3-4722658c4fb2/NIKE+VOMERO+18.png");
        product.setDescription("Maximum cushioning in the Vomero provides a comfortable ride for everyday runs. Our softest, most cushioned ride has lightweight ZoomX foam stacked on top of responsive ReactX foam in the midsole. Plus, a redesigned traction pattern offers a smooth heel-to-toe transition.");
        product.setPrice(19.99);

        //Fetch category
        Category category1 = categoryService.findById(1);
        Category category3 = categoryService.findById(3);

        //Use the convenient method to link
        product.addCategory(category1);
        product.addCategory(category3);

        //save the product
        productService.save(product);

        System.out.println("Product created: " + product.toString());
    }

//    User clicks “Add to cart” button on a product, specifying a quantity →
//    Backend adds a new CartItem to that user's Cart (creating the cart if necessary) →
//    If the product is already in the cart, increase the quantity.

    private void findAllUser(UserService userService) {
        System.out.println("All users: " + userService.findAll());
    }

    private void deleteAllCategories(CategoryService categoryService) {
        categoryService.deleteAll();
        System.out.println("All categories deleted!");
    }

    private void addCategories(CategoryService categoryService) {

        Category category = new Category("Đồ Nam");
        Category category2 = new Category("Đồ Nữ");
        Category category3 = new Category("Đồ Bé Trai");
        Category category4 = new Category("Đồ Bé Gái");

        categoryService.addCategory(category);
        categoryService.addCategory(category2);
        categoryService.addCategory(category3);
        categoryService.addCategory(category4);

        System.out.println("Add categories successfully!");

    }

    private void deleteUserByUser(UserService userService) {
        int theId = 3;
        System.out.println("Find user id = " + theId);
        if (userService.findUserById(theId) == null) {
            System.out.println("No user found");
        } else {
            User userDb = userService.findUserById(theId);
            userService.deleteUserByUserEntity(userDb);
            System.out.println("User id - " + theId + " deleted.");
        }
    }

    private void findUserByUserId(UserService userService) {
        int theId = 3;
        System.out.println("Find user by id = " + theId);
        if (userService.findUserById(theId) == null) {
            System.out.println("No user found");
        } else {
            System.out.println("User infor: ");
            System.out.println(userService.findUserById(theId).toString());
        }
    }

    private void createUser(UserService userService) {
        //Create a User entity
        User tempUser = new User("nguyenhiu2k2", "123456", "nguyenhiu2k2@gmail.com", "0912345678");

        //Save the user and its associated cart
        User savedUser = userService.saveUsersWithCart(tempUser);

        //Print the saved user (and cart details)
        System.out.println("Saved User: " + savedUser);
        System.out.println("Saved Cart: " + savedUser.getCart());
    }

}
