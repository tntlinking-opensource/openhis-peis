package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdCustFeedback;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 故障反馈(MdCustFeedback)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:20
 */
public interface MdCustFeedbackMapper extends BaseMapper<MdCustFeedback> {

    /**
     * 分页查询[故障反馈]列表
     *
     * @param page  分页参数
     * @param param MdCustFeedback查询参数
     * @return 分页数据
     */
    IPage<MdCustFeedback> getPage(PageParam<MdCustFeedback> page, @Param("param") MdCustFeedback param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdCustFeedback getInfoById(@Param("id") String id);

}
