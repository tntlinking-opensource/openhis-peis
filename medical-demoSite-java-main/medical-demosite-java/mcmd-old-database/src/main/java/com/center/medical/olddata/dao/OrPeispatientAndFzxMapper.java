package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrPeispatientAndFzx;
import org.apache.ibatis.annotations.Param;

/**
 * (PeispatientAndFzx)数据库访问层
 *
 * @author ay
 * @since 2024-04-17 10:37:32
 */
public interface OrPeispatientAndFzxMapper extends BaseMapper<OrPeispatientAndFzx> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param PeispatientAndFzx查询参数
     * @return 分页数据
     */
    IPage<OrPeispatientAndFzx> getPage(PageParam<OrPeispatientAndFzx> page, @Param("param") OrPeispatientAndFzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrPeispatientAndFzx getInfoById(@Param("id") String id);

}
