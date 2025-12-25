package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdHarmAndFzxMapper;
import com.center.medical.datamove.common.bean.model.MdHarmAndFzx;
import com.center.medical.datamove.common.service.MdHarmAndFzxService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 危害因素和分中心(MdHarmAndFzx)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:22
 */
@Slf4j
@Service("mdHarmAndFzxService")
@RequiredArgsConstructor
public class MdHarmAndFzxServiceImpl extends ServiceImpl<MdHarmAndFzxMapper, MdHarmAndFzx> implements MdHarmAndFzxService {

    private final MdHarmAndFzxMapper mdHarmAndFzxMapper;

    /**
     * 分页查询[危害因素和分中心]列表
     *
     * @param page  分页参数
     * @param param MdHarmAndFzx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdHarmAndFzx> getPage(PageParam<MdHarmAndFzx> page, MdHarmAndFzx param) {
        return mdHarmAndFzxMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdHarmAndFzx getInfoById(String id) {
        return mdHarmAndFzxMapper.getInfoById(id);
    }

    ;

}


