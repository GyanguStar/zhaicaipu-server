package com.github.gyangustar.zhaicaipuserver

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    @RequestMapping("/ping")
    fun pingController() : String {
        return "pong"
    }

    @GetMapping("/user")
    fun user(@AuthenticationPrincipal principal: Jwt): Map<String, Any>{
        return principal.claims
    }
}
