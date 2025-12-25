package com.center.medical.olddata.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.VCheckReqmainMysql;
import com.center.medical.olddata.dao.VCheckReqmainMysqlMapper;
import com.center.medical.olddata.service.VCheckReqmainMysqlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (VCheckReqmainMysql)服务实现类
 *
 * @author ay
 * @since 2024-08-09 15:46:19
 */
@Slf4j
@Service("vCheckReqmainMysqlService")
@RequiredArgsConstructor
public class VCheckReqmainMysqlServiceImpl extends ServiceImpl<VCheckReqmainMysqlMapper, VCheckReqmainMysql> implements VCheckReqmainMysqlService {

    private final VCheckReqmainMysqlMapper vCheckReqmainMysqlMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param VCheckReqmainMysql查询参数
     * @return 分页数据
     */
    @Override
    public IPage<VCheckReqmainMysql> getPage(PageParam<VCheckReqmainMysql> page, VCheckReqmainMysql param) {
        return vCheckReqmainMysqlMapper.getPage(page, param);
    }

    /**
     * 获取视图数据
     * @param patientcode
     * @return
     */
    @Override
    public List<VCheckReqmainMysql> getCheckReqmain(String patientcode) {
        return vCheckReqmainMysqlMapper.getCheckReqmain(patientcode);
    }

    /**
     * 删除体检号对应的数据
     * @param patientcode
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public void deleteByCode(String patientcode) {
        vCheckReqmainMysqlMapper.delete(new LambdaQueryWrapper<VCheckReqmainMysql>()
                .eq(VCheckReqmainMysql::getCheckregno,patientcode));
    }

    /**
     * 批量保存
     * @param checkReqmainMysqlList
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public void saveList(List<VCheckReqmainMysql> checkReqmainMysqlList) {
        saveBatch(checkReqmainMysqlList);
    }

    /**
     * 查询过期数据
     * @param dateTime
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public Integer deleteTimeOutList(DateTime dateTime) {
        return vCheckReqmainMysqlMapper.deleteTimeOutList(dateTime);
    }
}

