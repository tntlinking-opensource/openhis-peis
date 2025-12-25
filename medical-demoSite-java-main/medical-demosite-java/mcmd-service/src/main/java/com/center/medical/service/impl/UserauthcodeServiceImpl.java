package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.UserauthcodeMapper;
import com.center.medical.bean.model.Userauthcode;
import com.center.medical.service.UserauthcodeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户授权码(Userauthcode)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:18
 */
@Slf4j
@Service("userauthcodeService")
@RequiredArgsConstructor
public class UserauthcodeServiceImpl extends ServiceImpl<UserauthcodeMapper, Userauthcode> implements UserauthcodeService {

    private final UserauthcodeMapper userauthcodeMapper;

    /**
     * 分页查询[用户授权码]列表
     *
     * @param page  分页参数
     * @param param Userauthcode查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Userauthcode> getList(PageParam<Userauthcode> page, Userauthcode param) {
        return userauthcodeMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Userauthcode getInfoById(String id) {
        return userauthcodeMapper.getInfoById(id);
    }

}

