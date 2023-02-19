package com.freestrokes.auth.service;

import com.freestrokes.auth.domain.User;
import com.freestrokes.auth.dto.UserDetailsDto;
import com.freestrokes.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// TODO: UserDetailsService
// 사용자 조회를 위해 UserDetailsService 인터페이스를 구현

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * 사용자 조회
     *
     * @param email the username identifying the user whose data is required.
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // 사용자 조회
        User findUser = userRepository
            .findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User [" + email + "] not found."));

        // 사용자 권한 설정
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(findUser.getRole().toString()));

        return UserDetailsDto.builder()
            .username(findUser.getName())
            .password(findUser.getPassword())
            .authorities(authorities)
            // TODO
//            .authorities(getAuthorities(findUser.getRole().getAuthority()))
            .isAccountNonExpired(true)
            .isAccountNonLocked(true)
            .isCredentialsNonExpired(true)
            .isEnabled(true)
            .build();

    }

}
