//package com.example.coupleapp.controller;
//
//import com.example.coupleapp.dto.ChatDTO;
//import com.example.coupleapp.service.ChatService;
//import io.swagger.annotations.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/chats")
//@Api(value = "Chat Controller", description = "Operations pertaining to chats")
//public class ChatController {
//    private final ChatService chatService;
//
//    @Autowired
//    public ChatController(ChatService chatService) {
//        this.chatService = chatService;
//    }
//
//    @GetMapping("/{id}")
//    @ApiOperation(value = "Get a chat by ID", response = ChatDTO.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successfully retrieved chat by ID"),
//            @ApiResponse(code = 404, message = "Chat not found")
//    })
//    public ChatDTO getChat(
//            @ApiParam(value = "ID of the chat", required = true) @PathVariable Long id) {
//        return chatService.getChatById(id);
//    }
//
//    @PostMapping
//    @ApiOperation(value = "Create a new chat", response = ChatDTO.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 201, message = "Chat created successfully"),
//            @ApiResponse(code = 400, message = "Invalid input")
//    })
//    public ChatDTO createChat(
//            @ApiParam(value = "Chat data", required = true) @RequestBody ChatDTO chatDTO) {
//        return chatService.createChat(chatDTO);
//    }
//
//    @PutMapping("/{id}")
//    @ApiOperation(value = "Update a chat by ID", response = ChatDTO.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successfully updated chat by ID"),
//            @ApiResponse(code = 404, message = "Chat not found"),
//            @ApiResponse(code = 400, message = "Invalid input")
//    })
//    public ChatDTO updateChat(
//            @ApiParam(value = "ID of the chat", required = true) @PathVariable Long id,
//            @ApiParam(value = "Updated chat data", required = true) @RequestBody ChatDTO chatDTO) {
//        return chatService.updateChat(id, chatDTO);
//    }
//
//    @DeleteMapping("/{id}")
//    @ApiOperation(value = "Delete a chat by ID")
//    @ApiResponses(value = {
//            @ApiResponse(code = 204, message = "Chat deleted successfully"),
//            @ApiResponse(code = 404, message = "Chat not found")
//    })
//    public void deleteChat(
//            @ApiParam(value = "ID of the chat", required = true) @PathVariable Long id) {
//        chatService.deleteChat(id);
//    }
//}
