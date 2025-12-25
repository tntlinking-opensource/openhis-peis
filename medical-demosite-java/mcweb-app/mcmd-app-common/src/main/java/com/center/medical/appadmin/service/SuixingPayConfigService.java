package com.center.medical.appadmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.appadmin.bean.model.SuixingPayConfig;
import com.center.medical.appadmin.bean.param.SuixingPayConfigParam;
import com.center.medical.appadmin.bean.param.SuixingSaOrUpParam;
import com.center.medical.appadmin.bean.vo.SuixingPayConfigVo;
import com.center.medical.common.utils.page.PageParam;

import java.util.List;

/**
 * 随行支付配置参数(SuixingPayConfig)服务接口
 *
 * @author ay
 * @since 2024-07-12 17:05:40
 */
public interface SuixingPayConfigService extends IService<SuixingPayConfig> {

    /**
     * 分页查询[随行支付配置参数]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SuixingPayConfigVo> getPage(PageParam<SuixingPayConfigVo> page, SuixingPayConfigParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SuixingPayConfig getInfoById(String id);

    /**
     * 通过分中心id查询
     * @param branchId
     * @return
     */
    SuixingPayConfig getInfoByFzx(String branchId);

    /**
     * 添加或修改
     * @param param
     * @return
     */
    Boolean saOrUp(SuixingSaOrUpParam param);

    /**
     * 删除随行支付配置参数
     * @param ids
     * @return
     */
    Boolean deleteByIds(List<String> ids);
}

