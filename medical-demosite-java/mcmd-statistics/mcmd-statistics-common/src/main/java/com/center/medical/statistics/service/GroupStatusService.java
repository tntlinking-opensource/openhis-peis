package com.center.medical.statistics.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.GroupStatusParam;
import com.center.medical.statistics.bean.vo.GroupStatusVo;

import java.util.List;

/**
 * 体检状态明细(Peispatient)表服务接口
 *
 * @author ay
 * @since 2023-04-14 16:40:24
 */
public interface GroupStatusService extends IService<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<GroupStatusVo> getList(PageParam<GroupStatusVo> page, GroupStatusParam param);


    /**
     * 导出体检者团体状态统计
     * @param param
     * @return
     */
    List<GroupStatusVo> exportData(GroupStatusParam param);
}

