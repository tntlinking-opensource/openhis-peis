package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdWzZybs;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS问诊——职业病史(MdWzZybs)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:26
 */
public interface MdWzZybsMapper extends BaseMapper<MdWzZybs> {

    /**
     * 分页查询[KS问诊——职业病史]列表
     *
     * @param page  分页参数
     * @param param MdWzZybs查询参数
     * @return 分页数据
     */
    IPage<MdWzZybs> getPage(PageParam<MdWzZybs> page, @Param("param") MdWzZybs param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdWzZybs getInfoById(@Param("id") String id);

}
