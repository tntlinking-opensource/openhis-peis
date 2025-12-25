package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdPrinttypeMapper;
import com.center.medical.datamove.common.bean.model.MdPrinttype;
import com.center.medical.datamove.common.service.MdPrinttypeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 销售打印分类设置(MdPrinttype)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:16
 */
@Slf4j
@Service("mdPrinttypeService")
@RequiredArgsConstructor
public class MdPrinttypeServiceImpl extends ServiceImpl<MdPrinttypeMapper, MdPrinttype> implements MdPrinttypeService {

    private final MdPrinttypeMapper mdPrinttypeMapper;

    /**
     * 分页查询[销售打印分类设置]列表
     *
     * @param page  分页参数
     * @param param MdPrinttype查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdPrinttype> getPage(PageParam<MdPrinttype> page, MdPrinttype param) {
        return mdPrinttypeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdPrinttype getInfoById(String id) {
        return mdPrinttypeMapper.getInfoById(id);
    }

}


