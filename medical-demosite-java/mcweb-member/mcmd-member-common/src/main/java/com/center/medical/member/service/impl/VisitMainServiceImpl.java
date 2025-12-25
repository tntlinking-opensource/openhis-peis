package com.center.medical.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.FailTotalVisit;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.FailTotalVisitMapper;
import com.center.medical.member.bean.model.VisitMain;
import com.center.medical.member.bean.param.VisitMainParam;
import com.center.medical.member.bean.param.VmFormdata;
import com.center.medical.member.bean.vo.VisitMainVo;
import com.center.medical.member.dao.VisitMainMapper;
import com.center.medical.member.service.VisitMainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 与迟检、阳性、不合格样本回访表一对多关联，(VisitMain)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:50
 */
@Slf4j
@Service("visitMainService")
@RequiredArgsConstructor
public class VisitMainServiceImpl extends ServiceImpl<VisitMainMapper, VisitMain> implements VisitMainService {

    private final VisitMainMapper visitMainMapper;
    private final MapperFacade mapperFacade;
    private final FailTotalVisitMapper failTotalVisitMapper;

    /**
     * 分页查询[与迟检、阳性、不合格样本回访表一对多关联，]列表
     *
     * @param page  分页参数
     * @param param VisitMain查询参数
     * @return 分页数据
     */
    @Override
    public IPage<VisitMainVo> getList(PageParam<VisitMainVo> page, VisitMainParam param) {
        return visitMainMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public VisitMain getInfoById(String id) {
        return visitMainMapper.getInfoById(id);
    }

    /**
     * 新增数据
     *
     * @param vmFormdata
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saOrUp(VmFormdata vmFormdata) {

        VisitMain visitMain = mapperFacade.map(vmFormdata, VisitMain.class);
        if (StringUtils.isBlank(visitMain.getId())) {
            // 保存
            visitMain.setType(2);
            visitMain.setIsDelete(0);
            this.save(visitMain);
            //如果此体检号已在不合格样本回访中，回访过，就修改回访为再通知
            FailTotalVisit failTotalVisit = failTotalVisitMapper.selectOne(new QueryWrapper<FailTotalVisit>()
                    .eq("personcode", visitMain.getPatientcode())
                    .eq("visit_main_id", visitMain.getId())
            );
            if (ObjectUtils.isNotEmpty(failTotalVisit)) {
                failTotalVisit.setNoticeAgain(1);
                failTotalVisitMapper.updateById(failTotalVisit);
            }
        }
        return Boolean.TRUE;
    }


    /**
     * 获取导出数据
     *
     * @param param
     * @return
     */
    @Override
    public List<VisitMainVo> getExportDate(VisitMainParam param) {
        return visitMainMapper.getExportDate(param);
    }

    /**
     * 前台迟捡操作
     *
     * @param vm
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveChijian(VisitMain vm) {
        if (StringUtils.isBlank(vm.getId())) {
            // 往VisitMain表中插入需要迟捡的客户
            VisitMain vmNew = visitMainMapper.selectOne(new QueryWrapper<VisitMain>()
                    .eq("id", vm.getId()).eq("patientcode", vm.getPatientcode()).eq("type", vm.getType()));
            if (Objects.isNull(vmNew)) {
                vm.setType(0);//迟捡，区分阳性结果和不合格样本
                visitMainMapper.insert(vm);
            } else {
                throw new ServiceException("保存失败：" + vm.getIdExamfeeitem() + "收费项目迟检失败！");
            }
        }
        return Boolean.TRUE;
    }
}

