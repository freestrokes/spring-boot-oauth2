package com.freestrokes.auth.service;

import com.freestrokes.constants.AuthConstants;
import com.freestrokes.auth.domain.User;
import com.freestrokes.auth.dto.OAuth2UserDto;
import com.freestrokes.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // OAuth2 서비스 registration id (구글, 카카오, 네이버)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // OAuth2 로그인 진행 시 키가 되는 필드 값(PK)
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        Map<String, Object> oAuth2UserAttributes = oAuth2User.getAttributes();

        httpSession.setAttribute(
            "user",
            OAuth2UserDto.RequestDto.builder()
                .userNameAttributeName(userNameAttributeName)
                .name(oAuth2UserAttributes.get("name").toString())
                .email(oAuth2UserAttributes.get("email").toString())
                .picture(oAuth2UserAttributes.get("picture").toString())
                .attributes(oAuth2UserAttributes)
                .build()
        );

        // TODO
        // OAuth2UserService
//        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
//        User user = saveOrUpdate(attributes);
//        httpSession.setAttribute("user", new SessionUser(user)); // SessionUser (직렬화된 dto 클래스 사용)

        // 유저 생성 및 수정
        User findUser = userRepository.findByEmail(oAuth2UserAttributes.get("email").toString())
            .map(user -> {
                return user.updateUser(
                    oAuth2UserAttributes.get("name").toString(),
                    oAuth2UserAttributes.get("email").toString(),
                    oAuth2UserAttributes.get("picture").toString(),
                    AuthConstants.Role.valueOf(user.getRole().toString())
                );
            })
            .orElse(
                User.builder()
                    .name(oAuth2UserAttributes.get("name").toString())
                    .email(oAuth2UserAttributes.get("email").toString())
                    .picture(oAuth2UserAttributes.get("picture").toString())
                    .role(AuthConstants.Role.GUEST)
                    .build()
            );

        return new DefaultOAuth2User(
            Collections.singleton(new SimpleGrantedAuthority(findUser.getRole().toString())),
            oAuth2UserAttributes,
            userNameAttributeName
        );

    }

}
