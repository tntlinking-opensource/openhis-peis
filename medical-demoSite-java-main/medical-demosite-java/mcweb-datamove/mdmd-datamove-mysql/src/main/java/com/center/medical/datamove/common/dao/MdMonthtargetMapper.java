package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdMonthtarget;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 销售月度计划(MdMonthtarget)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:26
 */
public interface MdMonthtargetMapper extends BaseMapper<MdMonthtarget> {

    /**
     * 分页查询[销售月度计划]列表
     *
     * @param page  分页参数
     * @param param MdMonthtarget查询参数
     * @return 分页数据
     */
    IPage<MdMonthtarget> getPage(PageParam<MdMonthtarget> page, @Param("param") MdMonthtarget param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdMonthtarget getInfoById(@Param("id") String id);

}
