package com.prashant.smatpersonalfinance.service;

import com.prashant.smatpersonalfinance.dto.ExpanseDTO;
import com.prashant.smatpersonalfinance.dto.IncomeDTO;
import com.prashant.smatpersonalfinance.entity.CategoryEntity;
import com.prashant.smatpersonalfinance.entity.ExpanseEntity;
import com.prashant.smatpersonalfinance.entity.IncomeEntity;
import com.prashant.smatpersonalfinance.entity.ProfileEntity;
import com.prashant.smatpersonalfinance.repository.CategoryRepository;
import com.prashant.smatpersonalfinance.repository.ExpanseRepository;
import com.prashant.smatpersonalfinance.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpanseService {
    private final ExpanseRepository expanseRepository;
    private  final ProfileService profileService;
    private final CategoryRepository categoryRepository;
    private ExpanseEntity toEntity(ExpanseDTO expanseDTO, CategoryEntity category, ProfileEntity profile){

        return ExpanseEntity.builder()
                .id(expanseDTO.getId())
                .name(expanseDTO.getName())
                .amount(expanseDTO.getAmount())
                .category(category)
                .profile(profile)
                .date(expanseDTO.getDate())



                .build();
    }
    public List<ExpanseDTO> getCurrentMonth(){
        ProfileEntity current=profileService.getCurrentProfile();
        LocalDate now=LocalDate.now();
        LocalDate st=now.withDayOfMonth(1);
        LocalDate ed=now.withDayOfMonth(now.lengthOfMonth());
        List<ExpanseEntity> list=expanseRepository.findByProfileIdAndDateBetween(current.getId(),st,ed);
        return list.stream().map(this::toDto).toList();
    } public void delete(Long id){
        ProfileEntity current=profileService.getCurrentProfile();;
        ExpanseEntity entity=expanseRepository.findByIdAndProfileId(id,current.getId()).orElseThrow(
                ()->new RuntimeException("Not found a income")
        );
        expanseRepository.delete(entity);

    }

    private ExpanseDTO toDto(ExpanseEntity expanseEntity){

        return ExpanseDTO.builder()
                .id(expanseEntity.getId())
                .name(expanseEntity.getName())
                .amount(expanseEntity.getAmount())
                .createdAt(expanseEntity.getCreatedAt())
                .date(expanseEntity.getDate())
                .updatedAt(expanseEntity.getUpdatedAt())
                .categoryId(expanseEntity.getCategory()!=null?expanseEntity.getCategory().getId():null)
                .profileId(expanseEntity.getProfile()!=null?expanseEntity.getProfile().getId():null)



                .build();
    }

    public    ExpanseDTO addIncome(ExpanseDTO expanseDTO){
        ProfileEntity current=profileService.getCurrentProfile();


        CategoryEntity category=       categoryRepository.findById(expanseDTO.getCategoryId()).orElseThrow(()->
                new RuntimeException("Not a valid category"));
        ExpanseEntity incomeEntity=toEntity(expanseDTO,category,current);
        ExpanseEntity saved=    expanseRepository.save(incomeEntity);

        return toDto(saved);


    }
    public List<ExpanseDTO> getLatest5Income(){

        ProfileEntity current=profileService.getCurrentProfile();
        List<ExpanseEntity>list=expanseRepository.findTop5ByProfileIdOrderByDateDesc(current.getId());
        return list.stream().map(this::toDto).toList();
    }
    public BigDecimal totalExpanse(){
        ProfileEntity current=profileService.getCurrentProfile();
        BigDecimal val=expanseRepository.findTotalByProfileId(current.getId());
        if(val==null)val=BigDecimal.ZERO;
        return val;
    }

 public    List<ExpanseDTO> getDailyExpanse(Long profileId, LocalDate date){
        List<ExpanseEntity>list=expanseRepository.findByProfileIdAndDate(profileId,date);
        return list.stream().map(this::toDto).toList();
    }
}
