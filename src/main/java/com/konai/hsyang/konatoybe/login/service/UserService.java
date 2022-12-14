package com.konai.hsyang.konatoybe.login.service;

import com.konai.hsyang.konatoybe.exceptions.NoUserFoundException;
import com.konai.hsyang.konatoybe.login.domain.User;
import com.konai.hsyang.konatoybe.login.dto.UserJoinRequestDto;
import com.konai.hsyang.konatoybe.login.dto.UserUpdateRequestDto;
import com.konai.hsyang.konatoybe.login.repository.UserRepository;
import com.konai.hsyang.konatoybe.login.roles.Role;
import com.konai.hsyang.konatoybe.posts.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PostsRepository postsRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public Long join(UserJoinRequestDto requestDto){

        String rawPassword = requestDto.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        requestDto.setEncPassword(encPassword);
        requestDto.setRole(Role.USER);
        return userRepository.save(requestDto.toEntity()).getUserID();
    }

    public User findById(Long userID){

        return userRepository.findById(userID).orElseThrow(() -> new NoUserFoundException());
    }

    public User findByUsername(String name){

        return userRepository.findByUsername(name).orElseThrow(() -> new NoUserFoundException());
    }

    public int validateID(String name){

        return userRepository.findByUsername(name).orElse(null) == null ? 1 : -1;
    }

    public int validateNickname(String name){

        return userRepository.findByNickname(name).orElse(null) == null ? 1 : -1;
    }

    public boolean validatePassword(User user, String inputPassword){ // ????????? ??????????????? ??????????????? ???????????? ??????????????? ??????????????? ??????

        return bCryptPasswordEncoder.matches(inputPassword, user.getPassword());
    }

    @Transactional
    public Long updateNickname(Long id, UserUpdateRequestDto requestDto){

        User user = userRepository.findById(id).orElseThrow(() -> new NoUserFoundException());
        if(!validatePassword(user, requestDto.getOldPassword())) // ?????? ??????????????? ???????????? ?????????
            return -1L; // -1 ??????
        user.updateNickname(requestDto);
        // ?????? ????????????
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), requestDto.getOldPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return id;
    }

    @Transactional
    public Long updatePassword(Long id, UserUpdateRequestDto requestDto){

        User user = userRepository.findById(id).orElseThrow(() -> new NoUserFoundException());
        String rawPassword = requestDto.getNewPassword(); // ????????? ????????????
        String encPassword = bCryptPasswordEncoder.encode(rawPassword); // ????????? ???????????? ?????????
        if(validatePassword(user, requestDto.getOldPassword())) { // ?????? ??????????????? ????????????
            requestDto.setEncPassword(encPassword); // ???????????? ???????????? ??????
            user.updatePassword(requestDto);
            return id;
        }else
            return -1L;
    }

    @Transactional
    public void delete(Long id){

        postsRepository.deleteAllByUser(id); // ????????? ???????????? ???????????? ?????? ??????
        userRepository.delete(userRepository.findById(id).orElseThrow(() -> new NoUserFoundException()));
    }
}
