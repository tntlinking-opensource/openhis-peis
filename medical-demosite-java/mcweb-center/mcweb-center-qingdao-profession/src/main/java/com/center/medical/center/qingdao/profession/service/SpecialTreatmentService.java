package com.center.medical.center.qingdao.profession.service;

import com.center.medical.center.qingdao.profession.entity.dto.FeeItemDTO;
import com.center.medical.center.qingdao.profession.entity.dto.RDataDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * 特殊情况处理
 *
 * 血常规23项-血常规职业问题，职业总检时复查项目只能从最小套餐中选择，所以复查不用处理
 */
public interface SpecialTreatmentService {
    /**
     * 将收费项目id转为基础套餐中的收费项目id
     * @param feeItemDTOS
     */
    void treatItemId(List<FeeItemDTO> feeItemDTOS);

    /**
     * 处理血常规职业、血常规23项
     * @param itemId 收费项目id
     * @return
     */
    List<String> getItemIds(String itemId);

    String treatSql();

    /**
     * 健康体检表和危害因素表，当这两个表里同一个体检人涉及的危害因素有多个粉尘时，只传任意一个粉尘，其他粉尘不传。（重点）
     * @param jhysCodes
     * @return
     */
    ArrayList<String> treatJhys(ArrayList<String> jhysCodes);

    String[] treatJhys(String[] jhysIds);

    void treatDefaultRange(List<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> dtos);

}
