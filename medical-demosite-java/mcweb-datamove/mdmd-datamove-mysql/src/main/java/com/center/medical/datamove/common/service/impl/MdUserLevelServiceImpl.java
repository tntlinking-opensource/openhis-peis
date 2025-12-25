package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdUserLevelMapper;
import com.center.medical.datamove.common.bean.model.MdUserLevel;
import com.center.medical.datamove.common.service.MdUserLevelService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 会员等级表(MdUserLevel)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:49:19
 */
@Slf4j
@Service("mdUserLevelService")
@RequiredArgsConstructor
public class MdUserLevelServiceImpl extends ServiceImpl<MdUserLevelMapper, MdUserLevel> implements MdUserLevelService {

    private final MdUserLevelMapper mdUserLevelMapper;

    /**
     * 分页查询[会员等级表]列表
     *
     * @param page  分页参数
     * @param param MdUserLevel查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdUserLevel> getPage(PageParam<MdUserLevel> page, MdUserLevel param) {
        return mdUserLevelMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键levelId
     * @return 详情信息
     */
    @Override
    public MdUserLevel getInfoById(Integer id) {
        return mdUserLevelMapper.getInfoById(id);
    }

}


