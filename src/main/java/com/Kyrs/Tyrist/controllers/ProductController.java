
package com.Kyrs.Tyrist.controllers;

import jakarta.validation.Valid;
import org.springframework.ui.Model;
import com.Kyrs.Tyrist.models.Product;
import com.Kyrs.Tyrist.models.ProductDto;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.Kyrs.Tyrist.services.ProductsRepository;
import org.springframework.validation.BindingResult;





import java.util.Date;
import java.util.Optional;
@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductsRepository repo;

    public ProductController(ProductsRepository repo) {
        this.repo = repo;
    }

    @GetMapping({"", "/"})
    public String showProductList(Model model) {
        model.addAttribute("products", repo.findAll());
        return "products/index";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        model.addAttribute("productDto", new ProductDto());
        return "products/CreateProduct";
    }

    @PostMapping("/create")
    public String createProduct(@Valid @ModelAttribute ProductDto productDto, BindingResult result) {
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
        repo.save(product);
        return "redirect:/products";
    }




    @GetMapping("/edit/{id}")
    public String showEditPage(@PathVariable int id, Model model) {
        Optional<Product> optionalProduct = repo.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            ProductDto productDto = new ProductDto();

            // Заполнение DTO
            productDto.setName(product.getName());
            productDto.setBrand(product.getBrand());
            productDto.setCategory(product.getCategory());
            productDto.setPrice(product.getPrice());
            productDto.setDescription(product.getDescription());

            // Выводим информацию о продукте в терминал
            System.out.println("Product ID: " + product.getId());
            System.out.println("Product Name: " + product.getName());
            System.out.println("Product Brand: " + product.getBrand());
            System.out.println("Product Category: " + product.getCategory());
            System.out.println("Product Price: " + product.getPrice());
            System.out.println("Product Description: " + product.getDescription());

            model.addAttribute("product", product);
            model.addAttribute("productDto", productDto);

            return "products/EditProduct"; // Возвращаем страницу редактирования
        }
        return "redirect:/products?error=not-found";
    }





//
//
//    @PostMapping("/edit/{id}")
//    public String updateProduct(@PathVariable int id, @Valid @ModelAttribute ProductDto productDto, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            return "products/EditProduct";
//        }
//
//        Optional<Product> optionalProduct = repo.findById(id);
//        if (optionalProduct.isPresent()) {
//            Product product = optionalProduct.get();
//
//            product.setName(productDto.getName());
//            product.setBrand(productDto.getBrand());
//            product.setCategory(productDto.getCategory());
//            product.setPrice(productDto.getPrice());
//            product.setDescription(productDto.getDescription());
//            product.setCreatedAt(new Date());  // Можно оставить старое значение, если не хотите обновлять дату
//
//            repo.save(product);
//
//            return "redirect:/products";
//        }
//
//        return "redirect:/products?error=not-found";
//    }
//
//
//
//
//
//
//
//
//    @GetMapping("/delete")
//    public String deleteProduct(
//            @RequestParam int id
//    ){
//        try {
//            Product product = repo.findById(id).get();
//
//        }
//        catch (Exception ex ){
//            System.out.println("Ex");
//        }
//
//
//
//
//        return "rediect:/products";
//    }

}
