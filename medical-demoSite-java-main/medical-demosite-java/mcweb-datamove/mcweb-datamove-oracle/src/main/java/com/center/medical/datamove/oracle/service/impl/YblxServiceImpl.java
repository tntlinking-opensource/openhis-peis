package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.YblxMapper;
import com.center.medical.datamove.oracle.bean.model.Yblx;
import com.center.medical.datamove.oracle.service.YblxService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (Yblx)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:30:53
 */
@Slf4j
@Service("yblxService")
@RequiredArgsConstructor
public class YblxServiceImpl extends ServiceImpl<YblxMapper, Yblx> implements YblxService {

    private final YblxMapper yblxMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param Yblx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Yblx> getPage(PageParam<Yblx> page, Yblx param) {
        return yblxMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Yblx getInfoById(String id) {
        return yblxMapper.getInfoById(id);
    }

}


