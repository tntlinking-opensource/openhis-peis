package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdGroupReviewNoticePatientMapper;
import com.center.medical.datamove.common.bean.model.MdGroupReviewNoticePatient;
import com.center.medical.datamove.common.service.MdGroupReviewNoticePatientService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 批量复查通知人员(MdGroupReviewNoticePatient)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:21
 */
@Slf4j
@Service("mdGroupReviewNoticePatientService")
@RequiredArgsConstructor
public class MdGroupReviewNoticePatientServiceImpl extends ServiceImpl<MdGroupReviewNoticePatientMapper, MdGroupReviewNoticePatient> implements MdGroupReviewNoticePatientService {

    private final MdGroupReviewNoticePatientMapper mdGroupReviewNoticePatientMapper;

    /**
     * 分页查询[批量复查通知人员]列表
     *
     * @param page  分页参数
     * @param param MdGroupReviewNoticePatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdGroupReviewNoticePatient> getPage(PageParam<MdGroupReviewNoticePatient> page, MdGroupReviewNoticePatient param) {
        return mdGroupReviewNoticePatientMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdGroupReviewNoticePatient getInfoById(String id) {
        return mdGroupReviewNoticePatientMapper.getInfoById(id);
    }

}


