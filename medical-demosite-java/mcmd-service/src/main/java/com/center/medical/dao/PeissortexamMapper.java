package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Peissortexam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 排检(Peissortexam)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:28
 */
public interface PeissortexamMapper extends BaseMapper<Peissortexam> {

    /**
     * 分页查询[排检]列表
     *
     * @param page  分页参数
     * @param param Peissortexam查询参数
     * @return 分页数据
     */
    IPage<Peissortexam> getList(PageParam<Peissortexam> page, @Param("param") Peissortexam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Peissortexam getInfoById(@Param("id") String id);

}
