package com.cgd.cvm_technical_support.controller.rest;
import com.cgd.cvm_technical_support.service.SeIssue;
import com.cgd.cvm_technical_support.dto.IssueNextStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
public class CnIssue {
    private final SeIssue service;

    public CnIssue(SeIssue service) {
        this.service = service;
    }

    @GetMapping("/api/v1/web/dashboard")
    public ResponseEntity<Object> getWebDashboardAnalytics(){
        return ResponseEntity.ok(service.getWebDashboardAnalytics());
    }

    @PostMapping("/api/v1/issue/create")
    public ResponseEntity<Object> createIssue(
            HttpServletRequest request,
            @RequestParam(required = false) String shopCode,
            @RequestParam Long machineId,
            @RequestParam(required = false,defaultValue = "1") Long issueTypeId){
        return ResponseEntity.ok(service.createNewIssue(request, shopCode, machineId,issueTypeId));
    }

    @GetMapping("/api/v1/issue/active/status")
    public ResponseEntity<Object> getAllActiveIssueStatus(HttpServletRequest request){
        return ResponseEntity.ok(service.getAllActiveIssueStatus(request));
    }

    @GetMapping("/api/v1/issue/detail")
    public ResponseEntity<Object> getIssueDetail(HttpServletRequest request,@RequestParam Long id){
        return ResponseEntity.ok(service.getIssueDetail(request,id));
    }

    @PostMapping("/api/v1/issue/status/next")
    public ResponseEntity<Object> setNextStatus(HttpServletRequest request,@RequestBody IssueNextStatus body){
        return ResponseEntity.ok(service.setNextStatus(request,body));
    }

    @PostMapping("/api/v1/issue/close")
    public ResponseEntity<Object> closeIssue(HttpServletRequest request,@RequestParam Long id){
        return ResponseEntity.ok(service.closeIssue(request,id));
    }

}
