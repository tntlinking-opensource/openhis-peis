package com.center.medical.member.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.member.bean.model.Memberbirthdat;
import com.center.medical.member.bean.param.BirtthdaySmsParam;
import com.center.medical.member.bean.param.MemberbirthdayParam;
import com.center.medical.member.bean.vo.MemberbirthdatVo;

import java.util.List;

/**
 * 会员生日提醒回访表(Memberbirthdat)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-24 09:59:56
 */
public interface MemberbirthdatService extends IService<Memberbirthdat> {

    /**
     * 分页查询[会员生日提醒回访表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MemberbirthdatVo> getPage(PageParam<Memberbirthdat> page, MemberbirthdayParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Memberbirthdat getInfoById(String id);

    /**
     * 根据条件查询列表
     *
     * @param param
     * @return
     */
    List<MemberbirthdatVo> getList(MemberbirthdayParam param);

    /**
     * 保存或修改
     * @param param
     * @return
     */
    String saOrUp(BirtthdaySmsParam param);

    /**
     * 取消发送
     * @param ids
     * @return
     */
    Boolean cancelSMS(List<String> ids);
}

