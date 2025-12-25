package com.center.medical.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.KdSaleer;
import com.center.medical.system.bean.param.KdSaleerParam;

/**
 * 金蝶销售员(Saleer)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:36
 */
public interface KdSaleerService extends IService<KdSaleer> {

    /**
     * 分页查询[金蝶销售员]列表
     *
     * @param page  分页参数
     * @param param Saleer查询参数
     * @return 分页数据
     */
    IPage<KdSaleer> getList(PageParam<KdSaleer> page, KdSaleerParam param);

    /**
     * 用户-金蝶数据更新
     */
    void upgradeSaleer();

    /**
     * 通过账户号查询
     * @param kingdeeAccountNo
     * @return
     */
    KdSaleer getByAccountNo(String kingdeeAccountNo);

    /**
     * 更新用户和金蝶关联表
     * @param cid
     * @return
     */
    Boolean updateUserSaleer(String cid);
}

