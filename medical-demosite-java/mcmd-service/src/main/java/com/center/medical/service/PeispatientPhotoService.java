package com.center.medical.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.PeispatientPhoto;
import com.center.medical.common.utils.page.PageParam;

/**
 * 体检者头像(PeispatientPhoto)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:06
 */
public interface PeispatientPhotoService extends IService<PeispatientPhoto> {

    /**
     * 分页查询[体检者头像]列表
     *
     * @param page  分页参数
     * @param param PeispatientPhoto查询参数
     * @return 分页数据
     */
    IPage<PeispatientPhoto> getList(PageParam<PeispatientPhoto> page, PeispatientPhoto param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    PeispatientPhoto getInfoById(String id);

    /**
     * 获取图片
     *
     * @param user
     * @return
     */
    String getPicture(Peispatient user);


    /**
     * 通过体检号获取图片
     *
     * @param patientcode
     * @return
     */
    String getPictureByCode(String patientcode);
}

