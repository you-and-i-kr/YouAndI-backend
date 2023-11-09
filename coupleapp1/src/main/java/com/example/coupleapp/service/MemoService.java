package com.example.coupleapp.service;

import com.example.coupleapp.dto.MemoDTO;
import com.example.coupleapp.dto.MemoResponseDTO;
import com.example.coupleapp.entity.MemberEntity;
import com.example.coupleapp.entity.MemoEntity;
import com.example.coupleapp.exception.domian.*;
import com.example.coupleapp.repository.Member.MemberRepository;
import com.example.coupleapp.repository.Memo.MemoRepository;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

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

    public Map<String,Object> getMemos(Long memberId,int pageNumber,int pageSize) {
        MemberEntity member = memberRepository.findMemberById(memberId);
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<Tuple> getMemoList = memoRepository.findMemoList(member,pageable);

        if(getMemoList.getContent().size() == 0) throw new CommonException(CommonErrorCode.NOT_FOUND_MEDIA_FILES);

        List<Map<String,Object>> resultList = new ArrayList<>();
        for (Tuple tuple : getMemoList) {
            Map<String, Object> memoMap = new HashMap<>();
            memoMap.put("memo_id", tuple.get(0, Long.class));
            memoMap.put("content",tuple.get(1,String.class));
            memoMap.put("created_AT",tuple.get(2,LocalDateTime.class));
            resultList.add(memoMap);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("content", resultList);
        result.put("number", getMemoList.getNumber());
        result.put("size", getMemoList.getSize());
        result.put("totalPages", getMemoList.getTotalPages());
        result.put("hasPrevious", getMemoList.hasPrevious());
        result.put("hasNext", getMemoList.hasNext());


        return result;

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

}
