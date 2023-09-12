package com.team2.fsoft.Ecommerce.controller;


import com.team2.fsoft.Ecommerce.dto.UserDTO;
import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/updateUser")
    public MessagesResponse updateUserInfo(@RequestParam String email, @RequestBody @Valid UserDTO userDTO) {
        MessagesResponse messagesResponse = new MessagesResponse();
        userService.updateUserInformation(email, userDTO);
        messagesResponse.message = "Success";
        return messagesResponse;
    }

    @DeleteMapping("/deleteUser")
    public MessagesResponse deleteUser(@RequestParam @Valid String email) {
        MessagesResponse messagesResponse = new MessagesResponse();
        userService.deleteUser(email);
        messagesResponse.message = "Success";
        return messagesResponse;
    }

    @PostMapping("/changePassword")
    public MessagesResponse changePassword(@RequestParam String email, @RequestParam String oldPassword, @RequestParam String newPassword) {
        MessagesResponse messagesResponse = new MessagesResponse();
        userService.changePassword(email, oldPassword, newPassword);
        messagesResponse.message = "Success";
        return messagesResponse;
    }
}
