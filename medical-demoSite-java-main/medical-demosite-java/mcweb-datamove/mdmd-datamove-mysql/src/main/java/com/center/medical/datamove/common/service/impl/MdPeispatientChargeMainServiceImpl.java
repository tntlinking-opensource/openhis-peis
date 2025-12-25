package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.common.bean.model.MdPeispatientChargeMain;
import com.center.medical.datamove.common.dao.MdPeispatientChargeMainMapper;
import com.center.medical.datamove.common.service.MdPeispatientChargeMainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 体检者费用主表(MdPeispatientChargeMain)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:11
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
     * 保存
     *
     * @param map
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public void saOrUp(MdPeispatientChargeMain map) {
        saOrUp(map);
    }
}


