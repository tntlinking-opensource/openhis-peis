package com.center.medical.sellcrm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Customercommunicate;
import com.center.medical.sellcrm.bean.param.CustomercommunicateParam;
import org.apache.ibatis.annotations.Param;

/**
 * 客户沟通表(Customercommunicate)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-21 09:02:49
 */
public interface CustomercommunicateMapper extends BaseMapper<Customercommunicate> {

    /**
     * 分页查询[客户沟通表]列表
     *
     * @param page  分页参数
     * @param customercommunicateParam Customercommunicate查询参数
     * @return 分页数据
     */
    IPage<Customercommunicate> getPage(PageParam<Customercommunicate> page, @Param("param") CustomercommunicateParam customercommunicateParam);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Customercommunicate getInfoById(@Param("id") String id);

}
