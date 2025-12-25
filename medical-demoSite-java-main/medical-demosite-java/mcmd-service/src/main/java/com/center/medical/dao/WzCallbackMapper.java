package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.WzCallback;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS问诊——复查随访(WzCallback)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:29
 */
public interface WzCallbackMapper extends BaseMapper<WzCallback> {

    /**
     * 分页查询[KS问诊——复查随访]列表
     *
     * @param page  分页参数
     * @param param WzCallback查询参数
     * @return 分页数据
     */
    IPage<WzCallback> getList(PageParam<WzCallback> page, @Param("param") WzCallback param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    WzCallback getInfoById(@Param("id") String id);

}
