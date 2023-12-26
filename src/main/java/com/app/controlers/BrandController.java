package com.app.controlers;


import com.app.dtos.AddBrandsDto;
import com.app.services.NewBrandsServices;

import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
    @RequestMapping("/brands")
    public class BrandController {
        @Autowired
        private final NewBrandsServices newBrandsServices;

        private static final Logger LOG = LogManager.getLogger(Controller.class);

        public BrandController(NewBrandsServices newBrandsServices) {
            this.newBrandsServices = newBrandsServices;
        }

        @ModelAttribute("brandModel")
        public AddBrandsDto initCompany() {
            return new AddBrandsDto();
        }

        @GetMapping("/add")
        public String addBrand(){
            LOG.info("Received request to add brand");
            return "brand-add";
        }

        @PostMapping("/add")
        public String addBrand(@Valid AddBrandsDto brandModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("brandModel", brandModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.brandModel", bindingResult);
            LOG.info("Brand added successfully: {}", brandModel);
            return "redirect:/brands/add";
        }
        newBrandsServices.addBrands(brandModel);

        return "redirect:/";
    }
        @GetMapping("/all")
        public String showAllBrands(Model model){
            model.addAttribute("BrandInfo", newBrandsServices.allBrands());
            LOG.info("All Brands");
            return  "brand-all";
        }

        @GetMapping("/brand-details/{brand-name}")
        public  String brandsDetails(@PathVariable("brand-name") String brandName, Model model){
            LOG.info("Received request for brand details: {}", brandName);
            model.addAttribute("brandsDetails", newBrandsServices.brandsDetails(brandName));
            return "brand-details";
        }

        @GetMapping("/brand-delete/{brand-name}")
        public String deleteBrand(@PathVariable("brand-name") String brandName) {
            newBrandsServices.removeBrands(brandName);
            LOG.info("Brand deleted successfully: {}", brandName);
            return "redirect:/brands/all";
        }

}
