package com.dev.issuebook.msclient;

import com.dev.issuebook.dto.UserInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("auth-service")
public interface AuthClient {

    @GetMapping("/authenticate/by-username/{username}")
    UserInfoResponse getUserByUsername(
            @PathVariable String username
    );
}

