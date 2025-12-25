package com.center.medical.center.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.center.common.bean.dto.PickupBrDto;
import com.center.medical.center.common.bean.model.PacsResultMiddel;
import com.center.medical.pacslis.bean.dto.LisDto;
import com.center.medical.pacslis.bean.dto.MiddleDbDto;
import com.center.medical.pacslis.bean.dto.PacsDto;

import java.util.List;

/**
 * @author xhp
 * @since 2023-03-02 9:50
 */
public interface LisPacsService extends IService<Peispatient> {
    /**
     * 插入中间库
     * @param middleDbDto
     */
    void save(MiddleDbDto middleDbDto);


    /**
     * 查询lis结果
     * @param patientcode
     * @return
     */
    List<LisDto> selectList(String patientcode,String loginid,String password,String lisConfigStr);

    /**
     * 查询pacs结果
     */
    List<PacsDto> selectPacsList(String patientcode,String pacsConfigStr);

    /**
     * 抓取中间库职业的体检号
     * @return
     */
    List<PickupBrDto> pickupBr();

    /**
     * 设置是否核收标志
     * @param patientcodes
     * @return
     */
    Boolean setFTransfered(List<String> patientcodes);

    /**
     * 保存pacs结果
     * @param pacsResult
     * @return
     */
    Boolean saveResult(List<PacsResultMiddel> pacsResult);
}
