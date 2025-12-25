package com.center.medical.center.qingdao.profession.repository;


import com.center.medical.center.qingdao.profession.entity.MissingFeeItemDTO;
import com.center.medical.center.qingdao.profession.entity.dto.CheckItemDTO;
import com.center.medical.center.qingdao.profession.entity.dto.FeeItemDTO;
import com.center.medical.center.qingdao.profession.entity.dto.NeedFeeItemDTO;
import com.center.medical.center.qingdao.profession.entity.dto.OrdinaryCheckItemDto;

import java.util.List;
import java.util.Set;

public interface ItemQueryRepository {
    List<FeeItemDTO> queryFeeItem(String patientCode);

    List<CheckItemDTO> queryCheckItemDTO(String patientCode, String itemId);

    List<OrdinaryCheckItemDto> queryOrdinaryCheckItem(String patientCode);

    List<NeedFeeItemDTO> queryNeedFeeItem(String medicaltype, String harmIds);

    List<CheckItemDTO> queryNotCheckItemDTO(List<String> itemId);

    List<MissingFeeItemDTO> queryMissingFeeItemsDTOS(String patientcode, List<String> list);

    Set<String> queryNeedFeeItemMust(Set<String> comboIds);

    List<FeeItemDTO> queryFeeItem2(String patientcode);
}
