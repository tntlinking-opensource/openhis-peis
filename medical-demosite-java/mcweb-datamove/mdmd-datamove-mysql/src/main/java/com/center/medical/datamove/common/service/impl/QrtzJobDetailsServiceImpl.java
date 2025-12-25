package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.QrtzJobDetailsMapper;
import com.center.medical.datamove.common.bean.model.QrtzJobDetails;
import com.center.medical.datamove.common.service.QrtzJobDetailsService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 任务详细信息表(QrtzJobDetails)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:31
 */
@Slf4j
@Service("qrtzJobDetailsService")
@RequiredArgsConstructor
public class QrtzJobDetailsServiceImpl extends ServiceImpl<QrtzJobDetailsMapper, QrtzJobDetails> implements QrtzJobDetailsService {

    private final QrtzJobDetailsMapper qrtzJobDetailsMapper;

    /**
     * 分页查询[任务详细信息表]列表
     *
     * @param page  分页参数
     * @param param QrtzJobDetails查询参数
     * @return 分页数据
     */
    @Override
    public IPage<QrtzJobDetails> getPage(PageParam<QrtzJobDetails> page, QrtzJobDetails param) {
        return qrtzJobDetailsMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键schedName
     * @return 详情信息
     */
    @Override
    public QrtzJobDetails getInfoById(String id) {
        return qrtzJobDetailsMapper.getInfoById(id);
    }

}


