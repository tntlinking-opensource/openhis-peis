package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdFxReviewInfoMapper;
import com.center.medical.datamove.common.bean.model.MdFxReviewInfo;
import com.center.medical.datamove.common.service.MdFxReviewInfoService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 职业健康检查职业病危害效应相关指标异常需要复查人员(MdFxReviewInfo)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:19
 */
@Slf4j
@Service("mdFxReviewInfoService")
@RequiredArgsConstructor
public class MdFxReviewInfoServiceImpl extends ServiceImpl<MdFxReviewInfoMapper, MdFxReviewInfo> implements MdFxReviewInfoService {

    private final MdFxReviewInfoMapper mdFxReviewInfoMapper;

    /**
     * 分页查询[职业健康检查职业病危害效应相关指标异常需要复查人员]列表
     *
     * @param page  分页参数
     * @param param MdFxReviewInfo查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdFxReviewInfo> getPage(PageParam<MdFxReviewInfo> page, MdFxReviewInfo param) {
        return mdFxReviewInfoMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdFxReviewInfo getInfoById(String id) {
        return mdFxReviewInfoMapper.getInfoById(id);
    }

}


