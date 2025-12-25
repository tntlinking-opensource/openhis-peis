package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.param.OnlineImportParam;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrCreateorder;
import com.center.medical.olddata.bean.param.ImportTjPeopleParam;
import com.center.medical.olddata.dao.OrCreateorderMapper;
import com.center.medical.olddata.service.OrCreateorderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单表(Createorder)服务实现类
 *
 * @author ay
 * @since 2023-07-25 18:20:59
 */

@Slf4j
@Service("orCreateorderService")
@RequiredArgsConstructor
@DataSource(value = DataSourceType.SLAVE)
public class OrCreateorderServiceImpl extends ServiceImpl<OrCreateorderMapper, OrCreateorder> implements OrCreateorderService {

    private final OrCreateorderMapper createorderMapper;

    /**
     * 分页查询[订单表]列表
     *
     * @param page  分页参数
     * @param param Createorder查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OrCreateorder> getPage(PageParam<OrCreateorder> page, OrCreateorder param) {
        return createorderMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public OrCreateorder getInfoById(String id) {
        return createorderMapper.getInfoById(id);
    }


    /**
     * 根据订单号获取记录详情
     *
     * @param ddh 订单号
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public OrCreateorder getInfoByDdh(String ddh) {
        return createorderMapper.getInfoByDdh(ddh);
    }


    /**
     * 查询未完成的订单
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<OrCreateorder> getIncomplete(ImportTjPeopleParam param) {
        return createorderMapper.getIncomplete(param);
    }


    /**
     * 获取线上导入数据
     * @param param
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<OrCreateorder> getOnlineImport(OnlineImportParam param) {
        return createorderMapper.getOnlineImport(param);
    }
}


