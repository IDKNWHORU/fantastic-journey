package org.fantastic.journey.common.users;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserBean userBean;

    public UserController(UserBean userBean) {
        this.userBean = userBean;
    }

    @GetMapping("/user")
    public String user() {
        return "user mail is " + userBean.findUserMail(1);
    }
}
