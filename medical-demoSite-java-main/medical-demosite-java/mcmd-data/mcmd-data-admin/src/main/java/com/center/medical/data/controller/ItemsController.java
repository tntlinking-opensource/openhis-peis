package com.center.medical.data.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Fylx;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.exception.GlobalException;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.data.bean.model.Items;
import com.center.medical.data.bean.model.ItemsAndFzx;
import com.center.medical.data.bean.model.ItemsBarcodeClass;
import com.center.medical.data.bean.model.Printtype;
import com.center.medical.data.bean.param.ItemsParam;
import com.center.medical.data.bean.vo.AllItemsVo;
import com.center.medical.data.bean.vo.ItemsDataVO;
import com.center.medical.data.bean.vo.ItemsExportAllVo;
import com.center.medical.data.bean.vo.ListDatasVo;
import com.center.medical.data.service.InspectChargeService;
import com.center.medical.data.service.ItemsAndFzxService;
import com.center.medical.data.service.ItemsBarcodeClassService;
import com.center.medical.data.service.ItemsService;
import com.center.medical.service.AttachmentService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 收费项目设置(Items)表控制层
 *
 * @author 路飞船长
 * @since 2022-11-18 10:28:05
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "收费项目设置")
@RequestMapping("items")
public class ItemsController extends BaseController {
    /**
     * 服务对象
     */
    private final ItemsService itemsService;
    private final MapperFacade mapperFacade;
    private final ItemsAndFzxService itemsAndFzxService;
    private final ItemsBarcodeClassService itemsBarcodeClassService;
    private final InspectChargeService inspectChargeService;
    private final AttachmentService attachmentService;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询JC收费项目表", position = 1)
    public R<IPage<Items>> getPage(PageParamSimple pageParamSimple, ItemsParam param) {
        PageParam<Items> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.itemsService.getPage(page, param));
    }

    /**
     * 根据条件查询全部收费项目数据
     *
     * @param param 查询条件
     * @return 所有数据
     */
    @GetMapping("/getItemsData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "根据条件查询全部", notes = "根据条件查询全部收费项目数据", position = 2)
    public R<List<ItemsDataVO>> getItemsData(ItemsParam param) {
        return R.ok(this.itemsService.getItemsData(param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查JC收费项目表详情", position = 3)
    public R<Items> selectOne(@PathVariable String id) {
        return R.ok(this.itemsService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param items 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加JC收费项目表", position = 4)
    @Log(title = "JC收费项目表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"items.id"})
    public R insert(@RequestBody Items items) {
        return R.toResult(this.itemsService.saOrUp(items));
    }

    /**
     * 修改数据
     *
     * @param items 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新JC收费项目表", position = 5)
    @Log(title = "JC收费项目表", businessType = BusinessType.UPDATE)
    public R update(@RequestBody Items items) {
        return R.toResult(this.itemsService.saOrUp(items));
    }

    /**
     * 删除数据
     *
     * @param ids 主键结合
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除JC收费项目表", position = 6)
    @Log(title = "JC收费项目表", businessType = BusinessType.DELETE)
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.itemsService.delete(ids));
    }

    /**
     * 获取收费项目图片
     */
    @PostMapping("/getPicture/{itemsId}")
    //@PreAuthorize("@ss.hasPermi('::upload')")
    @ApiOperation(value = "获取图片", notes = "获取收费项目图片", position = 7)
    public R<String> upload(@PathVariable("itemsId") String itemsId) throws Exception {
        //获取项目信息
        Items items = itemsService.getById(itemsId);
        if (Objects.nonNull(items)) {
            return R.ok(items.getInputcodee());
        }
        throw new ServiceException("收费项目不存在！");
    }


    /**
     * 上传图片
     *
     * @param file
     * @param itemsId 绑定的收费项目ID
     * @return
     * @throws Exception
     */
    @PostMapping("/upload")
    //@PreAuthorize("@ss.hasPermi('::upload')")
    @Log(title = "上传图片", businessType = BusinessType.UPDATE)
    @ApiOperation(value = "上传图片", notes = "上传图片", position = 7)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "上传的图片文件"),
            @ApiImplicitParam(name = "itemsId", value = "绑定的收费项目ID")
    })
    public R upload(@RequestParam("file") MultipartFile file, @RequestParam("itemsId") String itemsId) throws Exception {
        return R.ok(itemsService.upload(file, itemsId));

    }

    /**
     * 删除图片
     */
    @PostMapping("/removeImg")
    //@PreAuthorize("@ss.hasPermi('::upload')")
    @Log(title = "删除图片", businessType = BusinessType.UPDATE)
    @ApiOperation(value = "删除图片", notes = "删除图片", position = 8)
    public R<String> removeImg(@RequestParam("itemsId") String itemsId) throws Exception {
        //获取项目信息
        Items items = itemsService.getById(itemsId);
        if (Objects.nonNull(items)) {
            if (StringUtils.isNotBlank(items.getInputcodee())) {
                //删除原图片
                attachmentService.deleteFile(items.getInputcodee());
                //更新项目的信息
                itemsService.update(new UpdateWrapper<Items>().set("inputcodee", null).eq("id", items.getId()));
                return R.ok("删除成功");
            } else {
                throw new ServiceException("图片已被删除，请勿重复操作！");
            }
        }
        throw new ServiceException("删除失败，收费项目已不存在!");
    }

    /**
     * 手动计算更新按钮:更新收费项目检查次数，在收费项目表中有个字段，有多少人检了此收费项目，同步的时候，更新收费项目表的字段。手动点一下更新就行，这块功能之前没做按钮
     *
     * @return 更新结果
     */
    @GetMapping("/updateJccs")
    //@PreAuthorize("@ss.hasPermi('::updateJccs')")
    @ApiOperation(value = "更新按钮", notes = "手动计算更新按钮", position = 9)
    public R updateJccs() {
        return R.ok(this.itemsService.updateJccs());
    }

    /**
     * 设置为/取消APP项目
     *
     * @param ids   操作的对象id集合
     * @param state 操作标识：0取消推荐 1设置为推荐
     * @return
     */
    @PostMapping("/setApp")
    @ApiOperation(value = "设置为/取消APP项目", notes = "设置为/取消APP项目", position = 10)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "要操作的项目id集合，多个以英文逗号隔开"),
            @ApiImplicitParam(name = "state", value = "操作标识：0取消推荐 1设置为推荐")
    })
    public R<Boolean> setApp(@RequestParam(name = "ids", required = true) List<String> ids,
                             @RequestParam(name = "state", required = true) Integer state) {
        if (CollectionUtil.isEmpty(ids)) {
            throw new ServiceException("请选择要操作的项目对象");
        }
        Items item = new Items();
        item.setExamfeeitemCodex(String.valueOf(state));
        item.setModifydate(new Date());
        return R.ok(itemsService.update(item, new LambdaUpdateWrapper<Items>().in(Items::getId, ids)));
    }


    /**
     * 禁用/反禁用
     *
     * @param ids 要操作的项目id集合
     * @return
     */
    @PostMapping("/setBan")
    @ApiOperation(value = "禁用/反禁用", notes = "禁用/反禁用", position = 11)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "要操作的项目id集合，多个以英文逗号隔开"),
            @ApiImplicitParam(name = "state", value = "操作标识：0反禁用 1禁用")
    })
    public R<Boolean> setBan(@RequestParam(name = "ids", required = true) List<String> ids, @RequestParam(name = "state", required = true) Integer state) {
        if (CollectionUtil.isEmpty(ids)) {
            throw new ServiceException("请选择要操作的对象");
        }
        if (!SecurityUtils.hasRole(RoleAuthName.DATA_MANAGE)) {
            String cId = SecurityUtils.getCId();
            if (itemsAndFzxService.count(new LambdaQueryWrapper<ItemsAndFzx>()
                    .in(ItemsAndFzx::getItemsId, ids).ne(ItemsAndFzx::getFzxId, cId)) > 0) {
                throw new GlobalException("操作失败，您只能" + (state == 1 ? "禁用" : "反禁用") + "只属于您自己中心的项目");
            }
        }
        Items item = new Items();
        item.setIsBan(state);
        item.setModifydate(new Date());
        return R.ok(itemsService.update(item, new LambdaUpdateWrapper<Items>().in(Items::getId, ids)));
    }


    /**
     * 获取销售打印分类
     *
     * @return
     */
    @GetMapping("/getXsdyfl")
    //@PreAuthorize("@ss.hasPermi('::updateJccs')")
    @ApiOperation(value = "获取销售打印分类", notes = "获取销售打印分类", position = 12)
    @ApiImplicitParam(name = "key", value = "输入码")
    public R getXsdyfl(String key) {
        List<Printtype> list = itemsService.getXsdyfl(key);
        return R.ok(list);
    }


    /**
     * 获取费用类型
     *
     * @param key
     * @return
     */
    @GetMapping("/getFylx")
    //@PreAuthorize("@ss.hasPermi('::updateJccs')")
    @ApiOperation(value = "获取费用类型", notes = "获取费用类型", position = 13)
    @ApiImplicitParam(name = "key", value = "输入码")
    public R getFylx(String key) {
        List<Fylx> list = itemsService.getFylx(key);
        return R.ok(list);
    }


    /**
     * 获取收费项目条码打印分类
     *
     * @return
     */
    @GetMapping("/getItemsBarcodeClass")
    //@PreAuthorize("@ss.hasPermi('::updateJccs')")
    @ApiOperation(value = "获取收费项目条码打印分类", notes = "获取收费项目条码打印分类", position = 14)
    public R<List<ItemsBarcodeClass>> getItemsBarcodeClass() {
        List<ItemsBarcodeClass> list = itemsBarcodeClassService.list();
        return R.ok(list);
    }


    /**
     * 获取所有收费项目
     *
     * @param key
     * @return
     */
    @GetMapping("/getAllItemsData")
    //@PreAuthorize("@ss.hasPermi('::updateJccs')")
    @ApiOperation(value = "获取所有收费项目", notes = "获取所有收费项目", position = 15)
    @ApiImplicitParam(name = "key", value = "输入码")
    public R<IPage<AllItemsVo>> getAllItemsData(PageParamSimple pageParamSimple, String key) {
        PageParam<AllItemsVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<AllItemsVo> list = itemsService.getAllItemsData(page, key);
        return R.ok(list);
    }


    /**
     * 编辑收费项目-右下表格数据
     *
     * @param pageParamSimple
     * @param id
     * @return
     */
    @GetMapping("/getListDatas")
    //@PreAuthorize("@ss.hasPermi('::updateJccs')")
    @ApiOperation(value = "编辑收费项目-右下表格数据", notes = "编辑收费项目-右下表格数据", position = 16)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "检查项目ID"),
            @ApiImplicitParam(name = "type", value = "类型 1：list页字表 0 编辑页，没用")
    })
    public R<IPage<ListDatasVo>> getListDatas(PageParamSimple pageParamSimple, String id, String type) {
        PageParam<ListDatasVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<ListDatasVo> iPage = inspectChargeService.getAllItemsData(page, id, type);
        return R.ok(iPage);
    }


    /**
     * 收费项目分类-收费项目维护
     *
     * @param param
     * @return
     */
    @GetMapping("/getAppTypeData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "查询APP分类", notes = "查询APP分类", position = 17)
    public R<List<ItemsDataVO>> getAppTypeData(ItemsParam param) {
        // TODO: wait 缺少 app_type 表
//        List<AppType> list;
//        if(StringUtils.isNotEmpty(key)){
//            list=autocompleteDataService.getOrderList(AppType.class, Order.asc("typeSort")
//                    , Restrictions.like("inputCode", key.trim().toUpperCase(),MatchMode.ANYWHERE));
//        }else{
//            list=autocompleteDataService.getOrderList(AppType.class, Order.asc("typeSort"));
//        }
//        return ajax(Status.success,JSONUtil.toJsonStr(list));
        return R.ok();
    }


    /**
     * 导出收费项目设置
     *
     * @param response
     * @param param
     */
    @PostMapping("/export")
    @ApiOperation(value = "导出", notes = "导出收费项目设置")
    @Log(title = "加项查询", businessType = BusinessType.EXPORT)
    public void export(HttpServletResponse response, ItemsParam param) {
        List<Items> list = itemsService.getExportData(param);
        ExcelUtil<Items> util = new ExcelUtil<Items>(Items.class);
        util.exportExcel(response, list, "收费项目设置");
    }


    /**
     * 导入收费项目设置
     */
    @PostMapping("/importItems")
    @ApiOperation(value = "导入收费项目设置", notes = "导入收费项目设置")
    @Log(title = "收费项目设置-导入收费项目设置", businessType = BusinessType.IMPORT)
    public R importItems(MultipartFile file) throws Exception {
        //判断文件后缀
        String fileName = file.getOriginalFilename();
        String endSuffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        if (!endSuffix.toLowerCase().endsWith("xls") && !endSuffix.toLowerCase().endsWith("xlsx")) {
            throw new GlobalException("请选择正确的文件类型，必须是以.xls或.xlsx结尾！");
        }
        ExcelUtil<Items> util = new ExcelUtil<Items>(Items.class);
        List<Items> list = util.importExcel(file.getInputStream());
        String message = itemsService.importItems(list);
        return R.ok(message);
    }




    /**
     * 导出页面内容
     *
     * @param response
     * @param param
     */
    @PostMapping("/exportAll")
    @ApiOperation(value = "导出页面内容", notes = "导出页面内容")
    @Log(title = "收费项目设置", businessType = BusinessType.EXPORT)
    public void exportAll(HttpServletResponse response, ItemsParam param) {
        List<ItemsExportAllVo> list = itemsService.getExportAllData(param);
        ExcelUtil<ItemsExportAllVo> util = new ExcelUtil<ItemsExportAllVo>(ItemsExportAllVo.class);
        util.exportExcel(response, list, "收费项目设置");
    }
}

