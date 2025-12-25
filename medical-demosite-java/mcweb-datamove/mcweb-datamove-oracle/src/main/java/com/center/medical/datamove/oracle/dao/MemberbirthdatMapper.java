package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Memberbirthdat;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KF生日回访表(Memberbirthdat)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:22:04
 */
public interface MemberbirthdatMapper extends BaseMapper<Memberbirthdat> {

    /**
     * 分页查询[KF生日回访表]列表
     *
     * @param page  分页参数
     * @param param Memberbirthdat查询参数
     * @return 分页数据
     */
    IPage<Memberbirthdat> getPage(PageParam<Memberbirthdat> page, @Param("param") Memberbirthdat param);


}
