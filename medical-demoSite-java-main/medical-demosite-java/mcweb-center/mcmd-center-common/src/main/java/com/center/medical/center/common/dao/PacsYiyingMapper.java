package com.center.medical.center.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.PacsResult;
import com.center.medical.center.common.bean.dto.PacsYiyingDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 易影
 * @author xhp
 * @since 2025-02-25 15:26
 */
@Repository
public interface PacsYiyingMapper extends BaseMapper<PacsResult> {

    /**
     * 支持长号+短号
     * 有时候技师老师做的时候，没短条码就会扫长条码。设备可能会传长码或短码给易影。易影表示他们改不了体检号。
     * @param patientcode8
     * @param patientcode
     * @return
     */
    List<PacsYiyingDto> selectList(@Param("patientcode8") String patientcode8,@Param("patientcode") String patientcode);
}
