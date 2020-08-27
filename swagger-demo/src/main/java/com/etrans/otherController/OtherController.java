package com.etrans.otherController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OtherController {

    @GetMapping("/other")
    //@RequestMapping("/hello")
    public String other() {
        return "other";
    }
}
