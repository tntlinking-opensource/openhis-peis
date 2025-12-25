package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdPeisorgreservationreceiptMapper;
import com.center.medical.datamove.common.bean.model.MdPeisorgreservationreceipt;
import com.center.medical.datamove.common.service.MdPeisorgreservationreceiptService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 发票(MdPeisorgreservationreceipt)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:10
 */
@Slf4j
@Service("mdPeisorgreservationreceiptService")
@RequiredArgsConstructor
public class MdPeisorgreservationreceiptServiceImpl extends ServiceImpl<MdPeisorgreservationreceiptMapper, MdPeisorgreservationreceipt> implements MdPeisorgreservationreceiptService {

    private final MdPeisorgreservationreceiptMapper mdPeisorgreservationreceiptMapper;

    /**
     * 分页查询[发票]列表
     *
     * @param page  分页参数
     * @param param MdPeisorgreservationreceipt查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdPeisorgreservationreceipt> getPage(PageParam<MdPeisorgreservationreceipt> page, MdPeisorgreservationreceipt param) {
        return mdPeisorgreservationreceiptMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdPeisorgreservationreceipt getInfoById(String id) {
        return mdPeisorgreservationreceiptMapper.getInfoById(id);
    }

}


