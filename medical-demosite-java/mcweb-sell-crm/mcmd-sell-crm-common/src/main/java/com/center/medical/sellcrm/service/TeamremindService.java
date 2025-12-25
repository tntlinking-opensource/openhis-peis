package com.center.medical.sellcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Teamremind;
import com.center.medical.sellcrm.bean.param.DataExceptionPeiParam;
import com.center.medical.sellcrm.bean.param.SaveKhgtParam;
import com.center.medical.sellcrm.bean.param.TeamremindParam;
import com.center.medical.sellcrm.bean.vo.DataExceptionPeiVo;

import java.util.List;

/**
 * 客户预检跟踪表(Teamremind)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-22 14:52:38
 */
public interface TeamremindService extends IService<Teamremind> {

    /**
     * 分页查询[客户预检跟踪表]列表
     *
     * @param page            分页参数
     * @param teamremindParam 查询参数
     * @return 分页数据
     */
    IPage<Teamremind> getPage(PageParam<Teamremind> page, TeamremindParam teamremindParam);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Teamremind getInfoById(String id);

    /**
     * 保存客户沟通记录
     *
     * @param param
     * @return
     */
    Boolean saveKhgtData(SaveKhgtParam param);

    /**
     * 获取主页数据
     *
     * @param
     * @param
     * @return
     */
    List<Teamremind> getHomeData(TeamremindParam teamremindParam);


    /**
     * 获取数据异常体检者数据
     * @param page
     * @param param
     * @return
     */
    IPage<DataExceptionPeiVo> getDataExceptionPeiPage(PageParam<DataExceptionPeiVo> page, DataExceptionPeiParam param);
}

