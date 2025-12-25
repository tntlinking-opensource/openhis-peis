package com.center.medical.statistics.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.RegistrationNotCheckParam;
import com.center.medical.statistics.bean.vo.RegistrationNotCheckVo;

import java.util.List;

/**
 * 登记未检客户统计(Peispatient)服务接口
 *
 * @author ay
 * @since 2023-12-18 15:52:58
 */
public interface RegistrationNotCheckService extends IService<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<RegistrationNotCheckVo> getPage(PageParam<RegistrationNotCheckVo> page, RegistrationNotCheckParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatient getInfoById(String id);

    /**
     * 导出登记未检客户统计
     * @param param
     * @return
     */
    List<RegistrationNotCheckVo> export(RegistrationNotCheckParam param);
}

