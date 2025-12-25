package com.center.medical.datamove.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.Peisorgreservation;
import com.center.medical.datamove.admin.dao.OrPeisorgreservationMapper;
import com.center.medical.datamove.admin.service.OrPeisorgreservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 体检团体任务表(Peisorgreservation)服务实现类
 *
 * @author ay
 * @since 2023-08-12 11:54:58
 */
@Slf4j
@Service("orPeisorgreservationService")
@RequiredArgsConstructor
public class OrPeisorgreservationServiceImpl extends ServiceImpl<OrPeisorgreservationMapper, Peisorgreservation> implements OrPeisorgreservationService {

    private final OrPeisorgreservationMapper orPeisorgreservationMapper;

    /**
     * 分页查询[体检团体任务表]列表
     *
     * @param page  分页参数
     * @param param Peisorgreservation查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Peisorgreservation> getPage(PageParam<Peisorgreservation> page, Peisorgreservation param) {
        return orPeisorgreservationMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peisorgreservation getInfoById(String id) {
        return orPeisorgreservationMapper.getInfoById(id);
    }


    /**
     * 查询未结束的体检任务
     *
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<Peisorgreservation> getUnFinished() {
        List<Peisorgreservation> peisorgreservations = orPeisorgreservationMapper.selectList(new LambdaQueryWrapper<Peisorgreservation>()
                .ne(Peisorgreservation::getFFinished, 1)
                .last("AND ROWNUM <= 20")
        );
        return peisorgreservations;
    }

    /**
     * 通过订单号查询体检者团体任务
     * @param ddh
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public Peisorgreservation getByDdh(String ddh) {
        Peisorgreservation peisorgreservations = orPeisorgreservationMapper.selectOne(new LambdaQueryWrapper<Peisorgreservation>()
                .eq(Peisorgreservation::getDdh, ddh)
        );
        return peisorgreservations;
    }
}


