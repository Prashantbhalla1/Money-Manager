package com.prashant.smatpersonalfinance.controller;

import com.prashant.smatpersonalfinance.dto.AuthDto;
import com.prashant.smatpersonalfinance.dto.ProfileDto;
import com.prashant.smatpersonalfinance.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;


    @PostMapping("/register")
    public ResponseEntity<Object> registerProfile( @RequestBody ProfileDto profileDto){

        try {
            ProfileDto profileDto1=profileService.registerProfile(profileDto);
            return ResponseEntity.ok(profileDto1);
        }
        catch (Exception e){
        return     ResponseEntity.badRequest().body(

               Map.of(
                       "msg",e.getMessage()
               )

            );
        }
//return null;
    }
    @GetMapping("/activate")
    public String activateUser(@RequestParam String token){
        boolean is=profileService.activateProfile(token);
        if(is){
            return "Activated user";
        }
        else{
            return "Not activated";
        }

    }
   @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> login(@RequestBody AuthDto authDto){
        try{
            if(!profileService.isAccountActive(authDto.getEmail())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                        "msg","Account is not active"

                ));
            }
            System.out.println(authDto);
         Map<String,Object> res=   profileService.authenticateAndGenToken(authDto);
            return ResponseEntity.ok(res);
        }
        catch (Exception e){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "msg",e.getMessage()
            ));

        }
    }
    @GetMapping("/test")
 public    String doTest(){
        return "Success";
    }
}
