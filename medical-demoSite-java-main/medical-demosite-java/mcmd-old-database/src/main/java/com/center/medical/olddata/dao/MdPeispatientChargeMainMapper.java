package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdPeispatientChargeMain;
import org.apache.ibatis.annotations.Param;

/**
 * 体检者费用主表(MdPeispatientChargeMain)数据库访问层
 *
 * @author ay
 * @since 2023-08-12 11:53:33
 */
public interface MdPeispatientChargeMainMapper extends BaseMapper<MdPeispatientChargeMain> {

    /**
     * 分页查询[体检者费用主表]列表
     *
     * @param page  分页参数
     * @param param MdPeispatientChargeMain查询参数
     * @return 分页数据
     */
    IPage<MdPeispatientChargeMain> getPage(PageParam<MdPeispatientChargeMain> page, @Param("param") MdPeispatientChargeMain param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPeispatientChargeMain getInfoById(@Param("id") String id);

}
