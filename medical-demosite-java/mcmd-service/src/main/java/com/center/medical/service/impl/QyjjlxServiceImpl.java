package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.QyjjlxMapper;
import com.center.medical.bean.model.Qyjjlx;
import com.center.medical.service.QyjjlxService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 企业经济类型(Qyjjlx)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:15
 */
@Slf4j
@Service("qyjjlxService")
@RequiredArgsConstructor
public class QyjjlxServiceImpl extends ServiceImpl<QyjjlxMapper, Qyjjlx> implements QyjjlxService {

    private final QyjjlxMapper qyjjlxMapper;

    /**
     * 分页查询[企业经济类型]列表
     *
     * @param page  分页参数
     * @param param Qyjjlx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Qyjjlx> getList(PageParam<Qyjjlx> page, Qyjjlx param) {
        return qyjjlxMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Qyjjlx getInfoById(String id) {
        return qyjjlxMapper.getInfoById(id);
    }

}

