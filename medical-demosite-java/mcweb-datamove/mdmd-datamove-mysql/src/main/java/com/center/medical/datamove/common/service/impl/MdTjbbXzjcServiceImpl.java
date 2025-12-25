package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdTjbbXzjcMapper;
import com.center.medical.datamove.common.bean.model.MdTjbbXzjc;
import com.center.medical.datamove.common.service.MdTjbbXzjcService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS血脂检测体检报表(MdTjbbXzjc)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:49:06
 */
@Slf4j
@Service("mdTjbbXzjcService")
@RequiredArgsConstructor
public class MdTjbbXzjcServiceImpl extends ServiceImpl<MdTjbbXzjcMapper, MdTjbbXzjc> implements MdTjbbXzjcService {

    private final MdTjbbXzjcMapper mdTjbbXzjcMapper;

    /**
     * 分页查询[KS血脂检测体检报表]列表
     *
     * @param page  分页参数
     * @param param MdTjbbXzjc查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdTjbbXzjc> getPage(PageParam<MdTjbbXzjc> page, MdTjbbXzjc param) {
        return mdTjbbXzjcMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdTjbbXzjc getInfoById(String id) {
        return mdTjbbXzjcMapper.getInfoById(id);
    }

    ;

}


