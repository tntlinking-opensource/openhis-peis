package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.FxPersonnelview;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 表1 疑似职业病人员一览表
 * 表2  职业禁忌证人员一览表
 * 表3职业病危害效应相关指标异常需复查人员一览表
 * 表4 其他疾病异常结果人员一览表
 * 表5 本次职业健康检查未见异常人员一览表(FxPersonnelview)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:18:55
 */
public interface FxPersonnelviewMapper extends BaseMapper<FxPersonnelview> {

    /**
     * 分页查询[表1 疑似职业病人员一览表
     * 表2  职业禁忌证人员一览表
     * 表3职业病危害效应相关指标异常需复查人员一览表
     * 表4 其他疾病异常结果人员一览表
     * 表5 本次职业健康检查未见异常人员一览表]列表
     *
     * @param page  分页参数
     * @param param FxPersonnelview查询参数
     * @return 分页数据
     */
    IPage<FxPersonnelview> getPage(PageParam<FxPersonnelview> page, @Param("param") FxPersonnelview param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    FxPersonnelview getInfoById(@Param("id") String id);

}
