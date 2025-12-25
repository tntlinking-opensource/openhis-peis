package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdExpresscompanyMapper;
import com.center.medical.datamove.common.bean.model.MdExpresscompany;
import com.center.medical.datamove.common.service.MdExpresscompanyService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 快递公司表(MdExpresscompany)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:16
 */
@Slf4j
@Service("mdExpresscompanyService")
@RequiredArgsConstructor
public class MdExpresscompanyServiceImpl extends ServiceImpl<MdExpresscompanyMapper, MdExpresscompany> implements MdExpresscompanyService {

    private final MdExpresscompanyMapper mdExpresscompanyMapper;

    /**
     * 分页查询[快递公司表]列表
     *
     * @param page  分页参数
     * @param param MdExpresscompany查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdExpresscompany> getPage(PageParam<MdExpresscompany> page, MdExpresscompany param) {
        return mdExpresscompanyMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdExpresscompany getInfoById(String id) {
        return mdExpresscompanyMapper.getInfoById(id);
    }

}


