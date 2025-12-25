package com.center.medical.member.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.AdvanceVisitWrite;
import com.center.medical.bean.model.Branch;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Peispatientarchive;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.AdvanceVisitWriteMapper;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.dao.PeispatientarchiveMapper;
import com.center.medical.member.bean.param.PTPageParam;
import com.center.medical.member.bean.param.PTsaOrUpParam;
import com.center.medical.member.bean.vo.PreviewingTrackVo;
import com.center.medical.member.dao.PreviewingTrackMapper;
import com.center.medical.member.service.PreviewingTrackService;
import com.center.medical.system.bean.model.SysUserDep;
import com.center.medical.system.bean.vo.AllUserDataVo;
import com.center.medical.system.dao.BranchMapper;
import com.center.medical.system.dao.SysDeptMapper;
import com.center.medical.system.dao.SysUserDepMapper;
import com.center.medical.system.dao.SysUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * QT体检者表(Peispatient)表服务实现类
 *
 * @author ay
 * @since 2023-02-10 14:25:16
 */
@Slf4j
@Service("previewingTrackService")
@RequiredArgsConstructor
public class PreviewingTrackServiceImpl extends ServiceImpl<PreviewingTrackMapper, Peispatient> implements PreviewingTrackService {

    private final PreviewingTrackMapper previewingTrackMapper;
    private final BranchMapper branchMapper;
    private final SysUserMapper sysUserMapper;
    private final SysDeptMapper sysDeptMapper;
    private final PeispatientarchiveMapper peispatientarchiveMapper;
    private final PeispatientMapper peispatientMapper;
    private final AdvanceVisitWriteMapper advanceVisitWriteMapper;
    private final MapperFacade mapperFacade;
    private final SysUserDepMapper sysUserDepMapper;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PreviewingTrackVo> getList(PageParam<PreviewingTrackVo> page, PTPageParam param) {
        param.setFzxid(SecurityUtils.getCId());
        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        //是否来检：-1空 0是 1.否  2再通知
        if (CollectionUtils.isNotEmpty(param.getIsInspect())) {
            //分割
            List<String> inspects = param.getIsInspect();
            List<String> ins = new ArrayList<String>();
            boolean hasEmpty = false;
            for (String str : inspects) {
                if ("-1".equals(str)) {
                    hasEmpty = true;
                } else {
                    ins.add(str);
                }
            }
            //是否为空
            param.setHasEmpty(hasEmpty + "");
            //是否来检去掉-1，再放回去
            param.setIsInspect(ins);
            if (hasEmpty) {
                if (ins.size() == 0) {
                    param.setFlag("true");
                } else {
                    param.setFlag("false");
                }
            }
        }
        return previewingTrackMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peispatient getInfoById(String id) {
        return previewingTrackMapper.getInfoById(id);
    }

    ;


    /**
     * 获取开单医师
     *
     * @param key
     * @return
     */
    @Override
    public List<Map<String, String>> getKdys(String key) {
        /**查所有启用的用户（未删除），不对部门进行限制*/
        //分中心ID
        String cid = SecurityUtils.getCId();
        Branch branch = branchMapper.getInfoByBranchId(cid);
        //分中心名字
        String cname = branch == null ? "" : branch.getFzx();

        if (ObjectUtils.isNotEmpty(key)) {
            key = key.trim().toUpperCase();
        }
        //查询20个启用的用户
        List<AllUserDataVo> list = sysUserMapper.selectEnabledUser(key);

        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        for (int i = 0, s = list.size(); i < s; i++) {
            AllUserDataVo users = list.get(i);
            Map<String, String> map = new HashMap<String, String>();
            map.put("inputCode", users.getInputCode());
            map.put("id", users.getId());
            map.put("name", users.getUsername());
            map.put("cname", cname);
            /**存放部门id*/
            List<SysUserDep> userDlist = sysUserDepMapper.selectList(new QueryWrapper<SysUserDep>().eq("user_id", users.getId()));
            String dname = "";
            int ul = userDlist.size();
            /**存放部门id*/
            List<String> uids = new ArrayList<>();
            for(int j = 0 ; j < ul ; j++){
                SysUserDep ud = userDlist.get(j);
                uids.add(ud.getDepId());
            }

            /**获取用户所在部门*/
            if(uids.size()>0){
                List<SysDept> udd = sysDeptMapper.getByDeptNos(uids);
                if(udd!=null&&udd.size()>0){
                    for(SysDept dep:udd){
                        String depname = dep.getDeptName();
                        dname=dname+depname+",";
                    }
                }
            }

            if(dname.length()>0){dname=dname.substring(0,dname.length()-1);}
            map.put("dname", dname);
            data.add(map);
        }
        return data;
    }


    /**
     * 保存
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saOrUp(PTsaOrUpParam param) {
        Peispatientarchive pa = mapperFacade.map(param, Peispatientarchive.class);
        AdvanceVisitWrite aw = mapperFacade.map(param, AdvanceVisitWrite.class);
        if (StringUtils.isBlank(pa.getId())) {
            throw new ServiceException("系统发生异常，请联系管理员。");
        } else {// 保存
            Peispatient peispatient = peispatientMapper.getInfoById(param.getId());
            Peispatientarchive peispatientarchive = peispatientarchiveMapper.getInfoByNo(peispatient.getPatientarchiveno());
            pa.setId(peispatientarchive.getId());
            //主表记录体检者是否来检
            AdvanceVisitWrite awNews = advanceVisitWriteMapper.selectOne(new QueryWrapper<AdvanceVisitWrite>()
                    .eq("patientarchiveno_id", pa.getId()));
            if (awNews == null) {
                aw.setId(null);
                //回访人
                aw.setVisitId(SecurityUtils.getUsername());
                //档案ID
                aw.setPatientarchivenoId(pa.getId());
                aw.setIsInspected(1);
                advanceVisitWriteMapper.insert(aw);
            } else {
                // 编辑
                AdvanceVisitWrite awNew = advanceVisitWriteMapper.selectOne(new QueryWrapper<AdvanceVisitWrite>()
                        .eq("patientarchiveno_id", pa.getId()));
                aw.setId(awNew.getId());
                advanceVisitWriteMapper.updateById(aw);
            }
        }
        peispatientarchiveMapper.updateById(pa);
        return Boolean.TRUE;
    }


    /**
     * 个检预检回访导出
     *
     * @param param
     * @return
     */
    @Override
    public List<PreviewingTrackVo> getExportData(PTPageParam param) {
        param.setFzxid(SecurityUtils.getCId());
        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        //是否来检：-1空 0是 1.否  2再通知
        if (CollectionUtils.isNotEmpty(param.getIsInspect())) {
            //分割
            List<String> inspects = param.getIsInspect();
            List<String> ins = new ArrayList<String>();
            boolean hasEmpty = false;
            for (String str : inspects) {
                if ("-1".equals(str)) {
                    hasEmpty = true;
                } else {
                    ins.add(str);
                }
            }
            //是否为空
            param.setHasEmpty(hasEmpty + "");
            //是否来检去掉-1，再放回去
            param.setIsInspect(ins);
            if (hasEmpty) {
                if (ins.size() == 0) {
                    param.setFlag("true");
                } else {
                    param.setFlag("false");
                }
            }
        }
        List<PreviewingTrackVo> previewingTrackVoList = previewingTrackMapper.getExportData(param);
        //加序号
        for (int i = 0; i < previewingTrackVoList.size(); i++) {
            PreviewingTrackVo vo = previewingTrackVoList.get(i);
            vo.setRownum(i+1);
        }
        return previewingTrackVoList;
    }
}

