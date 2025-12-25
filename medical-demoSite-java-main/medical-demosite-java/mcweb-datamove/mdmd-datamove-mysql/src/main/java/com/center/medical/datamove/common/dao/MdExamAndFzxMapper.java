package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdExamAndFzx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 检查项目和分中心关联表(MdExamAndFzx)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:16
 */
public interface MdExamAndFzxMapper extends BaseMapper<MdExamAndFzx> {

    /**
     * 分页查询[检查项目和分中心关联表]列表
     *
     * @param page  分页参数
     * @param param MdExamAndFzx查询参数
     * @return 分页数据
     */
    IPage<MdExamAndFzx> getPage(PageParam<MdExamAndFzx> page, @Param("param") MdExamAndFzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdExamAndFzx getInfoById(@Param("id") String id);

}
