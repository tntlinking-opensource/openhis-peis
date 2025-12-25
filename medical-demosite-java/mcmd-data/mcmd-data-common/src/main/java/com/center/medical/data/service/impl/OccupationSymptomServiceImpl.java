package com.center.medical.data.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.bean.BeanUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.data.bean.model.OccupationSymptom;
import com.center.medical.data.bean.param.ShowDataParam;
import com.center.medical.data.dao.OccupationSymptomMapper;
import com.center.medical.data.service.OccupationSymptomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JC职业症状(OccupationSymptom)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:30
 */
@Slf4j
@Service("occupationSymptomService")
@RequiredArgsConstructor
public class OccupationSymptomServiceImpl extends ServiceImpl<OccupationSymptomMapper, OccupationSymptom> implements OccupationSymptomService {

    private final OccupationSymptomMapper occupationSymptomMapper;
    private final PeispatientMapper peispatientMapper;
    private final Snowflake snowflake;

    /**
     * 分页查询[JC职业症状]列表
     *
     * @param page  分页参数
     * @param param OccupationSymptom查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OccupationSymptom> getList(PageParam<OccupationSymptom> page, OccupationSymptom param) {
        return occupationSymptomMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public OccupationSymptom getInfoById(String id) {
        return occupationSymptomMapper.getInfoById(id);
    }

    /**
     * 保存或更新
     *
     * @param odis
     * @return
     */
    @Override
    public String saveOrUpdateOcc(OccupationSymptom odis) {
        //获取当前用户名
        String name = SecurityUtils.getLoginUser().getUsername();
        // 判断是否为空
        if (StringUtils.isBlank(odis.getId())) {
            //判断是否存在重复的职业名称,排除删除数据有相同名称的影响
            OccupationSymptom harmNew = occupationSymptomMapper.selectOne(
                    new QueryWrapper<OccupationSymptom>().eq("symptom", odis.getSymptom())
                            .and(wrapper -> wrapper.isNull("is_delete").or().eq("is_delete", 0)));
            if (null != harmNew) {
                return "保存失败！存在相同的名称";
            } else {
                //保存
                //设置isDelete字段为0
                odis.setIsDelete(0);
                odis.setDbUser(name);
                odis.setId(String.valueOf(snowflake.nextId()));
                odis.setCreatedate(new Date());
                this.save(odis);
            }
        } else {
            // 判断是否存在重复数据,排除删除数据的影响
            // 判断是否假删、ID是否重复
            OccupationSymptom harmNew = occupationSymptomMapper.getOccupationSymptomById(odis.getId());
            if (harmNew != null) {
                // 判断名称是否重复
                OccupationSymptom harmNews = occupationSymptomMapper.selectOne(new QueryWrapper<OccupationSymptom>().ne("id", odis.getId())
                        .eq("symptom", odis.getSymptom()).eq("is_delete", 0));
                if (harmNews == null) {
                    // 更新实体类
                    odis.setDbUser(name);
                    odis.setModifydate(new Date());
                    BeanUtils.copyBeanProp(harmNew, odis);
                    this.updateById(harmNew);
                } else {
                    throw new ServiceException("更新失败：" + odis.getSymptom() + " 名称重复");
                }
            } else {
                throw new ServiceException("对象已删除，请刷新页面");
            }
        }
        return "success";
    }

    /**
     * 逻辑删除
     *
     * @param ids
     * @return
     */
    @Override
    public String removeOccu(String ids) {
        String id[] = ids.split(",");
        for (int i = 0; i < id.length; i++) {
            OccupationSymptom zyoc = occupationSymptomMapper.getOccupationSymptomById(id[i]);
            if (null != zyoc) {
                //将isDelete设置为1，为删除
                zyoc.setIsDelete(1);
                this.updateById(zyoc);
            } else {
                return "删除失败";
            }
        }
        return "success";
    }


    /**
     * 弹窗数据,职业性问诊用到
     *
     * @param param
     * @return
     */
    @Override
    public List<OccupationSymptom> getShowData(ShowDataParam param) {
        List<OccupationSymptom> oss = new ArrayList<>();
        String patientCode = param.getPatientCode();
        //体检号
        if (StringUtils.isNotEmpty(patientCode)) {
            //体检者表
            Peispatient patient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>().eq("patientcode", patientCode));
            if (patient != null) {
                //根据接害因素和体检类别查询
                String jhys = patient.getJhys();
                param.setJhys(ObjectUtils.isNotEmpty(jhys)?jhys.split(","):null);
                param.setMedicaltype(patient.getMedicaltype());
                oss = occupationSymptomMapper.getShowData(param);
                //男的不显示月经异常
                if (patient.getIdSex() == 0) {
                    oss = oss.stream()
                            .filter(os -> !"月经异常".equals(os.getSymptom()))
                            .collect(Collectors.toList());
                }
            }
        } else {
            if (StringUtils.isNotBlank(param.getInputCode())) {
                oss = occupationSymptomMapper.selectList(new QueryWrapper<OccupationSymptom>().orderByAsc("input_code")
                        .like("input_code", param.getInputCode().toUpperCase()).eq("is_delete", 0));

            } else {
                oss = occupationSymptomMapper.selectList(new QueryWrapper<OccupationSymptom>().orderByAsc("input_code")
                        .eq("is_delete", 0));
            }
        }
        return oss;
    }
}

