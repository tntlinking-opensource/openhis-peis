package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdItemsBarcodeClassMapper;
import com.center.medical.datamove.common.bean.model.MdItemsBarcodeClass;
import com.center.medical.datamove.common.service.MdItemsBarcodeClassService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 收费项目条码打印分类(MdItemsBarcodeClass)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:24
 */
@Slf4j
@Service("mdItemsBarcodeClassService")
@RequiredArgsConstructor
public class MdItemsBarcodeClassServiceImpl extends ServiceImpl<MdItemsBarcodeClassMapper, MdItemsBarcodeClass> implements MdItemsBarcodeClassService {

    private final MdItemsBarcodeClassMapper mdItemsBarcodeClassMapper;

    /**
     * 分页查询[收费项目条码打印分类]列表
     *
     * @param page  分页参数
     * @param param MdItemsBarcodeClass查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdItemsBarcodeClass> getPage(PageParam<MdItemsBarcodeClass> page, MdItemsBarcodeClass param) {
        return mdItemsBarcodeClassMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdItemsBarcodeClass getInfoById(String id) {
        return mdItemsBarcodeClassMapper.getInfoById(id);
    }

}


