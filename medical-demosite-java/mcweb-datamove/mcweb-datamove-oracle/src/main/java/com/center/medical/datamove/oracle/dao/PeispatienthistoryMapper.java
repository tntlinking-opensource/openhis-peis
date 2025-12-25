package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Peispatienthistory;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (Peispatienthistory)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:23:30
 */
public interface PeispatienthistoryMapper extends BaseMapper<Peispatienthistory> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param Peispatienthistory查询参数
     * @return 分页数据
     */
    IPage<Peispatienthistory> getPage(PageParam<Peispatienthistory> page, @Param("param") Peispatienthistory param);


}
