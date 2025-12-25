package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdDrugstoreClassMapper;
import com.center.medical.datamove.common.bean.model.MdDrugstoreClass;
import com.center.medical.datamove.common.service.MdDrugstoreClassService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 药品分类(MdDrugstoreClass)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:14
 */
@Slf4j
@Service("mdDrugstoreClassService")
@RequiredArgsConstructor
public class MdDrugstoreClassServiceImpl extends ServiceImpl<MdDrugstoreClassMapper, MdDrugstoreClass> implements MdDrugstoreClassService {

    private final MdDrugstoreClassMapper mdDrugstoreClassMapper;

    /**
     * 分页查询[药品分类]列表
     *
     * @param page  分页参数
     * @param param MdDrugstoreClass查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdDrugstoreClass> getPage(PageParam<MdDrugstoreClass> page, MdDrugstoreClass param) {
        return mdDrugstoreClassMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdDrugstoreClass getInfoById(String id) {
        return mdDrugstoreClassMapper.getInfoById(id);
    }

}


