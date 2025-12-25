package com.center.medical.member.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Peispatientarchive;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.dao.PeispatientarchiveMapper;
import com.center.medical.member.bean.model.Satisfaction;
import com.center.medical.member.bean.param.SatisfactionParam;
import com.center.medical.member.bean.param.SecondInterviewParam;
import com.center.medical.member.bean.param.TotalCountParam;
import com.center.medical.member.bean.param.WpjDataParam;
import com.center.medical.member.bean.vo.AddSatisficingVo;
import com.center.medical.member.bean.vo.FirstInterviewVo;
import com.center.medical.member.bean.vo.TotalCountVo;
import com.center.medical.member.dao.SatisfactionMapper;
import com.center.medical.member.service.SatisfactionService;
import com.center.medical.system.bean.model.SysDepartment;
import com.center.medical.system.dao.SysDepartmentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * KF满意度(Satisfaction)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:51
 */
@Slf4j
@Service("satisfactionService")
@RequiredArgsConstructor
public class SatisfactionServiceImpl extends ServiceImpl<SatisfactionMapper, Satisfaction> implements SatisfactionService {

    private final SatisfactionMapper satisfactionMapper;
    private final PeispatientMapper peispatientMapper;
    private final PeispatientarchiveMapper peispatientarchiveMapper;
    private final SysDepartmentMapper sysDepartmentMapper;

    /**
     * 分页查询[KF满意度]列表
     *
     * @param page              分页参数
     * @param satisfactionParam Satisfaction查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Satisfaction> getPage(PageParam<Satisfaction> page, SatisfactionParam satisfactionParam) {

        //设置开始时间 0点0分0秒
        if (ObjectUtils.isNotEmpty(satisfactionParam.getStartDate())) {
            DateTime startDate = DateUtil.beginOfDay(satisfactionParam.getStartDate());
            satisfactionParam.setStartDate(startDate);
        }
        //设置结束时间 23点59分59秒
        if (ObjectUtils.isNotEmpty(satisfactionParam.getEndDate())) {
            DateTime endDate = DateUtil.endOfDay(satisfactionParam.getEndDate());
            satisfactionParam.setEndDate(endDate);
        }
        return satisfactionMapper.getList(page, satisfactionParam);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Satisfaction getInfoById(String id) {
        return satisfactionMapper.getInfoById(id);
    }


    /**
     * 获取对科室评价不是非常满意的数据
     *
     * @param page
     * @param satisfactionParam
     * @return
     */
    @Override
    public IPage<Satisfaction> getListData(PageParam<Satisfaction> page, SatisfactionParam satisfactionParam) {
        //设置开始时间 0点0分0秒
        if (ObjectUtils.isNotEmpty(satisfactionParam.getStartDate())) {
            DateTime startDate = DateUtil.beginOfDay(satisfactionParam.getStartDate());
            satisfactionParam.setStartDate(startDate);
        }
        //设置结束时间 23点59分59秒
        if (ObjectUtils.isNotEmpty(satisfactionParam.getEndDate())) {
            DateTime startDate = DateUtil.endOfDay(satisfactionParam.getStartDate());
            satisfactionParam.setEndDate(startDate);
        }
        //分页查询
        IPage<Satisfaction> list = satisfactionMapper.getListData(page, satisfactionParam);
        return list;
    }


    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Boolean saOrUp(Satisfaction sf) {
        //数据为空
        if (StringUtils.isBlank(sf.getId())) {
            throw new ServiceException("回访失败，数据不存在");
        } else {
            //设置回访人
            sf.setVisitPerson(SecurityUtils.getUsername());
            //根据体检号查询体检者
            Peispatient pt = peispatientMapper.selectOne(new QueryWrapper<Peispatient>().eq("patientcode", sf.getPersoncode()));
            //查询档案
            if (ObjectUtils.isNotEmpty(pt)) {
                Peispatientarchive pa = peispatientarchiveMapper.getInfoByNo(pt.getPatientarchiveno());
                pa.setRestatus(1);// 设置为已回访状态
                //更新档案表
                peispatientarchiveMapper.updateById(pa);
            }


            //更新
            sf.setVisitText("2");// 跟进内容
            sf.setModifydate(new Date());
            this.updateById(sf);
        }
        return Boolean.TRUE;
    }


    /**
     * 取消不满意
     *
     * @param ids
     * @return
     */
    @Override
    public Boolean cancelDissat(List<String> ids) {
        for (String id : ids) {
            Satisfaction sa = satisfactionMapper.getInfoById(id);
            //如果为空
            if (ObjectUtils.isEmpty(sa)) {
                throw new ServiceException("评价信息已被删除，取消失败！");
            }
            sa.setAppraiseResult("5");
            sa.setModifydate(new Date());
            satisfactionMapper.updateById(sa);
        }
        return Boolean.TRUE;
    }

    /**
     * 满意度排名数据
     *
     * @param page
     * @param satisfactionParam
     * @return
     */
    @Override
    public IPage<Satisfaction> getAnalyseData(PageParam<Satisfaction> page, SatisfactionParam satisfactionParam) {
        //设置开始时间 0点0分0秒
        if (ObjectUtils.isNotEmpty(satisfactionParam.getStartDate())) {
            DateTime startDate = DateUtil.beginOfDay(satisfactionParam.getStartDate());
            satisfactionParam.setStartDate(startDate);
        }
        //设置结束时间 23点59分59秒
        if (ObjectUtils.isNotEmpty(satisfactionParam.getEndDate())) {
            DateTime startDate = DateUtil.endOfDay(satisfactionParam.getStartDate());
            satisfactionParam.setEndDate(startDate);
        }
        return satisfactionMapper.getAnalyseData(page, satisfactionParam);
    }


    /**
     * 更新qt
     *
     * @param sf
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Boolean saveOrUpdateQt(Satisfaction sf) {
        // 判断回访数据是否存在
        if (StringUtils.isBlank(sf.getId())) {
            throw new ServiceException("回访失败，数据不存在");
        } else {
            Satisfaction sfn = satisfactionMapper.selectOne(new QueryWrapper<Satisfaction>().eq("id", sf.getId()).eq("is_delete", 0));
            sf.setVisitPerson(SecurityUtils.getUsername());
            Peispatient pt = peispatientMapper.selectOne(new QueryWrapper<Peispatient>().eq("patientcode", sf.getPersoncode()));
            //体检者档案表
            if (ObjectUtils.isNotEmpty(pt) && StringUtils.isNotEmpty(pt.getPatientarchiveno())) {
                //体检者档案表
                Peispatientarchive pa = peispatientarchiveMapper.getInfoById(pt.getPatientarchiveno());
                pa.setRestatus(1);// 设置为已回访状态
                pa.setModifydate(new Date());
                peispatientarchiveMapper.updateById(pa);
            }
            //更新满意度
//            sfn.setVisitText("2");// 跟进内容
//            sfn.setVisitPerson(SecurityUtils.getUsername());
            sfn.setVisitTime(sf.getVisitTime());
            sfn.setVisitResult(sf.getVisitResult());
            sfn.setVisitNote(sf.getVisitNote());
            sfn.setModifydate(new Date());
            this.updateById(sfn);
        }

        return Boolean.TRUE;
    }

    /**
     * 不分页，根据筛选条件查全部
     *
     * @param satisfactionParam
     * @return
     */
    @Override
    public List<Satisfaction> getAllList(SatisfactionParam satisfactionParam) {
        return satisfactionMapper.getAllList(satisfactionParam);
    }

    /**
     * 分页查询体检满意度所有数据
     *
     * @param page
     * @param satisfactionParam
     * @return
     */
    @Override
    public IPage<AddSatisficingVo> getAddSatisficingPage(PageParam<AddSatisficingVo> page, SatisfactionParam satisfactionParam) {
        //设置开始时间 0点0分0秒
        if (ObjectUtils.isNotEmpty(satisfactionParam.getStartDate())) {
            DateTime startDate = DateUtil.beginOfDay(satisfactionParam.getStartDate());
            satisfactionParam.setStartDate(startDate);
        }
        //设置结束时间 23点59分59秒
        if (ObjectUtils.isNotEmpty(satisfactionParam.getEndDate())) {
            DateTime endDate = DateUtil.endOfDay(satisfactionParam.getStartDate());
            satisfactionParam.setEndDate(endDate);
        }
        //设置登记开始时间
        if (ObjectUtils.isNotEmpty(satisfactionParam.getStartRegTime())) {
            DateTime StartRegTime = DateUtil.beginOfDay(satisfactionParam.getStartRegTime());
            satisfactionParam.setStartRegTime(StartRegTime);
        }
        //设置登记结束时间
        if (ObjectUtils.isNotEmpty(satisfactionParam.getEndRegTime())) {
            DateTime endRegTime = DateUtil.endOfDay(satisfactionParam.getEndRegTime());
            satisfactionParam.setStartRegTime(endRegTime);
        }
        return satisfactionMapper.getAddSatisficingPage(page, satisfactionParam);
    }


    @Override
    public Boolean saOrUpAdd(Satisfaction sf) {
        if (StringUtils.isBlank(sf.getId())) {
            // 判断是否存在重复数据 体检号、评价科室、评价结果不能同时相同
            Satisfaction sfNew = satisfactionMapper.selectOne(new QueryWrapper<Satisfaction>()
                    .eq("personcode", sf.getPersoncode())
                    .eq("division_id", sf.getDivisionId())
                    .eq("appraise_result", sf.getAppraiseResult()));
            if (ObjectUtils.isNotEmpty(sfNew)) {
                // 根据科室ID 获得该科室ID下的科室名称
                SysDepartment qxDepart = sysDepartmentMapper.selectOne(new QueryWrapper<SysDepartment>().eq("is_function", 1)
                        .eq("id", sf.getDivisionId()));
                // 评价结果 1~4
                String[] pj = {"", "非常满意", "满意", "基本满意", "不满意"};
                throw new ServiceException("评价失败!存在体检号："
                        + sf.getPersoncode() + " 评价科室："
                        + qxDepart.getName() + " 评价结果:"
                        + pj[Integer.valueOf(sf.getAppraiseResult())] + "相同数据");
                // 保存
            } else {
                sf.setCreatedate(new Date());
                sf.setIsDelete(0);
                this.save(sf);
            }
            // 编辑
        } else {
            Satisfaction sfNew = satisfactionMapper.selectOne(new QueryWrapper<Satisfaction>().eq("id", sf.getId())
                    .eq("is_delete", 0));
            if (ObjectUtils.isNotEmpty(sfNew)) {
                Satisfaction sfNews = satisfactionMapper.selectOne(new QueryWrapper<Satisfaction>().ne("id", sf.getId())
                        .eq("personcode", sf.getPersoncode()).eq("division_id", sf.getDivisionId())
                        .eq("appraise_result", sf.getAppraiseResult()));
                if (ObjectUtils.isEmpty(sfNews)) {
                    sf.setModifydate(new Date());
                    this.updateById(sf);
                } else {
                    // 根据科室ID 获得该科室ID下的科室名称
                    SysDepartment qxDepart = sysDepartmentMapper.selectOne(new QueryWrapper<SysDepartment>().eq("is_function", 1)
                            .eq("id", sf.getDivisionId()));
                    // 评价结果 1~4
                    String[] pj = {"", "非常满意", "满意", "基本满意", "不满意"};
                    throw new ServiceException("编辑失败!存在体检号："
                            + sf.getPersoncode() + " 评价科室："
                            + qxDepart.getName() + " 评价结果:"
                            + pj[Integer.valueOf(sf.getAppraiseResult())] + "相同数据");
                }
            } else {
                throw new ServiceException("编辑失败！数据已被删除。");
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 不分页，根据筛选条件查全部体检满意度
     *
     * @param satisfactionParam
     * @return
     */
    @Override
    public List<AddSatisficingVo> getAllAddList(SatisfactionParam satisfactionParam) {
        //设置开始时间 0点0分0秒
        if (ObjectUtils.isNotEmpty(satisfactionParam.getStartDate())) {
            DateTime startDate = DateUtil.beginOfDay(satisfactionParam.getStartDate());
            satisfactionParam.setStartDate(startDate);
        }
        //设置结束时间 23点59分59秒
        if (ObjectUtils.isNotEmpty(satisfactionParam.getEndDate())) {
            DateTime endDate = DateUtil.endOfDay(satisfactionParam.getStartDate());
            satisfactionParam.setEndDate(endDate);
        }
        //设置登记开始时间
        if (ObjectUtils.isNotEmpty(satisfactionParam.getStartRegTime())) {
            DateTime StartRegTime = DateUtil.beginOfDay(satisfactionParam.getStartRegTime());
            satisfactionParam.setStartRegTime(StartRegTime);
        }
        //设置登记结束时间
        if (ObjectUtils.isNotEmpty(satisfactionParam.getEndRegTime())) {
            DateTime endRegTime = DateUtil.endOfDay(satisfactionParam.getEndRegTime());
            satisfactionParam.setStartRegTime(endRegTime);
        }
        return satisfactionMapper.getAllAddList(satisfactionParam);
    }

    /**
     * 分页显示未评价的数据
     *
     * @param page
     * @param wpjDataParam
     * @return
     */
    @Override
    public IPage<Peispatient> getWpjData(PageParam<Peispatient> page, WpjDataParam wpjDataParam) {
        return satisfactionMapper.getWpjData(page, wpjDataParam);
    }


    /**
     * 不分页显示未评价的数据
     *
     * @param wpjDataParam
     * @return
     */
    @Override
    public List<Peispatient> getAllWpjData(WpjDataParam wpjDataParam) {
        return satisfactionMapper.getAllWpjData(wpjDataParam);
    }


    /**
     * 分页查询体检满意度
     *
     * @param page
     * @param satisfactionParam
     * @return
     */
    @Override
    public IPage<FirstInterviewVo> getFirstInterviewPage(PageParam<FirstInterviewVo> page, SatisfactionParam satisfactionParam) {
        //设置开始时间 0点0分0秒
        if (ObjectUtils.isNotEmpty(satisfactionParam.getStartDate())) {
            DateTime startDate = DateUtil.beginOfDay(satisfactionParam.getStartDate());
            satisfactionParam.setStartDate(startDate);
        }
        //设置结束时间 23点59分59秒
        if (ObjectUtils.isNotEmpty(satisfactionParam.getEndDate())) {
            DateTime endDate = DateUtil.endOfDay(satisfactionParam.getStartDate());
            satisfactionParam.setEndDate(endDate);
        }
        IPage<FirstInterviewVo> iPage = satisfactionMapper.getFirstInterviewPage(page, satisfactionParam);
        return iPage;
    }

    /**
     * 获取回访全部为非常满意的数据
     *
     * @param page
     * @param satisfactionParam
     * @return
     */
    @Override
    public IPage<FirstInterviewVo> getFcmyListData(PageParam<FirstInterviewVo> page, SatisfactionParam satisfactionParam) {
        //设置开始时间 0点0分0秒
        if (ObjectUtils.isNotEmpty(satisfactionParam.getStartDate())) {
            DateTime startDate = DateUtil.beginOfDay(satisfactionParam.getStartDate());
            satisfactionParam.setStartDate(startDate);
        }
        //设置结束时间 23点59分59秒
        if (ObjectUtils.isNotEmpty(satisfactionParam.getEndDate())) {
            DateTime endDate = DateUtil.endOfDay(satisfactionParam.getEndDate());
            satisfactionParam.setEndDate(endDate);
        }
        IPage<FirstInterviewVo> iPage = satisfactionMapper.getFcmyListData(page, satisfactionParam);
        return iPage;
    }

    /**
     * 增或修改满意度回访数据
     *
     * @param sf
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Boolean saOrUpFirstInterview(Satisfaction sf) {
        // 新增
        if (StringUtils.isBlank(sf.getId())) {
            sf.setVisitPerson(SecurityUtils.getUsername());
            sf.setDivisionReceptionist(1);
            sf.setIsDelete(0);
            Peispatient pt = peispatientMapper.selectOne(new QueryWrapper<Peispatient>().eq("patientcode", sf.getPersoncode()));
            if (ObjectUtils.isEmpty(pt)) {
                throw new ServiceException("请检查对应的Personcode是否正确");
            }
            Peispatientarchive pa = peispatientarchiveMapper.getInfoByNo(pt.getPatientarchiveno());
            if (ObjectUtils.isEmpty(pa)) {
                throw new ServiceException("请检查对应的IdPatientarchive是否正确");
            }
            pa.setRestatus(1);// 设置为已回访状态
            pa.setModifydate(new Date());
            peispatientarchiveMapper.updateById(pa);
            sf.setVisitText("1");// 跟进内容
            sf.setDoctorId(pt.getDoctorapply());
            sf.setCreatedate(new Date());
            save(sf);
        } else {
            //更新
            Satisfaction sfn = satisfactionMapper.getInfoById(sf.getId());
            sf.setVisitPerson(SecurityUtils.getUsername());
            Peispatient pt = peispatientMapper.selectOne(new QueryWrapper<Peispatient>().eq("patientcode", sf.getPersoncode()));
            //体检者档案表
            Peispatientarchive pa = peispatientarchiveMapper.getInfoById(pt.getPatientarchiveno());
            if (ObjectUtils.isNotEmpty(pa)){
                pa.setRestatus(1);// 设置为已回访状态
                pa.setModifydate(new Date());
                peispatientarchiveMapper.updateById(pa);
            }
            sf.setVisitText("1");// 跟进内容
            sf.setModifydate(new Date());
            this.updateById(sf);
        }
        return Boolean.TRUE;
    }

    /**
     * 不满意客户回访导出excel
     *
     * @param satisfactionParam
     * @return
     */
    @Override
    public List<FirstInterviewVo> exportFirstInterview(SatisfactionParam satisfactionParam) {
        //设置开始时间 0点0分0秒
        if (ObjectUtils.isNotEmpty(satisfactionParam.getStartDate())) {
            DateTime startDate = DateUtil.beginOfDay(satisfactionParam.getStartDate());
            satisfactionParam.setStartDate(startDate);
        }
        //设置结束时间 23点59分59秒
        if (ObjectUtils.isNotEmpty(satisfactionParam.getEndDate())) {
            DateTime endDate = DateUtil.endOfDay(satisfactionParam.getStartDate());
            satisfactionParam.setEndDate(endDate);
        }
        return satisfactionMapper.exportFirstInterview(satisfactionParam);
    }


    /**
     * 导出回访全部为非常满意的数据
     *
     * @param satisfactionParam
     * @return
     */
    @Override
    public List<Satisfaction> exportsFirstInterview(SatisfactionParam satisfactionParam) {
        QueryWrapper<Satisfaction> queryWrapper = new QueryWrapper<>();
        // 判断是否为空
        if (StringUtils.isNotEmpty(satisfactionParam.getPatientname())) {
            queryWrapper.like("patientname", satisfactionParam.getPatientname());
        }
        if (StringUtils.isNotEmpty(satisfactionParam.getPersoncode())) {
            queryWrapper.like("personcode", satisfactionParam.getPersoncode());
        }
        if (StringUtils.isNotEmpty(satisfactionParam.getPhone())) {
            queryWrapper.like("phone", satisfactionParam.getPhone());
        }
        if (StringUtils.isNotEmpty(satisfactionParam.getAppraiseResult())) {
            queryWrapper.like("appraiseResult", satisfactionParam.getAppraiseResult());
        }
        if (StringUtils.isNotEmpty(satisfactionParam.getVisitResult())) {
            queryWrapper.like("visitResult", satisfactionParam.getVisitResult());
        }
        //设置开始时间
        if (ObjectUtils.isNotEmpty(satisfactionParam.getStartDate())) {
            DateTime startDate = DateUtil.beginOfDay(satisfactionParam.getStartDate());
            satisfactionParam.setStartDate(startDate);
        }
        //设置结束时间 23点59分59秒
        if (ObjectUtils.isNotEmpty(satisfactionParam.getEndDate())) {
            DateTime endDate = DateUtil.endOfDay(satisfactionParam.getStartDate());
            satisfactionParam.setEndDate(endDate);
        }

        // 分页查询 排除删除数据
        List<Satisfaction> satisfactionList = satisfactionMapper.selectList(new QueryWrapper<Satisfaction>()
                .orderByDesc("modifyDate").ne("appraise_result", "1").eq("is_delete", 0)
                .eq("visit_result", 1));


        return satisfactionList;
    }

    /**
     * 满意度统计分页查询
     *
     * @param page
     * @param totalCountParam
     * @return
     */
    @Override
    public IPage<TotalCountVo> getTotalCountPage(PageParam<TotalCountVo> page, TotalCountParam totalCountParam) {
        return satisfactionMapper.getTotalCountPage(page, totalCountParam);
    }

    /**
     * 不满意客户深访分页查询所有数据
     *
     * @param page
     * @param secondInterviewParam
     * @return
     */
    @Override
    public IPage<Satisfaction> getSecondInterviewPage(PageParam<Satisfaction> page, SecondInterviewParam secondInterviewParam) {
        //设置开始时间 0点0分0秒
        if (ObjectUtils.isNotEmpty(secondInterviewParam.getStartDate())) {
            DateTime startDate = DateUtil.beginOfDay(secondInterviewParam.getStartDate());
            secondInterviewParam.setStartDate(startDate);
        }
        //设置结束时间 23点59分59秒
        if (ObjectUtils.isNotEmpty(secondInterviewParam.getEndDate())) {
            DateTime endDate = DateUtil.endOfDay(secondInterviewParam.getEndDate());
            secondInterviewParam.setEndDate(endDate);
        }
        return satisfactionMapper.getSecondInterviewPage(page, secondInterviewParam);
    }

    /**
     * 更新不满意客户深访数据
     *
     * @param sf
     * @return
     */
    @Override
    public Boolean saOrUpSecondInterview(Satisfaction sf) {
        // 判断回访数据是否存在
        if (StringUtils.isBlank(sf.getId())) {
            throw new ServiceException("回访失败，数据不存在");
        } else {
            sf.setSecondPerson(SecurityUtils.getUsername());
            Peispatient pt = peispatientMapper.getByPatientCode(sf.getPersoncode());
            if (ObjectUtils.isEmpty(pt)) {
                throw new ServiceException("请检查对应的Personcode是否正确");
            }
            Peispatientarchive pa = peispatientarchiveMapper.getInfoByNo(pt.getPatientarchiveno());
            if (ObjectUtils.isEmpty(pa)) {
                throw new ServiceException("请检查对应的IdPatientarchive是否正确");
            }
            pa.setRestatus(1);// 设置为已回访状态
            pa.setModifydate(new Date());
            peispatientarchiveMapper.updateById(pa);
            sf.setVisitText("2");// 跟进内容
            sf.setModifydate(new Date());
            this.updateById(sf);
        }
        return Boolean.TRUE;
    }

    /**
     * 再次不满意数据导出excel
     *
     * @param secondInterviewParam
     * @return
     */
    @Override
    public List<Satisfaction> exportSecondInterview(SecondInterviewParam secondInterviewParam) {
        return satisfactionMapper.exportSecondInterview(secondInterviewParam);
    }

    /**
     * 获取科室回访全部为非常满意的数据
     *
     * @param page
     * @param secondInterviewParam
     * @return
     */
    @Override
    public IPage<Satisfaction> getFcmyListSecondData(PageParam<Satisfaction> page, SecondInterviewParam secondInterviewParam) {
        //设置开始时间 0点0分0秒
        if (ObjectUtils.isNotEmpty(secondInterviewParam.getStartDate())) {
            DateTime startDate = DateUtil.beginOfDay(secondInterviewParam.getStartDate());
            secondInterviewParam.setStartDate(startDate);
        }
        //设置结束时间 23点59分59秒
        if (ObjectUtils.isNotEmpty(secondInterviewParam.getEndDate())) {
            DateTime endDate = DateUtil.endOfDay(secondInterviewParam.getStartDate());
            secondInterviewParam.setEndDate(endDate);
        }
        IPage<Satisfaction> iPage = satisfactionMapper.getFcmyListSecondData(page, secondInterviewParam);
        return iPage;
    }

    /**
     * 导出科室回访全部为非常满意数据
     *
     * @param secondInterviewParam
     * @return
     */
    @Override
    public List<Satisfaction> exportsSecondInterview(SecondInterviewParam secondInterviewParam) {
        //设置开始时间 0点0分0秒
        if (ObjectUtils.isNotEmpty(secondInterviewParam.getStartDate())) {
            DateTime startDate = DateUtil.beginOfDay(secondInterviewParam.getStartDate());
            secondInterviewParam.setStartDate(startDate);
        }
        //设置结束时间 23点59分59秒
        if (ObjectUtils.isNotEmpty(secondInterviewParam.getEndDate())) {
            DateTime endDate = DateUtil.endOfDay(secondInterviewParam.getStartDate());
            secondInterviewParam.setEndDate(endDate);
        }
        List<Satisfaction> list = satisfactionMapper.exportsSecondInterview(secondInterviewParam);
        return list;
    }
}

