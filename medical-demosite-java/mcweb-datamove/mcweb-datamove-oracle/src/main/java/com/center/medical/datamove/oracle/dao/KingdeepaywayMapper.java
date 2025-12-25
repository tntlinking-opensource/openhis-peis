package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Kingdeepayway;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (Kingdeepayway)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:19:27
 */
public interface KingdeepaywayMapper extends BaseMapper<Kingdeepayway> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param Kingdeepayway查询参数
     * @return 分页数据
     */
    IPage<Kingdeepayway> getPage(PageParam<Kingdeepayway> page, @Param("param") Kingdeepayway param);


}
