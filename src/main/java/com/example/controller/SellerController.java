package com.example.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.entity.Item;
import com.example.entity.ItemImage;
import com.example.entity.ItemProjection;
import com.example.repository.ItemImageRepository;
import com.example.repository.ItemRepository;
import com.example.repository.MemberRepsoitory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    ItemRepository iRepository;

    @Autowired
    MemberRepsoitory mRepsoitory;

    @Autowired
    ItemImageRepository iiRepository;

    @GetMapping(value = "/select_item")
    public String selectItemGet(Model model) {
        List<ItemProjection> list = iRepository.findAllByOrderByNoAsc();
        model.addAttribute("list", list);
        return "select_item";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home() {
        return "seller_home";
    }

    @GetMapping(value = "item_image_preview")
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

    @PostMapping(value = "/insert_item_image")
    public String insertItemImagePost(@RequestParam(name = "no") long no,
            @RequestParam(name = "file") MultipartFile[] files) throws IOException {

        Item item = iRepository.findById(no).get();

        List<ItemImage> list = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            ItemImage itemImage = new ItemImage();

            itemImage.setItem(item);
            itemImage.setImage(files[i].getBytes());
            itemImage.setImagename(files[i].getOriginalFilename());
            itemImage.setImagesize(files[i].getSize());
            itemImage.setImagetype(files[i].getContentType());
            list.add(itemImage);
        }

        iiRepository.saveAll(list);
        return "redirect:/seller/select_item";
    }

    @GetMapping(value = "/insert_item_image")
    public String insertItemImageGet() {
        return "insert_item_image";
    }

    @GetMapping(value = "/insert_item")
    public String insertItemGet() {
        return "insert_item";
    }

    @PostMapping(value = "/insert_item")
    public String insertItemPost(@RequestParam(name = "name") String[] name,
            @RequestParam(name = "content") String[] content, @RequestParam(name = "price") Long[] price,
            @RequestParam(name = "quantity") Long[] quantity) {

        List<Item> list = new ArrayList<>();
        for (int i = 0; i < name.length; i++) {
            Item item = new Item();
            item.setName(name[i]);
            item.setContent(content[i]);
            item.setPrice(price[i]);
            item.setQuantity(quantity[i]);
            list.add(item);
        }
        iRepository.saveAll(list);
        return "redirect:/seller/select_item"; // 페이지 전환
    }

}
