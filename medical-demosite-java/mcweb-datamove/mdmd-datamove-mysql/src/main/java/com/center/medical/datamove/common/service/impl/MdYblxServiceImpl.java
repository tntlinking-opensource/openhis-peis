package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdYblxMapper;
import com.center.medical.datamove.common.bean.model.MdYblx;
import com.center.medical.datamove.common.service.MdYblxService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 样本类型(MdYblx)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:27
 */
@Slf4j
@Service("mdYblxService")
@RequiredArgsConstructor
public class MdYblxServiceImpl extends ServiceImpl<MdYblxMapper, MdYblx> implements MdYblxService {

    private final MdYblxMapper mdYblxMapper;

    /**
     * 分页查询[样本类型]列表
     *
     * @param page  分页参数
     * @param param MdYblx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdYblx> getPage(PageParam<MdYblx> page, MdYblx param) {
        return mdYblxMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdYblx getInfoById(String id) {
        return mdYblxMapper.getInfoById(id);
    }

}


