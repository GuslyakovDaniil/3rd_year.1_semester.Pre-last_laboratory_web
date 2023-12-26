package com.app.controlers;
import com.app.dtos.AddModelsDto;
import com.app.dtos.AddOffersDto;
import com.app.services.NewOffersServices;
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
@RequestMapping("/offers")
public class OfferController {
    @Autowired
    private final NewOffersServices newOffersServices;

    public OfferController(NewOffersServices newOffersServices) {
        this.newOffersServices = newOffersServices;
    }

    private static final Logger LOG = LogManager.getLogger(OfferController.class);

    @ModelAttribute("offerModel")
    public AddOffersDto initOffer(){
        return new AddOffersDto();
    }

    @GetMapping("/add")
    public String addOffer(){
        LOG.info("Received request to add offer");
        return "offer-add";
    }

    @PostMapping("/add")
    public String addOffer(@Valid AddOffersDto offerModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("offerModel", offerModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.offerModel", bindingResult);
            return "redirect:/offers/add";
        }
        newOffersServices.addOffer(offerModel);

        return "redirect:/";
    }

    @GetMapping("/all")
    public String showAllOffers(Model model){
        model.addAttribute("OfferInfo", newOffersServices.allOffers());
        LOG.info("Showing all offers");
        return  "offer-all";
    }

    @GetMapping("/top")
    public String showTenOffers(Model model){
        model.addAttribute("OfferInfo", newOffersServices.top10Offers());
        LOG.info("Show top offers");
        return  "top-tree";
    }

    @GetMapping("/offer-details/{offer-description}")
    public  String offerDetails(@PathVariable("offer-description") String description, Model model){
        model.addAttribute("offersDetails", newOffersServices.offersDetails(description));
        LOG.info("Showing description of offers: {}", description);
        return "offer-details";
    }

    @GetMapping("/offer-delete/{offer-description}")
    public String deleteOffer(@PathVariable("offer-description") String description){
        newOffersServices.removeOffer(description);
        LOG.info("Deleting model");
        return "redirect:/offers/all";
    }
}
