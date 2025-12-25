package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Shortmessage;
import org.apache.ibatis.annotations.Param;

/**
 * JC短信信息表(Shortmessage)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:17
 */
public interface ShortmessageMapper extends BaseMapper<Shortmessage> {

    /**
     * 分页查询[JC短信信息表]列表
     *
     * @param page  分页参数
     * @param param Shortmessage查询参数
     * @return 分页数据
     */
    IPage<Shortmessage> getList(PageParam<Shortmessage> page, @Param("param") Shortmessage param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Shortmessage getInfoById(@Param("id") String id);

}
