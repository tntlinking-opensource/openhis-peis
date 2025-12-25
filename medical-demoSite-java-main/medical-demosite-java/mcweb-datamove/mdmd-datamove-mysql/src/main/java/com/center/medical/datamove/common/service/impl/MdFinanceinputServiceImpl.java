package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdFinanceinputMapper;
import com.center.medical.datamove.common.bean.model.MdFinanceinput;
import com.center.medical.datamove.common.service.MdFinanceinputService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 销售财务录入表(MdFinanceinput)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:17
 */
@Slf4j
@Service("mdFinanceinputService")
@RequiredArgsConstructor
public class MdFinanceinputServiceImpl extends ServiceImpl<MdFinanceinputMapper, MdFinanceinput> implements MdFinanceinputService {

    private final MdFinanceinputMapper mdFinanceinputMapper;

    /**
     * 分页查询[销售财务录入表]列表
     *
     * @param page  分页参数
     * @param param MdFinanceinput查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdFinanceinput> getPage(PageParam<MdFinanceinput> page, MdFinanceinput param) {
        return mdFinanceinputMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdFinanceinput getInfoById(String id) {
        return mdFinanceinputMapper.getInfoById(id);
    }

}


