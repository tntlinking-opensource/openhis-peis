package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdPeispatienthistoryMapper;
import com.center.medical.datamove.common.bean.model.MdPeispatienthistory;
import com.center.medical.datamove.common.service.MdPeispatienthistoryService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检者（history）表(MdPeispatienthistory)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:15
 */
@Slf4j
@Service("mdPeispatienthistoryService")
@RequiredArgsConstructor
public class MdPeispatienthistoryServiceImpl extends ServiceImpl<MdPeispatienthistoryMapper, MdPeispatienthistory> implements MdPeispatienthistoryService {

    private final MdPeispatienthistoryMapper mdPeispatienthistoryMapper;

    /**
     * 分页查询[体检者（history）表]列表
     *
     * @param page  分页参数
     * @param param MdPeispatienthistory查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdPeispatienthistory> getPage(PageParam<MdPeispatienthistory> page, MdPeispatienthistory param) {
        return mdPeispatienthistoryMapper.getPage(page, param);
    }


}


