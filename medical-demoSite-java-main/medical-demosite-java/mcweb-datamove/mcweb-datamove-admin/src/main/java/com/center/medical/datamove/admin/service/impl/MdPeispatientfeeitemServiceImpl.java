package com.center.medical.datamove.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.MdPeispatientfeeitem;
import com.center.medical.datamove.admin.dao.MdPeispatientfeeitemMapper;
import com.center.medical.datamove.admin.service.MdPeispatientfeeitemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 体检者收费项目表(MdPeispatientfeeitem)服务实现类
 *
 * @author ay
 * @since 2023-08-12 11:53:33
 */
@Slf4j
@Service("mdPeispatientfeeitemService")
@RequiredArgsConstructor
public class MdPeispatientfeeitemServiceImpl extends ServiceImpl<MdPeispatientfeeitemMapper, MdPeispatientfeeitem> implements MdPeispatientfeeitemService {

    private final MdPeispatientfeeitemMapper mdPeispatientfeeitemMapper;

    /**
     * 分页查询[体检者收费项目表]列表
     *
     * @param page  分页参数
     * @param param MdPeispatientfeeitem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdPeispatientfeeitem> getPage(PageParam<MdPeispatientfeeitem> page, MdPeispatientfeeitem param) {
        return mdPeispatientfeeitemMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdPeispatientfeeitem getInfoById(String id) {
        return mdPeispatientfeeitemMapper.getInfoById(id);
    }


    /**
     * 批量保存
     *
     * @param mapAsList
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public void saOrUpList(List<MdPeispatientfeeitem> mapAsList) {
        saveOrUpdateBatch(mapAsList);
    }
}


