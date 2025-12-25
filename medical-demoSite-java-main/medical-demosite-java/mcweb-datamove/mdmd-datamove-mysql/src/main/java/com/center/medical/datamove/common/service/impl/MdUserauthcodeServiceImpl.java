package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdUserauthcodeMapper;
import com.center.medical.datamove.common.bean.model.MdUserauthcode;
import com.center.medical.datamove.common.service.MdUserauthcodeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户授权码(MdUserauthcode)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:49:27
 */
@Slf4j
@Service("mdUserauthcodeService")
@RequiredArgsConstructor
public class MdUserauthcodeServiceImpl extends ServiceImpl<MdUserauthcodeMapper, MdUserauthcode> implements MdUserauthcodeService {

    private final MdUserauthcodeMapper mdUserauthcodeMapper;

    /**
     * 分页查询[用户授权码]列表
     *
     * @param page  分页参数
     * @param param MdUserauthcode查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdUserauthcode> getPage(PageParam<MdUserauthcode> page, MdUserauthcode param) {
        return mdUserauthcodeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdUserauthcode getInfoById(String id) {
        return mdUserauthcodeMapper.getInfoById(id);
    }

}


