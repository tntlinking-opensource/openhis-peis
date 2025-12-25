package com.center.medical.sellcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Customercommunicate;
import com.center.medical.sellcrm.bean.param.CustomercommunicateParam;

/**
 * 客户沟通表(Customercommunicate)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-21 09:02:49
 */
public interface CustomercommunicateService extends IService<Customercommunicate> {

    /**
     * 分页查询[客户沟通表]列表
     *
     * @param customercommunicateParam  分页参数
     * @return 分页数据
     */
    IPage<Customercommunicate> getPage(PageParam<Customercommunicate> page, CustomercommunicateParam customercommunicateParam);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Customercommunicate getInfoById(String id);

}

