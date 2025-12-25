package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdPeispatientChargeOther;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检者其他缴费(MdPeispatientChargeOther)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:11
 */
public interface MdPeispatientChargeOtherMapper extends BaseMapper<MdPeispatientChargeOther> {

    /**
     * 分页查询[体检者其他缴费]列表
     *
     * @param page  分页参数
     * @param param MdPeispatientChargeOther查询参数
     * @return 分页数据
     */
    IPage<MdPeispatientChargeOther> getPage(PageParam<MdPeispatientChargeOther> page, @Param("param") MdPeispatientChargeOther param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPeispatientChargeOther getInfoById(@Param("id") String id);

}
