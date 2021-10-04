package com.example.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.Item;
import com.example.entity.Member;
import com.example.entity.Order;
import com.example.jwt.JwtUtil;
import com.example.repository.ItemRepository;
import com.example.repository.MemberRepsoitory;
import com.example.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class ApiCustomerController {

    @Autowired
    MemberRepsoitory mRepository;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    OrderRepository oRepository;
    @Autowired
    ItemRepository iRepository;

    @RequestMapping(value = "/customer/order", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    // headers => token, form => cnt, itemno
    public Map<String, Object> memberPasswd(@ModelAttribute Order order, @RequestHeader("token") String token) {
        String id = jwtUtil.extractUsername(token.substring(7));
        Member member = mRepository.findById(id).get();
        Item item = iRepository.findById(order.getItemno()).get();

        order.setMember(member);
        order.setItem(item);
        oRepository.save(order);
        Map<String, Object> map = new HashMap<>();
        map.put("status", 200L);
        return map;
    }

    // 127.0.0.1:8080/api/customer/orderlist
    // token
    @RequestMapping(value = "/customer/orderlist", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> customerOrderList(@RequestHeader("token") String token) {
        String id = jwtUtil.extractUsername(token.substring(7));
        List<Order> list = oRepository.findByMember_Userid(id);
        Map<String, Object> map = new HashMap<>();
        map.put("status", 200L);
        map.put("list", list);
        return map;
    }

}
