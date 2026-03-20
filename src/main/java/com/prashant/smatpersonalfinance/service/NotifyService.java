package com.prashant.smatpersonalfinance.service;

import com.prashant.smatpersonalfinance.dto.ExpanseDTO;
import com.prashant.smatpersonalfinance.entity.ExpanseEntity;
import com.prashant.smatpersonalfinance.entity.ProfileEntity;
import com.prashant.smatpersonalfinance.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotifyService {


   private final ProfileRepository profileRepository;
  private final ExpanseService expanseService;
  private final EmailService emailService;
  //  @Scheduled(cron="0 * * * * *" , zone = "IST")
    public void sendExpanseEmailToAllUser(){
        System.out.println("sended");
        List<ProfileEntity> profiles=profileRepository.findAll();

        for(ProfileEntity profile:profiles){
            StringBuilder table=new StringBuilder();
            List<ExpanseDTO> expanses=expanseService.getDailyExpanse(profile.getId(), LocalDate.now());
        table.append("Hi , ").append(profile.getFullName()).append("<br> <br>");
            table.append("<table>");
            table.append("<tr> <th>Sno</th> <th>Exp Name</th> <th> Amount</th> </tr> ");
            int i=1;
            for(ExpanseDTO expanse:expanses) {
                table.append("<tr> <td>" + i + "</td>" + "<td>" + expanse.getName() + "</td>"+ "<td>" + expanse.getAmount() + "</td>")
;
            i++;}
            table.append("</table>").append("<br><br> Regards <br>MoneyManager ");
            String body= ""+ table;
            emailService.sendEmail(profile.getEmail(),"Daily Expanses",body);

        }


    }
}

