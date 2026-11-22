package com.edusync.dto;

import com.edusync.model.LeaveRequest;
import com.edusync.model.Notice;
import com.edusync.model.PlacementDrive;
import com.edusync.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

// ─── Leave DTOs ─────────────────────────────────────────────────────────────

public class AppDto {

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class LeaveRequestDto {
        @NotNull
        private LeaveRequest.LeaveType leaveType;
        @NotBlank
        private String reason;
        @NotNull
        private LocalDate fromDate;
        @NotNull
        private LocalDate toDate;
        private String eventName;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class LeaveApprovalDto {
        @NotNull
        private LeaveRequest.Status status;   // APPROVED or REJECTED
        private String remark;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class UserSummary {
        private Long id;
        private String name;
        private String email;
        private String department;
        private String semester;
        private String section;
        private String rollNumber;
        private String employeeId;

        public static UserSummary from(User user) {
            if (user == null) return null;
            return UserSummary.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .department(user.getDepartment())
                    .semester(user.getSemester())
                    .section(user.getSection())
                    .rollNumber(user.getRollNumber())
                    .employeeId(user.getEmployeeId())
                    .build();
        }
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class LeaveResponse {
        private Long id;
        private UserSummary student;
        private LeaveRequest.LeaveType leaveType;
        private String reason;
        private LocalDate fromDate;
        private LocalDate toDate;
        private int noOfDays;
        private String eventName;
        private String attachmentPath;
        private LeaveRequest.Status overallStatus;
        private LeaveRequest.Status facultyStatus;
        private String facultyRemark;
        private String facultyApprovedBy;
        private LocalDateTime facultyApprovedAt;
        private LeaveRequest.Status hodStatus;
        private String hodRemark;
        private String hodApprovedBy;
        private LocalDateTime hodApprovedAt;
        private LeaveRequest.Status principalStatus;
        private String principalRemark;
        private LocalDateTime principalApprovedAt;
        private String currentStage;
        private String pdfPath;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public static LeaveResponse from(LeaveRequest leave) {
            if (leave == null) return null;
            return LeaveResponse.builder()
                    .id(leave.getId())
                    .student(UserSummary.from(leave.getStudent()))
                    .leaveType(leave.getLeaveType())
                    .reason(leave.getReason())
                    .fromDate(leave.getFromDate())
                    .toDate(leave.getToDate())
                    .noOfDays(leave.getNoOfDays())
                    .eventName(leave.getEventName())
                    .attachmentPath(leave.getAttachmentPath())
                    .overallStatus(leave.getOverallStatus())
                    .facultyStatus(leave.getFacultyStatus())
                    .facultyRemark(leave.getFacultyRemark())
                    .facultyApprovedBy(leave.getFacultyApprovedBy())
                    .facultyApprovedAt(leave.getFacultyApprovedAt())
                    .hodStatus(leave.getHodStatus())
                    .hodRemark(leave.getHodRemark())
                    .hodApprovedBy(leave.getHodApprovedBy())
                    .hodApprovedAt(leave.getHodApprovedAt())
                    .principalStatus(leave.getPrincipalStatus())
                    .principalRemark(leave.getPrincipalRemark())
                    .principalApprovedAt(leave.getPrincipalApprovedAt())
                    .currentStage(leave.getCurrentStage())
                    .pdfPath(leave.getPdfPath())
                    .createdAt(leave.getCreatedAt())
                    .updatedAt(leave.getUpdatedAt())
                    .build();
        }
    }

    // ─── Notice DTOs ────────────────────────────────────────────────────────

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class NoticeDto {
        @NotBlank
        private String title;
        @NotBlank
        private String content;
        private Notice.Category category;
        private Notice.Visibility visibility;
        private LocalDateTime expiresAt;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class NoticeResponse {
        private Long id;
        private String title;
        private String content;
        private String category;
        private String visibility;
        private String createdBy;
        private LocalDateTime createdAt;
        private LocalDateTime expiresAt;
    }

    // ─── Placement DTOs ─────────────────────────────────────────────────────

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class PlacementDriveDto {
        @NotBlank
        private String companyName;
        private String logo;
        @NotBlank
        private String role;
        private String description;
        private Double packageLpa;
        private LocalDate driveDate;
        private LocalDate lastDateToApply;
        private Double minCgpa;
        private String eligibleBranches;
        private String eligibleBatch;
        private String jobType;
        private String location;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class PlacementApplicationDto {
        private Long driveId;
        private Double cgpa;
    }

    // ─── Attendance DTOs ────────────────────────────────────────────────────

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class AttendanceMark {
        @NotNull
        private Long studentId;
        @NotNull
        private Long subjectId;
        @NotNull
        private LocalDate date;
        @NotNull
        private com.edusync.model.Attendance.AttendanceStatus status;
        private Integer periodNumber;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class AttendanceSummary {
        private String subjectName;
        private String subjectCode;
        private long totalClasses;
        private long attended;
        private double percentage;
    }

    // ─── Mark DTOs ──────────────────────────────────────────────────────────

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class MarkDto {
        @NotNull
        private Long studentId;
        @NotNull
        private Long subjectId;
        @NotNull
        private com.edusync.model.Mark.ExamType examType;
        @NotNull
        private Double marksObtained;
        @NotNull
        private Double maxMarks;
        private String semester;
        private String academicYear;
    }

    // ─── Dashboard stats ────────────────────────────────────────────────────

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class DashboardStats {
        private long totalStudents;
        private long totalFaculty;
        private long pendingLeaves;
        private long activeNotices;
        private long upcomingDrives;
        private long totalDepartments;
    }

    // ─── Generic API response wrapper ───────────────────────────────────────

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class ApiResponse<T> {
        private boolean success;
        private String message;
        private T data;

        public static <T> ApiResponse<T> ok(String msg, T data) {
            return AppDto.ApiResponse.<T>builder()
                    .success(true).message(msg).data(data).build();
        }

        public static <T> ApiResponse<T> error(String msg) {
            return AppDto.ApiResponse.<T>builder()
                    .success(false).message(msg).build();
        }
    }
}
