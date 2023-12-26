package com.app.controlers;

import com.app.dtos.AddBrandsDto;
import com.app.dtos.AddModelsDto;
import com.app.dtos.ShowModelInfoDto;
import com.app.enums.Category;
import com.app.services.NewModelsServices;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/models")
public class ModelController {
    @Autowired
    private final NewModelsServices newModelsServices;

    private static final Logger LOG = LogManager.getLogger(ModelController.class);

    public ModelController(NewModelsServices newModelsServices) {
        this.newModelsServices = newModelsServices;
    }

    @ModelAttribute("modelModel")
    public AddModelsDto initModel(){
        return new AddModelsDto();
    }

    @GetMapping("/add")
    public String addModel(){
        LOG.info("Received request to add model");
        return "model-add";
    }

    @PostMapping("/add")
    public String addModel(@Valid AddModelsDto modelModel, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            // Оставить ошибки в модели для отображения на странице
            redirectAttributes.addFlashAttribute("modelModel", modelModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.modelModel", bindingResult);
            return "redirect:/models/add"; // Перенаправить на страницу с формой
        }

        newModelsServices.addModels(modelModel);

        return "redirect:/"; // Перенаправить на главную страницу или другую страницу по вашему выбору
    }



    // Add the following method to handle filtering based on enum
    @GetMapping("/all")
    public String showModelsByCategory(@RequestParam(name = "category", required = false) Category category, Model model) {
        List<ShowModelInfoDto> filteredModels;

        if (category != null) {
            // If a category is selected, filter models by category
            filteredModels = newModelsServices.modelsByCategory(category);
        } else {
            // If no category is selected, get all models
            filteredModels = newModelsServices.allModels();
        }

        model.addAttribute("ModelInfo", filteredModels);
        LOG.info("Showing models by category: {}", category);
        return "model-all";
    }



    @GetMapping("/model-details/{model-name}")
    public  String modelDetails(@PathVariable("model-name") String name, Model model){
        model.addAttribute("modelsDetails", newModelsServices.modelsDetails(name));
        LOG.info("Showing details for model: {}", name);
        return "model-details";
    }

    @GetMapping("/model-delete/{model-name}")
    public String deleteModel(@PathVariable("model-name") String modelName){
        newModelsServices.removeModel(modelName);
        LOG.info("Deleting model: {}", modelName);
        return "redirect:/models/all";
    }
}
