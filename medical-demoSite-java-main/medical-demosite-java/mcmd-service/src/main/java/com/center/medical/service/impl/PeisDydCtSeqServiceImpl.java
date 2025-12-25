package com.center.medical.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.PeisDydCtSeq;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PeisDydCtSeqMapper;
import com.center.medical.service.PeisDydCtSeqService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 体检者导引单CT排序(PeisDydCtSeq)服务实现类
 *
 * @author ay
 * @since 2023-09-01 18:47:31
 */
@Slf4j
@Service("peisDydCtSeqService")
@RequiredArgsConstructor
public class PeisDydCtSeqServiceImpl extends ServiceImpl<PeisDydCtSeqMapper, PeisDydCtSeq> implements PeisDydCtSeqService {

    private final PeisDydCtSeqMapper peisDydCtSeqMapper;

    /**
     * 分页查询[体检者导引单CT排序]列表
     *
     * @param page  分页参数
     * @param param PeisDydCtSeq查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PeisDydCtSeq> getPage(PageParam<PeisDydCtSeq> page, PeisDydCtSeq param) {
        return peisDydCtSeqMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public PeisDydCtSeq getInfoById(String id) {
        return peisDydCtSeqMapper.getInfoById(id);
    }


    /**
     * 通过体检号查询
     * @param patientCode
     * @return
     */
    @Override
    public PeisDydCtSeq getByPatientcode(String patientCode) {
        return peisDydCtSeqMapper.getByPatientcode(patientCode);
    }


    /**
     * 获取当天最后一个序号
     * @param date
     * @return
     */
    @Override
    public Integer getLastSeq(Date date) {
        Integer lastSeq = peisDydCtSeqMapper.getLastSeq(date);
        return ObjectUtils.isNotEmpty(lastSeq)?lastSeq:0;
    }
}

