package com.center.medical.sellcrm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Branch;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.CustomerOperateHistory;
import com.center.medical.sellcrm.bean.model.CustomerTransfer;
import com.center.medical.sellcrm.bean.model.Sellcustomer;
import com.center.medical.sellcrm.bean.param.SaveTransferParam;
import com.center.medical.sellcrm.dao.CustomerOperateHistoryMapper;
import com.center.medical.sellcrm.dao.CustomerTransferMapper;
import com.center.medical.sellcrm.dao.SellcustomerMapper;
import com.center.medical.sellcrm.service.CustomerTransferService;
import com.center.medical.system.bean.param.XsryDataParam;
import com.center.medical.system.bean.vo.XsryVo;
import com.center.medical.system.dao.BranchMapper;
import com.center.medical.system.dao.SysUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 客户转移：转移时如果销售经理已离职，需要修改所有未登记的体检者的开单医师为新的销售经理。(CustomerTransfer)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-21 09:02:50
 */
@Slf4j
@Service("customerTransferService")
@RequiredArgsConstructor
public class CustomerTransferServiceImpl extends ServiceImpl<CustomerTransferMapper, CustomerTransfer> implements CustomerTransferService {

    private final CustomerTransferMapper customerTransferMapper;
    private final SysUserMapper sysUserMapper;
    private final BranchMapper branchMapper;
    private final SellcustomerMapper sellcustomerMapper;
    private final CustomerOperateHistoryMapper customerOperateHistoryMapper;

    /**
     * 分页查询[客户转移：转移时如果销售经理已离职，需要修改所有未登记的体检者的开单医师为新的销售经理。]列表
     *
     * @param page  分页参数
     * @param param CustomerTransfer查询参数
     * @return 分页数据
     */
    @Override
    public IPage<XsryVo> getPage(PageParam<XsryVo> page, XsryDataParam param) {
        //没有决策管理只能看自己分中心的
        if (!SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE)) {
            param.setCid(SecurityUtils.getCId());
        }
        //销售部
        param.setName("销售部");
        //输入码去空格
        if (ObjectUtils.isNotEmpty(param.getKey())){
            param.setKey(param.getKey().trim());
        }
        return sysUserMapper.getXsry(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public CustomerTransfer getInfoById(String id) {
        return customerTransferMapper.getInfoById(id);
    }

    /**
     * 客户转移-转移参数
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveTransfer(SaveTransferParam param) {
        SysUser toXsjl = sysUserMapper.selectUserByUserNo(param.getId());
        String toXsjlname = toXsjl.getUserName();
        String operateName = SecurityUtils.getUsername();
        String operateId = SecurityUtils.getUserId().toString();
        String operateType = "客户转移";
        Integer transType = Integer.parseInt(param.getKey());
        List<Branch> branches = branchMapper.selectList(new QueryWrapper<Branch>().eq("is_delete", 0));
        //客户id
        for (String khid : param.getIds()) {
            //我的客户表
            Sellcustomer sc = sellcustomerMapper.getInfoById(khid);
            String fromXsjlid = sc.getXsjlid();
            //用户id相同就跳过
            if (fromXsjlid.equals(param.getId())) {
                continue;
            }
            SysUser fromXsjl = sysUserMapper.selectUserByUserNo(fromXsjlid);
            String fromXsjlname = fromXsjl.getUserName();

            //直接修改id，统计时属于同一个单位
            sc.setXsjlid(param.getId());
            sc.setXsjl(toXsjlname);
            sellcustomerMapper.updateById(sc);

            //保存操作记录
            CustomerOperateHistory history = new CustomerOperateHistory(
                    operateId
                    , sc.getId()
                    , sc.getKhdwmc()
                    , operateType
                    , param.getId()
                    , toXsjlname
                    , operateName
                    , null
                    , null);
            history.setFromXsjlid(fromXsjlid);
            history.setFromXsjlname(fromXsjlname);
            history.setTransType(transType);
            customerOperateHistoryMapper.insert(history);

            //如果 原销售离职，同步订单时，修改客户，同时将所有未完成登记的体检者开单医师修改为新销售
            //如果 未离职，原来导入的人员仍是原开单医师，后来的订单属于新销售经理（不需要处理）
            if ("1".equals(param.getKey())) {
                for (Branch branch : branches) {
                    //客户转移表
                    CustomerTransfer customerTransfer = new CustomerTransfer(fromXsjlid
                            , param.getId()
                            , toXsjlname
                            , branch.getId().toString()
                            , 0
                            , null
                            , khid
                    );
                    customerTransferMapper.insert(customerTransfer);
                }
            }
        }
        return Boolean.TRUE;
    }
}

