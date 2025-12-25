package com.center.medical.statistics.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.PTTotalListParam;
import com.center.medical.statistics.bean.param.PersonalTotalParam;
import com.center.medical.statistics.bean.vo.PTTotalListVo;
import com.center.medical.statistics.bean.vo.PersonalTotalVo;

import java.util.List;

/**
 * QT体检者表(Peispatient)表服务接口
 *
 * @author ay
 * @since 2023-04-14 16:40:24
 */
public interface PersonalTotalService extends IService<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<PersonalTotalVo> getList(PageParam<PersonalTotalVo> page, PersonalTotalParam param);


    /**
     * 导出销售团检统计
     * @param param
     * @return
     */
    List<PersonalTotalVo> getExportData(PersonalTotalParam param);

    /**
     * 查询右边列表
     * @param param
     * @return
     */
    List<PTTotalListVo> getTotalList(PTTotalListParam param);
}

