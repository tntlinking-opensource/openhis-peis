package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Monthtarget;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 销售月度计划(Monthtarget)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:22:07
 */
public interface MonthtargetMapper extends BaseMapper<Monthtarget> {

    /**
     * 分页查询[销售月度计划]列表
     *
     * @param page  分页参数
     * @param param Monthtarget查询参数
     * @return 分页数据
     */
    IPage<Monthtarget> getPage(PageParam<Monthtarget> page, @Param("param") Monthtarget param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Monthtarget getInfoById(@Param("id") String id);

}
