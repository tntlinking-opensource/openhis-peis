package com.center.medical.app.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.app.bean.model.FamilyList;
import com.center.medical.app.bean.model.User;
import com.center.medical.app.bean.param.FamilySaOrUpParam;
import com.center.medical.app.common.constants.Constant;
import com.center.medical.app.common.enums.AppHttpStatus;
import com.center.medical.app.common.exception.AppBindException;
import com.center.medical.app.common.response.AppResponse;
import com.center.medical.app.common.util.IdcardUtil;
import com.center.medical.app.config.OsZhongKangConfig;
import com.center.medical.app.security.util.SecurityUtils;
import com.center.medical.app.service.FamilyListService;
import com.center.medical.app.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 家人列表(FamilyList)接口
 *
 * @author ay
 * @since 2024-03-13 14:31:01
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "家人列表")
@RequestMapping("api/v1/familyList")
public class FamilyListController {
    /**
     * 服务对象
     */
    private final FamilyListService familyListService;
    private final MapperFacade mapperFacade;
    private final UserService userService;
    private final OsZhongKangConfig osZhongKangConfig;

    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping("/newList")
    @ApiOperation(value = "新版查询", notes = "新版查询")
    public ResponseEntity<List<FamilyList>> newList() {
        String userId = SecurityUtils.getUser().getUserId();

        List<FamilyList> list = familyListService.list(new LambdaQueryWrapper<FamilyList>().eq(FamilyList::getUserId, userId));
        //如果为空的话,去查询线上系统的数据，并插入进来
        if (CollectionUtil.isEmpty(list)){
            User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserId, userId));
            String phone = user.getUserMobile();
            //加密
            Map<String, Object> mapParam = osZhongKangConfig.encryptV2(phone);
            AppResponse response = osZhongKangConfig.sendWithMap(Constant.RT_NEWRESERVATION_USERIDCARD_PATH, 2, mapParam);
            if (AppResponse.isSuccess(response)) {
                //获取成功
                if (Objects.nonNull(response.getData())) {
                    List<FamilyList> familyLists = JSONUtil.toList((String) response.getData(), FamilyList.class);
                    for (FamilyList familyList : familyLists) {
                        familyList.setUserId(userId);
                        familyList.setCreateTime(new Date());
                        familyList.setFromOnline(1);
                        //能通过身份证校验的设为身份证，否则为护照
                        boolean validCard = IdcardUtil.isValidCard(familyList.getIdcardno());
                        if (validCard){
                            familyList.setCountreportoccupationxml(1);
                        }else {
                            familyList.setCountreportoccupationxml(2);
                        }
                    }
                    familyListService.saveBatch(familyLists);
                    list = familyListService.list(new LambdaQueryWrapper<FamilyList>().eq(FamilyList::getUserId, userId));
                    return ResponseEntity.ok(list);
                } else {
                    throw new AppBindException(AppHttpStatus.SUCCESS, "暂无数据");
                }
            } else {
                throw new AppBindException("获取失败，请稍后再重试！");
            }
        }

        return ResponseEntity.ok(list);
    }

    /**
     * 新增数据
     *
     * @param param 实体对象
     * @return 新增结果
     */
    @PostMapping("/saOrUp")
    @ApiOperation(value = "添加或修改", notes = "添加家人列表")
    public ResponseEntity insert(@RequestBody FamilySaOrUpParam param) {
        FamilyList familyList = mapperFacade.map(param, FamilyList.class);

        //校验证件号
        if (ObjectUtils.isNotEmpty(familyList.getCountreportoccupationxml())
                && familyList.getCountreportoccupationxml() == 1){
            boolean validCard = IdcardUtil.isValidCard(familyList.getIdcardno());
            if (!validCard){
                throw new AppBindException("身份证不合法！");
            }
        }else {
            if (familyList.getIdcardno().length() < 7){
                throw new AppBindException("请确认证件号是否正确！");
            }
        }

        //插入或更新
        if (StringUtils.isEmpty(familyList.getId())){
            String userId = SecurityUtils.getUser().getUserId();
            familyList.setUserId(userId);
            familyList.setFromOnline(0);
            familyList.setCreateTime(new Date());
        }else {
            familyList.setUpdateTime(new Date());
        }

        return ResponseEntity.ok(this.familyListService.saveOrUpdate(familyList));
    }



    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除家人列表")
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public ResponseEntity delete(@PathVariable List<String> ids) {
        return ResponseEntity.ok(this.familyListService.removeByIds(ids));
    }






    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping("/generate")
    @ApiOperation(value = "生成", notes = "生成")
    public void generate() {
        String userId = SecurityUtils.getUser().getUserId();
        List<FamilyList> list = familyListService.list(new LambdaQueryWrapper<FamilyList>().eq(FamilyList::getUserId, userId));
        //如果为空的话,去查询线上系统的数据，并插入进来
        if (CollectionUtil.isEmpty(list)){
            User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserId, userId));
            String phone = user.getUserMobile();
            //加密
            Map<String, Object> mapParam = osZhongKangConfig.encryptV2(phone);
            AppResponse response = osZhongKangConfig.sendWithMap(Constant.RT_NEWRESERVATION_USERIDCARD_PATH, 2, mapParam);
            if (AppResponse.isSuccess(response)) {
                //获取成功
                if (Objects.nonNull(response.getData())) {
                    List<FamilyList> familyLists = JSONUtil.toList((String) response.getData(), FamilyList.class);
                    for (FamilyList familyList : familyLists) {
                        familyList.setUserId(userId);
                        familyList.setCreateTime(new Date());
                        familyList.setFromOnline(1);
                        //能通过身份证校验的设为身份证，否则为护照
                        boolean validCard = IdcardUtil.isValidCard(familyList.getIdcardno());
                        if (validCard){
                            familyList.setCountreportoccupationxml(1);
                        }else {
                            familyList.setCountreportoccupationxml(2);
                        }
                    }
                    familyListService.saveBatch(familyLists);

                } else {
                    throw new AppBindException(AppHttpStatus.SUCCESS, "暂无数据");
                }
            } else {
                throw new AppBindException("获取失败，请稍后再重试！");
            }
        }
    }
}
