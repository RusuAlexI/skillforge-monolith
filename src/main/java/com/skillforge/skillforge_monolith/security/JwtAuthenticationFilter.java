package com.skillforge.skillforge_monolith.security;

import com.skillforge.skillforge_monolith.entity.User;
import com.skillforge.skillforge_monolith.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private  JwtUtil jwtUtil;
    private  UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String header = request.getHeader("Authorization");

            if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
                String token = header.substring(7);

                if (StringUtils.hasText(token) && jwtUtil.validateToken(token)) {
                    Long userId = Long.valueOf(jwtUtil.getUserIdFromToken(token));
                    if (userId != null) {
                        userRepository.findById(userId).ifPresent(user -> {
                            UsernamePasswordAuthenticationToken auth =
                                    new UsernamePasswordAuthenticationToken(user, null, List.of());
                            SecurityContextHolder.getContext().setAuthentication(auth);
                            log.debug("Authentication set for user: {}", user.getEmail());
                        });
                    }
                }
            }
        } catch (Exception e) {
            // Log the error but don't let it break the filter chain
            log.error("Error in JWT authentication filter: {}", e.getMessage(), e);
        }

        filterChain.doFilter(request, response);
    }
}