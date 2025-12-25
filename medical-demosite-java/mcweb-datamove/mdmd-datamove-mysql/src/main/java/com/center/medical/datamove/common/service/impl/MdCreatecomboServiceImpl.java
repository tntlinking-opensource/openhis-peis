package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdCreatecomboMapper;
import com.center.medical.datamove.common.bean.model.MdCreatecombo;
import com.center.medical.datamove.common.service.MdCreatecomboService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 最小套餐(MdCreatecombo)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:19
 */
@Slf4j
@Service("mdCreatecomboService")
@RequiredArgsConstructor
public class MdCreatecomboServiceImpl extends ServiceImpl<MdCreatecomboMapper, MdCreatecombo> implements MdCreatecomboService {

    private final MdCreatecomboMapper mdCreatecomboMapper;

    /**
     * 分页查询[最小套餐]列表
     *
     * @param page  分页参数
     * @param param MdCreatecombo查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdCreatecombo> getPage(PageParam<MdCreatecombo> page, MdCreatecombo param) {
        return mdCreatecomboMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdCreatecombo getInfoById(String id) {
        return mdCreatecomboMapper.getInfoById(id);
    }

}


