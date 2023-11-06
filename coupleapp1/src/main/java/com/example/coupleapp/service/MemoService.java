package com.example.coupleapp.service;

import com.example.coupleapp.dto.MemoDTO;
import com.example.coupleapp.dto.MemoResponseDTO;
import com.example.coupleapp.entity.MemberEntity;
import com.example.coupleapp.entity.MemoEntity;
import com.example.coupleapp.entity.PhotoEntity;
import com.example.coupleapp.exception.domian.CommonErrorCode;
import com.example.coupleapp.exception.domian.CommonException;
import com.example.coupleapp.exception.domian.MemoErrorCode;
import com.example.coupleapp.exception.domian.MemoException;
import com.example.coupleapp.repository.MemberRepository;
import com.example.coupleapp.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;
    private final MemberRepository memberRepository;
    public MemoResponseDTO createMemo(MemoDTO memoDTO, Long memberId) {

        MemberEntity member = memberRepository.findMemberById(memberId);

        MemoEntity newMemo = new MemoEntity();
        newMemo.setMember(member);
        newMemo.setMyPhoneNumber(member.getMy_phone_number());
        newMemo.setYourPhoneNumber(member.getYour_phone_number());
        newMemo.setMemoContent(memoDTO.getMemoContent());
        newMemo.setCreated_at(LocalDateTime.now());

        MemoEntity saveMemo = memoRepository.save(newMemo);
        MemoResponseDTO memoResponseDTO = new MemoResponseDTO();
        memoResponseDTO.setMemoContent(saveMemo.getMemoContent());
        memoResponseDTO.setMemoId(saveMemo.getId());
        return memoResponseDTO;
    }




    public MemoEntity updateMemo(Long memoId, String memoContent) {
        // Retrieve the existing memoEntity by memoId
        MemoEntity existingMemo = memoRepository.findById(memoId)
                .orElseThrow(() ->new MemoException(MemoErrorCode.FAIL_UPDATE));
        // Update the memoContent
        existingMemo.setMemoContent(memoContent);
        // Save the updated memoEntity in the repository
        return memoRepository.save(existingMemo);
    }

    public void deleteMemo(Long memoId) {
        memoRepository.deleteById(memoId);
    }

//    private MemoDTO convertEntityToDTO(MemoEntity memoEntity) {
//        MemoDTO memoDTO = new MemoDTO();
//        memoDTO.setMemberId(memoEntity.getMemberId());
//        memoDTO.setMemoContent(memoEntity.getMemoContent());
//        memoDTO.setMemoId(memoEntity.getMemoId());
//        // 다른 필드들도 설정해야 할 수 있음
//
//        return memoDTO;
//    }


//    public MemoEntity getMemo(Long memberId, Long memoId) {
//        MemoEntity memo = memoRepository.findByMemoId(memoId);
//        if(!Objects.equals(memberId, memo.getMemo_id())) throw new MemoException(MemoErrorCode.NOT_EQUL_COUPLE);
//        return  memoRepository.findByMemberAndMemoId(memberId,memoId);
//    }
}
