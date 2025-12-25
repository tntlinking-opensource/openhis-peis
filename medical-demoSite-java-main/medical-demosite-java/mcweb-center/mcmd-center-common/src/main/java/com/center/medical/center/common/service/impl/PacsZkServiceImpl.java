package com.center.medical.center.common.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.PacsResult;
import com.center.medical.center.common.service.PacsService;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.http.HttpUtils;
import com.center.medical.dao.PacsResultMapper;
import com.center.medical.pacslis.bean.dto.MiddleDbDto;
import com.center.medical.pacslis.bean.dto.PacsDto;
import com.center.medical.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 沃德pacs(老系统pacs)
 * @author xhp
 * @since 2023-03-07 8:57
 */
@Service("pacsZkService")
@RequiredArgsConstructor
@Slf4j
public class PacsZkServiceImpl extends ServiceImpl<PacsResultMapper, PacsResult> implements PacsService {
    private final ISysConfigService iSysConfigService;

    @Override
    public void save(MiddleDbDto middleDbDto) {
        String url = iSysConfigService.getDomain().getPacsDomain()+"/inter/pacs!save.action";
        String s = HttpUtils.sendPost(url,JSONUtil.toJsonStr(middleDbDto));
        R responseEntity = JSONUtil.toBean(s, R.class);

        if (200 != responseEntity.getCode()) {
            log.error("插入沃德pacs失败", url, JSONUtil.toJsonStr(responseEntity.getMsg()));
            throw new ServiceException("插入沃德pacs失败！");
        }
        log.info("插入沃德pacs成功", JSONUtil.toJsonStr(responseEntity.getData()));
    }

    @Override
    public List<PacsDto> selectList(String patientcode){
        String url = iSysConfigService.getDomain().getPacsDomain()+"/inter/pacs!getListData.action";
        String s = HttpUtils.sendGet(url,"patientcode="+patientcode);
        R responseEntity = JSONUtil.toBean(s, R.class);

        if (200 != responseEntity.getCode()) {
            log.error("获取pacs结果失败", url, JSONUtil.toJsonStr(responseEntity.getMsg()),patientcode);
            throw new ServiceException("获取pacs结果失败！");
        }
        return (List<PacsDto>) responseEntity.getData();
    }
}
