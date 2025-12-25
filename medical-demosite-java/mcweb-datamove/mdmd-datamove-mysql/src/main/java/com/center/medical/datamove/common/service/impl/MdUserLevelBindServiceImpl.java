package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdUserLevelBindMapper;
import com.center.medical.datamove.common.bean.model.MdUserLevelBind;
import com.center.medical.datamove.common.service.MdUserLevelBindService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 会员当前的等级(MdUserLevelBind)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:49:20
 */
@Slf4j
@Service("mdUserLevelBindService")
@RequiredArgsConstructor
public class MdUserLevelBindServiceImpl extends ServiceImpl<MdUserLevelBindMapper, MdUserLevelBind> implements MdUserLevelBindService {

    private final MdUserLevelBindMapper mdUserLevelBindMapper;

    /**
     * 分页查询[会员当前的等级]列表
     *
     * @param page  分页参数
     * @param param MdUserLevelBind查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdUserLevelBind> getPage(PageParam<MdUserLevelBind> page, MdUserLevelBind param) {
        return mdUserLevelBindMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键userLevelId
     * @return 详情信息
     */
    @Override
    public MdUserLevelBind getInfoById(Long id) {
        return mdUserLevelBindMapper.getInfoById(id);
    }

}


