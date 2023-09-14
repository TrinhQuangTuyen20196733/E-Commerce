package com.team2.fsoft.Ecommerce.service.impl;

import com.team2.fsoft.Ecommerce.dto.UserDTO;
import com.team2.fsoft.Ecommerce.dto.request.ChangePasswordRequest;
import com.team2.fsoft.Ecommerce.dto.request.RegisterReq;
import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.entity.User;
import com.team2.fsoft.Ecommerce.mapper.impl.UserMapper;
import com.team2.fsoft.Ecommerce.repository.UserRepository;
import com.team2.fsoft.Ecommerce.security.UserDetail;
import com.team2.fsoft.Ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User create(RegisterReq registerReq) {
        return userRepository.save(userMapper.toEntity(registerReq));
    }

    @Override
    public void updateUserInformation(@Valid RegisterReq registerReq) {
      userRepository.save(userMapper.toEntity(registerReq));

    }

    @Override
    public void deleteUser(long id) {

            userRepository.deleteById(id);
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return user.get();
        }
        throw new RuntimeException("Email doesn't exist");
    }

    @Override
    public MessagesResponse changePassword(ChangePasswordRequest changePasswordRequest) {
        MessagesResponse ms = new MessagesResponse();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = (UserDetail) authentication.getPrincipal();
        Long userId = user.getId();

        var userAccountOptional = userRepository.findById(userId);
        if (userAccountOptional.isPresent()) {
            var userAccount = userAccountOptional.get();
            if (passwordEncoder.matches(changePasswordRequest.oldPassword, userAccount.getPassword())) {
                String password = passwordEncoder.encode(changePasswordRequest.newPassword);
                userAccount.setPassword(password);
                userRepository.save(userAccount);
            } else {
                ms.code = 400;
                ms.message="Mật khẩu hiện tại bạn nhập không đúng. Vui lòng nhập lại!";
            }
        }
        return  ms;

    }
}
