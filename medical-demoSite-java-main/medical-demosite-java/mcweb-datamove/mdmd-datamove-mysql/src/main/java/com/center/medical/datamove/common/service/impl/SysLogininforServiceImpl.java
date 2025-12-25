package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.SysLogininforMapper;
import com.center.medical.datamove.common.bean.model.SysLogininfor;
import com.center.medical.datamove.common.service.SysLogininforService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 系统访问记录(SysLogininfor)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:36
 */
@Slf4j
@Service("sysLogininforService")
@RequiredArgsConstructor
public class SysLogininforServiceImpl extends ServiceImpl<SysLogininforMapper, SysLogininfor> implements SysLogininforService {

    private final SysLogininforMapper sysLogininforMapper;

    /**
     * 分页查询[系统访问记录]列表
     *
     * @param page  分页参数
     * @param param SysLogininfor查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysLogininfor> getPage(PageParam<SysLogininfor> page, SysLogininfor param) {
        return sysLogininforMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键infoId
     * @return 详情信息
     */
    @Override
    public SysLogininfor getInfoById(Long id) {
        return sysLogininforMapper.getInfoById(id);
    }

}


