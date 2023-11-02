package com.example.coupleapp.controller;

import com.example.coupleapp.dto.MemoDTO;
import com.example.coupleapp.entity.MemoEntity;
import com.example.coupleapp.security.AuthHolder;
import com.example.coupleapp.service.MemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memos")
@Api(tags = "메모 API", value = "메모 작업")
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    // 새로운 메모 생성
    @PostMapping
    @ApiOperation(value = "새로운 메모 생성")
    public ResponseEntity<MemoDTO> createMemo(
            @ApiParam(value = "메모 데이터", required = true) @RequestBody MemoDTO memoDTO) {
        Long memberId = AuthHolder.getMemberId();
        MemoDTO createdMemo = memoService.createMemo(memoDTO,memberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMemo);
    }
//
//    // 모든 메모 목록 가져오기
//    @GetMapping
//    @ApiOperation(value = "모든 메모 목록 가져오기")
//    public ResponseEntity<List<MemoDTO>> getAllMemos() {
//        List<MemoDTO> memos = memoService.getAllMemos();
//        return ResponseEntity.ok(memos);
//    }

    // 특정 ID에 해당하는 메모 가져오기
    @GetMapping("/{memoId}")
    @ApiOperation(value = "특정 ID에 해당하는 메모 가져오기")
    public ResponseEntity<MemoDTO> getMemo(
            @ApiParam(value = "메모 ID", required = true) @PathVariable Long memoid) {
//        Long memberId = AuthHolder.getMemberId();
        MemoDTO memo = memoService.getMemoById(memoid);
        return ResponseEntity.ok(memo);
    }

    // 메모 업데이트
    @PutMapping("/{memoId}")
    @ApiOperation(value = "메모 업데이트")
    public ResponseEntity<MemoDTO> updateMemo(
            @ApiParam(value = "메모 ID", required = true) @PathVariable Long memoId,
            @ApiParam(value = "업데이트된 메모 정보", required = true) @RequestBody MemoDTO updatedMemoDTO) {
        MemoDTO updatedMemo = memoService.updateMemo(memoId, updatedMemoDTO);
        return ResponseEntity.ok(updatedMemo);
    }

    // 특정 ID에 해당하는 메모 삭제
    @DeleteMapping("/{memoId}")
    @ApiOperation(value = "특정 ID에 해당하는 메모 삭제")
    public ResponseEntity<Void> deleteMemo(
            @ApiParam(value = "메모 ID", required = true) @PathVariable Long memoId) {
        memoService.deleteMemo(memoId);
        return ResponseEntity.noContent().build();
    }
}
