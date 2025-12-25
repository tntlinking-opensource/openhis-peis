package com.center.medical.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.app.bean.dto.PageParamSimple;
import com.center.medical.app.bean.model.User;
import com.center.medical.app.bean.model.UserIdcard;
import com.center.medical.app.common.util.PageParam;
import com.center.medical.app.security.util.SecurityUtils;
import com.center.medical.app.service.UserIdcardService;
import com.center.medical.app.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户和身份证关联表(UserIdcard)接口
 *
 * @author ay
 * @since 2023-08-23 15:12:05
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "用户和身份证关联表")
@RequestMapping("/api/v1/userIdcard")
public class UserIdcardController {
    /**
     * 服务对象
     */
    private final UserIdcardService userIdcardService;
    private final MapperFacade mapperFacade;
    private final UserService userService;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param phone           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询", notes = "分页查询用户和身份证关联表")
    public ResponseEntity<IPage<UserIdcard>> getPage(PageParamSimple pageParamSimple, String phone) {
        PageParam<UserIdcard> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return ResponseEntity.ok(this.userIdcardService.getPage(page, phone));
    }


    /**
     * 新增数据
     *
     * @param idCards 实体对象
     * @return 新增结果
     */
    @PostMapping("/saOrUp")
    @ApiOperation(value = "添加或修改", notes = "添加或修改")
    public ResponseEntity insert(@RequestBody List<String> idCards) {
        String userId = SecurityUtils.getUser().getUserId();
        return ResponseEntity.ok(this.userIdcardService.saOrUp(idCards, userId));
    }


    /**
     * 添加或修改
     *
     * @param
     * @return
     */
    @PostMapping("/checkIdCard")
    @ApiOperation(value = "判断是否绑定过身份证号", notes = "判断是否绑定过身份证号")
    public ResponseEntity checkIdCard() {
        String userId = SecurityUtils.getUser().getUserId();
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserId, userId));
        long count = userIdcardService.count(new LambdaQueryWrapper<UserIdcard>().eq(UserIdcard::getUserId, user.getUserId()));
        return ResponseEntity.ok(count > 0 ? true : false);
    }

}
