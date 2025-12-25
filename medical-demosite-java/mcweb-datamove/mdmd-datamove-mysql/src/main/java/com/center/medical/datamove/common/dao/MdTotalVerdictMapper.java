package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdTotalVerdict;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * ZJ总检结论词表(MdTotalVerdict)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:49:10
 */
public interface MdTotalVerdictMapper extends BaseMapper<MdTotalVerdict> {

    /**
     * 分页查询[ZJ总检结论词表]列表
     *
     * @param page  分页参数
     * @param param MdTotalVerdict查询参数
     * @return 分页数据
     */
    IPage<MdTotalVerdict> getPage(PageParam<MdTotalVerdict> page, @Param("param") MdTotalVerdict param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdTotalVerdict getInfoById(@Param("id") String id);

}
