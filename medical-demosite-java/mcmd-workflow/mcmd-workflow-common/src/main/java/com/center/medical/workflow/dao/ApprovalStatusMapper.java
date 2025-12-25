package com.center.medical.workflow.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.workflow.bean.param.ApprovalStatusParam;
import com.center.medical.workflow.bean.vo.ApprovalStatusVo;
import org.apache.ibatis.annotations.Param;

/**
 * QT体检者表(Peispatient)数据库访问层
 *
 * @author ay
 * @since 2024-03-29 13:37:41
 */
public interface ApprovalStatusMapper extends BaseMapper<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    IPage<ApprovalStatusVo> getPage(PageParam<ApprovalStatusVo> page, @Param("param") ApprovalStatusParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatient getInfoById(@Param("id") String id);

}
