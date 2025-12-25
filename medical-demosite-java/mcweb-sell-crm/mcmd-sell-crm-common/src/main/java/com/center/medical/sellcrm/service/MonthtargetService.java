package com.center.medical.sellcrm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Monthtarget;
import com.center.medical.sellcrm.bean.param.MTSaOrUpParam;
import com.center.medical.sellcrm.bean.param.MonthtargetParam;
import com.center.medical.sellcrm.bean.vo.MTSummaryVo;
import com.center.medical.sellcrm.bean.vo.MonthtargetVo;

import java.util.List;

/**
 * 销售月度计划(Monthtarget)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:26
 */
public interface MonthtargetService extends IService<Monthtarget> {

    /**
     * 分页查询[销售月度计划]列表
     *
     * @param page  分页参数
     * @param param Monthtarget查询参数
     * @return 分页数据
     */
    IPage<MonthtargetVo> getList(PageParam<MonthtargetVo> page, MonthtargetParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Monthtarget getInfoById(String id);

    /**
     * 获取总结数据
     * @param param
     * @return
     */
    List<MTSummaryVo> getSummaryData(MonthtargetParam param);

    /**
     * 数据保存或编辑
     * @param param
     * @return
     */
    Boolean saOrUp(MTSaOrUpParam param);

    /**
     * 导出销售月度目标
     * @param param
     * @return
     */
    List<MonthtargetVo> getExportData(MonthtargetParam param);
}

