package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.KdPayway;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 金蝶中的收银方式（kingdeepayway）(KdPayway)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:04
 */
public interface KdPaywayMapper extends BaseMapper<KdPayway> {

    /**
     * 分页查询[金蝶中的收银方式（kingdeepayway）]列表
     *
     * @param page  分页参数
     * @param param KdPayway查询参数
     * @return 分页数据
     */
    IPage<KdPayway> getPage(PageParam<KdPayway> page, @Param("param") KdPayway param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键accountNo
     * @return 详情信息
     */
    KdPayway getInfoById(@Param("id") String id);

}
