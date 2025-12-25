package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.SellRemindMapper;
import com.center.medical.datamove.oracle.bean.model.SellRemind;
import com.center.medical.datamove.oracle.service.SellRemindService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (SellRemind)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:24:51
 */
@Slf4j
@Service("sellRemindService")
@RequiredArgsConstructor
public class SellRemindServiceImpl extends ServiceImpl<SellRemindMapper, SellRemind> implements SellRemindService {

    private final SellRemindMapper sellRemindMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param SellRemind查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SellRemind> getPage(PageParam<SellRemind> page, SellRemind param) {
        return sellRemindMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SellRemind getInfoById(String id) {
        return sellRemindMapper.getInfoById(id);
    }

}


