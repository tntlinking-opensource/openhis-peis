package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdPeispatientChargeMain;
import com.center.medical.olddata.dao.MdPeispatientChargeMainMapper;
import com.center.medical.olddata.service.MdPeispatientChargeMainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 体检者费用主表(MdPeispatientChargeMain)服务实现类
 *
 * @author ay
 * @since 2023-08-12 11:53:34
 */
@Slf4j
@Service("mdPeispatientChargeMainService")
@RequiredArgsConstructor
public class MdPeispatientChargeMainServiceImpl extends ServiceImpl<MdPeispatientChargeMainMapper, MdPeispatientChargeMain> implements MdPeispatientChargeMainService {

    private final MdPeispatientChargeMainMapper mdPeispatientChargeMainMapper;

    /**
     * 分页查询[体检者费用主表]列表
     *
     * @param page  分页参数
     * @param param MdPeispatientChargeMain查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdPeispatientChargeMain> getPage(PageParam<MdPeispatientChargeMain> page, MdPeispatientChargeMain param) {
        return mdPeispatientChargeMainMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdPeispatientChargeMain getInfoById(String id) {
        return mdPeispatientChargeMainMapper.getInfoById(id);
    }


    /**
     * 保存或修改
     *
     * @param map
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public void saOrUp(MdPeispatientChargeMain map) {
        saveOrUpdate(map);
    }


    /**
     * 批量保存
     * @param mdPeispatientChargeMainList
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public void saOrUpList(List<MdPeispatientChargeMain> mdPeispatientChargeMainList) {
        saveOrUpdateBatch(mdPeispatientChargeMainList);
    }
}


