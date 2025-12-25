package com.center.medical.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.dto.PriceAndFactPriceDto;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.bean.param.ExportItemsParam;
import com.center.medical.bean.vo.*;
import com.center.medical.common.config.LisConfig;
import com.center.medical.common.config.LoadProperties;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.dao.PeispatientfeeitemMapper;
import com.center.medical.service.PeisStateService;
import com.center.medical.service.PeispatientfeeitemService;
import com.center.medical.system.dao.SysDeptMapper;
import com.center.medical.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 体检者收费项目表(Peispatientfeeitem)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-18 19:40:46
 */
@Slf4j
@Service("peispatientfeeitemService")
@RequiredArgsConstructor
public class PeispatientfeeitemServiceImpl extends ServiceImpl<PeispatientfeeitemMapper, Peispatientfeeitem> implements PeispatientfeeitemService {

    private final PeispatientfeeitemMapper peispatientfeeitemMapper;
    private final SysDeptMapper sysDeptMapper;
    private final PeisStateService peisStateService;
    private final PeispatientMapper peispatientMapper;
    private final ISysConfigService iSysConfigService;

    @Autowired
    private LoadProperties loadProperties;

    /**
     * 分页查询[体检者收费项目表]列表
     *
     * @param page  分页参数
     * @param param Peispatientfeeitem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Peispatientfeeitem> getPage(PageParam<Peispatientfeeitem> page, Peispatientfeeitem param) {
        return peispatientfeeitemMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peispatientfeeitem getInfoById(String id) {
        return peispatientfeeitemMapper.getInfoById(id);
    }

    /**
     * 查询未缴费项目
     *
     * @param userNo
     * @param patientCode
     * @return
     */
    @Override
    public List<Peispatientfeeitem> selectUnfees(String userNo, String patientCode) {
        return peispatientfeeitemMapper.selectUnfees(userNo, patientCode);
    }

    /**
     * 项目列表
     *
     * @param page
     * @param patientcode
     * @return
     */
    @Override
    public IPage<GetItemDataVo> getItemData(PageParam<GetItemDataVo> page, String patientcode) {
        patientcode = patientcode.trim().toUpperCase();
        return peispatientfeeitemMapper.getItemData(page, patientcode);
    }

    /**
     * 外送项目
     *
     * @param page
     * @param key
     * @param patientcode
     * @return
     */
    @Override
    public IPage<WsxmDataVo> getWsxmData(PageParam<WsxmDataVo> page, String key, String patientcode) {
        //去空格大写
        if (ObjectUtils.isNotEmpty(key)) {
            key = key.trim().toUpperCase();
        }
        return peispatientfeeitemMapper.getWsxmData(page, key, patientcode);
    }


    /**
     * 科室加项右侧数据
     *
     * @param patientcode
     * @return
     */
    @Override
    public List<AddListDataVo> getAddListData(String patientcode) {
        return peispatientfeeitemMapper.getAddListData(patientcode);
    }

    @Override
    public String saOrUp(List<Peispatientfeeitem> itemList) {
        return null;
    }


    @Override
    public Object[] getFactPrice(HashMap<String, String> map, int size, int index, Boolean isMakeGb) {
        Object[] result = {null, false};
        if ("1".equals(map.get("isbx"))) {
            result[0] = map.get("zhjg");
        } else {
            boolean isFunction = false;
            String idKs = map.get("idKs");
            if (StringUtils.isNotEmpty(idKs)) {
                SysDept dept = sysDeptMapper.getByDeptNo(idKs);
                if (dept != null && dept.getIsFunction() != null && "1".equals(dept.getIsFunction())) {
                    isFunction = true;
                }
            }

            // 【个检报告工本费】
//             if (map.get("examfeeitemName").equals("个检报告工本费")) {
            if (!isFunction && !isMakeGb) {//不是功能科室
                result[0] = map.get("zhjg");
                result[1] = true;
            } else {
                // 套餐中不存在【个检报告工本费】就把折扣价放在最后一个收费项目
                if ((index + 1) == size && !isMakeGb) {
                    result[0] = map.get("zhjg");
                } else {
                    result[0] = "0";
                }
            }
        }
        return result;
    }

    /**
     * 无关联科室已检
     *
     * @param patientcode
     * @return
     */
    @Override
    public List<Peispatientfeeitem> irrelevantInspect(String patientcode) {
        return peispatientfeeitemMapper.irrelevantInspect(patientcode);
    }

    /**
     * 查找剩余的未检收费项目是否是弃检、迟检、退项状态判断是否需要分检完成，无科室的检查完
     *
     * @param pei
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkFj(Peispatient pei) {
        // 体检者不存在
        if (null == pei) {
            return;
        }
        // 功能科室
        List<String> depIds = sysDeptMapper.getFunctionKsIds(1);
        // 查找未检、未弃检、未退项、存在科室的收费项目数量
        List<Peispatientfeeitem> peispatientfeeitems = depIds.size() == 0 ? new ArrayList<Peispatientfeeitem>()
                : peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>()
                .in("id_ks", depIds)
                .eq("id_patient", pei.getPatientcode())
                .eq("change_item", 0)
                .eq("f_giveup", 0)
                .eq("sfjj", 0)
                .isNotNull("id_ks")
                .isNull("f_transferedhl7")
                .eq("f_examinated", 0));
        if (peispatientfeeitems.size() == 0) {
            pei.setFReadytofinal(1);
            peisStateService.setScbs(pei.getPatientcode(), 0);
            pei.setReadytofinalDate(new Date());
            // 无关联科室已检
            List<Peispatientfeeitem> others = irrelevantInspect(pei.getPatientcode());

            for (Peispatientfeeitem peispatientfeeitem : others) {
                peispatientfeeitem.setFExaminated(1);//设置未关联科室项目为已检,反审核时不改回去
                // 更新收费实体类
                peispatientfeeitemMapper.updateById(peispatientfeeitem);
            }
        } else {
            pei.setFReadytofinal(0);
            peisStateService.setScbs(pei.getPatientcode(), 0);
        }
        peispatientMapper.updateById(pei);
    }


    /**
     * 外送登记项目列表
     *
     * @param page
     * @param patientcode
     * @return
     */
    @Override
    public IPage<OSItemDataVo> getOSItemData(PageParam<OSItemDataVo> page, String patientcode) {
        if (StringUtils.equals(loadProperties.name, "huaou")
                || StringUtils.equals(loadProperties.name, "pingdu")
                || StringUtils.equals(loadProperties.name, "changsha")
        ){
            //因为华欧医院pacs系统的问题，这里需要看全部检验科的数据
            IPage<OSItemDataVo> iPage = peispatientfeeitemMapper.getHuaOuItemData(page,patientcode);
            return iPage;
        }else {
            //其他区只看外送的
            return peispatientfeeitemMapper.getOSItemData(page, patientcode);
        }
    }


    /**
     * 导出体检者收费项目列表
     *
     * @param param
     * @return
     */
    @Override
    public List<ExportItemsVo> exportItems(ExportItemsParam param) {
        return peispatientfeeitemMapper.exportItems(param);
    }

    /**
     * 获取所有尚未获取Lis数据的体检号
     *
     * @return
     */
    @Override
    public List<String> receiveLisDataUser() {
        LisConfig lisConfig=iSysConfigService.getSysConfigObject(Constants.LIS_CONFIG, LisConfig.class);
        return peispatientfeeitemMapper.receiveLisDataUser(lisConfig.getDaysAgo());
    }

    /**
     * 一键体检者收费项目去重
     *
     * @param patientcode 体检号
     * @return
     */
    @Override
    public Boolean deduplication(String patientcode) {
        return peispatientfeeitemMapper.deduplication(patientcode);
    }

    /**
     * 查询该科室未出结果的项目
     * @param patientcode
     * @return
     */
    @Override
    public int getDepartmentResults(String patientcode) {
        return peispatientfeeitemMapper.getDepartmentResults(patientcode);
    }


    /**
     * 获取原价和优惠价
     * @param patientCode
     * @return
     */
    @Override
    public PriceAndFactPriceDto getPriceAndFactprice(String patientCode) {
        return peispatientfeeitemMapper.getPriceAndFactprice(patientCode);
    }


    /**
     * 查询未收费项目
     * @param patientcode
     * @return
     */
    @Override
    public Integer unpaidItems(String patientcode) {
        return peispatientfeeitemMapper.unpaidItems(patientcode);
    }

    /**
     * 判断体检者收费项目是否重复
     * @param patientcode
     * @return
     */
    @Override
    public boolean isRepeat(String patientcode) {
        List<Peispatientfeeitem> repeat = peispatientfeeitemMapper.isRepeat(patientcode);
        log.info("体检者收费项目重复：{}、{}", patientcode, repeat);
        if (CollectionUtil.isNotEmpty(repeat)){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 获取未退费的项目数量
     * @param patientcode
     * @return
     */
    @Override
    public int getUnreimbursedProjects(String patientcode) {
        return peispatientfeeitemMapper.getUnreimbursedProjects(patientcode);
    }


    /**
     * 判断是否包含早餐
     * @param patientcode
     * @return
     */
    @Override
    public Integer includesBreakfast(String patientcode) {
        return peispatientfeeitemMapper.includesBreakfast(patientcode);
    }
}

