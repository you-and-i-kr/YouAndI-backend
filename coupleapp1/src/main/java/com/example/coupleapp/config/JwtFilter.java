package com.example.coupleapp.config;

import com.example.coupleapp.exception.domian.MemberErrorCode;
import com.example.coupleapp.exception.domian.MemberException;
import com.example.coupleapp.service.MemberDetailServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final MemberDetailServiceImpl memberDetailService;
    public JwtFilter(JwtUtil jwtUtil, MemberDetailServiceImpl memberDetailService) {
        this.jwtUtil =jwtUtil;
        this.memberDetailService = memberDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 헤더에서 토큰 가져오기
        String accessToken = jwtUtil.getHeaderToken(request, "Access");
        String refreshToken = jwtUtil.getHeaderToken(request, "Refresh");
        // 토큰 존재 여부 확인
        if(StringUtils.hasText(accessToken)) {
            if(jwtUtil.tokenValidation(accessToken)) {
                Claims info = jwtUtil.getUserInfoFromToken(accessToken);
                setAuthentication(info.getSubject());
            } else if(StringUtils.hasText(refreshToken)) {
                boolean isRefreshToken = jwtUtil.refreshTokenValidation(refreshToken);
                if (isRefreshToken) {
                    Claims info = jwtUtil.getUserInfoFromToken(refreshToken);
                    String userName = (String) info.get(JwtUtil.AUTHORIZATION_KEY);

                    String newAccessToken = jwtUtil.createToken(info.getSubject(),userName,"Access");
                    response.setHeader("ACCESS_TOKEN",newAccessToken);
                    setAuthentication(jwtUtil.getUserInfoFromToken(newAccessToken.substring(7)).getSubject());
                } else {
                    jwtExceptionHandler(response, "RefreshToken Expired", HttpStatus.BAD_REQUEST);
                    return;
                }
            }
        }
        filterChain.doFilter(request,response);
    }


    // JWT 예외처리
    private void jwtExceptionHandler(HttpServletResponse response, String msg, HttpStatus status) {
        response.setStatus(status.value());
        response.setContentType("application/json");
        try{
            String json = new ObjectMapper().writeValueAsString(new MemberException(MemberErrorCode.AUTHENTICATION_FAIL));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


    //인증 처리
    private void setAuthentication(String email) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(email);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    private Authentication createAuthentication(String email) {
        UserDetails userDetails = memberDetailService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
