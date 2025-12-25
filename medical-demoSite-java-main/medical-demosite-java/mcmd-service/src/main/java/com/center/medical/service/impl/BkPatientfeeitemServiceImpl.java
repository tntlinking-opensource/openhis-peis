package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.BkPatientfeeitemMapper;
import com.center.medical.bean.model.BkPatientfeeitem;
import com.center.medical.service.BkPatientfeeitemService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检者收费项目(BkPatientfeeitem)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:15
 */
@Slf4j
@Service("bkPatientfeeitemService")
@RequiredArgsConstructor
public class BkPatientfeeitemServiceImpl extends ServiceImpl<BkPatientfeeitemMapper, BkPatientfeeitem> implements BkPatientfeeitemService {

    private final BkPatientfeeitemMapper bkPatientfeeitemMapper;

    /**
     * 分页查询[体检者收费项目]列表
     *
     * @param page  分页参数
     * @param param BkPatientfeeitem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<BkPatientfeeitem> getList(PageParam<BkPatientfeeitem> page, BkPatientfeeitem param) {
        return bkPatientfeeitemMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public BkPatientfeeitem getInfoById(String id) {
        return bkPatientfeeitemMapper.getInfoById(id);
    }

}

