package com.center.medical.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Financeinput;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.param.FIPageParam;
import com.center.medical.finance.bean.vo.FIPageVo;

/**
 * QT体检者表(Peispatient)表服务接口
 *
 * @author ay
 * @since 2023-04-06 16:22:37
 */
public interface FinanceInputService extends IService<Financeinput> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<FIPageVo> getList(PageParam<FIPageVo> page, FIPageParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Financeinput getInfoById(String id);

    /**
     * 是否允许编辑
     *
     * @param euserId
     * @param eyear
     * @return
     */
    String isEdit(String euserId, String eyear);

    /**
     * 是否允许查看
     *
     * @param viewUserId
     * @param viewYear
     * @return
     */
    String isView(String viewUserId, String viewYear);

    /**
     * 财务录入-是否已填写
     *
     * @param fuserId
     * @param fyear
     * @return
     */
    String isFinanceInput(String fuserId, String fyear);

    /**
     * 数据保存或修改
     *
     * @param financeinput
     * @return
     */
    Boolean saOrUp(Financeinput financeinput);
}

