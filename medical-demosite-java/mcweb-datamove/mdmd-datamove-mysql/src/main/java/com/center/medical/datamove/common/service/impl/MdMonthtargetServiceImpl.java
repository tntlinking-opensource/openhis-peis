package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdMonthtargetMapper;
import com.center.medical.datamove.common.bean.model.MdMonthtarget;
import com.center.medical.datamove.common.service.MdMonthtargetService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 销售月度计划(MdMonthtarget)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:26
 */
@Slf4j
@Service("mdMonthtargetService")
@RequiredArgsConstructor
public class MdMonthtargetServiceImpl extends ServiceImpl<MdMonthtargetMapper, MdMonthtarget> implements MdMonthtargetService {

    private final MdMonthtargetMapper mdMonthtargetMapper;

    /**
     * 分页查询[销售月度计划]列表
     *
     * @param page  分页参数
     * @param param MdMonthtarget查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdMonthtarget> getPage(PageParam<MdMonthtarget> page, MdMonthtarget param) {
        return mdMonthtargetMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdMonthtarget getInfoById(String id) {
        return mdMonthtargetMapper.getInfoById(id);
    }

}


