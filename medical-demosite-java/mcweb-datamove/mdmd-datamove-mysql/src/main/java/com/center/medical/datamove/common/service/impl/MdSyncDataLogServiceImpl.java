package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdSyncDataLogMapper;
import com.center.medical.datamove.common.bean.model.MdSyncDataLog;
import com.center.medical.datamove.common.service.MdSyncDataLogService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 同步数据操作记录(MdSyncDataLog)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:51
 */
@Slf4j
@Service("mdSyncDataLogService")
@RequiredArgsConstructor
public class MdSyncDataLogServiceImpl extends ServiceImpl<MdSyncDataLogMapper, MdSyncDataLog> implements MdSyncDataLogService {

    private final MdSyncDataLogMapper mdSyncDataLogMapper;

    /**
     * 分页查询[同步数据操作记录]列表
     *
     * @param page  分页参数
     * @param param MdSyncDataLog查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdSyncDataLog> getPage(PageParam<MdSyncDataLog> page, MdSyncDataLog param) {
        return mdSyncDataLogMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键logId
     * @return 详情信息
     */
    @Override
    public MdSyncDataLog getInfoById(Long id) {
        return mdSyncDataLogMapper.getInfoById(id);
    }

}


