package com.center.medical.datamove.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.MdPeisorgreservation;
import com.center.medical.datamove.admin.dao.MdPeisorgreservationMapper;
import com.center.medical.datamove.admin.service.MdPeisorgreservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 体检者团体任务(MdPeisorgreservation)服务实现类
 *
 * @author ay
 * @since 2023-08-12 11:53:30
 */
@Slf4j
@Service("mdPeisorgreservationService")
@RequiredArgsConstructor
public class MdPeisorgreservationServiceImpl extends ServiceImpl<MdPeisorgreservationMapper, MdPeisorgreservation> implements MdPeisorgreservationService {

    private final MdPeisorgreservationMapper mdPeisorgreservationMapper;

    /**
     * 分页查询[体检者团体任务]列表
     *
     * @param page  分页参数
     * @param param MdPeisorgreservation查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdPeisorgreservation> getPage(PageParam<MdPeisorgreservation> page, MdPeisorgreservation param) {
        return mdPeisorgreservationMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdPeisorgreservation getInfoById(String id) {
        return mdPeisorgreservationMapper.getInfoById(id);
    }

    /**
     * 批量添加
     *
     * @param mapAsList
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public void saOrUpList(List<MdPeisorgreservation> mapAsList) {
        saveOrUpdateBatch(mapAsList);
    }


    /**
     * 单条保存
     * @param map
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public void saorUp(MdPeisorgreservation map) {
        saorUp(map);
    }
}


