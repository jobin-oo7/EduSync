package com.edusync.service;

import com.edusync.dto.AppDto;
import com.edusync.model.Notice;
import com.edusync.model.User;
import com.edusync.repository.NoticeRepository;
import com.edusync.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Notice board service — create, list, delete notices.
 */
@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepo;
    private final UserRepository userRepo;

    @Transactional
    public Notice createNotice(Long adminId, AppDto.NoticeDto dto) {
        User creator = userRepo.findById(adminId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Notice notice = Notice.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .category(dto.getCategory() != null ? dto.getCategory() : Notice.Category.ACADEMIC)
                .visibility(dto.getVisibility() != null ? dto.getVisibility() : Notice.Visibility.ENTIRE_COLLEGE)
                .createdBy(creator)
                .expiresAt(dto.getExpiresAt())
                .build();

        return noticeRepo.save(notice);
    }

    public List<Notice> getActiveNotices() {
        return noticeRepo.findActiveNotices(LocalDateTime.now());
    }

    @Transactional
    public void deleteNotice(Long id) {
        Notice notice = noticeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Notice not found"));
        notice.setActive(false);
        noticeRepo.save(notice);
    }

    public List<Notice> getAllNotices() {
        return noticeRepo.findByActiveTrueOrderByCreatedAtDesc();
    }
}
