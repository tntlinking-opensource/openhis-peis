package com.center.medical.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.PeisState;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PeisStateMapper;
import com.center.medical.service.PeisStateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 体检者上传状态(PeisState)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:15
 */
@Slf4j
@Service("peisStateService")
@RequiredArgsConstructor
public class PeisStateServiceImpl extends ServiceImpl<PeisStateMapper, PeisState> implements PeisStateService {

    private final PeisStateMapper peisStateMapper;

    /**
     * 分页查询[体检者上传状态]列表
     *
     * @param page  分页参数
     * @param param PeisState查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PeisState> getList(PageParam<PeisState> page, PeisState param) {
        return peisStateMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public PeisState getInfoById(String id) {
        return peisStateMapper.getInfoById(id);
    }

    /**
     * 设置上传标识
     *
     * @param patientcode
     * @param scbs
     * @return
     */
    @Override
    public PeisState setScbs(String patientcode, int scbs) {
        //上传标识可能不一样，造成会出现两条的情况，所以直接查体检号
        PeisState ps = peisStateMapper.getByPatientcode(patientcode);
        if (ps == null) {
            ps = new PeisState(patientcode);
            ps.setScbs(scbs);
            save(ps);
        } else {
            ps.setScbs(scbs);
            updateById(ps);
        }
        return ps;
    }

    /**
     * 记录体检者状态
     *
     * @param peispatient
     * @param value
     */
    @Override
    public void saOrUp(Peispatient peispatient, int value) {
        PeisState ps = peisStateMapper.selectOne(new LambdaQueryWrapper<PeisState>().eq(PeisState::getPatientcode, peispatient.getPatientcode()));

        if (Objects.isNull(ps)) {
            ps = new PeisState(peispatient.getPatientcode());
            ps.setIdPatientclass2(value);
            peisStateMapper.insert(ps);
        } else {
            ps.setIdPatientclass2(value);
            peisStateMapper.updateById(ps);
        }
    }


    /**
     * 根据体检号获取记录详情
     *
     * @param patientcode 体检号
     * @return
     */
    @Override
    public PeisState getByPatientcode(String patientcode) {
        return peisStateMapper.getByPatientcode(patientcode);
    }
}

