package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdGroupBalanceMapper;
import com.center.medical.datamove.common.bean.model.MdGroupBalance;
import com.center.medical.datamove.common.service.MdGroupBalanceService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检团体结算表(MdGroupBalance)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:20
 */
@Slf4j
@Service("mdGroupBalanceService")
@RequiredArgsConstructor
public class MdGroupBalanceServiceImpl extends ServiceImpl<MdGroupBalanceMapper, MdGroupBalance> implements MdGroupBalanceService {

    private final MdGroupBalanceMapper mdGroupBalanceMapper;

    /**
     * 分页查询[体检团体结算表]列表
     *
     * @param page  分页参数
     * @param param MdGroupBalance查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdGroupBalance> getPage(PageParam<MdGroupBalance> page, MdGroupBalance param) {
        return mdGroupBalanceMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdGroupBalance getInfoById(String id) {
        return mdGroupBalanceMapper.getInfoById(id);
    }

}


