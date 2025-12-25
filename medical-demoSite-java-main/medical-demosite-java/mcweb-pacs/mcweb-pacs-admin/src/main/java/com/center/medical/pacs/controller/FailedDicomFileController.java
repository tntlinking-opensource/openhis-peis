package com.center.medical.pacs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.center.medical.common.constant.CacheConstants;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageAdapter;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.redis.RedisUtil;
import com.center.medical.dicom.bean.model.FailedDicomFile;
import com.center.medical.pacs.bean.param.FailedDicomParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 接收失败的dicom文件(FailedDicomFile)接口
 *
 * @author makejava
 * @since 2023-09-12 15:00:26
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "接收失败的dicom文件")
@RequestMapping("pacs/failedDicomFile")
public class FailedDicomFileController extends BaseController {
    private final MapperFacade mapperFacade;

    /**
     * 获取dicom接收图片失败记录
     *
     * @return
     */
    @GetMapping("/fail/page")
    @ApiOperation(value = "获取dicom接收图片失败记录", notes = "获取dicom接收图片失败记录")
    public R<Page<FailedDicomFile>> failPage(PageParamSimple pageParamSimple, FailedDicomParam param) {
        String key = CacheConstants.FAILED_DICOM_FILE_KEY + "*";
        if (StringUtils.isNotBlank(param.getPatientcode())) {
            key = CacheConstants.FAILED_DICOM_FILE_KEY + param.getPatientcode() + "*";
        }
        Set<String> keys = RedisUtil.keys(key);
        PageParam page = mapperFacade.map(pageParamSimple, PageParam.class);
        PageAdapter pageAdapter = new PageAdapter(page);
//        log.info("分页参数：{}，{}", pageParamSimple, pageAdapter);
        int begin = pageAdapter.getBegin();
        int size = pageAdapter.getSize();
        List<String> keyList = new ArrayList<>();
        List<String> list = keys.stream().sorted().collect(Collectors.toList());

        if (keys.size() > begin) {
            int len = size + begin;
            if (keys.size() < len) {
                len = keys.size();
            }
            for (int i = begin; i < len; i++) {
                keyList.add(list.get(i));
            }
        }
//        log.info("数据key：{}，{}", keys, keyList);
        page.setRecords(RedisUtil.multiGet(keyList));
        page.setTotal(keys.size());
        return R.ok(page);
    }

    /**
     * 获取dicom接收图片失败记录
     *
     * @return
     */
    @GetMapping("/fail/deal/{rkeys}")
    @ApiOperation(value = "提交处理", notes = "提交处理")
    @ApiImplicitParam(name = "rkeys", value = "处理的redis的key集合，多个以英文逗号（,）隔开")
    public R<String> deal(@PathVariable List<String> rkeys) {
        log.info("处理数据：{}", rkeys);
        RedisUtil.delByKeys(rkeys);
        return R.ok("操作成功！");
    }
}
