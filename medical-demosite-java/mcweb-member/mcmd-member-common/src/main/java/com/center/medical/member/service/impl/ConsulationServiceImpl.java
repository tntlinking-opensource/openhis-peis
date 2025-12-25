package com.center.medical.member.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.member.bean.model.Consulation;
import com.center.medical.member.bean.param.ConSaOrUpParam;
import com.center.medical.member.bean.param.ConStatisticsParam;
import com.center.medical.member.bean.param.ConsulationParam;
import com.center.medical.member.bean.param.DCSaOrUpParam;
import com.center.medical.member.bean.vo.StatisticsListVo;
import com.center.medical.member.dao.ConsulationMapper;
import com.center.medical.member.service.ConsulationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 科室/总检咨询(Consulation)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:32
 */
@Slf4j
@Service("consulationService")
@RequiredArgsConstructor
public class ConsulationServiceImpl extends ServiceImpl<ConsulationMapper, Consulation> implements ConsulationService {

    private final ConsulationMapper consulationMapper;
    private final MapperFacade mapperFacade;
    private final PeispatientMapper peispatientMapper;

    /**
     * 分页查询[科室/总检咨询]列表
     *
     * @param page  分页参数
     * @param param Consulation查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Consulation> getList(PageParam<Consulation> page, ConsulationParam param) {
        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        return consulationMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Consulation getInfoById(String id) {
        return consulationMapper.getInfoById(id);
    }

    /**
     * 保存
     *
     * @param param
     * @return
     */
    @Override
    public Boolean saOrUp(ConSaOrUpParam param) {
        Consulation con = mapperFacade.map(param, Consulation.class);
        con.setConsultTime(new Date());
        con.setDoctorUsername(SecurityUtils.getUsername());
        //体检者表
        Peispatient patient = peispatientMapper.getByPatientCode(con.getPatientcode());
        con.setConsultName(patient.getPatientname());
        con.setConsultPhone(patient.getPhone());
        String id = con.getId();
        if (StringUtils.isEmpty(id)) {
            consulationMapper.insert(con);
        } else {
            Consulation old = consulationMapper.getInfoById(id);
            if (ObjectUtils.isEmpty(old)) {
                throw new ServiceException("该id不存在!");
            }
            consulationMapper.updateById(con);
        }

        return Boolean.TRUE;
    }


    /**
     * 咨询与随访统计分页查询
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<StatisticsListVo> getStatisticsListData(PageParam<StatisticsListVo> page, ConStatisticsParam param) {
        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        return consulationMapper.getStatisticsListData(page, param);
    }


    /**
     * 科室咨询添加或修改
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean DCSaOrUp(DCSaOrUpParam param) {
        //快速赋值
        Consulation con = mapperFacade.map(param, Consulation.class);
        con.setConsultTime(new Date());
        con.setDoctorUsername(SecurityUtils.getUsername());
        //体检者表
        Peispatient patient = peispatientMapper.getByPatientCode(con.getPatientcode());
        con.setConsultName(patient.getPatientname());
        con.setConsultPhone(patient.getPhone());
        //签名加密
//        if (StringUtils.isNotEmpty(param.getSignPath())) {
//            AttachmentConfig latest = attachmentConfigService.getLatestConfig();
//            con.setConfigId(latest.getId());
//            String signPath = "\\image\\consulation\\"
//                    + new SimpleDateFormat("yyyy-MM-dd").format(new Date())
//                    + "\\"
//                    + UUID.randomUUID() + ".jpg";
//            try {
//                Base64Util.base64StringToImage(param.getSignPath(), latest.getRealPath() + "\\" + signPath);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            con.setSignPath(signPath);
//        } else {
//            con.setConfigId(null);
//            con.setSignPath(null);
//        }
        String id = con.getId();
        if (StringUtils.isEmpty(id)) {
            //插入
            consulationMapper.insert(con);
        } else {
            //更新
            consulationMapper.updateById(con);
        }
        return Boolean.TRUE;
    }
}

