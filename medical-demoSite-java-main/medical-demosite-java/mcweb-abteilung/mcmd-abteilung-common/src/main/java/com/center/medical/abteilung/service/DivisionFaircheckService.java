package com.center.medical.abteilung.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.abteilung.bean.param.DFsaOrUpParam;
import com.center.medical.abteilung.bean.param.DivisionFaircheckParam;
import com.center.medical.abteilung.bean.param.GetSignParam;
import com.center.medical.abteilung.bean.vo.GetSignVo;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;

import java.util.List;

/**
 * QT体检者表(Peispatient)表服务接口
 *
 * @author 路飞船长
 * @since 2022-12-06 10:15:51
 */
public interface DivisionFaircheckService extends IService<Peispatient> {



    /**
     * 体检者表格数据
     * @param page
     * @param ksId
     * @return
     */
    IPage<Peispatient> listData(PageParam<Peispatient> page, String ksId);

    /**
     * 审核
     * @param param
     * @return
     */
    Boolean saOrUp(DFsaOrUpParam param);

    /**
     * 获取体征词
     * @param getSignParam
     * @return
     */
    GetSignVo getSign(GetSignParam getSignParam);

    /**
     * 结伦词列表数据
     * @param patientCode
     * @param ksId
     * @return
     */
    List<GetSignVo> jlcData(String patientCode, String ksId);


    /**
     * 保存结伦词
     * @param divisionFaircheckParam
     * @return
     */
    Boolean saveJlc(DivisionFaircheckParam divisionFaircheckParam);

    /**
     * 反审核
     * @param patientCode
     * @param ksId
     * @return
     */
    Boolean reverse(String patientCode, String ksId);
}

