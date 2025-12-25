package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdFxHarm;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 职业团检分析-危害因素(MdFxHarm)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:18
 */
public interface MdFxHarmMapper extends BaseMapper<MdFxHarm> {

    /**
     * 分页查询[职业团检分析-危害因素]列表
     *
     * @param page  分页参数
     * @param param MdFxHarm查询参数
     * @return 分页数据
     */
    IPage<MdFxHarm> getPage(PageParam<MdFxHarm> page, @Param("param") MdFxHarm param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdFxHarm getInfoById(@Param("id") String id);

}
