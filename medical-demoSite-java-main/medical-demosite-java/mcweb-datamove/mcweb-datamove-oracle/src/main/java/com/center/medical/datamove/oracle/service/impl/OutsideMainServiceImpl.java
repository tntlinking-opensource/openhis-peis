package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.OutsideMainMapper;
import com.center.medical.datamove.oracle.bean.model.OutsideMain;
import com.center.medical.datamove.oracle.service.OutsideMainService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS外送登记结果主表(OutsideMain)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:34
 */
@Slf4j
@Service("outsideMainService")
@RequiredArgsConstructor
public class OutsideMainServiceImpl extends ServiceImpl<OutsideMainMapper, OutsideMain> implements OutsideMainService {

    private final OutsideMainMapper outsideMainMapper;

    /**
     * 分页查询[KS外送登记结果主表]列表
     *
     * @param page  分页参数
     * @param param OutsideMain查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OutsideMain> getPage(PageParam<OutsideMain> page, OutsideMain param) {
        return outsideMainMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public OutsideMain getInfoById(String id) {
        return outsideMainMapper.getInfoById(id);
    }

}


