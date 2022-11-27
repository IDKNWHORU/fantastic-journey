package org.fantastic.journey.common.counsel;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CounselController {

    @GetMapping("/counsels")
    public List<Object> getCounsels() {
        return new ArrayList<>();
    }
}
