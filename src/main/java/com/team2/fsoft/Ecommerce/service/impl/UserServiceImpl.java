package com.team2.fsoft.Ecommerce.service.impl;

import com.team2.fsoft.Ecommerce.dto.UserDTO;
import com.team2.fsoft.Ecommerce.dto.request.RegisterReq;
import com.team2.fsoft.Ecommerce.entity.User;
import com.team2.fsoft.Ecommerce.mapper.impl.UserMapper;
import com.team2.fsoft.Ecommerce.repository.UserRepository;
import com.team2.fsoft.Ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @Override
    public User create(RegisterReq registerReq) {
        return userRepository.save(userMapper.toEntity(registerReq));
    }

    @Override
    public void updateUserInformation(String email, UserDTO userDTO) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            user.get().setName(userDTO.getName());
            user.get().setAddress(userDTO.getAddress());
            user.get().setAge(userDTO.getAge());
            user.get().setGender(userDTO.getGender());
        }

    }

    @Override
    public void deleteUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            userRepository.delete(user.get());
        }
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
    public void changePassword(String email, String oldPassword, String newPassword) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            if (user.get().getPassword().equals(oldPassword)) {
                user.get().setPassword(newPassword);
            }
        }
    }
}
