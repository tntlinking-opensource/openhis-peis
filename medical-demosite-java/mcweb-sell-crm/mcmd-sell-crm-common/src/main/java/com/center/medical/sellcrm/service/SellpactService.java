package com.center.medical.sellcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Sellpact;
import com.center.medical.sellcrm.bean.param.SellpactLoseCustParam;
import com.center.medical.sellcrm.bean.param.SellpactParam;
import com.center.medical.sellcrm.bean.vo.SellpactVo;

/**
 * 销售合同维护表(Sellpact)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-22 11:51:25
 */
public interface SellpactService extends IService<Sellpact> {

    /**
     * 分页查询[销售合同维护表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Sellpact> getPage(PageParam<Sellpact> page, SellpactParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Sellpact getInfoById(String id);

    /**
     * 新增/编辑操作
     *
     * @param sellpact
     * @return
     */
    Boolean saOrUp(Sellpact sellpact);

    /**
     * 分页查询，流失客户
     * @param page
     * @param sellpactLoseCustParam
     * @return
     */
    IPage<SellpactVo> getLoseCustPage(PageParam<SellpactVo> page, SellpactLoseCustParam sellpactLoseCustParam);

    /**
     * 通过主键查找流失客户
     * @param id
     * @return
     */
    SellpactVo getloseCustInfoById(String id);
}

