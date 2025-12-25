package com.center.medical.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysServiceBranch;
import com.center.medical.system.bean.model.SysVersion;
import com.center.medical.system.bean.model.SysVersionDeploy;
import com.center.medical.system.bean.model.SysVersionService;
import com.center.medical.system.bean.param.SysVersionExecuteParam;
import com.center.medical.system.bean.param.SysVersionServiceParam;
import com.center.medical.system.dao.SysVersionServiceMapper;
import com.center.medical.system.service.SysServiceBranchService;
import com.center.medical.system.service.SysVersionDeployService;
import com.center.medical.system.service.SysVersionServiceService;
import com.center.medical.system.service.SysVersionSvService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 版本控制-系统服务更新包记录(SysVersionService)服务实现类
 *
 * @author makejava
 * @since 2024-03-01 18:02:38
 */
@Slf4j
@Service("sysVersionServiceService")
@RequiredArgsConstructor
public class SysVersionServiceServiceImpl extends ServiceImpl<SysVersionServiceMapper, SysVersionService> implements SysVersionServiceService {

    private final SysVersionServiceMapper sysVersionServiceMapper;
    private final SysServiceBranchService sysServiceBranchService;
    private final SysVersionSvService sysVersionSvService;
    private final SysVersionDeployService sysVersionDeployService;

    /**
     * 分页查询[版本控制-系统服务更新包记录]列表
     *
     * @param page  分页参数
     * @param param SysVersionService查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysVersionService> getPage(PageParam<SysVersionService> page, SysVersionService param) {
        return sysVersionServiceMapper.getPage(page, param);
    }

    /**
     * 查询版本控制-系统服务更新包记录列表
     *
     * @param param 查询条件
     * @return 所有数据
     */
    @Override
    public List<SysVersionService> getList(SysVersionServiceParam param) {
        return sysVersionServiceMapper.getList(param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SysVersionService getInfoById(Integer id) {
        return sysVersionServiceMapper.getInfoById(id);
    }

    /**
     * 执行更新
     *
     * @param param 执行参数
     * @return 新增结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean execute(SysVersionExecuteParam param) {
        //查询版本信息
        SysVersion version = sysVersionSvService.getInfoById(param.getVersionId());
        if (Objects.isNull(version)) {
            throw new ServiceException("系统查询不到该版本信息！");
        }

        LambdaQueryWrapper<SysServiceBranch> wrapper = new LambdaQueryWrapper<SysServiceBranch>().eq(SysServiceBranch::getStatus, 0);
        if (CollectionUtil.isNotEmpty(param.getBranchIds())) {
            wrapper.in(SysServiceBranch::getBranchId, param.getBranchIds());
        }
        List<SysServiceBranch> list = sysServiceBranchService.list(wrapper);
        if (CollectionUtil.isEmpty(list)) {
            throw new ServiceException("没有需要执行更新的分中心服务！");
        }

        // 生成各分中心新版本各种服务的更新记录
        for (SysServiceBranch ssb : list) {
            //查询需要更新的服务更新包记录
            SysVersionService svs = sysVersionServiceMapper.selectOne(new LambdaUpdateWrapper<SysVersionService>()
                    .eq(SysVersionService::getServiceId, ssb.getServiceId()).eq(SysVersionService::getStatus, 0));
            SysVersionDeploy svdDb = sysVersionDeployService.getOne(new LambdaQueryWrapper<SysVersionDeploy>()
                    .eq(SysVersionDeploy::getBranchId, ssb.getBranchId())
                    .eq(SysVersionDeploy::getVersionId, svs.getVersionId())
                    .eq(SysVersionDeploy::getSvsId, svs.getId()));
            if (Objects.nonNull(svdDb)) {
                if (svdDb.getStatus() == 1 || svdDb.getSqlStatus() == 2) {
                    //该中心该系统的该版本已执行过更新或者正在更新中，无需再次执行
                    continue;
                }
                //删掉svdDb
                sysVersionDeployService.removeById(svdDb.getId());
            }

            SysVersionDeploy svd = new SysVersionDeploy();
            svd.setBranchId(ssb.getBranchId());
            svd.setVersionId(svs.getVersionId());
            svd.setIpAddress(svd.getIpAddress());
            svd.setSvsId(svs.getId());
            svd.setServiceName(svs.getServiceName());
            svd.setSqlFile(svs.getSqlFile());
            svd.setSqlStatus(0);
            svd.setSqlRemark("");
            svs.setFilePath(svs.getFilePath());
            svd.setFileStatus(0);
            svd.setFileRemark("");
            svd.setOtherFile(svs.getOtherFile());
            svd.setExecuteTime(ssb.getExecuteTime());
            svd.setStatus(0);
            svd.setIsManual(ssb.getIsManual());
            if (ssb.getIsManual() == 1) {
                svd.setExecuter(ssb.getExecuter());
            }
            svd.setRemark(svs.getRemark());
            sysVersionDeployService.save(svd);

        }
        return Boolean.TRUE;
    }

}

