package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdBasconclusionNew;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 总检结论词(MdBasconclusionNew)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:08
 */
public interface MdBasconclusionNewMapper extends BaseMapper<MdBasconclusionNew> {

    /**
     * 分页查询[总检结论词]列表
     *
     * @param page  分页参数
     * @param param MdBasconclusionNew查询参数
     * @return 分页数据
     */
    IPage<MdBasconclusionNew> getPage(PageParam<MdBasconclusionNew> page, @Param("param") MdBasconclusionNew param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdBasconclusionNew getInfoById(@Param("id") String id);

}
