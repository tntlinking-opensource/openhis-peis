package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.SysLogininfor;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 系统访问记录(SysLogininfor)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:35
 */
public interface SysLogininforMapper extends BaseMapper<SysLogininfor> {

    /**
     * 分页查询[系统访问记录]列表
     *
     * @param page  分页参数
     * @param param SysLogininfor查询参数
     * @return 分页数据
     */
    IPage<SysLogininfor> getPage(PageParam<SysLogininfor> page, @Param("param") SysLogininfor param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键infoId
     * @return 详情信息
     */
    SysLogininfor getInfoById(@Param("id") Long id);

}
