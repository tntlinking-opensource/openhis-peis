package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdTjbbXtjcMapper;
import com.center.medical.datamove.common.bean.model.MdTjbbXtjc;
import com.center.medical.datamove.common.service.MdTjbbXtjcService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS血糖检测体检报表(MdTjbbXtjc)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:49:03
 */
@Slf4j
@Service("mdTjbbXtjcService")
@RequiredArgsConstructor
public class MdTjbbXtjcServiceImpl extends ServiceImpl<MdTjbbXtjcMapper, MdTjbbXtjc> implements MdTjbbXtjcService {

    private final MdTjbbXtjcMapper mdTjbbXtjcMapper;

    /**
     * 分页查询[KS血糖检测体检报表]列表
     *
     * @param page  分页参数
     * @param param MdTjbbXtjc查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdTjbbXtjc> getPage(PageParam<MdTjbbXtjc> page, MdTjbbXtjc param) {
        return mdTjbbXtjcMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdTjbbXtjc getInfoById(String id) {
        return mdTjbbXtjcMapper.getInfoById(id);
    }

    ;

}


