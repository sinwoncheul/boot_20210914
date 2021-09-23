package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration // 환경설정 파일
@EnableWebSecurity // security를 적용
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // DB연동을 위해 만든 sevice
    @Autowired
    private MemberDetailsService mService;

    // 환경설정 파일에서 객체 만들기(회원가입시 암호화 방법)
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 기존에 정의되어 있던 메소드를 지우고 새로 작성
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(mService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 등급
        http.authorizeRequests().antMatchers("/admin", "/admin/*").hasAuthority("ADMIN")
                .antMatchers("/seller", "/seller/*").hasAnyAuthority("ADMIN", "SELLER").anyRequest().permitAll().and()

                // 로그인페이지
                // 127.0.0.1:8080/ROOT/member/login
                // <form action="/member/login">
                // <input type="text" name="userid"
                // <input type="text" name="userpw"
                .formLogin().loginPage("/member/login").loginProcessingUrl("/member/login").usernameParameter("userid")
                .passwordParameter("userpw").permitAll().successHandler(new MemberLoginSuccessHandler()).and()

                // 로그아웃 페이지
                .logout().logoutUrl("/member/logout").logoutSuccessHandler(new MemberLogoutSuccessHandler())
                .invalidateHttpSession(true).clearAuthentication(true).permitAll().and()

                // 접근 불가 시 보여질 페이지 주소 설정
                .exceptionHandling().accessDeniedPage("/page403").and();
    }

}
