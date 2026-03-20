package com.prashant.smatpersonalfinance.controller;

import com.prashant.smatpersonalfinance.dto.ExpanseDTO;
import com.prashant.smatpersonalfinance.dto.IncomeDTO;
import com.prashant.smatpersonalfinance.service.IncomeService;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/income")
@RequiredArgsConstructor
public class IncomeController {

    private final IncomeService incomeService;
    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody IncomeDTO incomeDTO){
        try{
         IncomeDTO saveDto=   incomeService.addIncome(incomeDTO);
         return ResponseEntity.ok(saveDto);

        }
        catch (Exception e){
          return   ResponseEntity.badRequest().body(Map.of(
                    "message",e.getMessage()
            ));

        }
    }
    @PostMapping("/update/{id}")
    public ResponseEntity<Object> update( @PathVariable Long id, @RequestBody IncomeDTO incomeDTO){
        try{
            IncomeDTO saveDto=   incomeService.update(id,incomeDTO);
            return ResponseEntity.ok(incomeDTO);

        }
        catch (Exception e){
            return   ResponseEntity.badRequest().body(Map.of(
                    "message",e.getMessage()
            ));

        }
    }
    @GetMapping("/getCurrentMonth")
    public ResponseEntity<List<IncomeDTO>> getCurrentMonthIncome(){

            List<IncomeDTO>list=incomeService.getCurrentMonth();
            return ResponseEntity.ok(list);


    }
    @PostMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        try{
            incomeService.delete(id);
            return ResponseEntity.noContent().build();
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of(
                    "message" , e.getMessage()

            ));
        }
    }
}
