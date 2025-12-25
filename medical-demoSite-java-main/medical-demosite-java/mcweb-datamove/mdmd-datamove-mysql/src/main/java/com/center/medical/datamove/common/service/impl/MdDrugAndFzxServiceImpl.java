package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdDrugAndFzxMapper;
import com.center.medical.datamove.common.bean.model.MdDrugAndFzx;
import com.center.medical.datamove.common.service.MdDrugAndFzxService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 药品分中心映射(MdDrugAndFzx)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:13
 */
@Slf4j
@Service("mdDrugAndFzxService")
@RequiredArgsConstructor
public class MdDrugAndFzxServiceImpl extends ServiceImpl<MdDrugAndFzxMapper, MdDrugAndFzx> implements MdDrugAndFzxService {

    private final MdDrugAndFzxMapper mdDrugAndFzxMapper;

    /**
     * 分页查询[药品分中心映射]列表
     *
     * @param page  分页参数
     * @param param MdDrugAndFzx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdDrugAndFzx> getPage(PageParam<MdDrugAndFzx> page, MdDrugAndFzx param) {
        return mdDrugAndFzxMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdDrugAndFzx getInfoById(String id) {
        return mdDrugAndFzxMapper.getInfoById(id);
    }

}


