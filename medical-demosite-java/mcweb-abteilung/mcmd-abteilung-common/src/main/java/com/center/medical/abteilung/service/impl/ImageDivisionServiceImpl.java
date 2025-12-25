package com.center.medical.abteilung.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.center.medical.abteilung.service.ImageDivisionService;
import com.center.medical.common.config.LoadProperties;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.data.bean.model.KsIp;
import com.center.medical.data.dao.KsIpMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xhp
 * @since 2023-06-29 9:25
 */
@Service
@RequiredArgsConstructor
public class ImageDivisionServiceImpl implements ImageDivisionService {
    private final KsIpMapper ksIpMapper;
    @Autowired
    private LoadProperties loadProperties;

    /**
     * 传图
     *
     * @param id
     * @return
     */
    @Override
    public String uploadImage(String id) {
        KsIp ks = ksIpMapper.selectById(id);
        String s = null;
        try {
            s = HttpUtil.get(Constants.HTTP + ks.getIp() + Constants.KS_UPLAOD_IMAGE_PATH + ks.getId());
        } catch (Exception e) {
            return "该科室传图服务连接超时，获取图片失败，请联系系统管理员处理！";
        }
        R<String> r = JSONUtil.toBean(s, R.class);
        return R.isSuccess(r) ? r.getData() : "目前暂无图片或者获取图片失败！";
    }

    /**
     * 上传数据
     * @param id
     * @return
     */
    @Override
    public String uploadData(String id) {
        KsIp ks = ksIpMapper.selectById(id);
        String s = null;
        String path = null;
        switch (ks.getKsId()) {
            case "77"://肺功能
                path = Constants.KS_UPLOAD_DATA_PATH;
                break;
            case "163"://电测听
                if (StringUtils.equals(loadProperties.name, "jiaonan")
                        || StringUtils.equals(loadProperties.name, "chengyang")
                        || StringUtils.equals(loadProperties.name, "pingdu")
                ){
                    path = Constants.KS_JNUPLOADELE_DATA_PATH;
                }else {
                    path = Constants.KS_HOUPLOADELE_DATA_PATH;
                }
                break;
            default:
                return "未知的科室!";
        }
        try {
            s = HttpUtil.get(Constants.HTTP + ks.getIp() + path + ks.getId());
//            s = HttpUtil.get("http://localhost:8090" + path + ks.getId());
        } catch (Exception e) {
            return "该科室上传数据连接超时，请联系系统管理员处理！";
        }
        System.out.println("上传数据返回的数据是:" + s);
        R<String> r = JSONUtil.toBean(s, R.class);
        return R.isSuccess(r) ? r.getData() : "目前暂无数据上传！";
    }
}
