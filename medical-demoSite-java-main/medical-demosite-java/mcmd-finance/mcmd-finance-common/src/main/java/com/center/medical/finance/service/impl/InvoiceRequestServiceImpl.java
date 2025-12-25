package com.center.medical.finance.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.param.BaseParam;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.param.IRPageParam;
import com.center.medical.finance.bean.param.IRSaOrUpParam;
import com.center.medical.finance.bean.vo.IRPageVo;
import com.center.medical.finance.dao.InvoiceRequestMapper;
import com.center.medical.finance.service.InvoiceRequestService;
import com.center.medical.sellcrm.bean.model.Peisorgreservationreceipt;
import com.center.medical.sellcrm.dao.PeisorgreservationreceiptMapper;
import com.center.medical.sellcrm.service.PeisorgreservationreceiptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 发票管理(Peisorgreservationreceipt)表服务实现类
 *
 * @author ay
 * @since 2023-04-04 11:00:04
 */
@Slf4j
@Service("invoiceRequestService")
@RequiredArgsConstructor
public class InvoiceRequestServiceImpl extends ServiceImpl<InvoiceRequestMapper, Peisorgreservationreceipt> implements InvoiceRequestService {

    private final InvoiceRequestMapper invoiceRequestMapper;
    private final MapperFacade mapperFacade;
    private final PeisorgreservationreceiptMapper peisorgreservationreceiptMapper;
    private final PeisorgreservationreceiptService peisorgreservationreceiptService;

    /**
     * 分页查询[发票]列表
     *
     * @param page  分页参数
     * @param param Peisorgreservationreceipt查询参数
     * @return 分页数据
     */
    @Override
    public IPage<IRPageVo> getList(PageParam<IRPageVo> page, IRPageParam param) {
        //发票管理
        Boolean fpgl = SecurityUtils.hasRole(RoleAuthName.CONTROL_OVER_INVOICES);
        if ( !fpgl) {
            // 查询当前用户的数据
            param.setUserName(SecurityUtils.getUsername());
        }

        //是否可以搜索销售经理
        if (fpgl) {
            // 查询当前用户的数据
            param.setFlag("可以搜索");
        }

        return invoiceRequestMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peisorgreservationreceipt getInfoById(String id) {
        return invoiceRequestMapper.getInfoById(id);
    }


    /**
     * 发票导出
     *
     * @param param
     * @return
     */
    @Override
    public List<IRPageVo> getExportData(IRPageParam param) {
        //发票管理 和 财务权限
        Boolean fpgl = SecurityUtils.hasRole(RoleAuthName.CONTROL_OVER_INVOICES);
        Boolean cwgl = SecurityUtils.hasRole(RoleAuthName.CAIWU);
        if (!fpgl && !cwgl) {
            // 查询当前用户的数据
            param.setUserName(SecurityUtils.getUsername());
        }

        //是否可以搜索销售经理
        if (fpgl || cwgl) {
            // 查询当前用户的数据
            param.setFlag("可以搜索");
        }
        return invoiceRequestMapper.getExportData(param);
    }

    /**
     * 获取图表数据
     *
     * @param param
     * @return
     */
    @Override
    public List<String> getBarData(BaseParam param) {
        List<String> list = new ArrayList<>();
        String barData = invoiceRequestMapper.getBarData(param, 0);
        String barData2 = invoiceRequestMapper.getBarData(param, 1);
        String barData3 = invoiceRequestMapper.getBarData(param, 3);
        list.add(barData);
        list.add(barData2);
        list.add(barData3);
        return list;
    }


    /**
     * 发票申请保存
     *
     * @param param
     * @return
     */
    @Override
    public Boolean saOrUp(IRSaOrUpParam param) {
        //发票
        Peisorgreservationreceipt odis = mapperFacade.map(param, Peisorgreservationreceipt.class);
        //获取当前用户名
        String name = SecurityUtils.getUsername();
        // 判断是否为空
        if (StringUtils.isBlank(odis.getId())) {
            //保存
            //当前登录用户
            odis.setProposer(name);

            odis.setStatus(0);
            Date da = new Date();
            //发表申请时间
            odis.setApplicationTime(da);
            //设置isDelete字段为0
            odis.setIsDelete(0);
            peisorgreservationreceiptMapper.insert(odis);

        } else {
            // 判断是否存在重复数据,排除删除数据的影响
            // 判断是否假删、ID是否重复
            Peisorgreservationreceipt harmNew = peisorgreservationreceiptMapper.getInfoById(odis.getId());
            if (harmNew != null) {
                // 更新实体类
                //当前登录用户
                odis.setProposer(name);
                peisorgreservationreceiptMapper.updateById(odis);
            } else {
                throw new ServiceException("更新失败：对象不存在，请刷新页面");
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 删除发票
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeIds(List<String> ids) {
        List<Peisorgreservationreceipt> list = new ArrayList<>();
        for (String id : ids) {
            //发票
            Peisorgreservationreceipt p = peisorgreservationreceiptMapper.getInfoById(id);
            if (p == null) {
                throw new ServiceException("记录不存在，请刷新重试！");
            }
            if (p.getStatus() != null && 0 != p.getStatus()) {
                throw new ServiceException("只能删除未审核的记录！");
            }
            if (!SecurityUtils.getUsername().equals(p.getProposer())) {
                throw new ServiceException("只能删除自己申请的发票！");
            }
            //已删除
            p.setIsDelete(1);
            list.add(p);
        }
        //批量更新
        if (CollectionUtil.isNotEmpty(list)) {
            peisorgreservationreceiptService.updateBatchById(list);
        }
        return Boolean.TRUE;
    }


    /**
     * 审核
     *
     * @param id
     * @return
     */
    @Override
    public Boolean examine(String id) {
        //发票
        Peisorgreservationreceipt pei = peisorgreservationreceiptMapper.getInfoById(id);
        if (pei != null) {
            //发票状态
            pei.setStatus(1);
            //审核人
            pei.setUapprove(SecurityUtils.getUsername());
            //审核时间
            pei.setUapprovedate(new Date());
            peisorgreservationreceiptMapper.updateById(pei);
        } else {
            throw new ServiceException("更新失败：对象不存在，请刷新页面");
        }
        return Boolean.TRUE;
    }


    /**
     * 反审核
     *
     * @param id
     * @param unauditNote
     * @return
     */
    @Override
    public Boolean unauditSave(String id, String unauditNote) {
        //发票
        Peisorgreservationreceipt pei = peisorgreservationreceiptMapper.getInfoById(id);
        if (pei != null) {
            pei.setStatus(0);
            pei.setUnauditNote(unauditNote);
            pei.setUnauditName(SecurityUtils.getUsername());
            pei.setUnauditDate(new Date());
            peisorgreservationreceiptMapper.updateById(pei);
        } else {
            throw new ServiceException("更新失败：对象不存在，请刷新页面");
        }
        return Boolean.TRUE;
    }


    /**
     * 审核不通过
     *
     * @param id
     * @return
     */
    @Override
    public Boolean unapprove(String id) {
        //发票
        Peisorgreservationreceipt pei = peisorgreservationreceiptMapper.getInfoById(id);
        if (pei != null) {
            //发票状态
            pei.setStatus(6);
            //审核人
            pei.setUapprove(SecurityUtils.getUsername());
            pei.setUapprovedate(new Date());
            peisorgreservationreceiptMapper.updateById(pei);
        } else {
            throw new ServiceException("更新失败：对象不存在，请刷新页面");
        }
        return Boolean.TRUE;
    }


    /**
     * 出票信息保存
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saOrUpPrint(IRSaOrUpParam param) {
        //发票
        Peisorgreservationreceipt odis = mapperFacade.map(param, Peisorgreservationreceipt.class);
        //获取当前用户名
        String name = SecurityUtils.getUsername();
        // 判断是否为空
        if (!StringUtils.isBlank(odis.getId())) {
            // 判断是否假删
            Peisorgreservationreceipt harmNew = peisorgreservationreceiptMapper.getInfoById(odis.getId());
            if (harmNew != null) {
                // 更新实体类
                //当前登录用户
                odis.setStatus(2);
                odis.setIdRemitter(name);
                Date da = new Date();
                odis.setRemittime(da);
                peisorgreservationreceiptMapper.updateById(odis);
            } else {
                throw new ServiceException("更新失败：对象不存在，请刷新页面");
            }
            return Boolean.TRUE;
        }
        throw new ServiceException("记录不存在");
    }

    /**
     * 换票申请保存
     *
     * @param param
     * @return
     */
    @Override
    public Boolean saveReturnApply(IRSaOrUpParam param) {
        String id = param.getId();
        //发票
        Peisorgreservationreceipt p = peisorgreservationreceiptMapper.getInfoById(id);
        if (p.getStatus()!=2 && p.getStatus()!=3) {
            throw new ServiceException("该记录不是已出票状态，无法进行换票申请操作！");
        }
        p.setStatus(3);
        p.setIdReturnApplyer(SecurityUtils.getUsername());
        p.setReturnApplyTime(new Date());
        //原发票号码
        p.setFirstReceiptsheetno(p.getReceiptsheetno());
        p.setTtyy(param.getTtyy());
        peisorgreservationreceiptMapper.updateById(p);
        return Boolean.TRUE;
    }


    /**
     * 换票撤回
     *
     * @param id
     * @return
     */
    @Override
    public Boolean saveReturnCancle(String id) {
        Peisorgreservationreceipt p = peisorgreservationreceiptMapper.getInfoById(id);
        if (p.getStatus() != 3) {
            throw new ServiceException("该记录不是换票申请状态，无法进行换票撤回操作！");
        }
        p.setStatus(2);
        p.setIdReturnApplyer(null);
        p.setReturnApplyTime(null);
        p.setFirstReceiptsheetno(null);
        p.setTtyy(null);
        peisorgreservationreceiptMapper.updateById(p);
        return Boolean.TRUE;
    }


    /**
     * 换票审核
     *
     * @param param
     * @return
     */
    @Override
    public Boolean saveReturnAudit(IRSaOrUpParam param) {
        Peisorgreservationreceipt p = peisorgreservationreceiptMapper.getInfoById(param.getId());
        if (p.getStatus() != 3) {
            throw new ServiceException("该记录不是换票申请状态，无法进行换票审核操作！");
        }
        p.setStatus(4);
        p.setReturnAuditer(SecurityUtils.getUsername());
        p.setReturnAuditTime(new Date());
        peisorgreservationreceiptMapper.updateById(p);
        return Boolean.TRUE;
    }


    /**
     * 换票反审核
     *
     * @param param
     * @return
     */
    @Override
    public Boolean saveReturnUnaudit(IRSaOrUpParam param) {
        Peisorgreservationreceipt p = peisorgreservationreceiptMapper.getInfoById(param.getId());
        if (p.getStatus() != 4) {
            throw new ServiceException("该记录不是换票已审核状态，无法进行换票反审核操作！");
        }
        p.setStatus(3);
        p.setReturnAuditer(null);
        p.setReturnAuditTime(null);
        peisorgreservationreceiptMapper.updateById(p);
        return Boolean.TRUE;
    }

    /**
     * 换票
     *
     * @param param
     * @return
     */
    @Override
    public Boolean saveReturnConfirm(IRSaOrUpParam param) {
        Peisorgreservationreceipt p = peisorgreservationreceiptMapper.getInfoById(param.getId());
        if (p.getStatus() != 4) {
            throw new ServiceException("该记录不是换票已审核状态，无法进行换票操作！");
        }
        p.setStatus(5);
        p.setIdReturner(SecurityUtils.getUsername());
        p.setReturntime(new Date());
        p.setReceiptsheetno(param.getReceiptsheetno());
        peisorgreservationreceiptMapper.updateById(p);
        return Boolean.TRUE;
    }
}


