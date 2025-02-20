package org.gestionstock.stock.Security.Component;

import java.util.Optional;

import org.gestionstock.stock.Security.Service.BlackListService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class LogoutHandlerJwt implements LogoutHandler{
    private final BlackListService blackListService;
    
    @Override
    public void logout(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
        ) {
        if(authentication != null){
            log.info("Request to logout user: {}", authentication.getPrincipal().toString());
        }
        Optional.ofNullable(request.getHeader("Authorization"))
            .filter(header -> header.startsWith("Bearer "))
            .map(header -> header.substring(7))
            .ifPresent(token -> {
                blackListService.addTokenToBlackList(token);
            });
    }
    
}
