package com.center.medical.reception.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reception.bean.model.OutsideMain;
import com.center.medical.reception.bean.param.RecordMatchParam;
import com.center.medical.reception.bean.param.SendGovernParam;
import com.center.medical.reception.bean.param.SgSaOrUpParam;
import com.center.medical.reception.bean.vo.SendGovernVo;

import java.util.HashMap;
import java.util.List;

/**
 * KS外送登记结果主表(OutsideMain)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:28
 */
public interface OutsideMainService extends IService<OutsideMain> {

    /**
     * 分页查询[KS外送登记结果主表]列表
     *
     * @param page  分页参数
     * @param param OutsideMain查询参数
     * @return 分页数据
     */
    IPage<SendGovernVo> getPage(PageParam<OutsideMain> page, SendGovernParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    OutsideMain getInfoById(String id);

    /**
     * 新增外送登记结果处理-查询
     *
     * @param param
     * @return
     */
    HashMap<String, Object> recordMatch(RecordMatchParam param);

    /**
     * 保存
     *
     * @param sgSaOrUpParam
     * @return
     */
    Boolean saOrUp(SgSaOrUpParam sgSaOrUpParam);

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    Boolean delete(List<String> ids);


    /**
     * 功能科室的收费项目是否全部已检
     *
     * @param inputCode
     * @return
     */
    boolean getAllSfxmtzjStatus(String inputCode);

    /**
     * 外送登记信息-查询
     * @param page
     * @param param
     * @return
     */
    IPage<SendGovernVo> getMainListData(PageParam<OutsideMain> page, SendGovernParam param);
}

