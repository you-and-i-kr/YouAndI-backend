package com.example.coupleapp.service;

import com.example.coupleapp.dto.FollowDTO;
import com.example.coupleapp.entity.FollowEntity;
import com.example.coupleapp.entity.MemberEntity;
import com.example.coupleapp.repository.FollowRepository;
import com.example.coupleapp.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowService {
    private final FollowRepository followRepository;
    private final MemberRepository memberRepository; // MemberRepository 주입

    @Autowired
    public FollowService(FollowRepository followRepository, MemberRepository memberRepository) {
        this.followRepository = followRepository;
        this.memberRepository = memberRepository; // 주입된 MemberRepository를 초기화
    }
    public FollowDTO getFollowById(Long followId) {
        FollowEntity followEntity = followRepository.findById(followId).orElse(null);
        return convertToDTO(followEntity);
    }

    public FollowDTO createFollow(FollowDTO followDTO) {
        FollowEntity followEntity = convertToEntity(followDTO);
        followEntity = followRepository.save(followEntity);
        return convertToDTO(followEntity);
    }

    public FollowDTO updateFollow(Long followId, FollowDTO followDTO) {
        FollowEntity existingFollowEntity = followRepository.findById(followId).orElse(null);
        if (existingFollowEntity != null) {
            updateEntityFromDTO(existingFollowEntity, followDTO);
            followRepository.save(existingFollowEntity);
            return convertToDTO(existingFollowEntity);
        } else {
            return null; // 업데이트할 항목을 찾지 못한 경우
        }
    }

    public void deleteFollow(Long followId) {
        followRepository.deleteById(followId);
    }

    private FollowDTO convertToDTO(FollowEntity followEntity) {
        if (followEntity == null) {
            return null;
        }
        FollowDTO followDTO = new FollowDTO();
        followDTO.setFollowId(followEntity.getFollowId());
        followDTO.setFollowerId(followEntity.getFollower().getMemberId());
        followDTO.setFollowingId(followEntity.getFollowing().getMemberId());
        return followDTO;
    }

    private FollowEntity convertToEntity(FollowDTO followDTO) {
        FollowEntity followEntity = new FollowEntity();
        followEntity.setFollowId(followDTO.getFollowId());

        // MemberEntity를 얻는 방법으로 findByEmail 메서드를 사용하고, String 값을 전달
        List<MemberEntity> followers = memberRepository.findByEmail(String.valueOf(followDTO.getFollowerId()));
        List<MemberEntity> followings = memberRepository.findByEmail(String.valueOf(followDTO.getFollowingId()));

        // 리스트가 비어있지 않고, 값이 있는 경우에만 설정
        if (!followers.isEmpty() && !followings.isEmpty()) {
            followEntity.setFollower(followers.get(0)); // 첫 번째 값
            followEntity.setFollowing(followings.get(0)); // 첫 번째 값
        } else {
            // MemberEntity를 찾지 못한 경우에 대한 처리
            // 적절한 예외 처리 또는 오류 핸들링을 수행
            // 예: throw new MemberNotFoundException("Follower or Following not found");
        }

        return followEntity;
    }


    private void updateEntityFromDTO(FollowEntity existingEntity, FollowDTO followDTO) {
        // MemberEntity 업데이트 로직 추가
        // 예: existingEntity.setFollower(memberEntity);와 existingEntity.setFollowing(memberEntity);를 사용하여 업데이트
        // 다른 필드도 필요한 경우 업데이트
    }
}
