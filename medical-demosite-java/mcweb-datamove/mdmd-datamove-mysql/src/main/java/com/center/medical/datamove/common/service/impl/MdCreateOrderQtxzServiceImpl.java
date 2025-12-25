package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdCreateOrderQtxzMapper;
import com.center.medical.datamove.common.bean.model.MdCreateOrderQtxz;
import com.center.medical.datamove.common.service.MdCreateOrderQtxzService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 线上变更订单前台须知记录(MdCreateOrderQtxz)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:18
 */
@Slf4j
@Service("mdCreateOrderQtxzService")
@RequiredArgsConstructor
public class MdCreateOrderQtxzServiceImpl extends ServiceImpl<MdCreateOrderQtxzMapper, MdCreateOrderQtxz> implements MdCreateOrderQtxzService {

    private final MdCreateOrderQtxzMapper mdCreateOrderQtxzMapper;

    /**
     * 分页查询[线上变更订单前台须知记录]列表
     *
     * @param page  分页参数
     * @param param MdCreateOrderQtxz查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdCreateOrderQtxz> getPage(PageParam<MdCreateOrderQtxz> page, MdCreateOrderQtxz param) {
        return mdCreateOrderQtxzMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdCreateOrderQtxz getInfoById(String id) {
        return mdCreateOrderQtxzMapper.getInfoById(id);
    }

}


