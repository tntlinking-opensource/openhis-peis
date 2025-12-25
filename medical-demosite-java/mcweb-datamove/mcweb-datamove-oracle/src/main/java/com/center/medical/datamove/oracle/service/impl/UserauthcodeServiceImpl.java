package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.UserauthcodeMapper;
import com.center.medical.datamove.oracle.bean.model.Userauthcode;
import com.center.medical.datamove.oracle.service.UserauthcodeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (Userauthcode)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:25:47
 */
@Slf4j
@Service("userauthcodeService")
@RequiredArgsConstructor
public class UserauthcodeServiceImpl extends ServiceImpl<UserauthcodeMapper, Userauthcode> implements UserauthcodeService {

    private final UserauthcodeMapper userauthcodeMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param Userauthcode查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Userauthcode> getPage(PageParam<Userauthcode> page, Userauthcode param) {
        return userauthcodeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Userauthcode getInfoById(String id) {
        return userauthcodeMapper.getInfoById(id);
    }

    ;

}


