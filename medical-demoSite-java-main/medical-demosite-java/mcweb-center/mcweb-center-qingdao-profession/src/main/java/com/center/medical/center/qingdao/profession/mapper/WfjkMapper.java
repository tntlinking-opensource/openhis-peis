package com.center.medical.center.qingdao.profession.mapper;

import com.center.medical.center.qingdao.profession.entity.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * QT体检者表(Peispatient)数据库访问层
 *
 * @author ay
 * @since 2024-05-07 15:54:16
 */
@Mapper
@Component
public interface WfjkMapper {

    /**
     * 获取没被删除的危害因素
     * @return
     */
    List<HarmDto> getHarmList();


}
