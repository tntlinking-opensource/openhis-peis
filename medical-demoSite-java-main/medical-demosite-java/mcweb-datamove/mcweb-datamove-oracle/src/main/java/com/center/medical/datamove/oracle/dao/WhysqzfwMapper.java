package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Whysqzfw;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC危害因素取值范围(Whysqzfw)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:25:55
 */
public interface WhysqzfwMapper extends BaseMapper<Whysqzfw> {

    /**
     * 分页查询[JC危害因素取值范围]列表
     *
     * @param page  分页参数
     * @param param Whysqzfw查询参数
     * @return 分页数据
     */
    IPage<Whysqzfw> getPage(PageParam<Whysqzfw> page, @Param("param") Whysqzfw param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Whysqzfw getInfoById(@Param("id") String id);

}
