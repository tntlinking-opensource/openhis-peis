package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdSectionResultTwoMapper;
import com.center.medical.datamove.common.bean.model.MdSectionResultTwo;
import com.center.medical.datamove.common.service.MdSectionResultTwoService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS科室检查结果表-结论词、小结(MdSectionResultTwo)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:37
 */
@Slf4j
@Service("mdSectionResultTwoService")
@RequiredArgsConstructor
public class MdSectionResultTwoServiceImpl extends ServiceImpl<MdSectionResultTwoMapper, MdSectionResultTwo> implements MdSectionResultTwoService {

    private final MdSectionResultTwoMapper mdSectionResultTwoMapper;

    /**
     * 分页查询[KS科室检查结果表-结论词、小结]列表
     *
     * @param page  分页参数
     * @param param MdSectionResultTwo查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdSectionResultTwo> getPage(PageParam<MdSectionResultTwo> page, MdSectionResultTwo param) {
        return mdSectionResultTwoMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdSectionResultTwo getInfoById(String id) {
        return mdSectionResultTwoMapper.getInfoById(id);
    }

}


