package com.center.medical.sellcrm.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.MathUtil;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Monthtarget;
import com.center.medical.sellcrm.bean.param.MTSaOrUpParam;
import com.center.medical.sellcrm.bean.param.MonthtargetParam;
import com.center.medical.sellcrm.bean.vo.MTSummaryVo;
import com.center.medical.sellcrm.bean.vo.MonthtargetVo;
import com.center.medical.sellcrm.dao.MonthtargetMapper;
import com.center.medical.sellcrm.service.MonthtargetService;
import com.center.medical.system.dao.SysUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 销售月度计划(Monthtarget)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:26
 */
@Slf4j
@Service("monthtargetService")
@RequiredArgsConstructor
public class MonthtargetServiceImpl extends ServiceImpl<MonthtargetMapper, Monthtarget> implements MonthtargetService {

    private final MonthtargetMapper monthtargetMapper;
    private final MapperFacade mapperFacade;
    private final SysUserMapper sysUserMapper;

    /**
     * 分页查询[销售月度计划]列表
     *
     * @param page  分页参数
     * @param param Monthtarget查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MonthtargetVo> getList(PageParam<MonthtargetVo> page, MonthtargetParam param) {
        //判断是否是领导和拥有[决策管理]权限，没有就查用户id的
        Boolean isLeader = SecurityUtils.isLeader() || SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE);
        if (!isLeader) {
            param.setUserNo(SecurityUtils.getUserNo());
        }
        IPage<MonthtargetVo> iPage = monthtargetMapper.getList(page, param);
        List<MonthtargetVo> list = iPage.getRecords();
        //计算每个月的完成进度，完成/目标
        for (MonthtargetVo vo : list) {
            vo.setCompletion1(MathUtil.getPercent(vo.getComplete1(),vo.getTarget1()));
            vo.setCompletion2(MathUtil.getPercent(vo.getComplete2(),vo.getTarget2()));
            vo.setCompletion3(MathUtil.getPercent(vo.getComplete3(),vo.getTarget3()));
            vo.setCompletion4(MathUtil.getPercent(vo.getComplete4(),vo.getTarget4()));
            vo.setCompletion5(MathUtil.getPercent(vo.getComplete5(),vo.getTarget5()));
            vo.setCompletion6(MathUtil.getPercent(vo.getComplete6(),vo.getTarget6()));
            vo.setCompletion7(MathUtil.getPercent(vo.getComplete7(),vo.getTarget7()));
            vo.setCompletion8(MathUtil.getPercent(vo.getComplete8(),vo.getTarget8()));
            vo.setCompletion9(MathUtil.getPercent(vo.getComplete9(),vo.getTarget9()));
            vo.setCompletion10(MathUtil.getPercent(vo.getComplete10(),vo.getTarget10()));
            vo.setCompletion11(MathUtil.getPercent(vo.getComplete11(),vo.getTarget11()));
            vo.setCompletion12(MathUtil.getPercent(vo.getComplete12(),vo.getTarget12()));
        }
        iPage.setRecords(list);
        return iPage;
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Monthtarget getInfoById(String id) {
        return monthtargetMapper.getInfoById(id);
    }

    /**
     * 获取总结数据
     * @param param
     * @return
     */
    @Override
    public List<MTSummaryVo> getSummaryData(MonthtargetParam param) {
        //判断是否是领导和拥有[决策管理]权限，没有就查用户id的
        Boolean isLeader = SecurityUtils.isLeader() || SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE);
        if (!isLeader) {
            param.setUserNo(SecurityUtils.getUserNo());
        }
        return monthtargetMapper.getSummaryData(param);
    }


    /**
     * 数据保存或编辑
     * @param param
     * @return
     */
    @Override
    public Boolean saOrUp(MTSaOrUpParam param) {
        //快速赋值
        Monthtarget sellTarget = mapperFacade.map(param, Monthtarget.class);
        if(StringUtils.isEmpty(sellTarget.getId())){
            //保存操作
            String fzxId = sysUserMapper.selectUserByUserNo(param.getUserid()).getCid();
            sellTarget.setXsjlid(param.getUserid());
            sellTarget.setFzxid(fzxId);
            monthtargetMapper.insert(sellTarget);
        }else{
            //编辑操作
            Monthtarget selltarget = monthtargetMapper.getInfoById(sellTarget.getId());
            if (ObjectUtils.isEmpty(selltarget)){
                throw new ServiceException("请确认该id是否正确！");
            }
            this.updateById(sellTarget);
        }
        return Boolean.TRUE;
    }


    /**
     * 导出销售月度目标
     * @param param
     * @return
     */
    @Override
    public List<MonthtargetVo> getExportData(MonthtargetParam param) {
        //判断是否是领导和拥有[决策管理]权限，没有就查用户id的
        Boolean b = SecurityUtils.isLeader() || SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE);
        if (!b) {
            param.setUserNo(SecurityUtils.getUserNo());
        }
        List<MonthtargetVo> list = monthtargetMapper.getExportData(param);
        //计算每个月的完成进度，完成/目标
        for (MonthtargetVo vo : list) {
            vo.setCompletion1(MathUtil.getPercent(vo.getComplete1(),vo.getTarget1()));
            vo.setCompletion2(MathUtil.getPercent(vo.getComplete2(),vo.getTarget2()));
            vo.setCompletion3(MathUtil.getPercent(vo.getComplete3(),vo.getTarget3()));
            vo.setCompletion4(MathUtil.getPercent(vo.getComplete4(),vo.getTarget4()));
            vo.setCompletion5(MathUtil.getPercent(vo.getComplete5(),vo.getTarget5()));
            vo.setCompletion6(MathUtil.getPercent(vo.getComplete6(),vo.getTarget6()));
            vo.setCompletion7(MathUtil.getPercent(vo.getComplete7(),vo.getTarget7()));
            vo.setCompletion8(MathUtil.getPercent(vo.getComplete8(),vo.getTarget8()));
            vo.setCompletion9(MathUtil.getPercent(vo.getComplete9(),vo.getTarget9()));
            vo.setCompletion10(MathUtil.getPercent(vo.getComplete10(),vo.getTarget10()));
            vo.setCompletion11(MathUtil.getPercent(vo.getComplete11(),vo.getTarget11()));
            vo.setCompletion12(MathUtil.getPercent(vo.getComplete12(),vo.getTarget12()));
        }
        return list;
    }


}

