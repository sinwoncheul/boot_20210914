package com.example.security;

import java.util.Collection;
import java.util.Optional;

import com.example.entity.Member;
import com.example.repository.MemberRepsoitory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// security를 제공해주는
// UserDetailService인터페이스를 통해 구현되는 메소드

@Service
public class MemberDetailsService implements UserDetailsService {
	@Autowired
	private MemberRepsoitory mRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println(username);

		// 이쪽으로 아이디가 넘어오니
		// 아이디를 이용해서 정보를 가져와서 User객체 타입으로 리턴
		Optional<Member> obj = mRepository.findById(username);
		Member member = obj.get();

		// String의 권한을 Collection<GrantedAuthority로 변환
		String[] userRoles = { member.getUserrole() };
		Collection<GrantedAuthority> roles = AuthorityUtils.createAuthorityList(userRoles);

		// 아이디, 암호, 권한
		User user = new User(member.getUserid(), member.getUserpw(), roles);
		return user;
	}
}