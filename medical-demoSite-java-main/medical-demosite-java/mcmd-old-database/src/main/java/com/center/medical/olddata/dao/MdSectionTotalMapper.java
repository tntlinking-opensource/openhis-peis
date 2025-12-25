package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdSectionTotal;
import org.apache.ibatis.annotations.Param;

/**
 * ZJ总检主表(MdSectionTotal)数据库访问层
 *
 * @author ay
 * @since 2023-11-10 14:27:20
 */
public interface MdSectionTotalMapper extends BaseMapper<MdSectionTotal> {

    /**
     * 分页查询[ZJ总检主表]列表
     *
     * @param page  分页参数
     * @param param MdSectionTotal查询参数
     * @return 分页数据
     */
    IPage<MdSectionTotal> getPage(PageParam<MdSectionTotal> page, @Param("param") MdSectionTotal param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdSectionTotal getInfoById(@Param("id") String id);

}
