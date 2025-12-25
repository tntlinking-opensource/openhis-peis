package com.center.medical.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.KsIp;
import com.center.medical.data.bean.param.KsIpPageParam;

import java.util.List;

/**
 * 科室IP(KsIp)表服务接口
 *
 * @author 路飞船长
 * @since 2023-04-20 19:37:55
 */
public interface KsIpService extends IService<KsIp> {

    /**
     * 分页查询[科室IP]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<KsIp> getPage(PageParam<KsIp> page, KsIpPageParam param);

    /**
     * 查询[科室IP]列表
     *
     * @param ksId
     * @param branchId
     * @return
     */
    List<KsIp> getList(String ksId, String branchId);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    KsIp getInfoById(String id);

}

