package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Temporaryqueue;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * temporaryqueue(Temporaryqueue)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:01
 */
public interface TemporaryqueueMapper extends BaseMapper<Temporaryqueue> {

    /**
     * 分页查询[temporaryqueue]列表
     *
     * @param page  分页参数
     * @param param Temporaryqueue查询参数
     * @return 分页数据
     */
    IPage<Temporaryqueue> getList(PageParam<Temporaryqueue> page, @Param("param") Temporaryqueue param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Temporaryqueue getInfoById(@Param("id") String id);

}
