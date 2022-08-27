package com.cgd.cvm_technical_support.controller.rest;
import com.cgd.cvm_technical_support.CgdCvmTechnicalSupport;
import com.cgd.cvm_technical_support.service.SeIssue;
import com.cgd.cvm_technical_support.tmp.IssueNextStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
public class CnIssue {
    private final String minLongInf = CgdCvmTechnicalSupport.MIN_LONG_INF;
    private final String maxLongInf = CgdCvmTechnicalSupport.MAX_LONG_INF;
    private final SeIssue service;

    public CnIssue(SeIssue service) {
        this.service = service;
    }

    //for monitoring web portal
    @GetMapping("/api/v1/issue/all")
    public ResponseEntity<Object> getAllIssues(HttpServletRequest request,
                  @RequestParam(required = false,defaultValue = "1") int page,
                  @RequestParam(required = false,defaultValue = "") String sortBy,
                  @RequestParam(required = false,defaultValue = "") String sortDir,
                  @RequestParam(required = false,defaultValue = "%") String shopCode,
                  @RequestParam(required = false,defaultValue = "%") String machineNumber,
                  @RequestParam(required = false,defaultValue = "%") String msoPhone,
                  @RequestParam(required = false,defaultValue = "%") String ticketNumber,
                  @RequestParam(required = false,defaultValue = "%") String statusId,
                  @RequestParam(required = false,defaultValue = "%") String statusTag,
                  @RequestParam(required = false,defaultValue = "1970-01-01") String startDate,
                  @RequestParam(required = false,defaultValue = "2100-12-31") String endDate,
                  @RequestParam(required = false,defaultValue = minLongInf) Long creationToResolveMinStart,
                  @RequestParam(required = false,defaultValue = maxLongInf) Long creationToResolveMinEnd,
                  @RequestParam(required = false,defaultValue = "true") String creationToResolveWithNull,
                  @RequestParam(required = false,defaultValue = minLongInf) Long creationToClosingMinStart,
                  @RequestParam(required = false,defaultValue = maxLongInf) Long creationToClosingMinEnd,
                  @RequestParam(required = false,defaultValue = "true") String creationToClosingWithNull
    ) {return ResponseEntity.ok(service.getAllIssues(request,page,sortBy,sortDir,shopCode,machineNumber,
            msoPhone,ticketNumber,statusId,statusTag,startDate,endDate,
            creationToResolveMinStart,creationToResolveMinEnd,creationToResolveWithNull,
            creationToClosingMinStart,creationToClosingMinEnd,creationToClosingWithNull));
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
