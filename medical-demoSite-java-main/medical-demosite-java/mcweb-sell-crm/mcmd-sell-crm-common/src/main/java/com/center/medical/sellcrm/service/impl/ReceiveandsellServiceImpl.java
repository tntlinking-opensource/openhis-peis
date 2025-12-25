package com.center.medical.sellcrm.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Receiveandsell;
import com.center.medical.sellcrm.dao.ReceiveandsellMapper;
import com.center.medical.sellcrm.service.ReceiveandsellService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 客户公共池领取与领取人员关联表(Receiveandsell)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:22
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
    public IPage<Receiveandsell> getList(PageParam<Receiveandsell> page, Receiveandsell param) {
        return receiveandsellMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Receiveandsell getInfoById(String id) {
        return receiveandsellMapper.getInfoById(id);
    }


}

