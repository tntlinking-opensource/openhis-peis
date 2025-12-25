package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdBaseAdiconExamItemCodeMapper;
import com.center.medical.datamove.common.bean.model.MdBaseAdiconExamItemCode;
import com.center.medical.datamove.common.service.MdBaseAdiconExamItemCodeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 艾迪康项目代码表(MdBaseAdiconExamItemCode)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:09
 */
@Slf4j
@Service("mdBaseAdiconExamItemCodeService")
@RequiredArgsConstructor
public class MdBaseAdiconExamItemCodeServiceImpl extends ServiceImpl<MdBaseAdiconExamItemCodeMapper, MdBaseAdiconExamItemCode> implements MdBaseAdiconExamItemCodeService {

    private final MdBaseAdiconExamItemCodeMapper mdBaseAdiconExamItemCodeMapper;

    /**
     * 分页查询[艾迪康项目代码表]列表
     *
     * @param page  分页参数
     * @param param MdBaseAdiconExamItemCode查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdBaseAdiconExamItemCode> getPage(PageParam<MdBaseAdiconExamItemCode> page, MdBaseAdiconExamItemCode param) {
        return mdBaseAdiconExamItemCodeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdBaseAdiconExamItemCode getInfoById(String id) {
        return mdBaseAdiconExamItemCodeMapper.getInfoById(id);
    }

}


