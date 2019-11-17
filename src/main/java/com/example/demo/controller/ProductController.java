package com.example.demo.controller;

import com.example.demo.domain.entity.shop.Product;
import com.example.demo.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @RequestMapping(value= "getAllProductsEnabled/{page_id}", method= RequestMethod.GET)
    @ResponseBody
    public ModelAndView getAllProductsEnabled(@PathVariable(value = "page_id") int page_id) {
        Page<Product> pageProduct = productService.findAllByEnabledTrueOrderByProductName(page_id);
        List<Product> productList = pageProduct.getContent();

        ModelAndView mav = new ModelAndView();
        mav.addObject("products", productList);
        mav.addObject("showPrevious", pageProduct.hasPrevious());
        mav.addObject("showNext", pageProduct.hasNext());
        mav.addObject("previousPage", page_id - 1);
        mav.addObject("nextPage", page_id + 1);
        mav.setViewName("productList");
        logger.info("Model take List<Product> with status Enabled, listProducts_size= " + productList.size());
        return mav;
    }

    @RequestMapping(value = "admin/product/getProductsDisabled", method = RequestMethod.GET)
    public String listDisabledProducts (Model uiModel){
        List<Product> products = productService.getAllDisabledProducts();
        logger.info("Model take List<Product> with status Disabled, listProducts_size= " + products.size());
        uiModel.addAttribute("products", products);
        return "productDisableList";
    }

    @RequestMapping(value = "getProductById/{productId}")
    public ModelAndView getProductById(@PathVariable(value = "productId") Long productId) {
        logger.info("Product will find by productId= " + productId);
        Product product = productService.getProductById(productId);
        return new ModelAndView("productPage", "productEntity", product);
    }

    @RequestMapping(value = "admin/product/delete/{productId}")
    public String deleteProduct(@PathVariable(value = "productId") Long productId) {
        logger.info("Product will have status disabled by productId= " + productId);
        productService.deleteProduct(productId);
        return "redirect:/getAllProductsEnabled/0";
    }


    @RequestMapping(value = "admin/product/addProduct", method = RequestMethod.GET)
    public String getProductForm(Model model) {
        Product product = new Product();
        logger.info("New product entity send to model, product= " + product);
        model.addAttribute("productForm", product);
        return "addProduct";
    }

    @RequestMapping(value = "admin/product/addProduct", method = RequestMethod.POST)
    public String addProduct(@Valid @ModelAttribute(value = "productForm")
                                         Product product, BindingResult result) {
        if (result.hasErrors())
            return "addProduct";
        logger.info("Product take from model and will send to service for save/update, product= " + product);
        productService.addProduct(product);
        return "redirect:/admin/product/addProduct";
    }

    @RequestMapping(value = "admin/product/update/{id}")
    public ModelAndView getEditForm(@PathVariable(value = "id") Long id) {
        Product product = productService.getProductById(id);
        logger.info("Product find by productId and send to model for edit, product= " + product);
        return new ModelAndView("editProduct", "product", product);
    }

    @RequestMapping(value = "admin/product/update", method = RequestMethod.POST)
    public String editProduct(@ModelAttribute(value = "product") Product product) {
        logger.info("Product take from model and will send to service for update , product= " + product);
        productService.editProduct(product);
        return "redirect:/getAllProductsEnabled/0";
    }

}
