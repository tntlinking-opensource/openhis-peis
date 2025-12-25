package com.center.medical.report.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.report.bean.model.PacsResultThird;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 易影报告回传(PacsResultThird)数据库访问层
 *
 * @author makejava
 * @since 2025-08-14 10:15:32
 */
public interface PacsResultThirdMapper extends BaseMapper<PacsResultThird> {

    /**
     * 按体检号获取易影云胶片二维码内容网址
     * 一个体检号只有一个二维码
     * @param patientcode
     * @param patientcode8
     * @return
     */
    List<String> getQrCodeContentByPatientcode(@Param("patientcode")String patientcode, @Param("patientcode8") String patientcode8);
}
