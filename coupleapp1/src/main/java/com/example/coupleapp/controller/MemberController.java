package com.example.coupleapp.controller;

import com.example.coupleapp.security.AuthHolder;
import com.example.coupleapp.security.JwtUtil;
import com.example.coupleapp.dto.LoginRequestDTO;
import com.example.coupleapp.dto.MemberDTO;
import com.example.coupleapp.dto.TokenDTO;
import com.example.coupleapp.service.MemberService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Api(tags = "회원 관련 API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v2/api/members")
public class MemberController {
    private final MemberService memberService;

    @ApiOperation(value = "회원가입")
    @PostMapping("/create")
    public ResponseEntity<String> createMember(
            @RequestBody MemberDTO memberDTO) {
        return ResponseEntity.ok(memberService.createMember(memberDTO));
    }
    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(
            @RequestBody LoginRequestDTO loginRequestDTO, HttpServletResponse httpServletResponse){
        TokenDTO tokenDTO = memberService.login(loginRequestDTO);
        httpServletResponse.setHeader(JwtUtil.ACCESS_TOKEN,tokenDTO.getAccessToken());
        httpServletResponse.setHeader(JwtUtil.ACCESS_TOKEN,tokenDTO.getAccessToken());
        return ResponseEntity.ok().body(tokenDTO);
    }

    @GetMapping
    public ResponseEntity<?> test() {
        return ResponseEntity.ok().body("로그인한 유저의 식별값입니다.  = " + AuthHolder.getMemberId());
    }
}

