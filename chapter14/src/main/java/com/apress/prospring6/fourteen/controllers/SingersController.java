package com.apress.prospring6.fourteen.controllers;

import com.apress.prospring6.fourteen.entities.AbstractEntity;
import com.apress.prospring6.fourteen.entities.Singer;
import com.apress.prospring6.fourteen.services.SingerService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/singers")
public class SingersController {

    private static Comparator<AbstractEntity> COMPARATOR_BY_ID = Comparator.comparing(AbstractEntity::getId);

    private final SingerService singerService;
    private final MessageSource messageSource;

    public SingersController(SingerService singerService, MessageSource messageSource) {
        this.singerService = singerService;
        this.messageSource = messageSource;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel) {
        List<Singer> singers = singerService.findAll();
        singers.sort(COMPARATOR_BY_ID);
        uiModel.addAttribute("singers", singers);

        return "singers/list";
    }
}
