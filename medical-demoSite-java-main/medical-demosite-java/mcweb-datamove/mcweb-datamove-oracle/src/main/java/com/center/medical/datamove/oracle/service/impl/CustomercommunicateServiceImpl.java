package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.CustomercommunicateMapper;
import com.center.medical.datamove.oracle.bean.model.Customercommunicate;
import com.center.medical.datamove.oracle.service.CustomercommunicateService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户沟通表(Customercommunicate)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:18:05
 */
@Slf4j
@Service("customercommunicateService")
@RequiredArgsConstructor
public class CustomercommunicateServiceImpl extends ServiceImpl<CustomercommunicateMapper, Customercommunicate> implements CustomercommunicateService {

    private final CustomercommunicateMapper customercommunicateMapper;

    /**
     * 分页查询[客户沟通表]列表
     *
     * @param page  分页参数
     * @param param Customercommunicate查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Customercommunicate> getPage(PageParam<Customercommunicate> page, Customercommunicate param) {
        return customercommunicateMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Customercommunicate getInfoById(String id) {
        return customercommunicateMapper.getInfoById(id);
    }

}


