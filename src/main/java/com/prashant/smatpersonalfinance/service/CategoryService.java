package com.prashant.smatpersonalfinance.service;

import com.prashant.smatpersonalfinance.dto.CategoryDto;
import com.prashant.smatpersonalfinance.entity.CategoryEntity;
import com.prashant.smatpersonalfinance.entity.ProfileEntity;
import com.prashant.smatpersonalfinance.repository.CategoryRepository;
import com.prashant.smatpersonalfinance.repository.ExpanseRepository;
import com.prashant.smatpersonalfinance.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProfileService profileService;
    private final IncomeRepository incomeRepository;
    private final ExpanseRepository expanseRepository;
    private CategoryEntity toEntity(CategoryDto categoryDto, ProfileEntity profileEntity){
        return CategoryEntity.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .icon(categoryDto.getIcon())
                .type(categoryDto.getType())
                .profile(profileEntity)


                .build();
    }
    private CategoryDto toDto(CategoryEntity entity){
        return CategoryDto.builder().
                id(entity.getId())
                .name(entity.getName())
                .icon(entity.getIcon())
                .profileId(entity.getProfile()!=null?entity.getProfile().getId():null)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .type(entity.getType())

        .build();
    }
    public CategoryDto saveCategory(CategoryDto categoryDto){
        ProfileEntity currentProfile=profileService.getCurrentProfile();
        CategoryEntity categoryEntity=toEntity(categoryDto,currentProfile);
        if(categoryRepository.existsByNameAndProfileId(categoryDto.getName(),currentProfile.getId())){
            throw new RuntimeException("Category Already exist");
        }
      CategoryEntity entity=  categoryRepository.save(categoryEntity);
        return toDto(entity);
    }

    public List<CategoryDto> allCategoryForCurrentProfile(){

        ProfileEntity currentProfile=profileService.getCurrentProfile();
        List<CategoryEntity>list=categoryRepository.findByProfileId(currentProfile.getId());
        List<CategoryDto>newDto=new ArrayList<>();
        for(CategoryEntity e:list)
            newDto.add(toDto(e));
        return newDto;
    }
    public List<CategoryDto> getByType(String type){
        ProfileEntity currentProfile=profileService.getCurrentProfile();
   //     List<CategoryEntity>list=categoryRepository.findByProfileId(currentProfile.getId());
        List<CategoryEntity>list=categoryRepository.findByTypeAndProfileId(type,currentProfile.getId());
        return list.stream().map(this::toDto).toList();

    }
    public CategoryDto updateCategory(Long id,CategoryDto categoryDto){
        ProfileEntity currentProfile=profileService.getCurrentProfile();
   CategoryEntity entity=     categoryRepository.findByIdAndProfileId(id,currentProfile.getId()).orElseThrow(
           ()->   new RuntimeException("Categroy not found")
        );
   entity.setName(categoryDto.getName());
   entity.setType(categoryDto.getType());
   entity.setIcon(categoryDto.getIcon());
   categoryRepository.save(entity);
   return toDto(entity);

    }
@Transactional
    public CategoryDto delete(Long id){
        ProfileEntity currentProfile=profileService.getCurrentProfile();
        CategoryEntity entity=     categoryRepository.findByIdAndProfileId(id,currentProfile.getId()).orElseThrow(
                ()->   new RuntimeException("Categroy not found")
        );
       if(entity.getType().equals("Income")){
           incomeRepository.deleteByCategoryId(entity.getId());
       }
       else{
           expanseRepository.deleteByCategoryId(entity.getId());
       }
       categoryRepository.delete(entity);
        return toDto(entity);

    }
}
