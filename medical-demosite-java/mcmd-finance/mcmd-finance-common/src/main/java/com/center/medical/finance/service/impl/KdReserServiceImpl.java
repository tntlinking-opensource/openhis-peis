package com.center.medical.finance.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.common.constant.KingdeeConstants;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.dto.SelectOtherPayableDto;
import com.center.medical.finance.bean.model.KdCustomer;
import com.center.medical.finance.bean.model.KdRemittance;
import com.center.medical.finance.bean.model.KdReser;
import com.center.medical.finance.dao.KdCustomerMapper;
import com.center.medical.finance.dao.KdRemittanceMapper;
import com.center.medical.finance.dao.KdReserMapper;
import com.center.medical.finance.service.KdReserService;
import com.center.medical.sellcrm.bean.model.Sellcustomer;
import com.center.medical.sellcrm.dao.SellcustomerMapper;
import com.center.medical.system.dao.SysBranchMapper;
import com.center.medical.system.dao.SysDeptMapper;
import com.center.medical.system.utils.KingdeeUtil;
import jodd.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 每笔银行汇款结算详情(KdReser)表服务实现类
 *
 * @author 路飞船长
 * @since 2023-02-17 09:56:44
 */
@Slf4j
@Service("kdReserService")
@RequiredArgsConstructor
public class KdReserServiceImpl extends ServiceImpl<KdReserMapper, KdReser> implements KdReserService {

    private final KdReserMapper kdReserMapper;
    private final SysBranchMapper sysBranchMapper;
    private final SysDeptMapper sysDeptMapper;
    private final KdRemittanceMapper kdRemittanceMapper;
    private final KingdeeUtil kingdeeUtil;
    private final SellcustomerMapper sellcustomerMapper;
    private final KdCustomerMapper kdCustomerMapper;

    /**
     * 分页查询[每笔银行汇款结算详情]列表
     *
     * @param page  分页参数
     * @param param KdReser查询参数
     * @return 分页数据
     */
    @Override
    public IPage<KdReser> getPage(PageParam<KdReser> page, KdReser param) {
        return kdReserMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public KdReser getInfoById(String id) {
        return kdReserMapper.getInfoById(id);
    }

    @Override
    @Transactional
    public void upgradeOtherPayable(String transactionNumber) {
        String branchId = SecurityUtils.getCId();
        if (StrUtil.isEmpty(branchId)) {
            throw new ServiceException("无法获取当前用户分中心id");
        }
        SysBranch sysBranch = sysBranchMapper.getByBranchId(branchId);
        if (sysBranch == null) {
            throw new ServiceException("当前用户所属分中心不存在，可能已被删除：" + branchId);
        }
        String centerOrgName = sysBranch.getCenterorgname();
        if (StrUtil.isEmpty(centerOrgName)) {
            throw new ServiceException("请联系管理员为分中心【" + sysBranch.getFzx() + "】维护当前中心组织名");
        }

        SysDept salesDept = sysDeptMapper.selectDeptByName(KingdeeConstants.SALES_DEPARTMENT_NAME);
        if (salesDept == null) {
            throw new ServiceException("名称为【" + KingdeeConstants.SALES_DEPARTMENT_NAME + "】的部门不存在，请联系管理员添加。");
        }
        String departMent = salesDept.getKingdeeAccountNo();
        if (StrUtil.isEmpty(departMent)) {
            throw new ServiceException("名称为【" + KingdeeConstants.SALES_DEPARTMENT_NAME + "】的部门没有维护金蝶编号，请联系管理员维护。");
        }

        KdRemittance kdRemittance = kdRemittanceMapper.selectOne(
                new QueryWrapper<KdRemittance>()
                        .eq("transactionnumber", transactionNumber)
        );
        if (kdRemittance == null) {
            throw new ServiceException("流水号【" + transactionNumber + "】不存在。");
        }

        List<SelectOtherPayableDto> kingdeereserList = kdReserMapper.selectOtherPayable(transactionNumber);

        Double remittancePay = Double.valueOf(kdRemittance.getIncome());
        Double reserPay = 0.00;
        if (kingdeereserList.size() > 0) {
            for (int i = 0; i < kingdeereserList.size(); i++) {
                reserPay = reserPay + kingdeereserList.get(i).getMoneyamountpaid().doubleValue();
                if (kingdeereserList.get(i).getIsUpdate().equals("1")) {
                    throw new ServiceException("检测到有已同步的数据，请联系财务");
                }
            }
        }
        if (!reserPay.toString().equals(remittancePay.toString())) {
            throw new ServiceException("已审核的流水详情总金额不等于银行交易流水金额，不能上传");
        }

        cn.hutool.json.JSONObject config = kingdeeUtil.getConfig();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String Date = simpleDateFormat.format(kdRemittance.getTransdate());
        String CreateOrg = centerOrgName;
        String ContactUnit = kdRemittance.getRemitter();
        String Sporadic = config.getStr("saleer");
        List<HashMap<String, String>> entityList = new ArrayList<>();
        HashMap<String, Object> packageData = new HashMap<>();
        for (int i = 0; i < kingdeereserList.size(); i++) {
            SelectOtherPayableDto selectOtherPayableDto = kingdeereserList.get(i);
            HashMap<String, String> FEntity = new HashMap<>();
            FEntity.put("Expense", selectOtherPayableDto.getIdRemittanceway());//费用项目编号
            FEntity.put("Department", departMent);//部门编号 默认销售部门
            FEntity.put("Amount", selectOtherPayableDto.getMoneyamountpaid().toString());
            String paywayName = kingdeereserList.get(i).getPaywayName();
            if (paywayName.equals("团检-统收")) {
                int intId = Integer.parseInt(selectOtherPayableDto.getIdCustomer());
                Sellcustomer sellcustomer = sellcustomerMapper.selectOne(
                        new QueryWrapper<Sellcustomer>()
                                .eq("int_id", intId)
                );
                List<KdCustomer> kdCustomers = kdCustomerMapper.selectList(
                        new QueryWrapper<KdCustomer>()
                                .eq("account_name", sellcustomer.getJindieId())
                                .eq("use_status_id", "1")
                                .eq("centerorgname", centerOrgName)
                );
                if (CollectionUtil.isEmpty(kdCustomers)){
                    throw new ServiceException("金碟账户实体类为空,请先执行金蝶客户数据更新后去维护客户金蝶!");
                }
                if (kdCustomers.size() > 1) {
                    Set<String> centerorgnames = new HashSet<>();
                    for (KdCustomer kdCustomer : kdCustomers) {
                        centerorgnames.add(kdCustomer.getCenterorgname());
                    }
                    throw new ServiceException("存在多条名称为【" + sellcustomer.getJindieId() + "】的金蝶客户，请找财务核实。这些客户的组织分别为："
                            + StringUtil.join(centerorgnames, "、"));
                }
                KdCustomer kdCustomer = kdCustomers.get(0);
                FEntity.put("Customer", kdCustomer.getAccountNo());
                FEntity.put("Organization", "");
                if (StrUtil.isEmpty(selectOtherPayableDto.getAccountNo())) {
                    if (StrUtil.isEmpty(selectOtherPayableDto.getIdFeecharger())) {
                        throw new ServiceException("请选择销售经理。");
                    } else {
                        throw new ServiceException("请联系管理员，为销售经理设置金蝶账号。");
                    }
                }
                FEntity.put("Empinfo", selectOtherPayableDto.getAccountNo());
            } else if (paywayName.equals("个检-记账") || paywayName.equals("充卡-银行存款")) {
                FEntity.put("Customer", "");
                FEntity.put("Organization", "");
                FEntity.put("Empinfo", Sporadic);
            } else {
                FEntity.put("Customer", "");
                if (StrUtil.isEmpty(selectOtherPayableDto.getAccountNo())) {
                    if (StrUtil.isEmpty(selectOtherPayableDto.getIdFeecharger())) {
                        throw new ServiceException("请选择销售经理。");
                    } else {
                        throw new ServiceException("请联系管理员，为销售经理设置金蝶账号。");
                    }
                }
                FEntity.put("Empinfo", selectOtherPayableDto.getAccountNo());
                FEntity.put("Organization", selectOtherPayableDto.getIdCustomer());
            }
            entityList.add(FEntity);
        }
        packageData.put("Date", Date);
        packageData.put("CreateOrg", CreateOrg);
        packageData.put("ContactUnit", ContactUnit);
        packageData.put("FEntity", entityList);
        List<HashMap<String, Object>> packageDataList = new ArrayList<>();
        packageDataList.add(packageData);
        HashMap<String, Object> map = new HashMap<>();
        map.put("OtherPayable", packageDataList);
        JSONObject jsonObj = new JSONObject(map);//转化为json格式
        String updateJson = jsonObj.toJSONString();
        log.info("上传银行流水：" + updateJson);

        kingdeeUtil.send(KingdeeConstants.METHOD_SAVE_OTHER_PAYABLE, "json", updateJson);

        List<KdReser> kdResers = kdReserMapper.selectList(
                new QueryWrapper<KdReser>()
                        .eq("is_audit", "1")
                        .eq("id_remitter", transactionNumber)
        );
        for (KdReser kdReser : kdResers) {
            kdReser.setIsUpdate("1");
        }
        updateBatchById(kdResers);
    }
}

