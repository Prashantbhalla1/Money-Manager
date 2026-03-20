package com.prashant.smatpersonalfinance.controller;

import com.prashant.smatpersonalfinance.dto.RequestDto;
import com.prashant.smatpersonalfinance.dto.ResponseDto;
import com.prashant.smatpersonalfinance.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;
    @GetMapping
  public   ResponseEntity<Map<String,Object>> getAll(){
        return
                ResponseEntity.ok(dashboardService.getDashboardData());
    }

    @PostMapping("/getAll/filter")
    public  ResponseEntity<List<ResponseDto>> getALlFilteredData(@RequestBody RequestDto requestDto){
        return ResponseEntity.ok(dashboardService.getDashboardFilterdData(requestDto));
    }
}
