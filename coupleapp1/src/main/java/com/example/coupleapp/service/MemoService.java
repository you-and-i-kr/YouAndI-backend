package com.example.coupleapp.service;

import com.example.coupleapp.dto.MemoDTO;
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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;
    private final MemberRepository memberRepository;
    public MemoEntity createMemo(MemoDTO memoDTO,Long memberId) {
        MemberEntity member = memberRepository.findMemberByMemberId(memberId);
        MemoEntity memoEntity = new MemoEntity();
        memoEntity.setMemberId(memberId);
        memoEntity.setMyPhoneNumber(member.getMy_phone_number());
        memoEntity.setYourPhoneNumber(member.getYour_phone_number());
        memoEntity.setMemoContent(memoDTO.getMemoContent());
        return  memoRepository.save(memoEntity);
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


    public MemoEntity getMemo(Long memberId, Long memoId) {
        //1.메모 테이블에 있는 멤버아이디에 해당하는 데이터들 가져오기
        //2. 방금 가져온 데이터들 중에서 메모 아이디에 해당하는 값을 추출하기
        //3. 예외처리
        MemoEntity memo = memoRepository.findByMemoId(memoId);
        // 예외처리 코드 새로 짜서 수정해주사면 됩니당~!!
        if(!Objects.equals(memberId, memo.getMemberId())) throw new CommonException(CommonErrorCode.NOT_FOUND_IMG_FILES);
        return  memoRepository.findByMemberIdAndMemoId(memberId,memoId);
    }
}
