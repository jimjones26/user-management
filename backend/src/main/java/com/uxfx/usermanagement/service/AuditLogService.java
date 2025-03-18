package com.uxfx.usermanagement.service;

import com.uxfx.usermanagement.dto.AuditLogDto;
import com.uxfx.usermanagement.model.AuditLog;
import com.uxfx.usermanagement.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuditLogService {
    @Autowired
    private AuditLogRepository auditLogRepository;

    public List<AuditLogDto> getAuditLogs(Long userId, String action, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        Specification<AuditLog> spec = Specification.where(null);
        if (userId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("user").get("userId"), userId));
        }
        if (action != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("action"), action));
        }
        if (startDate != null && endDate != null) {
            spec = spec.and((root, query, cb) -> cb.between(root.get("timestamp"), startDate, endDate));
        }
        List<AuditLog> auditLogs = auditLogRepository.findAll(spec, pageable).getContent();
        return auditLogs.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private AuditLogDto convertToDto(AuditLog auditLog) {
        AuditLogDto dto = new AuditLogDto();
        dto.setLogId(auditLog.getLogId());
        dto.setAction(auditLog.getAction());
        dto.setTimestamp(auditLog.getTimestamp());
        dto.setDetails(auditLog.getDetails());
        return dto;
    }
}