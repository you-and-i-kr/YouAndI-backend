package com.example.coupleapp.controller;

import com.example.coupleapp.dto.LoginRequest;
import com.example.coupleapp.dto.MemberDTO;
import com.example.coupleapp.service.MemberService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/members")
@Api(value = "Member Controller", description = "Operations pertaining to members")
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get member by ID", response = MemberDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved member by ID"),
            @ApiResponse(code = 404, message = "Member not found")
    })
    public MemberDTO getMember(
            @ApiParam(value = "ID of the member", required = true) @PathVariable Long id) {
        return memberService.getMemberById(id);
    }

    @PostMapping
    @ApiOperation(value = "Create new member", response = MemberDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Member created successfully"),
            @ApiResponse(code = 400, message = "Invalid input")
    })
    public MemberDTO createMember(
            @ApiParam(value = "Member data", required = true) @RequestBody MemberDTO memberDTO) {
        return memberService.createMember(memberDTO);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update member by ID", response = MemberDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated member by ID"),
            @ApiResponse(code = 404, message = "Member not found"),
            @ApiResponse(code = 400, message = "Invalid input")
    })
    public MemberDTO updateMember(
            @ApiParam(value = "ID of the member", required = true) @PathVariable Long id,
            @ApiParam(value = "Updated member data", required = true) @RequestBody MemberDTO memberDTO) {
        return memberService.updateMember(id, memberDTO);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete member by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Member deleted successfully"),
            @ApiResponse(code = 404, message = "Member not found")
    })
    public void deleteMember(
            @ApiParam(value = "ID of the member", required = true) @PathVariable Long id) {
        memberService.deleteMember(id);
    }

    @PostMapping("/login")
    @ApiOperation(value = "Login")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Login successful"),
            @ApiResponse(code = 401, message = "Login failed")
    })
    public ResponseEntity<String> login(
            @ApiParam(value = "Login request data", required = true) @RequestBody LoginRequest loginRequest) {
        ResponseEntity<String> response = memberService.login(loginRequest);
        return response;
    }

    @PostMapping("/logout")
    @ApiOperation(value = "Logout")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Logout successful")
    })
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logout successful");
    }
}
