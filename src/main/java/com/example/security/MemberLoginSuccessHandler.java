package com.example.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class MemberLoginSuccessHandler implements AuthenticationSuccessHandler {

    // 로그인 성공시 자동으로 호출되는 메소드
    // (req, res, 로그인사용자세션정보)
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        // 1. 세션에서 사용자정보 얻기
        User user = (User) authentication.getPrincipal();
        if (user != null) {

            // List<GrantedAuthority>
            Collection<GrantedAuthority> roles = user.getAuthorities();
            // 반복 횟수 : 1회 회전
            for (GrantedAuthority role : roles) {
                if (role.getAuthority().equals("ADMIN")) {
                    response.sendRedirect(request.getContextPath() + "/admin/home");
                } else if (role.getAuthority().equals("SELLER")) {
                    response.sendRedirect(request.getContextPath() + "/seller/home");
                } else {
                    response.sendRedirect(request.getContextPath() + "/");
                }
            }
        }

    }

}
