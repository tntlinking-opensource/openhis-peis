package com.center.medical.abteilung.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.abteilung.bean.dto.SrtPcDto;
import com.center.medical.abteilung.bean.model.SectionResultTwo;
import com.center.medical.abteilung.dao.SectionResultTwoMapper;
import com.center.medical.abteilung.service.SectionResultTwoService;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * KS科室检查结果表-结论词、小结(SectionResultTwo)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-23 10:54:20
 */
@Slf4j
@Service("sectionResultTwoService")
@RequiredArgsConstructor
public class SectionResultTwoServiceImpl extends ServiceImpl<SectionResultTwoMapper, SectionResultTwo> implements SectionResultTwoService {

    private final SectionResultTwoMapper sectionResultTwoMapper;

    /**
     * 分页查询[KS科室检查结果表-结论词、小结]列表
     *
     * @param page  分页参数
     * @param param SectionResultTwo查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SectionResultTwo> getPage(PageParam<SectionResultTwo> page, SectionResultTwo param) {
        return sectionResultTwoMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SectionResultTwo getInfoById(String id) {
        return sectionResultTwoMapper.getInfoById(id);
    }

    /**
     * 获取体检者分检结论
     *
     * @param patientcode 体检号
     * @param dh          体检类型：0.健康体检 1.职业体检 2.综合 3.复查
     * @return
     */
    @Override
    public List<SrtPcDto> getListByPatientCode(String patientcode, int dh) {
        return sectionResultTwoMapper.getListByPatientCode(patientcode, dh);
    }

}

