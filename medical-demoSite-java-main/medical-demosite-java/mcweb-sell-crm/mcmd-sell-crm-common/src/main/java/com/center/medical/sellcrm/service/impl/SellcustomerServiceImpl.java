package com.center.medical.sellcrm.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.enums.CustomerStatus;
import com.center.medical.bean.enums.JinanStatus;
import com.center.medical.bean.model.Savefilepath;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.bean.model.WsUser;
import com.center.medical.bean.model.WsUserRole;
import com.center.medical.common.config.KingdeeConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.constant.KingdeeConstants;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.core.domain.entity.SysRole;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.core.domain.model.LoginUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.CodeUtil;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.data.bean.vo.*;
import com.center.medical.data.dao.*;
import com.center.medical.dao.SavefilepathMapper;
import com.center.medical.data.bean.model.Harm;
import com.center.medical.sellcrm.bean.model.*;
import com.center.medical.sellcrm.bean.param.FormalPageParam;
import com.center.medical.sellcrm.bean.param.SaveCuParam;
import com.center.medical.sellcrm.bean.param.SellcustomerParam;
import com.center.medical.sellcrm.bean.vo.*;
import com.center.medical.sellcrm.dao.*;
import com.center.medical.sellcrm.service.SellcustomerService;
import com.center.medical.sellcrm.service.WsUserService;
import com.center.medical.service.WsUserRoleService;
import com.center.medical.system.dao.SysBranchMapper;
import com.center.medical.system.dao.SysUserMapper;
import com.center.medical.system.service.SysFunctionBranchService;
import com.center.medical.system.utils.KingdeeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 我的客户表(Sellcustomer)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:21
 */
@Slf4j
@Service("sellcustomerService")
@RequiredArgsConstructor
public class SellcustomerServiceImpl extends ServiceImpl<SellcustomerMapper, Sellcustomer> implements SellcustomerService {

    private final SellcustomerMapper sellcustomerMapper;
    private final SavefilepathMapper savefilepathMapper;
    private final ClientcommonMapper clientcommonMapper;
    private final ReceiveandsellMapper receiveandsellMapper;
    private final CustomerOperateHistoryMapper customerOperateHistoryMapper;
    private final Snowflake snowflake;
    private final SysUserMapper sysUserMapper;
    private final BaseZoneMapper baseZoneMapper;
    private final BaseIndustryMapper baseIndustryMapper;
    private final BaseDictionaryMapper baseDictionaryMapper;
    private final BaseZoneQdMapper baseZoneQdMapper;
    private final CustomerfollowMapper customerfollowMapper;
    private final MapperFacade mapperFacade;
    private final SellpactMapper sellpactMapper;
    private final HarmMapper harmMapper;
    private final KingdeeUtil kingdeeUtil;
    private final SysBranchMapper sysBranchMapper;
    private final SysFunctionBranchService sysFunctionBranchService;
    private final WsUserService wsUserService;
    private final WsUserRoleService wsUserRoleService;

    /**
     * 分页查询[我的客户表]列表
     *
     * @param page  分页参数
     * @param param Sellcustomer查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Sellcustomer> getPage(PageParam<Sellcustomer> page, SellcustomerParam param) {
        String cId = SecurityUtils.getCId();
        //判断是否拥有[决策管理]权限：有则查询所有数据，无则查询当前用户所在分中心数据
        if (SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE)) {
            //查询所有
        } else if (!SecurityUtils.isLeader()) {
            //不是领导,获取所有或根据条件进行查询
            param.setXsjlid(SecurityUtils.getUserNo());
        } else {
            // 领导查看下级的数据
            List<String> lowerLevelIds = sysUserMapper.getLowerLevelId(SecurityUtils.getUserNo());
            lowerLevelIds.add(SecurityUtils.getUserNo());
            param.setLowerLevelIds(lowerLevelIds);
        }
        return sellcustomerMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Sellcustomer getInfoById(String id) {
        Sellcustomer sellcustomer = sellcustomerMapper.getInfoById(id);
        if (ObjectUtils.isEmpty(sellcustomer)){
            throw new ServiceException("客户不存在！");
        }
        //职业病危害因素
        String zybwhys = sellcustomer.getZybwhys();
        if (ObjectUtils.isNotEmpty(zybwhys)) {
            String harms = "";
            List<Harm> harmList = harmMapper.selectList(new QueryWrapper<Harm>().in("id", zybwhys.split(",")));
            for (Harm harm : harmList) {
                harms += harm.getHarmName() + ",";
            }
            if (harms.length() > 0) {
                sellcustomer.setZybwhyName(harms.substring(0, harms.length() - 1));
            }
        }
        List<Savefilepath> savefilepaths = savefilepathMapper.selectList(new LambdaQueryWrapper<Savefilepath>()
                .eq(Savefilepath::getGgid, sellcustomer.getId()));
        List<String> filePaths = savefilepaths.stream().map(Savefilepath::getFilepath).collect(Collectors.toList());
        sellcustomer.setFilePaths(filePaths);
        return sellcustomer;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public String saOrUp(Sellcustomer sellcustomer) {
        String id = sellcustomer.getId();
        //判断该记录是否存在
        if (StringUtils.isNotBlank(id)) {
            //编辑
            //获取原先记录
            Sellcustomer sellCus = sellcustomerMapper.selectOne(new QueryWrapper<Sellcustomer>().eq("id", sellcustomer.getId()).eq("is_delete", 0));
            if (Objects.nonNull(sellCus)) {
                sellCus.copyFrom(sellcustomer);
                sellCus.setJinanStatus(-1);
                if (!"".equals(sellCus.getKhdwmc())
                        && !"".equals(sellCus.getSocialCreditCode())
                        && !"".equals(sellCus.getStreet())
                        && !"".equals(sellCus.getIndusTypeCode())
                        && !"".equals(sellCus.getEconomyCode())
                        && !"".equals(sellCus.getCrptSizeCode())
                        && sellCus.getWorkForce() != null
                        && sellCus.getHoldCardMan() != null
                        && !"".equals(sellCus.getKhdwzcdz())
                        && !"".equals(sellCus.getLinkman2())
                        && !"".equals(sellCus.getLinkphone2())
                        && !"".equals(sellCus.getUnitarea())
                ) {
                    sellCus.setJinanStatus(0);
                }
                sellCus.setModifydate(new Date());
                sellcustomerMapper.updateById(sellCus);
            } else {
                throw new ServiceException("更新失败，更新对象不存在或者已被删除");
            }
        } else {
            //设置初始值,执行保存操作
            sellcustomer.setKhzt(CustomerStatus.QIANZAI.value());
            sellcustomer.setIsDelete(0);
            sellcustomer.setXsjlid(SecurityUtils.getUserNo());
            sellcustomer.setFzxid((SecurityUtils.getCId()));
            id = String.valueOf(snowflake.nextId());
            sellcustomer.setId(id);
            sellcustomer.setCreatedate(new Date());
            sellcustomer.setJinanStatus(JinanStatus.UNABLE.value());

            if (!"".equals(sellcustomer.getKhdwmc())
                    && !"".equals(sellcustomer.getSocialCreditCode())
                    && !"".equals(sellcustomer.getStreet())
                    && !"".equals(sellcustomer.getIndusTypeCode())
                    && !"".equals(sellcustomer.getEconomyCode())
                    && !"".equals(sellcustomer.getCrptSizeCode())
                    && sellcustomer.getWorkForce() != null
                    && sellcustomer.getHoldCardMan() != null
                    && !"".equals(sellcustomer.getKhdwzcdz())
                    && !"".equals(sellcustomer.getLinkman2())
                    && !"".equals(sellcustomer.getLinkphone2())
                    && !"".equals(sellcustomer.getUnitarea())
            ) {
                sellcustomer.setJinanStatus(JinanStatus.PENDING.value());
            }
            //保存数据
            Long count = 0L;
            Integer intId = 0;
            Integer lastIntId = 1;
            Sellcustomer sellcustomer1 = sellcustomerMapper.selectOne(new LambdaQueryWrapper<Sellcustomer>().orderByDesc(Sellcustomer::getIntId).last("limit 1"));
            if (ObjectUtils.isNotEmpty(sellcustomer1)) {
                lastIntId = sellcustomer1.getIntId();
            }
            do {
                intId = CodeUtil.getIntId();
            } while (intId < lastIntId);
            do {
                count = sellcustomerMapper.selectCount(new LambdaQueryWrapper<Sellcustomer>().eq(Sellcustomer::getIntId, intId));
                if (count > 0) intId = CodeUtil.getIntId();
//
            } while (count > 0);
            sellcustomer.setIntId(intId);
            sellcustomerMapper.insert(sellcustomer);
        }

        return id;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(List<String> ids) {
        //进行假删
        for (String id : ids) {
            //获取实体,修改假删状态
            Sellcustomer sellCustomer = sellcustomerMapper.selectOne(new QueryWrapper<Sellcustomer>().eq("id", id).eq("is_delete", 0));
            if (Objects.nonNull(sellCustomer)) {
                if ("1".equals(sellCustomer.getKhzt())) {
                    throw new ServiceException(sellCustomer.getKhdwmc() + "是正式客户不能删除。");
                }
                if (StringUtils.isNotEmpty(sellCustomer.getClientid())) {
                    //是从客户公共池领取的,判断客户公共池里这条记录是否还存在,存在修改领取人记录与领取次数;客户公共池记录被假删了,修改信息无意义,不进行任何操作
                    Clientcommon clientCommon = clientcommonMapper.selectOne(new QueryWrapper<Clientcommon>().eq("id", sellCustomer.getClientid()).eq("is_delete", 0));
                    //判断该记录是否还存在在客户公共池里
                    if (Objects.nonNull(clientCommon)) {
                        //假删领取人记录
                        Receiveandsell receiveAndSell = receiveandsellMapper.selectOne(new QueryWrapper<Receiveandsell>().eq("xsjlid", sellCustomer.getXsjlid()).eq("clientid", clientCommon.getId()).eq("is_delete", 0));
                        if (receiveAndSell != null) {
                            //进行假删
                            sellCustomer.setIsDelete(1);
                            sellCustomer.setModifydate(new Date());
                            sellcustomerMapper.updateById(sellCustomer);
                            //记录存在,进行假删
                            receiveAndSell.setIsDelete(1);
                            receiveAndSell.setModifydate(new Date());
                            receiveandsellMapper.updateById(receiveAndSell);
                            //修改客户公共池领取次数减1
                            clientCommon.setLqcstj(clientCommon.getLqcstj() - 1);
                            clientCommon.setModifydate(new Date());
                            clientcommonMapper.updateById(clientCommon);
                        }
                    } else {
                        //当初领取的记录已经不存在在客户公共池里
                        sellCustomer.setIsDelete(1);
                        sellCustomer.setModifydate(new Date());
                        sellcustomerMapper.updateById(sellCustomer);
                    }
                } else if (StringUtils.isNotBlank(sellCustomer.getLdfpid())) {
                    //是领导分配记录,假删掉分配给销售时留下的记录,不进行修改【领取】次数
                    Clientcommon clientCommon = clientcommonMapper.selectOne(new QueryWrapper<Clientcommon>().eq("id", sellCustomer.getLdfpid()).eq("is_delete", 0));
                    //判断该记录是否还存在在客户公共池里
                    if (clientCommon != null) {
                        //假删领取人记录
                        Receiveandsell receiveAndSell = receiveandsellMapper.selectOne(new QueryWrapper<Receiveandsell>().eq("xsjlid", sellCustomer.getXsjlid()).eq("clientid", clientCommon.getId()).eq("is_delete", 0));
                        if (receiveAndSell != null) {
                            //进行假删
                            sellCustomer.setIsDelete(1);
                            sellCustomer.setModifydate(new Date());
                            sellcustomerMapper.updateById(sellCustomer);
                            //记录存在,进行假删
                            receiveAndSell.setIsDelete(1);
                            receiveAndSell.setModifydate(new Date());
                            receiveandsellMapper.updateById(receiveAndSell);
                        }
                    } else {
                        //客户公共池里记录已被假删,进行客户的假删
                        sellCustomer.setIsDelete(1);
                        sellCustomer.setModifydate(new Date());
                        sellcustomerMapper.updateById(sellCustomer);
                    }
                } else {
                    //不是从客户公共池领取的,也不是领导分配的,进行假删
                    sellCustomer.setIsDelete(1);
                    sellCustomer.setModifydate(new Date());
                    sellcustomerMapper.updateById(sellCustomer);
                }
            } else {
                throw new ServiceException("删除失败，找不到ID为[" + id + "的客户信息");
            }
        }
        return Boolean.TRUE;
    }


//    @Override
//    public String compressToDownLoad(String saveFilePathId) {
//        //获取关联的文件路径
//        List<Savefilepath> list = savefilepathMapper.selectList(new QueryWrapper<Savefilepath>().eq("ggid", saveFilePathId));
//        //判断是否有文件
//        if (list.size() != 0) {
//            byte[] buffer = new byte[1024];
//            //获取公用文件路径
//            String filePath = list.get(0).getFilepath();
//            //TODO 封装一个文件压缩工具类
//            //TODO ？还没解析文件地址
//            filePath = getPropert("doc_config.properties", "real_path") + filePath;
//            try {
//                //截取字符串
//                String path = filePath.substring(0, filePath.lastIndexOf("\\") + 1) + URLEncoder.encode("ZK", "UTF-8") + ".zip";
//                File oldFile = new File(path);
//                if (oldFile.exists()) {
//                    oldFile.delete();
//                }
//                ZipOutputStream out = new ZipOutputStream(new FileOutputStream(path));
////                out.setEncoding("UTF-8");
//                //遍历
//                for (Savefilepath saveFilePath : list) {
//                    String fileName = saveFilePath.getFilepath().substring(saveFilePath.getFilepath().lastIndexOf("\\") + 1);
//                    File file = new File(getPropert("doc_config.properties", "real_path") + saveFilePath.getFilepath());
//                    FileInputStream fis = new FileInputStream(file);
//                    out.putNextEntry(new ZipEntry(fileName));
//                    int len;
//                    //读入需要下载的文件的内容，打包到zip文件
//                    while ((len = fis.read(buffer)) > 0) {
//                        out.write(buffer, 0, len);
//                    }
//                    out.closeEntry();
//                    fis.close();
//                }
//                out.close();
//                return "success," + path;
//            } catch (Exception e) {
//                e.printStackTrace();
//                return "false,系统异常,请联系管理员！";
//            }
//        } else {
//            return "false,您还未上传过文件！";
//        }
//    }


    public static String getPropert(String property, String name) {
        String url = "";
        try {
            ClassLoader cls = Thread.currentThread().getContextClassLoader();
            InputStream in = cls.getResourceAsStream(property);
            Properties p = new Properties();

            p.load(in);
            if (p.containsKey(name)) {
                url = p.getProperty(name);
            }
        } catch (Exception e) {
        }

        return url;
    }

//    @Override
//    public void downLoad(String path, HttpServletResponse res) {
//        String fileName = "";
//        try {
//            fileName = URLEncoder.encode(path.substring(path.lastIndexOf("\\") + 1, path.length()), "UTF-8");
//        } catch (UnsupportedEncodingException e1) {
//            e1.printStackTrace();
//        }
//        //TODO 封装一个文件下载工具类
//        OutputStream os = null;
//        BufferedInputStream bis = null;
//        File file = new File(path);
//        Long length = file.length();
//        try {
//            res.setHeader("Content-Length", String.valueOf(length));
//            res.setContentLength(length.intValue());
//            res.setContentType("APPLICATION/x-download");
//            res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
//            os = res.getOutputStream();
//            FileInputStream fis = new FileInputStream(file);
//            bis = new BufferedInputStream(fis);
//            bis.available();
//            byte[] b = new byte[10];
//            int line;
//            while ((line = bis.read(b)) != -1) {
//                os.write(b, 0, line);
//            }
//            os.flush();
//            os.close();
//            bis.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    /**
     * 客户释放/客户流失
     *
     * @param ids   操作的对象主键id集合
     * @param state 操作标识：0.客户流失 1.客户释放
     * @return
     */
    @Override
    public Boolean customerRelease(List<String> ids, Integer state) {
        //判断该记录是否是从客户公共池领取的
        for (String id : ids) {
            Sellcustomer sellCustomer = sellcustomerMapper.selectOne(new QueryWrapper<Sellcustomer>().eq("id", id).eq("is_delete", 0));
            if (Objects.nonNull(sellCustomer)) {
                //记录存在,判断是否从客户公共池领取的
                if (StringUtils.isNotEmpty(sellCustomer.getClientid())) {
                    //是从客户公共池领取的,判断客户公共池是否还存在该记录,要是有,只进行修改领取次数;若没有,进行新增,设置领取次数为0
                    Clientcommon clientCommon = clientcommonMapper.selectOne(new QueryWrapper<Clientcommon>().eq("id", sellCustomer.getClientid()).eq("is_delete", 0));
                    if (Objects.nonNull(clientCommon)) {
                        //修改我的客户为释放
                        sellCustomer.setKhzt(CustomerStatus.SHIFANG.value());
                        sellcustomerMapper.updateById(sellCustomer);
                        //修改领取次数
                        BeanUtils.copyProperties(sellCustomer, clientCommon
                                , new String[]{"clientid", "is_delete", "khzt", "ldfpid", "sjrq", "id", "khdwzcdz"});
                        clientCommon.setKhdwdz(sellCustomer.getKhdwzcdz());
                        clientCommon.setLqcstj(clientCommon.getLqcstj() - 1);
                        clientcommonMapper.updateById(clientCommon);
                        //删掉领取人
                        List<Receiveandsell> data = receiveandsellMapper.selectList(new QueryWrapper<Receiveandsell>().eq("clientid", clientCommon.getId()).eq("is_delete", 0));
                        for (Receiveandsell receiveAndSell : data) {
                            receiveAndSell.setIsDelete(1);
                            receiveandsellMapper.updateById(receiveAndSell);
                        }
                    } else {
                        //客户公共池记录已经被删,进行释放添加
                        sellCustomer.setKhzt(CustomerStatus.SHIFANG.value());
                        sellcustomerMapper.updateById(sellCustomer);
                        Clientcommon clientsCommon = new Clientcommon();
                        BeanUtils.copyProperties(sellCustomer, clientsCommon
                                , new String[]{"clientid", "is_delete", "khzt", "ldfpid", "sjrq", "id", "khdwzcdz"});
//                        clientsCommon.setKhdwmc(sellCustomer.getKhdwmc());
//                        clientsCommon.setKhdwsrm(sellCustomer.getKhdwsrm());
//                        clientsCommon.setKhdh(sellCustomer.getKhdh());
//                        clientsCommon.setSshy(sellCustomer.getSshy());
//                        clientsCommon.setXsjl(sellCustomer.getXsjl());
//                        clientsCommon.setXsjlid(sellCustomer.getXsjlid());
//                        clientsCommon.setDwjgdm(sellCustomer.getDwjgdm());
                        clientsCommon.setKhdwdz(sellCustomer.getKhdwzcdz());
//                        clientsCommon.setFzxid(sellCustomer.getFzxid());
                        clientsCommon.setLqcstj(0);
                        clientsCommon.setIsDelete(0);
                        clientsCommon.setFpzt("0");
                        clientcommonMapper.insert(clientsCommon);
                    }
                } else if (StringUtils.isNotEmpty(sellCustomer.getLdfpid())) {
                    //如果是领导分配的
                    Receiveandsell receiveAndSell = receiveandsellMapper.selectOne(new QueryWrapper<Receiveandsell>().eq("clientid", sellCustomer.getLdfpid()).eq("xsjlid", sellCustomer.getXsjlid()).eq("is_delete", 0));
                    if (receiveAndSell != null) {
                        sellCustomer.setKhzt(CustomerStatus.SHIFANG.value());
                        sellcustomerMapper.updateById(sellCustomer);
                        receiveAndSell.setIsDelete(1);
                        receiveandsellMapper.updateById(receiveAndSell);
                    }
                    Clientcommon clientsCommon = clientcommonMapper.selectOne(new QueryWrapper<Clientcommon>().eq("id", sellCustomer.getLdfpid()).eq("is_delete", 0));//升级为正式后  公共池被删除其他非正式同名客户被删除
                    if (clientsCommon != null) {
                        BeanUtils.copyProperties(sellCustomer, clientsCommon
                                , new String[]{"clientid", "is_delete", "khzt", "ldfpid", "sjrq", "id", "khdwzcdz"});
                        clientsCommon.setKhdwdz(sellCustomer.getKhdwzcdz());
                        clientcommonMapper.updateById(clientsCommon);
                    } else {
                        clientsCommon = new Clientcommon();
                        BeanUtils.copyProperties(sellCustomer, clientsCommon
                                , new String[]{"clientid", "is_delete", "khzt", "ldfpid", "sjrq", "id", "khdwzcdz"});
                        clientsCommon.setKhdwdz(sellCustomer.getKhdwzcdz());
                        clientsCommon.setIsDelete(0);
                        clientsCommon.setLqcstj(0);
                        clientsCommon.setFpzt("0");
                        clientcommonMapper.insert(clientsCommon);
                    }
                } else {
                    //不是从客户公共池领取的
                    sellCustomer.setKhzt(CustomerStatus.SHIFANG.value());
                    sellcustomerMapper.updateById(sellCustomer);
                    Clientcommon clientsCommon = new Clientcommon();
                    BeanUtils.copyProperties(sellCustomer, clientsCommon
                            , new String[]{"clientid", "is_delete", "khzt", "ldfpid", "sjrq", "id", "khdwzcdz"});
//                    clientsCommon.setKhdwmc(sellCustomer.getKhdwmc());
//                    clientsCommon.setKhdwsrm(sellCustomer.getKhdwsrm());
//                    clientsCommon.setKhdh(sellCustomer.getKhdh());
//                    clientsCommon.setSshy(sellCustomer.getSshy());
//                    clientsCommon.setXsjl(sellCustomer.getXsjl());
//                    clientsCommon.setXsjlid(sellCustomer.getXsjlid());
//                    clientsCommon.setDwjgdm(sellCustomer.getDwjgdm());
                    clientsCommon.setKhdwdz(sellCustomer.getKhdwzcdz());
//                    clientsCommon.setFzxid(sellCustomer.getFzxid());
                    clientsCommon.setLqcstj(0);
                    clientsCommon.setIsDelete(0);
                    clientsCommon.setFpzt("0");
                    clientcommonMapper.insert(clientsCommon);
                }
                /*插入历史记录*/
                LoginUser loginUser = SecurityUtils.getLoginUser();
                CustomerOperateHistory history = new CustomerOperateHistory(String.valueOf(loginUser.getUserId())
                        , id
                        , sellCustomer.getKhdwmc()
                        , CustomerStatus.getName(state)
                        , sellCustomer.getXsjlid()
                        , loginUser.getUser().getUserName()
                        , loginUser.getUsername()
                        , null
                        , null);
                customerOperateHistoryMapper.insert(history);
            }
        }
        return Boolean.TRUE;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean upgrade(List<String> ids, String centerOrgName) {
        //存储非正式记录id
        List sjId = new ArrayList();
        //存储正式记录id
        List zsId = new ArrayList();
        //接收客户单位名称
        String khdwmc = "";
        //所选客户中被升级的
        List<Sellcustomer> customers = new ArrayList<Sellcustomer>();
        for (String id : ids) {
            //得到客户单位名称,客户单位名称唯一
            Sellcustomer sellCustomer = this.getOne(new QueryWrapper<Sellcustomer>().eq("id", id).eq("is_delete", 0));
            if (Objects.isNull(sellCustomer)) {
                continue;
            }

            //判断该分中心是否启用金蝶
            SysBranch defaultBranch = sysBranchMapper.getDefaultBranch();
            Integer count = sysFunctionBranchService.getByfunIdAndCid(1,defaultBranch.getBranchId());
            if (count >= 1){
                String licenseName = sellCustomer.getLicenseName();
                String isKingdeeUpdate = KingdeeConfig.isKingdeeUpdate;
                if (isKingdeeUpdate != null && isKingdeeUpdate.equals("1")) {

                }
                // TODO: 需要完善客户升级相关的业务逻辑
                if (StrUtil.isEmpty(centerOrgName)) {
                    throw new ServiceException("请联系管理员为分中心维护当前中心组织名");
                }

                try {
                    log.info("-------开始获取金蝶数据!-------");
                    String centerOrgNameEncrypted = KingdeeUtil.EncryptDES(centerOrgName);
                    String json = kingdeeUtil.send(KingdeeConstants.METHOD_GET_CUSTOMER, "OrgName", centerOrgNameEncrypted);
                    JSONObject jo = JSONUtil.parseObj(json);
                    log.info("-------获取金蝶数据成功!-------");
                    boolean flg=false;
                    //匹配到就将ID保存到金蝶中

                    JSONArray ja = jo.getJSONArray("Customer");
                    for (int i = 0; i < ja.size(); i++) {
                        JSONObject customerJo = ja.getJSONObject(i);
                        if(customerJo.getStr("NAME").equals(licenseName)){
                            sellCustomer.setJindieId(customerJo.getStr("NAME"));
                            flg=true;
                        }
                    }

                    if(!flg){
                        throw new ServiceException("升级失败，请查看是否维护执照名称，或联系财务维护财务客户名称");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ServiceException("金蝶获取失败"+e.getMessage());
                }
            }


            //获取本分中心下未删除的，客户单位名称相同的，但不是当前id的客户集合
            List<Sellcustomer> data = sellcustomerMapper.selectList(new QueryWrapper<Sellcustomer>().eq("khdwmc", khdwmc).ne("id", id).ne("khzt", "2").eq("fzxid", sellCustomer.getFzxid()).eq("is_delete", 0));
            if (data.size() != 0) {
                //遍历集合查看是否已经有升级的记录,有升级的记录则把其它未升级的记录假删掉
                for (Sellcustomer sc : data) {
                    if (!Objects.equals(sc.getKhzt(), 1)) {
                        //将非正式的记录添加到集合,等待删除掉
                        sjId.add(sc.getId());
                    } else {
                        //存储正式记录的id,用于判断是否存在正式记录
                        zsId.add(sc.getId());
                    }
                }
                //前后遍历的集合数量不一致,说明存在正式的客户
                if (zsId.size() != 0) {
                    //出现正式记录
                    sjId.add(sellCustomer.getId());
                    //获取集合中非正式记录的id,进行假删
                    for (int j = 0; j < sjId.size(); j++) {
                        Sellcustomer sellCus = sellcustomerMapper.selectOne(new QueryWrapper<Sellcustomer>().eq("id", sjId.get(j)).eq("is_delete", 0));
                        if (null != sellCus) {
                            //假删掉数据
                            sellCus.setIsDelete(1);
                            sellcustomerMapper.updateById(sellCus);
                        }
                    }
                    //将集合清空
                    sjId.clear();
                    zsId.clear();
                } else {
                    //未出现升级的记录,则把当前的这条记录升级为正式,升级为正式后，让客户公共池中原先的记录消失
                    //升级当前记录为正式
                    sellCustomer.setKhzt(CustomerStatus.ZHENGSHI.value());
                    //升级日期
                    sellCustomer.setSjrq(new Date());
                    sellcustomerMapper.updateById(sellCustomer);
                    //获取集合中非正式记录的id,进行假删
                    for (int j = 0; j < sjId.size(); j++) {
                        Sellcustomer sellCus = new Sellcustomer();
                        //获取id进行假删
                        sellCus = sellcustomerMapper.selectOne(new QueryWrapper<Sellcustomer>().eq("id", sjId.get(j)).eq("is_delete", 0));
                        if (null != sellCus) {
                            //假删掉数据
                            sellCus.setIsDelete(1);
                            sellcustomerMapper.updateById(sellCus);
                        }
                    }
                    //将集合清空
                    sjId.clear();
                    zsId.clear();
                    customers.add(sellCustomer);
                }
            } else {
                //升级当前记录为正式
                sellCustomer.setKhzt(CustomerStatus.ZHENGSHI.value());
                //升级日期
                sellCustomer.setSjrq(new Date());
                sellcustomerMapper.updateById(sellCustomer);
                customers.add(sellCustomer);
            }


            //若记录为领取的记录,升级为正式后,客户公共池里这条数据随即删除
            if (StringUtils.isNotEmpty(sellCustomer.getClientid())) {
                //为领取记录,进行假删
                Clientcommon clientCommon = clientcommonMapper.selectOne(new QueryWrapper<Clientcommon>().eq("id", sellCustomer.getClientid()).eq("is_delete", 0));
                if (clientCommon != null) {
                    //该记录存在,进行假删操作
                    clientCommon.setIsDelete(1);
                    clientCommon.setModifydate(new Date());
                    clientcommonMapper.updateById(clientCommon);
                }
            }
            //若记录为领导分配的记录,升级为正式后,客户公共池里这条记录随即删除
            if (StringUtils.isNotEmpty(sellCustomer.getLdfpid())) {
                //为领导分配的记录,升级为正式后,客户公共池这条记录随即删除
                Clientcommon clientCommon = clientcommonMapper.selectOne(new QueryWrapper<Clientcommon>().eq("id", sellCustomer.getLdfpid()).eq("is_delete", 0));
                if (clientCommon != null) {
                    //该记录存在,进行假删操作
                    clientCommon.setIsDelete(1);
                    clientCommon.setModifydate(new Date());
                    clientcommonMapper.updateById(clientCommon);
                }
            }
        }
        //生成企业网站账号 部门
        for (Sellcustomer customer : customers) {
            sellcustomerMapper.updateById(customer);
        }

        return Boolean.TRUE;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String generateAccount(List<String> ids) {
        List<Sellcustomer> customers = sellcustomerMapper.selectList(new QueryWrapper<Sellcustomer>().in("id", ids));
        //插入网站客户
        if(customers.size() > 0){
            //网站客户默认权限 管理员
                for(Sellcustomer customer:customers){
                    //账号已生成
                    long count = wsUserService.count(new LambdaQueryWrapper<WsUser>().eq(WsUser::getCustomerId, customer.getId()));
                    if(count > 0){
                        continue;
                    }
                    String customerId = customer.getId();

                    //插入用户
                    //生成一个唯一的username
                    String username = customer.getIntId().toString()+"_"+((int)((Math.random()*9+1)*1000));
                    while(wsUserService.count(new LambdaQueryWrapper<WsUser>().eq(WsUser::getUserName, username)) > 0){
                        username=username+"_"+((int)((Math.random()*9+1)*1000));
                    }
                    WsUser wu = new WsUser(
                            100L,
                            username,//账号
                            customer.getKhdwmc(),
                            customer.getKhdh(),
                            SecurityUtils.encryptPassword(customer.getKhdh()),//密码
                            SecurityUtils.getUsername(),
                            new Date(),
                            customerId
                    );
                    wsUserService.save(wu);

                    //用户和角色关联表 默认为普通用户
                    WsUserRole wsUserRole = new WsUserRole(wu.getUserId(),2L);
                    wsUserRoleService.save(wsUserRole);
                }
            }else{
                throw new ServiceException("网站权限未维护");
            }

        return "success";
    }





    @Override
    public void export(HttpServletResponse response, SellcustomerParam param, List<SysRole> sysRoles, String fileName) {
        try {
            LoginUser loginUser = SecurityUtils.getLoginUser();
            //表格标题
            String[] tableCaption = {"序列", "客户单位名称", "客户单位输入码", "单位机构代码", "所属行业", "客户单位联系人", "客户电话", "销售经理", "客户单位注册地址", "备注", "客户状态"};
            List<ArrayList> tableData = new ArrayList<>();
            //判断当前登录用户是否是领导
            Boolean isLeader = SecurityUtils.isLeader();
            List<Sellcustomer> data = null;
            List<String> roleNameList = sysRoles.stream().map(s -> s.getRoleName()).collect(Collectors.toList());
            boolean greatLeader = roleNameList.contains("决策管理");
            if (greatLeader) {
                //如果搜索条件不为空就加上搜索条件
                QueryWrapper<Sellcustomer> queryWrapper = new QueryWrapper<Sellcustomer>().orderByDesc("createDate").ne("khzt", "2").eq("is_delete", 0);
                if (ObjectUtils.isNotEmpty(param.getKhdwmc())) {
                    queryWrapper.like("khdwmc", param.getKhdwmc());
                }
                if (ObjectUtils.isNotEmpty(param.getKhdwsrm())) {
                    queryWrapper.like("khdwsrm", param.getKhdwsrm().trim().toUpperCase());
                }
                data = sellcustomerMapper.selectList(queryWrapper);
            } else if (!isLeader) {
                //不是领导,获取所有或根据条件进行查询
                QueryWrapper<Sellcustomer> queryWrapper = new QueryWrapper<Sellcustomer>().orderByDesc("createDate")
                        .eq("xsjlid", loginUser.getUserId()).ne("khzt", "2").eq("is_delete", 0);
                if (ObjectUtils.isNotEmpty(param.getKhdwmc())) {
                    queryWrapper.like("khdwmc", param.getKhdwmc());
                }
                if (ObjectUtils.isNotEmpty(param.getKhdwsrm())) {
                    queryWrapper.like("khdwsrm", param.getKhdwsrm().trim().toUpperCase());
                }
                data = sellcustomerMapper.selectList(queryWrapper);
            } else {
                List<String> lowerLevelIds = sysUserMapper.getLowerLevelId(SecurityUtils.getUserNo());
                lowerLevelIds.add(SecurityUtils.getUserNo());
                //是领导,展现领导所属分中心下的信息
                QueryWrapper<Sellcustomer> queryWrapper = new QueryWrapper<Sellcustomer>().orderByDesc("createDate").orderByAsc("khdwmc")
                        .in("xsjlid", lowerLevelIds).ne("khzt", "2").eq("is_delete", 0);
                if (ObjectUtils.isNotEmpty(param.getKhdwmc())) {
                    queryWrapper.like("khdwmc", param.getKhdwmc());
                }
                if (ObjectUtils.isNotEmpty(param.getKhdwsrm())) {
                    queryWrapper.like("khdwsrm", param.getKhdwsrm().trim().toUpperCase());
                }
                data = sellcustomerMapper.selectList(queryWrapper);
            }
            //表格页脚
            ArrayList footData = new ArrayList();
            footData.add("导出人:" + ExcelUtil.toString(loginUser.getUsername()));
            footData.add("导出日期:" + ExcelUtil.toString(new Date()));
            ExcelUtil excelUtil = new ExcelUtil(Sellcustomer.class);
            excelUtil.exportExcel(response, data, StringUtils.isNotBlank(fileName) ? fileName : "我的客户");


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public IPage<Sellcustomer> getAllCompany(PageParam<Sellcustomer> page, String key) {
        QueryWrapper<Sellcustomer> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isBlank(key)) {
            queryWrapper.like("khdwsrm", key.toUpperCase());
        }
        queryWrapper.eq("is_delete", 0);
        PageParam<Sellcustomer> sellcustomerPageParam = sellcustomerMapper.selectPage(page, queryWrapper);
        return sellcustomerPageParam;
    }

    /**
     * 导入客户信息
     *
     * @param list     客户信息列表
     * @param operName 操作者名称
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Boolean importSellcustomer(List<Sellcustomer> list, String operName) {
        //判断excel中添加的客户单位名称是否存在重复
        List<String> result = list.stream()
                .map(Sellcustomer::getKhdwmc)
                .collect(Collectors.toMap(e -> e, e -> 1, Integer::sum))
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        log.info("重复判断结果：{}", result);
        if (CollectionUtils.isNotEmpty(result)) {
            log.warn("导入客户信息失败，存在相同的客户名称: [{}]", list);
            throw new ServiceException(String.format("%s：%s", "文件导入失败，要导入的数据中存在相同的客户单位名称：", StringUtils.join(",", list)));
        }
        int i = 1;
        for (Sellcustomer item : list) {
            if (StringUtils.isEmpty(item.getKhdwmc())) {
                throw new ServiceException("文件导入失败：第" + i + "条记录的客户单位名称未填写！");
            }
            if (StringUtils.isEmpty(item.getSocialCreditCode())) {
                throw new ServiceException("文件导入失败：第" + i + "条记录的社会信用代码未填写！");
            }
            if (StringUtils.isEmpty(item.getKhdwlxr())) {
                throw new ServiceException("文件导入失败：第" + i + "条记录的客户单位联系人未填写！");
            }
            if (StringUtils.isEmpty(item.getKhdh())) {
                throw new ServiceException("文件导入失败：第" + i + "条记录的客户电话未填写！");
            }
            //验证输入的客户名称是否在该销售名下存在重复
            if (StringUtils.isNotBlank(onBlur(item.getKhdwmc()))) {
                throw new ServiceException("文件导入失败：第" + i + "条记录的客户单位名称：" + item.getKhdwmc() + "已存在,不能重复添加！");
            }
            //验证邮政编码和电话格式
            if (StringUtils.isNotBlank(item.getYzbm()) && !Pattern.matches("[1-9]\\d{5}", item.getYzbm())) {
                //判断邮政编码是否6位,整数,大于0
                throw new ServiceException("文件导入失败：第" + i + "条记录的邮政编码格式不正确,应为6位数字编码！");
            }
            //判断客户电话,20位不能为中、英文
            Matcher matcher = Pattern.compile("[\\s\\S]*[\\u4e00-\\u9fa5a-zA-Z]+[\\s\\S]*").matcher(item.getKhdh());
            if (matcher.matches()) {
                throw new ServiceException("文件导入失败：第" + i + "条记录的客户电话存在中文或英文！");
            }
            if (item.getKhdh().length() > 20) {
                throw new ServiceException("文件导入失败：第" + i + "条记录的客户电话长度最多20位！");
            }
            //设置客户单位输入码
            item.setKhdwsrm(ToolUtil.getHanziPinyinHeadChar(item.getKhdwmc()));
            item.setXsjl(operName);
            //设置体检团体类型
            item.setTjttlx(0);
            //设置客户状态
            item.setKhzt(0);
            //设置假删状态
            item.setIsDelete(0);
            //设置销售经理id
            item.setXsjlid(SecurityUtils.getUserNo());
            //获取当前登录用户分中心id
            item.setFzxid(SecurityUtils.getCId());
            //intId
            Long count = 0L;
            Integer intId = 0;
            Integer lastIntId = 1;
            Sellcustomer sellcustomer1 = sellcustomerMapper.selectOne(new LambdaQueryWrapper<Sellcustomer>().orderByDesc(Sellcustomer::getIntId).last("limit 1"));
            if (ObjectUtils.isNotEmpty(sellcustomer1)) {
                lastIntId = sellcustomer1.getIntId();
            }
            do {
                intId = CodeUtil.getIntId();
            } while (intId < lastIntId);
            do {
                count = sellcustomerMapper.selectCount(new LambdaQueryWrapper<Sellcustomer>().eq(Sellcustomer::getIntId, intId));
                if (count > 0) intId = CodeUtil.getIntId();
//
            } while (count > 0);
            item.setIntId(intId);
        }
        //进行实体保存
        saveBatch(list);
        return Boolean.TRUE;
    }

    /**
     * 验证输入的客户名称是否在该销售名下存在重复
     *
     * @param cusName 客户单位名称
     * @return "success":存在重复 "":不存在
     */
    @Override
    public String onBlur(String cusName) {
        String state = "";
        cusName = cusName.replaceAll(" ", "");
        Long count = sellcustomerMapper.selectCount(new LambdaQueryWrapper<Sellcustomer>()
                .eq(Sellcustomer::getKhdwmc, cusName).eq(Sellcustomer::getIsDelete, 0));
        Long count1 = clientcommonMapper.selectCount(new LambdaQueryWrapper<Clientcommon>()
                .eq(Clientcommon::getKhdwmc, cusName).eq(Clientcommon::getIsDelete, 0));
        if (count > 0 || count1 > 0) {
            //销售名下存在相同的客户名称
            return "success";
        }
        return state;
    }


    /**
     * 获取所有的客户
     *
     * @param page
     * @param key
     * @return
     */
    @Override
    public IPage<AllOrgVo> getAllOrg(PageParam<AllOrgVo> page, String key) {
        //去空格
        if (ObjectUtils.isNotEmpty(key)) {
            key = key.trim();
        }
        IPage<AllOrgVo> iPage = sellcustomerMapper.getAllOrg(page, key);
        return iPage;
    }

    /**
     * 进行领导分配
     *
     * @param clientIds 公共池记录ID集合
     * @param xsIds     销售ID列表
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean allocation(List<String> clientIds, List<String> xsIds) {
        StringBuffer state = new StringBuffer("");
        //进行领导分配,为多个人分配选择的记录
        for (int i = 0; i < xsIds.size(); i++) {
            for (int j = 0; j < clientIds.size(); j++) {
                //判断要分配的记录在分配之前是否已被删除,若被删除是分配不成功的
                Clientcommon clientCommon = clientcommonMapper.selectOne(new QueryWrapper<Clientcommon>().eq("id", clientIds.get(j)));
                //判断记录是否存在
                if (ObjectUtils.isNotEmpty(clientCommon) && clientCommon.getIsDelete() == 0) {
                    //进行分配
                    String sId = null;
                    Sellcustomer sellCustomer = sellcustomerMapper.selectOne(new QueryWrapper<Sellcustomer>().eq("khdwmc", clientCommon.getKhdwmc())
                            .eq("is_delete", 0).eq("khzt", "2").eq("xsjlid", xsIds.get(i)));
                    if (ObjectUtils.isEmpty(sellCustomer)) {
                        sellCustomer = new Sellcustomer();
                    }
                    BeanUtils.copyProperties(clientCommon, sellCustomer
                            , new String[]{"fpzt", "is_delete", "lqcstj", "xsjl", "xsjlid", "fzxid", "tjttlx", "id", "khdwdz"});
//                	sellCustomer.setKhdwmc(clientCommon.getKhdwmc());
//                    sellCustomer.setKhdwsrm(clientCommon.getKhdwsrm());
//                    sellCustomer.setKhdwlxr(clientCommon.getKhdwlxr());
//                    sellCustomer.setKhdh(clientCommon.getKhdh());
                    sellCustomer.setKhdwzcdz(clientCommon.getKhdwdz());
//                    sellCustomer.setSshy(clientCommon.getSshy());
//                    sellCustomer.setDwjgdm(clientCommon.getDwjgdm());
                    sellCustomer.setKhzt(0);
                    sellCustomer.setIsDelete(0);
                    SysUser sysUser = sysUserMapper.selectUserByUserNo(xsIds.get(i));
                    sellCustomer.setXsjl(sysUser.getUserName());
                    sellCustomer.setXsjlid(xsIds.get(i));
                    sellCustomer.setLdfpid(clientCommon.getId());
                    sellCustomer.setFzxid(sysUser.getCid());
                    sellCustomer.setClientid(null);
                    //体检团体类型,前台需要
                    sellCustomer.setTjttlx(0);
                    if (ObjectUtils.isEmpty(sellCustomer.getId())) {
                        sellCustomer.setCreatedate(new Date());
                        sellCustomer.setId(String.valueOf(snowflake.nextId()));
                        sellCustomer.setIntId(CodeUtil.getIntId());
                        Long count = 0L;
                        Integer intId = 0;
                        Integer lastIntId = sellcustomerMapper.selectOne(new LambdaQueryWrapper<Sellcustomer>().orderByDesc(Sellcustomer::getIntId).last("limit 1")).getIntId();
                        do {
                            intId = CodeUtil.getIntId();
                        } while (intId < lastIntId);
                        do {
                            count = sellcustomerMapper.selectCount(new LambdaQueryWrapper<Sellcustomer>().eq(Sellcustomer::getIntId, intId));
                            if (count > 0) intId = CodeUtil.getIntId();
//
                        } while (count > 0);
                        sellcustomerMapper.insert(sellCustomer);
                        sId = sellCustomer.getId();
                    } else {
                        sId = sellCustomer.getId();
                        sellCustomer.setModifydate(new Date());
                        sellcustomerMapper.updateById(sellCustomer);
                    }
                    if (StringUtils.isNotEmpty(sId)) {
                        //修改公共池记录的分配状态为‘分配’;0:未分配、1:已分配
                        if (!clientCommon.getFpzt().equals("1")) {
                            clientCommon.setFpzt("1");
                            clientCommon.setModifydate(new Date());
                            clientcommonMapper.updateById(clientCommon);
                        }
                        //操作子表,保存分配记录(哪条记录分配给哪些人)
                        Receiveandsell receiveAndSell = new Receiveandsell();
                        receiveAndSell.setClientid(clientIds.get(j));
                        receiveAndSell.setXsjlid(xsIds.get(i));
                        receiveAndSell.setLqrq(new Date());
                        //分配状态
                        receiveAndSell.setCzzt(1);
                        receiveAndSell.setIsDelete(0);
                        receiveandsellMapper.insert(receiveAndSell);
                    }
                    /*插入历史记录*/
                    CustomerOperateHistory history = new CustomerOperateHistory(SecurityUtils.getUserNo()
                            , sId
                            , clientCommon.getKhdwmc()
                            , "领导分配"
                            , clientCommon.getXsjlid()
                            , sysUserMapper.selectUserByUserNo(clientCommon.getXsjlid()).getUserName()
                            , SecurityUtils.getLoginUser().getUsername()
                            , xsIds.get(i)
                            , sysUserMapper.selectUserByUserNo(xsIds.get(i)).getUserName());
                    customerOperateHistoryMapper.insert(history);
                } else {
                    //正在循环,某条正要分配的记录突然被某人删除
                    String xsName = sysUserMapper.selectUserByUserNo(xsIds.get(i)).getUserName();

                    throw new ServiceException("★正在为销售【" + xsName + "】分配的客户【" + (ObjectUtils.isNotEmpty(clientCommon) ? clientCommon.getKhdwmc() : null) + "】被其他用户从客户公共池删除！分配失败！其它分配成功！");
                }
            }
        }

        return true;
    }

    /**
     * 判断领导分配的记录里面是否存在销售之前领取过的记录
     *
     * @param clientIds 公共池记录ID集合
     * @param xsIds     销售ID列表
     * @return
     */
    @Override
    public boolean distribution(List<String> clientIds, List<String> xsIds) {
        //进行领导分配
        for (int i = 0; i < xsIds.size(); i++) {
            for (int f = 0; f < clientIds.size(); f++) {
                //判断该销售在领导分配之前是否已经领取过该记录
                Sellcustomer sellCustomer = sellcustomerMapper.selectOne(new QueryWrapper<Sellcustomer>().eq("xsjlid", xsIds.get(i))
                        .eq("clientid", clientIds.get(f)).eq("is_delete", 0).ne("khzt", "2"));
                if (ObjectUtils.isNotEmpty(sellCustomer)) {
                    //销售之前领取过,给领导提示信息
                    SysUser qxUsers = sysUserMapper.selectUserByUserNo(xsIds.get(i));
                    Clientcommon clientCommon = clientcommonMapper.selectOne(new QueryWrapper<Clientcommon>().eq("id", clientIds.get(f)).eq("is_delete", 0));
                    if (ObjectUtils.isNotEmpty(qxUsers)) {
                        //存在该用户,获取用户名给予领导提示
                        throw new ServiceException("★分配失败！");
                    }
                    if (ObjectUtils.isNotEmpty(clientCommon)) {
                        throw new ServiceException("销售人员【" + qxUsers.getUserName() + "】之前领取了客户【" + clientCommon.getKhdwmc() + "】,请重新选择！<br/>");
                    } else {
                        throw new ServiceException("要分配的记录被删除！");
                    }
                }
            }
        }
        return true;
    }


    /**
     * 判断领导分配的记录里面是否存在销售之前领取过的记录
     *
     * @param clientIds 公共池记录ID集合
     * @param xsIds     销售ID列表
     * @return
     */
    @Override
    public boolean isAllocation(List<String> clientIds, List<String> xsIds) {
        StringBuffer state = new StringBuffer("");
        //为多个人判断是否之前分配过的多条记录
        for (int i = 0; i < xsIds.size(); i++) {
            for (int j = 0; j < clientIds.size(); j++) {
                //判断之前是否已经把该记录分配给销售过
                Sellcustomer sellCustomer = sellcustomerMapper.selectOne(new QueryWrapper<Sellcustomer>()
                        .eq("ldfpid", clientIds.get(j)).eq("xsjlid", xsIds.get(i))
                        .eq("is_delete", 0).ne("khzt", "2"));
                if (ObjectUtils.isNotEmpty(sellCustomer)) {
                    //之前分配过
                    String xsName = sysUserMapper.selectUserByUserNo(xsIds.get(i)).getUserName();
                    String clientName = sellCustomer.getKhdwmc();
                    throw new ServiceException("★之前为销售人员【" + xsName + "】分配过客户【" + clientName + "】,不能重复分配！");
                }
            }
        }
        return true;
    }


    /**
     * 获取所有的年份
     *
     * @return
     */
    @Override
    public List getAllYear() {
        List data = new ArrayList();
        Map record = new HashMap();
//        record.put("pid", "-1");
//        record.put("id", "1");
//        record.put("year", "年份");
//        data.add(record);
        String yearStr = Constants.YEAR;
        Calendar c = Calendar.getInstance();
        int yearEnd = c.get(Calendar.YEAR);
        int yearStart = StringUtils.isNotEmpty(yearStr) ? Integer.parseInt(yearStr) : yearEnd;
        while (yearStart <= yearEnd) {
            record = new HashMap();
            record.put("pid", "1");
            record.put("id", yearStart);
            record.put("year", yearStart);
            data.add(record);
            yearStart++;
        }
        return data;
    }


    /**
     * 根据年份获取本分中心下的正式客户
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<Sellcustomer> getYearListData(PageParam<Sellcustomer> page, SellcustomerParam param) {
        String fzxId = SecurityUtils.getCId();
        QueryWrapper<Sellcustomer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("khzt", "1").orderByDesc("createdate");
        //判断是否有权限管理的权限
        if (!SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE)) {
            queryWrapper.eq("fzxid", fzxId).eq("xsjl",SecurityUtils.getUserNo());
        }
        //客户单位名称
        if (ObjectUtils.isNotEmpty(param.getKhdwmc())) {
            queryWrapper.like("khdwmc", param.getKhdwmc());
        }
        //客户单位输入码
        if (ObjectUtils.isNotEmpty(param.getKhdwsrm())) {
            queryWrapper.like("khdwsrm", param.getKhdwsrm());
        }
        //客户单位联系人
        if (ObjectUtils.isNotEmpty(param.getKhdwlxr())) {
            queryWrapper.like("khdwlxr", param.getKhdwlxr());
        }

        queryWrapper.eq("is_delete", 0);
        String year = param.getYear();
        PageParam<Sellcustomer> sellcustomerPageParam = new PageParam<>();
        //年份不为空
        if (StringUtils.isNotEmpty(year)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                sellcustomerPageParam = sellcustomerMapper.selectPage(page, queryWrapper.ge("sjrq", sdf.parse(year + "-1-1")).le("sjrq", sdf.parse(year + "-12-31")));
            } catch (ParseException e) {
            }
        } else {
            sellcustomerPageParam = sellcustomerMapper.selectPage(page, queryWrapper);
        }
        //放入销售经理电话
        List<Sellcustomer> records = sellcustomerPageParam.getRecords();
        for (Sellcustomer record : records) {
            SysUser sysUser = sysUserMapper.selectUserByUserNo(record.getXsjlid());
            record.setXsjldh(ObjectUtils.isNotEmpty(sysUser) ? sysUser.getPhonenumber() : "");
        }
        sellcustomerPageParam.setRecords(records);
        return sellcustomerPageParam;
    }


    /**
     * 团体名称下拉
     *
     * @param key
     * @return
     */
    @Override
    public List<GetOrgsVo> getOrgs(String key) {
        return sellcustomerMapper.getOrgs(key);
    }


    /**
     * 获取区域代码
     *
     * @param zoneCode
     * @param level
     * @return
     */
    @Override
    public List<ZoneVo> getZoneData(String zoneCode, String level) {
        //青岛标准
        Integer area = Constants.customerArea;
        //所属地区信息
        List<ZoneVo> list = baseZoneMapper.getZoneSql(zoneCode, level, area);
        return list;
    }

    /**
     * 获取行业类别代码
     *
     * @param indusTypeCode
     * @param level
     * @return
     */
    @Override
    public List<IndusDataVo> getIndusData(String indusTypeCode, String level) {
        return baseIndustryMapper.getIndusData(indusTypeCode, level);
    }


    /**
     * 获取经济类型316
     *
     * @return
     */
    @Override
    public List<EconomyCodeVo> getEconomyCode() {
        return baseDictionaryMapper.getEconomyCode();
    }


    /**
     * 获取经济类型317
     *
     * @return
     */
    @Override
    public List<CrptSizeCodeVo> getCrptSizeCode() {
        return baseDictionaryMapper.getCrptSizeCode();
    }

    /**
     * 获取所属区域
     *
     * @return
     */
    @Override
    public List<UnitAreaVo> getUnitArea() {
        return baseZoneQdMapper.getUnitArea();
    }


    /**
     * 获取记账单位
     *
     * @param page
     * @param key
     * @return
     */
    @Override
    public IPage<JzOrgVo> getJzOrg(PageParam<JzOrgVo> page, String key) {
        //去空格
        if (ObjectUtils.isNotEmpty(key)) {
            key = key.trim();
        }
        return sellcustomerMapper.getJzOrg(page, key);
    }

    /**
     * 分页查询正式客户
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<FormalPageVo> getFormalPage(PageParam<FormalPageVo> page, FormalPageParam param) {
        //决策管理权限
        Boolean b = SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE);
        if (b) {
            if (CollectionUtil.isEmpty(param.getBranchIds())) {
                //当前登录用户的cid
                List<String> branchIds = new ArrayList<>();
                branchIds.add(SecurityUtils.getCId());
                param.setBranchIds(branchIds);
            }
        } else {
            //当前登录用户的cid
            List<String> branchIds = new ArrayList<>();
            branchIds.add(SecurityUtils.getCId());
            param.setBranchIds(branchIds);
        }
        return sellcustomerMapper.getFormalPage(page, param);
    }


    /**
     * 导出正式客户
     *
     * @param param
     * @return
     */
    @Override
    public List<FormalPageVo> getExportData(FormalPageParam param) {
        //决策管理权限
        Boolean b = SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE);
        if (b) {
            if (CollectionUtil.isEmpty(param.getBranchIds())) {
                //当前登录用户的cid
                List<String> branchIds = new ArrayList<>();
                branchIds.add(SecurityUtils.getCId());
                param.setBranchIds(branchIds);
            }
        } else {
            //当前登录用户的cid
            List<String> branchIds = new ArrayList<>();
            branchIds.add(SecurityUtils.getCId());
            param.setBranchIds(branchIds);
        }
        return sellcustomerMapper.getExportData(param);
    }

    /**
     * 客户跟进
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveCustomerFollowData(SaveCuParam param) {
        //阶段结束
        String jdjs = param.getJdjs();
        //客户跟踪表
        Customerfollow customerFollow = mapperFacade.map(param, Customerfollow.class);
        customerFollow.setXsjlid(SecurityUtils.getUserNo());
        customerFollow.setFzxid(SecurityUtils.getCId());
        Date today = new Date();
        boolean js = "true".equals(jdjs);
        if (js) {
            //结束日期
            customerFollow.setJsrq(today);
        }
        Integer gjjd = customerFollow.getGjjd();//跟进阶段
        List<Customerfollow> cfs = customerfollowMapper.selectList(new QueryWrapper<Customerfollow>().orderByDesc("gjrq")
                .eq("khdwmcid", customerFollow.getKhdwmcid()));//按跟进日期排序  跟进日期默认当天不能修改
        //如果阶段结束，修改本阶段所有结束时间。 如果没勾选阶段结束，阶段与上一次阶段不同，修改上一阶段所有结束时间。
        for (Customerfollow cf : cfs) {
            //遇到结束的跳出循环
            if (cf.getJsrq() != null) {
                break;
            }
            //阶段相同且未结束
            if (cf.getGjjd().equals(gjjd)) {
                if (js) {
                    cf.setJsrq(today);
                    customerfollowMapper.updateById(cf);
                }
            } else {//阶段不同且未结束
                cf.setJsrq(today);
                customerfollowMapper.updateById(cf);
            }
        }
        customerfollowMapper.insert(customerFollow);
        return Boolean.TRUE;
    }


    /**
     * 客户升级-判断是否为正式客户
     *
     * @param isZskhid
     * @return
     */
    @Override
    public String isZskh(List<String> isZskhid) {
        String text = "";
        for (int i = 0; i < isZskhid.size(); i++) {
            //我的客户表
            Sellcustomer sellCustomer = sellcustomerMapper.getInfoById(isZskhid.get(i));
            if (null != sellCustomer) {
                //判断是否为正式
                if ("1".equals(sellCustomer.getKhzt())) {
                    text += "<font color='red'>★</font>客户【<font color='red'>" + sellCustomer.getKhdwmc() + "</font>】已经为正式,不能再进行升级！请重新选择！<br/>";
                } else if ("2".equals(sellCustomer.getKhzt())) {
                    text += "<font color='red'>★</font>客户【<font color='red'>" + sellCustomer.getKhdwmc() + "</font>】已经被释放,不能进行升级！请重新选择！<br/>";
                }
            }
            //销售合同维护表
            List<Sellpact> sps = sellpactMapper.selectList(new QueryWrapper<Sellpact>().eq("khdwmcid", isZskhid.get(i)));
            if (sps.size() == 0) {
                text += "<font color='red'>★</font>客户【<font color='red'>" + sellCustomer.getKhdwmc() + "</font>】没有销售合同,不能进行升级！请重新选择！<br/>";
            }
        }
        return text;
    }


    /**
     * 获取当前登录用户分中心下正式客户管理信息
     *
     * @param page
     * @param key
     * @return
     */
    @Override
    public IPage<SCListVo> getListDatas(PageParam<SCListVo> page, String key) {
        String cId = SecurityUtils.getCId();
        if (ObjectUtils.isNotEmpty(key)) {
            key = key.trim();
        }
        return sellcustomerMapper.getListDatas(page, key, cId);
    }


    /**
     * 重置密码
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean resetPassword(List<String> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            throw new ServiceException("请先选择一条或多条记录");
        }

        List<WsUser> wus = wsUserService.list(new LambdaQueryWrapper<WsUser>().in(WsUser::getCustomerId, ids));
        for(WsUser wu:wus){
            Sellcustomer sell = sellcustomerMapper.getInfoById(wu.getCustomerId());
            //还是用手机号当密码
            wu.setPassword(SecurityUtils.encryptPassword(sell.getKhdh()));
            wsUserService.updateById(wu);
        }
        return Boolean.TRUE;
    }


    /**
     * 处理重复intId的
     * @param indIds
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean intIdRepeated(List<String> indIds) {
        for (String indId : indIds) {
            List<Sellcustomer> sellcustomers = sellcustomerMapper.selectList(new LambdaQueryWrapper<Sellcustomer>().eq(Sellcustomer::getIntId, indId).orderByDesc(Sellcustomer::getCreatedate));
            //目前只有两条的情况
            if (sellcustomers.size() >= 2) {
                Sellcustomer sellcustomer = sellcustomers.get(0);
                sellcustomer.setIntId(CodeUtil.getIntId());
                sellcustomerMapper.updateById(sellcustomer);
            }
        }

        return Boolean.TRUE;
    }
}

