package com.center.medical.sellcrm.controller.sell;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.sellcrm.bean.model.Riskclient;
import com.center.medical.sellcrm.bean.model.Riskclientcon;
import com.center.medical.sellcrm.bean.param.RiskclientParam;
import com.center.medical.sellcrm.service.RiskclientService;
import com.center.medical.sellcrm.service.RiskclientconService;
import com.center.medical.service.PeispatientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 高危人员管理表(Riskclient)表控制层
 *
 * @author 路飞船长
 * @since 2022-11-22 19:29:27
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "危急值管理")
@RequestMapping("sell/riskclient")
public class RiskclientController extends BaseController {
    /**
     * 服务对象
     */
    private final RiskclientService riskclientService;
    private final MapperFacade mapperFacade;
    private final RiskclientconService riskclientconService;
    private final PeispatientService peispatientService;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询高危人员管理表")
    public R<IPage<Riskclient>> getPage(PageParamSimple pageParamSimple, RiskclientParam param) {
        PageParam<Riskclient> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.riskclientService.getPage(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查高危人员管理表详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<Riskclient> selectOne(@PathVariable String id) {
        Riskclient item = this.riskclientService.getInfoById(id);
        if (Objects.isNull(item)) {
            throw new ServiceException("该记录不存在或者已被删除！");
        }
        List<Riskclientcon> list = riskclientconService.list(new LambdaQueryWrapper<Riskclientcon>()
                .eq(Riskclientcon::getRiskid, id));
        Peispatient patient = peispatientService.getOne(new LambdaQueryWrapper<Peispatient>()
                .eq(Peispatient::getPatientcode, item.getTjid()));
        item.setRiskclientconList(list);
        item.setPatient(patient);
        return R.ok(item);
    }

    /**
     * 处理高危人员
     *
     * @param riskId 操作的对象主键id集合
     * @return
     */
    @PutMapping("/handle")
    @ApiOperation(value = "处理高危人员", notes = "处理高危人员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "riskId", value = "id"),
            @ApiImplicitParam(name = "bz", value = "备注")
    })
    public R<Boolean> handle(@RequestParam("riskId") String riskId, @RequestParam("bz") String bz) {
        Riskclient info = riskclientService.getInfoById(riskId);
        if (Objects.isNull(riskId)) {
            throw new ServiceException("该记录不存在或者已被删除！");
        }
        info.setTjzt(1);
        info.setBz(bz);
        info.setModifydate(new Date());
        return R.ok(this.riskclientService.updateById(info));
    }


    /**
     * 判断选择的记录是否还能在进行处理
     *
     * @param isClId
     * @return
     */
    @GetMapping("/isCl")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "判断选择的记录是否还能在进行处理", notes = "判断选择的记录是否还能在进行处理")
    @ApiImplicitParam(name = "isClId", value = "判断是否允许再处理{id}")
    public R isCl(String isClId) {
        String state = "";
        //获取实体
        Riskclient riskClient = riskclientService.getInfoById(isClId);
        if (null != riskClient) {
            //状态：0：未处理1：处理
            if (riskClient.getTjzt() != null && 1 == riskClient.getTjzt()) {
                state = "success";
            }
        }
        return R.ok(state);
    }


}

