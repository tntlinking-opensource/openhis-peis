package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdConclusionAndFzx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC结伦词和分中心关联表(MdConclusionAndFzx)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:18
 */
public interface MdConclusionAndFzxMapper extends BaseMapper<MdConclusionAndFzx> {

    /**
     * 分页查询[JC结伦词和分中心关联表]列表
     *
     * @param page  分页参数
     * @param param MdConclusionAndFzx查询参数
     * @return 分页数据
     */
    IPage<MdConclusionAndFzx> getPage(PageParam<MdConclusionAndFzx> page, @Param("param") MdConclusionAndFzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdConclusionAndFzx getInfoById(@Param("id") String id);

}
