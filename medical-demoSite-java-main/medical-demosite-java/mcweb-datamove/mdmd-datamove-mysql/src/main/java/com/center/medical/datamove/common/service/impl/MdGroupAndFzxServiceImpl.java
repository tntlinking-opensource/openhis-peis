package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdGroupAndFzxMapper;
import com.center.medical.datamove.common.bean.model.MdGroupAndFzx;
import com.center.medical.datamove.common.service.MdGroupAndFzxService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 分组分中心(MdGroupAndFzx)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:20
 */
@Slf4j
@Service("mdGroupAndFzxService")
@RequiredArgsConstructor
public class MdGroupAndFzxServiceImpl extends ServiceImpl<MdGroupAndFzxMapper, MdGroupAndFzx> implements MdGroupAndFzxService {

    private final MdGroupAndFzxMapper mdGroupAndFzxMapper;

    /**
     * 分页查询[分组分中心]列表
     *
     * @param page  分页参数
     * @param param MdGroupAndFzx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdGroupAndFzx> getPage(PageParam<MdGroupAndFzx> page, MdGroupAndFzx param) {
        return mdGroupAndFzxMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdGroupAndFzx getInfoById(String id) {
        return mdGroupAndFzxMapper.getInfoById(id);
    }

}


