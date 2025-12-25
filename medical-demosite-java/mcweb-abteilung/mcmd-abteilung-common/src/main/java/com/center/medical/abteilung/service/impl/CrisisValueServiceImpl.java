package com.center.medical.abteilung.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.abteilung.bean.param.CrisisValueParam;
import com.center.medical.abteilung.bean.param.CrisisValueSaveParam;
import com.center.medical.abteilung.bean.param.CrisisValuesaOrUpParam;
import com.center.medical.abteilung.bean.vo.CrisisValueVo;
import com.center.medical.abteilung.bean.vo.GetKsVo;
import com.center.medical.abteilung.dao.CrisisValueMapper;
import com.center.medical.abteilung.service.CrisisValueService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.sellcrm.bean.model.Riskclient;
import com.center.medical.sellcrm.bean.model.Riskclientcon;
import com.center.medical.sellcrm.bean.vo.AllOrgVo;
import com.center.medical.sellcrm.dao.RiskclientMapper;
import com.center.medical.sellcrm.dao.RiskclientconMapper;
import com.center.medical.sellcrm.dao.SellcustomerMapper;
import com.center.medical.system.dao.SysBranchMapper;
import com.center.medical.system.dao.SysDeptMapper;
import com.center.medical.system.dao.SysUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 高危人员管理表(Riskclient)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-22 19:29:10
 */
@Slf4j
@Service("crisisValueService")
@RequiredArgsConstructor
public class CrisisValueServiceImpl extends ServiceImpl<CrisisValueMapper, Riskclient> implements CrisisValueService {

    private final CrisisValueMapper crisisValueMapper;
    private final SysUserMapper sysUserMapper;
    private final MapperFacade mapperFacade;
    private final SysBranchMapper sysBranchMapper;
    private final RiskclientMapper riskclientMapper;
    private final RiskclientconMapper riskclientconMapper;
    private final SysDeptMapper sysDeptMapper;
    private final SellcustomerMapper sellcustomerMapper;
    private final PeispatientMapper peispatientMapper;

    /**
     * 分页查询[高危人员管理表]列表
     *
     * @param page  分页参数
     * @param param Riskclient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CrisisValueVo> getPage(PageParam<CrisisValueVo> page, CrisisValueParam param) {
        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        //是否是医生
        SysUser sysUser = sysUserMapper.selectUserByUserNo(SecurityUtils.getUserNo());
        param.setIsdoc(sysUser.getIsDoc());
        return crisisValueMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Riskclient getInfoById(String id) {
        return riskclientMapper.getInfoById(id);
    }


    /**
     * 导出
     *
     * @param param
     * @return
     */
    @Override
    public List<CrisisValueVo> getExportData(CrisisValueParam param) {
        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        //是否是医生
        SysUser sysUser = sysUserMapper.selectUserByUserNo(SecurityUtils.getUserNo());
        param.setIsdoc(sysUser.getIsDoc());
        return crisisValueMapper.getExportData(param);
    }

    /**
     * 保存或修改
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saOrUp(CrisisValuesaOrUpParam param) {
        //高危人员管理表
        Riskclient rc = mapperFacade.map(param, Riskclient.class);
        //高危人员关联表
        Riskclientcon rcn = mapperFacade.map(param, Riskclientcon.class);
        //id为空添加
        if (StringUtils.isBlank(rc.getId())) {
            Date now = new Date();
            rc.setReportTime(now);
            rc.setCid(sysBranchMapper.getDefaultCid());
            rc.setHfclzt(0);
            rc.setZjclzt(0);
            //保存
            riskclientMapper.insert(rc);
            String rcId = rc.getId();
            rcn.setRiskid(rcId);// 给Riskclientcon设置外键
            riskclientconMapper.insert(rcn);
        } else {
            //更新
            rc.setCid(sysBranchMapper.getDefaultCid());
            riskclientMapper.updateById(rc);
            //更新关联表
            Riskclientcon riskid = riskclientconMapper.selectOne(new QueryWrapper<Riskclientcon>().eq("riskid", rc.getId()));
            rcn.setId(riskid.getId());
            riskclientconMapper.updateById(rcn);
        }
        return Boolean.TRUE;
    }

    /**
     * 删除高危人员管理表及中间表
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeCrisisValue(List<String> ids) {
        riskclientMapper.deleteBatchIds(ids);
        riskclientconMapper.delete(new QueryWrapper<Riskclientcon>().in("riskid", ids));
        return Boolean.TRUE;
    }


    /**
     * 获取自己分中心下的公司下拉
     *
     * @param page
     * @param key
     * @return
     */
    @Override
    public IPage<AllOrgVo> getListData(PageParam<AllOrgVo> page, String key) {
        String fzxId = SecurityUtils.getCId();
        //去空格
        if (ObjectUtils.isNotEmpty(key)) {
            key = key.trim();
        }
        return sellcustomerMapper.getFzxOrg(page, key, fzxId);
    }


    /**
     * 业务处理
     *
     * @param map
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveYw(CrisisValueSaveParam map) {
        String id = map.getId();
        //高危人员管理表
        Riskclient riskclient = riskclientMapper.getInfoById(id);
        //体检者表
        Peispatient peispatient = peispatientMapper.getByPatientCode(riskclient.getTjid());
        String ywfffs = map.getYwfffs();
        peispatient.setIdInformway(ywfffs);
        riskclient.setYwfffs(ywfffs);
        riskclient.setYwclr(SecurityUtils.getUsername());
        riskclient.setYwclsj(new Date());
        riskclient.setYwbz(map.getYwbz());
        //业务处理状态
        if (StringUtils.isEmpty(map.getYwclzt())) {
            riskclient.setYwclzt(0);
        } else {
            riskclient.setYwclzt(Integer.parseInt(map.getYwclzt()));
        }
        //更新
        peispatientMapper.updateById(peispatient);
        riskclientMapper.updateById(riskclient);
        return Boolean.TRUE;
    }


    /**
     * 回访处理
     *
     * @param map
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveHf(CrisisValueSaveParam map) {
        String id = map.getId();
        Riskclient riskclient = riskclientMapper.getInfoById(id);

        if (riskclient.getZjclzt() != null && riskclient.getZjclzt() == 1) {
            throw new ServiceException("处理失败，此条记录已被专家处理，不能再进行回访处理！");
        }

        Peispatient peispatient = peispatientMapper.getByPatientCode(riskclient.getTjid());
        String hffffs = map.getHffffs();
        peispatient.setIdInformway(hffffs);
        riskclient.setHffffs(hffffs);
        riskclient.setHfclr(map.getHfclr());
        riskclient.setHfclsj(new Date());
        riskclient.setHfbz(map.getHfbz());
        riskclient.setHfclzt(Integer.parseInt(map.getHfclzt()));
        riskclient.setHfclfs(Integer.parseInt(map.getHfclfs()));
        //更新
        peispatientMapper.updateById(peispatient);
        riskclientMapper.updateById(riskclient);
        return Boolean.TRUE;
    }


    /**
     * 专家处理
     *
     * @param map
     * @return
     */
    @Override
    public Boolean saveZj(CrisisValueSaveParam map) {
        String id = map.getId();
        Riskclient riskclient = riskclientMapper.getInfoById(id);
        //回访处理状态：0.已提报 1.继续提报 2.已完成 3.已取消
        if (riskclient.getHfclzt() != null && riskclient.getHfclzt() == 2) {
            throw new ServiceException("处理失败，此条记录完成回访处理，不需要进行专家处理！");
        }

        riskclient.setZjclr(map.getZjclr());
        riskclient.setZjclsj(new Date());
        riskclient.setZjbz(map.getZjbz());
        riskclient.setZjclzt(Integer.parseInt(map.getZjclzt()));
        riskclient.setZjclfs(Integer.parseInt(map.getZjclfs()));
        riskclientMapper.updateById(riskclient);
        return Boolean.TRUE;
    }


    /**
     * 获取科室及体检结果
     * @param patientcode
     * @param key
     * @return
     */
    @Override
    public List<GetKsVo> getKs(String patientcode, String key) {
        //去空格
        if (ObjectUtils.isNotEmpty(key)){
            key = key.trim();
        }
        return crisisValueMapper.getKs(patientcode,key);
    }
}

