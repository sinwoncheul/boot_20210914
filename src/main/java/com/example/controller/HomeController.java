package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Item;
import com.example.entity.ItemImage;
import com.example.entity.ItemProjection2;
import com.example.repository.ItemImageRepository;
import com.example.repository.ItemRepository;

@Controller
public class HomeController {
    @Autowired
    ItemRepository iRepository;

    @Autowired
    ItemImageRepository iiRepository;

    @RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
    public String home(Model model, Authentication auth) {
        // 고유한 물품번호
        // List<ItemProjection2> list = iiRepository.queryDistinctItem();

        List<Item> list = iRepository.findAll();
        model.addAttribute("list", list);
        return "home";
    }

    @RequestMapping(value = "/page403", method = RequestMethod.GET)
    public String home() {
        return "page403"; // page403.jsp
    }

    @GetMapping(value = "/item_image_preview")
    public ResponseEntity<byte[]> itemImagePreview(@RequestParam("itemno") long no, @RequestParam("idx") int idx) {
        List<ItemImage> list = iiRepository.findByItem_No(no);
        ItemImage itemImage = list.get(idx);
        HttpHeaders headers = new HttpHeaders();
        if (itemImage.getImagetype().equals("image/jpeg")) {
            headers.setContentType(MediaType.IMAGE_JPEG);
        } else if (itemImage.getImagetype().equals("image/png")) {
            headers.setContentType(MediaType.IMAGE_PNG);
        } else if (itemImage.getImagetype().equals("image/gif")) {
            headers.setContentType(MediaType.IMAGE_GIF);
        }
        ResponseEntity<byte[]> response = new ResponseEntity<>(itemImage.getImage(), headers, HttpStatus.OK);
        return response;
    }

}