package com.center.medical.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.PeispatientPhoto;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PeispatientPhotoMapper;
import com.center.medical.service.PeispatientPhotoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 体检者头像(PeispatientPhoto)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:06
 */
@Slf4j
@Service("peispatientPhotoService")
@RequiredArgsConstructor
public class PeispatientPhotoServiceImpl extends ServiceImpl<PeispatientPhotoMapper, PeispatientPhoto> implements PeispatientPhotoService {

    private final PeispatientPhotoMapper peispatientPhotoMapper;

    /**
     * 分页查询[体检者头像]列表
     *
     * @param page  分页参数
     * @param param PeispatientPhoto查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PeispatientPhoto> getList(PageParam<PeispatientPhoto> page, PeispatientPhoto param) {
        return peispatientPhotoMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public PeispatientPhoto getInfoById(String id) {
        return peispatientPhotoMapper.getInfoById(id);
    }

    /**
     * 获取图片
     *
     * @param patient
     * @return
     */
    @Override
    public String getPicture(Peispatient patient) {
        if (patient == null) {
            return null;
        }
        PeispatientPhoto photo = peispatientPhotoMapper.selectOne(new QueryWrapper<PeispatientPhoto>()
                .eq("patientcode", patient.getPatientcode()));
        return photo == null || StringUtils.isBlank(photo.getPicture()) ? null : photo.getPicture();
    }


    /**
     * 通过体检号获取图片
     *
     * @param patientcode
     * @return
     */
    @Override
    public String getPictureByCode(String patientcode) {
        if (StringUtils.isEmpty(patientcode)) {
            return "";
        }
        PeispatientPhoto photo = peispatientPhotoMapper.selectOne(new QueryWrapper<PeispatientPhoto>()
                .eq("patientcode", patientcode));
        return photo == null || photo.getPicture() == null ? "" : photo.getPicture();
    }
}

