package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.FxPersonnelviewMapper;
import com.center.medical.datamove.oracle.bean.model.FxPersonnelview;
import com.center.medical.datamove.oracle.service.FxPersonnelviewService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 表1 疑似职业病人员一览表
 * 表2  职业禁忌证人员一览表
 * 表3职业病危害效应相关指标异常需复查人员一览表
 * 表4 其他疾病异常结果人员一览表
 * 表5 本次职业健康检查未见异常人员一览表(FxPersonnelview)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:18:56
 */
@Slf4j
@Service("fxPersonnelviewService")
@RequiredArgsConstructor
public class FxPersonnelviewServiceImpl extends ServiceImpl<FxPersonnelviewMapper, FxPersonnelview> implements FxPersonnelviewService {

    private final FxPersonnelviewMapper fxPersonnelviewMapper;

    /**
     * 分页查询[表1 疑似职业病人员一览表
     * 表2  职业禁忌证人员一览表
     * 表3职业病危害效应相关指标异常需复查人员一览表
     * 表4 其他疾病异常结果人员一览表
     * 表5 本次职业健康检查未见异常人员一览表]列表
     *
     * @param page  分页参数
     * @param param FxPersonnelview查询参数
     * @return 分页数据
     */
    @Override
    public IPage<FxPersonnelview> getPage(PageParam<FxPersonnelview> page, FxPersonnelview param) {
        return fxPersonnelviewMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public FxPersonnelview getInfoById(String id) {
        return fxPersonnelviewMapper.getInfoById(id);
    }

}


