package com.center.medical.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysVersionItem;
import com.center.medical.system.bean.param.SysVersionItemParam;
import org.apache.ibatis.annotations.Param;

/**
 * 系统更新记录(SysVersionItem)数据库访问层
 *
 * @author makejava
 * @since 2024-03-01 18:02:37
 */
public interface SysVersionItemMapper extends BaseMapper<SysVersionItem> {

    /**
     * 分页查询[系统更新记录]列表
     *
     * @param page  分页参数
     * @param param SysVersionItem查询参数
     * @return 分页数据
     */
    IPage<SysVersionItem> getPage(PageParam<SysVersionItem> page, @Param("param") SysVersionItemParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键logId
     * @return 详情信息
     */
    SysVersionItem getInfoById(@Param("id") Integer id);

}
