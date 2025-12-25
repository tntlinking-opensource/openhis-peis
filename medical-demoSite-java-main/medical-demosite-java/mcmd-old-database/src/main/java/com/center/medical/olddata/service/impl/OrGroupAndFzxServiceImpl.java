package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrGroupAndFzx;
import com.center.medical.olddata.dao.OrGroupAndFzxMapper;
import com.center.medical.olddata.service.OrGroupAndFzxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (GroupAndFzx)服务实现类
 *
 * @author ay
 * @since 2024-04-11 10:44:32
 */
@Slf4j
@Service("orGroupAndFzxService")
@RequiredArgsConstructor
public class OrGroupAndFzxServiceImpl extends ServiceImpl<OrGroupAndFzxMapper, OrGroupAndFzx> implements OrGroupAndFzxService {

    private final OrGroupAndFzxMapper orGroupAndFzxMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param GroupAndFzx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OrGroupAndFzx> getPage(PageParam<OrGroupAndFzx> page, OrGroupAndFzx param) {
        return orGroupAndFzxMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public OrGroupAndFzx getInfoById(String id) {
        return orGroupAndFzxMapper.getInfoById(id);
    }


    /**
     * 通过分组id和分中心id查询
     * @param groupId
     * @param fzxId
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public OrGroupAndFzx getByGroupIdAndFzx(String groupId, String fzxId) {
        return orGroupAndFzxMapper.selectOne(new LambdaQueryWrapper<OrGroupAndFzx>()
                .eq(OrGroupAndFzx::getGroupId,groupId)
                .eq(OrGroupAndFzx::getFzxId,fzxId)
        );
    }

    /**
     * 通过分组id查询
     * @param groupId
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<OrGroupAndFzx> getByGroupId(String groupId) {
        return orGroupAndFzxMapper.selectList(new LambdaQueryWrapper<OrGroupAndFzx>()
                .eq(OrGroupAndFzx::getGroupId,groupId)
        );
    }
}

