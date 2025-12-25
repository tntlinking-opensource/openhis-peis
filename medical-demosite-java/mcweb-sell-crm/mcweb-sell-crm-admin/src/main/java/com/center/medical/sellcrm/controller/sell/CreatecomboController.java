package com.center.medical.sellcrm.controller.sell;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.data.bean.model.Harm;
import com.center.medical.data.bean.param.GetShowParam;
import com.center.medical.data.bean.vo.GetShowVo;
import com.center.medical.data.service.HarmService;
import com.center.medical.data.service.ItemsService;
import com.center.medical.sellcrm.bean.model.Comboanditem;
import com.center.medical.sellcrm.bean.model.Createcombo;
import com.center.medical.sellcrm.bean.param.CreatecomboParam;
import com.center.medical.sellcrm.bean.param.TcCopyParam;
import com.center.medical.sellcrm.bean.vo.CoSimpleVo;
import com.center.medical.sellcrm.bean.vo.ComboAndItemVo;
import com.center.medical.sellcrm.bean.vo.GetItemsVo;
import com.center.medical.sellcrm.bean.vo.TypeListVo;
import com.center.medical.sellcrm.service.ComboanditemService;
import com.center.medical.sellcrm.service.CreatecomboService;
import com.center.medical.sellcrm.service.CreatecomboTypeService;
import com.center.medical.sellcrm.service.CreatemealService;
import com.center.medical.system.service.ISysBranchService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 最小套餐(Createcombo)表控制层
 *
 * @author 路飞船长
 * @since 2022-11-15 10:47:37
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@ApiSort(value = 9)
@Api(tags = "基础数据-最小套餐")
@RequestMapping("sell/createcombo")
public class CreatecomboController extends BaseController {
    /**
     * 服务对象
     */
    private final CreatecomboService createcomboService;
    private final MapperFacade mapperFacade;
    private final ComboanditemService comboanditemService;
    private final HarmService harmService;
    private final CreatemealService createmealService;
    private final ItemsService itemsService;
    private final CreatecomboTypeService createcomboTypeService;
    private final ISysBranchService iSysBranchService;

    //TODO 权限设置

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询实体
     * @return 所有数据
     */
    @GetMapping("/list")
//    @PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页列表", notes = "分页查询最小套餐", position = 1)
    public R<IPage<Createcombo>> selectAll(PageParamSimple pageParamSimple, CreatecomboParam param) {
        PageParam<Createcombo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.createcomboService.getList(page, param));
    }

    /**
     * 根据输入套餐名称或拼音码分页查询
     *
     * @param pageParamSimple 分页参数
     * @param key             输入值
     * @return 所有数据
     */
    @GetMapping("/getAutoCompleteData")
//    @PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "根据输入值分页查询", notes = "根据输入套餐名称或拼音码分页查询", position = 2)
    public R<IPage<CoSimpleVo>> getAutoCompleteData(PageParamSimple pageParamSimple, @RequestParam(value = "key") String key) {
        Long userId = SecurityUtils.getUserId();
        PageParam<Createcombo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.createcomboService.getAutoCompleteData(page, key, userId));
    }

    /**
     * 获取和套餐关联的收费项目
     *
     * @param id 主键
     * @return 套餐关联的收费项目列表
     */
    @SuppressWarnings("rawtypes")
    @GetMapping("/items")
//    @PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取和套餐关联的收费项目", notes = "根据套餐获取和套餐关联的收费项目")
    @ApiImplicitParam(name = "id", value = "套餐id")
    public R<IPage<ComboAndItemVo>> getItems(PageParamSimple pageParamSimple, String id) {
        PageParam<Comboanditem> page = mapperFacade.map(pageParamSimple, PageParam.class);
        //获取关联数据
        return R.ok(comboanditemService.getListByComboId(page, id));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
//    @PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查最小套餐详情")
    @ApiImplicitParam(name = "id", value = "查询对象的套餐id")
    public R<Createcombo> selectOne(@PathVariable String id) {

//        log.info("通过主键查询单条数据：{}、{}", id, SecurityUtils.getUserNo());
        Createcombo createcombo = this.createcomboService.getInfoById(id);
        //获取接害因素
        if (StringUtils.isNotBlank(createcombo.getJhys())) {
            String[] ids = createcombo.getJhys().split(Constants.COMMA);
            List<String> list = Arrays.stream(ids).collect(Collectors.toList());
            List<Harm> harms = harmService.listByIds(list);
            Set<String> set = harms.stream().map(Harm::getHarmName).collect(Collectors.toSet());
            if (CollectionUtil.isNotEmpty(set)) {
                createcombo.setJhysmc(StringUtils.join(set, Constants.COMMA));
            }
        }
        //分中心名称
        if (StringUtils.isNotBlank(createcombo.getFzxid())) {
            String[] ids = createcombo.getFzxid().split(Constants.COMMA);
            List<String> list = Arrays.stream(ids).collect(Collectors.toList());
            List<SysBranch> list1 = iSysBranchService.list(new LambdaQueryWrapper<SysBranch>().in(SysBranch::getBranchId, list));
            Set<String> set = list1.stream().map(SysBranch::getFzx).collect(Collectors.toSet());
            if (CollectionUtil.isNotEmpty(set)) {
                createcombo.setFzx(StringUtils.join(set, Constants.COMMA));
            }
        }

        return R.ok(createcombo);
    }

    /**
     * 新增数据
     *
     * @param createcombo 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加最小套餐")
    @Log(title = "最小套餐", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"createcombo.id"})
    public R insert(@RequestBody Createcombo createcombo) {
        log.info("添加或更新最小套餐参数"+ createcombo);
        createcombo.setIsDelete(0);
        //设置套餐由谁创建
        createcombo.setXsjlid(SecurityUtils.getUserNo());
        createcombo.setCreatedate(new Date());
        if (Objects.equals(createcombo.getTjlx(), 0)) {
            createcombo.setCombostate("1");
        } else {
            createcombo.setCombostate("2");
        }
        //数据保存
        return R.toResult(this.createcomboService.saOrUp(createcombo));
    }

    /**
     * 修改数据
     *
     * @param createcombo 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新最小套餐")
    @Log(title = "最小套餐", businessType = BusinessType.UPDATE)
    public R update(@RequestBody Createcombo createcombo) {
        createcombo.setModifydate(new Date());
        createcombo.setModifier(SecurityUtils.getUsername());
        return R.toResult(this.createcomboService.updateById(createcombo));
    }

    /**
     * 删除数据
     *
     * @param ids 主键结合
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除最小套餐")
    @ApiImplicitParam(name = "ids", value = "要删除的套餐id集合，多个以英文逗号隔开")
    @Log(title = "最小套餐", businessType = BusinessType.DELETE)
    public R delete(@PathVariable List<String> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            throw new ServiceException("请选择要删除的对象");
        }
        Createcombo createCombo = new Createcombo();
        createCombo.setIsDelete(1);
        createCombo.setModifydate(new Date());
        createCombo.setModifier(SecurityUtils.getUserNo());
        return R.toResult(createcomboService.update(createCombo, new LambdaUpdateWrapper<Createcombo>().in(Createcombo::getId, ids)));
    }

    /**
     * 复制套餐
     *
     * @param param 复制属性
     * @return 复制结果
     */
    @PostMapping("/copy")
//    @PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "复制套餐", notes = "复制套餐")
    @Log(title = "最小套餐复制", businessType = BusinessType.INSERT)
    public R copy(@RequestBody TcCopyParam param) {
        //进行仿制
        String userNo = SecurityUtils.getUserNo();
        return R.toResult(createcomboService.copy(param, userNo));
    }


    /**
     * 判断最小套餐是否允许编辑
     *
     * @return
     */
    @GetMapping("/isEdit/{comboId}")
    @ApiOperation(value = "是否允许编辑", notes = "判断最小套餐是否允许编辑")
    @ApiImplicitParam(name = "comboId", value = "判断的套餐ID")
    public R<Boolean> isEdit(@PathVariable String comboId) {
        Createcombo one = createcomboService.getOne(new LambdaQueryWrapper<Createcombo>().eq(Createcombo::getId, comboId)
                .eq(Createcombo::getIsDelete, 0));
        return R.ok(Objects.nonNull(one) ? Objects.equals(one.getBjzt(), 0) : Boolean.FALSE);
    }

    /**
     * 判断最小套餐是否允许删除
     *
     * @return
     */
    @GetMapping("/isRemove/{comboIds}")
    @ApiOperation(value = "是否允许删除", notes = "判断最小套餐是否允许删除")
    @ApiImplicitParam(name = "comboIds", value = "要删除的套餐id集合，多个以英文逗号隔开")
    public R<Boolean> isRemove(@PathVariable List<String> comboIds) {
        if (CollectionUtil.isEmpty(comboIds)) {
            throw new ServiceException("请选择要删除的对象");
        }
        List<Createcombo> coList = createcomboService.list(new LambdaQueryWrapper<Createcombo>()
                .in(Createcombo::getId, comboIds)
                .eq(Createcombo::getIsDelete, 0));
        for (Createcombo co : coList) {
            if (Objects.equals(co.getBjzt(), 1)) {
                return R.ok(Boolean.FALSE);
            }
        }
        return R.ok(Boolean.TRUE);
    }

    /**
     * 根据接害因素和职业体检类别判断是否存在重复,存在重复不能进行保存
     *
     * @return
     */
    @GetMapping("/isRepeat")
    @ApiOperation(value = "是否重复", notes = "根据接害因素和职业体检类别判断是否存在重复,存在重复(true)不能进行保存")
    public R<Boolean> isRepeat(TcCopyParam param) {
        return R.ok(createcomboService.isRepeat(param));
    }

    /**
     * 同步套餐
     *
     * @param comboIds 要同步的套餐id集合
     * @return
     */
    @PutMapping("/synchronous/{comboIds}")
    @ApiOperation(value = "同步套餐", notes = "同步套餐")
    @ApiImplicitParam(name = "comboIds", value = "要同步的套餐id集合，多个以英文逗号隔开")
    public R<Boolean> synchronous(@PathVariable List<String> comboIds) {
        if (CollectionUtil.isEmpty(comboIds)) {
            throw new ServiceException("请选择要同步的套餐对象");
        }
        return R.ok(createcomboService.synchronous(comboIds));
    }

    /**
     * 设置为/取消活动套餐
     *
     * @param comboIds 操作的套餐id集合
     * @return
     */
    @PutMapping("/setActive")
    @ApiOperation(value = "设置为/取消活动套餐", notes = "设置为/取消活动套餐")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "comboIds", value = "要操作的套餐id集合，多个以英文逗号隔开"),
            @ApiImplicitParam(name = "state", value = "操作标识：0取消活动 1设置为活动")
    })
    public R<Boolean> setActive(@RequestParam(name = "comboIds", required = true) List<String> comboIds, @RequestParam(name = "state", required = true) Integer state) {
        if (CollectionUtil.isEmpty(comboIds)) {
            throw new ServiceException("请选择要操作的套餐对象");
        }
        return R.ok(createcomboService.setActive(comboIds, state));
    }

    /**
     * 设置为/取消推荐套餐
     *
     * @param comboIds 操作的套餐id集合
     * @return
     */
    @PutMapping("/setRecommend")
    @ApiOperation(value = "设置为/取消推荐套餐", notes = "设置为/取消推荐套餐")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "comboIds", value = "要操作的套餐id集合，多个以英文逗号隔开"),
            @ApiImplicitParam(name = "state", value = "操作标识：0取消推荐 1设置为推荐")
    })
    public R<Boolean> setRecommend(@RequestParam(name = "comboIds", required = true) List<String> comboIds, @RequestParam(name = "state", required = true) Integer state) {
        if (CollectionUtil.isEmpty(comboIds)) {
            throw new ServiceException("请选择要操作的套餐对象");
        }
        return R.ok(createcomboService.setRecommend(comboIds, state));
    }


    /**
     * 禁用/反禁用套餐
     *
     * @param comboIds 操作的套餐id集合
     * @return
     */
    @PutMapping("/setBan")
    @ApiOperation(value = "禁用/反禁用套餐", notes = "禁用/反禁用套餐")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "comboIds", value = "要操作的套餐id集合，多个以英文逗号隔开"),
            @ApiImplicitParam(name = "state", value = "操作标识：0反禁用 1禁用")
    })
    public R<Boolean> setBan(@RequestParam(name = "comboIds", required = true) List<String> comboIds, @RequestParam(name = "state", required = true) Integer state) {
        if (CollectionUtil.isEmpty(comboIds)) {
            throw new ServiceException("请选择要操作的套餐对象");
        }
        return R.ok(createcomboService.setBan(comboIds, state));
    }


    /**
     * 获取收费项目
     *
     * @param cid
     * @return
     */
    @GetMapping("/getItems")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取收费项目", notes = "获取收费项目")
    @ApiImplicitParam(name = "cid", value = "套餐id")
    public R<List<GetItemsVo>> getItems(String cid) {
        List<GetItemsVo> list = comboanditemService.getItems(cid);
        return R.ok(list);
    }


    /**
     * 保存前判断是否重复
     *
     * @param jhysValue
     * @param zytjlbValue
     * @param id
     * @return
     */
    @GetMapping("/isReport")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "保存前判断是否重复", notes = "根据接害因素和职业体检类别判断是否存在重复,存在重复不能进行保存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jhysValue", value = "接害因素数值,用于判断是否存在重复"),
            @ApiImplicitParam(name = "zytjlbValue", value = "职业体检类别数值,用于判断是否存在重复"),
            @ApiImplicitParam(name = "id", value = "最小套餐id")
    })
    public R isReport(String jhysValue, String zytjlbValue, String id) {
        String state = createmealService.isReport(jhysValue, zytjlbValue, id);
        return R.ok(state);
    }


    /**
     * 返回符合条件的收费项目数据
     *
     * @param pageParamSimple
     * @param param
     * @return
     */
    @GetMapping("/getShowData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "返回符合条件的收费项目数据", notes = "返回符合条件的收费项目数据")
    public R getShowData(PageParamSimple pageParamSimple, GetShowParam param) {
        PageParam<GetShowVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<GetShowVo> iPage = itemsService.getShowData(page, param);
        return R.ok(iPage);
    }


    /**
     * 获取最小套餐分类
     *
     * @param pageParamSimple
     * @return
     */
    @GetMapping("/getTypeList")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取最小套餐分类", notes = "获取最小套餐分类")
    public R<IPage<TypeListVo>> getTypeList(PageParamSimple pageParamSimple) {
        PageParam<TypeListVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<TypeListVo> iPage = createcomboTypeService.getTypeList(page);
        return R.ok(iPage);
    }


    /**
     * 职业性问诊-获取接害因素
     *
     * @param pageParamSimple
     * @param key
     * @return
     */
    @GetMapping("/getJhysData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取接害因素", notes = "获取接害因素")
    @ApiImplicitParam(name = "key", value = "输入码")
    public R<IPage<Harm>> getJhysData(PageParamSimple pageParamSimple, String key) {
        PageParam<Harm> page = mapperFacade.map(pageParamSimple, PageParam.class);
        //获取接害因素
        if (com.center.medical.common.utils.StringUtils.isNotEmpty(key)) {
            key = key.trim().toUpperCase();
            Pattern p = Pattern.compile("[A-Z]+");
            Matcher m = p.matcher(key);
            QueryWrapper<Harm> con = new QueryWrapper<>();
            con.like("input_code", key);
            while (m.find()) {
                con = con.or().like("input_code", "%" + m.group() + "%");
            }
            //若搜索值不为空
            PageParam<Harm> page1 = harmService.page(page, con.orderByDesc("createdate")
                    .eq("is_delete", 0).eq("lv", 2));
            return R.ok(page1);
        } else {
            PageParam<Harm> page1 = harmService.page(page, new QueryWrapper<Harm>().orderByDesc("createdate").eq("is_delete", 0).eq("lv", 2));
            //不搜索
            return R.ok(page1);
        }
    }


    /**
     * 获取输入码
     *
     * @param inputName
     * @return
     */
    @GetMapping("/getBlurData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取输入码", notes = "根据体检套餐名称获取体检套餐输入码")
    @ApiImplicitParam(name = "inputName", value = "体检套餐名称")
    public R getBlurData(String inputName) {
        String inputCode = ToolUtil.getHanziPinyinHeadChar(inputName).toUpperCase();
        return R.ok(inputCode);
    }



    /**
     * 仿制页面详情
     *
     * @param id
     * @return
     */
    @GetMapping("/repeat")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "仿制页面详情", notes = "仿制页面详情")
    @ApiImplicitParam(name = "id", value = "id")
    public R repeat(String id) {
        Map<String,Object> map = new HashMap<>();
        Createcombo createCombo = createcomboService.getInfoById(id);
        //接害因素id
        String jhysIds = createCombo.getJhys();
        if(jhysIds != null){
            String [] ids = jhysIds.split(",");
            StringBuilder builder = new StringBuilder();
            for(String jhysId:ids){
                if(StringUtils.isNotEmpty(jhysId)){
                    Harm harm = harmService.getInfoById(jhysId);
                    if(harm!=null){
                        if(builder.length()==0){
                            builder.append(harm.getHarmName());
                        }else{
                            builder.append(","+harm.getHarmName());
                        }
                    }
                }
            }
            String jhys = builder.toString();
            map.put("jhys",jhys);
        }
        map.put("syxb", createCombo.getSyxb());
        map.put("zytjlb", createCombo.getZytjlb());
        return R.ok(map);
    }


    /**
     * 批量添加分中心
     * @param fzxId
     * @return
     */
    @PutMapping("/addFzx")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "批量添加分中心", notes = "在体检基础套餐维护,批量添加分中心")
    @ApiImplicitParam(name = "fzxId", value = "分中心id")
    public R addFzx(@RequestParam String fzxId) {
        Boolean b = createcomboService.addFzx(fzxId);
        return R.toResult(b);
    }



    /**
     * 批量删除分中心
     * @param fzxId
     * @return
     */
    @PutMapping("/deleteFzx")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "批量删除分中心", notes = "在体检基础套餐维护,批量删除分中心")
    @ApiImplicitParam(name = "fzxId", value = "分中心id")
    public R deleteFzx(@RequestParam String fzxId) {
        Boolean b = createcomboService.deleteFzx(fzxId);
        return R.toResult(b);
    }





    /**
     * 添加项目成本价合计
     *
     */
    @PostMapping("/addTotalCostprice")
    @ApiOperation(value = "添加项目成本价合计", notes = "添加项目成本价合计")
    public R<Boolean> addTotalCostprice() {
        Boolean b = createcomboService.addTotalCostprice();
        return R.ok(b);
    }

}

