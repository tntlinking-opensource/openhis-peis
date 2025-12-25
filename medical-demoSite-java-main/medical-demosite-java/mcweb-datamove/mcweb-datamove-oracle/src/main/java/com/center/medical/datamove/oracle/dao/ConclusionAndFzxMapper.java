package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.ConclusionAndFzx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC结伦词和分中心关联表(ConclusionAndFzx)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:17:46
 */
public interface ConclusionAndFzxMapper extends BaseMapper<ConclusionAndFzx> {

    /**
     * 分页查询[JC结伦词和分中心关联表]列表
     *
     * @param page  分页参数
     * @param param ConclusionAndFzx查询参数
     * @return 分页数据
     */
    IPage<ConclusionAndFzx> getPage(PageParam<ConclusionAndFzx> page, @Param("param") ConclusionAndFzx param);


}
