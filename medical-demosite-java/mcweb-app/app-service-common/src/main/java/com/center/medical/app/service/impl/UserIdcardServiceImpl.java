package com.center.medical.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.app.bean.model.UserIdcard;
import com.center.medical.app.common.exception.AppBindException;
import com.center.medical.app.common.util.PageParam;
import com.center.medical.app.dao.UserIdcardMapper;
import com.center.medical.app.service.UserIdcardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户和身份证关联表(UserIdcard)服务实现类
 *
 * @author ay
 * @since 2023-08-23 15:12:06
 */
@Slf4j
@Service("userIdcardService")
@RequiredArgsConstructor
public class UserIdcardServiceImpl extends ServiceImpl<UserIdcardMapper, UserIdcard> implements UserIdcardService {

    private final UserIdcardMapper userIdcardMapper;

    /**
     * 分页查询[用户和身份证关联表]列表
     *
     * @param page  分页参数
     * @param param UserIdcard查询参数
     * @return 分页数据
     */
    @Override
    public IPage<UserIdcard> getPage(PageParam<UserIdcard> page, String param) {
        return userIdcardMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public UserIdcard getInfoById(Long id) {
        return userIdcardMapper.getInfoById(id);
    }


    /**
     * 添加或修改
     *
     * @param idCards
     * @return
     */
    @Override
    public Boolean saOrUp(List<String> idCards,String userId) {
        //删除之前的数据
        userIdcardMapper.delete(new LambdaQueryWrapper<UserIdcard>()
                .eq(UserIdcard::getUserId, userId));
        List<UserIdcard> idCardList = new ArrayList<>();
        //设置数据
        List<String> con = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(idCards)){
            for (String idcard : idCards) {
                //重复抛异常,不重复把身份证号放进去
                if (con.contains(idcard)) {
                    throw new AppBindException("身份证号重复");
                } else {
                    con.add(idcard);
                }
                //设置文件属性
                UserIdcard userIdcard = new UserIdcard(userId,idcard,new Date(),new Date());
                idCardList.add(userIdcard);
            }
            saveOrUpdateBatch(idCardList);
        }
        return Boolean.TRUE;
    }
}

