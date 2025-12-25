package com.center.medical.sellcrm.controller.crm;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Branch;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.exception.GlobalException;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.data.bean.model.Sshy;
import com.center.medical.data.service.SshyService;
import com.center.medical.sellcrm.bean.model.Clientcommon;
import com.center.medical.sellcrm.bean.model.Receiveandsell;
import com.center.medical.sellcrm.bean.model.Sellcustomer;
import com.center.medical.sellcrm.bean.param.ClientcommonParam;
import com.center.medical.sellcrm.service.ClientcommonService;
import com.center.medical.sellcrm.service.ReceiveandsellService;
import com.center.medical.sellcrm.service.SellcustomerService;
import com.center.medical.system.bean.param.SysUserParam;
import com.center.medical.system.service.BranchService;
import com.center.medical.system.service.ISysDeptService;
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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 客户公共池(Clientcommon)表控制层
 *
 * @author ay
 * @since 2022-11-09 10:41:30
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@ApiSort(value = 1)
@Api(tags = "客户关系-公共池")
@RequestMapping("crm/clientcommon")
public class ClientcommonController extends BaseController {
    /**
     * 服务对象
     */
    private final ClientcommonService clientcommonService;
    private final ISysUserService sysUserService;
    private final ReceiveandsellService receiveandsellService;
    private final BranchService branchService;
    private final MapperFacade mapperFacade;
    private final SshyService sshyService;
    private final SellcustomerService sellcustomerService;
    private final ISysDeptService iSysDeptService;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页对象
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/list")
//    @PreAuthorize("@ss.hasPermi('customer:publicPool:list')")
    @ApiOperation(value = "客户列表", notes = "分页查询客户公共池表", position = 1)
    public R<IPage<Clientcommon>> selectAll(PageParamSimple pageParamSimple, ClientcommonParam param) {
        PageParam<Clientcommon> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.clientcommonService.getPage(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/view/{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查客户公共池表详情", position = 2)
    public R<Clientcommon> view(@PathVariable String id) {
        return R.ok(this.clientcommonService.getInfoById(id));
    }

    /**
     * 添加
     *
     * @param clientcommon 新增的对象信息
     * @return
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加客户公共池表", position = 3)
    @Log(title = "客户公共池表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"clientcommon.id"})
    public R insert(@RequestBody Clientcommon clientcommon) {
        return R.toResult(this.clientcommonService.saOrUp(clientcommon));
    }


    /**
     * 修改数据
     *
     * @param clientcommon 实体对象
     * @return 修改结果
     */
    @PutMapping("/edit")
    //@PreAuthorize("@ss.hasPermi('::update')")
    @ApiOperation(value = "更新", notes = "更新客户公共池表", position = 4)
    @Log(title = "客户公共池表", businessType = BusinessType.UPDATE)
    public R update(@RequestBody Clientcommon clientcommon) {
        return R.toResult(this.clientcommonService.saOrUp(clientcommon));
    }

    /**
     * 判断客户公共池中的客户单位名称是否重复
     *
     * @param clientName
     * @return
     */
    @GetMapping("/onBlur")
    @ApiOperation(value = "客户单位名称是否重复", notes = "判断客户公共池中的客户单位名称是否重复：data为true则表示重复，false则不重复", position = 4)
    @ApiImplicitParam(name = "clientName", value = "需要判断的客户单位名称,用于判断是否重复")
    public R<Boolean> onBlur(@RequestParam("clientName") String clientName) {
        clientName = clientName.trim();
        return R.ok(clientcommonService.onBlur(clientName));
    }


    /**
     * 获取所属行业列表
     *
     * @return
     */
    @GetMapping("/getHymcData")
    @ApiOperation(value = "所属行业列表", notes = "获取所属行业列表", position = 4)
    public R getHymcData() {
        List<Sshy> list = sshyService.list(new QueryWrapper<Sshy>().orderByDesc("createdate"));
        return R.ok(list);
    }

    /**
     * 删除数据
     *
     * @param ids 主键结合
     * @return 删除结果
     */
    @DeleteMapping("/remove/{ids}")
    //@PreAuthorize("@ss.hasPermi('::del')")
    @ApiOperation(value = "删除", notes = "删除客户公共池表", position = 5)
    @ApiImplicitParam(name = "ids", value = "要删除的对象id集合，多个以英文逗号（,）隔开")
    @Log(title = "客户公共池表", businessType = BusinessType.DELETE)
    public R remove(@PathVariable List<String> ids) {
        //假删，0:显示,1:不显示
        Clientcommon clientcommon = new Clientcommon();
        clientcommon.setIsDelete(1);
        clientcommon.setModifydate(new Date());
        return R.toResult(this.clientcommonService.update(clientcommon, new UpdateWrapper<Clientcommon>().in("id", ids)));
    }

    /**
     * 判断当前登录用户是否为领导(0:不是领导,1:是领导)
     *
     * @return
     */
    @GetMapping("/isLeader")
    @ApiOperation(value = "判断是否为领导", notes = "判断当前登录用户是否为领导", position = 6)
    public R<Boolean> isLeader() {
        //获取登录人下id
        Long userId = getUserId();
        SysUser sysUser = sysUserService.selectUserById(userId);
        //是否领导：0.否 1.是
        if ("0".equals(sysUser.getIsleader())) {
            //不为领导
            return R.ok(Boolean.FALSE);
        } else {
            //为领导
            return R.ok(Boolean.TRUE);
        }
    }


    /**
     * 判断是否领取过：同一个人不能重复领取同一条记录
     *
     * @param ids
     * @return
     */
    @GetMapping("/isReceive")
    @ApiOperation(value = "判断是否领取过", notes = "判断是否领取过：同一个人不能重复领取同一条记录", position = 7)
    @ApiImplicitParam(name = "ids", value = "领取客户id集合，多个以英文逗号（,）隔开")
    public R isReceive(@RequestParam("ids") List<String> ids) {
        StringBuffer msg = new StringBuffer("");
        Boolean flag = false;
        for (String id : ids) {
            //同一个人不能重复领取同一条记录
            Sellcustomer sellcustomer = sellcustomerService.getOne(new QueryWrapper<Sellcustomer>().eq("clientid", id).eq("is_delete", 0).eq("xsjlid", SecurityUtils.getUserNo()).ne("khzt", "2"));
            if (Objects.nonNull(sellcustomer)) {
                //之前领取过
                msg.append("★客户【" + sellcustomer.getKhdwmc() + "】您之前领取过,不能重复领取！");
                flag = true;
            }
        }
        return R.ok(flag, msg.toString());
    }

    /**
     * 进行客户领取
     *
     * @param ids 客户id列表
     * @return
     */
    @GetMapping("/receive")
    @ApiOperation(value = "进行客户领取", notes = "进行客户领取", position = 8)
    @ApiImplicitParam(name = "ids", value = "领取客户id集合，多个以英文逗号（,）隔开")
    public R<String> receive(@RequestParam("ids") List<String> ids) {
        return R.ok(clientcommonService.receive(ids));
    }

    /**
     * 获取领取人员的信息
     *
     * @param simplePage 分页参数
     * @param clientId
     * @return
     */
    @GetMapping("/getLqryData")
    @ApiOperation(value = "获取领取人员的信息", notes = "获取领取人员的信息", position = 9)
    @ApiImplicitParam(name = "clientId", value = "领取人员关联id")
    public R getLqryData(PageParamSimple simplePage, @RequestParam("clientId") String clientId) {
        PageParam<Receiveandsell> page = mapperFacade.map(simplePage, PageParam.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //获取相关联信息
        PageParam<Receiveandsell> pageParam = receiveandsellService.page(page, new QueryWrapper<Receiveandsell>()
                .eq("clientid", clientId).eq("is_delete", 0));
        List<Receiveandsell> data = pageParam.getRecords();
        for (Receiveandsell item : data) {
            SysUser sysUser = sysUserService.selectUserByUserNo(item.getXsjlid());
            item.setXs(sysUser.getUserName());
            if (ObjectUtils.isNotEmpty(sysUser.getCid())) {
                item.setFzx(branchService.getOne(new QueryWrapper<Branch>().eq("branch_id", sysUser.getCid())).getFzx());
            }
        }
        return R.ok(data);
    }


    /**
     * 获取本分中心下所有销售人员信息
     *
     * @param key
     * @return
     */
    @GetMapping("/getXsryData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "领导分配：获取销售人员列表", notes = "获取本分中心下所有销售人员信息", position = 10)
    @ApiImplicitParam(name = "key", value = "销售人员名称")
    public R getXsryData(String key) {
        //获取分中心id
        String fzxId = SecurityUtils.getCId();
        SysUserParam param = new SysUserParam();
        param.setCid(fzxId);
        param.setIsleader(0);
        if (StringUtils.isNotEmpty(key)) {
            param.setUserName(key);
        }
        SysDept d = iSysDeptService.selectDeptByName("销售部");
        if (ObjectUtils.isNotEmpty(d)) {
            param.setDeptId(d.getDeptId());
        } else {
            throw new ServiceException("销售部门不存在！");
        }
        return R.ok(this.sysUserService.getAllData(param));
    }


    /**
     * 判断领导分配的记录里面是否存在销售之前领取过的记录
     *
     * @param clientIds 公共池记录ID集合
     * @param xsIds     销售ID列表
     * @return
     */
    @PutMapping("/distribution")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "领导分配：判断是否存在领取过的记录", notes = "判断领导分配的记录里面是否存在销售之前领取过的记录：返回true则表示不存在销售之前领取过的记录", position = 11)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clientIds", value = "公共池记录ID集合"),
            @ApiImplicitParam(name = "xsIds", value = "销售ID列表")
    })
    public R distribution(@RequestParam("clientIds") List<String> clientIds, @RequestParam("xsIds") List<String> xsIds) {
        if (CollectionUtils.isEmpty(clientIds)) {
            throw new ServiceException("分中心ID不能为空!");
        }
        if (CollectionUtils.isEmpty(xsIds)) {
            throw new ServiceException("请选择销售经理!");
        }
        return R.ok(this.sellcustomerService.distribution(clientIds, xsIds));
    }


    /**
     * 判断领导选择的记录里面是否在之前给销售们分配过
     *
     * @param clientIds 公共池记录ID集合
     * @param xsIds     销售ID列表
     * @return
     */
    @PutMapping("/isAllocation")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "领导分配：判断是否存在已分配过的记录", notes = "判断领导选择的记录里面是否在之前给销售们分配过：返回true则表示之前没有给销售们分配过", position = 11)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clientIds", value = "公共池记录ID集合"),
            @ApiImplicitParam(name = "xsIds", value = "销售ID列表")
    })
    public R<Boolean> isAllocation(@RequestParam("clientIds") List<String> clientIds, @RequestParam("xsIds") List<String> xsIds) {
        if (CollectionUtils.isEmpty(clientIds)) {
            throw new ServiceException("分中心ID不能为空!");
        }
        if (CollectionUtils.isEmpty(xsIds)) {
            throw new ServiceException("请选择销售经理!");
        }
        return R.ok(this.sellcustomerService.isAllocation(clientIds, xsIds));
    }

    /**
     * 判断是否存在领取次数为3次的记录或被领导强制分配过的记录
     *
     * @param clientIds 客户id集合，多个以英文逗号（,）隔开
     * @return
     */
    @GetMapping("/isLqAndFp")
    @ApiOperation(value = "领导分配：判断领取次数为3或被强制分配", notes = "判断是否存在领取次数为3次的记录或被领导强制分配过的记录：data为true则表示存在，false则不存在", position = 11)
    @ApiImplicitParam(name = "clientIds", value = "客户id集合，多个以英文逗号（,）隔开")
    public R isLqAndFp(@RequestParam("clientIds") List<String> clientIds) {
        return clientcommonService.isLqAndFp(clientIds);
    }


    /**
     * 进行领导分配
     *
     * @param clientIds 公共池记录ID集合
     * @param xsIds     销售ID列表
     * @return
     */
    @PutMapping("/allocation")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "领导分配：提交", notes = "进行领导分配提交", position = 12)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clientIds", value = "公共池记录ID集合"),
            @ApiImplicitParam(name = "xsIds", value = "销售ID列表")
    })
    public R allocation(@RequestParam("clientIds") List<String> clientIds, @RequestParam("xsIds") List<String> xsIds) {
        if (CollectionUtils.isEmpty(clientIds)) {
            throw new ServiceException("分中心ID不能为空!");
        }
        if (CollectionUtils.isEmpty(xsIds)) {
            throw new ServiceException("请选择销售经理!");
        }
        return R.toResult(this.sellcustomerService.allocation(clientIds, xsIds));
    }

    /**
     * 领导释放
     *
     * @param clientId 公共池记录ID
     * @param xsIds    销售ID列表
     * @return
     */
    @PutMapping("/release")
    @ApiOperation(value = "领导释放", notes = "领导释放", position = 13)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clientId", value = "公共池记录ID"),
            @ApiImplicitParam(name = "xsIds", value = "销售ID列表")
    })
    public R release(@RequestParam("clientId") String clientId, @RequestParam("xsIds") List<String> xsIds) {
        if (StringUtils.isBlank(clientId)) {
            throw new ServiceException("分中心ID不能为空!");
        }
        if (CollectionUtils.isEmpty(xsIds)) {
            throw new ServiceException("请选择销售经理!");
        }
        return R.ok(clientcommonService.release(clientId, xsIds));
    }


    /**
     * 客户公共池导入
     *
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/beachSave")
    @ApiOperation(value = "导入", notes = "客户公共池导入", position = 14)
    public R<String> beachSave(MultipartFile file) throws Exception {
        if (Objects.isNull(file)) {
            throw new GlobalException("请选择上传文件！");
        }
        String extName = FileUtil.extName(file.getOriginalFilename());
        if (!extName.toLowerCase().endsWith("xls") && !extName.toLowerCase().endsWith("xlsx")) {
            throw new GlobalException("请选择正确的文件类型，必须是以.xls或.xlsx结尾！");
        }
        ExcelUtil<Clientcommon> util = new ExcelUtil<>(Clientcommon.class);
        List<Clientcommon> list = util.importExcel(file.getInputStream());
        if (CollectionUtil.isEmpty(list)) {
            throw new GlobalException("文件导入失败：该文件没数据，不能进行导入！");
        }
        String operName = SecurityUtils.getUsername();
        return R.ok(clientcommonService.importUser(list, operName));
    }


    @Log(title = "客户公共池管理-客户公共池导出", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('system:user:export')")
    @PostMapping("/export")
    @ApiOperation(value = "导出", notes = "客户公共池导出", position = 15)
    public void export(HttpServletResponse response, Clientcommon clientcommon) {
        List<Clientcommon> list = new ArrayList<>();
        ExcelUtil<Clientcommon> util = new ExcelUtil<Clientcommon>(Clientcommon.class);
        util.exportExcel(response, list, "客户公共池数据");
    }


}

