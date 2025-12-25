package com.center.medical.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.CodeConfig;
import org.apache.ibatis.annotations.Param;

/**
 * 加密密钥及授权码表(CodeConfig)表数据库访问层
 *
 * @author 路飞船长
 * @since 2023-04-04 16:10:31
 */
public interface CodeConfigMapper extends BaseMapper<CodeConfig> {

    /**
     * 分页查询[加密密钥及授权码表]列表
     *
     * @param page  分页参数
     * @param param SysCodeConfig查询参数
     * @return 分页数据
     */
    IPage<CodeConfig> getPage(PageParam<CodeConfig> page, @Param("param") CodeConfig param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CodeConfig getInfoById(@Param("id") Long id);

}
