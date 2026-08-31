package com.apress.prospring6.seventeen;

import com.apress.prospring6.seventeen.entities.Singer;
import com.apress.prospring6.seventeen.services.SingerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/singer/{id}")
public class OneSingerController {

    private final Logger LOGGER = LoggerFactory.getLogger(OneSingerController.class);

    private final SingerService singerService;

    public OneSingerController(SingerService singerService) {
        this.singerService = singerService;
    }

    @GetMapping
    public String showSingerData(@PathVariable("id") Long id, Model uiModel) {
        Singer singer = singerService.findById(id);
        uiModel.addAttribute("singer", singer);
        return "show";
    }

    @PreAuthorize("hasRole('admin')")
    @DeleteMapping
    public String deleteSinger(@PathVariable("id") Long id) {
        singerService.findById(id);
        singerService.delete(id);
        return "redirect:/singers/list";
    }
}
