package com.center.medical.data.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.data.bean.model.OccupationSymptom;
import com.center.medical.data.bean.param.ShowDataParam;
import com.center.medical.data.service.OccupationSymptomService;
import com.center.medical.service.PeispatientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 职业检查设置-职业健康症状名称维护(OccupationSymptom)表控制层
 *
 * @author ay
 * @since 2022-11-16 17:17:42
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "职业检查设置-职业健康症状名称维护")
@RequestMapping("occupationSymptom")
public class OccupationSymptomController extends BaseController {
    /**
     * 服务对象
     */
    private final OccupationSymptomService occupationSymptomService;
    private final MapperFacade mapperFacade;
    private final PeispatientService peispatientService;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple   分页参数
     * @param occupationSymptom 查询实体
     * @return 所有数据
     */
    @GetMapping("/getListData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询JC职业症状")
    public R<IPage<OccupationSymptom>> getListData(PageParamSimple pageParamSimple, OccupationSymptom occupationSymptom) {
        PageParam<OccupationSymptom> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.occupationSymptomService.getList(page, occupationSymptom));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查JC职业症状详情")
    public R<OccupationSymptom> selectOne(@PathVariable String id) {
        return R.ok(this.occupationSymptomService.getInfoById(id));
    }

    /**
     * 保存或更新
     *
     * @param occupationSymptom 实体对象
     * @return 新增结果
     */
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "保存或更新", notes = "保存或更新JC职业症状")
    @Log(title = "JC职业症状", businessType = BusinessType.INSERT)
    public R saveOrUpdate(@RequestBody OccupationSymptom occupationSymptom) {
        String s = occupationSymptomService.saveOrUpdateOcc(occupationSymptom);
        return R.ok(s);
    }

    /**
     * 返回编辑数据
     *
     * @param id 实体对象
     * @return 修改结果
     */
    @GetMapping("/edit")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "返回编辑数据", notes = "返回编辑数据")
    public R edit(String id) {
        return R.ok(occupationSymptomService.getInfoById(id));
    }

    /**
     * 逻辑删除
     *
     * @param ids 主键结合
     * @return 删除结果
     */
    @DeleteMapping("/remove/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "逻辑删除", notes = "逻辑删除")
    @Log(title = "JC职业症状", businessType = BusinessType.DELETE)
    public R delete(@PathVariable String ids) {
        String s = occupationSymptomService.removeOccu(ids);
        return R.ok(s);
    }


    /**
     * 症状数据
     *
     * @param param
     * @return
     */
    @GetMapping("/getShowData")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "症状数据", notes = "症状数据")
    public R getShowData(ShowDataParam param) {
        List<OccupationSymptom> s = occupationSymptomService.getShowData(param);
        return R.ok(s);
    }


}

