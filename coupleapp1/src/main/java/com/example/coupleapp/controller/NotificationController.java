//package com.example.coupleapp.controller;
//
//import com.example.coupleapp.dto.NotificationDTO;
//import com.example.coupleapp.service.NotificationService;
//import io.swagger.annotations.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/notifications")
//@Api(value = "Notification Controller", description = "Operations pertaining to notifications")
//public class NotificationController {
//    private final NotificationService notificationService;
//
//    @Autowired
//    public NotificationController(NotificationService notificationService) {
//        this.notificationService = notificationService;
//    }
//
//    @GetMapping("/{id}")
//    @ApiOperation(value = "Get a notification by ID", response = NotificationDTO.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successfully retrieved notification by ID"),
//            @ApiResponse(code = 404, message = "Notification not found")
//    })
//    public ResponseEntity<NotificationDTO> getNotification(
//            @ApiParam(value = "ID of the notification", required = true) @PathVariable Long id) {
//        NotificationDTO notificationDTO = notificationService.getNotificationById(id);
//        if (notificationDTO != null) {
//            return ResponseEntity.ok(notificationDTO);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @PostMapping
//    @ApiOperation(value = "Create a new notification", response = NotificationDTO.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 201, message = "Notification created successfully"),
//            @ApiResponse(code = 400, message = "Invalid input")
//    })
//    public ResponseEntity<NotificationDTO> createNotification(
//            @ApiParam(value = "Notification data", required = true) @RequestBody NotificationDTO notificationDTO) {
//        NotificationDTO createdNotificationDTO = notificationService.createNotification(notificationDTO);
//        return ResponseEntity.ok(createdNotificationDTO);
//    }
//
//    @PutMapping("/{id}")
//    @ApiOperation(value = "Update a notification by ID", response = NotificationDTO.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successfully updated notification by ID"),
//            @ApiResponse(code = 404, message = "Notification not found"),
//            @ApiResponse(code = 400, message = "Invalid input")
//    })
//    public ResponseEntity<NotificationDTO> updateNotification(
//            @ApiParam(value = "ID of the notification", required = true) @PathVariable Long id,
//            @ApiParam(value = "Updated notification data", required = true) @RequestBody NotificationDTO notificationDTO) {
//        NotificationDTO updatedNotificationDTO = notificationService.updateNotification(id, notificationDTO);
//        if (updatedNotificationDTO != null) {
//            return ResponseEntity.ok(updatedNotificationDTO);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    @ApiOperation(value = "Delete a notification by ID")
//    @ApiResponses(value = {
//            @ApiResponse(code = 204, message = "Notification deleted successfully"),
//            @ApiResponse(code = 404, message = "Notification not found")
//    })
//    public ResponseEntity<Void> deleteNotification(
//            @ApiParam(value = "ID of the notification", required = true) @PathVariable Long id) {
//        notificationService.deleteNotification(id);
//        return ResponseEntity.noContent().build();
//    }
//}
