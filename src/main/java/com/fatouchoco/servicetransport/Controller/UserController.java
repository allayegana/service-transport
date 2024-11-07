package com.fatouchoco.servicetransport.Controller;


import com.fatouchoco.servicetransport.Model.User;
import com.fatouchoco.servicetransport.Repository.UserRepository;
import com.fatouchoco.servicetransport.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/api/v1/transport")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(@ModelAttribute("user") User user) {
        return "register";
    }


    @GetMapping("/users")
    public String getUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users"; // Ensure this matches your template name
    }

    @PostMapping("/users/update")
    public String updateUser(@ModelAttribute User user) {
        userService.update(user); // Implement this method to update user details
        return "redirect:/api/v1/transport/users"; // Redirect back to the user list
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUsers(id); // Make sure this method exists and is correctly named in your service.
            redirectAttributes.addFlashAttribute("success", "User deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting user: " + e.getMessage());
        }
        return "redirect:/api/v1/transport/users"; // Ensure this redirect is correct based on your view resolver setup.
    }



    @PostMapping("/register")
    public ModelAndView registerUser(@Valid User user) {
        ModelAndView mv = new ModelAndView("register");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (!user.getAgency().equalsIgnoreCase("FatouHarlem")){
            return mv;
        }
        userRepository.save(user);
        mv.setViewName("redirect:/api/v1/transport/login");
        return mv;
    }
}