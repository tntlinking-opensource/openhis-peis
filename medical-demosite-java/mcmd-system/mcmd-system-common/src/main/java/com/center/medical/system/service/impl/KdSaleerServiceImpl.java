package com.center.medical.system.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.bean.model.UserSaleer;
import com.center.medical.common.constant.KingdeeConstants;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.KdSaleer;
import com.center.medical.system.bean.param.KdSaleerParam;
import com.center.medical.system.dao.KdSaleerMapper;
import com.center.medical.system.dao.SysBranchMapper;
import com.center.medical.system.dao.SysUserMapper;
import com.center.medical.system.dao.UserSaleerMapper;
import com.center.medical.system.service.KdSaleerService;
import com.center.medical.system.utils.KingdeeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 金蝶销售员(Saleer)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:36
 */
@Slf4j
@Service("kdSaleerService")
@RequiredArgsConstructor
public class KdSaleerServiceImpl extends ServiceImpl<KdSaleerMapper, KdSaleer> implements KdSaleerService {

    private final KdSaleerMapper saleerMapper;
    private final KingdeeUtil kingdeeUtil;
    private final SysBranchMapper sysBranchMapper;

    private final SysUserMapper sysUserMapper;
    private final UserSaleerMapper userSaleerMapper;

    /**
     * 分页查询[金蝶销售员]列表
     *
     * @param page  分页参数
     * @param param Saleer查询参数
     * @return 分页数据
     */
    @Override
    public IPage<KdSaleer> getList(PageParam<KdSaleer> page, KdSaleerParam param) {
        IPage<KdSaleer> iPage = saleerMapper.getList(page, param);
        List<KdSaleer> records = iPage.getRecords();
        for (KdSaleer record : records) {
            if (StringUtils.isNotEmpty(record.getUseStatusId()) && "1".equals(record.getUseStatusId())){
                record.setUseStatusName("正常");
            }else {
                record.setUseStatusName("下线");
            }
        }
        return iPage;
    }

    /**
     * 用户-金蝶数据更新
     */
    @Override
    @Transactional
    public void upgradeSaleer() {
        String branchId = SecurityUtils.getCId();
        if (StrUtil.isEmpty(branchId)) {
            throw new ServiceException("无法获取当前用户分中心id");
        }
        SysBranch sysBranch = sysBranchMapper.getByBranchId(branchId);
        if (sysBranch == null) {
            throw new ServiceException("当前用户所属分中心不存在，可能已被删除：" + branchId);
        }
        String centerOrgName = sysBranch.getCenterorgname();
        if (StrUtil.isEmpty(centerOrgName)) {
            throw new ServiceException("请联系管理员为分中心【" + sysBranch.getFzx() + "】维护当前中心组织名");
        }
        String centerOrgNameEncrypted = KingdeeUtil.EncryptDES(centerOrgName);

        baseMapper.delete(
                new QueryWrapper<KdSaleer>()
                        .eq("centerorgname", centerOrgName)
        );

        String json = kingdeeUtil.send(KingdeeConstants.METHOD_EMPINFO, "OrgName", centerOrgNameEncrypted);
        JSONObject jo = JSONUtil.parseObj(json);
        JSONArray ja = jo.getJSONArray("Empinfo");
        List<KdSaleer> saleerList = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            JSONObject saleerObj = ja.getJSONObject(i);
            KdSaleer saleer = new KdSaleer();
            saleer.setCenterorgname(centerOrgName);
            saleer.setAccountNo(saleerObj.getStr("Number"));
            String MD5 = centerOrgName + saleer.getAccountNo();
            MD5 = DigestUtils.md5DigestAsHex(MD5.getBytes());//生成MD5
            saleer.setMd5(MD5);
            saleer.setAccountName(saleerObj.getStr("NAME"));
            saleer.setCtDate(kingdeeUtil.getDateFromJson(saleerObj, "CT_DATE"));
            saleer.setLtDate(kingdeeUtil.getDateFromJson(saleerObj, "LT_DATE"));
            saleer.setUseStatusId(saleerObj.getStr("USE_STATUS_ID"));
            saleerList.add(saleer);
        }
        saveBatch(saleerList);
    }

    /**
     * 通过账户号查询
     * @param kingdeeAccountNo
     * @return
     */
    @Override
    public KdSaleer getByAccountNo(String kingdeeAccountNo) {
        return saleerMapper.getByAccountNo(kingdeeAccountNo);
    }

    /**
     * 更新用户和金蝶关联表
     * @param cid
     * @return
     */
    @Override
    public Boolean updateUserSaleer(String cid) {
        //根据分中心id查询金蝶账号不为空的
        List<SysUser> users = sysUserMapper.getAccountNoByCid(cid);
        for (SysUser user : users) {
            String kingdeeAccountNo = user.getKingdeeAccountNo();
            KdSaleer kdSaleer = saleerMapper.getByAccountNo(kingdeeAccountNo);
            if (ObjectUtils.isNotEmpty(kdSaleer)){
                //金蝶销售员存在的话，重新绑定用户和金蝶关联表
                UserSaleer userSaleer = userSaleerMapper.getInfoByUserId(user.getUserNo());
                if(userSaleer==null){
                    userSaleer= new UserSaleer();
                    userSaleer.setUserId(user.getUserNo());
                    userSaleer.setSaleerMd5(kdSaleer.getMd5());
                    userSaleerMapper.insert(userSaleer);
                }else{
                    userSaleer.setSaleerMd5(kdSaleer.getMd5());
                    userSaleerMapper.updateById(userSaleer);
                }
            }
        }
        return Boolean.TRUE;
    }
}

