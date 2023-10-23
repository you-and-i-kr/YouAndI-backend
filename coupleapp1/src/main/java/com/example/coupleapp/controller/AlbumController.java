package com.example.coupleapp.controller;

import com.example.coupleapp.dto.AlbumDTO;
import com.example.coupleapp.service.AlbumService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/albums")
@Api(value = "Album Controller", description = "Operations pertaining to albums")
public class AlbumController {
    private final AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get an album by ID", response = AlbumDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved album by ID"),
            @ApiResponse(code = 404, message = "Album not found")
    })
    public ResponseEntity<AlbumDTO> getAlbum(
            @ApiParam(value = "ID of the album", required = true) @PathVariable Long id) {
        AlbumDTO albumDTO = albumService.getAlbumById(id);
        if (albumDTO != null) {
            return ResponseEntity.ok(albumDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ApiOperation(value = "Create a new album", response = AlbumDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Album created successfully"),
            @ApiResponse(code = 400, message = "Invalid input")
    })
    public ResponseEntity<AlbumDTO> createAlbum(
            @ApiParam(value = "Album data", required = true) @RequestBody AlbumDTO albumDTO) {
        AlbumDTO createdAlbumDTO = albumService.createAlbum(albumDTO);
        return ResponseEntity.ok(createdAlbumDTO);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an album by ID", response = AlbumDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated album by ID"),
            @ApiResponse(code = 404, message = "Album not found"),
            @ApiResponse(code = 400, message = "Invalid input")
    })
    public ResponseEntity<AlbumDTO> updateAlbum(
            @ApiParam(value = "ID of the album", required = true) @PathVariable Long id,
            @ApiParam(value = "Updated album data", required = true) @RequestBody AlbumDTO albumDTO) {
        AlbumDTO updatedAlbumDTO = albumService.updateAlbum(id, albumDTO);
        if (updatedAlbumDTO != null) {
            return ResponseEntity.ok(updatedAlbumDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete an album by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Album deleted successfully"),
            @ApiResponse(code = 404, message = "Album not found")
    })
    public ResponseEntity<Void> deleteAlbum(
            @ApiParam(value = "ID of the album", required = true) @PathVariable Long id) {
        albumService.deleteAlbum(id);
        return ResponseEntity.noContent().build();
    }
}
