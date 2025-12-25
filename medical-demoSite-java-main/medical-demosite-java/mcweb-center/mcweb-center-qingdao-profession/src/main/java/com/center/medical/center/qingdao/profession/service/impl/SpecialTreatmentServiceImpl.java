package com.center.medical.center.qingdao.profession.service.impl;

import cn.hutool.core.util.StrUtil;
import com.center.medical.center.qingdao.profession.command.DictionaryCache;
import com.center.medical.center.qingdao.profession.entity.dto.BaseDictionaryDto;
import com.center.medical.center.qingdao.profession.entity.dto.FeeItemDTO;
import com.center.medical.center.qingdao.profession.entity.dto.RDataDTO;
import com.center.medical.center.qingdao.profession.service.SpecialTreatmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpecialTreatmentServiceImpl implements SpecialTreatmentService {
    private final DictionaryCache dictionaryCache;

    @Override
    public void treatItemId(List<FeeItemDTO> feeItemDTOS){
        for(FeeItemDTO feeItemDTO:feeItemDTOS){
            //血常规23项转血常规职业
            if("61".equals(feeItemDTO.getItemId())){
                feeItemDTO.setItemId("Z020");
            }
        }
    }

    @Override
    public List<String> getItemIds(String itemId){
        List<String> list=new ArrayList<>();
        list.add(itemId);
        if("Z020".equals(itemId)){
            list.add("61");
        }
        return list;
    }

    @Override
    public  String treatSql(){
        return "AND (" +
                "CEI.ITEM_ID = PI.ID_EXAMFEEITEM " +
                "OR " +
                "(CEI.ITEM_ID='Z020' AND PI.ID_EXAMFEEITEM='61')" +
                ")"
                ;
    }

    /**
     * 当劳动者的体检危害
     * 因素中包含以下粉尘中
     * 的多个时，只能选择一种
     * 最主要粉尘上传
     */
    public final static Set<String> DUST_CODES=new HashSet<>(Arrays.asList(
            "1001","1002","1004","1005","1006","1007","1011","1012","1013","1014","1019","1021","1025","1027","1029","1030","1031","1033","1034","1035","1036"
            ,"1037","1038","1039","1042","1043","1044","1045","1046","1049","1050","1051","1052","1053","1056","1099"
    ));

    @Override
    public ArrayList<String> treatJhys(ArrayList<String> jhysCodes){
        ArrayList<String> result=new ArrayList<>();
        boolean hasDust=false;
        for(String code:jhysCodes){
            //2024.6.17 多个粉尘类危害因素，只可以选取其中一个危害因素编码上传（1001--1099选取其中一个进行上传）。
//            if(DUST_CODES.contains(code)){
            if(code.startsWith("10")&&code.length()==4){
                if(!hasDust){
                    hasDust=true;
                    result.add(code);
                }else{
                    continue;
                }
            }else{
                result.add(code);
            }
        }

        return result;
    }

    @Override
    public String[] treatJhys(String[] jhysIds){
        boolean hasDust=false;
        ArrayList<String> result=new ArrayList<>();
        for (String jhysId : jhysIds) {
            BaseDictionaryDto hazards = dictionaryCache.getHazards(jhysId);
            if (ObjectUtils.isEmpty(hazards)) {
                continue;
            }
            String code = hazards.getDictionaryCode();
//            if(DUST_CODES.contains(code)){
            if(code.startsWith("10")&&code.length()==4){
                if(!hasDust){
                    hasDust=true;
                    result.add(jhysId);
                }else{
                    continue;
                }
            }else{
                result.add(jhysId);
            }
        }
        return result.toArray(new String[result.size()]);
    }



    /**
     * 检查结果为数值型时最大值和最小值不能存在空值。
     * 检查结果为字符型时最大值和最小值传空
     * @param dtos
     */
    @Override
    public void treatDefaultRange(List<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> dtos){
        for(RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO dto:dtos){
            if("01".equals(dto.getExamResultType())){
                if(StrUtil.isEmpty(dto.getReferenceRangeMax())){
                    dto.setReferenceRangeMax("0");
                }
                if(StrUtil.isEmpty(dto.getReferenceRangeMin())){
                    dto.setReferenceRangeMin("0");
                }
            }

            String code=dto.getExamItemCode();
            if("3041700".equals(code)
                    ||"3041800".equals(code)
                    ||"3041900".equals(code)
                    ||"3042000".equals(code)
            ){
                dto.setReferenceRangeMin("-40");
                dto.setReferenceRangeMax("150");
            }
        }
    }

}
