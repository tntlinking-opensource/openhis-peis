package com.center.medical.reception.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.reception.bean.param.PlusEmParam;
import com.center.medical.reception.bean.vo.PlusEmergencyVo;
import com.center.medical.reception.dao.PlusEmergencyMapper;
import com.center.medical.reception.service.PlusEmergencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

/**
 * QT体检者表(Peispatient)表服务实现类
 *
 * @author ay
 * @since 2023-02-02 15:30:33
 */
@Slf4j
@Service("PlusEmergency")
@RequiredArgsConstructor
public class PlusEmergencyServiceImpl extends ServiceImpl<PlusEmergencyMapper, Peispatient> implements PlusEmergencyService {

    private final PlusEmergencyMapper plusEmergencyMapper;
    private final PeispatientMapper peispatientMapper;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page 分页参数
     * @return 分页数据
     */
    @Override
    public IPage<PlusEmergencyVo> getList(PageParam<PlusEmergencyVo> page, PlusEmParam param) {
        return plusEmergencyMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peispatient getInfoById(String id) {
        return plusEmergencyMapper.getInfoById(id);
    }

    ;


    /**
     * 加急-保存
     *
     * @param patientcode
     * @return
     */
    @Override
    public Boolean SaOrUp(String patientcode) {
        // 判断是否存在重复数据,排除删除数据的影响
        Peispatient peis = peispatientMapper.selectOne(new QueryWrapper<Peispatient>()
                .eq("patientcode", patientcode).eq("isjj", 1));
        if (ObjectUtils.isNotEmpty(peis)) {
            throw new ServiceException("该体检号已经是加急状态 ，请检查输入的是否正确");
        } else {
            Peispatient harmNew = peispatientMapper.getByPatientCode(patientcode);
            if (harmNew != null) {
                // 更新实体类
                //当前登录用户
                harmNew.setIsjj(1);
                this.updateById(harmNew);
            } else {
                throw new ServiceException("体检号不存在，请检查输入的是否正确");
            }
        }
        return Boolean.TRUE;
    }
}

