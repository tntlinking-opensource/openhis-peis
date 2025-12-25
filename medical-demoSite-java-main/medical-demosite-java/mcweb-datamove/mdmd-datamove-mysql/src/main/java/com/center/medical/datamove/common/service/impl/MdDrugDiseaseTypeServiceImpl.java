package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdDrugDiseaseTypeMapper;
import com.center.medical.datamove.common.bean.model.MdDrugDiseaseType;
import com.center.medical.datamove.common.service.MdDrugDiseaseTypeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * jc禁忌疾病分类(MdDrugDiseaseType)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:14
 */
@Slf4j
@Service("mdDrugDiseaseTypeService")
@RequiredArgsConstructor
public class MdDrugDiseaseTypeServiceImpl extends ServiceImpl<MdDrugDiseaseTypeMapper, MdDrugDiseaseType> implements MdDrugDiseaseTypeService {

    private final MdDrugDiseaseTypeMapper mdDrugDiseaseTypeMapper;

    /**
     * 分页查询[jc禁忌疾病分类]列表
     *
     * @param page  分页参数
     * @param param MdDrugDiseaseType查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdDrugDiseaseType> getPage(PageParam<MdDrugDiseaseType> page, MdDrugDiseaseType param) {
        return mdDrugDiseaseTypeMapper.getPage(page, param);
    }


}


