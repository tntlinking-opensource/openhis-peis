package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.PeispatientChargeOther;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检者其他缴费(PeispatientChargeOther)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:10
 */
public interface PeispatientChargeOtherMapper extends BaseMapper<PeispatientChargeOther> {

    /**
     * 分页查询[体检者其他缴费]列表
     *
     * @param page  分页参数
     * @param param PeispatientChargeOther查询参数
     * @return 分页数据
     */
    IPage<PeispatientChargeOther> getList(PageParam<PeispatientChargeOther> page, @Param("param") PeispatientChargeOther param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    PeispatientChargeOther getInfoById(@Param("id") String id);

}
