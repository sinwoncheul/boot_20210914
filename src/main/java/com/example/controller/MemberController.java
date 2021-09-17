package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.entity.Member;
import com.example.repository.MemberRepsoitory;

@Controller
@RequestMapping(value = "/member")
public class MemberController {

    @Autowired
    private MemberRepsoitory mRepository;

    // 127.0.0.1:8080/ROOT/member/join
    @RequestMapping(value = "/join", method = RequestMethod.GET)
    public String joinGET() {
        return "member_join";
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public String joinPOST(@ModelAttribute Member member) {
        // 암호화 종류 SHA1, MD5, Bcrypt, ...

        // a1 => 알고리즘 + salt => 3498fu89f3j83uur83r3
        // 3498fu89f3j83uur83r3 => 알고리즘 + salt => a1
        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
        member.setUserpw(bcpe.encode(member.getUserpw()));

        mRepository.save(member);
        return "redirect:/";
    }
}
