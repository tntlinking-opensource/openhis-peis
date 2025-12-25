package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.CrmSellprintteamsMapper;
import com.center.medical.datamove.common.bean.model.CrmSellprintteams;
import com.center.medical.datamove.common.service.CrmSellprintteamsService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 销售打印项目分类设置表(CrmSellprintteams)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:02
 */
@Slf4j
@Service("crmSellprintteamsService")
@RequiredArgsConstructor
public class CrmSellprintteamsServiceImpl extends ServiceImpl<CrmSellprintteamsMapper, CrmSellprintteams> implements CrmSellprintteamsService {

    private final CrmSellprintteamsMapper crmSellprintteamsMapper;

    /**
     * 分页查询[销售打印项目分类设置表]列表
     *
     * @param page  分页参数
     * @param param CrmSellprintteams查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CrmSellprintteams> getPage(PageParam<CrmSellprintteams> page, CrmSellprintteams param) {
        return crmSellprintteamsMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public CrmSellprintteams getInfoById(String id) {
        return crmSellprintteamsMapper.getInfoById(id);
    }

}


