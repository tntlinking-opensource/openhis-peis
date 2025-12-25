package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdTotalVerdictMapper;
import com.center.medical.datamove.common.bean.model.MdTotalVerdict;
import com.center.medical.datamove.common.service.MdTotalVerdictService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * ZJ总检结论词表(MdTotalVerdict)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:49:12
 */
@Slf4j
@Service("mdTotalVerdictService")
@RequiredArgsConstructor
public class MdTotalVerdictServiceImpl extends ServiceImpl<MdTotalVerdictMapper, MdTotalVerdict> implements MdTotalVerdictService {

    private final MdTotalVerdictMapper mdTotalVerdictMapper;

    /**
     * 分页查询[ZJ总检结论词表]列表
     *
     * @param page  分页参数
     * @param param MdTotalVerdict查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdTotalVerdict> getPage(PageParam<MdTotalVerdict> page, MdTotalVerdict param) {
        return mdTotalVerdictMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdTotalVerdict getInfoById(String id) {
        return mdTotalVerdictMapper.getInfoById(id);
    }

}


