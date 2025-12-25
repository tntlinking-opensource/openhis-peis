package com.center.medical.pacslis.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Peispatientexamitem;
import com.center.medical.pacslis.bean.dto.LisExamDto;
import com.center.medical.pacslis.bean.dto.LisItemDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xhp
 * @since 2023-02-20 16:16
 */
public interface LisInterfaceMapper extends BaseMapper<Peispatientexamitem> {
    /**
     * 查询所有需要获取的lis收费项目
     * @param patientcode 体检号
     * @param depId 科室id
     * @return
     */
    List<LisItemDto> selectItemList(@Param("patientcode") String patientcode,@Param("depId") String depId);

    /**
     * 查询收费项目下的所有检查项目
     * @param itemId 收费项目id
     * @return
     */
    List<LisExamDto> selectExamList(@Param("itemId") String itemId);
}
