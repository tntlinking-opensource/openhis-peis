package com.center.medical.pacs.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.AttachmentConfig;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.service.AttachmentConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 图像存储配置(AttachmentConfig)表控制层
 *
 * @author ay
 * @since 2022-12-29 10:47:56
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "PACS基础配置-图像存储配置")
@RequestMapping("/lis/attachmentConfig")
public class AttachmentConfigController extends BaseController {
    /**
     * 服务对象
     */
    private final AttachmentConfigService attachmentConfigService;
    private final MapperFacade mapperFacade;


    /**
     * 【PACS基础配置-图像存储配置】功能接口总结
     */
    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【PACS基础配置-图像存储配置】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("分页查询", "GET", "/lis/attachmentConfig/page", "11.pacs/lis->PACS基础配置-图像存储配置->分页查询", null),
                new InterfaceVo("详情", "GET", "/lis/attachmentConfig/{id}", "11.pacs/lis->PACS基础配置-图像存储配置->详情", null),
                new InterfaceVo("添加或修改", "POST", "/lis/attachmentConfig/saveOrUpdate", "11.pacs/lis->PACS基础配置-图像存储配置->添加或修改", null),
                new InterfaceVo("删除", "DELETE", "/lis/attachmentConfig/{ids}", "11.pacs/lis->PACS基础配置-图像存储配置->删除", null)

        );
        return R.ok(new FunctionVo("11.pacs/lis", "PACS基础配置-图像存储配置", interfaceVos, "11.pacs/lis"));
    }

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public R<IPage<AttachmentConfig>> getPage(PageParamSimple pageParamSimple) {
        PageParam<AttachmentConfig> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.attachmentConfigService.getPage(page));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<AttachmentConfig> selectOne(@PathVariable String id) {
        return R.ok(this.attachmentConfigService.getInfoById(id));
    }

    /**
     * 添加或修改
     *
     * @param attachmentConfig 实体对象
     * @return 新增结果
     */
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加或修改", notes = "添加或修改")
    @Log(title = "", businessType = BusinessType.INSERT)
    public R insert(@RequestBody AttachmentConfig attachmentConfig) {
        return R.toResult(this.attachmentConfigService.saOrUp(attachmentConfig));
    }


    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除")
    @Log(title = "", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.attachmentConfigService.removeByIds(ids));
    }
}

