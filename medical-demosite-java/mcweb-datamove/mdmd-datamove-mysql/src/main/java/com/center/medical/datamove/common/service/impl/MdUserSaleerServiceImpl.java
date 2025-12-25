package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdUserSaleerMapper;
import com.center.medical.datamove.common.bean.model.MdUserSaleer;
import com.center.medical.datamove.common.service.MdUserSaleerService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户-销售关联表 (MdUserSaleer)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:49:25
 */
@Slf4j
@Service("mdUserSaleerService")
@RequiredArgsConstructor
public class MdUserSaleerServiceImpl extends ServiceImpl<MdUserSaleerMapper, MdUserSaleer> implements MdUserSaleerService {

    private final MdUserSaleerMapper mdUserSaleerMapper;

    /**
     * 分页查询[用户-销售关联表 ]列表
     *
     * @param page  分页参数
     * @param param MdUserSaleer查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdUserSaleer> getPage(PageParam<MdUserSaleer> page, MdUserSaleer param) {
        return mdUserSaleerMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdUserSaleer getInfoById(String id) {
        return mdUserSaleerMapper.getInfoById(id);
    }

}


