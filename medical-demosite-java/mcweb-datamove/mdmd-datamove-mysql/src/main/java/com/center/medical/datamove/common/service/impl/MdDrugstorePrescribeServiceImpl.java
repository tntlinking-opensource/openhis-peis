package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdDrugstorePrescribeMapper;
import com.center.medical.datamove.common.bean.model.MdDrugstorePrescribe;
import com.center.medical.datamove.common.service.MdDrugstorePrescribeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 开药记录(MdDrugstorePrescribe)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:15
 */
@Slf4j
@Service("mdDrugstorePrescribeService")
@RequiredArgsConstructor
public class MdDrugstorePrescribeServiceImpl extends ServiceImpl<MdDrugstorePrescribeMapper, MdDrugstorePrescribe> implements MdDrugstorePrescribeService {

    private final MdDrugstorePrescribeMapper mdDrugstorePrescribeMapper;

    /**
     * 分页查询[开药记录]列表
     *
     * @param page  分页参数
     * @param param MdDrugstorePrescribe查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdDrugstorePrescribe> getPage(PageParam<MdDrugstorePrescribe> page, MdDrugstorePrescribe param) {
        return mdDrugstorePrescribeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdDrugstorePrescribe getInfoById(String id) {
        return mdDrugstorePrescribeMapper.getInfoById(id);
    }

}


