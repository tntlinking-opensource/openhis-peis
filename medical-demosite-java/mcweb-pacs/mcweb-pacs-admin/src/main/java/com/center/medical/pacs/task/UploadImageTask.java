package com.center.medical.pacs.task;

import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.data.bean.model.KsIp;
import com.center.medical.data.service.KsIpService;
import com.center.medical.system.service.ISysBranchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author: 路飞
 * @date: 2023-02-07 10:46
 * @description: 科室传图定时任务
 */
@Slf4j
@Component("uploadImageTask")
public class UploadImageTask {

    @Resource
    private KsIpService ksIpService;
    @Resource
    private ISysBranchService iSysBranchService;

    /**
     * 科室传图定时任务
     *
     * @return
     */
    public void uploadImage() {
        log.info("科室传图定时任务开始");
        SysBranch branch = iSysBranchService.getDefaultBranch();
        if (Objects.nonNull(branch)) {
            List<KsIp> list = ksIpService.list(new LambdaQueryWrapper<KsIp>().eq(KsIp::getStatus, 1)
                    .eq(KsIp::getBranchId, branch.getBranchId()));
            for (KsIp ks :
                    list) {
                if (StringUtils.isNotBlank(ks.getKsId())) {
                    HttpUtil.get(Constants.HTTP + ks.getIp() + Constants.KS_UPLAOD_IMAGE_PATH + ks.getId());
                }
            }
        }
        log.info("科室传图定时任务结束");
    }

}
