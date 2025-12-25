package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrSellpact;

import java.util.List;

/**
 * 销售合同维护表(Sellpact)服务接口
 *
 * @author ay
 * @since 2023-07-25 21:19:14
 */
public interface OrSellpactService extends IService<OrSellpact> {

    /**
     * 分页查询[销售合同维护表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<OrSellpact> getPage(PageParam<OrSellpact> page, OrSellpact param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrSellpact getInfoById(String id);

    /**
     * 通过客户id查询合同
     *
     * @param khdwmcid
     * @return
     */
    List<OrSellpact> getByKhId(String khdwmcid);

    /**
     * 通过合同编号查询
     *
     * @param htbh
     * @return
     */
    List<OrSellpact> getByHtbh(String htbh);
}

