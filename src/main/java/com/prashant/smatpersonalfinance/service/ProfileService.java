package com.prashant.smatpersonalfinance.service;

import com.prashant.smatpersonalfinance.dto.AuthDto;
import com.prashant.smatpersonalfinance.dto.ProfileDto;
import com.prashant.smatpersonalfinance.entity.ProfileEntity;
import com.prashant.smatpersonalfinance.repository.ProfileRepository;
import com.prashant.smatpersonalfinance.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Value("${backend_url}")
    private  String  url;

    public ProfileDto registerProfile(ProfileDto profileDto){

     Optional<ProfileEntity> exist= profileRepository.findByEmail(profileDto.getEmail());
     if(exist.isPresent()){
         throw new RuntimeException("Profile Already Exists!");
     }
ProfileEntity profileEntity=toEntity(profileDto);
profileEntity.setActivatedToken(UUID.randomUUID().toString());

 String activationUrl=url+"/activate?token="+profileEntity.getActivatedToken();
 String subject="Activate ";
try {
    emailService.sendEmail(profileEntity.getEmail(), subject, activationUrl);
}
catch (Exception e){
    profileEntity.setIsActive(true);
}
        profileEntity= profileRepository.save(profileEntity);
        return toDto(profileEntity);
    }
    public ProfileEntity toEntity(ProfileDto profileDto){
        return ProfileEntity.builder().id(profileDto.getId()).fullName(profileDto.getFullName())
                .email(profileDto.getEmail()).
                password( passwordEncoder.encode(profileDto.getPassword()))
                .createdAt(profileDto.getCreatedAt())
                .updatedAt(profileDto.getUpdatedAt())
                .build();
    }

    public ProfileDto toDto(ProfileEntity profileEntity){
        return ProfileDto.builder().id(profileEntity.getId())
                .fullName(profileEntity.getFullName())
                .email(profileEntity.getEmail())

                .createdAt(profileEntity.getCreatedAt())
                .updatedAt(profileEntity.getUpdatedAt())
                .build();

    }
    public boolean activateProfile(String token){
return profileRepository.findByActivatedToken(token).map(
        profile->{
            profile.setIsActive(true);
            profileRepository.save(profile);
            return true;
        }
).orElse(false);
    }

    public boolean isAccountActive(String email){
        return profileRepository.findByEmail(email)
                .map(ProfileEntity::getIsActive)
                .orElse(false);

    }

    public ProfileEntity getCurrentProfile(){
      Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
      String email=authentication.getName();
     return  profileRepository.findByEmail(email)
              .orElseThrow(()->new UsernameNotFoundException("Profile not fount "+email));
    }
    public ProfileDto getPublicProfile(String email){
        ProfileEntity profileEntity;
        if(email==null){
        profileEntity=    getCurrentProfile();
        }
        else{
            profileEntity=profileRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("Not fount "+email ));

        }
        return toDto(profileEntity);
    }
public Map<String,Object> authenticateAndGenToken (AuthDto authDto){
        try{
            System.out.println("1");

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDto.getEmail(),authDto.getPassword()));
            System.out.println("22");
            String token=jwtUtil.generateToken(authDto.getEmail());

            return Map.of(
                    "token",token,
                    "user",getPublicProfile(authDto.getEmail())
            );
        }
        catch (Exception e){
throw new RuntimeException("Invalid email or password");
        }

}
}
