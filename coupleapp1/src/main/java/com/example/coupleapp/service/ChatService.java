package com.example.coupleapp.service;

import com.example.coupleapp.dto.ChatDTO;
import com.example.coupleapp.entity.ChatEntity;
import com.example.coupleapp.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private String memberEntity;

    @Autowired
    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public ChatDTO getChatById(Long chatId) {
        ChatEntity chatEntity = chatRepository.findById(chatId).orElse(null);
        return convertToDTO(chatEntity);
    }

    public ChatDTO createChat(ChatDTO chatDTO) {
        ChatEntity chatEntity = convertToEntity(chatDTO);
        chatEntity = chatRepository.save(chatEntity);
        return convertToDTO(chatEntity);
    }

    public ChatDTO updateChat(Long chatId, ChatDTO chatDTO) {
        ChatEntity existingChatEntity = chatRepository.findById(chatId).orElse(null);
        if (existingChatEntity != null) {
            updateEntityFromDTO(existingChatEntity, chatDTO);
            chatRepository.save(existingChatEntity);
            return convertToDTO(existingChatEntity);
        } else {
            return null; // 업데이트할 항목을 찾지 못한 경우
        }
    }

    public void deleteChat(Long chatId) {
        chatRepository.deleteById(chatId);
    }

    private ChatDTO convertToDTO(ChatEntity chatEntity) {
        if (chatEntity == null) {
            return null;
        }
        ChatDTO chatDTO = new ChatDTO();
        chatDTO.setChatId(chatEntity.getChatId());
        chatDTO.setMemberId(chatEntity.getMember().getId());
        chatDTO.setPartnerId(chatEntity.getPartnerId());
        chatDTO.setTimestamp(chatEntity.getTimestamp());
        chatDTO.setMessage(chatEntity.getMessage());
        return chatDTO;
    }

    private ChatEntity convertToEntity(ChatDTO chatDTO) {
        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setChatId(chatDTO.getChatId());
        // MemberEntity를 얻는 방법은 필요에 따라 변경 가능
        // 예: chatDTO에서 memberId를 이용하여 MemberEntity 조회
        chatEntity.setMessage(memberEntity);
        chatEntity.setPartnerId(chatDTO.getPartnerId());
        chatEntity.setTimestamp(chatDTO.getTimestamp());
        chatEntity.setMessage(chatDTO.getMessage());
        return chatEntity;
    }

    private void updateEntityFromDTO(ChatEntity existingEntity, ChatDTO chatDTO) {
        existingEntity.setPartnerId(chatDTO.getPartnerId());
        existingEntity.setTimestamp(chatDTO.getTimestamp());
        existingEntity.setMessage(chatDTO.getMessage());
        // 다른 필드도 필요한 경우 업데이트
    }
}
