package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdSectionTotal;
import com.center.medical.olddata.dao.MdSectionTotalMapper;
import com.center.medical.olddata.service.MdSectionTotalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * ZJ总检主表(MdSectionTotal)服务实现类
 *
 * @author ay
 * @since 2023-11-10 14:27:20
 */
@Slf4j
@Service("mdSectionTotalService")
@RequiredArgsConstructor
public class MdSectionTotalServiceImpl extends ServiceImpl<MdSectionTotalMapper, MdSectionTotal> implements MdSectionTotalService {

    private final MdSectionTotalMapper mdSectionTotalMapper;

    /**
     * 分页查询[ZJ总检主表]列表
     *
     * @param page  分页参数
     * @param param MdSectionTotal查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdSectionTotal> getPage(PageParam<MdSectionTotal> page, MdSectionTotal param) {
        return mdSectionTotalMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdSectionTotal getInfoById(String id) {
        return mdSectionTotalMapper.getInfoById(id);
    }

}

