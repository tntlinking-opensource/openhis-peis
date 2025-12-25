package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.CrmSellprintteams;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 销售打印项目分类设置表(CrmSellprintteams)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:01
 */
public interface CrmSellprintteamsMapper extends BaseMapper<CrmSellprintteams> {

    /**
     * 分页查询[销售打印项目分类设置表]列表
     *
     * @param page  分页参数
     * @param param CrmSellprintteams查询参数
     * @return 分页数据
     */
    IPage<CrmSellprintteams> getPage(PageParam<CrmSellprintteams> page, @Param("param") CrmSellprintteams param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CrmSellprintteams getInfoById(@Param("id") String id);

}
