package com.team2.fsoft.Ecommerce.service;

import com.team2.fsoft.Ecommerce.dto.UserDTO;
import com.team2.fsoft.Ecommerce.dto.request.ApiParameter;
import com.team2.fsoft.Ecommerce.dto.request.RegisterReq;
import com.team2.fsoft.Ecommerce.dto.response.UserRes;
import com.team2.fsoft.Ecommerce.entity.User;
import com.team2.fsoft.Ecommerce.repository.UserRepository;

import java.util.List;

public interface UserService {
    User create(RegisterReq registerReq);

    void changePassword(String email, String oldPassword, String newPassword);

    void updateUserInformation(RegisterReq registerReq);

    void deleteUser(long id);

    User findByEmail(String email);


    List<UserRes> getLists(ApiParameter apiParameter);
}