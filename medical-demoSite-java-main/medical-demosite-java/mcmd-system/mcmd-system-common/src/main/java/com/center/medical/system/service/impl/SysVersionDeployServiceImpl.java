package com.center.medical.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysVersionDeploy;
import com.center.medical.system.dao.SysVersionDeployMapper;
import com.center.medical.system.service.SysVersionDeployService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 版本控制-分中心版本更新记录(SysVersionDeploy)服务实现类
 *
 * @author makejava
 * @since 2024-03-01 18:02:37
 */
@Slf4j
@Service("sysVersionDeployService")
@RequiredArgsConstructor
public class SysVersionDeployServiceImpl extends ServiceImpl<SysVersionDeployMapper, SysVersionDeploy> implements SysVersionDeployService {

    private final SysVersionDeployMapper sysVersionDeployMapper;

    /**
     * 分页查询[版本控制-分中心版本更新记录]列表
     *
     * @param page  分页参数
     * @param param SysVersionDeploy查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysVersionDeploy> getPage(PageParam<SysVersionDeploy> page, SysVersionDeploy param) {
        return sysVersionDeployMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SysVersionDeploy getInfoById(String id) {
        return sysVersionDeployMapper.getInfoById(id);
    }

}

