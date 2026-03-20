package com.prashant.smatpersonalfinance.controller;

import com.prashant.smatpersonalfinance.dto.CategoryDto;
import com.prashant.smatpersonalfinance.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping("/add")
    public ResponseEntity<Object> saveCategory(@RequestBody CategoryDto categoryDto){
        try{
            CategoryDto saved=categoryService.saveCategory(categoryDto);
            return ResponseEntity.ok(saved);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of(
                    "message",e.getMessage()
            ));

        }
    }
    @GetMapping("/get")
    public List<CategoryDto> getALlCategoryForCurrentUser(){
        return categoryService.allCategoryForCurrentProfile();
    }
    @GetMapping("/getByType/{type}")
    public ResponseEntity<List<CategoryDto>> getAllByType(@PathVariable String type){
        List<CategoryDto>list=categoryService.getByType(type);
        return ResponseEntity.ok(list);
    }
    @PostMapping("/update/{id}")
    public ResponseEntity<Object> updateCategory(@PathVariable Long id,@RequestBody CategoryDto categoryDto){
        try{
       CategoryDto entity=     categoryService.updateCategory(id,categoryDto);
            System.out.println(entity);
       return ResponseEntity.ok(entity);
        }
        catch (Exception e){
        return    ResponseEntity.badRequest().body(Map.of(
                    "message",e.getMessage()
            ));

        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        try{
            CategoryDto entity=     categoryService.delete(id);
            System.out.println(entity);
            return ResponseEntity.ok(entity);
        }
        catch (Exception e){
            return    ResponseEntity.badRequest().body(Map.of(
                    "message",e.getMessage()
            ));

        }
    }



}
