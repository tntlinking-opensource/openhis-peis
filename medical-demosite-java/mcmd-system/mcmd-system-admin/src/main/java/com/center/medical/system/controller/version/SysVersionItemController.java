package com.center.medical.system.controller.version;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.exception.GlobalException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.system.bean.dto.SysVersionItemDto;
import com.center.medical.system.bean.model.SysVersion;
import com.center.medical.system.bean.model.SysVersionItem;
import com.center.medical.system.bean.param.SysVersionItemParam;
import com.center.medical.system.service.SysVersionItemService;
import com.center.medical.system.service.SysVersionSvService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 系统更新记录(SysVersionItem)接口
 *
 * @author makejava
 * @since 2024-03-01 18:03:43
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "版本控制-系统更新记录")
@RequestMapping("sysVersionItem")
public class SysVersionItemController extends BaseController {
    /**
     * 服务对象
     */
    private final SysVersionItemService sysVersionItemService;
    private final MapperFacade mapperFacade;
    private final SysVersionSvService sysVersionSvService;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询系统更新记录")
    public R<IPage<SysVersionItem>> getPage(PageParamSimple pageParamSimple, SysVersionItemParam param) {
        PageParam<SysVersionItem> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.sysVersionItemService.getPage(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param logId 主键
     * @return 单条数据
     */
    @GetMapping("{logId}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据logId查系统更新记录详情")
    @ApiImplicitParam(name = "logId", value = "要查询的对象的主键{logId}")
    public R<SysVersionItem> selectOne(@PathVariable Integer logId) {
        return R.ok(this.sysVersionItemService.getInfoById(logId));
    }

    /**
     * 新增数据
     *
     * @param sysVersionItems 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加系统更新记录")
    @Log(title = "系统更新记录", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"sysVersionItem.logId"})
    public R insert(@RequestBody List<SysVersionItem> sysVersionItems) {
        return R.toResult(this.sysVersionItemService.saveBatch(sysVersionItems));
    }

    /**
     * 修改数据
     *
     * @param sysVersionItem 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新系统更新记录")
    @Log(title = "系统更新记录", businessType = BusinessType.UPDATE)
    public R update(@RequestBody SysVersionItem sysVersionItem) {
        return R.toResult(this.sysVersionItemService.updateById(sysVersionItem));
    }

    /**
     * 删除数据
     *
     * @param logIds 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{logIds}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除系统更新记录")
    @Log(title = "系统更新记录", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "logIds", value = "要删除的对象主键{logId}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<Integer> logIds) {
        return R.toResult(this.sysVersionItemService.removeByIds(logIds));
    }


    /**
     * 导入更新内容
     *
     * @param file
     * @return
     * @throws Exception
     */
//    @PreAuthorize("@ss.hasPermi(':import')")
    @PostMapping("/importItem")
    @ApiOperation(value = "导入更新内容", notes = "导入更新内容", position = 10)
    @ApiImplicitParam(name = "file", value = "导入的文件")
    @Log(title = "版本控制-系统更新记录-导入", businessType = BusinessType.IMPORT)
    public R<String> importItem(MultipartFile file) throws Exception {
        if (Objects.isNull(file)) {
            throw new GlobalException("请选择上传文件！");
        }
        String extName = FileUtil.extName(file.getOriginalFilename());
        if (!extName.toLowerCase().endsWith("xls") && !extName.toLowerCase().endsWith("xlsx")) {
            throw new GlobalException("请选择正确的文件类型，必须是以.xls或.xlsx结尾！");
        }
        ExcelUtil<SysVersionItemDto> util = new ExcelUtil<>(SysVersionItemDto.class);
        List<SysVersionItemDto> list = util.importExcel(file.getInputStream());
        if (CollectionUtil.isEmpty(list)) {
            throw new GlobalException("文件导入失败：该文件没数据，不能进行导入！");
        }
        String operName = SecurityUtils.getUsername();
        List<SysVersionItem> itemList = new ArrayList<>();
        Boolean flag = Boolean.TRUE;
        String result = "";
        int i = 1;
        for (SysVersionItemDto item : list) {
            if (StringUtils.isBlank(item.getContent()) || Objects.isNull(item.getVersionId())) {
                flag = Boolean.FALSE;
                result = result + "第" + i + "条记录更新内容或者版本号为空；";
                i++;
                continue;
            }
            SysVersion version = sysVersionSvService.getById(item.getVersionId());
            if (Objects.isNull(version)) {
                flag = Boolean.FALSE;
                result = result + "第" + i + "条记录版本号不存在；";
                i++;
                continue;
            }
            item.setCreateBy(operName);
            SysVersionItem sysVersionItem = new SysVersionItem();
            sysVersionItem.setVersionId(item.getVersionId());
            sysVersionItem.setModifyType(item.getModifyType());
            sysVersionItem.setModuleName(item.getModuleName());
            sysVersionItem.setContent(item.getContent());
            sysVersionItem.setFinishTime(DateUtil.parse(StringUtils.replace(item.getFinishTime(), " ", " "), "yyyy-MM-dd HH:mm:ss"));
//            log.warn("更新内容：{}、{}", item, sysVersionItem);
            itemList.add(sysVersionItem);
            i++;
        }
        if (flag) {
            sysVersionItemService.saveBatch(itemList);
            result = "导入成功！共计" + (i - 1) + "条记录";
        } else {
            throw new GlobalException("导入失败！" + result);
        }

        return R.ok(result);
    }
}


