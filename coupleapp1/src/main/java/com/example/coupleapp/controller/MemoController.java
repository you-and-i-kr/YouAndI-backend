//package com.example.coupleapp.controller;
//
//import com.example.coupleapp.dto.MemoDTO;
//import com.example.coupleapp.service.MemoService;
//import io.swagger.annotations.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/memos")
//@Api(value = "Memo Controller", description = "Operations pertaining to memos")
//public class MemoController {
//    private final MemoService memoService;
//
//    @Autowired
//    public MemoController(MemoService memoService) {
//        this.memoService = memoService;
//    }
//
//    @GetMapping("/{id}")
//    @ApiOperation(value = "Get a memo by ID", response = MemoDTO.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successfully retrieved memo by ID"),
//            @ApiResponse(code = 404, message = "Memo not found")
//    })
//    public ResponseEntity<MemoDTO> getMemo(
//            @ApiParam(value = "ID of the memo", required = true) @PathVariable Long id) {
//        MemoDTO memoDTO = memoService.getMemoById(id);
//        if (memoDTO != null) {
//            return ResponseEntity.ok(memoDTO);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @PostMapping
//    @ApiOperation(value = "Create a new memo", response = MemoDTO.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 201, message = "Memo created successfully"),
//            @ApiResponse(code = 400, message = "Invalid input")
//    })
//    public ResponseEntity<MemoDTO> createMemo(
//            @ApiParam(value = "Memo data", required = true) @RequestBody MemoDTO memoDTO) {
//        MemoDTO createdMemoDTO = memoService.createMemo(memoDTO);
//        return ResponseEntity.ok(createdMemoDTO);
//    }
//
//    @PutMapping("/{id}")
//    @ApiOperation(value = "Update a memo by ID", response = MemoDTO.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successfully updated memo by ID"),
//            @ApiResponse(code = 404, message = "Memo not found"),
//            @ApiResponse(code = 400, message = "Invalid input")
//    })
//    public ResponseEntity<MemoDTO> updateMemo(
//            @ApiParam(value = "ID of the memo", required = true) @PathVariable Long id,
//            @ApiParam(value = "Updated memo data", required = true) @RequestBody MemoDTO memoDTO) {
//        MemoDTO updatedMemoDTO = memoService.updateMemo(id, memoDTO);
//        if (updatedMemoDTO != null) {
//            return ResponseEntity.ok(updatedMemoDTO);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    @ApiOperation(value = "Delete a memo by ID")
//    @ApiResponses(value = {
//            @ApiResponse(code = 204, message = "Memo deleted successfully"),
//            @ApiResponse(code = 404, message = "Memo not found")
//    })
//    public ResponseEntity<Void> deleteMemo(
//            @ApiParam(value = "ID of the memo", required = true) @PathVariable Long id) {
//        memoService.deleteMemo(id);
//        return ResponseEntity.noContent().build();
//    }
//}
