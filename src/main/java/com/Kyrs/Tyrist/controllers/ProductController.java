package com.Kyrs.Tyrist.controllers;

import com.Kyrs.Tyrist.models.Product;
import com.Kyrs.Tyrist.models.ProductDto;
import com.Kyrs.Tyrist.services.ProductsRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductsRepository repo;

    public ProductController(ProductsRepository repo) {
        this.repo = repo;
    }

    @GetMapping({"", "/"})
    public String showProductList(Model model) {
        List<Product> products = repo.findAll();
        model.addAttribute("products", products);
        return "products/index";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        model.addAttribute("productDto", new ProductDto());
        return "products/CreateProduct";
    }

    @PostMapping("/create")
    public String createProduct(
            @Valid @ModelAttribute ProductDto productDto,
            BindingResult result) {

//        if (productDto.getImageFile() == null || productDto.getImageFile().isEmpty()) {
//            result.addError(new FieldError("productDto", "imageFile", "Image file is required"));
//        }

        if (result.hasErrors()) {
            return "products/CreateProduct";
        }

        Product product = new Product();
        product.setName(productDto.getName());
        product.setBrand(productDto.getBrand());
        product.setCategory(productDto.getCategory());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setCreatedAt(new Date());
        // Добавьте здесь логику сохранения файла изображения, если требуется
        // product.setImageFileName(storageFileName);

        repo.save(product);

        return "redirect:/products";
    }
}
