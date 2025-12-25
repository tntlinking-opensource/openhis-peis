package com.center.medical.pacs.open;

import com.alibaba.fastjson.JSON;
import com.center.medical.abteilung.bean.model.ElectroAudiometer;
import com.center.medical.abteilung.service.ElectroAudiometerService;
import com.center.medical.pacs.bean.dto.EleDataDto;
import com.center.medical.pacs.bean.dto.HOUploadEleDto;
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
 * 华欧电测听上传结果
 */
@Slf4j
@RestController
@RequestMapping("/lan/hoUploadEle")
@Api(tags = "华欧电测听上传结果")
@RequiredArgsConstructor
@Validated
public class HOUploadEleController {
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
        List<HOUploadEleDto> list = JSON.parseArray(jsonStr, HOUploadEleDto.class);
        List<ElectroAudiometer> electroAudiometers = new ArrayList<>();
        for (HOUploadEleDto param : list) {
            ElectroAudiometer electroAudiometer = new ElectroAudiometer();
            //设置体检号
            electroAudiometer.setPatientcode(param.getPatientcode());
            electroAudiometer.setDepId(param.getKsId());
            //设置检查时间
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            electroAudiometer.setCreatedate(format.parse(param.getCreatDate()));
            List<EleDataDto> numResultsList = param.getEleDataDtoList();
            for (EleDataDto dto : numResultsList) {
                if ("Air".equals(dto.getType())){
                    //气导
                    switch (dto.getHz()) {
                        case "F125":
                            if ("L".equals(dto.getSide())){
                                electroAudiometer.setAirLeft125(dto.getValue());
                            }else if ("R".equals(dto.getSide())){
                                electroAudiometer.setAirRight125(dto.getValue());
                            }
                            break;
                        case "F250":
                            if ("L".equals(dto.getSide())){
                                electroAudiometer.setAirLeft250(dto.getValue());
                            }else if ("R".equals(dto.getSide())){
                                electroAudiometer.setAirRight250(dto.getValue());
                            }
                            break;
                        case "F500":
                            if ("L".equals(dto.getSide())){
                                electroAudiometer.setAirLeft500(dto.getValue());
                            }else if ("R".equals(dto.getSide())){
                                electroAudiometer.setAirRight500(dto.getValue());
                            }
                            break;
                        case "F1000":
                            if ("L".equals(dto.getSide())){
                                electroAudiometer.setAirLeft1000(dto.getValue());
                            }else if ("R".equals(dto.getSide())){
                                electroAudiometer.setAirRight1000(dto.getValue());
                            }
                            break;
                        case "F2000":
                            if ("L".equals(dto.getSide())){
                                electroAudiometer.setAirLeft2000(dto.getValue());
                            }else if ("R".equals(dto.getSide())){
                                electroAudiometer.setAirRight2000(dto.getValue());
                            }
                            break;
                        case "F3000":
                            if ("L".equals(dto.getSide())){
                                electroAudiometer.setAirLeft3000(dto.getValue());
                            }else if ("R".equals(dto.getSide())){
                                electroAudiometer.setAirRight3000(dto.getValue());
                            }
                            break;
                        case "F4000":
                            if ("L".equals(dto.getSide())){
                                electroAudiometer.setAirLeft4000(dto.getValue());
                            }else if ("R".equals(dto.getSide())){
                                electroAudiometer.setAirRight4000(dto.getValue());
                            }
                            break;
                        case "F6000":
                            if ("L".equals(dto.getSide())){
                                electroAudiometer.setAirLeft6000(dto.getValue());
                            }else if ("R".equals(dto.getSide())){
                                electroAudiometer.setAirRight6000(dto.getValue());
                            }
                            break;
                        case "F8000":
                            if ("L".equals(dto.getSide())){
                                electroAudiometer.setAirLeft8000(dto.getValue());
                            }else if ("R".equals(dto.getSide())){
                                electroAudiometer.setAirRight8000(dto.getValue());
                            }
                            break;
                    }
                }else {
                    //骨导
                    switch (dto.getHz()) {
                        case "F250":
                            if ("L".equals(dto.getSide())){
                                electroAudiometer.setBoneLeft250(dto.getValue());
                            }else if ("R".equals(dto.getSide())){
                                electroAudiometer.setBoneRight250(dto.getValue());
                            }
                            break;
                        case "F500":
                            if ("L".equals(dto.getSide())){
                                electroAudiometer.setBoneLeft500(dto.getValue());
                            }else if ("R".equals(dto.getSide())){
                                electroAudiometer.setBoneRight500(dto.getValue());
                            }
                            break;
                        case "F1000":
                            if ("L".equals(dto.getSide())){
                                electroAudiometer.setBoneLeft1000(dto.getValue());
                            }else if ("R".equals(dto.getSide())){
                                electroAudiometer.setBoneRight1000(dto.getValue());
                            }
                            break;
                        case "F2000":
                            if ("L".equals(dto.getSide())){
                                electroAudiometer.setBoneLeft2000(dto.getValue());
                            }else if ("R".equals(dto.getSide())){
                                electroAudiometer.setBoneRight2000(dto.getValue());
                            }
                            break;
                        case "F3000":
                            if ("L".equals(dto.getSide())){
                                electroAudiometer.setBoneLeft3000(dto.getValue());
                            }else if ("R".equals(dto.getSide())){
                                electroAudiometer.setBoneRight3000(dto.getValue());
                            }
                            break;
                        case "F4000":
                            if ("L".equals(dto.getSide())){
                                electroAudiometer.setBoneLeft4000(dto.getValue());
                            }else if ("R".equals(dto.getSide())){
                                electroAudiometer.setBoneRight4000(dto.getValue());
                            }
                            break;
                        case "F6000":
                            if ("L".equals(dto.getSide())){
                                electroAudiometer.setBoneLeft6000(dto.getValue());
                            }else if ("R".equals(dto.getSide())){
                                electroAudiometer.setBoneRight6000(dto.getValue());
                            }
                            break;
                        case "F8000":
                            if ("L".equals(dto.getSide())){
                                electroAudiometer.setBoneLeft8000(dto.getValue());
                            }else if ("R".equals(dto.getSide())){
                                electroAudiometer.setBoneRight8000(dto.getValue());
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
