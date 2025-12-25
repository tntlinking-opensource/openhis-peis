package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Customercommunicate;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 客户沟通表(Customercommunicate)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:18:04
 */
public interface CustomercommunicateMapper extends BaseMapper<Customercommunicate> {

    /**
     * 分页查询[客户沟通表]列表
     *
     * @param page  分页参数
     * @param param Customercommunicate查询参数
     * @return 分页数据
     */
    IPage<Customercommunicate> getPage(PageParam<Customercommunicate> page, @Param("param") Customercommunicate param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Customercommunicate getInfoById(@Param("id") String id);

}
