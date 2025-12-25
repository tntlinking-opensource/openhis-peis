package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdGroupReviewNotice;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 批量职业健康检查复查通知书(MdGroupReviewNotice)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:20
 */
public interface MdGroupReviewNoticeMapper extends BaseMapper<MdGroupReviewNotice> {

    /**
     * 分页查询[批量职业健康检查复查通知书]列表
     *
     * @param page  分页参数
     * @param param MdGroupReviewNotice查询参数
     * @return 分页数据
     */
    IPage<MdGroupReviewNotice> getPage(PageParam<MdGroupReviewNotice> page, @Param("param") MdGroupReviewNotice param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdGroupReviewNotice getInfoById(@Param("id") String id);

}
