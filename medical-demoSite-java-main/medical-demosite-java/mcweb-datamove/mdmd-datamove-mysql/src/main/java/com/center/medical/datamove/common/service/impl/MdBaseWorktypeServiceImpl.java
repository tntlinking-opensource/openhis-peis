package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdBaseWorktypeMapper;
import com.center.medical.datamove.common.bean.model.MdBaseWorktype;
import com.center.medical.datamove.common.service.MdBaseWorktypeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 工种(MdBaseWorktype)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:10
 */
@Slf4j
@Service("mdBaseWorktypeService")
@RequiredArgsConstructor
public class MdBaseWorktypeServiceImpl extends ServiceImpl<MdBaseWorktypeMapper, MdBaseWorktype> implements MdBaseWorktypeService {

    private final MdBaseWorktypeMapper mdBaseWorktypeMapper;

    /**
     * 分页查询[工种]列表
     *
     * @param page  分页参数
     * @param param MdBaseWorktype查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdBaseWorktype> getPage(PageParam<MdBaseWorktype> page, MdBaseWorktype param) {
        return mdBaseWorktypeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdBaseWorktype getInfoById(String id) {
        return mdBaseWorktypeMapper.getInfoById(id);
    }

}


