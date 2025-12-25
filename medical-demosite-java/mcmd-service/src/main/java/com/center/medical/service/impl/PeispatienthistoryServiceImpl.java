package com.center.medical.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatienthistory;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PeispatienthistoryMapper;
import com.center.medical.service.PeispatienthistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 体检者（history）表(Peispatienthistory)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:37
 */
@Slf4j
@Service("peispatienthistoryService")
@RequiredArgsConstructor
public class PeispatienthistoryServiceImpl extends ServiceImpl<PeispatienthistoryMapper, Peispatienthistory> implements PeispatienthistoryService {

    private final PeispatienthistoryMapper peispatienthistoryMapper;

    /**
     * 分页查询[体检者（history）表]列表
     *
     * @param page  分页参数
     * @param param Peispatienthistory查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Peispatienthistory> getList(PageParam<Peispatienthistory> page, Peispatienthistory param) {
        return peispatienthistoryMapper.getList(page, param);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @Override
    public Peispatienthistory getInForById(String id) {
        return peispatienthistoryMapper.getInForById(id);
    }
}

