package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.common.bean.model.SysCodeConfig;
import org.apache.ibatis.annotations.Param;

/**
 * 加密密钥及授权码表(CodeConfig)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:33
 */
public interface SysCodeConfigMapper extends BaseMapper<SysCodeConfig> {

    /**
     * 分页查询[加密密钥及授权码表]列表
     *
     * @param page  分页参数
     * @param param SysCodeConfig查询参数
     * @return 分页数据
     */
    IPage<SysCodeConfig> getPage(PageParam<SysCodeConfig> page, @Param("param") SysCodeConfig param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysCodeConfig getInfoById(@Param("id") Object id);

}
