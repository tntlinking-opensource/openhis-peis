package com.center.medical.data.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.data.bean.model.*;
import com.center.medical.data.service.*;
import com.center.medical.data.bean.model.*;
import com.center.medical.data.bean.param.BaseExamItemParam;
import com.center.medical.data.bean.param.FeatureListParam;
import com.center.medical.data.bean.param.ThreeSaOrUpParam;
import com.center.medical.data.bean.vo.AdiconSelectVo;
import com.center.medical.data.bean.vo.FeatureListVo;
import com.center.medical.data.bean.vo.PostionCheckItemVo;
import com.center.medical.data.service.*;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysDeptService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * 检查项目设置(Basexamltem)表控制层
 *
 * @author 路飞船长
 * @since 2022-11-17 09:05:09
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "检查项目设置")
@RequestMapping("basexamltem")
public class BasexamltemController extends BaseController {
    /**
     * 服务对象
     */
    private final BasexamltemService basexamltemService;
    private final MapperFacade mapperFacade;
    private final BasconclusionService basconclusionService;
    private final BasexamltemtypeService basexamltemtypeService;
    private final ISysDeptService iSysDeptService;
    private final BasexamltemSignService basexamltemSignService;
    private final ISysBranchService sysBranchService;
    private final BaseAdiconExamItemCodeService baseAdiconExamItemCodeService;
    private final DevicetypePositionCheckitemService devicetypePositionCheckitemService;
    private final ItemRelatedInformationService itemRelatedInformationService;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询实体
     * @return 所有数据
     */
    @GetMapping("/list")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询JC检查项目表")
    public R<IPage<Basexamltem>> selectAll(PageParamSimple pageParamSimple, BaseExamItemParam param) {
        PageParam<Basexamltem> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<Basexamltem> iPage = basexamltemService.getList(page, param);
        return R.ok(iPage);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查JC检查项目表详情")
    public R<Basexamltem> selectOne(@PathVariable String id) {
        Basexamltem info = this.basexamltemService.getInfoById(id);
        if (Objects.nonNull(info)) {
            // 获取检查类型名称
            Basexamltemtype type = basexamltemtypeService.getById(info.getIdExamitemtype());
            if (Objects.nonNull(type)) {
                info.setExamitemtypeName(type.getExamitemtypeName());
            }
            // 获取科室名称
            SysDept dept = iSysDeptService.getByDeptNo(info.getDivisionId());
            if (Objects.nonNull(dept)) {
                info.setName(dept.getDeptName());
            }
            //获取结论词名称
            if (StringUtils.isNotBlank(info.getIdConclusionhi())) {
                Basconclusion hi = basconclusionService.getInfoById(info.getIdConclusionhi());
                if (Objects.nonNull(hi)) {
                    info.setIdConclusionhiName(hi.getName());
                }
            }
            if (StringUtils.isNotBlank(info.getIdConclusionlo())) {
                Basconclusion lo = basconclusionService.getInfoById(info.getIdConclusionlo());
                if (Objects.nonNull(lo)) {
                    info.setIdConclusionloName(lo.getName());
                }
            }
            if (StringUtils.isNotBlank(info.getIdConclusionne())) {
                Basconclusion ne = basconclusionService.getInfoById(info.getIdConclusionne());
                if (Objects.nonNull(ne)) {
                    info.setIdConclusionneName(ne.getName());
                }
            }
            if (StringUtils.isNotBlank(info.getIdConclusionpo())) {
                Basconclusion po = basconclusionService.getInfoById(info.getIdConclusionpo());
                if (Objects.nonNull(po)) {
                    info.setIdConclusionpoName(po.getName());
                }
            }
            //不是公共的，查询分中心的名称
            if (ObjectUtils.isNotEmpty(info.getIsPublic()) && info.getIsPublic() != 1) {
                String fzxIds = info.getFzxIds();
                //分中心
                List<SysBranch> sysBranches = sysBranchService.list(new QueryWrapper<SysBranch>().in("id", fzxIds.split(",")));
                String fzxName = "";
                for (SysBranch sysBranch : sysBranches) {
                    fzxName = fzxName + sysBranch.getFzx();
                }
                info.setFzxName(fzxName);
            }
            //艾迪康名称
            if (StringUtils.isNotEmpty(info.getExamitemCode3())) {
                BaseAdiconExamItemCode adicon = baseAdiconExamItemCodeService.getOne(new QueryWrapper<BaseAdiconExamItemCode>().eq("item_code", info.getExamitemCode3()));
                info.setAdiconName(ObjectUtils.isNotEmpty(adicon) ? adicon.getItemName() : "");
            }
        } else {
            throw new ServiceException("对象不存在或者已被删除");
        }
        return R.ok(info);
    }

    /**
     * 新增数据
     *
     * @param basexamltem 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加JC检查项目表")
    @Log(title = "JC检查项目表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"basexamltem.id"})
    public R insert(@RequestBody Basexamltem basexamltem) {
        return R.toResult(this.basexamltemService.saOrUp(basexamltem));
    }

    /**
     * 修改数据
     *
     * @param basexamltem 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新JC检查项目表")
    @Log(title = "JC检查项目表", businessType = BusinessType.UPDATE)
    public R update(@RequestBody Basexamltem basexamltem) {
        return R.toResult(this.basexamltemService.saOrUp(basexamltem));
    }

    /**
     * 删除数据
     *
     * @param ids 主键结合
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除JC检查项目表")
    @Log(title = "JC检查项目表", businessType = BusinessType.DELETE)
    public R delete(@PathVariable List<String> ids) {

        return R.toResult(this.basexamltemService.delete(ids));
    }


    /**
     * 根据检查项目ID获取相对应的体征词
     *
     * @param pageParamSimple
     * @param param
     * @return
     */
    @GetMapping("/getFeatureListData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "根据检查项目ID获取相对应的体征词", notes = "根据检查项目ID获取相对应的体征词")
    public R<IPage<FeatureListVo>> getFeatureListData(PageParamSimple pageParamSimple, FeatureListParam param) {
        PageParam<FeatureListVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<FeatureListVo> iPage = basexamltemSignService.getFeatureListData(page, param);
        return R.ok(iPage);
    }


    /**
     * 有无数据管理权限
     *
     * @return
     */
    @GetMapping("/getFdataRole")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "有无数据管理权限，true有，false没有", notes = "有无数据管理权限，true有，false没有")
    public R getFdataRole() {
        //有无数据管理
        Boolean b = SecurityUtils.hasRole(RoleAuthName.DATA_MANAGE);
        return R.ok(b);
    }


    /**
     * 通过输入码查询艾迪康项目代码表
     *
     * @param key
     * @return
     */
    @GetMapping("/getAdiconSelectData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "通过输入码查询艾迪康项目代码表", notes = "通过输入码查询艾迪康项目代码表")
    @ApiImplicitParam(name = "key", value = "输入码")
    public R<List<AdiconSelectVo>> getAdiconSelectData(String key) {
        List<AdiconSelectVo> list = baseAdiconExamItemCodeService.getAdiconSelectData(key);
        return R.ok(list);
    }


    /**
     * 获取部位检查项目数据
     *
     * @param key
     * @param id
     * @return
     */
    @GetMapping("/getPostionCheckItemData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取部位检查项目数据", notes = "获取部位检查项目数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "名称 模糊查询"),
            @ApiImplicitParam(name = "id", value = "id")
    })
    public R<IPage<PostionCheckItemVo>> getPostionCheckItemData(PageParamSimple pageParamSimple, String key, String id) {
        PageParam<Basexamltem> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<PostionCheckItemVo> list = devicetypePositionCheckitemService.getPostionCheckItemData(page, key, id);
        return R.ok(list);
    }


    /**
     * 第三方接口关联
     *
     * @param pageParamSimple
     * @param baseItemId
     * @param interfaceName
     * @return
     */
    @GetMapping("/getItemRelatedInformation")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "第三方接口关联", notes = "第三方接口关联")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "baseItemId", value = "检查项目id"),
            @ApiImplicitParam(name = "interfaceName", value = "接口名称")
    })
    public R<IPage<ItemRelatedInformation>> getItemRelatedInformation(PageParamSimple pageParamSimple, String baseItemId, String interfaceName) {
        PageParam<ItemRelatedInformation> page = mapperFacade.map(pageParamSimple, PageParam.class);
        //条件构造器
        QueryWrapper<ItemRelatedInformation> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(baseItemId)) {
            queryWrapper.eq("base_item_id", baseItemId);
        }
        if (StringUtils.isNotEmpty(interfaceName)) {
            queryWrapper.like("interface_name", interfaceName);
        }
        PageParam<ItemRelatedInformation> ipage = itemRelatedInformationService.page(page, queryWrapper);
        return R.ok(ipage);
    }


    /**
     * 第三方接口-保存或修改
     *
     * @return
     */
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "第三方接口-保存或修改", notes = "第三方接口-保存或修改,返回true或false")
    public R saveOrUpdate(@RequestBody ThreeSaOrUpParam param) {
        ItemRelatedInformation iteam = mapperFacade.map(param, ItemRelatedInformation.class);
        boolean b = itemRelatedInformationService.saveOrUpdate(iteam);
        return R.ok(b);
    }


    /**
     * 第三方接口-详情
     *
     * @return
     */
    @GetMapping("/edit")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "第三方接口-详情", notes = "第三方接口-详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "第三方接口数据id"),
            @ApiImplicitParam(name = "baseItemId", value = "检查项目id")
    })
    public R saveOrUpdate(String id, String baseItemId) {
        //第三方接口
        ItemRelatedInformation itemRelatedInformation = itemRelatedInformationService.getInfoById(id);
        //检查项目
        Basexamltem basExamLtem = basexamltemService.getInfoById(baseItemId);
        if (basExamLtem != null) {
            itemRelatedInformation.setBaseItemCode(basExamLtem.getInterfaceCode());
            itemRelatedInformation.setBaseItemName(basExamLtem.getExamitemName());
            itemRelatedInformation.setBaseItemId(basExamLtem.getId());
        }
        return R.ok(itemRelatedInformation);
    }


    /**
     * 第三方接口-删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/remove")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "第三方接口-删除", notes = "第三方接口-删除,返回true或false")
    @ApiImplicitParam(name = "ids", value = "第三方接口数据id")
    public R remove(@RequestParam List<String> ids) {
        boolean b = itemRelatedInformationService.remove(new QueryWrapper<ItemRelatedInformation>().in("id", ids));
        return R.ok(b);
    }
}

