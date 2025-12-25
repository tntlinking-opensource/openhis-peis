package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.BaseIndustry;
import com.center.medical.data.bean.vo.IndusDataVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 国民经济行业分类GB/T 4754—2017(BaseIndustry)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:51
 */
public interface BaseIndustryMapper extends BaseMapper<BaseIndustry> {

    /**
     * 分页查询[国民经济行业分类GB/T 4754—2017]列表
     *
     * @param page  分页参数
     * @param param BaseIndustry查询参数
     * @return 分页数据
     */
    IPage<BaseIndustry> getList(PageParam<BaseIndustry> page, @Param("param") BaseIndustry param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    BaseIndustry getInfoById(@Param("id") String id);

    /**
     * 获取行业类别代码
     * @param indusTypeCode
     * @param level
     * @return
     */
    List<IndusDataVo> getIndusData(@Param("indusTypeCode")String indusTypeCode,@Param("level") String level);
}
