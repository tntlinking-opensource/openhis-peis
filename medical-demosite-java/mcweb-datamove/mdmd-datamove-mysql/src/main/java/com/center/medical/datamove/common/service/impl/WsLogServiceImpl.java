package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.WsLogMapper;
import com.center.medical.datamove.common.bean.model.WsLog;
import com.center.medical.datamove.common.service.WsLogService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 网站日志(WsLog)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:41
 */
@Slf4j
@Service("wsLogService")
@RequiredArgsConstructor
public class WsLogServiceImpl extends ServiceImpl<WsLogMapper, WsLog> implements WsLogService {

    private final WsLogMapper wsLogMapper;

    /**
     * 分页查询[网站日志]列表
     *
     * @param page  分页参数
     * @param param WsLog查询参数
     * @return 分页数据
     */
    @Override
    public IPage<WsLog> getPage(PageParam<WsLog> page, WsLog param) {
        return wsLogMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public WsLog getInfoById(String id) {
        return wsLogMapper.getInfoById(id);
    }

}


