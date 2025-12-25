package com.center.medical.center.qingdao.profession.controller;

import cn.hutool.core.util.StrUtil;
import com.center.medical.center.qingdao.profession.entity.persistent.Peispatient;
import com.center.medical.center.qingdao.profession.repository.PeispatientRepository;
import com.center.medical.center.qingdao.profession.service.OccupationalHealthArchivesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("send")
@Slf4j
public class SendController {
    private final OccupationalHealthArchivesService occupationalHealthArchivesService;
    private final PeispatientRepository peispatientRepository;

    public SendController(OccupationalHealthArchivesService occupationalHealthArchivesService, PeispatientRepository peispatientRepository) {
        this.occupationalHealthArchivesService = occupationalHealthArchivesService;
        this.peispatientRepository = peispatientRepository;
    }

    @GetMapping("single/{patientCode}")
    @ResponseBody
    public void send(@PathVariable String patientCode) {
        occupationalHealthArchivesService.uploadSingle(patientCode);
    }

    @GetMapping("single/review/{patientCode}")
    @ResponseBody
    public void sendReview(@PathVariable String patientCode){
        occupationalHealthArchivesService.uploadReviewSingle(patientCode);
    }

    @GetMapping("date/{startDate}/{endDate}")
    public void sendByDate(@PathVariable Date startDate, @PathVariable Date endDate) {
        occupationalHealthArchivesService.uploadCondition(startDate, endDate);
    }

    @GetMapping("order/{order}")
    public void sendByOrder(@PathVariable String order) {
        occupationalHealthArchivesService.uploadByOrder(order);
    }

    @GetMapping("order/excel/1")
    public void sendByExcel1(){
        occupationalHealthArchivesService.uploadExcel(1);
    }

    @GetMapping("order/excel/2")
    public void sendByExcel2(){
        occupationalHealthArchivesService.uploadExcel(2);
    }

    @GetMapping("order/excel/3")
    public void sendByExcel3(){
        occupationalHealthArchivesService.uploadExcel(3);
    }

    @PostMapping("multi")
    public void sendMulti(@RequestBody List<String> patientcodes){
        if(patientcodes!=null&&patientcodes.size()>0){
            Thread thread=new Thread(()->{
                for(String patientcode:patientcodes){
                    Peispatient peispatient = peispatientRepository.findByPatientcode(patientcode);
                    if(peispatient!=null){
                        if(StrUtil.isNotEmpty(peispatient.getInpatientno())){ //复查体检号不为空
                            occupationalHealthArchivesService.uploadReviewSingle(patientcode);
                        }else{
                            occupationalHealthArchivesService.uploadSingle(patientcode);
                        }
                    }
                }
            });
            thread.start();
        }
    }

}
