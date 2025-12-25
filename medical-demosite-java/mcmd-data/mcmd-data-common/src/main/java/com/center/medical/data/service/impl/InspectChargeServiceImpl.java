package com.center.medical.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.vo.AllOutDataVo;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.data.bean.model.Basexamltem;
import com.center.medical.data.bean.model.InspectCharge;
import com.center.medical.data.bean.model.Items;
import com.center.medical.data.bean.vo.ListDatasVo;
import com.center.medical.data.dao.BasexamltemMapper;
import com.center.medical.data.dao.InspectChargeMapper;
import com.center.medical.data.dao.ItemsMapper;
import com.center.medical.data.service.InspectChargeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JC检查项目收费项目关联表(InspectCharge)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:08
 */
@Slf4j
@Service("inspectChargeService")
@RequiredArgsConstructor
public class InspectChargeServiceImpl extends ServiceImpl<InspectChargeMapper, InspectCharge> implements InspectChargeService {

    private final InspectChargeMapper inspectChargeMapper;
    private final PeispatientMapper peispatientMapper;
    private final ItemsMapper itemsMapper;
    private final BasexamltemMapper basexamltemMapper;

    /**
     * 分页查询[JC检查项目收费项目关联表]列表
     *
     * @param page  分页参数
     * @param param InspectCharge查询参数
     * @return 分页数据
     */
    @Override
    public IPage<InspectCharge> getList(PageParam<InspectCharge> page, InspectCharge param) {
        return inspectChargeMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public InspectCharge getInfoById(String id) {
        return inspectChargeMapper.getInfoById(id);
    }

    /**
     * 项目列表-结果-手动输入结果模块项目展示
     *
     * @param patientcode
     * @param idChargeFee
     * @return
     */
    @Override
    public List<AllOutDataVo> getAllOutData(String patientcode, String idChargeFee) {
        Peispatient pei = peispatientMapper.selectOne(new QueryWrapper<Peispatient>().eq("patientcode", patientcode));
        if (ObjectUtils.isEmpty(pei)) {
            throw new ServiceException("体检号不存在，请检查输入的是否正确");
        }
        return inspectChargeMapper.getAllOutData(idChargeFee, pei.getIdSex());
    }


    /**
     * 获取grid收费项目列表中是否存在相同的检查项目
     *
     * @param itemId
     * @return
     */
    @Override
    public String compareItemsToExam(List<String> itemId) {
        if (itemId.size() == 0) return "";
        StringBuffer text = new StringBuffer();
        Map<String, String> map = new HashMap<String, String>();
        // 检查项目收费项目关联表
        List<InspectCharge> inspectCharges = inspectChargeMapper.selectList(new QueryWrapper<InspectCharge>()
                .in("charge_id", itemId).eq("is_delete", 0));
        for (InspectCharge inspectCharge : inspectCharges) {
            List<String> items = inspectChargeMapper.getRepeatItems(inspectCharge.getInspectId(), itemId);
            if (items.size() <= 1) {
                continue;
            }
            StringBuffer str2 = new StringBuffer();
            for (int i = 0; i < items.size(); i++) {
                Items items2 = itemsMapper.selectOne(new QueryWrapper<Items>()
                        .eq("id", StringUtils.isBlank(items.get(i).toString()) ? "" : items.get(i).toString()).eq("is_delete", 0));
                if (null != items2) {
                    str2.append(items2.getExamfeeitemName() + "、");
                }
            }
            String res = str2.toString().substring(0, str2.toString().length() - 1);
            // 检查项目表
            Basexamltem exanm = basexamltemMapper.selectOne(new QueryWrapper<Basexamltem>()
                    .eq("id", inspectCharge.getInspectId()).eq("is_delete", 0));
            String jcxmName = "";
            if (null != exanm) {
                jcxmName = exanm.getExamitemName();
            }
            if (!StringUtils.isBlank(map.get(res))) {
                jcxmName = map.get(res) + "、" + jcxmName;
            }
            map.put(res, jcxmName);
        }

        for (Map.Entry<String, String> entry : map.entrySet()) {
            text.append("★收费项目:" + entry.getKey() + "存在相同的检查项目: " + entry.getValue());
        }

        // 不存在重复项
        if (StringUtils.isBlank(text.toString())) {
            return "";
        } else {
            return text.toString();
        }
    }

    /**
     * 编辑收费项目-右下表格数据
     *
     * @param page
     * @param id
     * @param type
     * @return
     */
    @Override
    public IPage<ListDatasVo> getAllItemsData(PageParam<ListDatasVo> page, String id, String type) {
        IPage<ListDatasVo> iPage = inspectChargeMapper.getAllItemsData(page, id, type);
        List<ListDatasVo> list = iPage.getRecords();
        //设置体征上限及下限
        for (ListDatasVo vo : list) {
            Integer forMale = vo.getSex();
            if (ObjectUtils.isEmpty(forMale)){
                vo.setTzsx(vo.getValuefemalemax());
                vo.setTzxx(vo.getValuefemalemin());
            }else {
                if (forMale == 0) {
                    vo.setTzsx(vo.getValuemalemax());
                    vo.setTzxx(vo.getValuemalemin());
                } else if (forMale == 1) {
                    vo.setTzsx(vo.getValuemalemax());
                    vo.setTzxx(vo.getValuemalemin());
                } else {
                    vo.setTzsx(vo.getValuefemalemax());
                    vo.setTzxx(vo.getValuefemalemin());
                }
            }
        }
        iPage.setRecords(list);
        return iPage;
    }
}

