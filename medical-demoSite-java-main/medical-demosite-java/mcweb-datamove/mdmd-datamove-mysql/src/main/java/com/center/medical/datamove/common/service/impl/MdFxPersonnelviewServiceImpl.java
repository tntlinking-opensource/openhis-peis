package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdFxPersonnelviewMapper;
import com.center.medical.datamove.common.bean.model.MdFxPersonnelview;
import com.center.medical.datamove.common.service.MdFxPersonnelviewService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 综合分析-人员一览表(MdFxPersonnelview)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:19
 */
@Slf4j
@Service("mdFxPersonnelviewService")
@RequiredArgsConstructor
public class MdFxPersonnelviewServiceImpl extends ServiceImpl<MdFxPersonnelviewMapper, MdFxPersonnelview> implements MdFxPersonnelviewService {

    private final MdFxPersonnelviewMapper mdFxPersonnelviewMapper;

    /**
     * 分页查询[综合分析-人员一览表]列表
     *
     * @param page  分页参数
     * @param param MdFxPersonnelview查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdFxPersonnelview> getPage(PageParam<MdFxPersonnelview> page, MdFxPersonnelview param) {
        return mdFxPersonnelviewMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdFxPersonnelview getInfoById(String id) {
        return mdFxPersonnelviewMapper.getInfoById(id);
    }

}


