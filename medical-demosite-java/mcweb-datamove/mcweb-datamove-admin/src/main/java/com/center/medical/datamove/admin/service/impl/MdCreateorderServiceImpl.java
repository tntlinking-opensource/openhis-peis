package com.center.medical.datamove.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.MdCreateorder;
import com.center.medical.datamove.admin.dao.MdCreateorderMapper;
import com.center.medical.datamove.admin.service.MdCreateorderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 订单表(MdCreateorder)服务实现类
 *
 * @author ay
 * @since 2023-07-25 18:18:43
 */


@Slf4j
@Service("mdCreateorderService")
@RequiredArgsConstructor
@DataSource(value = DataSourceType.MASTER)
public class MdCreateorderServiceImpl extends ServiceImpl<MdCreateorderMapper, MdCreateorder> implements MdCreateorderService {

    private final MdCreateorderMapper mdCreateorderMapper;

    /**
     * 分页查询[订单表]列表
     *
     * @param page  分页参数
     * @param param MdCreateorder查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdCreateorder> getPage(PageParam<MdCreateorder> page, MdCreateorder param) {
        return mdCreateorderMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdCreateorder getInfoById(String id) {
        return mdCreateorderMapper.getInfoById(id);
    }


    /**
     * 添加或修改
     *
     * @param mdCreateorder
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public void saOrUp(MdCreateorder mdCreateorder) {
        saveOrUpdate(mdCreateorder);
    }
}


