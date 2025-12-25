package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.MemberbirthdatMapper;
import com.center.medical.datamove.oracle.bean.model.Memberbirthdat;
import com.center.medical.datamove.oracle.service.MemberbirthdatService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KF生日回访表(Memberbirthdat)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:05
 */
@Slf4j
@Service("memberbirthdatService")
@RequiredArgsConstructor
public class MemberbirthdatServiceImpl extends ServiceImpl<MemberbirthdatMapper, Memberbirthdat> implements MemberbirthdatService {

    private final MemberbirthdatMapper memberbirthdatMapper;

    /**
     * 分页查询[KF生日回访表]列表
     *
     * @param page  分页参数
     * @param param Memberbirthdat查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Memberbirthdat> getPage(PageParam<Memberbirthdat> page, Memberbirthdat param) {
        return memberbirthdatMapper.getPage(page, param);
    }


}


