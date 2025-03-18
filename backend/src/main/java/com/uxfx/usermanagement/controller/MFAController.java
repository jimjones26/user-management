package com.uxfx.usermanagement.controller;

import com.uxfx.usermanagement.dto.MFASetupRequest;
import com.uxfx.usermanagement.dto.MFASetupResponse;
import com.uxfx.usermanagement.service.MFAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/mfa")
public class MFAController {
    @Autowired
    private MFAService mfaService;

    @PostMapping("/setup")
    @PreAuthorize("#request.userId == principal.userId")
    public ResponseEntity<MFASetupResponse> setupMFA(@Valid @RequestBody MFASetupRequest request) {
        MFASetupResponse response = mfaService.setupMFA(request.getUserId(), request.getMethod());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/backup-codes")
    @PreAuthorize("#userId == principal.userId")
    public ResponseEntity<List<String>> getBackupCodes(@RequestParam Long userId) {
        List<String> codes = mfaService.getBackupCodes(userId);
        return ResponseEntity.ok(codes);
    }
}