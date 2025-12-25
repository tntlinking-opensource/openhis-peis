package com.center.medical.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.dto.SysVersionItemDto;
import com.center.medical.system.bean.model.SysVersionItem;
import com.center.medical.system.bean.param.SysVersionItemParam;
import com.center.medical.system.dao.SysVersionItemMapper;
import com.center.medical.system.service.SysVersionItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统更新记录(SysVersionItem)服务实现类
 *
 * @author makejava
 * @since 2024-03-01 18:02:37
 */
@Slf4j
@Service("sysVersionItemService")
@RequiredArgsConstructor
public class SysVersionItemServiceImpl extends ServiceImpl<SysVersionItemMapper, SysVersionItem> implements SysVersionItemService {

    private final SysVersionItemMapper sysVersionItemMapper;

    /**
     * 分页查询[系统更新记录]列表
     *
     * @param page  分页参数
     * @param param SysVersionItem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysVersionItem> getPage(PageParam<SysVersionItem> page, SysVersionItemParam param) {
        return sysVersionItemMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键logId
     * @return 详情信息
     */
    @Override
    public SysVersionItem getInfoById(Integer id) {
        return sysVersionItemMapper.getInfoById(id);
    }

    @Override
    public boolean importItem(List<SysVersionItemDto> list, String operName) {
        return false;
    }

}

