package com.center.medical.sellcrm.controller.sell;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.annotation.RepeatSubmit;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.exception.GlobalException;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.data.bean.model.Harm;
import com.center.medical.data.bean.param.GetSfxmParam;
import com.center.medical.data.bean.param.ItemsParam;
import com.center.medical.data.bean.vo.*;
import com.center.medical.data.service.BasexamltemService;
import com.center.medical.data.service.HarmService;
import com.center.medical.data.service.ItemsService;
import com.center.medical.sellcrm.bean.dto.CreatemealExportXyDto;
import com.center.medical.sellcrm.bean.model.Comboanditem;
import com.center.medical.sellcrm.bean.model.Createmeal;
import com.center.medical.sellcrm.bean.model.Sellcustomer;
import com.center.medical.sellcrm.bean.param.*;
import com.center.medical.sellcrm.bean.vo.DataAddMealVo;
import com.center.medical.sellcrm.bean.vo.SmallItemsVo;
import com.center.medical.sellcrm.service.*;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysUserService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 普通套餐表(Createmeal)表控制层
 *
 * @author 路飞船长
 * @since 2022-11-15 10:47:37
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@ApiSort(value = 10)
@Api(tags = "销售管理-套餐管理")
@RequestMapping("sell/createmeal")
public class CreatemealController extends BaseController {

    /**
     * 服务对象
     */
    private final CreatemealService createmealService;
    private final MapperFacade mapperFacade;
    private final SellcustomerService sellcustomerService;
    private final ISysBranchService iSysBranchService;
    private final HarmService harmService;
    private final ItemsService itemsService;
    private final ISysUserService iSysUserService;
    private final BasexamltemService basexamltemService;
    private final CreatecomboService createcomboService;
    private final ComboanditemService comboanditemService;
    private final MealanditemService mealanditemService;
    private final CreatecomboTypeService createcomboTypeService;

    //TODO 权限设置

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/getPage")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询普通套餐表")
    public R<IPage<Createmeal>> getPage(PageParamSimple pageParamSimple, CreatemealParam param) {
        PageParam<Createmeal> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.createmealService.getPage(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查普通套餐表详情")
    public R<Createmeal> selectOne(@PathVariable String id) {
        Createmeal info = this.createmealService.getInfoById(id);

        //获取接害因素列表
        if (ObjectUtils.isNotEmpty(info) && StringUtils.isNotBlank(info.getJhys())) {
            List<String> jhysids = Arrays.stream(StringUtils.split(info.getJhys(), ",")).collect(Collectors.toList());
            List<Harm> harms = harmService.listByIds(jhysids);
            info.setHarmList(harms);
            //接害因素拼接
            StringBuilder JhysName = new StringBuilder();
            for (Harm harm : harms) {
                JhysName.append(harm.getHarmName());
                JhysName.append(",");
            }
            info.setJhysName(JhysName.substring(0, JhysName.length()-1));
        }




        //获取客户单位名称
        if (StringUtils.isBlank(info.getKhdwmc()) && StringUtils.isNotBlank(info.getKhdwmcid())) {
            Sellcustomer sc = sellcustomerService.getById(info.getKhdwmcid());
            if (Objects.nonNull(sc)) {
                info.setKhdwmc(sc.getKhdwmc());
            }
        }

        //获取分中心名称
        if (StringUtils.isNotBlank(info.getFzxid())) {
            List<String> fzxids = Arrays.stream(StringUtils.split(info.getFzxid(), ",")).collect(Collectors.toList());
            List<SysBranch> branches = iSysBranchService.list(new LambdaQueryWrapper<SysBranch>().in(SysBranch::getBranchId,  fzxids));
            for (SysBranch branch : branches) {
                info.setFzxName(StringUtils.isNotBlank(info.getFzxName()) ? info.getFzxName() + "," + branch.getFzx() : branch.getFzx());
            }
        }

        return R.ok(info);
    }

    /**
     * 新增数据
     *
     * @param param 实体对象
     * @return 新增结果
     */
    @PostMapping("/saveOrUpdate")
    @RepeatSubmit(interval = 3000, message = "正在保存中，请稍等...")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加或修改", notes = "添加或修改")
    @Log(title = "普通套餐表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"createmeal.id"})
    public R insert(@RequestBody MealSaOrUpParam param) {
        return R.toResult(this.createmealService.saOrUp(param));
    }


    /**
     * 删除数据
     *
     * @param ids 主键结合
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除普通套餐表")
    @Log(title = "普通套餐表", businessType = BusinessType.DELETE)
    public R delete(@PathVariable List<String> ids) {
        Createmeal createmeal = new Createmeal();
        createmeal.setIsDelete(1);
        createmeal.setModifydate(new Date());
        return R.toResult(this.createmealService.update(createmeal, new LambdaUpdateWrapper<Createmeal>()
                .in(Createmeal::getId, ids)));
    }

    /**
     * 分页查询收费数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/items/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询收费数据", notes = "根据条件分页查询收费数据")
    public R<IPage<ItemsVo>> getItemPage(PageParamSimple pageParamSimple, ItemsParam param) {
        PageParam<ItemsVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.itemsService.getItemVoPage(page, param));
    }


    /**
     * list页面双击获取收费项目信息
     *
     * @param tcId 套餐ID
     * @return 所有数据
     */
    @GetMapping("/getItemsData/{tcId}")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "双击获取收费项目信息", notes = "list页面双击根据套餐ID获取收费项目信息")
    @ApiImplicitParam(name = "tcId", value = "套餐ID")
    public R<List<ItemsDataVO>> getItemsData(@PathVariable(value = "tcId") String tcId) {
        return R.ok(itemsService.getItemsByTcId(tcId));
    }


    /**
     * 编辑时收费项目右边显示
     *
     * @param tcId 套餐ID
     * @return 所有数据
     */
    @GetMapping("/getSfxmsData/{tcId}")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "编辑时收费项目右边显示", notes = "list页面双击根据套餐ID获取收费项目信息")
    @ApiImplicitParam(name = "tcId", value = "套餐ID")
    public R<List<Map>> getSfxmsData(@PathVariable(value = "tcId") String tcId) {
        return R.ok(mealanditemService.getSfxmsData(tcId));
    }

    /**
     * 判断折扣是否低于领导折扣
     *
     * @return
     */
    @GetMapping("/isLower/{discount}")
    @ApiOperation(value = "判断折扣是否低于领导折扣", notes = "判断折扣是否低于领导折扣")
    @ApiImplicitParam(name = "discount", value = "当前折扣")
    public R<Boolean> isLower(@PathVariable Double discount) {
        //当前用户是否为领导
        Boolean isLeader = SecurityUtils.isLeader();
        //假如是领导,没有上下限,不过要在0-10之间
        if (!isLeader) {
            //不是领导,获取领导折扣
            SysUser sysUser = iSysUserService.selectUserById(SecurityUtils.getUserId());

            if (Objects.nonNull(sysUser) && discount < sysUser.getLdiscount()) {
                R.ok(Boolean.TRUE);
            }
        }
        return R.ok(Boolean.FALSE);
    }

    /**
     * 验证当前输入的套餐折扣是否小于之前设定的折扣下限
     *
     * @return
     */
    @GetMapping("/onValidaZk/{discount}")
    @ApiOperation(value = "验证套餐折扣小于之前设定的折扣下限", notes = "验证当前输入的套餐折扣是否小于之前设定的折扣下限")
    @ApiImplicitParam(name = "discount", value = "当前折扣")
    public R<Boolean> onValidaZk(@PathVariable Double discount) {
        //当前用户是否为领导
        Boolean isleader = SecurityUtils.isLeader();
        //假如是领导,没有上下限,不过要在0-10之间
        if (!isleader) {
            //不是领导,获取领导折扣
            SysUser sysUser = iSysUserService.selectUserById(SecurityUtils.getUserId());
            if (Objects.nonNull(sysUser) && discount < sysUser.getSdiscount()) {
                R.ok(Boolean.TRUE, "您能打的折扣范围为【" + sysUser.getSdiscount() + "-10折】");
            }
        }
        return R.ok(Boolean.FALSE);
    }

    /**
     * 复制套餐
     *
     * @param param 复制属性
     * @return 复制结果
     */
    @PostMapping("/repeat")
//    @PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "复制套餐", notes = "复制套餐")
    @Log(title = "普通套餐表", businessType = BusinessType.INSERT)
    public R repeat(@RequestBody TcCopyParam param) {
        //进行仿制
        String userNo = SecurityUtils.getUserNo();
        return R.toResult(createmealService.repeat(param, userNo));
    }

    /**
     * 检查添加的收费项目下是否有检查项目重复,给予提示
     *
     * @return
     */
    @GetMapping("/isJcxmReport")
    @ApiOperation(value = "检查添加的收费项目下是否有检查项目重复", notes = "检查添加的收费项目下是否有检查项目重复,给予提示")
    @ApiImplicitParam(name = "gridId", value = "原有的收费项目id")
    public R<String> isJcxmReport(@RequestParam List<String> gridId) {
        String text = createmealService.compareItemsToExam(gridId);
        return R.ok(text);
    }

    /**
     * 判断套餐能否编辑
     *
     * @param tcId
     * @return
     */
    @GetMapping("/isEdit/{tcId}")
    @ApiOperation(value = "判断套餐能否编辑", notes = "判断套餐能否编辑，返回值为true则可编辑，false则不可")
    @ApiImplicitParam(name = "tcId", value = "判断的套餐ID")
    public R<String> isEdit(@PathVariable String tcId) {
        return R.ok(createmealService.isEdit(tcId));
    }

    /**
     * 判断套餐能否删除
     *
     * @param tcId
     * @return
     */
    @GetMapping("/isRemove")
    @ApiOperation(value = "判断套餐能否删除", notes = "判断套餐能否删除，返回值为true则可删除，false则不可")
    @ApiImplicitParam(name = "tcId", value = "判断的套餐ID")
    public R<String> isRemove(@RequestParam List<String> tcId) {
        return R.ok(createmealService.isRemove(tcId));
    }

    /**
     * 根据接害因素和职业体检类别获取关联的收费项目,将数据返回
     *
     * @return
     */
    @PostMapping("/getPpZxtcData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "根据接害因素和职业体检类别获取关联的收费项目", notes = "根据接害因素和职业体检类别获取关联的收费项目")
    public R<List<ItemsVo>> getPpZxtcData(@RequestBody CreatecomboParam1 param) {
        return R.ok(this.createmealService.getPpZxtcData(param));
    }

    /**
     * 修改套餐状态位
     *
     * @param discountId 操作的对象主键id
     * @return
     */
    @PutMapping("/updateZkw/{discountId}")
    @ApiOperation(value = "修改套餐状态位", notes = "修改套餐状态位")
    @ApiImplicitParam(name = "discountId", value = "套餐主键id")
    public R<Boolean> updateZkw(@PathVariable(value = "discountId") String discountId) {
        if (StringUtils.isBlank(discountId)) {
            throw new ServiceException("请选择要操作的对象");
        }
        Createmeal createmeal = createmealService.getOne(new LambdaQueryWrapper<Createmeal>()
                .eq(Createmeal::getId, discountId).eq(Createmeal::getIsDelete, 0));
        if (Objects.nonNull(createmeal)) {
            createmeal.setZkztw(1);
            createmeal.setModifydate(new Date());
            return R.ok(createmealService.updateById(createmeal));
        } else {
            throw new ServiceException("修改失败，没有找到ID为【" + discountId + "】的套餐");
        }
    }

    /**
     * 根据状态位判断套餐折扣
     *
     * @param discount 当前折扣
     * @param tczkId   套餐ID
     * @return
     */
    @GetMapping("/onZk")
    @ApiOperation(value = "根据状态位判断套餐折扣", notes = "根据状态位判断套餐折扣")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "discount", value = "当前折扣"),
            @ApiImplicitParam(name = "tczkId", value = "套餐ID")
    })
    public R<Boolean> onZk(@RequestParam("discount") Double discount, @RequestParam("tczkId") String tczkId) {
        return createmealService.onZk(discount, tczkId);
    }

    /**
     * 判断是否必检
     *
     * @return
     */
    @PostMapping("/getSfbj")
    @ApiOperation(value = "判断是否必检", notes = "判断是否必检，返回值为true则是必检，false则否")
    public R<List<Integer>> getSfbj(@RequestBody CreatecomboParam1 param) {
        return R.ok(createmealService.getSfbj(param));
    }

    /**
     * 禁用/反禁用套餐
     *
     * @param ids 操作的对象主键id集合
     * @return
     */
    @PutMapping("/setBan")
    @ApiOperation(value = "禁用/反禁用套餐", notes = "禁用/反禁用套餐")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "要操作的对象主键id集合，多个以英文逗号隔开"),
            @ApiImplicitParam(name = "state", value = "操作标识：0反禁用 1禁用")
    })
    @Log(title = "禁用/反禁用套餐", businessType = BusinessType.UPDATE)
    public R<Boolean> setBan(@RequestParam(name = "ids", required = true) List<String> ids, @RequestParam(name = "state", required = true) Integer state) {
        if (CollectionUtil.isEmpty(ids)) {
            throw new ServiceException("请选择要操作的对象");
        }
        return R.ok(createmealService.setBan(ids, state));
    }


    /**
     * 设置平安ID
     *
     * @param id       操作的套餐id
     * @param pinganId 平安ID
     * @return
     */
    @PostMapping("/setPinganId")
    @ApiOperation(value = "设置平安ID", notes = "设置平安ID")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "操作的套餐id"),
            @ApiImplicitParam(name = "pinganId", value = "平安ID")
    })
    public R<Boolean> setPinganId(@RequestParam(name = "id", required = true) String id, @RequestParam(name = "pinganId", required = true) String pinganId) {
        if (StringUtils.isBlank(id) && StringUtils.isBlank(pinganId)) {
            throw new ServiceException("套餐id和平安id都不能为空");
        }
        return R.ok(createmealService.setPinganId(id, pinganId));
    }


    /**
     * 新增套餐-接害因素
     *
     * @param pageParamSimple
     * @param key
     * @return
     */
    @GetMapping("/getJhysDataByFzx")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "新增套餐-接害因素", notes = "创建套餐时，只能选择当前用户分中心的危害因素")
    @ApiImplicitParam(name = "key", value = "搜索key")
    public R<IPage<JhysDataVo>> getJhysDataByFzx(PageParamSimple pageParamSimple, String key) {
        PageParam<JhysDataVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<JhysDataVo> iPage = harmService.getJhysDataByFzx(page, key);
        return R.ok(iPage);
    }


    /**
     * 获取右侧表格子表格数据
     *
     * @param pageParamSimple
     * @param id
     * @return
     */
    @GetMapping("/getExamsByItemId")
    @ApiOperation(value = "获取右侧表格子表格数据", notes = "获取右侧表格子表格数据")
    @ApiImplicitParam(name = "id", value = "id")
    public R<IPage<ExamsByItemVo>> getExamsByItemId(PageParamSimple pageParamSimple, String id) {
        PageParam<ExamsByItemVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<ExamsByItemVo> iPage = basexamltemService.getExamsByItemId(page, id);
        return R.ok(iPage);
    }


    /**
     * 验证当前输入的套餐折扣是否小于之前设定的折扣下限
     *
     * @return
     */
    @GetMapping("/onValidaZkLeader")
    @ApiOperation(value = "验证当前输入的套餐折扣是否小于之前设定的折扣下限", notes = "验证当前输入的套餐折扣是否小于之前设定的折扣下限")
    @ApiImplicitParam(name = "discount", value = "折扣")
    public R onValidaZkLeader(Double discount) {
        String state = "error";
        //销售折扣
        SysUser sysUser = iSysUserService.getUserByNo(SecurityUtils.getUserNo());
        Double sDiscount = sysUser.getSdiscount();
        if (discount < sDiscount) {
            state = "您能打的折扣范围为【<font color='red'>" + sDiscount + "-1.0折</font>】";
        }
        return R.ok(state);
    }

    /**
     * 新增套餐-收费项目列表
     *
     * @param pageParamSimple
     * @param param
     * @return
     */
    @GetMapping("/getSfxm")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "新增套餐-收费项目列表", notes = "新增套餐获取基础数据的收费项目")
    public R<IPage<GetSfxmVo>> getSfxm(PageParamSimple pageParamSimple, GetSfxmParam param) {
        PageParam<GetSfxmVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<GetSfxmVo> iPage = itemsService.getSfxm(page, param);
        return R.ok(iPage);
    }


    /**
     * 新增套餐-体检套餐列表
     *
     * @param pageParamSimple
     * @param param
     * @return
     */
    @GetMapping("/getListDataAddMeal")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "新增套餐-体检套餐列表", notes = "新增套餐-体检套餐列表")
    public R<IPage<DataAddMealVo>> getListDataAddMeal(PageParamSimple pageParamSimple, DataAddMealParam param) {
        PageParam<DataAddMealVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<DataAddMealVo> iPage = createcomboService.getListDataAddMeal(page, param);
        return R.ok(iPage);
    }


    /**
     * 获取普通套餐关联的收费项目
     *
     * @param ids
     * @return
     */
    @GetMapping("/getItemsDataAjax")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取普通套餐关联的收费项目", notes = "获取普通套餐关联的收费项目")
    @ApiImplicitParam(name = "ids", value = "套餐id集合")
    public R getItemsDataAjax(@RequestParam List<String> ids) {
        //获取关联数据
        List<Comboanditem> list = comboanditemService.list(new QueryWrapper<Comboanditem>()
                .in("tcid", ids).eq("is_delete", 0));
        List<Map> data = createmealService.getDatas(list);
        return R.ok(data);
    }


    /**
     * 获取和套餐关联的收费项目
     *
     * @param pageParamSimple
     * @param test
     * @return
     */
    @GetMapping("/getSmallItems")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取和套餐关联的收费项目，返回页数", notes = "获取和套餐关联的收费项目，返回页数")
    @ApiImplicitParam(name = "test", value = "套餐id")
    public R<IPage<SmallItemsVo>> getSmallItems(PageParamSimple pageParamSimple, String test) {
        PageParam<SmallItemsVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<SmallItemsVo> iPage = createcomboService.getSmallItems(page, test);
        return R.ok(iPage);
    }

    /**
     * 导出套餐
     *
     * @param response
     * @param tcIds    选择导出的套餐ID集合
     */
    @PostMapping("/export")
//    @PreAuthorize("@ss.hasPermi('::export')")
    @ApiOperation(value = "导出套餐", notes = "导出套餐")
    @ApiImplicitParam(name = "tcIds", value = "选择导出的套餐ID集合，以数组的形式提交，如：[1,2,3]")
    @Log(title = "套餐导出", businessType = BusinessType.EXPORT)
    public void export(HttpServletResponse response, @RequestParam List<String> tcIds) {
        createmealService.getExportData(response,tcIds);
    }

    /**
     * 导出协议套餐
     *
     * @param response
     * @param tcId     选择的套餐ID
     */
    @PostMapping("/exportXy")
//    @PreAuthorize("@ss.hasPermi('::export')")
    @ApiOperation(value = "导出协议套餐", notes = "导出协议套餐")
    @ApiImplicitParam(name = "tcId", value = "选择的套餐ID")
    @Log(title = "导出协议套餐", businessType = BusinessType.EXPORT)
    public void exportXy(HttpServletResponse response, @RequestParam List<String> tcId) {
        if (CollectionUtil.isEmpty(tcId)) {
            throw new GlobalException("请选择要导出的套餐！");
        }
        List<CreatemealExportXyDto> list = createmealService.getExportXyData(tcId,null);
        ExcelUtil<CreatemealExportXyDto> util = new ExcelUtil<>(CreatemealExportXyDto.class);
        util.exportExcel(response, list, "体检协议套餐套餐信息");
    }




    /**
     * 导出协议套餐
     *
     * @param fzx
     */
    @PostMapping("/addItemsFzx")
    @ApiOperation(value = "添加项目的分中心", notes = "添加项目的分中心")
    @ApiImplicitParam(name = "fzx", value = "分中心")
    public R<Boolean> addItemsFzx(String fzx) {
        Boolean b = createmealService.addItemsFzx(fzx);
        return R.ok(b);
    }





    /**
     * 添加项目成本价合计
     *
     */
    @PostMapping("/addTotalCostprice")
    @ApiOperation(value = "添加项目成本价合计", notes = "添加项目成本价合计")
    public R<Boolean> addTotalCostprice() {
        Boolean b = createmealService.addTotalCostprice();
        return R.ok(b);
    }

}

