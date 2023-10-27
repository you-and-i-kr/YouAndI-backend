//package com.example.coupleapp.controller;
//
//import com.example.coupleapp.dto.SettingsDTO;
//import com.example.coupleapp.service.SettingsService;
//import io.swagger.annotations.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/settings")
//@Api(value = "Settings Controller", description = "Operations pertaining to settings")
//public class SettingsController {
//    private final SettingsService settingsService;
//
//    @Autowired
//    public SettingsController(SettingsService settingsService) {
//        this.settingsService = settingsService;
//    }
//
//    @GetMapping("/{id}")
//    @ApiOperation(value = "Get settings by ID", response = SettingsDTO.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successfully retrieved settings by ID"),
//            @ApiResponse(code = 404, message = "Settings not found")
//    })
//    public ResponseEntity<SettingsDTO> getSettings(
//            @ApiParam(value = "ID of the settings", required = true) @PathVariable Long id) {
//        SettingsDTO settingsDTO = settingsService.getSettingsById(id);
//        if (settingsDTO != null) {
//            return ResponseEntity.ok(settingsDTO);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @PostMapping
//    @ApiOperation(value = "Create new settings", response = SettingsDTO.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 201, message = "Settings created successfully"),
//            @ApiResponse(code = 400, message = "Invalid input")
//    })
//    public ResponseEntity<SettingsDTO> createSettings(
//            @ApiParam(value = "Settings data", required = true) @RequestBody SettingsDTO settingsDTO) {
//        SettingsDTO createdSettingsDTO = settingsService.createSettings(settingsDTO);
//        return ResponseEntity.ok(createdSettingsDTO);
//    }
//
//    @PutMapping("/{id}")
//    @ApiOperation(value = "Update settings by ID", response = SettingsDTO.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successfully updated settings by ID"),
//            @ApiResponse(code = 404, message = "Settings not found"),
//            @ApiResponse(code = 400, message = "Invalid input")
//    })
//    public ResponseEntity<SettingsDTO> updateSettings(
//            @ApiParam(value = "ID of the settings", required = true) @PathVariable Long id,
//            @ApiParam(value = "Updated settings data", required = true) @RequestBody SettingsDTO settingsDTO) {
//        SettingsDTO updatedSettingsDTO = settingsService.updateSettings(id, settingsDTO);
//        if (updatedSettingsDTO != null) {
//            return ResponseEntity.ok(updatedSettingsDTO);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    @ApiOperation(value = "Delete settings by ID")
//    @ApiResponses(value = {
//            @ApiResponse(code = 204, message = "Settings deleted successfully"),
//            @ApiResponse(code = 404, message = "Settings not found")
//    })
//    public ResponseEntity<Void> deleteSettings(
//            @ApiParam(value = "ID of the settings", required = true) @PathVariable Long id) {
//        settingsService.deleteSettings(id);
//        return ResponseEntity.noContent().build();
//    }
//}
