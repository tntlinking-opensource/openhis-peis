package com.center.medical.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.SqlOptionLog;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.SqlOptionLogMapper;
import com.center.medical.service.SqlOptionLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * SQL操作日志表(SqlOptionLog)表服务实现类
 *
 * @author 路飞船长
 * @since 2023-06-21 14:33:21
 */
@Slf4j
@Service("sqlOptionLogService")
@RequiredArgsConstructor
public class SqlOptionLogServiceImpl extends ServiceImpl<SqlOptionLogMapper, SqlOptionLog> implements SqlOptionLogService {

    private final SqlOptionLogMapper sqlOptionLogMapper;

    /**
     * 分页查询[SQL操作日志表]列表
     *
     * @param page  分页参数
     * @param param SqlOptionLog查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SqlOptionLog> getPage(PageParam<SqlOptionLog> page, SqlOptionLog param) {
        return sqlOptionLogMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SqlOptionLog getInfoById(Long id) {
        return sqlOptionLogMapper.getInfoById(id);
    }

}

