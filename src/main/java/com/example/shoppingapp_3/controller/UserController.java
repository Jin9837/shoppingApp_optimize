package com.example.shoppingapp_3.controller;

import com.example.shoppingapp_3.domain.User;
import com.example.shoppingapp_3.common.ApiRestResponse;
import com.example.shoppingapp_3.exception.CustomizedException;
import com.example.shoppingapp_3.exception.CustomizedExceptionEnum;
import com.example.shoppingapp_3.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    final UserService userService;

    @PostMapping("/test")
    public ApiRestResponse test()
    {
        return ApiRestResponse.success();
    }

    @PostMapping("/register")
    public ApiRestResponse register(@RequestParam("username") String userName, @RequestParam("password") String password,
                                    @RequestParam("email") String email) throws CustomizedException {
        // check the username exist or not
        if (!userService.isValidUserName(userName))
        {
            throw new CustomizedException(CustomizedExceptionEnum.NAME_EXISTED);
        }

        // check the email exist or not
        if (!userService.isValidEmail(email))
        {
            throw new CustomizedException(CustomizedExceptionEnum.EMAIL_EXISTED);
        }

        userService.register(userName, password, email);
        return ApiRestResponse.success();
    }


    @PostMapping("/login")
    public ApiRestResponse login(@RequestParam("username") String userName, @RequestParam("password") String password) {
        if (userName.isEmpty())
        {
            return ApiRestResponse.error(CustomizedExceptionEnum.NEED_USER_NAME);
        }
        if (password.isEmpty())
        {
            return ApiRestResponse.error(CustomizedExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.getUser(userName, password);
        if (user == null)
        {
            return ApiRestResponse.error(CustomizedExceptionEnum.WRONG_PASSWORD);
        }
        // for security concerns, remove password from memory before returning a response, does not change anything in database
        user.setPassword(null);
        return ApiRestResponse.success();
    }
}
