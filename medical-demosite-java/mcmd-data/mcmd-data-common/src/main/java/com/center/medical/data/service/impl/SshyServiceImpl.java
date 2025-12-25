package com.center.medical.data.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Sshy;
import com.center.medical.data.dao.SshyMapper;
import com.center.medical.data.service.SshyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 创建团体客户要选择的所属行业在这里维护(Sshy)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-19 14:11:08
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

}

