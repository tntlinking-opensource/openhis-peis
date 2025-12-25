package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.PeispatientexamitemMapper;
import com.center.medical.datamove.oracle.bean.model.Peispatientexamitem;
import com.center.medical.datamove.oracle.service.PeispatientexamitemService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * LIS结果(Peispatientexamitem)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:23:28
 */
@Slf4j
@Service("peispatientexamitemService")
@RequiredArgsConstructor
public class PeispatientexamitemServiceImpl extends ServiceImpl<PeispatientexamitemMapper, Peispatientexamitem> implements PeispatientexamitemService {

    private final PeispatientexamitemMapper peispatientexamitemMapper;

    /**
     * 分页查询[LIS结果]列表
     *
     * @param page  分页参数
     * @param param Peispatientexamitem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Peispatientexamitem> getPage(PageParam<Peispatientexamitem> page, Peispatientexamitem param) {
        return peispatientexamitemMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peispatientexamitem getInfoById(String id) {
        return peispatientexamitemMapper.getInfoById(id);
    }

    ;

}


