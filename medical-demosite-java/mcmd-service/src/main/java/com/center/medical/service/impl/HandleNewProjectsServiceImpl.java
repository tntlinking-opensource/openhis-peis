package com.center.medical.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.HandleNewProjects;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.bean.param.HNSOUParam;
import com.center.medical.bean.param.HandleNewParam;
import com.center.medical.bean.vo.HandleNewVo;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.HandleNewProjectsMapper;
import com.center.medical.service.HandleNewProjectsService;
import com.center.medical.service.PeispatientfeeitemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * KS检验科加项处理(HandleNewProjects)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:20
 */
@Slf4j
@Service("handleNewProjectsService")
@RequiredArgsConstructor
public class HandleNewProjectsServiceImpl extends ServiceImpl<HandleNewProjectsMapper, HandleNewProjects> implements HandleNewProjectsService {

    private final HandleNewProjectsMapper handleNewProjectsMapper;
    private final MapperFacade mapperFacade;
    private final PeispatientfeeitemService peispatientfeeitemService;

    /**
     * 分页查询[KS检验科加项处理]列表
     *
     * @param page  分页参数
     * @param param HandleNewProjects查询参数
     * @return 分页数据
     */
    @Override
    public IPage<HandleNewVo> getList(PageParam<HandleNewProjects> page, HandleNewParam param) {
        param.setType(0);
        //去空格大写
        //体检码
        if (ObjectUtils.isNotEmpty(param.getPatientcode())) {
            param.setPatientcode(param.getPatientcode().trim().toUpperCase());
        }
        //姓名
        if (ObjectUtils.isNotEmpty(param.getName())) {
            param.setName(param.getName().trim().toUpperCase());
        }
        //体检团体
        if (ObjectUtils.isNotEmpty(param.getKhdwmcid())) {
            param.setKhdwmcid(param.getKhdwmcid().trim().toUpperCase());
        }
        //开始时间00点00分
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        //结束时间23点59分
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        return handleNewProjectsMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public HandleNewProjects getInfoById(String id) {
        return handleNewProjectsMapper.getInfoById(id);
    }


    /**
     * 处理
     *
     * @param param
     * @return
     */
    @Override
    public Boolean saOrUp(HNSOUParam param) {
        HandleNewProjects handleNewProjects = mapperFacade.map(param, HandleNewProjects.class);
        if (ObjectUtils.isNotEmpty(handleNewProjects)) {
            if (StringUtils.isNotEmpty(handleNewProjects.getId())) {
                // 处理状态
                int status = handleNewProjects.getStatus();
                //处理状态
                if (status == 2 || status == 3) {
                    //已处理
                    handleNewProjects.setIsHandle(1);
                } else {
                    //未处理
                    handleNewProjects.setIsHandle(0);
                }
                //更新
                handleNewProjectsMapper.updateById(handleNewProjects);
            } else {
                throw new ServiceException("该条数据已经被删除");
            }
        } else {
            throw new ServiceException("请填写页面数据");
        }
        return Boolean.TRUE;
    }


    /**
     * 批量处理
     *
     * @param id
     * @param type
     * @return
     */
    @Override
    public Boolean CLSaveBatch(List<String> id, String type) {
        //状态 0未处理，2检验结束
        int kind = type.equals("0") ? 2 : 0;
        //0处理，1反处理
        String info = type.equals("0") ? "处理" : "反处理";
        List<HandleNewProjects> list = new ArrayList<HandleNewProjects>();
        //登录用户id
        String userId = SecurityUtils.getUserNo();
        Date now = new Date();
        for (int i = 0; i < id.size(); i++) {
            HandleNewProjects other = getInfoById(id.get(i));
            if (ObjectUtils.isEmpty(other)) {
                throw new ServiceException(info + "失败：第" + (i + 1) + "条数据不存在，已经被删除");
            }
            other.setHandleNameId(userId);
            //设置状态
            other.setStatus(kind);
            other.setIdea("无");
            //处理时间
            other.setHandleTime(now);
            //是否已处理：0.未处理 1.已处理
            if (kind == 2) {
                other.setIsHandle(1);
            } else {
                other.setIsHandle(0);
            }
            list.add(other);
        }
        //批量更新
        return updateBatchById(list);
    }


    /**
     * 检验科加项自动处理
     *
     * @param patientcode
     */
    @Override
    public void autoProcess(String patientcode) {
        //查询是否有未处理的检验科加项
        List<HandleNewProjects> handleNewProjects = handleNewProjectsMapper.selectList(new LambdaQueryWrapper<HandleNewProjects>()
                .eq(HandleNewProjects::getPatientcode, patientcode)
                .eq(HandleNewProjects::getIsHandle, 0));
//        log.info("检验科加项自动处理,体检号:{},有{}个未处理的",patientcode,handleNewProjects.size());
        for (HandleNewProjects handleNewProject : handleNewProjects) {
            //查询该体检号检验科的收费项目是否已全部获取结果，全部获取结果会改变状态为已检
            long count = peispatientfeeitemService.count(new LambdaQueryWrapper<Peispatientfeeitem>()
                    .eq(Peispatientfeeitem::getIdPatient, patientcode)
                    .eq(Peispatientfeeitem::getId, handleNewProject.getProjectsId())
                    .eq(Peispatientfeeitem::getFExaminated, 1));
            if (count > 0) {
                log.info("检验科加项自动处理项目id:{},count{}",handleNewProject.getProjectsId(),count);
                //已全部获取
                handleNewProject.setStatus(2);
                handleNewProject.setHandleNameId("402848e360c596c50160c99b52270082");//默认处理人是检验科
                handleNewProject.setHandleTime(new Date());
                handleNewProject.setIdea("检验科获取结果自动处理");
                //已处理
                handleNewProject.setIsHandle(1);
                //更新
                handleNewProjectsMapper.updateById(handleNewProject);
            }
        }
    }
}

