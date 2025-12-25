package com.center.medical.sellcrm.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Peisorgreservationreceipt;
import com.center.medical.sellcrm.dao.PeisorgreservationreceiptMapper;
import com.center.medical.sellcrm.service.PeisorgreservationreceiptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 发票(Peisorgreservationreceipt)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-12-02 11:41:04
 */
@Slf4j
@Service("peisorgreservationreceiptService")
@RequiredArgsConstructor
public class PeisorgreservationreceiptServiceImpl extends ServiceImpl<PeisorgreservationreceiptMapper, Peisorgreservationreceipt> implements PeisorgreservationreceiptService {

    private final PeisorgreservationreceiptMapper peisorgreservationreceiptMapper;

    /**
     * 分页查询[发票]列表
     *
     * @param page  分页参数
     * @param param Peisorgreservationreceipt查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Peisorgreservationreceipt> getPage(PageParam<Peisorgreservationreceipt> page, Peisorgreservationreceipt param) {
        return peisorgreservationreceiptMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peisorgreservationreceipt getInfoById(String id) {
        return peisorgreservationreceiptMapper.getInfoById(id);
    }

}

