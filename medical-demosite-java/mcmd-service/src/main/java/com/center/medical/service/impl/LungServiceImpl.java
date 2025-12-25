package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.vo.GetLungVo;
import com.center.medical.dao.LungMapper;
import com.center.medical.bean.model.Lung;
import com.center.medical.service.LungService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * KS肺功能(Lung)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:05
 */
@Slf4j
@Service("lungService")
@RequiredArgsConstructor
public class LungServiceImpl extends ServiceImpl<LungMapper, Lung> implements LungService {

    private final LungMapper lungMapper;

    /**
     * 分页查询[KS肺功能]列表
     *
     * @param page  分页参数
     * @param param Lung查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Lung> getList(PageParam<Lung> page, Lung param) {
        return lungMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Lung getInfoById(String id) {
        return lungMapper.getInfoById(id);
    }


    /**
     * 获取肺功能数据
     * @param patientcode
     * @return
     */
    @Override
    public List<GetLungVo> getLung(String patientcode) {
        return lungMapper.getLung(patientcode);
    }
}

