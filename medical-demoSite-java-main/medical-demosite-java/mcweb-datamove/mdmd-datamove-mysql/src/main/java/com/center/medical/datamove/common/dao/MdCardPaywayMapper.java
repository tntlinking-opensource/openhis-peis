package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdCardPayway;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 卡办理收款方式表(MdCardPayway)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:14
 */
public interface MdCardPaywayMapper extends BaseMapper<MdCardPayway> {

    /**
     * 分页查询[卡办理收款方式表]列表
     *
     * @param page  分页参数
     * @param param MdCardPayway查询参数
     * @return 分页数据
     */
    IPage<MdCardPayway> getPage(PageParam<MdCardPayway> page, @Param("param") MdCardPayway param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdCardPayway getInfoById(@Param("id") String id);

}
