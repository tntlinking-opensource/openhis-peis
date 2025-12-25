package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdMonthtarget;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 销售月度计划(MdMonthtarget)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:26
 */
public interface MdMonthtargetService extends IService<MdMonthtarget> {

    /**
     * 分页查询[销售月度计划]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdMonthtarget> getPage(PageParam<MdMonthtarget> page, MdMonthtarget param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdMonthtarget getInfoById(String id);

}

