package com.center.medical.data.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Harm;
import com.center.medical.data.bean.model.ZyHarmClass;
import com.center.medical.data.service.HarmService;
import com.center.medical.data.service.ZyHarmClassService;
import com.center.medical.sellcrm.service.SellcustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 职业检查设置-职业危害因素分类(ZyHarmClass)表控制层
 *
 * @author ay
 * @since 2022-11-15 10:22:35
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "职业检查设置-职业危害因素分类")
@RequestMapping("zyHarmClass")
public class ZyHarmClassController extends BaseController {
    /**
     * 服务对象
     */
    private final ZyHarmClassService zyHarmClassService;
    private final SellcustomerService sellcustomerService;
    private final HarmService harmService;

    /**
     * 分页查询所有数据
     *
     * @param page        分页对象
     * @param zyHarmClass 查询实体
     * @return 所有数据
     */
    @GetMapping("/getHarmclassData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询职业危害因素分类")
    public R<IPage<ZyHarmClass>> getHarmclassData(PageParam<ZyHarmClass> page, ZyHarmClass zyHarmClass) {
        return R.ok(this.zyHarmClassService.getList(page, zyHarmClass));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查职业危害因素分类详情")
    public R<ZyHarmClass> selectOne(@PathVariable String id) {
        return R.ok(this.zyHarmClassService.getInfoById(id));
    }

    /**
     * 添加或修改
     *
     * @param zyHarmClass 实体对象
     * @return 新增结果
     */
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加或修改", notes = "添加或修改职业危害因素分类")
    @Log(title = "职业危害因素分类", businessType = BusinessType.INSERT)
    public R saveOrUpdate(@RequestBody ZyHarmClass zyHarmClass) {
        String s = zyHarmClassService.saveOrUpdateDwHarm(zyHarmClass);
        return R.ok(s);
    }

    /**
     * 修改数据
     *
     * @param zyHarmClass 实体对象
     * @return 修改结果
     */
    @PutMapping("/edit")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "编辑", notes = "更新职业危害因素分类")
    @Log(title = "职业危害因素分类", businessType = BusinessType.UPDATE)
    public R update(@RequestBody ZyHarmClass zyHarmClass) {
        zyHarmClass.setModifydate(new Date());
        return R.toResult(this.zyHarmClassService.updateById(zyHarmClass));
    }

    /**
     * 删除数据
     *
     * @param ids 主键结合
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除职业危害因素分类")
    @Log(title = "职业危害因素分类", businessType = BusinessType.DELETE)
    public R delete(@PathVariable String ids) {
        String flg = "success";
        // 将获取的多个ID分解
        String id[] = ids.split(",");
        for (int i = 0; i < id.length; i++) {
            ZyHarmClass harmc = zyHarmClassService.getOne(new QueryWrapper<ZyHarmClass>().eq("id", id[i]).eq("is_delete", 0));
            if (null != harmc) {
                // 判断危害因素分类是否被引用
                Harm harm = harmService.getOne(new QueryWrapper<Harm>().eq("harm_class", harmc.getId()).eq("is_delete", 0));
                if (harm != null) {
                    throw new ServiceException("无法删除！【<font color='red'>" + harmc.getHarmClass() + "</font>】在危害因素【<font color='red'>" + harm.getHarmName() + "</font>】中已被占用");
                } else {
                    //将isDelete设置为1，为删除
                    harmc.setIsDelete(1);
                    this.update(harmc);
                }
            }
        }
        return R.ok(flg);
    }

}

