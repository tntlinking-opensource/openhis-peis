package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdDrugDiseaseMapper;
import com.center.medical.datamove.common.bean.model.MdDrugDisease;
import com.center.medical.datamove.common.service.MdDrugDiseaseService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * jc禁忌疾病(MdDrugDisease)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:14
 */
@Slf4j
@Service("mdDrugDiseaseService")
@RequiredArgsConstructor
public class MdDrugDiseaseServiceImpl extends ServiceImpl<MdDrugDiseaseMapper, MdDrugDisease> implements MdDrugDiseaseService {

    private final MdDrugDiseaseMapper mdDrugDiseaseMapper;

    /**
     * 分页查询[jc禁忌疾病]列表
     *
     * @param page  分页参数
     * @param param MdDrugDisease查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdDrugDisease> getPage(PageParam<MdDrugDisease> page, MdDrugDisease param) {
        return mdDrugDiseaseMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdDrugDisease getInfoById(String id) {
        return mdDrugDiseaseMapper.getInfoById(id);
    }

}


