package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdCarmanagefrMapper;
import com.center.medical.datamove.common.bean.model.MdCarmanagefr;
import com.center.medical.datamove.common.service.MdCarmanagefrService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检车与外出体检车上的人员关联表(MdCarmanagefr)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:15
 */
@Slf4j
@Service("mdCarmanagefrService")
@RequiredArgsConstructor
public class MdCarmanagefrServiceImpl extends ServiceImpl<MdCarmanagefrMapper, MdCarmanagefr> implements MdCarmanagefrService {

    private final MdCarmanagefrMapper mdCarmanagefrMapper;

    /**
     * 分页查询[体检车与外出体检车上的人员关联表]列表
     *
     * @param page  分页参数
     * @param param MdCarmanagefr查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdCarmanagefr> getPage(PageParam<MdCarmanagefr> page, MdCarmanagefr param) {
        return mdCarmanagefrMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdCarmanagefr getInfoById(String id) {
        return mdCarmanagefrMapper.getInfoById(id);
    }

}


