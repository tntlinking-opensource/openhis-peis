package com.center.medical.sellcrm.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.SellRemind;
import com.center.medical.sellcrm.bean.vo.SellRemindVo;
import com.center.medical.sellcrm.dao.SellRemindMapper;
import com.center.medical.sellcrm.service.SellRemindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 销售提醒(SellRemind)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-22 11:51:26
 */
@Slf4j
@Service("sellRemindService")
@RequiredArgsConstructor
public class SellRemindServiceImpl extends ServiceImpl<SellRemindMapper, SellRemind> implements SellRemindService {

    private final SellRemindMapper sellRemindMapper;

    /**
     * 分页查询[销售提醒]列表
     *
     * @param page     分页参数
     * @param username SellRemind查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SellRemindVo> getPage(PageParam<SellRemindVo> page, String username) {
        return sellRemindMapper.getPage(page, username);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SellRemind getInfoById(String id) {
        return sellRemindMapper.getInfoById(id);
    }

}

