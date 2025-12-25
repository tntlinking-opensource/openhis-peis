package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Peispatientcharge;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检者缴费表(Peispatientcharge)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:06
 */
public interface PeispatientchargeMapper extends BaseMapper<Peispatientcharge> {

    /**
     * 分页查询[体检者缴费表]列表
     *
     * @param page  分页参数
     * @param param Peispatientcharge查询参数
     * @return 分页数据
     */
    IPage<Peispatientcharge> getList(PageParam<Peispatientcharge> page, @Param("param") Peispatientcharge param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Peispatientcharge getInfoById(@Param("id") String id);

}
