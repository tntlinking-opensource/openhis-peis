package com.center.medical.center.qingdao.profession.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.center.qingdao.profession.entity.dto.FeeItemDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ItemQueryMapper {
    List<FeeItemDTO> queryFeeItem(IPage<Object> iPage, @Param("patientCode") String patientCode);

    Long queryFeeItemCount(String patientCode);
}
