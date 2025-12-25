package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.KsIp;
import com.center.medical.data.bean.param.KsIpPageParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 科室IP(KsIp)表数据库访问层
 *
 * @author 路飞船长
 * @since 2023-04-20 19:37:55
 */
public interface KsIpMapper extends BaseMapper<KsIp> {

    /**
     * 分页查询[科室IP]列表
     *
     * @param page  分页参数
     * @param param KsIp查询参数
     * @return 分页数据
     */
    IPage<KsIp> getPage(PageParam<KsIp> page, @Param("param") KsIpPageParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    KsIp getInfoById(@Param("id") String id);

    /**
     * 查询[科室IP]列表
     *
     * @param ksId
     * @param branchId
     * @return
     */
    List<KsIp> getList(@Param("ksId") String ksId, @Param("branchId") String branchId);
}
