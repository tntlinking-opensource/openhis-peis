package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.WzCallback;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS问诊——复查随访(WzCallback)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:30:48
 */
public interface WzCallbackMapper extends BaseMapper<WzCallback> {

    /**
     * 分页查询[KS问诊——复查随访]列表
     *
     * @param page  分页参数
     * @param param WzCallback查询参数
     * @return 分页数据
     */
    IPage<WzCallback> getPage(PageParam<WzCallback> page, @Param("param") WzCallback param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    WzCallback getInfoById(@Param("id") String id);

}
