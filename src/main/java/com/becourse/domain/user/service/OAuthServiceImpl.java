package com.becourse.domain.user.service;

import com.becourse.domain.user.domain.UserDetailsImpl;
import com.becourse.domain.user.domain.UserEntity;
import com.becourse.domain.user.domain.repository.UserRepository;
import com.becourse.domain.user.dto.CreateOAuthUserRequest;
import com.becourse.domain.user.dto.DAuthBaseResponse;
import com.becourse.domain.user.dto.DAuthResourceResponse;
import com.becourse.domain.user.dto.GoogleResourceResponse;
import com.becourse.domain.user.exception.UserException;
import com.becourse.global.auth.JwtProvider;
import com.becourse.global.common.BaseResponse;
import com.becourse.global.properties.DAuthProperties;
import com.becourse.global.properties.GoogleProperties;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuthServiceImpl implements OAuthService {
    private final DAuthProperties dAuthProperties;
    private final GoogleProperties googleProperties;
    private final RestTemplate restTemplate = new RestTemplate();
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public BaseResponse login(String code, String registrationId) {
        String accessToken = getAccessToken(code, registrationId);
        Object resource = getResource(accessToken, registrationId);
        Optional<UserEntity> user = loadUser(resource, registrationId);
        if (user.isEmpty()) {
            return new BaseResponse(HttpStatus.OK, "최초 로그인", resource);
        } else {
            UserDetailsImpl userDetails = new UserDetailsImpl(user.get());
            return new BaseResponse(HttpStatus.OK, "로그인 성공", jwtProvider.generateToken(userDetails));
        }
    }

    @Override
    public BaseResponse join(CreateOAuthUserRequest createOAuthUserRequest) {
        userRepository.save(UserEntity.builder()
                .oAuthId(createOAuthUserRequest.oAuthId())
                .oAuthProvider(createOAuthUserRequest.oAuthProvider())
                .email(createOAuthUserRequest.email())
                .userName(createOAuthUserRequest.userName())
                .build());

        return new BaseResponse(HttpStatus.OK, "회원가입 성공");
    }

    private Optional<UserEntity> loadUser(Object data, String registrationId) {
        switch (registrationId) {
            case "google":
                GoogleResourceResponse googleUser = (GoogleResourceResponse) data;
                return userRepository.findByOAuthIdContainingAndOAuthProviderContaining(googleUser.getId(), registrationId);
            case "dauth":
                DAuthResourceResponse dAuthUser = (DAuthResourceResponse) data;
                return userRepository.findByOAuthIdContainingAndOAuthProviderContaining(dAuthUser.getUniqueId(), registrationId);
            default:
                throw UserException.badRequestException();
        }
    }

    private String getAccessToken(String code, String registrationId) {
        switch (registrationId) {
            case "google":
                return getGoogleAccessToken(code);
            case "dauth":
                return getDAuthAccessToken(code);
            default:
                throw UserException.badRequestException();
        }
    }

    private Object getResource(String token, String registrationId) {
        switch (registrationId) {
            case "google":
                return getGoogleResource(token);
            case "dauth":
                return getDAuthResource(token);
            default:
                throw UserException.badRequestException();
        }
    }

    private GoogleResourceResponse getGoogleResource(String token) {
        String resourceUri = googleProperties.getResourceUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<GoogleResourceResponse> http = new HttpEntity<>(headers);

        return restTemplate.exchange(resourceUri, HttpMethod.GET, http, GoogleResourceResponse.class).getBody();
    }

    private DAuthResourceResponse getDAuthResource(String token) {
        String resourceUri = dAuthProperties.getResourceUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<DAuthBaseResponse> http = new HttpEntity<>(headers);
        return restTemplate.exchange(resourceUri, HttpMethod.GET, http, DAuthBaseResponse.class).getBody().getData();
    }

    private String getGoogleAccessToken(String code) {
        String clientId = googleProperties.getClientId();
        String clientSecret = googleProperties.getSecret();
        String redirectUri = googleProperties.getRedirectUri();
        String tokenUri = googleProperties.getTokenUri();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity entity = new HttpEntity(params, headers);

        ResponseEntity<JsonNode> responseNode = restTemplate.exchange(tokenUri, HttpMethod.POST, entity, JsonNode.class);
        JsonNode accessTokenNode = responseNode.getBody();
        return accessTokenNode.get("access_token").asText();
    }

    private String getDAuthAccessToken(String code) {
        String serviceId = dAuthProperties.getServiceId();
        String serviceSecret = dAuthProperties.getServiceSecret();
        String tokenUri = dAuthProperties.getTokenUri();

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", code);
        body.add("client_id", serviceId);
        body.add("client_secret", serviceSecret);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity entity = new HttpEntity<>(body, headers);

        ResponseEntity<JsonNode> responseNode = restTemplate.exchange(tokenUri, HttpMethod.POST, entity, JsonNode.class);
        JsonNode accessTokenNode = responseNode.getBody();
        return accessTokenNode.get("access_token").asText();
    }
}
