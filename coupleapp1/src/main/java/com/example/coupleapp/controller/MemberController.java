package com.example.coupleapp.controller;

import com.example.coupleapp.config.JwtUtil;
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
    public String test() {
        return "test";
    }
}
//    @Autowired
//    public MemberController(MemberService memberService) {
//        this.memberService = memberService;
//    }
//
//
//
//    @GetMapping("/{id}")
//    @ApiOperation(value = "Get member by ID", response = MemberDTO.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successfully retrieved member by ID"),
//            @ApiResponse(code = 404, message = "Member not found")
//    })
//    public MemberDTO getMember(
//            @ApiParam(value = "ID of the member", required = true) @PathVariable Long id) {
//        return memberService.getMemberById(id);
//    }
//
//    @PostMapping
//    @ApiOperation(value = "Create new member", response = MemberDTO.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 201, message = "Member created successfully"),
//            @ApiResponse(code = 400, message = "Invalid input")
//    })
//
//
//    @PutMapping("/{id}")
//    @ApiOperation(value = "Update member by ID", response = MemberDTO.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successfully updated member by ID"),
//            @ApiResponse(code = 404, message = "Member not found"),
//            @ApiResponse(code = 400, message = "Invalid input")
//    })
//    public MemberDTO updateMember(
//            @ApiParam(value = "ID of the member", required = true) @PathVariable Long id,
//            @ApiParam(value = "Updated member data", required = true) @RequestBody MemberDTO memberDTO) {
//        return memberService.updateMember(id, memberDTO);
//    }
//
//    @DeleteMapping("/{id}")
//    @ApiOperation(value = "Delete member by ID")
//    @ApiResponses(value = {
//            @ApiResponse(code = 204, message = "Member deleted successfully"),
//            @ApiResponse(code = 404, message = "Member not found")
//    })
//    public void deleteMember(
//            @ApiParam(value = "ID of the member", required = true) @PathVariable Long id) {
//        memberService.deleteMember(id);
//    }
//
//    @PostMapping("/login")
//    @ApiOperation(value = "Login")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Login successful"),
//            @ApiResponse(code = 401, message = "Login failed")
//    })
//    public ResponseEntity<String> login(
//            @ApiParam(value = "Login request data", required = true) @RequestBody LoginRequestDTO loginRequest) {
//        ResponseEntity<String> response = memberService.login(loginRequest);
//        return response;
//    }
//
//    @PostMapping("/logout")
//    @ApiOperation(value = "Logout")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Logout successful")
//    })
//    public ResponseEntity<String> logout(HttpSession session) {
//        session.invalidate();
//        return ResponseEntity.ok("Logout successful");
//    }

