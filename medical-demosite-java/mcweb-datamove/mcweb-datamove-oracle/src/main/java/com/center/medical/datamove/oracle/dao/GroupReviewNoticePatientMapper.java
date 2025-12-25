package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.GroupReviewNoticePatient;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 批量复查通知人员(GroupReviewNoticePatient)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:19:07
 */
public interface GroupReviewNoticePatientMapper extends BaseMapper<GroupReviewNoticePatient> {

    /**
     * 分页查询[批量复查通知人员]列表
     *
     * @param page  分页参数
     * @param param GroupReviewNoticePatient查询参数
     * @return 分页数据
     */
    IPage<GroupReviewNoticePatient> getPage(PageParam<GroupReviewNoticePatient> page, @Param("param") GroupReviewNoticePatient param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    GroupReviewNoticePatient getInfoById(@Param("id") String id);

}
