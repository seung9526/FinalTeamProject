package com.springboot.Teamproject.service;

import com.springboot.Teamproject.UserRole;
import com.springboot.Teamproject.entity.User;
import com.springboot.Teamproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    //스프링 시큐리티와 연동되는 함수로, 로그인 할 때 아이디와 비밀번호를 비교하여 로그인해줌
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> _user = this.userRepository.findById(username);

        if(_user.isEmpty())
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다");

        User user = _user.get();
        List<GrantedAuthority> authorities = new ArrayList<>();

        if("admin".equals(username))
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        else
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));

        return new org.springframework.security.core.userdetails.User(user.getId(),user.getPassword(),authorities);
    }
}
