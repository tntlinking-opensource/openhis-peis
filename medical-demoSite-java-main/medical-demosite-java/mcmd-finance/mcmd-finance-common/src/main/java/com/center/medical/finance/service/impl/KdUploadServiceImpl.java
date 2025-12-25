package com.center.medical.finance.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.finance.bean.dto.CollectiveCollectionDto;
import com.center.medical.finance.bean.dto.EpayWaysDto;
import com.center.medical.finance.bean.dto.KingdeeUploadDataDto;
import com.center.medical.finance.bean.dto.ReceivePaymentDto;
import com.center.medical.finance.bean.dto.*;
import com.center.medical.finance.bean.model.KdCustomer;
import com.center.medical.finance.bean.param.UploadPeiDataParam;
import com.center.medical.finance.constant.KingdeeConstants;
import com.center.medical.finance.dao.KdCustomerMapper;
import com.center.medical.finance.dao.KdUploadMapper;
import com.center.medical.finance.service.KdUploadService;
import com.center.medical.sellcrm.bean.model.Sellcustomer;
import com.center.medical.sellcrm.dao.SellcustomerMapper;
import com.center.medical.system.dao.SysBranchMapper;
import com.center.medical.system.utils.KingdeeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xhp
 * @since 2023-05-18 9:25
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class KdUploadServiceImpl extends ServiceImpl<KdUploadMapper, Sellcustomer> implements KdUploadService {
    private final SysBranchMapper sysBranchMapper;
    private final KingdeeUtil kingdeeUtil;
    private final KdCustomerMapper kdCustomerMapper;
    private final SellcustomerMapper sellcustomerMapper;

    /**
     * 上传个检
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public String taskAboutUpdateT(String startTime, String endTime) {
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

        List<EpayWaysDto> epayWaysList = baseMapper.statisticsOfAllInspectionPaidInExpensesOfTheDay(startTime, endTime);

        JSONObject config = kingdeeUtil.getConfig();
        String FCURRENCY = config.getStr("currency");
        String FEmpinfo = config.getStr("saleer");
        String FPriceQty = config.getStr("pricingQuantity");
        String creator = SecurityUtils.getUsername();
        String cashPerson = config.getStr("cashPerson");
        List<HashMap<String, Object>> packageList = new ArrayList<>();
        for (int i = 0, l = epayWaysList.size(); i < l; i++) {
            String thingKingdeeNumber = epayWaysList.get(i).getThingKingdeeNumber();
            //如果没有维护个检金蝶ID pass
            if (thingKingdeeNumber == null || thingKingdeeNumber.equals("")) {
                continue;
            }
            //数字是0的不需要上传
            BigDecimal geren = epayWaysList.get(i).getGeren();
            double personal = Double.parseDouble(geren.toString());
            if (personal == 0) {
                continue;
            }
            //没维护公司的也不上传
            String customer = epayWaysList.get(i).getPaywayCompany();
            if (customer == null || customer.equals("")) {
                continue;
            } else if (customer.contains("现金")) {
                customer = cashPerson;
            }
            //销售员名字相关，没有名字或者，名字不匹配就不可以上传
            String kingdeeSaleer = epayWaysList.get(i).getKingdeeSaleer();
            if (kingdeeSaleer == null || kingdeeSaleer.equals("")) {
                continue;
            }
            if (!kingdeeSaleer.equals(creator)) {
                continue;
            }
            //FEntity为分录内容
            HashMap<String, String> FEntity = new HashMap<String, String>();
            FEntity.put("FCost_Number", thingKingdeeNumber);
            FEntity.put("FPriceQty", FPriceQty);
            FEntity.put("FTaxPrice", String.valueOf(personal));
            List<HashMap<String, String>> entityList = new ArrayList<>();
            entityList.add(FEntity);
            HashMap<String, Object> packageData = new HashMap<>();
            packageData.put("FDate", endTime);
            packageData.put("FOrg", centerOrgName);
            packageData.put("FCURRENCY", FCURRENCY);
            packageData.put("FEndDate", endTime);
            packageData.put("Customer", customer);
            packageData.put("FEmpinfo", FEmpinfo);
            packageData.put("Creator", creator);
            packageData.put("FEntity", entityList);
            packageList.add(packageData);
        }
        if (packageList.size() == 0) throw new ServiceException("暂无数据需要上传");
        HashMap<String, Object> map = new HashMap<>();
        map.put("Receive", packageList);

        String updateJson = JSONUtil.toJsonStr(map);
        log.info("上传个检数据：" + updateJson);

        String json = kingdeeUtil.send(KingdeeConstants.METHOD_RECEIVE, "json", updateJson);

        return json;
    }

    /**
     * 上传团检
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public String taskAboutUpdateG(String startTime, String endTime) {
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

        List<EpayWaysDto> epayWaysList = baseMapper.statisticsOfAllInspectionPaidInExpensesOfTheDay(startTime, endTime);
        JSONObject config = kingdeeUtil.getConfig();
        String FCURRENCY = config.getStr("currency");
        String FEmpinfo = config.getStr("saleer");
        String FPriceQty = config.getStr("pricingQuantity");
        String creator = SecurityUtils.getUsername();
        String cashTeam = config.getStr("cashTeam");
        List<HashMap<String, Object>> packageList = new ArrayList<>();
        for (int i = 0, l = epayWaysList.size(); i < l; i++) {
            String groupKingdeeNumber = epayWaysList.get(i).getGroupKingdeeNumber();
            String thingKingdeeNumber = epayWaysList.get(i).getThingKingdeeNumber();

            if (groupKingdeeNumber == null || groupKingdeeNumber.equals("")) {
                if (thingKingdeeNumber == null || thingKingdeeNumber.equals("")) {
                    continue;
                } else {
                    groupKingdeeNumber = thingKingdeeNumber;
                }
            }
            //数字是0的不需要上传
            BigDecimal tuan = epayWaysList.get(i).getTuan();
            double group = Double.parseDouble(tuan.toString());
            if (group == 0) {
                continue;
            }
            //没维护公司的也不上传
            String customer = epayWaysList.get(i).getPaywayCompany();
            if (customer == null || customer.equals("")) {
                continue;
            } else if (customer.contains("现金")) {
                customer = cashTeam;
            }
            //销售员名字相关，没有名字或者，名字不匹配就不可以上传
            String kingdeeSaleer = epayWaysList.get(i).getKingdeeSaleer();
            if (kingdeeSaleer == null || kingdeeSaleer.equals("")) {
                continue;
            }
            if (!kingdeeSaleer.equals(creator)) {
                continue;
            }
            //FEntity为分录内容
            HashMap<String, String> FEntity = new HashMap<>();
            FEntity.put("FCost_Number", groupKingdeeNumber);
            FEntity.put("FPriceQty", FPriceQty);
            FEntity.put("FTaxPrice", String.valueOf(group));
            List<HashMap<String, String>> entityList = new ArrayList<>();
            entityList.add(FEntity);
            HashMap<String, Object> packageData = new HashMap<>();
            packageData.put("FDate", endTime);
            packageData.put("FOrg", centerOrgName);
            packageData.put("FCURRENCY", FCURRENCY);
            packageData.put("FEndDate", endTime);
            packageData.put("Customer", customer);
            packageData.put("FEmpinfo", FEmpinfo);
            packageData.put("Creator", creator);
            packageData.put("FEntity", entityList);
            packageList.add(packageData);
        }
        if (ObjectUtils.isEmpty(packageList)) {
            throw new ServiceException("没有数据需要上传！");
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("Receive", packageList);

        String updateJson = JSONUtil.toJsonStr(map);
        log.info("上传团检数据：" + updateJson);

        String json = kingdeeUtil.send(KingdeeConstants.METHOD_RECEIVE, "json", updateJson);

        return json;
    }

    /**
     * 上传个检结算
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public String updateSettlement(String startTime, String endTime) {
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

        List<EpayWaysDto> epayWaysList = baseMapper.statisticsOfAllInspectionPaidInExpensesOfTheDay(startTime, endTime);

        JSONObject config = kingdeeUtil.getConfig();
        String FCURRENCY = config.getStr("currency");
        String FEmpinfo = config.getStr("saleer");
        String FPriceQty = config.getStr("pricingQuantity");
        String creator = SecurityUtils.getUsername();
        List<HashMap<String, Object>> packageList = new ArrayList<>();
        for (int i = 0, l = epayWaysList.size(); i < l; i++) {
            String posKingdeeNumber = epayWaysList.get(i).getPosKingdeeNumber();
            //结算ID没有维护的不上传
            if (posKingdeeNumber == null || posKingdeeNumber.equals("")) {
                continue;
            }
            //数字是0的不需要上传
            BigDecimal gerenTj = epayWaysList.get(i).getGerenTj();
            double perjs = Double.parseDouble(gerenTj.toString());
            if (perjs == 0) {
                continue;
            }
            //没维护公司的也不上传
            String customer = epayWaysList.get(i).getPaywayCompany();
            if (customer == null || customer.equals("")) {
                continue;
            }
            //销售员名字相关，没有名字或者，名字不匹配就不可以上传
            String kingdeeSaleer = epayWaysList.get(i).getKingdeeSaleer();
            if (kingdeeSaleer == null || kingdeeSaleer.equals("")) {
                continue;
            }
            if (!kingdeeSaleer.equals(creator)) {
                continue;
            }
            //FEntity为分录内容
            HashMap<String, String> FEntity = new HashMap<>();
            FEntity.put("FCost_Number", posKingdeeNumber);
            FEntity.put("FPriceQty", FPriceQty);
            FEntity.put("FTaxPrice", String.valueOf(perjs));
            List<HashMap<String, String>> entityList = new ArrayList<>();
            entityList.add(FEntity);
            HashMap<String, Object> packageData = new HashMap<>();
            packageData.put("FDate", endTime);
            packageData.put("FOrg", centerOrgName);
            packageData.put("FCURRENCY", FCURRENCY);
            packageData.put("FEndDate", endTime);
            packageData.put("Customer", customer);
            packageData.put("FEmpinfo", FEmpinfo);
            packageData.put("Creator", creator);
            packageData.put("FEntity", entityList);
            packageList.add(packageData);
        }
        HashMap<String, Object> map = new HashMap<>();
        if (ObjectUtils.isEmpty(packageList)) {
            throw new ServiceException("没有数据需要上传！");
        }

        map.put("Receive", packageList);

        String updateJson = JSONUtil.toJsonStr(map);
        log.info("上传个检结算数据：" + updateJson);

        String json = kingdeeUtil.send(KingdeeConstants.METHOD_RECEIVE, "json", updateJson);

        return json;
    }

    /**
     * 上传团体结算
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public String updateGroupSettlement(String startTime, String endTime) {
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

        List<EpayWaysDto> epayWaysList = baseMapper.statisticsOfAllInspectionPaidInExpensesOfTheDay(startTime, endTime);

        JSONObject config = kingdeeUtil.getConfig();
        String FCURRENCY = config.getStr("currency");
        String FEmpinfo = config.getStr("saleer");
        String FPriceQty = config.getStr("pricingQuantity");
        String creator = SecurityUtils.getUsername();
        List<HashMap<String, Object>> packageList = new ArrayList<>();
        for (int i = 0, l = epayWaysList.size(); i < l; i++) {
            //团体和个检的记账结算对应金蝶都是个检记账-结算
            String posKingdeeNumber = epayWaysList.get(i).getPosKingdeeNumber();
            //结算ID没有维护的不上传
            if (posKingdeeNumber == null || posKingdeeNumber.equals("")) {
                continue;
            }
            //数字是0的不需要上传
            BigDecimal tuanTj = epayWaysList.get(i).getTuanTj();
            double perjs = Double.parseDouble(tuanTj.toString());
            if (perjs == 0) {
                continue;
            }
            //没维护公司的也不上传
            String customer = epayWaysList.get(i).getPaywayCompany();
            if (customer == null || customer.equals("")) {
                continue;
            }
            //销售员名字相关，没有名字或者，名字不匹配就不可以上传
            String kingdeeSaleer = epayWaysList.get(i).getKingdeeSaleer();
            if (kingdeeSaleer == null || kingdeeSaleer.equals("")) {
                continue;
            }
            if (!kingdeeSaleer.equals(creator)) {
                continue;
            }
            //FEntity为分录内容
            HashMap<String, String> FEntity = new HashMap<>();
            FEntity.put("FCost_Number", posKingdeeNumber);
            FEntity.put("FPriceQty", FPriceQty);
            FEntity.put("FTaxPrice", String.valueOf(perjs));
            List<HashMap<String, String>> entityList = new ArrayList<>();
            entityList.add(FEntity);
            HashMap<String, Object> packageData = new HashMap<>();
            packageData.put("FDate", endTime);
            packageData.put("FOrg", centerOrgName);
            packageData.put("FCURRENCY", FCURRENCY);
            packageData.put("FEndDate", endTime);
            packageData.put("Customer", customer);
            packageData.put("FEmpinfo", FEmpinfo);
            packageData.put("Creator", creator);
            packageData.put("FEntity", entityList);
            packageList.add(packageData);
        }
        if (ObjectUtils.isEmpty(packageList)) {
            throw new ServiceException("没有数据需要上传！");
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("Receive", packageList);

        String updateJson = JSONUtil.toJsonStr(map);
        log.info("上传团体结算：" + updateJson);

        String json = kingdeeUtil.send(KingdeeConstants.METHOD_RECEIVE, "json", updateJson);

        return json;
    }

    /**
     * 积分和体检卡月度个检结算
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public String updateMonth(String startTime, String endTime) {
        //获取开始时间的月初和结束时间的月末
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = simpleDateFormat.parse(startTime);
            endDate = simpleDateFormat.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        startCal.setTime(startDate);
        endCal.setTime(endDate);
        int start = startCal.getActualMinimum(Calendar.DAY_OF_MONTH);
        int last = endCal.getActualMaximum(Calendar.DAY_OF_MONTH);
        startCal.set(Calendar.DAY_OF_MONTH, start);
        endCal.set(Calendar.DAY_OF_MONTH, last);
        startTime = simpleDateFormat.format(startCal.getTime());
        endTime = simpleDateFormat.format(endCal.getTime());

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
        JSONObject config = kingdeeUtil.getConfig();
        String FCURRENCY = config.getStr("currency");
        String FEmpinfo = config.getStr("saleer");
        String FPriceQty = config.getStr("pricingQuantity");
        String creator = SecurityUtils.getUsername();

        List<EpayWaysDto> epayWaysList = baseMapper.statisticsOfALLInspectionPaidInExpensesOfTheMonth(startTime, endTime);

        List<HashMap<String, Object>> packageList = new ArrayList<>();
        for (int i = 0, l = epayWaysList.size(); i < l; i++) {
            String posKingdeeNumber = epayWaysList.get(i).getPosKingdeeNumber();
            //结算ID没有维护的不上传
            if (posKingdeeNumber == null || posKingdeeNumber.equals("")) {
                continue;
            }
            //数字是0的不需要上传
            BigDecimal gerenTj = epayWaysList.get(i).getGerenTj();
            double perjs = Double.parseDouble(gerenTj.toString());
            if (perjs == 0) {
                continue;
            }
            //没维护公司的也不上传
            String customer = epayWaysList.get(i).getPaywayCompany();
            if (customer == null || customer.equals("")) {
                continue;
            }
            //销售员名字相关，没有名字或者，名字不匹配就不可以上传
            String kingdeeSaleer = epayWaysList.get(i).getKingdeeSaleer();
            if (kingdeeSaleer == null || kingdeeSaleer.equals("")) {
                continue;
            }
            if (!kingdeeSaleer.equals(creator)) {
                continue;
            }
            //FEntity为分录内容
            HashMap<String, String> FEntity = new HashMap<>();
            FEntity.put("FCost_Number", posKingdeeNumber);
            FEntity.put("FPriceQty", FPriceQty);
            FEntity.put("FTaxPrice", String.valueOf(perjs));
            List<HashMap<String, String>> entityList = new ArrayList<>();
            entityList.add(FEntity);
            HashMap<String, Object> packageData = new HashMap<>();
            packageData.put("FDate", startTime);
            packageData.put("FOrg", centerOrgName);
            packageData.put("FCURRENCY", FCURRENCY);
            packageData.put("FEndDate", endTime);
            packageData.put("Customer", customer);
            packageData.put("FEmpinfo", FEmpinfo);
            packageData.put("Creator", creator);
            packageData.put("FEntity", entityList);
            packageList.add(packageData);
        }
        if (ObjectUtils.isEmpty(packageList)) {
            throw new ServiceException("没有数据需要上传！");
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("Receive", packageList);

        String updateJson = JSONUtil.toJsonStr(map);
        log.info("积分和体检卡月度个检结算数据：" + updateJson);

        String json = kingdeeUtil.send(KingdeeConstants.METHOD_RECEIVE, "json", updateJson);

        return json;
    }

    /**
     * 积分和体检卡月度团体结算
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public String updateMonthGroup(String startTime, String endTime) {
        //获取开始时间的月初和结束时间的月末
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = simpleDateFormat.parse(startTime);
            endDate = simpleDateFormat.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        startCal.setTime(startDate);
        endCal.setTime(endDate);
        int start = startCal.getActualMinimum(Calendar.DAY_OF_MONTH);
        int last = endCal.getActualMaximum(Calendar.DAY_OF_MONTH);
        startCal.set(Calendar.DAY_OF_MONTH, start);
        endCal.set(Calendar.DAY_OF_MONTH, last);
        startTime = simpleDateFormat.format(startCal.getTime());
        endTime = simpleDateFormat.format(endCal.getTime());

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
        JSONObject config = kingdeeUtil.getConfig();
        String FCURRENCY = config.getStr("currency");
        String FEmpinfo = config.getStr("saleer");
        String FPriceQty = config.getStr("pricingQuantity");
        String creator = SecurityUtils.getUsername();

        List<EpayWaysDto> epayWaysList = baseMapper.statisticsOfALLInspectionPaidInExpensesOfTheMonth(startTime, endTime);

        List<HashMap<String, Object>> packageList = new ArrayList<>();
        for (int i = 0, l = epayWaysList.size(); i < l; i++) {
            String posKingdeeNumber = epayWaysList.get(i).getPosKingdeeNumber();
            //结算ID没有维护的不上传
            if (posKingdeeNumber == null || posKingdeeNumber.equals("")) {
                continue;
            }
            //数字是0的不需要上传
            BigDecimal tuanTj = epayWaysList.get(i).getTuanTj();
            double perjs = Double.parseDouble(tuanTj.toString());
            if (perjs == 0) {
                continue;
            }
            //没维护公司的也不上传
            String customer = epayWaysList.get(i).getPaywayCompany();
            if (customer == null || customer.equals("")) {
                continue;
            }
            //销售员名字相关，没有名字或者，名字不匹配就不可以上传
            String kingdeeSaleer = epayWaysList.get(i).getKingdeeSaleer();
            if (kingdeeSaleer == null || kingdeeSaleer.equals("")) {
                continue;
            }
            if (!kingdeeSaleer.equals(creator)) {
                continue;
            }
            //FEntity为分录内容
            HashMap<String, String> FEntity = new HashMap<>();
            FEntity.put("FCost_Number", posKingdeeNumber);
            FEntity.put("FPriceQty", FPriceQty);
            FEntity.put("FTaxPrice", String.valueOf(perjs));
            List<HashMap<String, String>> entityList = new ArrayList<>();
            entityList.add(FEntity);
            HashMap<String, Object> packageData = new HashMap<>();
            packageData.put("FDate", startTime);
            packageData.put("FOrg", centerOrgName);
            packageData.put("FCURRENCY", FCURRENCY);
            packageData.put("FEndDate", endTime);
            packageData.put("Customer", customer);
            packageData.put("FEmpinfo", FEmpinfo);
            packageData.put("Creator", creator);
            packageData.put("FEntity", entityList);
            packageList.add(packageData);
        }
        if (ObjectUtils.isEmpty(packageList)) {
            throw new ServiceException("没有数据需要上传！");
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("Receive", packageList);

        String updateJson = JSONUtil.toJsonStr(map);
        log.info("积分和体检卡月度团体结算数据：" + updateJson);

        String json = kingdeeUtil.send(KingdeeConstants.METHOD_RECEIVE, "json", updateJson);

        return json;
    }


    /**
     * 上传统收
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public String updateSettlementOfOrg(String startTime, String endTime) {
        //获取开始时间的月初和结束时间的月末
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = simpleDateFormat.parse(startTime);
            endDate = simpleDateFormat.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        startCal.setTime(startDate);
        endCal.setTime(endDate);
        int start = startCal.getActualMinimum(Calendar.DAY_OF_MONTH);
        int last = endCal.getActualMaximum(Calendar.DAY_OF_MONTH);
        startCal.set(Calendar.DAY_OF_MONTH, start);
        endCal.set(Calendar.DAY_OF_MONTH, last);
        startTime = simpleDateFormat.format(startCal.getTime());
        endTime = simpleDateFormat.format(endCal.getTime());

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
        JSONObject config = kingdeeUtil.getConfig();
        String FCURRENCY = config.getStr("currency");
        String creator = SecurityUtils.getUsername();

        List<CollectiveCollectionDto> settlementOrgList = baseMapper.getCollectiveCollection(startTime, endTime);
        log.info("上传统收settlementOrgList.size:" + settlementOrgList.size());

        List<HashMap<String, Object>> packageList = new ArrayList<>();
        List<Integer> problemList = new ArrayList<>();
        for (int i = 0, l = settlementOrgList.size(); i < l; i++) {
            String posKingdeeNumber = settlementOrgList.get(i).getPosKingdeeNumber();
            //统收金蝶没有维护的不上传
            if (posKingdeeNumber == null || posKingdeeNumber.equals("")) {
                continue;
            }
            //数字是0的不需要上传
            BigDecimal orgPaid = settlementOrgList.get(i).getPaid();
            double paid = Double.parseDouble(orgPaid.toString());
            if (paid == 0) {
                continue;
            }
            //销售员金蝶不维护的要报错
            String kingdeeSaleer = settlementOrgList.get(i).getKingdeeSaleer();
            if (kingdeeSaleer == null || kingdeeSaleer.equals("")) {
                throw new ServiceException("客户单位【"+settlementOrgList.get(i).getNoteName()+"】对应的销售的中心：" + settlementOrgList.get(i).getFzx() + "，账号:" + settlementOrgList.get(i).getUserName() +
                        "未关联金蝶账号"
                );
            }
            String customerName = settlementOrgList.get(i).getOrgName();
            if (customerName == null || customerName.equals("")) {
                problemList.add(settlementOrgList.get(i).getIntId().intValue());
                continue;
            }
            //FEntity为分录内容
            HashMap<String, String> FEntity = new HashMap<>();
            FEntity.put("FCost_Number", posKingdeeNumber);
            String FPriceQty;
            if (paid < 0) {
                FPriceQty = "-1";
                paid = Math.abs(paid);
            } else {
                FPriceQty = "1";
            }
            FEntity.put("FPriceQty", FPriceQty);
            FEntity.put("FTaxPrice", String.valueOf(paid));
            List<HashMap<String, String>> entityList = new ArrayList<>();
            entityList.add(FEntity);
            HashMap<String, Object> packageData = new HashMap<>();
            packageData.put("FDate", endTime);
            packageData.put("FOrg", centerOrgName);
            packageData.put("FCURRENCY", FCURRENCY);
            packageData.put("FEndDate", endTime);
            packageData.put("Customer", customerName);
            packageData.put("FEmpinfo", kingdeeSaleer);
            packageData.put("Creator", creator);
            packageData.put("FEntity", entityList);
            packageList.add(packageData);
        }
        if (packageList.size() == 0) {
            throw new ServiceException("暂无数据需要上传");
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("Receive", packageList);

        String updateJson = JSONUtil.toJsonStr(map);
        log.info("上传统收数据：" + updateJson);

        String json = kingdeeUtil.send(KingdeeConstants.METHOD_RECEIVE, "json", updateJson);

        if (problemList.size() != 0) {
            String result = problemList.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
            throw new ServiceException("这些团体ID:" + result + "没有上传,请先关联金蝶数据!");
        }

        return json;
    }

    /**
     * 检验统收团体金蝶名
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    @Transactional
    public String checkOrgKingdeeName(String startTime, String endTime) {
        //意义不明的代码
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = simpleDateFormat.parse(startTime);
            endDate = simpleDateFormat.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        startCal.setTime(startDate);
        endCal.setTime(endDate);
        int start = startCal.getActualMinimum(Calendar.DAY_OF_MONTH);
        int last = endCal.getActualMaximum(Calendar.DAY_OF_MONTH);
        startCal.set(Calendar.DAY_OF_MONTH, start);
        endCal.set(Calendar.DAY_OF_MONTH, last);
        startTime = simpleDateFormat.format(startCal.getTime());
        endTime = simpleDateFormat.format(endCal.getTime());

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

        List<CollectiveCollectionDto> settlementOrgList = baseMapper.getCollectiveCollection(startTime, endTime);
        log.info("检验统收团体金蝶名settlementOrgList.size:" + settlementOrgList.size());

        List<Integer> problemList = new ArrayList<>();
        for (int i = 0, l = settlementOrgList.size(); i < l; i++) {
            //jindie_id
            String customerName = settlementOrgList.get(i).getOrgName();
            KdCustomer customer = kdCustomerMapper.selectOne(
                    new QueryWrapper<KdCustomer>()
                            .eq("centerorgname", centerOrgName)
                            .eq("account_name", customerName)
                            .eq("use_status_id", "1")
            );
            //客户单位名称
            String noteName = settlementOrgList.get(i).getNoteName();
            if (customer == null) {
                customer = kdCustomerMapper.selectOne(
                        new QueryWrapper<KdCustomer>()
                                .eq("centerorgname", centerOrgName)
                                .eq("account_name", noteName)
                                .eq("use_status_id", "1")
                );
                int problemId = settlementOrgList.get(i).getIntId().intValue();
                if (customer == null) {
                    problemList.add(problemId);
                } else {
                    Sellcustomer sellcustomer = sellcustomerMapper.selectOne(
                            new QueryWrapper<Sellcustomer>()
                                    .eq("khdwmc", noteName)
                                    .eq("int_id", problemId)
                    );
                    if (sellcustomer != null) {
                        sellcustomer.setJindieId(customer.getAccountName());
                    }
                    sellcustomerMapper.updateById(sellcustomer);
                }
            }
        }

        String problem;
        if (problemList.size() == 0) {
            problem = startTime + "到" + endTime + "期间" + settlementOrgList.size() + "条数据无异常，可以上传";
        } else {
            problem = startTime + "到" + endTime + "期间" + settlementOrgList.size() + "条数据中，共有" + problemList.size() + "条数据有问题，其团体号为" + problemList.toString();
        }

        return problem;
    }

    /**
     * 检验匹配团体金蝶名
     *
     * @return
     */
    public String checkOrgName() {
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
        boolean isGreatLeader = SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE);
        boolean isLeader = SecurityUtils.isLeader();
        String userNo = SecurityUtils.getUserNo();
        List<Sellcustomer> sellcustomerList = new ArrayList<>();
        if (isGreatLeader) {
            sellcustomerList = sellcustomerMapper.selectList(
                    new QueryWrapper<Sellcustomer>()
                            .ne("khzt", 2)
                            .eq("is_delete", 0)
                            .isNotNull("int_id")
            );
        } else if (!isLeader) {
            sellcustomerList = sellcustomerMapper.selectList(
                    new QueryWrapper<Sellcustomer>()
                            .ne("khzt", 2)
                            .eq("is_delete", 0)
                            .isNotNull("int_id")
                            .eq("xsjlid", userNo)
            );
        } else {
            sellcustomerList = sellcustomerMapper.selectList(
                    new QueryWrapper<Sellcustomer>()
                            .ne("khzt", 2)
                            .eq("is_delete", 0)
                            .isNotNull("int_id")
                            .eq("fzxid", branchId)
            );
        }

        List<Integer> problemList = new ArrayList<>();
        for (int i = 0, l = sellcustomerList.size(); i < l; i++) {
            String customerName = sellcustomerList.get(i).getJindieId();
            KdCustomer customer = kdCustomerMapper.selectOne(
                    new QueryWrapper<KdCustomer>()
                            .eq("centerorgname", centerOrgName)
                            .eq("account_name", customerName)
                            .eq("use_status_id", "1")
            );
            String noteName = sellcustomerList.get(i).getLicenseName();
            if (customer == null) {
                customer = kdCustomerMapper.selectOne(
                        new QueryWrapper<KdCustomer>()
                                .eq("centerorgname", centerOrgName)
                                .eq("account_name", noteName)
                                .eq("use_status_id", "1")
                );
                int problemId = sellcustomerList.get(i).getIntId();
                if (customer == null) {
                    problemList.add(problemId);
                } else {
                    Sellcustomer sellcustomer = sellcustomerMapper.selectOne(
                            new QueryWrapper<Sellcustomer>()
                                    .eq("khdwmc", sellcustomerList.get(i).getKhdwmc())
                                    .eq("int_id", problemId)
                    );
                    if (sellcustomer != null) {
                        sellcustomer.setJindieId(customer.getAccountName());
                    }
                    sellcustomerMapper.updateById(sellcustomer);
                }
            }
        }

        String problem;
        if (problemList.size() == 0) {
            problem = "您的客户数据均已匹配，无需调整";
        } else {
            problem = sellcustomerList.size() + "条数据中，共有" + problemList.size() + "条数据有问题，其团体号为" + problemList.toString();
        }
        return problem;
    }

    /**
     * 获取上传金蝶星空云数据
     * @return
     */
    @Override
    public List<KingdeeUploadDataDto> getKingdeeUploadData(UploadPeiDataParam param) {
        return kdCustomerMapper.getKingdeeUploadData(param);
    }


    /**
     * 获取收款数据
     * @param baseParam
     * @return
     */
    @Override
    public List<ReceivePaymentDto> getReceivePaymentData(UploadPeiDataParam baseParam) {
        return kdCustomerMapper.getReceivePaymentData(baseParam);
    }
}
