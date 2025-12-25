package com.center.medical.center.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * QT体检者表(MdPeispatient)数据库访问层
 *
 * @author ay
 * @since 2023-09-07 14:39:48
 */
public interface AdiconMapper extends BaseMapper<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param MdPeispatient查询参数
     * @return 分页数据
     */
    IPage<Peispatient> getPage(PageParam<Peispatient> page, @Param("param") Peispatient param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatient getInfoById(@Param("id") String id);

}
