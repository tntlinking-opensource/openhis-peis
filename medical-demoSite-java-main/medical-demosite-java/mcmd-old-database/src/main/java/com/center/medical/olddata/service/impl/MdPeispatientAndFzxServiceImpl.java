package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdPeispatientAndFzx;
import com.center.medical.olddata.dao.MdPeispatientAndFzxMapper;
import com.center.medical.olddata.service.MdPeispatientAndFzxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分组分中心(MdPeispatientAndFzx)服务实现类
 *
 * @author ay
 * @since 2024-04-17 10:34:48
 */
@Slf4j
@Service("mdPeispatientAndFzxService")
@RequiredArgsConstructor
public class MdPeispatientAndFzxServiceImpl extends ServiceImpl<MdPeispatientAndFzxMapper, MdPeispatientAndFzx> implements MdPeispatientAndFzxService {

    private final MdPeispatientAndFzxMapper mdPeispatientAndFzxMapper;

    /**
     * 分页查询[分组分中心]列表
     *
     * @param page  分页参数
     * @param param MdPeispatientAndFzx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdPeispatientAndFzx> getPage(PageParam<MdPeispatientAndFzx> page, MdPeispatientAndFzx param) {
        return mdPeispatientAndFzxMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdPeispatientAndFzx getInfoById(String id) {
        return mdPeispatientAndFzxMapper.getInfoById(id);
    }


    /**
     * 通过体检者id和分中心id查询
     * @param patientId
     * @param fzxId
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public MdPeispatientAndFzx getByPidAndFzx(String patientId, String fzxId) {
        MdPeispatientAndFzx mdPeispatientAndFzx = mdPeispatientAndFzxMapper.selectOne(new LambdaQueryWrapper<MdPeispatientAndFzx>()
                .eq(MdPeispatientAndFzx::getPatientId, patientId)
                .eq(MdPeispatientAndFzx::getFzxId, fzxId)
        );
        return mdPeispatientAndFzx;
    }


    /**
     * 批量保存
     * @param mdPeispatientAndFzxList
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public void saOrUpList(List<MdPeispatientAndFzx> mdPeispatientAndFzxList) {
        saveOrUpdateBatch(mdPeispatientAndFzxList);
    }
}

