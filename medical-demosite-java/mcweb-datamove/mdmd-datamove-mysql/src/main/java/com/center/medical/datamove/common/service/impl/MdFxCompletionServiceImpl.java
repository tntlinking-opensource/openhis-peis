package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdFxCompletionMapper;
import com.center.medical.datamove.common.bean.model.MdFxCompletion;
import com.center.medical.datamove.common.service.MdFxCompletionService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 本次职业健康检查漏检人员及漏检项目人员一览表(MdFxCompletion)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:18
 */
@Slf4j
@Service("mdFxCompletionService")
@RequiredArgsConstructor
public class MdFxCompletionServiceImpl extends ServiceImpl<MdFxCompletionMapper, MdFxCompletion> implements MdFxCompletionService {

    private final MdFxCompletionMapper mdFxCompletionMapper;

    /**
     * 分页查询[本次职业健康检查漏检人员及漏检项目人员一览表]列表
     *
     * @param page  分页参数
     * @param param MdFxCompletion查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdFxCompletion> getPage(PageParam<MdFxCompletion> page, MdFxCompletion param) {
        return mdFxCompletionMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdFxCompletion getInfoById(String id) {
        return mdFxCompletionMapper.getInfoById(id);
    }

}


