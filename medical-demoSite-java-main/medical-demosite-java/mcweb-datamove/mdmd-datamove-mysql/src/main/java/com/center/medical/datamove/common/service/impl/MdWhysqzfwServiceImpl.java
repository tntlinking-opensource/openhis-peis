package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdWhysqzfwMapper;
import com.center.medical.datamove.common.bean.model.MdWhysqzfw;
import com.center.medical.datamove.common.service.MdWhysqzfwService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC危害因素取值范围(MdWhysqzfw)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:49:33
 */
@Slf4j
@Service("mdWhysqzfwService")
@RequiredArgsConstructor
public class MdWhysqzfwServiceImpl extends ServiceImpl<MdWhysqzfwMapper, MdWhysqzfw> implements MdWhysqzfwService {

    private final MdWhysqzfwMapper mdWhysqzfwMapper;

    /**
     * 分页查询[JC危害因素取值范围]列表
     *
     * @param page  分页参数
     * @param param MdWhysqzfw查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdWhysqzfw> getPage(PageParam<MdWhysqzfw> page, MdWhysqzfw param) {
        return mdWhysqzfwMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdWhysqzfw getInfoById(String id) {
        return mdWhysqzfwMapper.getInfoById(id);
    }

}


