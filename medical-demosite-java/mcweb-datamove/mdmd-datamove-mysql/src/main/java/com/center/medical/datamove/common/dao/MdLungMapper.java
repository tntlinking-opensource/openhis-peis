package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdLung;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS肺功能(MdLung)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:25
 */
public interface MdLungMapper extends BaseMapper<MdLung> {

    /**
     * 分页查询[KS肺功能]列表
     *
     * @param page  分页参数
     * @param param MdLung查询参数
     * @return 分页数据
     */
    IPage<MdLung> getPage(PageParam<MdLung> page, @Param("param") MdLung param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdLung getInfoById(@Param("id") String id);

}
