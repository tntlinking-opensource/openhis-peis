package com.center.medical.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.report.bean.model.TotalVerdict;
import com.center.medical.report.bean.param.TotalVerdictParam;
import com.center.medical.report.bean.vo.TotalVerdictVo;
import com.center.medical.report.dao.TotalVerdictMapper;
import com.center.medical.report.service.TotalVerdictService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 总检管理-总检结论词(TotalVerdict)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-12-08 09:24:22
 */
@Slf4j
@Service("totalVerdictService")
@RequiredArgsConstructor
public class TotalVerdictServiceImpl extends ServiceImpl<TotalVerdictMapper, TotalVerdict> implements TotalVerdictService {

    private final TotalVerdictMapper totalVerdictMapper;

    @Override
    public List<TotalVerdictVo> getList(TotalVerdictParam param) {
        return totalVerdictMapper.getList(param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public TotalVerdict getInfoById(String id) {
        return totalVerdictMapper.getInfoById(id);
    }

    /**
     * 获取健康总检结论词列表数据
     * @param patientno
     * @param dh
     * @return
     */
    @Override
    public List<TotalVerdictVo> getVerdictListData(String patientno, String dh) {
        return totalVerdictMapper.getVerdictListData(patientno,dh);
    }
}

