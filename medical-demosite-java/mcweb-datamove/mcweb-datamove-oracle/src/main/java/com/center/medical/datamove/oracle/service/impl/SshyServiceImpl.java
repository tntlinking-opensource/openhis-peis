package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.SshyMapper;
import com.center.medical.datamove.oracle.bean.model.Sshy;
import com.center.medical.datamove.oracle.service.SshyService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 创建团体客户要选择的所属行业在这里维护(Sshy)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:25:05
 */
@Slf4j
@Service("sshyService")
@RequiredArgsConstructor
public class SshyServiceImpl extends ServiceImpl<SshyMapper, Sshy> implements SshyService {

    private final SshyMapper sshyMapper;

    /**
     * 分页查询[创建团体客户要选择的所属行业在这里维护]列表
     *
     * @param page  分页参数
     * @param param Sshy查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Sshy> getPage(PageParam<Sshy> page, Sshy param) {
        return sshyMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Sshy getInfoById(String id) {
        return sshyMapper.getInfoById(id);
    }

    ;

}


