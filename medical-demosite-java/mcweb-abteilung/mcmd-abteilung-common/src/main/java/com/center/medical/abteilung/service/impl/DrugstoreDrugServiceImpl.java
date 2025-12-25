package com.center.medical.abteilung.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.abteilung.bean.param.DrugstoreDrugParam;
import com.center.medical.abteilung.bean.vo.DrugstoreDrugVo;
import com.center.medical.abteilung.dao.DrugstoreDrugMapper;
import com.center.medical.abteilung.bean.model.DrugstoreDrug;
import com.center.medical.abteilung.service.DrugstoreDrugService;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.ToolUtil;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 药品基础表(DrugstoreDrug)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:06
 */
@Slf4j
@Service("drugstoreDrugService")
@RequiredArgsConstructor
public class DrugstoreDrugServiceImpl extends ServiceImpl<DrugstoreDrugMapper, DrugstoreDrug> implements DrugstoreDrugService {

    private final DrugstoreDrugMapper drugstoreDrugMapper;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询[药品基础表]列表
     *
     * @param page  分页参数
     * @param param DrugstoreDrug查询参数
     * @return 分页数据
     */
    @Override
    public IPage<DrugstoreDrugVo> getList(PageParam<DrugstoreDrugVo> page, DrugstoreDrugParam param) {
        return drugstoreDrugMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public DrugstoreDrug getInfoById(String id) {
        return drugstoreDrugMapper.getInfoById(id);
    }


    /**
     * 不分页查询
     * @param param
     * @return
     */
    @Override
    public List<DrugstoreDrugVo> getSelectData(DrugstoreDrugParam param) {
        return drugstoreDrugMapper.getSelectData(param);
    }


    /**
     * 添加或保存
     * @param drug
     * @return
     */
    @Override
    public Boolean saOrUp(DrugstoreDrug drug) {
        //拼音码
        drug.setInputCode(ToolUtil.getHanziPinyinHeadChar(drug.getDrugName()));
        String id = drug.getId();
        //没id插入,有id保存
        if(StringUtils.isEmpty(id)){
            drugstoreDrugMapper.insert(drug);
        }else{
            drugstoreDrugMapper.updateById(drug);
        }
        return Boolean.TRUE;
    }
}

