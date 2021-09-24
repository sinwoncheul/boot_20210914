package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.entity.Address;
import com.example.entity.Member;
import com.example.repository.AddressRepository;
import com.example.repository.MemberRepsoitory;

@Controller
@RequestMapping(value = "/member")
public class MemberController {

    @Autowired
    private MemberRepsoitory mRepository;

    @Autowired
    private AddressRepository aRepsoitory;

    @PostMapping(value = "/address_update")
    public String addressUpdatePost(Authentication auth, @ModelAttribute Address address) {
        if (auth != null) {
            User user = (User) auth.getPrincipal();
            Member member = mRepository.findById(user.getUsername()).get();

            address.setMember(member);
            aRepsoitory.save(address);
            return "redirect:/member/address";
        }
        return "redirect:/member/login";
    }

    // http://127.0.0.1:8080/ROOT/member/address_update?no=7
    @GetMapping(value = "/address_update")
    public String addressUpdate(Authentication auth, Model model, @RequestParam(name = "no") long no) {
        if (auth != null) {
            Address address = aRepsoitory.findById(no).get();
            model.addAttribute("address", address);
            return "member_address_update";
        }
        return "redirect:/member/login";
    }

    @GetMapping(value = "/address_delete")
    public String addressDelete(Authentication auth, @RequestParam(name = "no") long no) {
        if (auth != null) {
            User user = (User) auth.getPrincipal();
            // 기본키의 주소번호로 삭제
            aRepsoitory.deleteByNoAndMember_Userid(no, user.getUsername());

            // 기본키의 주소번호 and 현재사용자의 아이디
            return "redirect:/member/address";
        }
        return "redirect:/member/login";
    }

    @PostMapping(value = "address")
    public String addressPost(Authentication auth, @RequestParam(name = "addr") String addr) {
        if (auth != null) {
            User user = (User) auth.getPrincipal();
            Optional<Member> obj = mRepository.findById(user.getUsername());

            Address address = new Address();
            address.setMember(obj.get());
            address.setAddr(addr);
            aRepsoitory.save(address);

            return "redirect:/member/address";
        }
        return "redirect:/member/login";
    }

    @GetMapping(value = "/address")
    public String addressGET(Authentication auth, Model model) {
        if (auth != null) { // 로그인 되었다면
            User user = (User) auth.getPrincipal();

            List<Address> list = aRepsoitory.findByMember_UseridOrderByNoAsc(user.getUsername());

            model.addAttribute("list", list);
            return "member_address";
        }
        return "redirect:/member/login";
    }

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

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGET() {
        return "member_login";
    }

    @RequestMapping(value = "/mypage", method = RequestMethod.GET)
    public String mypageGET(Authentication authentication, Model model,
            @RequestParam(name = "menu", defaultValue = "0") int menu) {
        if (menu == 0) {
            return "redirect:/member/mypage?menu=1";
        }
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();

            Optional<Member> obj = mRepository.findById(user.getUsername());
            model.addAttribute("member", obj.get());

            return "member_mypage";
        } else { // 로그인이 되어 있지 않다면
            return "redirect:/member/login";
        }

    }

    @PostMapping(value = "/mypage")
    public String mypagePost(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(name = "menu") int menu, @RequestParam(name = "username", required = false) String username,
            @RequestParam(name = "usertel", required = false) String usertel,
            @RequestParam(name = "oldpw", required = false) String oldpw,
            @RequestParam(name = "newpw", required = false) String newpw, Authentication auth) {
        if (auth != null) { // 로그인이 되어 있다면
            User user = (User) auth.getPrincipal();
            Member member = mRepository.getById(user.getUsername());
            if (menu == 1) {
                // 정보 수정
                member.setUsername(username);
                member.setUsertel(usertel);
                mRepository.save(member);
                return "redirect:/member/mypage?menu=1";
            } else if (menu == 2) {
                // 암호 변경
                BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
                // 앞쪽이 암호화전문자, 뒤쪽은 암호화후문자
                if (bcpe.matches(oldpw, member.getUserpw())) {
                    member.setUserpw(bcpe.encode(newpw));
                    mRepository.save(member);
                }
                return "redirect:/member/mypage?menu=2";
            } else if (menu == 3) {
                // 회원탈퇴 및 자동 로그아웃
                BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
                if (bcpe.matches(oldpw, member.getUserpw())) {
                    mRepository.deleteById(member.getUserid());
                    new SecurityContextLogoutHandler().logout(request, response, auth);
                    return "redirect:/";
                }
                return "redirect:/member/mypage?menu=3";
            }
            return "redirect:/member/mypage?menu=1";

        } else {
            return "redirect:/member/login";
        }
    }

}
