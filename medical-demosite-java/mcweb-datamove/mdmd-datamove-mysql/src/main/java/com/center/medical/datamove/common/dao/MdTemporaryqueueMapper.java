package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdTemporaryqueue;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * temporaryqueue(MdTemporaryqueue)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:56
 */
public interface MdTemporaryqueueMapper extends BaseMapper<MdTemporaryqueue> {

    /**
     * 分页查询[temporaryqueue]列表
     *
     * @param page  分页参数
     * @param param MdTemporaryqueue查询参数
     * @return 分页数据
     */
    IPage<MdTemporaryqueue> getPage(PageParam<MdTemporaryqueue> page, @Param("param") MdTemporaryqueue param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdTemporaryqueue getInfoById(@Param("id") String id);

}
