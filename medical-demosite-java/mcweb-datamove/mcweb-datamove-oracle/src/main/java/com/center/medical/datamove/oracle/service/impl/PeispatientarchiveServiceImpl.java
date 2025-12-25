package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.PeispatientarchiveMapper;
import com.center.medical.datamove.oracle.bean.model.Peispatientarchive;
import com.center.medical.datamove.oracle.service.PeispatientarchiveService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检者档案表(Peispatientarchive)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:23:25
 */
@Slf4j
@Service("peispatientarchiveService")
@RequiredArgsConstructor
public class PeispatientarchiveServiceImpl extends ServiceImpl<PeispatientarchiveMapper, Peispatientarchive> implements PeispatientarchiveService {

    private final PeispatientarchiveMapper peispatientarchiveMapper;

    /**
     * 分页查询[体检者档案表]列表
     *
     * @param page  分页参数
     * @param param Peispatientarchive查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Peispatientarchive> getPage(PageParam<Peispatientarchive> page, Peispatientarchive param) {
        return peispatientarchiveMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peispatientarchive getInfoById(String id) {
        return peispatientarchiveMapper.getInfoById(id);
    }

}


