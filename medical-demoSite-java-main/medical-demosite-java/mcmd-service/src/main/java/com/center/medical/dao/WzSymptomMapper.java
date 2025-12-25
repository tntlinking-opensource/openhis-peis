package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.WzSymptom;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS问诊——症状(WzSymptom)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:24
 */
public interface WzSymptomMapper extends BaseMapper<WzSymptom> {

    /**
     * 分页查询[KS问诊——症状]列表
     *
     * @param page  分页参数
     * @param param WzSymptom查询参数
     * @return 分页数据
     */
    IPage<WzSymptom> getList(PageParam<WzSymptom> page, @Param("param") WzSymptom param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    WzSymptom getInfoById(@Param("id") String id);

}
