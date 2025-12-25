package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdFxPositiveMapper;
import com.center.medical.datamove.common.bean.model.MdFxPositive;
import com.center.medical.datamove.common.service.MdFxPositiveService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 综合分析-阳性小结(MdFxPositive)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:19
 */
@Slf4j
@Service("mdFxPositiveService")
@RequiredArgsConstructor
public class MdFxPositiveServiceImpl extends ServiceImpl<MdFxPositiveMapper, MdFxPositive> implements MdFxPositiveService {

    private final MdFxPositiveMapper mdFxPositiveMapper;

    /**
     * 分页查询[综合分析-阳性小结]列表
     *
     * @param page  分页参数
     * @param param MdFxPositive查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdFxPositive> getPage(PageParam<MdFxPositive> page, MdFxPositive param) {
        return mdFxPositiveMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdFxPositive getInfoById(String id) {
        return mdFxPositiveMapper.getInfoById(id);
    }

    ;

}


