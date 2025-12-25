package com.center.medical.datamove.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.MdPeisorgreservationgroup;
import com.center.medical.datamove.admin.dao.MdPeisorgreservationgroupMapper;
import com.center.medical.datamove.admin.service.MdPeisorgreservationgroupService;
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
}


