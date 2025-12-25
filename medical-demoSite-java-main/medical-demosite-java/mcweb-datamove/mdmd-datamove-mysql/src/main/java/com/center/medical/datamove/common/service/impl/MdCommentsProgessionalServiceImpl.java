package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdCommentsProgessionalMapper;
import com.center.medical.datamove.common.bean.model.MdCommentsProgessional;
import com.center.medical.datamove.common.service.MdCommentsProgessionalService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 职业处理意见表：这个表根据体检号进行筛选出这个体检号下所做的所有职业处理意见，这里会有多条，主要用于存储多条处理意见。这里的多条处理意见从基础数据的维护中取得(MdCommentsProgessional)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:17
 */
@Slf4j
@Service("mdCommentsProgessionalService")
@RequiredArgsConstructor
public class MdCommentsProgessionalServiceImpl extends ServiceImpl<MdCommentsProgessionalMapper, MdCommentsProgessional> implements MdCommentsProgessionalService {

    private final MdCommentsProgessionalMapper mdCommentsProgessionalMapper;

    /**
     * 分页查询[职业处理意见表：这个表根据体检号进行筛选出这个体检号下所做的所有职业处理意见，这里会有多条，主要用于存储多条处理意见。这里的多条处理意见从基础数据的维护中取得]列表
     *
     * @param page  分页参数
     * @param param MdCommentsProgessional查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdCommentsProgessional> getPage(PageParam<MdCommentsProgessional> page, MdCommentsProgessional param) {
        return mdCommentsProgessionalMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdCommentsProgessional getInfoById(String id) {
        return mdCommentsProgessionalMapper.getInfoById(id);
    }

}


