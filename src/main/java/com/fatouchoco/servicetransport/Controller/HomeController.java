package com.fatouchoco.servicetransport.Controller;

import com.fatouchoco.servicetransport.Model.Product;
import com.fatouchoco.servicetransport.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/transport")
public class HomeController {

    @Autowired
    private ProductService productService;


    @GetMapping("/home")
    public String home(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("user", userDetails);
        return "home";
    }

    @GetMapping("/receipt")
    public String receipt(Model model) {
        return "receipt";
    }

    @GetMapping("/access-denied")
    public String accessDenied(Model model) {
        return "access-denied";
    }

    @GetMapping("/products")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.findAllProducts());
        return "product-list";
    }

    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "product-form";
    }

    @PostMapping("/product")
    public String addProduct(@ModelAttribute("product") Product product) {
        productService.addProduct(product);
        return "redirect:/api/v1/transport/products";
    }

    @GetMapping("/{id}")
    public String viewProduct(@PathVariable Long id, Model model) {
        Optional<Product> product = productService.findProductById(id);
        product.ifPresent(value -> model.addAttribute("product", value));
        return product.isPresent() ? "product-view" : "redirect:/products";
    }

    @GetMapping("/receipt/{id}")
    public String showReceipt(@PathVariable Long id, Model model) {
        Optional<Product> product = productService.findProductById(id);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            return "receipt";
        } else {
            return "redirect:/api/v1/transport/products"; // Redirect if the product doesn't exist
        }
    }

    @PostMapping("/update-status/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String status = request.get("status");
        productService.updateStatus(id, status);
        return ResponseEntity.ok().build();
    }



    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/api/v1/transport/products";
    }
}
