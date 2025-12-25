package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrPeispatientarchive;
import com.center.medical.olddata.dao.OrPeispatientarchiveMapper;
import com.center.medical.olddata.service.OrPeispatientarchiveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 体检者档案表(MdPeispatientarchive)服务实现类
 *
 * @author ay
 * @since 2023-09-04 09:16:14
 */
@Slf4j
@Service("orPeispatientarchiveService")
@RequiredArgsConstructor
public class OrPeispatientarchiveServiceImpl extends ServiceImpl<OrPeispatientarchiveMapper, OrPeispatientarchive> implements OrPeispatientarchiveService {

    private final OrPeispatientarchiveMapper orPeispatientarchiveMapper;

    /**
     * 分页查询[体检者档案表]列表
     *
     * @param page  分页参数
     * @param param MdPeispatientarchive查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OrPeispatientarchive> getPage(PageParam<OrPeispatientarchive> page, OrPeispatientarchive param) {
        return orPeispatientarchiveMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public OrPeispatientarchive getInfoById(String id) {
        return orPeispatientarchiveMapper.getInfoById(id);
    }

}

