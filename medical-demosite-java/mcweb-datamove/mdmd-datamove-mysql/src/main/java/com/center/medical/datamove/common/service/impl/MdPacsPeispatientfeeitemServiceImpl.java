package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdPacsPeispatientfeeitemMapper;
import com.center.medical.datamove.common.bean.model.MdPacsPeispatientfeeitem;
import com.center.medical.datamove.common.service.MdPacsPeispatientfeeitemService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * PACS-体检者收费项目表(MdPacsPeispatientfeeitem)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:35
 */
@Slf4j
@Service("mdPacsPeispatientfeeitemService")
@RequiredArgsConstructor
public class MdPacsPeispatientfeeitemServiceImpl extends ServiceImpl<MdPacsPeispatientfeeitemMapper, MdPacsPeispatientfeeitem> implements MdPacsPeispatientfeeitemService {

    private final MdPacsPeispatientfeeitemMapper mdPacsPeispatientfeeitemMapper;

    /**
     * 分页查询[PACS-体检者收费项目表]列表
     *
     * @param page  分页参数
     * @param param MdPacsPeispatientfeeitem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdPacsPeispatientfeeitem> getPage(PageParam<MdPacsPeispatientfeeitem> page, MdPacsPeispatientfeeitem param) {
        return mdPacsPeispatientfeeitemMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdPacsPeispatientfeeitem getInfoById(String id) {
        return mdPacsPeispatientfeeitemMapper.getInfoById(id);
    }

}


