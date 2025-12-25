package com.center.medical.member.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.FailTotalVisit;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.FailTotalVisitMapper;
import com.center.medical.member.bean.model.VisitMain;
import com.center.medical.member.bean.param.BelowSampleEditParam;
import com.center.medical.member.bean.param.BelowSampleParam;
import com.center.medical.member.bean.vo.BelowSampleEditVo;
import com.center.medical.member.bean.vo.BelowSampleVo;
import com.center.medical.member.dao.BelowSampleMapper;
import com.center.medical.member.dao.VisitMainMapper;
import com.center.medical.member.service.BelowSampleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 与迟检、阳性、不合格样本回访表一对多关联，(VisitMain)表服务实现类
 *
 * @author makejava
 * @since 2023-02-07 08:31:21
 */
@Slf4j
@Service("belowSampleService")
@RequiredArgsConstructor
public class BelowSampleServiceImpl extends ServiceImpl<BelowSampleMapper, VisitMain> implements BelowSampleService {

    private final BelowSampleMapper belowSampleMapper;
    private final MapperFacade mapperFacade;
    private final FailTotalVisitMapper failTotalVisitMapper;
    private final VisitMainMapper visitMainMapper;

    /**
     * 分页查询[与迟检、阳性、不合格样本回访表一对多关联，]列表
     *
     * @param page  分页参数
     * @param param VisitMain查询参数
     * @return 分页数据
     */
    @Override
    public IPage<BelowSampleVo> getList(PageParam<BelowSampleVo> page, BelowSampleParam param) {
        //体检号
        if (ObjectUtils.isNotEmpty(param.getPatientcode())) {
            param.setPatientcode(param.getPatientcode().trim().toUpperCase());
        }
        //体检开始时间
        if (ObjectUtils.isNotEmpty(param.getStartDate())) {
            DateTime startDate = DateUtil.beginOfDay(param.getStartDate());
            param.setStartDate(startDate);
        }
        //体检结束时间
        if (ObjectUtils.isNotEmpty(param.getEndDate())) {
            DateTime endDate = DateUtil.endOfDay(param.getEndDate());
            param.setEndDate(endDate);
        }
        //取出分页数据
        IPage<BelowSampleVo> iPage = belowSampleMapper.getList(page, param);
        List<BelowSampleVo> records = iPage.getRecords();
        //循环添加拼接不合格原因
        for (BelowSampleVo record : records) {
            List<VisitMain> pfs = visitMainMapper.selectList(new QueryWrapper<VisitMain>()
                    .orderByAsc("id").eq("patientcode", record.getPatientcode())
                    .eq("type", 2).eq("is_delete", 0));
            String str = "";
            //拼接不合格原因
            for (int j = 0; j < pfs.size(); j++) {
                str += pfs.get(j).getBelowquestion() + ",";
            }
            str = str.substring(0, str.length() - 1);
            //替换
            String qq = str.replace("1", "溶血").replace("2", "脂血")
                    .replace("3", "凝血").replace("4", "多采集").replace("5", "容器不符").replace("6", "标本量错误").replace("7", "标识不清").replace("8", "TCL");
            record.setBelowquestion(qq);

            //是否全部已处理
            boolean allProcessed = pfs.stream()
                    .allMatch(pf -> ObjectUtils.isNotEmpty(pf.getIsInspected()) && pf.getIsInspected() != 0);
            record.setAllProcess(allProcessed ? 1 : 0);
        }
        iPage.setRecords(records);
        return iPage;
    }

    /**
     * 导出excel
     *
     * @param param
     * @return
     */
    @Override
    public List<BelowSampleVo> export(BelowSampleParam param) {
        //体检号
        if (ObjectUtils.isNotEmpty(param.getPatientcode())) {
            param.setPatientcode(param.getPatientcode().trim().toUpperCase());
        }
        //体检开始时间
        if (ObjectUtils.isNotEmpty(param.getStartDate())) {
            DateTime startDate = DateUtil.beginOfDay(param.getStartDate());
            param.setStartDate(startDate);
        }
        //体检结束时间
        if (ObjectUtils.isNotEmpty(param.getEndDate())) {
            DateTime endDate = DateUtil.endOfDay(param.getEndDate());
            param.setEndDate(endDate);
        }
        //取出分页数据
        List<BelowSampleVo> records = belowSampleMapper.export(param);
        //循环添加拼接不合格原因
        for (BelowSampleVo record : records) {
            List<VisitMain> pfs = visitMainMapper.selectList(new QueryWrapper<VisitMain>()
                    .orderByAsc("id").eq("patientcode", record.getPatientcode())
                    .eq("type", 2).eq("is_delete", 0));
            String str = "";
            //拼接不合格原因
            for (int j = 0; j < pfs.size(); j++) {
                str += pfs.get(j).getBelowquestion() + ",";
            }
            str = str.substring(0, str.length() - 1);
            //替换
            String qq = str.replace("1", "溶血").replace("2", "脂血")
                    .replace("3", "凝血").replace("4", "多采集").replace("5", "容器不符").replace("6", "标本量错误").replace("7", "标识不清").replace("8", "TCL");
            record.setBelowquestion(qq);
        }

        return records;
    }


    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public BelowSampleEditVo getInfoById(String id) {
        BelowSampleEditVo info = belowSampleMapper.getInfoById(id);
        //循环添加拼接不合格原因
        List<VisitMain> pfs = visitMainMapper.selectList(new QueryWrapper<VisitMain>()
                .orderByAsc("id").eq("patientcode", info.getPatientcode())
                .eq("type", 2).eq("is_delete", 0));
        String str = "";
        //拼接不合格原因
        for (int j = 0; j < pfs.size(); j++) {
            str += pfs.get(j).getBelowquestion() + ",";
        }
        str = str.substring(0, str.length() - 1);
        //替换
        String qq = str.replace("1", "溶血").replace("2", "脂血")
                .replace("3", "凝血").replace("4", "多采集").replace("5", "容器不符").replace("6", "标本量错误").replace("7", "标识不清").replace("8", "TCL");
        info.setBelowquestion(qq);
        return info;
    }

    /**
     * 不合格样品回访
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saOrUpBelowSample(BelowSampleEditParam param) {
        VisitMain vm = mapperFacade.map(param, VisitMain.class);
        FailTotalVisit fv = mapperFacade.map(param, FailTotalVisit.class);
        Date visitTime = fv.getVisitTime();
        //获取的时间晚了8小时
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(visitTime);
        calendar.add(Calendar.HOUR_OF_DAY, -8);
        Date newDate = calendar.getTime();
        fv.setVisitTime(newDate);
        fv.setPersoncode(vm.getPatientcode());
        fv.setNoticeAgain(param.getNoticeAgain());
        if (StringUtils.isBlank(vm.getId())) {
            throw new ServiceException("系统发生异常，请联系管理员");
        } else {
            //不合格样本回访
            FailTotalVisit failTotalVisit = failTotalVisitMapper.selectOne(new QueryWrapper<FailTotalVisit>()
                    .eq("personcode", vm.getPatientcode())
                    .eq("visit_main_id", vm.getId())
            );
            // 保存
            if (ObjectUtils.isEmpty(failTotalVisit)) {
                fv.setVisitMainId(param.getId());// 设置与VisitMain关联的id
                fv.setVisitType(2);// 设置为不合格样本回访
                fv.setVisitId(SecurityUtils.getUsername());// 回访人
                fv.setId(null);
                failTotalVisitMapper.insert(fv);
            } else {
                // 更新
                fv.setId(failTotalVisit.getId());
                failTotalVisitMapper.updateById(fv);
            }

            //更新VisitMain表的是否已处理标识
            VisitMain visitMain = new VisitMain();
            visitMain.setIsInspected(1);
            visitMainMapper.update(visitMain, new LambdaQueryWrapper<VisitMain>()
                    .eq(VisitMain::getPatientcode,param.getPatientcode()).isNull(VisitMain::getIsInspected));
        }
        return Boolean.TRUE;
    }

}

