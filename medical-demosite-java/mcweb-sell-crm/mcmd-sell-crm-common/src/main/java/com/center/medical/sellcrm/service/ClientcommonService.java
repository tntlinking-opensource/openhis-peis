package com.center.medical.sellcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Clientcommon;
import com.center.medical.sellcrm.bean.param.ClientcommonParam;

import java.util.List;

/**
 * 客户公共池表(Clientcommon)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:27
 */
public interface ClientcommonService extends IService<Clientcommon> {

    /**
     * 分页查询[客户公共池表]列表
     *
     * @param page  分页参数
     * @param param Clientcommon查询参数
     * @return 分页数据
     */
    IPage<Clientcommon> getPage(PageParam<Clientcommon> page, ClientcommonParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Clientcommon getInfoById(String id);

    /**
     * 获取当前登录人分中心下的客户公共池数据
     *
     * @param page
     * @param xsjlId
     * @return
     */
    IPage<Clientcommon> getListData(PageParam<Clientcommon> page, Long xsjlId);

    /**
     * 客户领取
     *
     * @param ids 客户id列表
     * @return
     */
    String receive(List<String> ids);

    /**
     * 领导释放
     *
     * @param clientId 公共池记录ID
     * @param xsIds    销售ID列表
     * @return
     */
    String release(String clientId, List<String> xsIds);

    /**
     * 判断客户公共池中的客户单位名称是否重复：返回true则表示重复，false则不重复
     *
     * @param clientName
     * @return
     */
    Boolean onBlur(String clientName);

    /**
     * 判断是否存在领取次数为3次的记录或被领导强制分配过的记录：返回true则表示存在，false则不存在
     *
     * @param clientIds 客户id集合，多个以英文逗号（,）隔开
     * @return
     */
    R isLqAndFp(List<String> clientIds);

    /**
     * 客户公共池导入
     *
     * @param clientcommonList
     * @param operName
     * @return
     */
    String importUser(List<Clientcommon> clientcommonList, String operName);

    /**
     * 新增/编辑操作
     *
     * @param clientcommon
     * @return
     */
    Boolean saOrUp(Clientcommon clientcommon);
}

