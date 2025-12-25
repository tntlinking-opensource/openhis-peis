package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdInterfaceAccountMapper;
import com.center.medical.datamove.common.bean.model.MdInterfaceAccount;
import com.center.medical.datamove.common.service.MdInterfaceAccountService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 各种接口加密信息(MdInterfaceAccount)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:23
 */
@Slf4j
@Service("mdInterfaceAccountService")
@RequiredArgsConstructor
public class MdInterfaceAccountServiceImpl extends ServiceImpl<MdInterfaceAccountMapper, MdInterfaceAccount> implements MdInterfaceAccountService {

    private final MdInterfaceAccountMapper mdInterfaceAccountMapper;

    /**
     * 分页查询[各种接口加密信息]列表
     *
     * @param page  分页参数
     * @param param MdInterfaceAccount查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdInterfaceAccount> getPage(PageParam<MdInterfaceAccount> page, MdInterfaceAccount param) {
        return mdInterfaceAccountMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdInterfaceAccount getInfoById(String id) {
        return mdInterfaceAccountMapper.getInfoById(id);
    }

}


