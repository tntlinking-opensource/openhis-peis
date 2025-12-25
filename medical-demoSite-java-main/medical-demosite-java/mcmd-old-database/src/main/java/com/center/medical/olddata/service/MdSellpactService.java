package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdSellpact;

import java.util.List;

/**
 * 销售合同维护表(CrmSellpact)服务接口
 *
 * @author ay
 * @since 2023-07-25 21:19:53
 */
public interface MdSellpactService extends IService<MdSellpact> {

    /**
     * 分页查询[销售合同维护表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdSellpact> getPage(PageParam<MdSellpact> page, MdSellpact param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdSellpact getInfoById(String id);

    /**
     * 保存或更新
     *
     * @param map
     */
    void saOrUp(MdSellpact map);

    /**
     * 通过合同编号获取
     * @param htbh
     * @return
     */
    List<MdSellpact> getByHtbh(String htbh);

    /**
     * 批量保存
     * @param mdSellpactList
     */
    void saOrUpList(List<MdSellpact> mdSellpactList);
}

