package com.center.medical.olddata.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdPeisorgreservationgroup;
import com.center.medical.olddata.dao.MdPeisorgreservationgroupMapper;
import com.center.medical.olddata.service.MdPeisorgreservationgroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 体检者任务分组(MdPeisorgreservationgroup)服务实现类
 *
 * @author ay
 * @since 2023-08-12 11:53:29
 */
@Slf4j
@Service("mdPeisorgreservationgroupService")
@RequiredArgsConstructor
public class MdPeisorgreservationgroupServiceImpl extends ServiceImpl<MdPeisorgreservationgroupMapper, MdPeisorgreservationgroup> implements MdPeisorgreservationgroupService {

    private final MdPeisorgreservationgroupMapper mdPeisorgreservationgroupMapper;

    /**
     * 分页查询[体检者任务分组]列表
     *
     * @param page  分页参数
     * @param param MdPeisorgreservationgroup查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdPeisorgreservationgroup> getPage(PageParam<MdPeisorgreservationgroup> page, MdPeisorgreservationgroup param) {
        return mdPeisorgreservationgroupMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdPeisorgreservationgroup getInfoById(String id) {
        return mdPeisorgreservationgroupMapper.getInfoById(id);
    }


    /**
     * 批量保存
     *
     * @param mapAsList
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public void saOrUpList(List<MdPeisorgreservationgroup> mapAsList) {
        saveOrUpdateBatch(mapAsList);
    }


    /**
     * 通过任务id和套餐id查询
     * @param id
     * @param idExamsuite
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public MdPeisorgreservationgroup getByVationIdAndTcId(String id, String idExamsuite) {
        //新系统一个套餐可能有多个分组，取第一个
        List<MdPeisorgreservationgroup> peisorgreservationgroup = mdPeisorgreservationgroupMapper.selectList(
                new LambdaQueryWrapper<MdPeisorgreservationgroup>()
                .eq(MdPeisorgreservationgroup::getIdOrgreservation, id)
                .eq(MdPeisorgreservationgroup::getIdExamsuite, idExamsuite)
        );
        if (CollectionUtil.isNotEmpty(peisorgreservationgroup)){
            return peisorgreservationgroup.get(0);
        }else {
            return null;
        }

    }


    /**
     * 通过任务id查询
     * @param id
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public List<MdPeisorgreservationgroup> getByVationId(String id) {
        List<MdPeisorgreservationgroup> list = mdPeisorgreservationgroupMapper.selectList(
                new LambdaQueryWrapper<MdPeisorgreservationgroup>()
                        .eq(MdPeisorgreservationgroup::getIdOrgreservation, id)
        );
        return list;
    }
}


