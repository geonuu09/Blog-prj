package com.project.blog.service;

import com.project.blog.domain.User;
import com.project.blog.dto.AddUserRequest;
import com.project.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service


// 서비스
// 서비스 로직을 구현할 떄는 Entity에 직접 접근하기 보다는, 해당 로직에 맞는 dto를 새로 구성하여 그곳에 접근
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserRequest dto) {
        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .build()).getId();
    }
}