package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdWzCallback;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS问诊——复查随访(MdWzCallback)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:22
 */
public interface MdWzCallbackMapper extends BaseMapper<MdWzCallback> {

    /**
     * 分页查询[KS问诊——复查随访]列表
     *
     * @param page  分页参数
     * @param param MdWzCallback查询参数
     * @return 分页数据
     */
    IPage<MdWzCallback> getPage(PageParam<MdWzCallback> page, @Param("param") MdWzCallback param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdWzCallback getInfoById(@Param("id") String id);

}
