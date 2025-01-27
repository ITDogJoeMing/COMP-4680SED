package com.comp4680.webportal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.Valid;
import com.comp4680.webportal.models.CustomException;
import com.comp4680.webportal.models.Items;
import com.comp4680.webportal.repos.EventsRepo;
import com.comp4680.webportal.repos.ItemsRepo;
import com.comp4680.webportal.utils.ItemsValidator;

@Controller
@RequestMapping("/items")
public class ItemsController {

    private static final String SUBPATH = "items/";

    @Autowired
    private ItemsRepo repo;

    @Autowired
    private EventsRepo eventsRepo;

    @Autowired
    private ItemsValidator validator;

    @GetMapping({ "", "/", "/index" })
    public String index(ModelMap map) {
        map.addAttribute("items", repo.findAll());
        return SUBPATH + "index";
    }

    @GetMapping("/create")
    public String create(ModelMap map) throws CustomException {
        if (eventsRepo.findAll().isEmpty()) {
            throw new CustomException("ITM-ADD", "No available events! Please create an event first", "/items/index");
        }
        map.addAttribute("item", new Items());
        map.addAttribute("events", eventsRepo.findAll());
        return SUBPATH + "form";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("item") Items item, BindingResult result, ModelMap map) {
        validator.validate(item, result);
        if (result.hasErrors()) {
            map.addAttribute("item", item);
            map.addAttribute("events", eventsRepo.findAll());
            return SUBPATH + "form";
        }
        repo.save(item);
        return "redirect:index";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, ModelMap map) throws CustomException {
        if (!repo.existsById(id)) {
            throw new CustomException("ITM-EDIT", String.format("Item not found with id [%d]", id), "/items/index");
        }
        map.addAttribute("item", repo.findById(id).get());
        map.addAttribute("events", eventsRepo.findAll());
        return SUBPATH + "form";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("item") Items item, BindingResult result, ModelMap map) {
        validator.validate(item, result);
        if (result.hasErrors()) {
            map.addAttribute("item", item);
            map.addAttribute("events", eventsRepo.findAll());
            return SUBPATH + "form";
        }
        repo.save(item);
        return "redirect:index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) throws CustomException {
        if (!repo.existsById(id)) {
            throw new CustomException("ITM-DEL", String.format("Item not found with id [%d]", id), "/items/index");
        }
        if (repo.isItemsSold(id).isPresent()) {
            throw new CustomException("ITM-DEL", String.format("Item with id [%d] is already sold! Cannot delete!", id),
                    "/items/index");
        }
        repo.deleteById(id);
        return "redirect:/" + SUBPATH + "index";
    }
}