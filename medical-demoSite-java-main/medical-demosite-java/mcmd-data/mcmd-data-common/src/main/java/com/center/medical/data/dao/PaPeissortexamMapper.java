package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.PaPeissortexam;
import com.center.medical.data.bean.param.PaPeiEParam;
import org.apache.ibatis.annotations.Param;

/**
 * 平安软件-排检(PaPeissortexam)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 11:26:54
 */
public interface PaPeissortexamMapper extends BaseMapper<PaPeissortexam> {

    /**
     * 分页查询[平安软件-排检]列表
     *
     * @param page  分页参数
     * @param param PaPeissortexam查询参数
     * @return 分页数据
     */
    IPage<PaPeissortexam> getList(PageParam<PaPeissortexam> page, @Param("param") PaPeiEParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id PaPeissortexam查询参数
     * @return 分页数据
     */
    PaPeissortexam getInfoById(@Param("id") String id);

}
