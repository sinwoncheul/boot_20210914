package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Member;
import com.example.jwt.JwtUtil;
import com.example.repository.MemberRepsoitory;

@RestController
@RequestMapping(value = "/api")
public class ApiMemberController {

    @Autowired
    MemberRepsoitory mRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @RequestMapping(value = "/member/passwd", method = RequestMethod.PUT, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    // {userid, userpw, usernewpw}
    public Map<String, Object> memberPasswd(@RequestBody Map<String, Object> mapObj,
            @RequestHeader("token") String token) {

        Map<String, Object> map = new HashMap<>();
        try {
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            // @RequestBody Map<> 으로 데이터 받기
            String userid = (String) mapObj.get("userid");
            String userpw = (String) mapObj.get("userpw");
            String usernewpw = (String) mapObj.get("usernewpw");

            System.out.println(token.substring(7));
            // 토큰과 사용자아이디 일치시점
            if (jwtUtil.extractUsername(token.substring(7)).equals(userid)) {

                // 아이디를 이용해서 기존 정보 가져오기
                Member member1 = mRepository.findById(userid).get();

                // 기존 암호와 전달된 암호가 같으면 새로운 암호로 변경
                if (bcpe.matches(userpw, member1.getUserpw())) {
                    Member member = new Member();
                    member.setUserid(userid);
                    member.setUserpw(bcpe.encode(usernewpw));

                    // 아이디, 암호를 제외한 항목은 기본 값으로 대체

                    mRepository.save(member);
                }

                map.put("status", 200);
            }
        } catch (Exception e) {
            // e.printStackTrace();
            map.put("status", 578);
        }
        return map;
    }

    @CrossOrigin("*")
    @RequestMapping(value = "/member/update", method = RequestMethod.PUT, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    // {"userid":"aaa", 이름, 연락처}
    public Map<String, Object> memberUpdate(@RequestBody Member member) {
        Map<String, Object> map = new HashMap<>();
        try {
            Member member1 = mRepository.findById(member.getUserid()).get();
            member.setUserpw(member1.getUserpw());
            member.setUserrole(member1.getUserrole());
            mRepository.save(member);
            map.put("status", 200);
        } catch (Exception e) {
            // e.printStackTrace();
            map.put("status", 578);
        }
        return map;
    }

    // 127.0.0.1:8080/ROOT/api/member/delete => body { }
    @RequestMapping(value = "/member/delete", method = RequestMethod.DELETE, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    // {"userid":"aaa", "userpw":"aaa"}
    public Map<String, Object> memberDelete(@RequestBody Member member) {
        Map<String, Object> map = new HashMap<>();
        try {
            Member member1 = mRepository.findById(member.getUserid()).get();
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            // 암호화 전 문자, 암호화 후 문자
            if (bcpe.matches(member.getUserpw(), member1.getUserpw())) {
                mRepository.deleteById(member.getUserid());
                map.put("status", 200);
            } else {
                map.put("status", 589);
            }
        } catch (Exception e) {
            // e.printStackTrace();
            map.put("status", 578);
        }
        return map;
    }

    // /ROOT/api/member_select => 토큰발행
    // {"userid":"aaa", "userpw":"bbb"}
    @RequestMapping(value = "/member_select", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> memberSelect(@RequestBody Member member) {
        Map<String, Object> map = new HashMap<>();
        try {
            // 인증서 매니저를 이용하여 아이디, 암호 전달 후 인증
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(member.getUserid(), member.getUserpw()));

            map.put("result", 1L);
            map.put("token", jwtUtil.generateToken(member.getUserid()));
        } catch (Exception e) {
            // e.printStackTrace();
            map.put("result", 0L);
            map.put("error", "아이디/암호 틀립니다.");
        }
        return map;
    }

    // 127.0.0.1:8080/ROOT/api/member_insert
    @RequestMapping(value = "/member_insert", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> memberInsert(@RequestBody Member member) {
        Map<String, Long> map = new HashMap<>();
        try {
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            member.setUserpw(bcpe.encode(member.getUserpw()));
            mRepository.save(member);
            map.put("result", 1L);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", 0L);
        }
        return map;
    }

    // 127.0.0.1:8080/ROOT/api/member_insert1
    @RequestMapping(value = "/member_insert1", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> memberInsert1(@ModelAttribute Member member) {
        Map<String, Long> map = new HashMap<>();
        try {
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            member.setUserpw(bcpe.encode(member.getUserpw()));
            mRepository.save(member);
            map.put("result", 1L);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", 0L);
        }
        return map;
    }

}