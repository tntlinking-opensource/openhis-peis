package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdWzZybsMapper;
import com.center.medical.datamove.common.bean.model.MdWzZybs;
import com.center.medical.datamove.common.service.MdWzZybsService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS问诊——职业病史(MdWzZybs)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:26
 */
@Slf4j
@Service("mdWzZybsService")
@RequiredArgsConstructor
public class MdWzZybsServiceImpl extends ServiceImpl<MdWzZybsMapper, MdWzZybs> implements MdWzZybsService {

    private final MdWzZybsMapper mdWzZybsMapper;

    /**
     * 分页查询[KS问诊——职业病史]列表
     *
     * @param page  分页参数
     * @param param MdWzZybs查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdWzZybs> getPage(PageParam<MdWzZybs> page, MdWzZybs param) {
        return mdWzZybsMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdWzZybs getInfoById(String id) {
        return mdWzZybsMapper.getInfoById(id);
    }

}


