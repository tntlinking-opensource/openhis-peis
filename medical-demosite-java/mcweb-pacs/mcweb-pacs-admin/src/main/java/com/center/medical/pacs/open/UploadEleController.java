package com.center.medical.pacs.open;

import com.alibaba.fastjson.JSON;
import com.center.medical.abteilung.bean.model.ElectroAudiometer;
import com.center.medical.abteilung.service.ElectroAudiometerService;
import com.center.medical.pacs.bean.dto.NumResultsDto;
import com.center.medical.pacs.bean.param.UploadEleResultParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 接收电测听上传结果
 */
@Slf4j
@RestController
@RequestMapping("/lan/uploadEle")
@Api(tags = "接收电测听上传结果")
@RequiredArgsConstructor
@Validated
public class UploadEleController {
    private final ElectroAudiometerService electroAudiometerService;




    /**
     * 接收图片
     *
     * @param jsonStr params
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation(value = "接收xml数据", notes = "接收xml数据")
    public String uploads(String jsonStr) throws ParseException {
        log.info("成功接收到xml数据:{}",jsonStr);
        List<UploadEleResultParam> list = JSON.parseArray(jsonStr, UploadEleResultParam.class);
        List<ElectroAudiometer> electroAudiometers = new ArrayList<>();
        for (UploadEleResultParam param : list) {
            ElectroAudiometer electroAudiometer = new ElectroAudiometer();
            //设置体检号
            electroAudiometer.setPatientcode(param.getPatientcode());
            //设置检查时间
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            electroAudiometer.setCreatedate(format.parse(param.getTime()));
            List<NumResultsDto> numResultsList = param.getNumResultsDtoList();
            for (NumResultsDto dto : numResultsList) {
                if ("1".equals(dto.getWay())){
                    //气导
                    switch (dto.getHz()) {
                        case "125":
                            if ("1".equals(dto.getEars())){
                                electroAudiometer.setAirLeft125(dto.getResult());
                            }else if ("2".equals(dto.getEars())){
                                electroAudiometer.setAirRight125(dto.getResult());
                            }
                            break;
                        case "250":
                            if ("1".equals(dto.getEars())){
                                electroAudiometer.setAirLeft250(dto.getResult());
                            }else if ("2".equals(dto.getEars())){
                                electroAudiometer.setAirRight250(dto.getResult());
                            }
                            break;
                        case "500":
                            if ("1".equals(dto.getEars())){
                                electroAudiometer.setAirLeft500(dto.getResult());
                            }else if ("2".equals(dto.getEars())){
                                electroAudiometer.setAirRight500(dto.getResult());
                            }
                            break;
                        case "1000":
                            if ("1".equals(dto.getEars())){
                                electroAudiometer.setAirLeft1000(dto.getResult());
                            }else if ("2".equals(dto.getEars())){
                                electroAudiometer.setAirRight1000(dto.getResult());
                            }
                            break;
                        case "2000":
                            if ("1".equals(dto.getEars())){
                                electroAudiometer.setAirLeft2000(dto.getResult());
                            }else if ("2".equals(dto.getEars())){
                                electroAudiometer.setAirRight2000(dto.getResult());
                            }
                            break;
                        case "3000":
                            if ("1".equals(dto.getEars())){
                                electroAudiometer.setAirLeft3000(dto.getResult());
                            }else if ("2".equals(dto.getEars())){
                                electroAudiometer.setAirRight3000(dto.getResult());
                            }
                            break;
                        case "4000":
                            if ("1".equals(dto.getEars())){
                                electroAudiometer.setAirLeft4000(dto.getResult());
                            }else if ("2".equals(dto.getEars())){
                                electroAudiometer.setAirRight4000(dto.getResult());
                            }
                            break;
                        case "6000":
                            if ("1".equals(dto.getEars())){
                                electroAudiometer.setAirLeft6000(dto.getResult());
                            }else if ("2".equals(dto.getEars())){
                                electroAudiometer.setAirRight6000(dto.getResult());
                            }
                            break;
                        case "8000":
                            if ("1".equals(dto.getEars())){
                                electroAudiometer.setAirLeft8000(dto.getResult());
                            }else if ("2".equals(dto.getEars())){
                                electroAudiometer.setAirRight8000(dto.getResult());
                            }
                            break;
                    }
                }else {
                    //骨导
                    switch (dto.getHz()) {
                        case "250":
                            if ("1".equals(dto.getEars())){
                                electroAudiometer.setBoneLeft250(dto.getResult());
                            }else if ("2".equals(dto.getEars())){
                                electroAudiometer.setBoneRight250(dto.getResult());
                            }
                            break;
                        case "500":
                            if ("1".equals(dto.getEars())){
                                electroAudiometer.setBoneLeft500(dto.getResult());
                            }else if ("2".equals(dto.getEars())){
                                electroAudiometer.setBoneRight500(dto.getResult());
                            }
                            break;
                        case "1000":
                            if ("1".equals(dto.getEars())){
                                electroAudiometer.setBoneLeft1000(dto.getResult());
                            }else if ("2".equals(dto.getEars())){
                                electroAudiometer.setBoneRight1000(dto.getResult());
                            }
                            break;
                        case "2000":
                            if ("1".equals(dto.getEars())){
                                electroAudiometer.setBoneLeft2000(dto.getResult());
                            }else if ("2".equals(dto.getEars())){
                                electroAudiometer.setBoneRight2000(dto.getResult());
                            }
                            break;
                        case "3000":
                            if ("1".equals(dto.getEars())){
                                electroAudiometer.setBoneLeft3000(dto.getResult());
                            }else if ("2".equals(dto.getEars())){
                                electroAudiometer.setBoneRight2000(dto.getResult());
                            }
                            break;
                        case "4000":
                            if ("1".equals(dto.getEars())){
                                electroAudiometer.setBoneLeft4000(dto.getResult());
                            }else if ("2".equals(dto.getEars())){
                                electroAudiometer.setBoneRight4000(dto.getResult());
                            }
                            break;
                        case "6000":
                            if ("1".equals(dto.getEars())){
                                electroAudiometer.setBoneLeft6000(dto.getResult());
                            }else if ("2".equals(dto.getEars())){
                                electroAudiometer.setBoneRight6000(dto.getResult());
                            }
                            break;
                        case "8000":
                            if ("1".equals(dto.getEars())){
                                electroAudiometer.setBoneLeft8000(dto.getResult());
                            }else if ("2".equals(dto.getEars())){
                                electroAudiometer.setBoneRight8000(dto.getResult());
                            }
                            break;
                    }
                }
            }
            electroAudiometers.add(electroAudiometer);
        }

        String str = electroAudiometerService.uploadEle(electroAudiometers);
        return str;
    }


}
