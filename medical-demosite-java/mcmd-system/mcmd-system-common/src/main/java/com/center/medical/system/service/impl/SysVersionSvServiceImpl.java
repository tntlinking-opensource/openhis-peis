package com.center.medical.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysVersion;
import com.center.medical.system.bean.model.SysVersionItem;
import com.center.medical.system.bean.param.SysVersionParam;
import com.center.medical.system.dao.SysVersionMapper;
import com.center.medical.system.service.SysVersionItemService;
import com.center.medical.system.service.SysVersionSvService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 版本控制-版本信息(SysVersion)服务实现类
 *
 * @author makejava
 * @since 2024-03-01 18:02:36
 */
@Slf4j
@Service("sysVersionService")
@RequiredArgsConstructor
public class SysVersionSvServiceImpl extends ServiceImpl<SysVersionMapper, SysVersion> implements SysVersionSvService {

    private final SysVersionMapper sysVersionMapper;
    private final SysVersionItemService sysVersionItemService;

    /**
     * 分页查询[版本控制-版本信息]列表
     *
     * @param page  分页参数
     * @param param SysVersion查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysVersion> getPage(PageParam<SysVersion> page, SysVersionParam param) {
        return sysVersionMapper.getPage(page, param);
    }

    /**
     * 查询[版本控制-版本信息]列表
     *
     * @param param SysVersion查询参数
     * @return 分页数据
     */
    @Override
    public List<SysVersion> getList(SysVersionParam param) {
        return sysVersionMapper.getList(param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SysVersion getInfoById(Integer id) {
        return sysVersionMapper.getInfoById(id);
    }

    /**
     * 获取历史版本信息列表
     *
     * @param page  分页信息
     * @param param 查询条件
     * @return 所有数据
     */
    @Override
    public IPage<SysVersion> getLastList(PageParam<SysVersion> page, SysVersionParam param) {
        IPage<SysVersion> pageList = sysVersionMapper.getLastList(page, param);
        List<SysVersion> records = pageList.getRecords();
        List<Integer> vids = records.stream().map(SysVersion::getId).collect(Collectors.toList());
        List<SysVersionItem> itemList = sysVersionItemService.list(new LambdaQueryWrapper<SysVersionItem>().in(SysVersionItem::getVersionId, vids));
        Map<Integer, List<SysVersionItem>> collect = itemList.stream().collect(Collectors.groupingBy(SysVersionItem::getVersionId));
//        log.info("版本更新小项信息：{}", collect);
        for (SysVersion sysVersion : records) {
            sysVersion.setVersionItemList(collect.get(sysVersion.getId()));
        }
        pageList.setRecords(records);
        return pageList;
    }

    /**
     * 获取最新版本
     *
     * @return
     */
    @Override
    public SysVersion getlastVersion() {
        return sysVersionMapper.getlastVersion();
    }

    @Override
    public Boolean saOrUp(SysVersion sysVersion) {
        if (Objects.isNull(sysVersion.getId())){
            //新增
            if (sysVersion.getVersionFlag() == 1) {
                //更新版本为当前版本
                sysVersion.setFinishTime(new Date());
            }
        }else {
            //更新
            SysVersion versionDb = sysVersionMapper.selectById(sysVersion.getId());
            if (sysVersion.getVersionFlag() == 1 && versionDb.getVersionFlag() != sysVersion.getVersionFlag()) {
                //更新版本为当前版本
                sysVersion.setFinishTime(new Date());
            }
        }
        if (sysVersion.getVersionFlag() == 1) {
            //将其他versionFlag=1设置为0
            SysVersion v = new SysVersion();
            v.setVersionFlag(0);
            sysVersionMapper.update(v, new LambdaUpdateWrapper<SysVersion>().eq(SysVersion::getVersionFlag, 1));
        }
        saveOrUpdate(sysVersion);
        return Boolean.TRUE;
    }

}

