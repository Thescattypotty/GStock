package org.gestionstock.stock.Security.Component;

import java.io.IOException;
import java.util.Optional;

import org.gestionstock.stock.Security.Service.BlackListService;
import org.gestionstock.stock.Security.Service.UserDetailsServiceImpl;
import org.gestionstock.stock.Util.JwtUtil;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OncePerRequestFilterJwt extends OncePerRequestFilter{
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final BlackListService blackListService;

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
        ) throws ServletException, IOException {

        Optional.ofNullable(request.getHeader("Authorization"))
            .filter(header -> header.startsWith("Bearer "))
            .map(header -> header.substring(7))
            .ifPresentOrElse(token -> {
                if(blackListService.isTokenBlackListed(token) || !jwtUtil.validateToken(token)){
                    safeDoFilter(filterChain, request, response);
                    return;
                }
                String username = jwtUtil.getSubjectFromToken(token);
                if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                    UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                    );
                    authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
                safeDoFilter(filterChain, request, response);
            }, () -> {
                safeDoFilter(filterChain, request, response);
            });
    }

    private void safeDoFilter(
            FilterChain filterChain,
            HttpServletRequest request,
            HttpServletResponse response) {
        try {
            filterChain.doFilter(request, response);
        } catch (IOException | ServletException e) {
            throw new RuntimeException("Exception dans le filtre", e);
        }
    }
}
