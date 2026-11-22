package com.edusync.service;

import com.edusync.dto.AppDto;
import com.edusync.model.User;
import com.edusync.repository.LeaveRequestRepository;
import com.edusync.repository.NoticeRepository;
import com.edusync.repository.PlacementDriveRepository;
import com.edusync.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * User management service.
 * Handles profile retrieval, admin user management, dashboard stats.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final LeaveRequestRepository leaveRepo;
    private final NoticeRepository noticeRepo;
    private final PlacementDriveRepository driveRepo;

    // ─── Get user profile ─────────────────────────────────────────────────

    public User getProfile(Long userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
    }

    public User getByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));
    }

    // ─── Admin: List all users ────────────────────────────────────────────

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public List<User> getUsersByRole(User.Role role) {
        return userRepo.findByRole(role);
    }

    public List<User> getUsersByDept(String dept) {
        return userRepo.findByRoleAndDepartment(User.Role.STUDENT, dept);
    }

    // ─── Admin: Toggle user active/inactive ──────────────────────────────

    @Transactional
    public User toggleUserActive(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setActive(!user.isActive());
        return userRepo.save(user);
    }

    // ─── Admin: Dashboard statistics ─────────────────────────────────────

    public AppDto.DashboardStats getDashboardStats() {
        long students = userRepo.findByRole(User.Role.STUDENT).size();
        long faculty  = userRepo.findByRole(User.Role.FACULTY).size();
        long pending  = leaveRepo.countByOverallStatus(
            com.edusync.model.LeaveRequest.Status.PENDING
        );
        long notices = noticeRepo.findActiveNotices(LocalDateTime.now()).size();
        long drives  = driveRepo.findByStatusOrderByDriveDateDesc(
            com.edusync.model.PlacementDrive.DriveStatus.UPCOMING
        ).size();

        return AppDto.DashboardStats.builder()
                .totalStudents(students)
                .totalFaculty(faculty)
                .pendingLeaves(pending)
                .activeNotices(notices)
                .upcomingDrives(drives)
                .totalDepartments(6)   // CSE, ECE, MECH, FT, EEE, CE
                .build();
    }

    // ─── Update profile ───────────────────────────────────────────────────

    @Transactional
    public User updateProfile(Long userId, String phone, String profilePic) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (phone != null)      user.setPhone(phone);
        if (profilePic != null) user.setProfilePic(profilePic);
        return userRepo.save(user);
    }
}
