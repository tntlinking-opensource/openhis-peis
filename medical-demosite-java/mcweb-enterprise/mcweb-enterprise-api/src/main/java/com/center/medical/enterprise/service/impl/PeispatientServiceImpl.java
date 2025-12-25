package com.center.medical.enterprise.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.enterprise.bean.dto.AgeDistributionDto;
import com.center.medical.enterprise.bean.dto.PhysicalDistributionDto;
import com.center.medical.enterprise.bean.dto.staffDto;
import com.center.medical.enterprise.bean.model.Peispatient;
import com.center.medical.enterprise.bean.vo.KongzhitaiDataVo;
import com.center.medical.enterprise.common.core.domain.entity.SysUser;
import com.center.medical.enterprise.common.util.DateUtils;
import com.center.medical.enterprise.common.util.PageParam;
import com.center.medical.enterprise.common.utils.SecurityUtils;
import com.center.medical.enterprise.mapper.PeispatientMapper;
import com.center.medical.enterprise.service.PeispatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * QT体检者表(Peispatient)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-12-06 10:15:52
 */
@Slf4j
@Service("peispatientService")
@RequiredArgsConstructor
public class PeispatientServiceImpl extends ServiceImpl<PeispatientMapper, Peispatient> implements PeispatientService {

    private final PeispatientMapper peispatientMapper;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Peispatient> getPage(PageParam<Peispatient> page, Peispatient param) {
        return peispatientMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peispatient getInfoById(String id) {
        return peispatientMapper.getInfoById(id);
    }


    /**
     * 通过体检码获取记录
     *
     * @param patientCode
     * @return
     */
    @Override
    public Peispatient getByPatientCode(String patientCode) {
        return peispatientMapper.getByPatientCode(patientCode);
    }

    /**
     * 获取控制台界面数据
     * @return
     */
    @Override
    public KongzhitaiDataVo getKongzhitaiData() {
        KongzhitaiDataVo vo = new KongzhitaiDataVo();
        SysUser user = SecurityUtils.getLoginUser().getUser();
        String customerId = user.getCustomerId();
        boolean isZk= "1".equals(customerId);

        //年度体检人数
        Long yearNum = peispatientMapper.getPeopleNum(DateUtil.beginOfYear(new Date()),
                DateUtil.endOfYear(new Date()),isZk ? null : customerId);
        vo.setYearNum(yearNum);
        //本月体检人数
        Long monthNum = peispatientMapper.getPeopleNum(DateUtil.beginOfMonth(new Date()),
                DateUtil.endOfMonth(new Date()),isZk ? null : customerId);
        vo.setMonthNum(monthNum);
        //上月体检人数
        Date currentMonthBegin = DateUtil.beginOfMonth(new Date());
        Date lastMonthBegin = DateUtil.offsetMonth(currentMonthBegin, -1);
        Date lastMonthEnd = DateUtil.endOfMonth(lastMonthBegin);
        Long lastMonthNum = peispatientMapper.getPeopleNum(lastMonthBegin,
                lastMonthEnd,isZk ? null : customerId);
        vo.setLastMonthNum(lastMonthNum);
        //人员构成
        List<staffDto> staffDtoList = peispatientMapper.getStaff(isZk ? null : customerId);
        vo.setSexData(staffDtoList);
        //年龄分布
        List<AgeDistributionDto> ageDistributionDtoList = peispatientMapper.getAgeDistribution(isZk ? null : customerId);
        List<String> ageyAxis = new ArrayList<String>();
        List<String> agexAxis = new ArrayList<String>();
        for (AgeDistributionDto dto : ageDistributionDtoList) {
            agexAxis.add(dto.getCount());
            ageyAxis.add(dto.getAge());
        }
        vo.setAgexAxis(agexAxis);
        vo.setAgeyAxis(ageyAxis);
        //体检分布情况
        List<PhysicalDistributionDto> physicalDistributionDtoList = peispatientMapper.getPhysicalDistribution(isZk ? null : customerId);
        vo.setTableData(physicalDistributionDtoList);

        return vo;
    }

    /**
     * 获取同一个档案号的其他体检号
     * @param patientarchiveno
     * @param patientcode
     * @return
     */
    @Override
    public List<String> getOtherCode(String patientarchiveno, String patientcode) {
        return peispatientMapper.getOtherCode(patientarchiveno,patientcode);
    }
}

