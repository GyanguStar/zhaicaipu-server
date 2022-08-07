package com.github.gyangustar.zhaicaipuserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity.http
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator
import org.springframework.security.oauth2.core.OAuth2TokenValidator
import org.springframework.security.oauth2.jwt.*
import org.springframework.security.oauth2.jwt.JwtClaimNames.AUD
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*


@SpringBootApplication
class ZhaicaipuServerApplication

fun main(args: Array<String>) {
    runApplication<ZhaicaipuServerApplication>(*args)
}
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

@EnableWebSecurity
class SecurityConfiguration {
    @Bean
    open fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http .
            authorizeRequests {
                it
                    .antMatchers("/ping").permitAll()
                    .anyRequest().authenticated()
            }.
            oauth2ResourceServer {
                it.jwt { }
            }

        return http.build()
    }
    @Bean
    fun jwtDecoder(): JwtDecoder {
        val jwtDecoder: NimbusJwtDecoder = JwtDecoders.fromIssuerLocation("https://account.kuroneko.xyz:8443") as NimbusJwtDecoder
        val audienceValidator = audienceValidator()
        val withIssuer: OAuth2TokenValidator<Jwt> = JwtValidators.createDefaultWithIssuer("https://account.kuroneko.xyz:8443")
        val withAudience: OAuth2TokenValidator<Jwt> = DelegatingOAuth2TokenValidator(withIssuer, audienceValidator)
        jwtDecoder.setJwtValidator(withAudience)
        return jwtDecoder
    }
    fun audienceValidator(): OAuth2TokenValidator<Jwt?> {
        return JwtClaimValidator<List<String>>(AUD) { aud -> aud.contains("kuancaipu") }
    }
}