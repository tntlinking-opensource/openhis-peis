package com.center.medical.sellcrm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Peisorgreservationgroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 体检者任务分组(Peisorgreservationgroup)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-12-02 11:41:02
 */
public interface PeisorgreservationgroupMapper extends BaseMapper<Peisorgreservationgroup> {

    /**
     * 分页查询[体检者任务分组]列表
     *
     * @param page  分页参数
     * @param param Peisorgreservationgroup查询参数
     * @return 分页数据
     */
    IPage<Peisorgreservationgroup> getPage(PageParam<Peisorgreservationgroup> page, @Param("param") Peisorgreservationgroup param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peisorgreservationgroup getInfoById(@Param("id") String id);

    /**
     * 查看套餐
     * @param idOrgreservation
     * @param tjtcmc
     * @return
     */
    List<Peisorgreservationgroup> getGroupData(@Param("idOrgreservation") String idOrgreservation,@Param("tjtcmc") String tjtcmc);

    /**
     * 获取套餐的分中心
     * @param id
     * @return
     */
    List<String> getComboAndMealFzx(@Param("tcid") String tcid);
}
