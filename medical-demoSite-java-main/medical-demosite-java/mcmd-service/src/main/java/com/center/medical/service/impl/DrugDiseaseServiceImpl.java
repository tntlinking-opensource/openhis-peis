package com.center.medical.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.dao.DrugDiseaseMapper;
import com.center.medical.bean.model.DrugDisease;
import com.center.medical.service.DrugDiseaseService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 禁忌疾病(DrugDisease)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:49
 */
@Slf4j
@Service("drugDiseaseService")
@RequiredArgsConstructor
public class DrugDiseaseServiceImpl extends ServiceImpl<DrugDiseaseMapper, DrugDisease> implements DrugDiseaseService {

    private final DrugDiseaseMapper drugDiseaseMapper;

    /**
     * 分页查询[禁忌疾病]列表
     *
     * @param page  分页参数
     * @param param DrugDisease查询参数
     * @return 分页数据
     */
    @Override
    public IPage<DrugDisease> getList(PageParam<DrugDisease> page, DrugDisease param) {
        return drugDiseaseMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public DrugDisease getInfoById(String id) {
        return drugDiseaseMapper.getInfoById(id);
    }

    /**
     * 根据条件分页获取
     * @param page
     * @param inputCode
     * @return
     */
    @Override
    public IPage<DrugDisease> getJjjbData(PageParam<DrugDisease> page, String inputCode) {
        QueryWrapper<DrugDisease> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isBlank(inputCode)) {
            queryWrapper.like("input_code", inputCode.toUpperCase());
        }
        queryWrapper.eq("is_delete", 0);
        PageParam<DrugDisease> drugDiseasePageParam = drugDiseaseMapper.selectPage(page, queryWrapper);
        return drugDiseasePageParam;

    }
}

