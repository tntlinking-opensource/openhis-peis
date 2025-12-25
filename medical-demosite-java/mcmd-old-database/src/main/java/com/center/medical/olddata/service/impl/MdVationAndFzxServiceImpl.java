package com.center.medical.olddata.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdVationAndFzx;
import com.center.medical.olddata.dao.MdVationAndFzxMapper;
import com.center.medical.olddata.service.MdVationAndFzxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 团体任务分中心（不会被同步）(MdVationAndFzx)服务实现类
 *
 * @author ay
 * @since 2024-04-11 10:54:33
 */
@Slf4j
@Service("mdVationAndFzxService")
@RequiredArgsConstructor
public class MdVationAndFzxServiceImpl extends ServiceImpl<MdVationAndFzxMapper, MdVationAndFzx> implements MdVationAndFzxService {

    private final MdVationAndFzxMapper mdVationAndFzxMapper;

    /**
     * 分页查询[团体任务分中心（不会被同步）]列表
     *
     * @param page  分页参数
     * @param param MdVationAndFzx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdVationAndFzx> getPage(PageParam<MdVationAndFzx> page, MdVationAndFzx param) {
        return mdVationAndFzxMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdVationAndFzx getInfoById(String id) {
        return mdVationAndFzxMapper.getInfoById(id);
    }

    /**
     * 通过任务id和分中心id查询
     * @param vationId
     * @param fzxId
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public MdVationAndFzx getByVationIdAndFzx(String vationId, String fzxId) {
        List<MdVationAndFzx> mdVationAndFzx = mdVationAndFzxMapper.selectList(new LambdaQueryWrapper<MdVationAndFzx>()
                .eq(MdVationAndFzx::getVationId, vationId)
                .eq(MdVationAndFzx::getFzxId, fzxId)
                .orderByAsc(MdVationAndFzx::getCreatedate)
        );
        if (CollectionUtil.isNotEmpty(mdVationAndFzx)) {
            return mdVationAndFzx.get(0);
        }else {
            return null;
        }
    }


    /**
     * 批量保存
     * @param mdVationAndFzxList
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public void saOrUpList(List<MdVationAndFzx> mdVationAndFzxList) {
        saveOrUpdateBatch(mdVationAndFzxList);
    }

    /**
     * 通过任务id查询
     * @param vationId
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public List<MdVationAndFzx> getByVationId(String vationId) {
        return mdVationAndFzxMapper.selectList(new LambdaQueryWrapper<MdVationAndFzx>()
                .eq(MdVationAndFzx::getVationId,vationId)
        );
    }
}

