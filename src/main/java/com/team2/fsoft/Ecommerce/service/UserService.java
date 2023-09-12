package com.team2.fsoft.Ecommerce.service;

import com.team2.fsoft.Ecommerce.dto.UserDTO;
import com.team2.fsoft.Ecommerce.dto.request.RegisterReq;
import com.team2.fsoft.Ecommerce.entity.User;
import com.team2.fsoft.Ecommerce.repository.UserRepository;

public interface UserService {
    User create(RegisterReq registerReq);

    void changePassword(String email, String oldPassword, String newPassword);

    void updateUserInformation(String email, UserDTO userDTO);

    void deleteUser(String email);

    User findByEmail(String email);

}