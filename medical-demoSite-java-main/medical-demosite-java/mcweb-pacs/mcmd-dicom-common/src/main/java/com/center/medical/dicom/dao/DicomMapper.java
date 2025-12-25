package com.center.medical.dicom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Attachment;
import com.center.medical.dicom.bean.dto.DicomFeeitemDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xhp
 * @since 2023-04-26 9:55
 */
@Repository
public interface DicomMapper extends BaseMapper<Attachment> {

    List<DicomFeeitemDto> selectDicomFeeitemList(@Param("patientcode") String patientcode, @Param("ennames") List<String> ennames, @Param("jklx") String jklx);

    List<DicomFeeitemDto> getInfoById(@Param("feeItemId") String feeItemId);
}
