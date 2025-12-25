package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdPeispatientAndFzxMapper;
import com.center.medical.datamove.common.bean.model.MdPeispatientAndFzx;
import com.center.medical.datamove.common.service.MdPeispatientAndFzxService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 分组分中心(MdPeispatientAndFzx)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:11
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

}


