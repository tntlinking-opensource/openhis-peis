package com.center.medical.quartz.task;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.center.medical.common.config.Domain;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.utils.http.HttpUtils;
import com.center.medical.common.utils.ip.IpUtils;
import com.center.medical.common.utils.redis.RedisSetUtil;
import com.center.medical.sync.bean.param.GenerateCodeParam;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysConfigService;
import org.quartz.DisallowConcurrentExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 定时生成体检号定时任务
 */
@Component("generateCodeTask")
@DisallowConcurrentExecution
public class GenerateCodeTask {
    private static final Logger log = LoggerFactory.getLogger(GenerateCodeTask.class);
    @Resource
    private ISysBranchService iSysBranchService;
    @Resource
    private ISysConfigService iSysConfigService;


    /**
     * 生成500条,存入redis中
     */
    public void generateCode() {
        log.info("生成体检号定时任务执行中--------------");
        List<String> ips = IpUtils.getInnerHostIp();
        if (iSysConfigService.sysJobAuthIp(11L, ips)) {
            //发送请求到线上,参数简码，版本号，数量
            Set<Object> set = RedisSetUtil.queryByRange(Constants.PATIENT_CODE_SET, null, null);
            int count = 20 - (ObjectUtils.isEmpty(set) ? 0 : set.size());
//        int count = 50;
            String jm = iSysBranchService.getBranchFlag(null);
            String version = "";
            GenerateCodeParam param = new GenerateCodeParam(jm, version, count);

            Domain domain = iSysConfigService.getDomain();
            String pfDomain = domain.getPlatformDomain();
            String url = pfDomain + Constants.ONLINE_SYNC_DATA_GENERATECODE;
            //生成体检号发送请求
            String s = HttpUtils.sendPost(url, JSONUtil.toJsonStr(param));
            log.info("请求的数据是:{}", s);
            JSONArray jsonArray = JSONUtil.parseArray(s);
            //插入本地redis
            for (Object patientCode : jsonArray) {
                RedisSetUtil.addToSortedSet(Constants.PATIENT_CODE_SET, patientCode, DateUtil.currentSeconds());
            }

            //生成档案号发送请求
            String archiveUrls = pfDomain + Constants.ONLINE_SYNC_DATA_GENERATEARCHIVECODE;
            String archives = HttpUtils.sendPost(archiveUrls, JSONUtil.toJsonStr(param));
            log.info("请求的数据是:{}", archives);
            JSONArray archiveJsonArray = JSONUtil.parseArray(archives);
            //插入本地redis
            for (Object archive : archiveJsonArray) {
                RedisSetUtil.addToSortedSet(Constants.PATIENT_ARCHIVE_CODE_SET, archive, DateUtil.currentSeconds());
            }
        }
    }


}
