package com.kimtr.web.security_config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kimtr.web.repository.IF_MemberDao;
import com.kimtr.web.vo.MemberVO;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {
	
	//@Autowired
	private final IF_MemberDao memberdao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		try {
			System.out.println("DB값 가져오기"+username);
			MemberVO member = memberdao.selectOne(username);
			System.out.println(member +"님 로그인 시도");
			return User.builder().username(member.getId()).password(member.getPass()).roles(member.getRole()).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
