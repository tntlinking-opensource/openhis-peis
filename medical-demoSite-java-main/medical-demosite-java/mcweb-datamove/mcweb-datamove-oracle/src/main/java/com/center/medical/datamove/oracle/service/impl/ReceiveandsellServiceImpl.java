package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.ReceiveandsellMapper;
import com.center.medical.datamove.oracle.bean.model.Receiveandsell;
import com.center.medical.datamove.oracle.service.ReceiveandsellService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户公共池领取与领取人员关联表(Receiveandsell)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:24:15
 */
@Slf4j
@Service("receiveandsellService")
@RequiredArgsConstructor
public class ReceiveandsellServiceImpl extends ServiceImpl<ReceiveandsellMapper, Receiveandsell> implements ReceiveandsellService {

    private final ReceiveandsellMapper receiveandsellMapper;

    /**
     * 分页查询[客户公共池领取与领取人员关联表]列表
     *
     * @param page  分页参数
     * @param param Receiveandsell查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Receiveandsell> getPage(PageParam<Receiveandsell> page, Receiveandsell param) {
        return receiveandsellMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Receiveandsell getInfoById(String id) {
        return receiveandsellMapper.getInfoById(id);
    }

}


