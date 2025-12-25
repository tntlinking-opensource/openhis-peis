package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.WhysqzfwMapper;
import com.center.medical.datamove.oracle.bean.model.Whysqzfw;
import com.center.medical.datamove.oracle.service.WhysqzfwService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC危害因素取值范围(Whysqzfw)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:25:56
 */
@Slf4j
@Service("whysqzfwService")
@RequiredArgsConstructor
public class WhysqzfwServiceImpl extends ServiceImpl<WhysqzfwMapper, Whysqzfw> implements WhysqzfwService {

    private final WhysqzfwMapper whysqzfwMapper;

    /**
     * 分页查询[JC危害因素取值范围]列表
     *
     * @param page  分页参数
     * @param param Whysqzfw查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Whysqzfw> getPage(PageParam<Whysqzfw> page, Whysqzfw param) {
        return whysqzfwMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Whysqzfw getInfoById(String id) {
        return whysqzfwMapper.getInfoById(id);
    }

}


