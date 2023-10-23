package com.example.coupleapp.controller;

import com.example.coupleapp.dto.FollowDTO;
import com.example.coupleapp.service.FollowService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follows")
@Api(value = "Follow Controller", description = "Operations pertaining to follows")
public class FollowController {
    private final FollowService followService;

    @Autowired
    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a follow by ID", response = FollowDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved follow by ID"),
            @ApiResponse(code = 404, message = "Follow not found")
    })
    public ResponseEntity<FollowDTO> getFollow(
            @ApiParam(value = "ID of the follow", required = true) @PathVariable Long id) {
        FollowDTO followDTO = followService.getFollowById(id);
        if (followDTO != null) {
            return ResponseEntity.ok(followDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ApiOperation(value = "Create a new follow", response = FollowDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Follow created successfully"),
            @ApiResponse(code = 400, message = "Invalid input")
    })
    public ResponseEntity<FollowDTO> createFollow(
            @ApiParam(value = "Follow data", required = true) @RequestBody FollowDTO followDTO) {
        FollowDTO createdFollowDTO = followService.createFollow(followDTO);
        return ResponseEntity.ok(createdFollowDTO);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update a follow by ID", response = FollowDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated follow by ID"),
            @ApiResponse(code = 404, message = "Follow not found"),
            @ApiResponse(code = 400, message = "Invalid input")
    })
    public ResponseEntity<FollowDTO> updateFollow(
            @ApiParam(value = "ID of the follow", required = true) @PathVariable Long id,
            @ApiParam(value = "Updated follow data", required = true) @RequestBody FollowDTO followDTO) {
        FollowDTO updatedFollowDTO = followService.updateFollow(id, followDTO);
        if (updatedFollowDTO != null) {
            return ResponseEntity.ok(updatedFollowDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a follow by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Follow deleted successfully"),
            @ApiResponse(code = 404, message = "Follow not found")
    })
    public ResponseEntity<Void> deleteFollow(
            @ApiParam(value = "ID of the follow", required = true) @PathVariable Long id) {
        followService.deleteFollow(id);
        return ResponseEntity.noContent().build();
    }
}
