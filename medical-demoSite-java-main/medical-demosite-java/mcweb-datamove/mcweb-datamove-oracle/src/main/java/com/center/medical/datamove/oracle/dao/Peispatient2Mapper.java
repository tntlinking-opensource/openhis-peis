package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Peispatient2;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * QT体检者表(Peispatient2)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:23:10
 */
public interface Peispatient2Mapper extends BaseMapper<Peispatient2> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient2查询参数
     * @return 分页数据
     */
    IPage<Peispatient2> getPage(PageParam<Peispatient2> page, @Param("param") Peispatient2 param);


}
