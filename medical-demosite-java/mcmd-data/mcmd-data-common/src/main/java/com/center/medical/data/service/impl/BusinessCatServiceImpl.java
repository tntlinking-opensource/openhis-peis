package com.center.medical.data.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.BusinessCat;
import com.center.medical.data.bean.param.BusinessCatParam;
import com.center.medical.data.dao.BusinessCatMapper;
import com.center.medical.data.service.BusinessCatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务类型(BusinessCat)表服务实现类
 *
 * @author 路飞船长
 * @since 2023-02-28 15:40:49
 */
@Slf4j
@Service("businessCatService")
@RequiredArgsConstructor
public class BusinessCatServiceImpl extends ServiceImpl<BusinessCatMapper, BusinessCat> implements BusinessCatService {

    private final BusinessCatMapper businessCatMapper;

    /**
     * 分页查询[业务类型]列表
     *
     * @param page  分页参数
     * @param param BusinessCat查询参数
     * @return 分页数据
     */
    @Override
    public IPage<BusinessCat> getPage(PageParam<BusinessCat> page, BusinessCatParam param) {
        return businessCatMapper.getPage(page, param);
    }

    @Override
    public List<BusinessCat> getList(BusinessCat param) {
        return businessCatMapper.getList(param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键typeId
     * @return 详情信息
     */
    @Override
    public BusinessCat getInfoById(Long id) {
        return businessCatMapper.getInfoById(id);
    }


    /**
     * 导出业务类型
     * @param param
     * @return
     */
    @Override
    public List<BusinessCat> getExportData(BusinessCatParam param) {
        return businessCatMapper.getExportData(param);
    }
}

