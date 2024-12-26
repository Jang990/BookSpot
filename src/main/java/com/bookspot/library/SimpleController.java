package com.bookspot.library;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class SimpleController {
    @GetMapping("/libraries/stock/search-setting")
    public String index() {
        return "libraries/stock/search-setting";
    }
}
