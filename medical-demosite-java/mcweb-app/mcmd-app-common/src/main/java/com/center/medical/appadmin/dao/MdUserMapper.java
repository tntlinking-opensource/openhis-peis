package com.center.medical.appadmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.appadmin.bean.dto.GetLastTimeDto;
import com.center.medical.appadmin.bean.model.MdUser;
import com.center.medical.appadmin.bean.param.AppUserParam;
import com.center.medical.appadmin.bean.vo.AppUserVo;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表(MdUser)数据库访问层
 *
 * @author ay
 * @since 2024-07-23 17:03:25
 */
public interface MdUserMapper extends BaseMapper<MdUser> {

    /**
     * 分页查询[用户表]列表
     *
     * @param page  分页参数
     * @param param MdUser查询参数
     * @return 分页数据
     */
    @DataSource(value = DataSourceType.APPLET)
    IPage<AppUserVo> getPage(PageParam<AppUserVo> page, @Param("param") AppUserParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键userId
     * @return 详情信息
     */
    MdUser getInfoById(@Param("id") String id);

    /**
     * 获取手机号对应的最后体检日期
     * @param phones
     * @return
     */
    @DataSource(value = DataSourceType.MASTER)
    List<GetLastTimeDto> getLastTime(@Param("phones") List<String> phones);
}
