package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdSshyMapper;
import com.center.medical.datamove.common.bean.model.MdSshy;
import com.center.medical.datamove.common.service.MdSshyService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 创建团体客户要选择的所属行业在这里维护(MdSshy)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:47
 */
@Slf4j
@Service("mdSshyService")
@RequiredArgsConstructor
public class MdSshyServiceImpl extends ServiceImpl<MdSshyMapper, MdSshy> implements MdSshyService {

    private final MdSshyMapper mdSshyMapper;

    /**
     * 分页查询[创建团体客户要选择的所属行业在这里维护]列表
     *
     * @param page  分页参数
     * @param param MdSshy查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdSshy> getPage(PageParam<MdSshy> page, MdSshy param) {
        return mdSshyMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdSshy getInfoById(String id) {
        return mdSshyMapper.getInfoById(id);
    }

}


