package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdSectionResultMainMapper;
import com.center.medical.datamove.common.bean.model.MdSectionResultMain;
import com.center.medical.datamove.common.service.MdSectionResultMainService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS科室检查结果主表(MdSectionResultMain)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:33
 */
@Slf4j
@Service("mdSectionResultMainService")
@RequiredArgsConstructor
public class MdSectionResultMainServiceImpl extends ServiceImpl<MdSectionResultMainMapper, MdSectionResultMain> implements MdSectionResultMainService {

    private final MdSectionResultMainMapper mdSectionResultMainMapper;

    /**
     * 分页查询[KS科室检查结果主表]列表
     *
     * @param page  分页参数
     * @param param MdSectionResultMain查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdSectionResultMain> getPage(PageParam<MdSectionResultMain> page, MdSectionResultMain param) {
        return mdSectionResultMainMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdSectionResultMain getInfoById(String id) {
        return mdSectionResultMainMapper.getInfoById(id);
    }

}


