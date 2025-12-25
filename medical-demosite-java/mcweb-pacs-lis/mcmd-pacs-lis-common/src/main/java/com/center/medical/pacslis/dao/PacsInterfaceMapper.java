package com.center.medical.pacslis.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.PacsResult;
import com.center.medical.pacslis.bean.dto.PacsItemDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xhp
 * @since 2023-03-07 10:38
 */
@Repository
public interface PacsInterfaceMapper extends BaseMapper<PacsResult> {
    /**
     * 按体检号查询需要获取结果的pacs收费项目信息
     * @param patientcode
     * @return
     */
    List<PacsItemDto> selectItemList(@Param("patientcode") String patientcode,@Param("deptNos") List<String> deptNos);

    /**
     * 获取所有尚未获取pacs结果的体检号
     * @return
     */
    List<String> receivePacsDataUser(@Param("deptNos") List<String> deptNos,@Param("daysAgo") Integer daysAgo);
}
