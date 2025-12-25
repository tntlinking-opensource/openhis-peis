package com.center.medical.abteilung.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.abteilung.bean.vo.ClassCheckDataVo;
import com.center.medical.abteilung.dao.DrugstoreClassMapper;
import com.center.medical.abteilung.bean.model.DrugstoreClass;
import com.center.medical.abteilung.service.DrugstoreClassService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 药品分类(DrugstoreClass)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:03
 */
@Slf4j
@Service("drugstoreClassService")
@RequiredArgsConstructor
public class DrugstoreClassServiceImpl extends ServiceImpl<DrugstoreClassMapper, DrugstoreClass> implements DrugstoreClassService {

    private final DrugstoreClassMapper drugstoreClassMapper;

    /**
     * 分页查询[药品分类]列表
     *
     * @param page  分页参数
     * @param param DrugstoreClass查询参数
     * @return 分页数据
     */
    @Override
    public IPage<DrugstoreClass> getList(PageParam<DrugstoreClass> page, DrugstoreClass param) {
        return drugstoreClassMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public DrugstoreClass getInfoById(String id) {
        return drugstoreClassMapper.getInfoById(id);
    }


    /**
     * 获取类别代号下拉列表
     * @param key
     * @return
     */
    @Override
    public List<ClassCheckDataVo> getClassCheckData(String key) {
        return drugstoreClassMapper.getClassCheckData(key);
    }
}

