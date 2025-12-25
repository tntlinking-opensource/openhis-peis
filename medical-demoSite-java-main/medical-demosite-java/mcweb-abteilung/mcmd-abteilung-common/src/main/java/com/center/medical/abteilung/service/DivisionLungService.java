package com.center.medical.abteilung.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.abteilung.bean.param.DLungParam;
import com.center.medical.abteilung.bean.param.DivisionLungParam;
import com.center.medical.abteilung.bean.param.lungSaveJlcParam;
import com.center.medical.bean.model.Lung;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

/**
 * KS肺功能(Lung)表服务接口
 *
 * @author ay
 * @since 2023-04-20 18:01:22
 */
public interface DivisionLungService extends IService<Lung> {


    /**
     * 肺功能读卡
     * @param param
     * @return
     */
    HashMap<String,Object> search(DivisionLungParam param);

    /**
     * 结伦词列表数据
     * @param patientCode
     * @param ksId
     * @return
     */
    List<HashMap<String,String>> jlcData(String patientCode, String ksId);

    /**
     * 获取体征词
     * @param key
     * @return
     */
    HashMap<String, Object> getSign(String key);

    /**
     * 保存结伦词
     * @param param
     * @return
     */
    Boolean saveJlc(lungSaveJlcParam param);

    /**
     * 审核
     * @param param
     * @return
     */
    Boolean saOrUp(DLungParam param);

    /**
     * 反审核
     * @param patientCode
     * @param ksId
     * @return
     */
    Boolean reverse(String patientCode, String ksId);

    /**
     * 肺功能即时上传
     * @param data
     * @return
     */
    Lung uploadLungIm(String data);

    /**
     * 淮南肺功能即时上传
     * @param data
     * @return
     */
    Lung uploadHnLung(String data) throws UnsupportedEncodingException;
}

