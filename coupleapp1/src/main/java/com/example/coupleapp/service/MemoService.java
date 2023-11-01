package com.example.coupleapp.service;

import com.example.coupleapp.entity.MemoEntity;
import com.example.coupleapp.repository.MemoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemoService {
    private final MemoRepository memoRepository;

    @Autowired
    public MemoService(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    public List<MemoEntity> getMemosByMemberId(Long memberId) {
        return memoRepository.findByMemberId(memberId);
    }

    public List<MemoEntity> getMemosByMyPhoneNumber(String myPhoneNumber) {
        return memoRepository.findByMyPhoneNumber(myPhoneNumber);
    }

    public List<MemoEntity> getMemosByYourPhoneNumber(String yourPhoneNumber) {
        return memoRepository.findByYourPhoneNumber(yourPhoneNumber);
    }

    public MemoEntity createMemo(MemoEntity memo) {
        return memoRepository.save(memo);
    }

    public void deleteMemo(Long memoId) {
        memoRepository.deleteById(memoId);
    }

    // Update 메서드 추가
    public MemoEntity updateMemo(Long memoId, MemoEntity updatedMemo) {
        MemoEntity existingMemo = memoRepository.findById(memoId)
                .orElseThrow(() -> new RuntimeException("메모를 찾을 수 없습니다."));

        // 업데이트할 필드 설정
        existingMemo.setMemoContent(updatedMemo.getMemoContent());
        // 다른 필드 업데이트

        return memoRepository.save(existingMemo);
    }
}
