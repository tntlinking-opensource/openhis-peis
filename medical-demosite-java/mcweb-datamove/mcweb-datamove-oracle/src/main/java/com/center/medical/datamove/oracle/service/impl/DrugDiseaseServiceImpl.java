package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.DrugDiseaseMapper;
import com.center.medical.datamove.oracle.bean.model.DrugDisease;
import com.center.medical.datamove.oracle.service.DrugDiseaseService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC禁忌疾病(DrugDisease)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:18:25
 */
@Slf4j
@Service("drugDiseaseService")
@RequiredArgsConstructor
public class DrugDiseaseServiceImpl extends ServiceImpl<DrugDiseaseMapper, DrugDisease> implements DrugDiseaseService {

    private final DrugDiseaseMapper drugDiseaseMapper;

    /**
     * 分页查询[JC禁忌疾病]列表
     *
     * @param page  分页参数
     * @param param DrugDisease查询参数
     * @return 分页数据
     */
    @Override
    public IPage<DrugDisease> getPage(PageParam<DrugDisease> page, DrugDisease param) {
        return drugDiseaseMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public DrugDisease getInfoById(String id) {
        return drugDiseaseMapper.getInfoById(id);
    }

}


