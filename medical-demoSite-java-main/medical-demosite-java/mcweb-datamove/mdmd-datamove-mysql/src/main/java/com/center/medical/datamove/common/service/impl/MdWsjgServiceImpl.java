package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdWsjgMapper;
import com.center.medical.datamove.common.bean.model.MdWsjg;
import com.center.medical.datamove.common.service.MdWsjgService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 外送机构(MdWsjg)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:22
 */
@Slf4j
@Service("mdWsjgService")
@RequiredArgsConstructor
public class MdWsjgServiceImpl extends ServiceImpl<MdWsjgMapper, MdWsjg> implements MdWsjgService {

    private final MdWsjgMapper mdWsjgMapper;

    /**
     * 分页查询[外送机构]列表
     *
     * @param page  分页参数
     * @param param MdWsjg查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdWsjg> getPage(PageParam<MdWsjg> page, MdWsjg param) {
        return mdWsjgMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdWsjg getInfoById(String id) {
        return mdWsjgMapper.getInfoById(id);
    }

}


