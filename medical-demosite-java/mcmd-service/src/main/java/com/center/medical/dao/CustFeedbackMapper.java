package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.CustFeedback;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 故障反馈(CustFeedback)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:16
 */
public interface CustFeedbackMapper extends BaseMapper<CustFeedback> {

    /**
     * 分页查询[故障反馈]列表
     *
     * @param page  分页参数
     * @param param CustFeedback查询参数
     * @return 分页数据
     */
    IPage<CustFeedback> getList(PageParam<CustFeedback> page, @Param("param") CustFeedback param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    CustFeedback getInfoById(@Param("id") String id);

}
