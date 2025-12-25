package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.PeisorgreservationreceiptMapper;
import com.center.medical.datamove.oracle.bean.model.Peisorgreservationreceipt;
import com.center.medical.datamove.oracle.service.PeisorgreservationreceiptService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * QT发票表(Peisorgreservationreceipt)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:23:08
 */
@Slf4j
@Service("peisorgreservationreceiptService")
@RequiredArgsConstructor
public class PeisorgreservationreceiptServiceImpl extends ServiceImpl<PeisorgreservationreceiptMapper, Peisorgreservationreceipt> implements PeisorgreservationreceiptService {

    private final PeisorgreservationreceiptMapper peisorgreservationreceiptMapper;

    /**
     * 分页查询[QT发票表]列表
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

    ;

}


