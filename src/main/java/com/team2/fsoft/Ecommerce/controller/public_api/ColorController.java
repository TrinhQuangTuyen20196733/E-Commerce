package com.team2.fsoft.Ecommerce.controller.public_api;

import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.service.CategoryService;
import com.team2.fsoft.Ecommerce.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/color")
public class ColorController {
    @Autowired
    ColorService colorService;

    @GetMapping
    public MessagesResponse getAll() {
        MessagesResponse ms = new MessagesResponse();
        try {
            ms.data = colorService.getAll();
        }
        catch (Exception e) {
            ms.code = 500;
            ms.setMessage("Intenal Server Error!");
        }
        return  ms;
    }
}
