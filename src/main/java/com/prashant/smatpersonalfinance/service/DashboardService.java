package com.prashant.smatpersonalfinance.service;

import com.prashant.smatpersonalfinance.dto.*;
import com.prashant.smatpersonalfinance.entity.ExpanseEntity;
import com.prashant.smatpersonalfinance.entity.IncomeEntity;
import com.prashant.smatpersonalfinance.entity.ProfileEntity;
import com.prashant.smatpersonalfinance.repository.CategoryRepository;
import com.prashant.smatpersonalfinance.repository.ExpanseRepository;
import com.prashant.smatpersonalfinance.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;




@RequiredArgsConstructor
@Service
public class DashboardService {
    private final  ProfileService profileService;
    private final IncomeService incomeService;
    private final IncomeRepository incomeRepository;
    private final ExpanseRepository expanseRepository;
    private final ExpanseService expanseService;
    private final CategoryRepository categoryRepository;
    public  List<ResponseDto> getDashboardFilterdData(RequestDto requestDto){
        List<ResponseDto>responseDtos=new ArrayList<>();
        ProfileEntity current=profileService.getCurrentProfile();
        if(requestDto.getType().equals("Income")){
            List<IncomeEntity> incomeEntities=incomeRepository.filterIncomes(current.getId(),
                    requestDto.getStartDate(),
                    requestDto.getEndDate(),
                    requestDto.getSearch(),
                    requestDto.getSortField(),
                    requestDto.getSortOrder()

                    );

            for(IncomeEntity i:incomeEntities){
                ResponseDto responseDto=ResponseDto.builder()
                        .type("Income")
                        .name(i.getName())
                        .amount(i.getAmount())
                        .date(i.getDate())



                        .build();
                responseDtos.add(responseDto);

            }

        }
        else{
            List<ExpanseEntity> expanseEntities=expanseRepository.filterExpanses(current.getId(),
                    requestDto.getStartDate(),
                    requestDto.getEndDate(),
                    requestDto.getSearch(),
                    requestDto.getSortField(),
                    requestDto.getSortOrder()

            );
            for(ExpanseEntity i:expanseEntities){
                ResponseDto responseDto=ResponseDto.builder()
                        .type("Expense")
                        .name(i.getName())
                        .amount(i.getAmount())
                        .date(i.getDate())



                        .build();
                responseDtos.add(responseDto);

            }

        }
        return responseDtos;
    }
    public Map<String,Object> getDashboardData(){
        ProfileEntity current=profileService.getCurrentProfile();


        BigDecimal incomeTotal=incomeService.totalExpanse();
        BigDecimal expanse=expanseService.totalExpanse();
        List<IncomeDTO>incomeList=incomeService.getLatest5Income();
        List<ExpanseDTO> expanseDTOList=expanseService.getLatest5Income();
        Map<String,Object>res=new LinkedHashMap<>();
//List<RecentTransactionDto>list=concat(incomeList.stream().map(income->
//        RecentTransactionDto.builder()
//                .id(income.getId())
//                .name(income.getName())
//                .amount(income.getAmount())
//                .date(income.getDate())
//                .type("income")
//                .createdAt(income.getCreatedAt())
//                .updatedAt(income.getUpdatedAt())
//                .build()
//
//        ))
//        expanseDTOList.stream().map(income1->
//                RecentTransactionDto.builder()
//                        .id(income1.getId())
//                        .name(income1.getName())
//                        .amount(income1.getAmount())
//                        .date(income1.getDate())
//                        .type("expanse")
//                        .createdAt(income1.getCreatedAt())
//                        .updatedAt(income1.getUpdatedAt())
//                        .build()
//
//        ))
//
//
//
//    }
        res.put("totalIncome",incomeTotal);
     res.put("totalExpanse",expanse);
        res.put("incomeList",incomeList);
        res.put("expanseList",expanseDTOList);
return res;}



}
