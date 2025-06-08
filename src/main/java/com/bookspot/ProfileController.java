package com.bookspot;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProfileController {
    private static final List<String> realProfiles = List.of("real1", "real2");
    private final Environment env;

    @GetMapping("profile")
    public String profile() {
        List<String> profiles = Arrays.asList(env.getActiveProfiles());

        return profiles.stream()
                .filter(realProfiles::contains)
                .findAny()
                .orElseGet(() -> profiles.isEmpty() ? "default" : profiles.getFirst());
    }
}
