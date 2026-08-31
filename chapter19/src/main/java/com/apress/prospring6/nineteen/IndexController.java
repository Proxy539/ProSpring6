package com.apress.prospring6.nineteen;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping(path = "index")
    public String auth(Model model, HttpServletRequest request) {
        var requestUrl = request.getRequestURL().toString();

        var webSocketAddress = requestUrl.replace("http", "ws").replace("index",
                "echoHandler");

        model.addAttribute("webSocket", webSocketAddress);
        return "index";
    }
}
