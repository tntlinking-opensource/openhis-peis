package com.center.medical.data.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.ZyFhcsGrClass;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;

import com.center.medical.data.bean.model.ZyFhclGr;
import com.center.medical.data.bean.param.ZyFhclGrPageParam;
import com.center.medical.data.bean.vo.AllPersonalTypeVo;
import com.center.medical.data.bean.vo.ProtectiveEquipmentVo;
import com.center.medical.data.bean.vo.ZyFhclGrPageVo;
import com.center.medical.data.service.ZyFhclGrService;
import com.center.medical.service.ZyFhcsGrClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * JC个人防护用品(MdZyFhclGr)接口
 *
 * @author ay
 * @since 2023-07-17 20:54:27
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "个人防护用品")
@RequestMapping("data/ZyFhclGr")
public class ZyFhclGrController extends BaseController {
    /**
     * 服务对象
     */
    private final ZyFhclGrService zyFhclGrService;
    private final MapperFacade mapperFacade;
    private final ZyFhcsGrClassService zyFhcsGrClassService;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param zyFhclGr      查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询", notes = "分页查询JC个人防护用品")
    public R<IPage<ZyFhclGrPageVo>> getPage(PageParamSimple pageParamSimple, ZyFhclGrPageParam param) {
        PageParam<ZyFhclGrPageVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.zyFhclGrService.getPage(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    @ApiOperation(value = "详情", notes = "根据id查JC个人防护用品详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<ZyFhclGr> selectOne(@PathVariable String id) {
        ZyFhclGr zyFhclGr = zyFhclGrService.getInfoById(id);
        ZyFhcsGrClass zyFhcsGrClass = zyFhcsGrClassService.getInfoById(zyFhclGr.getDefendIndividualClass());
        if (ObjectUtils.isNotEmpty(zyFhcsGrClass)){
            zyFhclGr.setDefendIndividualClassName(zyFhcsGrClass.getDefendIndividualClass());
        }
        return R.ok(zyFhclGr);
    }

    /**
     * 新增数据
     *
     * @param zyFhclGr 实体对象
     * @return 新增结果
     */
    @PostMapping("/saOrUp")
    @ApiOperation(value = "添加或更新", notes = "添加或更新")
    @Log(title = "JC个人防护用品", businessType = BusinessType.INSERT)
    public R insert(@RequestBody ZyFhclGr zyFhclGr) {
        return R.toResult(this.zyFhclGrService.saOrUp(zyFhclGr));
    }



    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除", notes = "删除JC个人防护用品")
    @Log(title = "JC个人防护用品", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        ZyFhclGr zyFhclGr = new ZyFhclGr();
        zyFhclGr.setIsDelete(1);
        zyFhclGrService.update(zyFhclGr,new QueryWrapper<ZyFhclGr>().in("id",ids));
        return R.toResult(Boolean.TRUE);
    }


    /**
     * 获取防护用品分类
     * @param pageParamSimple
     * @param inputCode
     * @return
     */
    @GetMapping("/getAllPersonalType")
    @ApiOperation(value = "获取防护用品分类", notes = "获取防护用品分类")
    public R<IPage<AllPersonalTypeVo>> getAllPersonalType(PageParamSimple pageParamSimple, String inputCode) {
        if (ObjectUtils.isNotEmpty(inputCode))  inputCode = inputCode.trim();
        PageParam<AllPersonalTypeVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.zyFhclGrService.getAllPersonalType(page, inputCode));
    }



    /**
     * 获取防护用品
     * @param pageParamSimple
     * @param inputCode
     * @return
     */
    @GetMapping("/getProtectiveEquipment")
    @ApiOperation(value = "获取防护用品", notes = "获取防护用品")
    public R<IPage<ProtectiveEquipmentVo>> getProtectiveEquipment(PageParamSimple pageParamSimple, String inputCode) {
        if (ObjectUtils.isNotEmpty(inputCode))  inputCode = inputCode.trim();
        PageParam<ProtectiveEquipmentVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.zyFhclGrService.getProtectiveEquipment(page, inputCode));
    }

}

