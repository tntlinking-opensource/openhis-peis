package com.center.medical.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Peispatientarchive;
import com.center.medical.bean.param.ArchiveMergeParam;
import com.center.medical.bean.param.ArchiveParam;
import com.center.medical.bean.param.MemberParam;
import com.center.medical.bean.vo.ArchiveVo;
import com.center.medical.bean.vo.MemberVo;
import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.CodeUtil;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.dao.PeispatientarchiveMapper;
import com.center.medical.service.PeispatientService;
import com.center.medical.service.PeispatientarchiveService;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 体检者档案表(Peispatientarchive)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-24 10:08:16
 */
@Slf4j
@Service("peispatientarchiveService")
@RequiredArgsConstructor
public class PeispatientarchiveServiceImpl extends ServiceImpl<PeispatientarchiveMapper, Peispatientarchive> implements PeispatientarchiveService {

    private final PeispatientarchiveMapper peispatientarchiveMapper;
    private final PeispatientMapper peispatientMapper;
    private final ISysBranchService iSysBranchService;
    private final ISysConfigService iSysConfigService;
    private final PeispatientService peispatientService;

    /**
     * 分页查询[体检者档案表]列表
     *
     * @param page  分页参数
     * @param param Peispatientarchive查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MemberVo> getPage(PageParam<MemberVo> page, MemberParam param) {
        return peispatientarchiveMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peispatientarchive getInfoById(String id) {
        return peispatientarchiveMapper.getInfoById(id);
    }

    /**
     * 导出会员列表数据
     *
     * @param param
     */
    @Override
    public List<Peispatientarchive> getList(MemberParam param) {
        return peispatientarchiveMapper.getList(param);
    }

    /**
     * 分页查询档案列表
     *
     * @param page  分页参数
     * @param param 查询条件
     * @return 所有数据
     */
    @Override
    public IPage<ArchiveVo> getArchivePage(PageParam<Peispatientarchive> page, ArchiveParam param) {
        return peispatientarchiveMapper.getArchivePage(page, param);
    }

    /**
     * 新增或者更新档案信息
     *
     * @param archive
     * @return
     */
    @Override
    public String saOrUp(Peispatientarchive archive) {

        // 最后一次体检时间（登记日期）
        if (StringUtils.isBlank(archive.getId())) {
            //新增
            archive.setCreatedate(new Date());
            peispatientarchiveMapper.insert(archive);
        } else {
            //更新
            archive.setModifydate(new Date());
            peispatientarchiveMapper.updateById(archive);
        }


        return archive.getPatientarchiveno();
    }

    /**
     * 合并
     *
     * @param mergeParam
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean merge(ArchiveMergeParam mergeParam) {
        Date now = new Date();
        //取出所有档案
        List<Peispatientarchive> archives = peispatientarchiveMapper.selectList(new LambdaQueryWrapper<Peispatientarchive>()
                .in(Peispatientarchive::getId, mergeParam.getIds())
                .orderByDesc(Peispatientarchive::getCreatedate));
        if (CollectionUtil.isEmpty(archives)) {
            throw new ServiceException("数据已改变，请刷新重试！");
        }
        Peispatientarchive archive = archives.get(0);
        archive.setPatientname(mergeParam.getPatientname());
        archive.setPhone(mergeParam.getPhone());
        archive.setIdcardno(mergeParam.getIdcardno());
        archive.setModifydate(now);
        String patientarchiveno = archive.getPatientarchiveno();
        List<Peispatientarchive> newList = new ArrayList<>();
        newList.add(archive);
        List<String> newPPIdList = new ArrayList<>();

        //更新体检者绑定的档案号
        for (int i = 0; i < archives.size(); i++) {
            Peispatientarchive a = archives.get(i);
            if (Objects.nonNull(a.getIsDelete()) && a.getIsDelete() == 1) {
                throw new ServiceException("数据已改变，请刷新重试！");
            }
            if (i > 0) {
                List<Peispatient> patients = peispatientMapper.selectList(new LambdaQueryWrapper<Peispatient>()
                        .eq(Peispatient::getPatientarchiveno, a.getPatientarchiveno()));
                for (Peispatient patient : patients) {
                    newPPIdList.add(patient.getId());
                }
                a.setIsDelete(1);
                newList.add(a);
            }
        }
        Peispatient peispatient = new Peispatient();
        peispatient.setPatientarchiveno(patientarchiveno);
        peispatientMapper.update(peispatient, new LambdaUpdateWrapper<Peispatient>()
                .in(Peispatient::getId, newPPIdList));

        //更新合并后的档案
        this.saveOrUpdateBatch(newList);

        return Boolean.TRUE;
    }

    /**
     * 会员升级
     *
     * @param ids
     * @return
     */
    @Override
    public Boolean up(List<String> ids) {
        for (int i = 0; i < ids.size(); i++) {
            Peispatientarchive pa = peispatientarchiveMapper.getInfoById(ids.get(i));
            if (Integer.valueOf(pa.getMemberlevel()) == 1) {
                pa.setMemberlevel("2");
                this.updateById(pa);
            } else if (Integer.valueOf(pa.getMemberlevel()) == 2) {
                pa.setMemberlevel("3");
                this.updateById(pa);
            } else {
                throw new ServiceException("升级失败！客户已是VVIP会员");
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 绑定体检者档案
     *
     * @param peispatient 体检者信息
     * @param isBatch     是否批量
     * @param membercreate     创建者
     * @return 返回档案号
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String bingIArchive(Peispatient peispatient, Boolean isBatch,String membercreate) {
        //需要再完善档案号生成规则和逻辑
        String patientarchiveno = peispatient.getPatientarchiveno();
        //判断两个证件号或身份证号是否相等
        if (StringUtils.isNotEmpty(patientarchiveno)){
            Peispatientarchive archive = peispatientarchiveMapper.selectOne(new LambdaQueryWrapper<Peispatientarchive>()
                    .eq(Peispatientarchive::getPatientarchiveno, patientarchiveno));
            //档案为空 或者 不相同就重新创建档案
            if (ObjectUtils.isEmpty(archive) || StringUtils.isEmpty(archive.getIdcardno()) || !archive.getIdcardno().equals(peispatient.getIdcardno())){
                patientarchiveno = null;
            }
        }
        if (StringUtils.isBlank(patientarchiveno)) {
            //查看是否拥有满足条件的档案
            LambdaQueryWrapper<Peispatientarchive> paWrapper = new LambdaQueryWrapper<>();
            if (StringUtils.isNotBlank(peispatient.getIdcardno())) {
                paWrapper.eq(Peispatientarchive::getIdcardno, peispatient.getIdcardno())
                        .eq(Peispatientarchive::getIsDelete,0);
                List<Peispatientarchive> alist = peispatientarchiveMapper.selectList(paWrapper);
                if (alist.size() >= 2) {
                    // 前台弹出档案列表进行选择
                    throw new ServiceException("存在多个档案，请去档案合并处合并!");
                } else if (alist.size() == 1) {
                    // 匹配到唯一档案
                    Peispatientarchive archive = alist.get(0);
                    // 档案号
                    patientarchiveno = archive.getPatientarchiveno();
                    // 更新档案黑名单
                    archive.setIshmd(peispatient.getIsHmd());
                    archive.setHmdbz(peispatient.getIsHmdb());
                    // 最后一次体检时间（登记日期）
                    archive.setDateregister(new Date());
                    // 档案是否团检（0：个人 1：团检）
                    archive.setIsOrg(StringUtils.isBlank(peispatient.getIdOrg()) ? 0 : 1);
                    // 保存、登记时要修改电话
                    archive.setPhone(peispatient.getPhone());

                    String archiveClass = archive.getMemberlevel();
                    String patientClass = peispatient.getIdPatientclass();// 必填字段
                    if (archiveClass == null) {
                        archive.setMemberlevel(patientClass);
                    } else {
                        int i = archiveClass.compareTo(patientClass);
                        if (i < 0) {//// 体检者的等级如果修改大于档案中的登记，就修改档案中的等级
                            archive.setMemberlevel(patientClass);
                        } else if (i > 0) {// 如果小于档案中的等级，就直接调取档案中的登记，覆盖体检者的等级。
                        }
                    }
                    peispatientarchiveMapper.updateById(archive);
                } else if (CollectionUtil.isEmpty(alist)) {
                    // 未匹配到 创建新档案
                    Peispatientarchive archive = new Peispatientarchive();
                    archive.setDateregister(new Date());
                    archive.setPatientname(peispatient.getPatientname());
                    archive.setIdSex(peispatient.getIdSex());
                    archive.setAge(peispatient.getAge());
                    archive.setMemberlevel(peispatient.getIdPatientclass());
                    archive.setIshmd(peispatient.getIsHmd());
                    archive.setHmdbz(peispatient.getIsHmdb());
                    archive.setIdcardno(peispatient.getIdcardno());
                    archive.setInputCode(ToolUtil.getHanziPinyinHeadChar(peispatient.getPatientname()));
                    archive.setBirthdate(peispatient.getBirthdate());
                    archive.setIdMarriage(String.valueOf(peispatient.getIdMarriage()));
                    // 分中心
                    archive.setFzx(SecurityUtils.getCId());
                    // 生成档案号，需要根据REDIS版本自增
                    patientarchiveno = getArchiveCode();
                    archive.setPatientarchiveno(patientarchiveno);
                    archive.setPhone(peispatient.getPhone());
                    archive.setMembercreate(membercreate);
                    // 保存档案
                    saOrUp(archive);
                    if (StringUtils.isBlank(patientarchiveno)) {
                        throw new ServiceException("保存失败：创建档案失败!");
                    }
                }
                return patientarchiveno;
            } else {
                throw new ServiceException("创建档案失败，证件号不能为空！");
            }
        } else {
            //判断档案是否存在
            Peispatientarchive archive = peispatientarchiveMapper.selectOne(new LambdaQueryWrapper<Peispatientarchive>()
                    .eq(Peispatientarchive::getPatientarchiveno, patientarchiveno));
            if (Objects.isNull(archive)) {
                throw new ServiceException("保存失败：选择的档案不存在，已经被删除!");
            }
            // 更新档案黑名单
            archive.setIshmd(peispatient.getIsHmd());
            archive.setHmdbz(peispatient.getIsHmdb());
            // 最后一次体检时间（登记日期）
            archive.setDateregister(new Date());
            // 档案是否团检（0：个人 1：团检）
            archive.setIsOrg(StringUtils.isBlank(peispatient.getIdOrg()) ? 0 : 1);
            // 保存、登记时要修改电话
            archive.setPhone(peispatient.getPhone());

            String archiveClass = archive.getMemberlevel();
            String patientClass = peispatient.getIdPatientclass();// 必填字段
            if (StringUtils.isBlank(archiveClass)) {
                archive.setMemberlevel(patientClass);
            } else {
                int i = archiveClass.compareTo(patientClass);
                if (i < 0) {// 体检者的等级如果修改大于档案中的登记，就修改档案中的等级
                    archive.setMemberlevel(patientClass);
                } else if (i > 0) {// 如果小于档案中的等级，就直接调取档案中的登记，覆盖体检者的等级。
                }
            }
            peispatientarchiveMapper.updateById(archive);
            return patientarchiveno;
        }
    }

    /**
     * 生成唯一的档案号
     *
     * @return 体检号
     */
    @Override
    public String getArchiveCode() {
        // 分中心前缀
        String prefix = ZhongkangConfig.isOnline() ? "00" : iSysBranchService.getBranchPrefix();
        String version = iSysConfigService.selectConfigByKey(Constants.VERSION_NO);
        Long count = 0L;
        String recordNo = "";
        do {
            recordNo = CodeUtil.getArchiveCode(prefix, version);
            count = peispatientarchiveMapper.selectCount(new LambdaQueryWrapper<Peispatientarchive>().eq(Peispatientarchive::getPatientarchiveno, recordNo));
        } while (count > 0);
        return recordNo;
    }

    @Override
    public Peispatientarchive getInfoByNo(String patientarchiveno) {
        return peispatientarchiveMapper.getInfoByNo(patientarchiveno);
    }

    /**
     * 重新绑定档案
     * @param codes
     * @return
     */
    @Override
    public Boolean reBingArchive(List<String> codes) {
        List<Peispatient> peispatientList = new ArrayList<>();
        List<Peispatientarchive> peispatientarchiveList = new ArrayList<>();
        for (String code : codes) {
            Peispatient peispatient = peispatientMapper.getByPatientCode(code);
            if (ObjectUtils.isEmpty(peispatient)){
                continue;
            }
            //删掉之前的档案
            peispatientarchiveMapper.delete(new LambdaQueryWrapper<Peispatientarchive>()
                    .eq(Peispatientarchive::getIdcardno, peispatient.getIdcardno()));
            //生成新档案
            Peispatientarchive archive = new Peispatientarchive();
            archive.setDateregister(new Date());
            archive.setPatientname(peispatient.getPatientname());
            archive.setIdSex(peispatient.getIdSex());
            archive.setAge(peispatient.getAge());
            archive.setMemberlevel(peispatient.getIdPatientclass());
            archive.setIshmd(peispatient.getIsHmd());
            archive.setHmdbz(peispatient.getIsHmdb());
            archive.setIdcardno(peispatient.getIdcardno());
            archive.setInputCode(ToolUtil.getHanziPinyinHeadChar(peispatient.getPatientname()));
            archive.setBirthdate(peispatient.getBirthdate());
            archive.setIdMarriage(String.valueOf(peispatient.getIdMarriage()));
            archive.setFzx(SecurityUtils.getCId());
            archive.setPatientarchiveno(getArchiveCode());
            archive.setPhone(peispatient.getPhone());
            archive.setMembercreate(SecurityUtils.getUserNo());
            peispatientarchiveList.add(archive);

            peispatient.setPatientarchiveno(archive.getPatientarchiveno());
            peispatientList.add(peispatient);
        }
        peispatientService.updateBatchById(peispatientList);
        saveBatch(peispatientarchiveList);
        return Boolean.TRUE;
    }
}

