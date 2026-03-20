package com.prashant.smatpersonalfinance.service;

import com.prashant.smatpersonalfinance.dto.IncomeDTO;
import com.prashant.smatpersonalfinance.entity.CategoryEntity;
import com.prashant.smatpersonalfinance.entity.IncomeEntity;
import com.prashant.smatpersonalfinance.entity.ProfileEntity;
import com.prashant.smatpersonalfinance.repository.CategoryRepository;
import com.prashant.smatpersonalfinance.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class IncomeService {
    private final IncomeRepository incomeRepository;
private  final ProfileService profileService;
private final CategoryRepository categoryRepository;
    private IncomeEntity toEntity(IncomeDTO incomeDTO, CategoryEntity category, ProfileEntity profile){

        return IncomeEntity.builder()
                .id(incomeDTO.getId())
                .name(incomeDTO.getName())
                .amount(incomeDTO.getAmount())
                .category(category)
                .profile(profile)
                .date(incomeDTO.getDate())



                .build();
    }
    private IncomeDTO toDto(IncomeEntity incomeEntity){

        return IncomeDTO.builder()
                .id(incomeEntity.getId())
                .name(incomeEntity.getName())
                .amount(incomeEntity.getAmount())
                .createdAt(incomeEntity.getCreatedAt())
                .date(incomeEntity.getDate())
                .updatedAt(incomeEntity.getUpdatedAt())
                .categoryName(incomeEntity.getCategory()!=null?incomeEntity.getCategory().getName():null)
                .categoryId(incomeEntity.getCategory()!=null?incomeEntity.getCategory().getId():null)
                .profileId(incomeEntity.getProfile()!=null?incomeEntity.getProfile().getId():null)



                .build();
    }

 public    IncomeDTO addIncome(IncomeDTO incomeDTO){
        ProfileEntity current=profileService.getCurrentProfile();


 CategoryEntity category=       categoryRepository.findById(incomeDTO.getCategoryId()).orElseThrow(()->
                 new RuntimeException("Not a valid category"));
     IncomeEntity incomeEntity=toEntity(incomeDTO,category,current);
    IncomeEntity saved=    incomeRepository.save(incomeEntity);
     System.out.println(saved);
        return toDto(saved);


 }
 public IncomeDTO update(Long id,IncomeDTO incomeDTO){
     ProfileEntity current=profileService.getCurrentProfile();


     IncomeEntity incomeEntity=incomeRepository.findByIdAndProfileId(id,current.getId()).orElseThrow(
             ()->new RuntimeException("Income not found")
     );
     incomeEntity.setAmount(incomeDTO.getAmount());
     incomeEntity.setName(incomeDTO.getName());
     return toDto(incomeRepository.save(incomeEntity));

 }
 public List<IncomeDTO> getCurrentMonth(){
     ProfileEntity current=profileService.getCurrentProfile();
     LocalDate now=LocalDate.now();
     LocalDate st=now.withDayOfMonth(1);
     LocalDate ed=now.withDayOfMonth(now.lengthOfMonth());
    List<IncomeEntity> list=incomeRepository.findByProfileIdAndDateBetween(current.getId(),st,ed);
    return list.stream().map(this::toDto).toList();
 }
 public List<IncomeDTO> getLatest5Income(){

     ProfileEntity current=profileService.getCurrentProfile();
     List<IncomeEntity>list=incomeRepository.findTop5ByProfileIdOrderByDateDesc(current.getId());
     return list.stream().map(this::toDto).toList();
 }
    public BigDecimal totalExpanse(){
        ProfileEntity current=profileService.getCurrentProfile();
        BigDecimal val=incomeRepository.findTotalByProfileId(current.getId());
        if(val==null)val=BigDecimal.ZERO;
        return val;
    }
 public void delete(Long id){
     ProfileEntity current=profileService.getCurrentProfile();;
     IncomeEntity entity=incomeRepository.findByIdAndProfileId(id,current.getId()).orElseThrow(
             ()->new RuntimeException("Not found a income")
     );
     incomeRepository.delete(entity);

 }

}
