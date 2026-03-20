package com.prashant.smatpersonalfinance.controller;

import com.prashant.smatpersonalfinance.dto.ExpanseDTO;
import com.prashant.smatpersonalfinance.dto.IncomeDTO;
import com.prashant.smatpersonalfinance.service.ExpanseService;
import com.prashant.smatpersonalfinance.service.IncomeService;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/expanse")
@RequiredArgsConstructor
public class ExpanseController {

    private final ExpanseService expanseService;
    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody ExpanseDTO expanseDTO){
        try{
            ExpanseDTO saveDto=   expanseService.addIncome(expanseDTO);
            return ResponseEntity.ok(saveDto);

        }
        catch (Exception e){
            return   ResponseEntity.badRequest().body(Map.of(
                    "message",e.getMessage()
            ));

        }
    }
//    @PostMapping("/update/{id}")
//    public ResponseEntity<Object> update( @PathVariable Long id, @RequestBody ExpanseDTO expanseDTO){
//        try{
//            IncomeDTO saveDto=   expanseService.update(id,expanseDTO);
//            return ResponseEntity.ok(expanseDTO);
//
//        }
//        catch (Exception e){
//            return   ResponseEntity.badRequest().body(Map.of(
//                    "message",e.getMessage()
//            ));
//
//        }
//    }
    @GetMapping("/getCurrentMonth")
    public ResponseEntity<List<ExpanseDTO>> getCurrentMonthIncome(){

        List<ExpanseDTO>list=expanseService.getCurrentMonth();
        return ResponseEntity.ok(list);


    }
    @PostMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        try{
            expanseService.delete(id);
            return ResponseEntity.noContent().build();
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of(
                    "message" , e.getMessage()

            ));
        }
    }
}
