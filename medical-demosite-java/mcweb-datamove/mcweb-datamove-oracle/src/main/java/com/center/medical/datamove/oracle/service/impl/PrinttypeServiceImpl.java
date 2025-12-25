package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.PrinttypeMapper;
import com.center.medical.datamove.oracle.bean.model.Printtype;
import com.center.medical.datamove.oracle.service.PrinttypeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 销售打印分类设置(Printtype)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:23:38
 */
@Slf4j
@Service("printtypeService")
@RequiredArgsConstructor
public class PrinttypeServiceImpl extends ServiceImpl<PrinttypeMapper, Printtype> implements PrinttypeService {

    private final PrinttypeMapper printtypeMapper;

    /**
     * 分页查询[销售打印分类设置]列表
     *
     * @param page  分页参数
     * @param param Printtype查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Printtype> getPage(PageParam<Printtype> page, Printtype param) {
        return printtypeMapper.getPage(page, param);
    }


}


