package com.example.coupleapp.service;

import com.example.coupleapp.dto.MemoDTO;
import com.example.coupleapp.entity.MemoEntity;
import com.example.coupleapp.repository.MemoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemoService {
    private final MemoRepository memoRepository;

    @Autowired
    public MemoService(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    public MemoDTO getMemoById(Long memoId) {
        MemoEntity memoEntity = memoRepository.findById(memoId).orElse(null);
        return convertToDTO(memoEntity);
    }

    public MemoDTO createMemo(MemoDTO memoDTO) {
        MemoEntity memoEntity = convertToEntity(memoDTO);
        memoEntity = memoRepository.save(memoEntity);
        return convertToDTO(memoEntity);
    }

    public MemoDTO updateMemo(Long memoId, MemoDTO memoDTO) {
        MemoEntity existingMemoEntity = memoRepository.findById(memoId).orElse(null);
        if (existingMemoEntity != null) {
            updateEntityFromDTO(existingMemoEntity, memoDTO);
            memoRepository.save(existingMemoEntity);
            return convertToDTO(existingMemoEntity);
        } else {
            return null; // 업데이트할 항목을 찾지 못한 경우
        }
    }

    public void deleteMemo(Long memoId) {
        memoRepository.deleteById(memoId);
    }

    private MemoDTO convertToDTO(MemoEntity memoEntity) {
        if (memoEntity == null) {
            return null;
        }
        MemoDTO memoDTO = new MemoDTO();
        memoDTO.setMemoId(memoEntity.getMemoId());
        memoDTO.setMemoContent(memoEntity.getMemoContent());
        memoDTO.setComment(memoEntity.getComment());
        memoDTO.setCreatedAt(memoEntity.getCreatedAt());
        return memoDTO;
    }

    private MemoEntity convertToEntity(MemoDTO memoDTO) {
        MemoEntity memoEntity = new MemoEntity();
        memoEntity.setMemoId(memoDTO.getMemoId());
        memoEntity.setMemoContent(memoDTO.getMemoContent());
        memoEntity.setComment(memoDTO.getComment());
        memoEntity.setCreatedAt(memoDTO.getCreatedAt());
        return memoEntity;
    }

    private void updateEntityFromDTO(MemoEntity existingEntity, MemoDTO memoDTO) {
        existingEntity.setMemoContent(memoDTO.getMemoContent());
        existingEntity.setComment(memoDTO.getComment());
        existingEntity.setCreatedAt(memoDTO.getCreatedAt());
        // 다른 필드도 필요한 경우 업데이트
    }
}
