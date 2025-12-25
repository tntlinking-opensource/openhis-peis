package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrVationAndFzx;
import com.center.medical.olddata.dao.OrVationAndFzxMapper;
import com.center.medical.olddata.service.OrVationAndFzxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 团体任务分中心（不会被同步）(MdVationAndFzx)服务实现类
 *
 * @author ay
 * @since 2024-04-11 11:00:25
 */
@Slf4j
@Service("orVationAndFzxService")
@RequiredArgsConstructor
public class OrVationAndFzxServiceImpl extends ServiceImpl<OrVationAndFzxMapper, OrVationAndFzx> implements OrVationAndFzxService {

    private final OrVationAndFzxMapper orVationAndFzxMapper;

    /**
     * 分页查询[团体任务分中心（不会被同步）]列表
     *
     * @param page  分页参数
     * @param param MdVationAndFzx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OrVationAndFzx> getPage(PageParam<OrVationAndFzx> page, OrVationAndFzx param) {
        return orVationAndFzxMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public OrVationAndFzx getInfoById(String id) {
        return orVationAndFzxMapper.getInfoById(id);
    }


    /**
     * 通过任务id和分中心id查询
     * @param vationId
     * @param fzxId
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public OrVationAndFzx getByVationIdAndFzx(String vationId, String fzxId) {
        return orVationAndFzxMapper.selectOne(new LambdaQueryWrapper<OrVationAndFzx>()
                .eq(OrVationAndFzx::getVationId,vationId)
                .eq(OrVationAndFzx::getFzxId,fzxId)
        );
    }

    /**
     * 通过任务id查询
     * @param vationId
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<OrVationAndFzx> getByVationId(String vationId) {
        return orVationAndFzxMapper.selectList(new LambdaQueryWrapper<OrVationAndFzx>()
                .eq(OrVationAndFzx::getVationId,vationId)
        );
    }
}

