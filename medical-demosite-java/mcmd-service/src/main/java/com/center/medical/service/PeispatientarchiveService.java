package com.center.medical.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Peispatientarchive;
import com.center.medical.bean.param.ArchiveMergeParam;
import com.center.medical.bean.param.ArchiveParam;
import com.center.medical.bean.param.MemberParam;
import com.center.medical.bean.vo.ArchiveVo;
import com.center.medical.bean.vo.MemberVo;
import com.center.medical.common.utils.page.PageParam;

import java.util.List;

/**
 * 体检者档案表(Peispatientarchive)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-24 10:08:13
 */
public interface PeispatientarchiveService extends IService<Peispatientarchive> {

    /**
     * 分页查询[体检者档案表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MemberVo> getPage(PageParam<MemberVo> page, MemberParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatientarchive getInfoById(String id);

    /**
     * 导出会员列表数据
     *
     * @param param
     */
    List<Peispatientarchive> getList(MemberParam param);

    /**
     * 分页查询档案列表
     *
     * @param page  分页参数
     * @param param 查询条件
     * @return 所有数据
     */
    IPage<ArchiveVo> getArchivePage(PageParam<Peispatientarchive> page, ArchiveParam param);

    /**
     * 新增或者更新档案信息
     *
     * @param archive
     * @return
     */
    String saOrUp(Peispatientarchive archive);

    /**
     * 合并
     *
     * @param mergeParam
     * @return
     */
    Boolean merge(ArchiveMergeParam mergeParam);

    /**
     * 会员升级
     *
     * @param ids
     * @return
     */
    Boolean up(List<String> ids);

    /**
     * 绑定体检者档案
     *
     * @param peispatient 体检者信息
     * @param isBatch     是否批量
     * @param membercreate 创建者
     * @return 返回档案号
     */
    String bingIArchive(Peispatient peispatient, Boolean isBatch,String membercreate);

    /**
     * 生成唯一的档案号
     *
     * @return 体检号
     */
    String getArchiveCode();

    Peispatientarchive getInfoByNo(String patientarchiveno);

    /**
     * 重新绑定档案
     * @param codes
     * @return
     */
    Boolean reBingArchive(List<String> codes);
}

