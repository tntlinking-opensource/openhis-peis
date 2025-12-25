package com.center.medical.olddata.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdGroupAndFzx;
import com.center.medical.olddata.dao.MdGroupAndFzxMapper;
import com.center.medical.olddata.service.MdGroupAndFzxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分组分中心(MdGroupAndFzx)服务实现类
 *
 * @author ay
 * @since 2024-04-11 10:49:53
 */
@Slf4j
@Service("mdGroupAndFzxService")
@RequiredArgsConstructor
public class MdGroupAndFzxServiceImpl extends ServiceImpl<MdGroupAndFzxMapper, MdGroupAndFzx> implements MdGroupAndFzxService {

    private final MdGroupAndFzxMapper mdGroupAndFzxMapper;

    /**
     * 分页查询[分组分中心]列表
     *
     * @param page  分页参数
     * @param param MdGroupAndFzx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdGroupAndFzx> getPage(PageParam<MdGroupAndFzx> page, MdGroupAndFzx param) {
        return mdGroupAndFzxMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdGroupAndFzx getInfoById(String id) {
        return mdGroupAndFzxMapper.getInfoById(id);
    }


    /**
     * 通过分组id和分中心id查询
     * @param groupId
     * @param fzxId
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public MdGroupAndFzx getByGroupIdAndFzx(String groupId, String fzxId) {
        List<MdGroupAndFzx> list = mdGroupAndFzxMapper.selectList(new LambdaQueryWrapper<MdGroupAndFzx>()
                .eq(MdGroupAndFzx::getGroupId, groupId)
                .eq(MdGroupAndFzx::getFzxId, fzxId)
        );
        if (CollectionUtil.isNotEmpty(list)){
            return list.get(0);
        }else {
            return null;
        }

    }


    /**
     * 批量插入
     * @param mdGroupAndFzxList
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public void saOrUpList(List<MdGroupAndFzx> mdGroupAndFzxList) {
        saveOrUpdateBatch(mdGroupAndFzxList);
    }

    /**
     * 通过分组id查询
     * @param groupId
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public List<MdGroupAndFzx> getByGroupId(String groupId) {
        return mdGroupAndFzxMapper.selectList(new LambdaQueryWrapper<MdGroupAndFzx>()
                .eq(MdGroupAndFzx::getGroupId, groupId)
        );
    }
}

