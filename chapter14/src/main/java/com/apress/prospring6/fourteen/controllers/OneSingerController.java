package com.apress.prospring6.fourteen.controllers;

import com.apress.prospring6.fourteen.entities.Singer;
import com.apress.prospring6.fourteen.services.SingerService;
import com.apress.prospring6.fourteen.util.SingerForm;
import jakarta.validation.Valid;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

@Controller
@RequestMapping("/singer/{id}")
public class OneSingerController {

    private final Logger LOGGER = LoggerFactory.getLogger(OneSingerController.class);
    private final SingerService singerService;
    private final MessageSource messageSource;

    public OneSingerController(SingerService singerService, MessageSource messageSource) {
        this.singerService = singerService;
        this.messageSource = messageSource;
    }

    @GetMapping
    public String showSingerData(@PathVariable("id") Long id, Model uiModel) {
        Singer singer = singerService.findById(id);
        uiModel.addAttribute("singer", singer);

        return "singers/show";
    }

    @GetMapping(value = "/photo")
    @ResponseBody
    public byte[] downloadPhoto(@PathVariable("id") Long id) {
        Singer singer = singerService.findById(id);

        if (singer.getPhoto() != null) {
            LOGGER.info("Downloading photo for id: {} with size: {}", singer.getId(),
                    singer.getPhoto().length);
            return singer.getPhoto();
        }

        return null;
    }

    @DeleteMapping
    public String deleteSinger(@PathVariable("id") Long id) {
        singerService.findById(id);
        singerService.delete(id);
        return "redirect:/singers/list";
    }

    @GetMapping(path = "/edit")
    public String showEditForm(@PathVariable("id") Long id, Model uiModel) {
        Singer singer = singerService.findById(id);
        uiModel.addAttribute("singer", singer);
        return "singers/edit";
    }

    @PutMapping
    public String updateSingerInfo(@Valid Singer singer, BindingResult bindingResult, Model uiModel, Locale locale) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("message", messageSource.getMessage("singer.save.fail",
                    new Object[]{}, locale));
            uiModel.addAttribute("singer", singer);
            return "singers/edit";
        } else {
            uiModel.asMap().clear();

            var fromDb = singerService.findById(singer.getId());

            fromDb.setFirstName(singer.getFirstName());
            fromDb.setLastName(singer.getLastName());
            fromDb.setBirthDate(singer.getBirthDate());

            singerService.save(fromDb);
            return "redirect:/singer/" + singer.getId();
        }
    }

    @GetMapping(value = "/create")
    public String showCreateForm(Model uiModel) {
        uiModel.addAttribute("singerForm", new SingerForm());
        return "singers/create";
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String create(@Valid SingerForm singerForm, BindingResult bindingResult,
                         Model uiModel,
                         Locale locale) throws IOException {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("message", messageSource.getMessage("singer.save.fail",
                    new Object[]{}, locale));
            uiModel.addAttribute("singerForm", singerForm);
            return "singers/create";
        }

        uiModel.asMap().clear();

        var singer = new Singer();
        singer.setFirstName(singerForm.getFirstName());
        singer.setLastName(singerForm.getLastName());
        singer.setBirthDate(singerForm.getBirthDate());

        //Process file upload
        if (!singerForm.getFile().isEmpty()) {
            setPhoto(singer, singerForm.getFile());
        }

        var created = singerService.save(singer);
        return "redirect:/singer/" + created.getId();
    }

    static void setPhoto(Singer singer, MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        var fileContent = IOUtils.toByteArray(inputStream);
        singer.setPhoto(fileContent);
    }
}
