package com.example.coupleapp.service;

import com.example.coupleapp.dto.MemoDTO;
import com.example.coupleapp.entity.MemberEntity;
import com.example.coupleapp.entity.MemoEntity;
import com.example.coupleapp.entity.PhotoEntity;
import com.example.coupleapp.exception.domian.MemoException;
import com.example.coupleapp.repository.MemberRepository;
import com.example.coupleapp.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;
    private final MemberRepository memberRepository;
    public MemoDTO createMemo(MemoDTO memoDTO,long memberId) {
        MemberEntity member = memberRepository.findMemberByMemberId(memberId);
        MemoEntity memoEntity = new MemoEntity();
        memoEntity.setMemberId(member.getMember_id());
        memoEntity.setMemoContent(memoDTO.getMemoContent());
        // 다른 필드들도 설정해야 할 수 있음

        MemoEntity savedMemo = memoRepository.save(memoEntity);

        return convertEntityToDTO(savedMemo);
    }

    public List<MemoDTO> getAllMemos() {
        List<MemoEntity> memoEntities = memoRepository.findAll();
        return convertEntitiesToDTOs(memoEntities);
    }

    public MemoDTO getMemoById(long memberId) {
        MemberEntity member = memberRepository.findMemberByMemberId(memberId);
        if (member != null) {
            // 멤버를 찾았을 경우, 이후의 로직을 수행합니다.
            // 이곳에서 메모를 가져오고 DTO로 변환하는 작업을 수행합니다.
            MemoEntity memo = (MemoEntity) memoRepository.findMemoByMemberId(member.getMember_id());
            MemoDTO memoDTO = convertEntityToDTO(memo);
            return memoDTO;
        } else {
            // 멤버를 찾지 못한 경우, 적절한 예외 처리 또는 다른 작업을 수행합니다.
            return null;
        }
    }


    public MemoDTO updateMemo(Long memoId, MemoDTO updatedMemoDTO) {
        Optional<MemoEntity> optionalMemo = memoRepository.findById(memoId);
        if (optionalMemo.isPresent()) {
            MemoEntity memoEntity = optionalMemo.get();
            // 업데이트로직 구현

            MemoEntity updatedMemo = memoRepository.save(memoEntity);
            return convertEntityToDTO(updatedMemo);
        } else {
            // 메모를 찾지 못한 경우 예외 처리
            // 예를 들어, throw new MemoNotFoundException();
            return null;
        }
    }

    public void deleteMemo(Long memoId) {
        memoRepository.deleteById(memoId);
    }

    private MemoDTO convertEntityToDTO(MemoEntity memoEntity) {
        MemoDTO memoDTO = new MemoDTO();
        memoDTO.setMemberId(memoEntity.getMemberId());
        memoDTO.setMemoContent(memoEntity.getMemoContent());
        // 다른 필드들도 설정해야 할 수 있음

        return memoDTO;
    }

    private List<MemoDTO> convertEntitiesToDTOs(List<MemoEntity> memoEntities) {
        // 엔티티 목록을 DTO 목록으로 변환
        // 여기에서 반복문을 사용하여 각 엔티티를 DTO로 변환하면 됨
        // 또는 Java 8 스트림을 활용할 수도 있음

        // 예시:
        // return memoEntities.stream()
        //     .map(this::convertEntityToDTO)
        //     .collect(Collectors.toList());

        return null;
    }
}
