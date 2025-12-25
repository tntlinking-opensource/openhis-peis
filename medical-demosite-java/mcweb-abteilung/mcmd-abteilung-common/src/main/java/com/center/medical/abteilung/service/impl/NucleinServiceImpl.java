package com.center.medical.abteilung.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.abteilung.bean.model.Nuclein;
import com.center.medical.abteilung.bean.param.NucleinParam;
import com.center.medical.abteilung.dao.NucleinMapper;
import com.center.medical.abteilung.service.NucleinService;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 核酸检测报告上传记录(Nuclein)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-04 20:38:01
 */
@Slf4j
@Service("nucleinService")
@RequiredArgsConstructor
public class NucleinServiceImpl extends ServiceImpl<NucleinMapper, Nuclein> implements NucleinService {

    private final NucleinMapper nucleinMapper;

    /**
     * 分页查询[核酸检测报告上传记录]列表
     *
     * @param page  分页参数
     * @param param Nuclein查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Nuclein> getList(PageParam<Nuclein> page, NucleinParam param) {
        return nucleinMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Nuclein getInfoById(String id) {
        return nucleinMapper.getInfoById(id);
    }

}

