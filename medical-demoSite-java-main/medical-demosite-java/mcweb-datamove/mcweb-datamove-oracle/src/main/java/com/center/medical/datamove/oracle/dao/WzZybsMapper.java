package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.WzZybs;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS问诊——职业病史(WzZybs)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:30:52
 */
public interface WzZybsMapper extends BaseMapper<WzZybs> {

    /**
     * 分页查询[KS问诊——职业病史]列表
     *
     * @param page  分页参数
     * @param param WzZybs查询参数
     * @return 分页数据
     */
    IPage<WzZybs> getPage(PageParam<WzZybs> page, @Param("param") WzZybs param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    WzZybs getInfoById(@Param("id") String id);

}
