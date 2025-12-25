package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.MonthtargetMapper;
import com.center.medical.datamove.oracle.bean.model.Monthtarget;
import com.center.medical.datamove.oracle.service.MonthtargetService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 销售月度计划(Monthtarget)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:08
 */
@Slf4j
@Service("monthtargetService")
@RequiredArgsConstructor
public class MonthtargetServiceImpl extends ServiceImpl<MonthtargetMapper, Monthtarget> implements MonthtargetService {

    private final MonthtargetMapper monthtargetMapper;

    /**
     * 分页查询[销售月度计划]列表
     *
     * @param page  分页参数
     * @param param Monthtarget查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Monthtarget> getPage(PageParam<Monthtarget> page, Monthtarget param) {
        return monthtargetMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Monthtarget getInfoById(String id) {
        return monthtargetMapper.getInfoById(id);
    }

}


