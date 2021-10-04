package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import com.example.entity.Item;
import com.example.entity.Member;
import com.example.jwt.JwtUtil;
import com.example.repository.ItemRepository;
import com.example.repository.MemberRepsoitory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/seller")
public class ApiSellerController {

    @Autowired
    ItemRepository iRepository;
    @Autowired
    MemberRepsoitory mRepository;
    @Autowired
    JwtUtil jwtUtil;

    // 물품등록 127.0.0.1:8080/ROOT/api/seller/insert_item
    @RequestMapping(value = "/insert_item", method = {
            RequestMethod.POST }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    // {"name":"a1", "content":"내용...", "price":1234, "quantity":1111}
    public Map<String, Object> insertItem(@RequestBody Item item, @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        String sellerId = jwtUtil.extractUsername(token.substring(7));
        Member member = mRepository.findById(sellerId).get();
        item.setMember(member);
        iRepository.save(item);
        map.put("status", 200); // SUCCESS
        return map;
    }

}
