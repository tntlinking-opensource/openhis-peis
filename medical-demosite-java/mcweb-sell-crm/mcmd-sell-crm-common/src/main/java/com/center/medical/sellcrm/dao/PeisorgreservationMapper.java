package com.center.medical.sellcrm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.param.PoGroupParam;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Peisorgreservation;
import com.center.medical.sellcrm.bean.param.DbOrderParam;
import com.center.medical.sellcrm.bean.vo.POSimpleVo;
import org.apache.ibatis.annotations.Param;

/**
 * 体检者团体任务(Peisorgreservation)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-12-02 11:41:01
 */
public interface PeisorgreservationMapper extends BaseMapper<Peisorgreservation> {

    /**
     * 分页查询[体检者团体任务]列表
     *
     * @param page  分页参数
     * @param param Peisorgreservation查询参数
     * @return 分页数据
     */
    IPage<Peisorgreservation> getPage(PageParam<Peisorgreservation> page, @Param("param") DbOrderParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peisorgreservation getInfoById(@Param("id") String id);

    /**
     * 登记页面团体列表
     *
     * @param param
     * @return
     */
    IPage<POSimpleVo> getGroupForOrgData(PageParam<Peisorgreservation> page, @Param("param") PoGroupParam param);
}
