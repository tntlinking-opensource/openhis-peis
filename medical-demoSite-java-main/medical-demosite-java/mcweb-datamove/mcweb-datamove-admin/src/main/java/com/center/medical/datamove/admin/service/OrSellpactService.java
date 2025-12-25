package com.center.medical.datamove.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.Sellpact;

import java.util.List;

/**
 * 销售合同维护表(Sellpact)服务接口
 *
 * @author ay
 * @since 2023-07-25 21:19:14
 */
public interface OrSellpactService extends IService<Sellpact> {

    /**
     * 分页查询[销售合同维护表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Sellpact> getPage(PageParam<Sellpact> page, Sellpact param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Sellpact getInfoById(String id);

    /**
     * 通过客户id查询合同
     *
     * @param khdwmcid
     * @return
     */
    List<Sellpact> getByKhId(String khdwmcid);

    /**
     * 通过合同编号查询
     *
     * @param htbh
     * @return
     */
    Sellpact getByHtbh(String htbh);
}

