package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.ClientcommonMapper;
import com.center.medical.datamove.oracle.bean.model.Clientcommon;
import com.center.medical.datamove.oracle.service.ClientcommonService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户公共池表(Clientcommon)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:17:39
 */
@Slf4j
@Service("clientcommonService")
@RequiredArgsConstructor
public class ClientcommonServiceImpl extends ServiceImpl<ClientcommonMapper, Clientcommon> implements ClientcommonService {

    private final ClientcommonMapper clientcommonMapper;

    /**
     * 分页查询[客户公共池表]列表
     *
     * @param page  分页参数
     * @param param Clientcommon查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Clientcommon> getPage(PageParam<Clientcommon> page, Clientcommon param) {
        return clientcommonMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Clientcommon getInfoById(String id) {
        return clientcommonMapper.getInfoById(id);
    }

}


