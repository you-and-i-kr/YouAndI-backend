package com.example.coupleapp.controller;
import com.example.coupleapp.entity.MemoEntity;
import com.example.coupleapp.exception.domian.MemberErrorCode;
import com.example.coupleapp.exception.domian.MemoErrorCode;
import com.example.coupleapp.exception.domian.MemoException;
import com.example.coupleapp.service.MemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memos")
@Api(tags = "메모 API", value = "메모 작업")
public class MemoController {

    private final MemoService memoService;
    private boolean isValidMemos(List<MemoEntity> memos) {
        // 특정 값과 일치하지 않는 경우를 검사합니다.
        // 이 예제에서는 memos가 null이거나 비어있는 경우가 틀린 경우로 가정합니다.
        return memos != null && !memos.isEmpty();
    }
    @Autowired
    public MemoController(MemoService memoService) {
        this.memoService = memoService;
    }

    @GetMapping("/member/{memberId}")
    @ApiOperation(value = "회원 ID로 메모 조회", notes = "특정 회원의 메모를 조회합니다.")
    public ResponseEntity<List<MemoEntity>> getMemosByMemberId(
            @PathVariable Long memberId
    ) {
        List<MemoEntity> memos = memoService.getMemosByMemberId(memberId);
        return ResponseEntity.ok(memos);
    }

    @GetMapping("/myPhoneNumber/{myPhoneNumber}")
    @ApiOperation(value = "내 전화번호로 메모 조회", notes = "내 전화번호로 작성한 메모를 조회합니다.")
    public ResponseEntity<List<MemoEntity>> getMemosByMyPhoneNumber(
            @PathVariable String myPhoneNumber
    ) {
        List<MemoEntity> memos = memoService.getMemosByMyPhoneNumber(myPhoneNumber);
        // 특정 값과 일치하지 않는 경우에 오류를 발생시킵니다.
        if (!isValidMemos(memos)) {
            throw new MemoException(MemoErrorCode.USER_NOT_FOUND);
        }

        return ResponseEntity.ok(memos);
    }
    @GetMapping("/yourPhoneNumber/{yourPhoneNumber}")
    @ApiOperation(value = "상대방 전화번호로 메모 조회", notes = "상대방 전화번호로 작성한 메모를 조회합니다.")
    public ResponseEntity<List<MemoEntity>> getMemosByYourPhoneNumber(
            @PathVariable String yourPhoneNumber
    ) {
        List<MemoEntity> memos = memoService.getMemosByYourPhoneNumber(yourPhoneNumber);

        if (!isValidMemos(memos)) {
            throw new MemoException(MemoErrorCode.USER_NOT_FOUND);
        }

        return ResponseEntity.ok(memos);
    }
    @PostMapping
    @ApiOperation(value = "메모 생성", notes = "새로운 메모를 생성합니다.")
    public ResponseEntity<MemoEntity> createMemo(
            @RequestBody MemoEntity memo
    ) {
        MemoEntity createdMemo = memoService.createMemo(memo);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMemo);
    }

    @PutMapping("/{memoId}")
    @ApiOperation(value = "메모 업데이트", notes = "기존 메모를 업데이트합니다.")
    public ResponseEntity<MemoEntity> updateMemo(
            @PathVariable Long memoId,
            @RequestBody MemoEntity updatedMemo
    ) {
        MemoEntity updated = memoService.updateMemo(memoId, updatedMemo);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{memoId}")
    @ApiOperation(value = "메모 삭제", notes = "특정 메모를 삭제합니다.")
    public ResponseEntity<Void> deleteMemo(
            @PathVariable Long memoId
    ) {
        memoService.deleteMemo(memoId);
        return ResponseEntity.noContent().build();
    }
}