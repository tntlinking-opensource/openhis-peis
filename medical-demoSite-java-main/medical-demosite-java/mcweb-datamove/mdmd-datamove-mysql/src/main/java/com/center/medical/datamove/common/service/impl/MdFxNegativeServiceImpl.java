package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdFxNegativeMapper;
import com.center.medical.datamove.common.bean.model.MdFxNegative;
import com.center.medical.datamove.common.service.MdFxNegativeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 综合分析-阴性小结(MdFxNegative)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:19
 */
@Slf4j
@Service("mdFxNegativeService")
@RequiredArgsConstructor
public class MdFxNegativeServiceImpl extends ServiceImpl<MdFxNegativeMapper, MdFxNegative> implements MdFxNegativeService {

    private final MdFxNegativeMapper mdFxNegativeMapper;

    /**
     * 分页查询[综合分析-阴性小结]列表
     *
     * @param page  分页参数
     * @param param MdFxNegative查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdFxNegative> getPage(PageParam<MdFxNegative> page, MdFxNegative param) {
        return mdFxNegativeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdFxNegative getInfoById(String id) {
        return mdFxNegativeMapper.getInfoById(id);
    }

}


