package com.center.medical.reservation.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reservation.bean.model.ReservationGroupCode;
import com.center.medical.reservation.bean.param.GenerateGroupCodeParam;
import com.center.medical.reservation.bean.param.ModifyGroupCodeParam;
import com.center.medical.reservation.bean.param.ReGroupCodePageParam;
import com.center.medical.reservation.bean.vo.GenerateGroupCodeVo;
import com.center.medical.reservation.dao.ReservationGroupCodeMapper;
import com.center.medical.reservation.service.ReservationGroupCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

/**
 * 团体预约分享码(ReservationGroupCode)服务实现类
 *
 * @author ay
 * @since 2024-03-08 16:38:40
 */
@Slf4j
@Service("reservationGroupCodeService")
@RequiredArgsConstructor
public class ReservationGroupCodeServiceImpl extends ServiceImpl<ReservationGroupCodeMapper, ReservationGroupCode> implements ReservationGroupCodeService {

    private final ReservationGroupCodeMapper reservationGroupCodeMapper;

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    /**
     * 分页查询[团体预约分享码]列表
     *
     * @param page  分页参数
     * @param param ReservationGroupCode查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ReservationGroupCode> getPage(PageParam<ReservationGroupCode> page, ReGroupCodePageParam param) {
        return reservationGroupCodeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public ReservationGroupCode getInfoById(String id) {
        return reservationGroupCodeMapper.getInfoById(id);
    }

    /**
     * 生成团检分享码
     * @param param
     * @return
     */
    @Override
    public GenerateGroupCodeVo generateGroupCode(GenerateGroupCodeParam param) {
        if (ObjectUtils.isEmpty(param) || ObjectUtils.isEmpty(param.getGroupId()) || ObjectUtils.isEmpty(param.getIdOrg())){
            throw new ServiceException("请输入参数！");
        }
        ReservationGroupCode reservationGroupCode = reservationGroupCodeMapper.selectOne(new LambdaQueryWrapper<ReservationGroupCode>()
                .eq(ReservationGroupCode::getGroupId, param.getGroupId()));
        if (ObjectUtils.isEmpty(reservationGroupCode)){
            //生成新记录
            reservationGroupCode = new ReservationGroupCode();
            String extractedCode = generateRandomCode();
            reservationGroupCode.setExtractedCode(extractedCode);//提取码
            reservationGroupCode.setGroupId(param.getGroupId());
            reservationGroupCode.setIdOrg(param.getIdOrg());
            //会员类型,小于800普通会员，800到1500VIP，1500以上VVIP
            Double jg = reservationGroupCodeMapper.getPriceByGroupId(param.getGroupId());
            if (jg < 800){
                reservationGroupCode.setIdPatientclass("1");
            }else if (jg >= 800 && jg < 1500){
                reservationGroupCode.setIdPatientclass("2");
            }else{
                reservationGroupCode.setIdPatientclass("3");
            }
            reservationGroupCode.setExpirationDate(30);//有效期默认30天
            //设置过期时间
            DateTime expirationTime = DateUtil.offsetDay(new Date(), 30);
            reservationGroupCode.setExpirationTime(expirationTime);
            reservationGroupCode.setStatus(0);//生效
            reservationGroupCode.setCreatedate(new Date());
            reservationGroupCode.setCreateId(SecurityUtils.getUserNo());
            reservationGroupCodeMapper.insert(reservationGroupCode);
        }
        //返回数据
        GenerateGroupCodeVo vo = new GenerateGroupCodeVo(reservationGroupCode.getId()
                ,reservationGroupCode.getExtractedCode(),reservationGroupCode.getIdPatientclass()
                ,reservationGroupCode.getExpirationDate(),reservationGroupCode.getExpirationTime()
                ,reservationGroupCode.getStatus());


        return vo;
    }




    /**
     * 生成随机四位的体检卡
     * @return
     */
    private String generateRandomCode() {
        StringBuilder code = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(index));
        }

        return code.toString();
    }

    /**
     * 修改团检分享码
     * @param param
     * @return
     */
    @Override
    public Boolean modifyGroupCode(ModifyGroupCodeParam param) {

        ReservationGroupCode reservationGroupCode = reservationGroupCodeMapper.getInfoById(param.getId());
        if (ObjectUtils.isEmpty(reservationGroupCode)){
            throw new ServiceException("请输入正确的id!");
        }
        //设置过期时间
        if (ObjectUtils.isNotEmpty(param.getExpirationDate())){
            DateTime expirationTime = DateUtil.offsetDay(reservationGroupCode.getCreatedate(), param.getExpirationDate());
            reservationGroupCode.setExpirationTime(expirationTime);
            reservationGroupCode.setExpirationDate(param.getExpirationDate());
            //判断是否过期
            Date date = new Date();
            boolean isExpired = date.before(reservationGroupCode.getExpirationTime());
            //更新状态
            if (!isExpired){
                //过期
                reservationGroupCode.setStatus(1);
            }else {
                //没过期
                reservationGroupCode.setStatus(0);
            }
        } else if (ObjectUtils.isNotEmpty(param.getStatus())){
            reservationGroupCode.setStatus(param.getStatus());
        }
        reservationGroupCodeMapper.updateById(reservationGroupCode);

        return Boolean.TRUE;
    }
}

