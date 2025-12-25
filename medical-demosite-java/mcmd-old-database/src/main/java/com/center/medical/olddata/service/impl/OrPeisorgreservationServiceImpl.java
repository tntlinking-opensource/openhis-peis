package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrPeisorgreservation;
import com.center.medical.olddata.dao.OrPeisorgreservationMapper;
import com.center.medical.olddata.service.OrPeisorgreservationService;
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
public class OrPeisorgreservationServiceImpl extends ServiceImpl<OrPeisorgreservationMapper, OrPeisorgreservation> implements OrPeisorgreservationService {

    private final OrPeisorgreservationMapper peisorgreservationMapper;

    /**
     * 分页查询[体检团体任务表]列表
     *
     * @param page  分页参数
     * @param param Peisorgreservation查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OrPeisorgreservation> getPage(PageParam<OrPeisorgreservation> page, OrPeisorgreservation param) {
        return peisorgreservationMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public OrPeisorgreservation getInfoById(String id) {
        return peisorgreservationMapper.getInfoById(id);
    }


    /**
     * 查询未结束的体检任务
     *
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<OrPeisorgreservation> getUnFinished() {
        List<OrPeisorgreservation> peisorgreservations = peisorgreservationMapper.selectList(new LambdaQueryWrapper<OrPeisorgreservation>()
                .ne(OrPeisorgreservation::getFFinished, 1)
                .last("AND ROWNUM <= 20")
        );
        return peisorgreservations;
    }


    /**
     * 通过订单号查询
     * @param ddh
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public OrPeisorgreservation getByDdh(String ddh) {
        return  peisorgreservationMapper.selectOne(new LambdaQueryWrapper<OrPeisorgreservation>()
                .eq(OrPeisorgreservation::getDdh,ddh)
        );
    }
}


