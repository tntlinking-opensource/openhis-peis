package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdUserMapper;
import com.center.medical.datamove.common.bean.model.MdUser;
import com.center.medical.datamove.common.service.MdUserService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检用户账号(MdUser)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:49:16
 */
@Slf4j
@Service("mdUserService")
@RequiredArgsConstructor
public class MdUserServiceImpl extends ServiceImpl<MdUserMapper, MdUser> implements MdUserService {

    private final MdUserMapper mdUserMapper;

    /**
     * 分页查询[体检用户账号]列表
     *
     * @param page  分页参数
     * @param param MdUser查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdUser> getPage(PageParam<MdUser> page, MdUser param) {
        return mdUserMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键userId
     * @return 详情信息
     */
    @Override
    public MdUser getInfoById(String id) {
        return mdUserMapper.getInfoById(id);
    }

}


