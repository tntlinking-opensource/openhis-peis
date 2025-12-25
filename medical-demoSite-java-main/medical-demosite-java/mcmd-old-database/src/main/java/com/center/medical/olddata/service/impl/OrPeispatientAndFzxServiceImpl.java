package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrPeispatientAndFzx;
import com.center.medical.olddata.dao.OrPeispatientAndFzxMapper;
import com.center.medical.olddata.service.OrPeispatientAndFzxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (PeispatientAndFzx)服务实现类
 *
 * @author ay
 * @since 2024-04-17 10:37:32
 */
@Slf4j
@Service("orPeispatientAndFzxService")
@RequiredArgsConstructor
public class OrPeispatientAndFzxServiceImpl extends ServiceImpl<OrPeispatientAndFzxMapper, OrPeispatientAndFzx> implements OrPeispatientAndFzxService {

    private final OrPeispatientAndFzxMapper orPeispatientAndFzxMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param PeispatientAndFzx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OrPeispatientAndFzx> getPage(PageParam<OrPeispatientAndFzx> page, OrPeispatientAndFzx param) {
        return orPeispatientAndFzxMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public OrPeispatientAndFzx getInfoById(String id) {
        return orPeispatientAndFzxMapper.getInfoById(id);
    }

    /**
     * 通过体检者id查询
     * @param pid
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<OrPeispatientAndFzx> getByPid(String pid) {
        return orPeispatientAndFzxMapper.selectList(new LambdaQueryWrapper<OrPeispatientAndFzx>()
                .eq(OrPeispatientAndFzx::getPatientId,pid));
    }
}

