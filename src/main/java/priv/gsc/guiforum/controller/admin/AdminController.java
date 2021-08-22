package priv.gsc.guiforum.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import priv.gsc.guiforum.annotation.LoginRequired;

@Controller
public class AdminController {

    @LoginRequired
    @GetMapping("/admin")
    public String getAdminPage() {
        return "/admin/index";
    }
}
