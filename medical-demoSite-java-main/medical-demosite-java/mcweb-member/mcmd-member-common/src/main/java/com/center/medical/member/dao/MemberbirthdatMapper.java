package com.center.medical.member.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.member.bean.model.Memberbirthdat;
import com.center.medical.member.bean.param.MemberbirthdayParam;
import com.center.medical.member.bean.vo.MemberbirthdatVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 会员生日提醒回访表(Memberbirthdat)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-24 09:59:56
 */
public interface MemberbirthdatMapper extends BaseMapper<Memberbirthdat> {

    /**
     * 分页查询[会员生日提醒回访表]列表
     *
     * @param page  分页参数
     * @param param Memberbirthdat查询参数
     * @return 分页数据
     */
    IPage<MemberbirthdatVo> getPage(PageParam<Memberbirthdat> page, @Param("param") MemberbirthdayParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Memberbirthdat getInfoById(@Param("id") String id);

    /**
     * 根据条件查询列表
     *
     * @param param
     * @return
     */
    List<MemberbirthdatVo> getList(@Param("param") MemberbirthdayParam param);
}
