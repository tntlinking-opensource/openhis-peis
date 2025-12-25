package com.center.medical.sellcrm.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.enums.CustomerStatus;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.core.domain.model.LoginUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.CodeUtil;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Clientcommon;
import com.center.medical.sellcrm.bean.model.CustomerOperateHistory;
import com.center.medical.sellcrm.bean.model.Receiveandsell;
import com.center.medical.sellcrm.bean.model.Sellcustomer;
import com.center.medical.sellcrm.bean.param.ClientcommonParam;
import com.center.medical.sellcrm.dao.ClientcommonMapper;
import com.center.medical.sellcrm.dao.CustomerOperateHistoryMapper;
import com.center.medical.sellcrm.dao.ReceiveandsellMapper;
import com.center.medical.sellcrm.dao.SellcustomerMapper;
import com.center.medical.sellcrm.service.ClientcommonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 客户公共池表(Clientcommon)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:27
 */
@Slf4j
@Service("this")
@RequiredArgsConstructor
public class ClientcommonServiceImpl extends ServiceImpl<ClientcommonMapper, Clientcommon> implements ClientcommonService {

    private final ClientcommonMapper clientcommonMapper;
    private final SellcustomerMapper sellcustomerMapper;
    private final ReceiveandsellMapper receiveandsellMapper;
    private final CustomerOperateHistoryMapper customerOperateHistoryMapper;


    /**
     * 分页查询[客户公共池表]列表
     *
     * @param page  分页参数
     * @param param Clientcommon查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Clientcommon> getPage(PageParam<Clientcommon> page, ClientcommonParam param) {
        String cId = SecurityUtils.getCId();
        //判断是否拥有[决策管理]权限：有则查询所有数据，无则查询当前用户所在分中心数据
        if (!SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE)) {
            // 查询当前用户所在分中心数据
            param.setBranchIds(Arrays.asList(cId));
        }
        return clientcommonMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Clientcommon getInfoById(String id) {
        return clientcommonMapper.getInfoById(id);
    }

    @Override
    public IPage<Clientcommon> getListData(PageParam<Clientcommon> page, Long xsjlId) {
        return clientcommonMapper.getListData(page, xsjlId);
    }

    /**
     * 新增/编辑操作
     *
     * @param clientCommon
     * @return
     */
    @Override
    public Boolean saOrUp(Clientcommon clientCommon) {
//        //判断单位名称是否已存在
//        if (clientcommonMapper.selectCount(new LambdaQueryWrapper<Clientcommon>()
//                .eq(Clientcommon::getKhdwmc, clientCommon.getKhdwmc())
//                .eq(Clientcommon::getIsDelete, 0)) > 0) {
//            throw new ServiceException("添加失败，单位名称已存在！");
//        }
        clientCommon.setKhdwsrm(ToolUtil.getHanziPinyinHeadChar(clientCommon.getKhdwmc()));
        if (StringUtils.isBlank(clientCommon.getId())) {
            //新增
            clientCommon.setCreatedate(new Date());
            //获取当前登录用户id,并传给销售人员
            clientCommon.setXsjlid(SecurityUtils.getUserNo());
            //获取当前登录者的分中心id,并赋值给对象
            clientCommon.setFzxid(SecurityUtils.getCId());
            //设置领取次数初始值
            clientCommon.setLqcstj(0);
            //设置假删状态(0:显示,1:不显示)
            clientCommon.setIsDelete(0);
            //设置分配状态初始为0(0：领导还未强制分配,1：领导强制分配过)
            clientCommon.setFpzt("0");
            //进行数据保存
            clientcommonMapper.insert(clientCommon);
        } else {
            //编辑
            //判断对象是否存在
            Clientcommon ccDb = clientcommonMapper.selectOne(new LambdaQueryWrapper<Clientcommon>()
                    .eq(Clientcommon::getId, clientCommon.getId()).eq(Clientcommon::getIsDelete, 0));
            if (Objects.nonNull(ccDb)) {
                //TODO ???是否需要设置更新权限
                clientCommon.setModifydate(new Date());
                clientcommonMapper.updateById(clientCommon);
            } else {
                throw new ServiceException("更新失败，找不到【" + clientCommon.getKhdwmc() + "】记录！");
            }
        }
        return Boolean.TRUE;
    }


    /**
     * 客户领取
     *
     * @param ids 客户id列表
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public String receive(List<String> ids) {
        //获取当前登录用户的分中心
        String fzxId = SecurityUtils.getCId();
        String name = SecurityUtils.getUsername();
        String userNo = SecurityUtils.getUserNo();
        //进行客户领取
        for (String id : ids) {
            Sellcustomer sellCustomer;
            //查询对应id及没删除的
            Clientcommon clientCommon = clientcommonMapper.selectOne(new QueryWrapper<Clientcommon>().eq("id", id).eq("is_delete", 0));
            if (Objects.nonNull(clientCommon)) {
                //查询我的客户表：销售经理（xsjlid）、客户名称（khdwmc）和删除状态（is_delete=0）共同确定唯一
                sellCustomer = sellcustomerMapper.selectOne(new QueryWrapper<Sellcustomer>().eq("is_delete", 0)
                        .eq("khdwmc", clientCommon.getKhdwmc())
                        .eq("xsjlid", userNo));
                //如果客户表为空的话就新建一个
                if (Objects.isNull(sellCustomer)) {
                    sellCustomer = new Sellcustomer();
                }
                //拷贝
                BeanUtils.copyProperties(clientCommon, sellCustomer
                        , new String[]{"fpzt", "isDelete", "lqcstj", "xsjl", "xsjlid", "fzxid", "tjttlx", "id", "khdwdz"});
//                sellCustomer.setKhdwmc(clientCommon.getKhdwmc());
//                sellCustomer.setKhdwsrm(clientCommon.getKhdwsrm());
//                sellCustomer.setKhdwlxr(clientCommon.getKhdwlxr());
//                sellCustomer.setKhdh(clientCommon.getKhdh());
                sellCustomer.setKhdwzcdz(clientCommon.getKhdwdz());
//                sellCustomer.setSshy(clientCommon.getSshy());
//                sellCustomer.setDwjgdm(clientCommon.getDwjgdm());
                sellCustomer.setClientid(clientCommon.getId());
                sellCustomer.setIsDelete(0);
                sellCustomer.setKhzt(CustomerStatus.QIANZAI.value());
                sellCustomer.setXsjl(name);
                sellCustomer.setXsjlid(SecurityUtils.getUserNo());
                sellCustomer.setFzxid(fzxId);
                sellCustomer.setLdfpid(null);
                //社会信用代码
                sellCustomer.setSocialCreditCode(clientCommon.getDwjgdm());
                //设置体检团体类型,前台需要
                sellCustomer.setTjttlx(0);
                if (ObjectUtils.isEmpty(sellCustomer.getIntId())) {
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
                    sellCustomer.setIntId(intId);
                }
                if (StringUtils.isBlank(sellCustomer.getId())) {
                    sellcustomerMapper.insert(sellCustomer);
                } else {
                    sellcustomerMapper.updateById(sellCustomer);
                }
                //修改客户公共池领取次数
                clientCommon.setLqcstj(clientCommon.getLqcstj() + 1);
                clientcommonMapper.updateById(clientCommon);
                //保存领取人信息
                Receiveandsell receiveAndSell = new Receiveandsell();
                receiveAndSell.setClientid(clientCommon.getId());
                receiveAndSell.setLqrq(new Date());
                receiveAndSell.setCzzt(0);
                receiveAndSell.setXsjlid(SecurityUtils.getUserNo());
                receiveAndSell.setIsDelete(0);
                receiveandsellMapper.insert(receiveAndSell);

                /*插入历史记录*/
                CustomerOperateHistory history = new CustomerOperateHistory(SecurityUtils.getUserNo()
                        , sellCustomer.getId()
                        , clientCommon.getKhdwmc()
                        , "客户领取"
                        , clientCommon.getXsjlid()
                        , SecurityUtils.getUsername()
                        , SecurityUtils.getUsername()
                        , null
                        , null);
                customerOperateHistoryMapper.insert(history);
            } else {
                throw new ServiceException("领取失败，找不到ID为[" + id + "的客户信息");
            }
        }
        return "领取成功";
    }


    /**
     * 领导释放
     *
     * @param clientId 公共池记录ID
     * @param xsIds    销售ID列表
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public String release(String clientId, List<String> xsIds) {
        for (String id : xsIds) {
            //进行领导释放.1、为领取记录：修改领取次数,操作中间表假删掉领取人信息;2、为分配记录：操作中间表假删掉领取人信息
            Sellcustomer sellCustomer = sellcustomerMapper.selectOne(new QueryWrapper<Sellcustomer>().eq("clientid", clientId).eq("xsjlid", id).eq("is_delete", 0));
            LoginUser loginUser = SecurityUtils.getLoginUser();
            if (Objects.nonNull(sellCustomer)) {
                //操作中间表假删掉领取人信息
                Receiveandsell receiveAndSell = receiveandsellMapper.selectOne(new QueryWrapper<Receiveandsell>().eq("clientid", clientId).eq("xsjlid", id).eq("is_delete", 0));
                Clientcommon clientCommon = clientcommonMapper.selectOne(new QueryWrapper<Clientcommon>().eq("id", clientId).eq("is_delete", 0));
                if (Objects.nonNull(receiveAndSell) && Objects.nonNull(clientCommon)) {
                    //为领取记录：假删掉我的客户里面的这条信息;操作中间表假删掉领取人信息;修改领取次数
//                    sellCustomer.setIsDelete(1);
                    sellCustomer.setKhzt(CustomerStatus.SHIFANG.value());
                    sellcustomerMapper.updateById(sellCustomer);
                    receiveAndSell.setIsDelete(1);
                    receiveandsellMapper.updateById(receiveAndSell);
                    //修改客户公共池领取次数
                    BeanUtils.copyProperties(sellCustomer, clientCommon
                            , new String[]{"clientid", "isDelete", "khzt", "ldfpid", "sjrq", "id", "khdwzcdz"});
                    clientCommon.setKhdwdz(sellCustomer.getKhdwzcdz());
                    clientCommon.setLqcstj(clientCommon.getLqcstj() - 1);
                    clientcommonMapper.updateById(clientCommon);
                    /*插入历史记录*/
                    CustomerOperateHistory history = new CustomerOperateHistory(SecurityUtils.getUserNo()
                            , sellCustomer.getId()
                            , sellCustomer.getKhdwmc()
                            , "领导释放"
                            , sellCustomer.getXsjlid()
                            , loginUser.getUser().getUserName()
                            , loginUser.getUsername()
                            , null
                            , null);
                    customerOperateHistoryMapper.insert(history);
                }
            } else {
                Sellcustomer sellCust = sellcustomerMapper.selectOne(new QueryWrapper<Sellcustomer>().eq("ldfpid", clientId).eq("xsjlid", id).eq("is_delete", 0));
                if (Objects.nonNull(sellCust)) {
                    //假删掉领取人中间表的数据
                    Receiveandsell receiveAndSell = receiveandsellMapper.selectOne(new QueryWrapper<Receiveandsell>().eq("clientid", clientId).eq("xsjlid", id).eq("is_delete", 0));
                    if (Objects.nonNull(receiveAndSell)) {
                        //为分配记录：假删掉自己名下的客户;操作中间表假删掉领取人信息
//                        sellCust.setIsDelete(1);
                        sellCust.setKhzt(CustomerStatus.SHIFANG.value());
                        sellcustomerMapper.updateById(sellCust);
                        receiveAndSell.setIsDelete(1);
                        receiveAndSell.setModifydate(new Date());
                        receiveandsellMapper.updateById(receiveAndSell);

                        Clientcommon clientsCommon = clientcommonMapper.selectOne(new QueryWrapper<Clientcommon>().eq("id", sellCust.getLdfpid()).eq("is_delete", 0));
                        if (Objects.nonNull(clientsCommon)) {
                            BeanUtils.copyProperties(sellCust, clientsCommon
                                    , new String[]{"clientid", "isDelete", "khzt", "ldfpid", "sjrq", "id", "khdwzcdz"});
                            clientsCommon.setKhdwdz(sellCust.getKhdwzcdz());
                            clientcommonMapper.updateById(clientsCommon);
                        } else {
                            clientsCommon = new Clientcommon();
                            BeanUtils.copyProperties(sellCust, clientsCommon
                                    , new String[]{"clientid", "isDelete", "khzt", "ldfpid", "sjrq", "id", "khdwzcdz"});
                            clientsCommon.setKhdwdz(sellCust.getKhdwzcdz());
                            clientsCommon.setIsDelete(0);
                            clientsCommon.setLqcstj(0);
                            clientsCommon.setFpzt("0");
                            clientcommonMapper.insert(clientsCommon);
                        }
                        /*插入历史记录*/
                        CustomerOperateHistory history = new CustomerOperateHistory(SecurityUtils.getUserNo()
                                , sellCust.getId()
                                , sellCust.getKhdwmc()
                                , "领导释放"
                                , sellCust.getXsjlid()
                                , SecurityUtils.getUsername()
                                , SecurityUtils.getUsername()
                                , null
                                , null);
                        customerOperateHistoryMapper.insert(history);
                    }
                }
            }
        }
        return "释放成功";
    }


    /**
     * 判断客户公共池中的客户单位名称是否重复：返回true则表示重复，false则不重复
     *
     * @param clientName
     * @return
     */
    @Override
    public Boolean onBlur(String clientName) {
        Long count = sellcustomerMapper.selectCount(new QueryWrapper<Sellcustomer>().eq("khdwmc", clientName).eq("is_delete", 0));
        Long count1 = clientcommonMapper.selectCount(new QueryWrapper<Clientcommon>().eq("khdwmc", clientName).eq("is_delete", 0));
        return count > 0 || count1 > 0;
    }


    /**
     * 判断是否存在领取次数为3次的记录或被领导强制分配过的记录：返回true则表示存在，false则不存在
     *
     * @param clientIds 客户id集合，多个以英文逗号（,）隔开
     * @return
     */
    @Override
    public R isLqAndFp(List<String> clientIds) {
        String text = "";
        String message = "";
        Boolean flag = false;
        for (String id : clientIds) {
            Clientcommon clientCommon = clientcommonMapper.selectOne(new QueryWrapper<Clientcommon>().eq("id", id).eq("is_delete", 0));
            if (Objects.nonNull(clientCommon)) {
                if (clientCommon.getLqcstj() == 3) {
                    //存在领取次数为3次的记录
                    text = "★客户【" + clientCommon.getKhdwmc() + "】已被领取过3次,不能再进行领取！请重新选择！<br/>";
                    flag = true;
                } else if ("1".equals(clientCommon.getFpzt())) {
                    //被领导强制分配过的记录
                    message = "★客户【" + clientCommon.getKhdwmc() + "】已被领导强制分配过,不能再进行领取！请重新选择！<br/>";
                    flag = true;
                }
            } else {
                throw new ServiceException("找不到ID为[" + id + "的客户信息");
            }
        }
        return R.ok(flag, text + message);
    }

    /**
     * 导入
     *
     * @param list
     * @param operName
     * @return
     */
    @Override
    public String importUser(List<Clientcommon> list, String operName) {

        //TODO 可以进行代码优化，可参考：”导入客户信息“处理逻辑
        log.info("ClientcommonList:{}" + list);
        //判断是否为1行表头
        if (list.size() == 1) {
            throw new ServiceException("文件导入失败： 该模板没数据,不能进行导入！");
        }
        //判断excel中添加的客户单位名称是否存在重复
        List<Clientcommon> data = new ArrayList();
        int flg = 0;
        int mou = 0;
        int idFlg = 0;
        int ids = 0;
        for (int i = 1; i < list.size(); i++) {
            Clientcommon clientcommon = list.get(i);
            //如果data里面没数据就加一个
            if (data.size() == 0) {
                data.add(clientcommon);
            } else {
                //判断是否存在重复的客户单位名称
                for (int m = 0; m < data.size(); m++) {
                    flg = 0;
                    idFlg = 0;
                    if (data.get(m).getKhdwmc().equals(clientcommon.getKhdwmc())) {
                        //存在重复的客户单位名称
                        flg = 1;
                        mou = 1;
                        break;
                    }
                    if (data.get(m).getId().equals(clientcommon.getId())) {
                        //存在重复的id
                        idFlg = 1;
                        ids = 1;
                        break;
                    }
                }
                if (flg == 0 && idFlg == 0) {
                    //不存在重复的数据,添加进集合
                    data.add(clientcommon);
                    flg = 0;
                    idFlg = 0;
                }
            }
        }
        if (mou != 0) {
            //excel中存在重复的客户单位名称
            throw new ServiceException("文件导入失败：要导入的数据中存在相同的客户单位名称！");
        }
        if (ids != 0) {
            //excel中存在重复的客户单位名称
            throw new ServiceException("文件导入失败：要导入的数据中存在相同的id！");
        }
        //效验之前id有没有重复的
        List<Clientcommon> list1 = clientcommonMapper.selectList(null);
        List<String> idList = list1.stream().map(c -> c.getId()).collect(Collectors.toList());
        List<String> khdwmcList = list1.stream().map(c -> c.getKhdwmc()).collect(Collectors.toList());
        for (Clientcommon clientcommon : list) {
            boolean b = idList.contains(clientcommon.getId());
            boolean c = khdwmcList.contains(clientcommon.getKhdwmc());
            if (b) {
                throw new ServiceException("文件导入失败：id:" + clientcommon.getId() + "重复！");
            }
            if (c) {
                throw new ServiceException("文件导入失败：客户单位" + clientcommon.getKhdwmc() + "重复！");
            }
        }
        //判断是否已必填
        for (int i = 1; i < list.size(); i++) {
            if (ObjectUtils.isEmpty(list.get(i))) {
                throw new ServiceException("文件导入失败：请确认格式及内容是否正确！");
            }
            Clientcommon clientcommon = list.get(i);
            if (ObjectUtils.isEmpty(clientcommon.getKhdwlxr())) {
                throw new ServiceException("文件导入失败：客户单位联系人未填写！");
            } else if (ObjectUtils.isEmpty(clientcommon.getKhdh())) {
                throw new ServiceException("文件导入失败：客户电话未填写！");
            } else if (ObjectUtils.isEmpty(clientcommon.getKhdwmc())) {
                throw new ServiceException("文件导入失败：客户单位名称未填写！");
            } else if (ObjectUtils.isEmpty(clientcommon.getKhdwmc())) {
                throw new ServiceException("文件导入失败：客户单位名称未填写！");
            }
        }
        //判断导入的名称是否重复
        for (int i = 1; i < list.size(); i++) {
            String state = "";
            String khdwmc = list.get(i).getKhdwmc();
            if (onBlur(khdwmc)) {
                //存在相同名称的客户单位名称,不能进行导入
                throw new ServiceException("文件导入失败：客户单位名称：" + khdwmc + "已存在,不能重复添加！");
            }
        }
        //验证电话格式
        for (int i = 1; i < list.size(); i++) {
            String khdh = list.get(i).getKhdh();
            //判断客户电话,20位不能为中、英文
            if (!"".equals(khdh)) {
                Pattern pattern = Pattern.compile("[\\s\\S]*[\\u4e00-\\u9fa5a-zA-Z]+[\\s\\S]*");
                Matcher matcher = pattern.matcher(khdh);
                boolean b = matcher.matches();
                if (b == true) {
                    throw new ServiceException("文件导入失败：客户电话存在中文或英文！");
                }
                if (khdh.length() > 20) {
                    throw new ServiceException("文件导入失败：客户电话长度最多20位！");
                }
            }
        }
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //获取数据进行导入
        List<Clientcommon> ccList = new ArrayList<>();
        for (Clientcommon clientcommon : list) {
            //销售经理名称
            String userName = loginUser.getUsername();
            clientcommon.setXsjl(userName);
            //设置领取次数统计
            clientcommon.setLqcstj(0);
            //设置分配状态
            clientcommon.setFpzt("0");
            //设置假删状态
            clientcommon.setIsDelete(0);
            //设置销售经理id
            clientcommon.setXsjlid(SecurityUtils.getUserNo());
            //获取当前登录用户分中心id
            String fzxId = loginUser.getUser().getCid();
            clientcommon.setFzxid(fzxId);
            //进行实体保存
            ccList.add(clientcommon);
        }
        saveBatch(ccList);
        return "导入成功";
    }


}

