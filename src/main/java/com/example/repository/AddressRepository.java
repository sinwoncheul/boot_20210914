package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import com.example.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

    // 전달되는 문자열의 주소가 일치하는 것 1개를 리턴
    // Address findByAddr(String addr);

    // Address findByNo(Long no);

    // Address findByRegdate(Date regdate);

    // 회원아이디가 일치하는 주소만 가져오기
    List<Address> findByMember_Userid(String userid);

    List<Address> findByMember_UseridOrderByNoAsc(String userid);

    List<Address> findByMember_UseridOrderByNoDesc(String userid);

    // 주소번호가 오면 삭제
    @Transactional
    void deleteByNo(Long no);

    // 주소번호와 주소정보가 둘다 일치 할때 삭제
    @Transactional
    void deleteByNoAndAddr(Long no, String addr);

    // 주소번호와 회원의 아이디가 일치 할때 삭제
    @Transactional
    void deleteByNoAndMember_Userid(Long no, String userid);
}
