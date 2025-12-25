package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.HarmAndFzx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 危害因素和分中心(HarmAndFzx)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:15
 */
public interface HarmAndFzxMapper extends BaseMapper<HarmAndFzx> {

    /**
     * 分页查询[危害因素和分中心]列表
     *
     * @param page  分页参数
     * @param param HarmAndFzx查询参数
     * @return 分页数据
     */
    IPage<HarmAndFzx> getList(PageParam<HarmAndFzx> page, @Param("param") HarmAndFzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    HarmAndFzx getInfoById(@Param("id") String id);

}
