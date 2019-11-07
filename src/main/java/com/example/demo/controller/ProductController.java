package com.example.demo.controller;

import com.example.demo.domain.entity.shop.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "getAllProducts", method = RequestMethod.GET)
    public String listProducts (Model uiModel){
        List<Product> products = productService.getAllProducts();
        uiModel.addAttribute("products", products);
        return "productList";
    }

    @RequestMapping(value = "admin/product/getProductsDisabled", method = RequestMethod.GET)
    public String listDisabledProducts (Model uiModel){
        List<Product> products = productService.getAllDisabledProducts();
        uiModel.addAttribute("products", products);
        return "productDisableList";
    }

    @RequestMapping(value = "getProductById/{productId}")
    public ModelAndView getProductById(@PathVariable(value = "productId") Long productId) {
        Product product = productService.getProductById(productId);
        return new ModelAndView("productPage", "productEntity", product);
    }

    @RequestMapping(value = "admin/product/delete/{productId}")
    public String deleteProduct(@PathVariable(value = "productId") Long productId) {
        productService.deleteProduct(productId);
        return "redirect:/getAllProducts";
    }

    @RequestMapping(value = "admin/product/addProduct", method = RequestMethod.GET)
    public String getProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("productForm", product);
        return "addProduct";
    }

    @RequestMapping(value = "admin/product/addProduct", method = RequestMethod.POST)
    public String addProduct(@Valid @ModelAttribute(value = "productForm")
                                         Product product, BindingResult result) {
        if (result.hasErrors())
            return "addProduct";
        productService.addProduct(product);
        return "redirect:addProduct";
    }

    @RequestMapping(value = "admin/product/update/{id}")
    public ModelAndView getEditForm(@PathVariable(value = "id") Long id) {
        Product product = productService.getProductById(id);
        return new ModelAndView("editProduct", "product", product);
    }

    @RequestMapping(value = "admin/product/update", method = RequestMethod.POST)
    public String editProduct(@ModelAttribute(value = "product") Product product) {
        productService.editProduct(product);
        return "redirect:/getAllProducts";
    }

}
