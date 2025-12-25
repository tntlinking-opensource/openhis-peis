package com.center.medical.appadmin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.appadmin.bean.model.SuixingPayConfig;
import com.center.medical.appadmin.bean.param.SuixingPayConfigParam;
import com.center.medical.appadmin.bean.param.SuixingSaOrUpParam;
import com.center.medical.appadmin.bean.vo.SuixingPayConfigVo;
import com.center.medical.appadmin.dao.SuixingPayConfigMapper;
import com.center.medical.appadmin.service.SuixingPayConfigService;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 随行支付配置参数(SuixingPayConfig)服务实现类
 *
 * @author ay
 * @since 2024-07-12 17:05:40
 */
@Slf4j
@Service("suixingPayConfigService")
@RequiredArgsConstructor
public class SuixingPayConfigServiceImpl extends ServiceImpl<SuixingPayConfigMapper, SuixingPayConfig> implements SuixingPayConfigService {

    private final SuixingPayConfigMapper suixingPayConfigMapper;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询[随行支付配置参数]列表
     *
     * @param page  分页参数
     * @param param SuixingPayConfig查询参数
     * @return 分页数据
     */
    @Override
    @DataSource(value = DataSourceType.APPLET)
    public IPage<SuixingPayConfigVo> getPage(PageParam<SuixingPayConfigVo> page, SuixingPayConfigParam param) {
        return suixingPayConfigMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    @DataSource(value = DataSourceType.APPLET)
    public SuixingPayConfig getInfoById(String id) {
        return suixingPayConfigMapper.getInfoById(id);
    }

    /**
     * 通过分中心id查询
     * @param branchId
     * @return
     */
    @Override
    public SuixingPayConfig getInfoByFzx(String branchId) {
        return suixingPayConfigMapper.getInfoByFzx(branchId);
    }

    /**
     * 添加或修改
     * @param param
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.APPLET)
    public Boolean saOrUp(SuixingSaOrUpParam param) {
        if (StringUtils.isNotEmpty(param.getId())){
            SuixingPayConfig suixingPayConfig = suixingPayConfigMapper.getInfoById(param.getId());
            if (ObjectUtils.isEmpty(suixingPayConfig)){
                throw new ServiceException("id不正确!");
            }
        }
        SuixingPayConfig suixingPayConfig = mapperFacade.map(param, SuixingPayConfig.class);
        return saveOrUpdate(suixingPayConfig);
    }

    /**
     * 删除随行支付配置参数
     * @param ids
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.APPLET)
    public Boolean deleteByIds(List<String> ids) {
        return removeBatchByIds(ids);
    }
}

