package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.BaseAdiconExamItemCodeMapper;
import com.center.medical.datamove.oracle.bean.model.BaseAdiconExamItemCode;
import com.center.medical.datamove.oracle.service.BaseAdiconExamItemCodeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (BaseAdiconExamItemCode)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:12:26
 */
@Slf4j
@Service("baseAdiconExamItemCodeService")
@RequiredArgsConstructor
public class BaseAdiconExamItemCodeServiceImpl extends ServiceImpl<BaseAdiconExamItemCodeMapper, BaseAdiconExamItemCode> implements BaseAdiconExamItemCodeService {

    private final BaseAdiconExamItemCodeMapper baseAdiconExamItemCodeMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param BaseAdiconExamItemCode查询参数
     * @return 分页数据
     */
    @Override
    public IPage<BaseAdiconExamItemCode> getPage(PageParam<BaseAdiconExamItemCode> page, BaseAdiconExamItemCode param) {
        return baseAdiconExamItemCodeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public BaseAdiconExamItemCode getInfoById(String id) {
        return baseAdiconExamItemCodeMapper.getInfoById(id);
    }

}


