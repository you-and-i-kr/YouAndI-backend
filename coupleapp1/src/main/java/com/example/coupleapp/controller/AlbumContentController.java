package com.example.coupleapp.controller;

import com.example.coupleapp.dto.AlbumContentDTO;
import com.example.coupleapp.service.AlbumContentService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/albumcontents")
@Api(value = "Album Content Controller", description = "Operations pertaining to album contents")
public class AlbumContentController {
    private final AlbumContentService albumContentService;

    @Autowired
    public AlbumContentController(AlbumContentService albumContentService) {
        this.albumContentService = albumContentService;
    }

    @GetMapping("/{contentId}")
    @ApiOperation(value = "Get an album content by content ID", response = AlbumContentDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved album content by content ID"),
            @ApiResponse(code = 404, message = "Album content not found")
    })
    public ResponseEntity<AlbumContentDTO> getAlbumContent(
            @ApiParam(value = "Content ID of the album", required = true) @PathVariable String contentId) {
        AlbumContentDTO albumContentDTO = albumContentService.getAlbumContentById(contentId);
        if (albumContentDTO != null) {
            return ResponseEntity.ok(albumContentDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ApiOperation(value = "Create a new album content", response = AlbumContentDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Album content created successfully"),
            @ApiResponse(code = 400, message = "Invalid input")
    })
    public ResponseEntity<AlbumContentDTO> createAlbumContent(
            @ApiParam(value = "Album content data", required = true) @RequestBody AlbumContentDTO albumContentDTO) {
        AlbumContentDTO createdAlbumContent = albumContentService.createAlbumContent(albumContentDTO);
        return ResponseEntity.ok(createdAlbumContent);
    }

    @PutMapping("/{contentId}")
    @ApiOperation(value = "Update an album content by content ID", response = AlbumContentDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated album content by content ID"),
            @ApiResponse(code = 404, message = "Album content not found"),
            @ApiResponse(code = 400, message = "Invalid input")
    })
    public ResponseEntity<AlbumContentDTO> updateAlbumContent(
            @ApiParam(value = "Content ID of the album", required = true) @PathVariable String contentId,
            @ApiParam(value = "Updated album content data", required = true) @RequestBody AlbumContentDTO albumContentDTO) {
        AlbumContentDTO updatedAlbumContent = albumContentService.updateAlbumContent(contentId, albumContentDTO);
        if (updatedAlbumContent != null) {
            return ResponseEntity.ok(updatedAlbumContent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{contentId}")
    @ApiOperation(value = "Delete an album content by content ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Album content deleted successfully"),
            @ApiResponse(code = 404, message = "Album content not found")
    })
    public ResponseEntity<Void> deleteAlbumContent(
            @ApiParam(value = "Content ID of the album", required = true) @PathVariable String contentId) {
        albumContentService.deleteAlbumContent(contentId);
        return ResponseEntity.noContent().build();
    }
}
