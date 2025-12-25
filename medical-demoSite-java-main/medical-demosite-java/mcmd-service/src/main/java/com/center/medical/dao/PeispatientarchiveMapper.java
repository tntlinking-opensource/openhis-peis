package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatientarchive;
import com.center.medical.bean.param.ArchiveParam;
import com.center.medical.bean.param.MemberParam;
import com.center.medical.bean.vo.ArchiveVo;
import com.center.medical.bean.vo.MemberVo;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 体检者档案表(Peispatientarchive)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-24 10:08:11
 */
public interface PeispatientarchiveMapper extends BaseMapper<Peispatientarchive> {

    /**
     * 分页查询[体检者档案表]列表
     *
     * @param page  分页参数
     * @param param Peispatientarchive查询参数
     * @return 分页数据
     */
    IPage<MemberVo> getPage(PageParam<MemberVo> page, @Param("param") MemberParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatientarchive getInfoById(@Param("id") String id);

    /**
     * 根据条件筛选会员列表数据
     *
     * @param param
     */
    List<Peispatientarchive> getList(@Param("param") MemberParam param);

    /**
     * 分页查询档案列表
     *
     * @param page  分页参数
     * @param param 查询条件
     * @return 所有数据
     */
    IPage<ArchiveVo> getArchivePage(PageParam<Peispatientarchive> page, @Param("param") ArchiveParam param);

    Peispatientarchive getInfoByNo(@Param("no") String patientarchiveno);

    /**
     * 通过体检号查询档案id
     * @param patientcode
     * @return
     */
    String getIdByPatientCode(@Param("patientcode") String patientcode);

    /**
     * 老系统通过身份证查询档案id
     * @param idcardno
     * @return
     */
    @DataSource(value = DataSourceType.SLAVE)
    List<String> getOldInfoByIdCardNo(@Param("idcardno") String idcardno);
}
