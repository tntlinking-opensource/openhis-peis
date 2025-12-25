package com.center.medical.pacs.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.aliyun.oss.ServiceException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.vo.PacsHistoryListVo;
import com.center.medical.common.annotation.RepeatSubmit;
import com.center.medical.common.config.LoadProperties;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.pacs.bean.dto.PCFromDataDto;
import com.center.medical.pacs.bean.param.*;
import com.center.medical.pacs.bean.vo.*;
import com.center.medical.pacs.service.PacsAbteilungService;
import com.center.medical.pacs.service.PacsCsharpService;
import com.center.medical.system.dao.SysUserMapper;
import com.center.medical.system.service.ISysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 彩超程序接口(Peispatient)接口
 *
 * @author ay
 * @since 2023-10-08 09:26:31
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "彩超程序接口")
@RequestMapping("/open/api/pacs/pacsCsharp")
public class PacsCsharpController extends BaseController {
    /**
     * 服务对象
     */
    private final PacsCsharpService pacsCsharpService;
    private final MapperFacade mapperFacade;
    private final SysUserMapper sysUserMapper;
    private final ISysConfigService iSysConfigService;

    private final PacsAbteilungService pacsAbteilungService;
    @Autowired
    private LoadProperties loadProperties;

    public static final String ksID="143";
    public static String pacs="PACS";//系统参数type
    public static String pacsn="PACSN";//系统参数type

    /**
     * 体检者列表数据
     *
     * @param pageParamSimple 分页参数
     * @param param     查询条件
     * @return 所有数据
     */
    @GetMapping("/getRankData")
    @ApiOperation(value = "体检者列表数据", notes = "体检者列表数据")
    public R<String> getPage(PageParamSimple pageParamSimple, GetRankDataParam param) {
        PageParam<PacsGetRankDataVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        page.setSize(100);
        param.setKsID(ksID);
        return R.ok(this.pacsCsharpService.getPage(page, param));
    }

    /**
     * 收费项目表格数据
     *
     * @param param     查询条件
     * @return 所有数据
     */
    @GetMapping("/getItemsData")
    @ApiOperation(value = "收费项目表格数据", notes = "收费项目表格数据")
    public R<List<PacsGetItemsDataVo>> getItemsData(GetItemsDataVoParam param) {
        param.setKsID(ksID);
        return R.ok(this.pacsCsharpService.getItemsData(param));
    }



    /**
     * 结论词表格数据
     *
     * @param param     查询条件
     * @return 所有数据
     */
    @GetMapping("/getJlcGridData")
    @ApiOperation(value = "结论词表格数据", notes = "结论词表格数据")
    public R<List<PacsGetJlcGridVo>> getJlcGridData(PacsGetJlcGridParam param) {
        return R.ok(this.pacsCsharpService.getJlcGridData(param));
    }


    /**
     * 选择收费项目
     *
     * @param param     查询条件
     * @return 所有数据
     */
    @GetMapping("/search")
    @ApiOperation(value = "选择收费项目", notes = "选择收费项目")
    public R<String> search(PacsSearchParam param) {
        param.setType(0);
        param.setKsID(ksID);
        return R.ok(this.pacsCsharpService.search(param));
    }


    /**
     * 登陆
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "登陆", notes = "登陆")
    public R<String> search(@RequestBody LoginParam param) {
        String username = param.getUsername();
        String password = param.getPassword();
        if(StringUtils.isEmpty(username)){
            throw new ServiceException("用户名不能为空");
        }
        if(StringUtils.isEmpty(password)){
            throw new ServiceException("密码不能为空");
        }
        SysUser user = sysUserMapper.getUserByUserName(username);
        if(user==null){
            throw new ServiceException("用户名或密码不正确");
        }
        if (!SecurityUtils.matchesPassword(password,user.getPassword())) {
            throw new ServiceException("用户名或密码不正确");
        }
        return R.ok("success");
    }


    /**
     * 监察人、审核人下拉数据
     * @return
     */
    @PostMapping("/allDoctors")
    @ApiOperation(value = "监察人、审核人下拉数据", notes = "监察人、审核人下拉数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名"),
            @ApiImplicitParam(name = "key", value = "搜索条件")
    })
    public R<List<PacsAllDoctorsVo>> allDoctors(String username,String key) {
        SysUser user = sysUserMapper.getUserByUserName(username);
        String userId=user==null?null:user.getUserNo();
        List<PacsAllDoctorsVo> list = pacsCsharpService.allDoctors(userId,ksID,key);
        return R.ok(list);
    }


    /**
     * 监察人、审核人下拉数据
     * @return
     */
    @GetMapping("/getConfig")
    @ApiOperation(value = "获取系统配置", notes = "获取系统配置")
    public R getConfig() {
        Map<String,Object> config = new HashMap<String, Object>();
        String pacsConfig = iSysConfigService.selectConfigByKey(Constants.PACS_CONFIG);
        if (StrUtil.isNotEmpty(pacsConfig)) {
            JSONObject jsonObject = JSONUtil.parseObj(pacsConfig);
            String imgConf = jsonObject.getStr("imageType");
            config.put("imageType",StringUtils.isEmpty(imgConf)?"0":"1");
            String ispacs = pacsCsharpService.getDictionaryStatusByType(pacs)? "1" : "0";
            config.put("ispacs", ispacs);
        }
        return R.ok(config);
    }



    /**
     * 监察人、审核人下拉数据
     * @return
     */
    @GetMapping("/check")
    @ApiOperation(value = "检查体检者状态", notes = "检查体检者状态,成功返回空字符串")
    @ApiImplicitParam(name = "patientcode", value = "体检号")
    public R check(String patientcode) {
        String str = pacsCsharpService.check(patientcode);
        return R.ok(str);
    }


    /**
     * 保存
     * @param param
     * @return
     */
    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "保存", notes = "保存")
//    @Log(title = "彩超客户端-保存", businessType = BusinessType.INSERT)
    @RepeatSubmit(interval = 3000, message = "正在处理，请稍等")
    public R saveOrUpdate(@RequestBody PacsCsharpSaOrUpParam param) {
        PCFromDataDto ma = param.getFormdata();
        //检查人姓名
        String rummagerName = ma.getRummagerName();
        SysUser rummager = sysUserMapper.getUserByUserName(rummagerName);
        ma.setRummagerId(rummager.getUserNo());
        //录入人
        String writeName = ma.getWriteName();
        SysUser writer = sysUserMapper.getUserByUserName(writeName);
        ma.setWriteId(writer.getUserNo());
        //保存
        String result = pacsCsharpService.saOrUp(ma,param.getGriddata(),param.getUsername());
        return R.ok(result);
    }


    /**
     * 审核
     * @param param
     * @return
     */
    @PostMapping("/shenhe")
    @ApiOperation(value = "审核", notes = "审核")
//    @Log(title = "彩超客户端-审核", businessType = BusinessType.INSERT)
    @RepeatSubmit(interval = 3000, message = "正在处理，请稍等")
    public R shenhe(@RequestBody PacsCsharpSaOrUpParam param) {
        PCFromDataDto ma = param.getFormdata();
        //检查人姓名
        String rummagerName = ma.getRummagerName();
        SysUser rummager = sysUserMapper.getUserByUserName(rummagerName);
        ma.setRummagerId(rummager.getUserNo());
        //录入人
        String writeName = ma.getWriteName();
        SysUser writer = sysUserMapper.getUserByUserName(writeName);
        ma.setWriteId(writer.getUserNo());
        //保存
        String result = pacsCsharpService.saOrUp(ma,param.getGriddata(),param.getUsername());
        if(result.equals("success") && StringUtils.equals(loadProperties.name, "bazhou")){
            //发送中间库保存数据
            pacsCsharpService.sendSaveResult(param.getFormdata().getPatientcode(),"143");
        }
        return R.ok(result);
    }


    /**
     * 反审核
     * @param param
     * @return
     */
    @PostMapping("/reverse")
    @ApiOperation(value = "反审核", notes = "反审核")
    public R reverse(@RequestBody PascReverseParam param) {
        String result = pacsCsharpService.reverse(param);
        return R.ok(result);
    }



    /**
     * 保存图片
     * @param param
     * @return
     */
    @PostMapping("/saveImg")
    @ApiOperation(value = "保存图片", notes = "保存图片")
    public R saveImg(@RequestBody PacsSaveImgParam param) {
        String result = pacsCsharpService.saveImg(param);
        return R.ok(result);
    }




    /**
     * 获取历史数据
     * @return
     */
    @GetMapping("/getHistoryData")
    @ApiOperation(value = "获取历史数据", notes = "获取历史数据")
    @ApiImplicitParam(name = "patientcode", value = "体检号")
    public R getHistoryData(String patientcode,String itemId,String ksID) {
        List<PacsHistoryListVo> historys = new ArrayList<>();
        PageParam page = new PageParam();
        page.setSize(9999);
        IPage<PacsAbteilungHistoryListVo> historyList = pacsAbteilungService.getHistoryList(page, patientcode, ksID,null);
        List<PacsAbteilungHistoryListVo> list = historyList.getRecords();
        for (PacsAbteilungHistoryListVo vo : list) {
            PacsHistoryListVo historyListVo = mapperFacade.map(vo, PacsHistoryListVo.class);
            historyListVo.setSex(vo.getIdSex());
            historys.add(historyListVo);
        }
        return R.ok(historys);
    }






    /**
     * 图片设置进报告
     *
     * @param param
     * @return
     */
    @GetMapping("/sendSaveResult")
    @ApiOperation(value = "发送中间库保存数据", notes = "发送中间库保存数据")
    public R<Boolean> sendSaveResult(String patientcode,String depId) {
        pacsCsharpService.sendSaveResult(patientcode,depId);
        return R.ok();
    }
}
