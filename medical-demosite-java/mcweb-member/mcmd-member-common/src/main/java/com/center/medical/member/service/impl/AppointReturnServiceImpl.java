package com.center.medical.member.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.AdvanceVisit;
import com.center.medical.bean.model.AdvanceVisitWrite;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.AdvanceVisitMapper;
import com.center.medical.member.bean.param.ARsaOrUpParam;
import com.center.medical.member.bean.param.AppointReturnParam;
import com.center.medical.member.bean.vo.AppointReturnVo;
import com.center.medical.member.dao.AppointReturnMapper;
import com.center.medical.member.service.AppointReturnService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 主表记录体检者是否来检，如果来检，显示来检时间，便于筛选，在预约来检未检列表中显示来检时间且是否已检为未检的做出标记，在预约来检未检回访操作时将该状态、来检时间重置，以此进行无限循环(AdvanceVisitWrite)表服务实现类
 *
 * @author makejava
 * @since 2023-02-16 14:03:32
 */
@Slf4j
@Service("appointReturnService")
@RequiredArgsConstructor
public class AppointReturnServiceImpl extends ServiceImpl<AppointReturnMapper, AdvanceVisitWrite> implements AppointReturnService {

    private final AppointReturnMapper appointReturnMapper;
    private final MapperFacade mapperFacade;
    private final AdvanceVisitMapper advanceVisitMapper;


    /**
     * 分页查询[主表记录体检者是否来检，如果来检，显示来检时间，便于筛选，在预约来检未检列表中显示来检时间且是否已检为未检的做出标记，在预约来检未检回访操作时将该状态、来检时间重置，以此进行无限循环]列表
     *
     * @param page  分页参数
     * @param param AdvanceVisitWrite查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AppointReturnVo> getPage(PageParam<AppointReturnVo> page, AppointReturnParam param) {
        //预约开始时间
        if (ObjectUtils.isNotEmpty(param.getStartDate())) {
            DateTime startDate = DateUtil.beginOfDay(param.getStartDate());
            param.setStartDate(startDate);
        }
        //预约结束时间
        if (ObjectUtils.isNotEmpty(param.getEndDate())) {
            DateTime endDate = DateUtil.endOfDay(param.getEndDate());
            param.setEndDate(endDate);
        }
        return appointReturnMapper.getPage(page, param);
    }

	/**
	 * 导出excel
	 * @param param
	 * @return
	 */
	@Override
	public List<AppointReturnVo> export (AppointReturnParam param){
		//预约开始时间
		if (ObjectUtils.isNotEmpty(param.getStartDate())) {
			DateTime startDate = DateUtil.beginOfDay(param.getStartDate());
			param.setStartDate(startDate);
		}
		//预约结束时间
		if (ObjectUtils.isNotEmpty(param.getEndDate())) {
			DateTime endDate = DateUtil.endOfDay(param.getEndDate());
			param.setEndDate(endDate);
		}
		return appointReturnMapper.export( param);
	}

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public AdvanceVisitWrite getInfoById(String id) {
        return appointReturnMapper.getInfoById(id);
    }

    /**
     * 预约来检回访
     * @param
     * @return
     */

    @Override
    @Transactional(rollbackFor=Exception.class)
    public Boolean saOrUp(ARsaOrUpParam param){
        AdvanceVisitWrite aw =mapperFacade.map(param, AdvanceVisitWrite.class);
        AdvanceVisit av = mapperFacade.map(param, AdvanceVisit.class);
        if (StringUtils.isBlank(aw.getId())){
            throw new ServiceException("系统发生异常，请联系管理员。");
        }else{
            AdvanceVisit avs = advanceVisitMapper.selectOne(new QueryWrapper<AdvanceVisit>().eq("vm_id", aw.getId()));
            if (ObjectUtils.isEmpty(avs)){
                //插入
                av.setId(null);
                av.setVisitId(SecurityUtils.getUsername());
                av.setVmId(aw.getId());
                advanceVisitMapper.insert(av);
            }else{
                //更新
                av.setId(avs.getId());
                advanceVisitMapper.updateById(av);
            }
        }
        return Boolean.TRUE;
    }

}

