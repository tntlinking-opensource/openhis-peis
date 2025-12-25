package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.OutsidePictrue;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS外送项目图片结果(OutsidePictrue)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:22:35
 */
public interface OutsidePictrueMapper extends BaseMapper<OutsidePictrue> {

    /**
     * 分页查询[KS外送项目图片结果]列表
     *
     * @param page  分页参数
     * @param param OutsidePictrue查询参数
     * @return 分页数据
     */
    IPage<OutsidePictrue> getPage(PageParam<OutsidePictrue> page, @Param("param") OutsidePictrue param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OutsidePictrue getInfoById(@Param("id") String id);

}
