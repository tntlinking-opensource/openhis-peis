package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.InterfaceAccountMapper;
import com.center.medical.bean.model.InterfaceAccount;
import com.center.medical.service.InterfaceAccountService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 各种接口加密信息(InterfaceAccount)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:02
 */
@Slf4j
@Service("interfaceAccountService")
@RequiredArgsConstructor
public class InterfaceAccountServiceImpl extends ServiceImpl<InterfaceAccountMapper, InterfaceAccount> implements InterfaceAccountService {

    private final InterfaceAccountMapper interfaceAccountMapper;

    /**
     * 分页查询[各种接口加密信息]列表
     *
     * @param page  分页参数
     * @param param InterfaceAccount查询参数
     * @return 分页数据
     */
    @Override
    public IPage<InterfaceAccount> getList(PageParam<InterfaceAccount> page, InterfaceAccount param) {
        return interfaceAccountMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public InterfaceAccount getInfoById(String id) {
        return interfaceAccountMapper.getInfoById(id);
    }

}

