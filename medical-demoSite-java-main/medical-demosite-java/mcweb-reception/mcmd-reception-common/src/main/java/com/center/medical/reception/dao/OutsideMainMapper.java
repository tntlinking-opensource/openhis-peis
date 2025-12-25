package com.center.medical.reception.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reception.bean.model.OutsideMain;
import com.center.medical.reception.bean.param.SendGovernParam;
import com.center.medical.reception.bean.vo.SendGovernVo;
import org.apache.ibatis.annotations.Param;

/**
 * KS外送登记结果主表(OutsideMain)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:28
 */
public interface OutsideMainMapper extends BaseMapper<OutsideMain> {

    /**
     * 分页查询[KS外送登记结果主表]列表
     *
     * @param page  分页参数
     * @param param OutsideMain查询参数
     * @return 分页数据
     */
    IPage<SendGovernVo> getList(PageParam<OutsideMain> page, @Param("param") SendGovernParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    OutsideMain getInfoById(@Param("id") String id);

    /**
     * 外送登记信息-查询
     * @param page
     * @param param
     * @return
     */
    IPage<SendGovernVo> getMainListData(PageParam<OutsideMain> page, @Param("param") SendGovernParam param);
}
