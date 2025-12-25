package com.center.medical.member.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.member.bean.param.FamilyBirthParam;
import com.center.medical.member.bean.vo.FamilyBirthVo;
import com.center.medical.member.service.FamilyMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

/**
 * 家庭会员-家庭卡生日(Peispatientarchive)表控制层
 *
 * @author ay
 * @since 2023-03-01 10:34:46
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "家庭会员-家庭卡生日")
@RequestMapping("/member/familyCardBirthday")
public class FamilyCardBirthdayController extends BaseController {
    /**
     * 服务对象
     */
    private final FamilyMemberService familyMemberService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple    分页参数
     * @param param 查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询家庭卡生日")
    public R<IPage<FamilyBirthVo>> getPage(PageParamSimple pageParamSimple, FamilyBirthParam param) {
        PageParam<FamilyBirthVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<FamilyBirthVo> iPage = familyMemberService.FamilyBirthPage(page,param);
        return R.ok(iPage);
    }


}

