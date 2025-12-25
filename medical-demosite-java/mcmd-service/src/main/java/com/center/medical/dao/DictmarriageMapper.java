package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Dictmarriage;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 婚姻表(Dictmarriage)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:50
 */
public interface DictmarriageMapper extends BaseMapper<Dictmarriage> {

    /**
     * 分页查询[婚姻表]列表
     *
     * @param page  分页参数
     * @param param Dictmarriage查询参数
     * @return 分页数据
     */
    IPage<Dictmarriage> getList(PageParam<Dictmarriage> page, @Param("param") Dictmarriage param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Dictmarriage getInfoById(@Param("id") String id);

}
