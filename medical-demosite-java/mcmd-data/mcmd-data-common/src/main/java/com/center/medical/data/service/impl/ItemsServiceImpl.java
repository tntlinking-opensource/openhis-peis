package com.center.medical.data.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.dto.ItemsExamCountDto;
import com.center.medical.bean.enums.AttachmentType;
import com.center.medical.bean.enums.ExamType;
import com.center.medical.bean.enums.FilePathConfigFlag;
import com.center.medical.bean.model.Attachment;
import com.center.medical.bean.model.Fylx;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.SpringContextUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.*;
import com.center.medical.data.bean.param.*;
import com.center.medical.data.bean.vo.*;
import com.center.medical.dao.FylxMapper;
import com.center.medical.dao.PeispatientfeeitemMapper;
import com.center.medical.data.bean.model.*;
import com.center.medical.data.bean.param.*;
import com.center.medical.data.bean.vo.*;
import com.center.medical.data.dao.ItemsMapper;
import com.center.medical.data.dao.PrinttypeMapper;
import com.center.medical.data.dao.YblxMapper;
import com.center.medical.data.service.InspectChargeService;
import com.center.medical.data.service.ItemsAndFzxService;
import com.center.medical.data.service.ItemsService;
import com.center.medical.service.AttachmentService;
import com.center.medical.system.bean.param.SysDeptParam;
import com.center.medical.system.bean.vo.DeptSimpleVo;
import com.center.medical.system.config.SystemConfig;
import com.center.medical.system.dao.SysBranchMapper;
import com.center.medical.system.dao.SysDeptMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * JC收费项目表(Items)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:04
 */
@Slf4j
@Service("itemsService")
@RequiredArgsConstructor
public class ItemsServiceImpl extends ServiceImpl<ItemsMapper, Items> implements ItemsService {

    private final ItemsMapper itemsMapper;
    private final SysDeptMapper sysDeptMapper;
    private final YblxMapper yblxMapper;
    private final Snowflake snowflake;
    private final InspectChargeService inspectChargeService;
    private final ItemsAndFzxService itemsAndFzxService;
    private final SysBranchMapper sysBranchMapper;
    private final PeispatientfeeitemMapper peispatientfeeitemMapper;
    private final PrinttypeMapper printtypeMapper;
    private final FylxMapper fylxMapper;
    private final AttachmentService attachmentService;
    private final SystemConfig systemConfig;


    /**
     * 分页查询[JC收费项目表]列表
     *
     * @param page  分页参数
     * @param param Items查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Items> getPage(PageParam<Items> page, ItemsParam param) {
        //参数全是空的标识
        if (ObjectUtils.isNotEmpty(param)) {
            param.setIsNull(0);
        }
        //去空格
        if (ObjectUtils.isNotEmpty(param.getSfxmsrm())) {
            param.setSfxmsrm(param.getSfxmsrm().trim());
        }

        String flag = "unitprice";
        // 只有登记页面，如果体检者所属订单适用市场价。unitprice/coopprice
        if (StringUtils.isNotEmpty(param.getNumorgresv())) {
            Object co = SpringContextUtils.getBean(SqlSession.class)
                    .selectOne("com.center.medical.sellcrm.dao.CreateorderMapper.getInfoByDdh", param.getNumorgresv());
            log.info("打印对象{}", JSONUtil.toJsonStr(co));
            //是否执行市价
            if (ObjectUtils.isNotEmpty(co)) {
                Map<String, Object> map = objectToMap(co);
                if (map != null && map.get("isMarket") != null && "1".equals(map.get("isMarket").toString())) {
                    flag = "coopprice";
                }
            }
        }
        param.setFlag(flag);
        //是否超级管理员
        Boolean role = SecurityUtils.hasRole(RoleAuthName.ADMIN);
        if (!role) {
            //不是超级管理员,查当前分中心的数据
            param.setIsAdmin(0);
            //分中心为空的话
            if (StringUtils.isEmpty(param.getCId())){
                param.setCId(SecurityUtils.getCId());
            }
        }
        return itemsMapper.getPage(page, param);
    }


    /**
     * 将Object对象里面的属性和值转化成Map对象
     *
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String, Object> objectToMap(Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = null;
            try {
                value = field.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            map.put(fieldName, value);
        }
        return map;
    }


    @Override
    public List<ItemsDataVO> getItemsData(ItemsParam param) {
        String flag = "unitprice";
        // 只有登记页面，如果体检者所属订单适用市场价。unitprice/coopprice
        if (StringUtils.isNotEmpty(param.getNumorgresv())) {
            Object co = SpringContextUtils.getBean(SqlSession.class)
                    .selectOne("com.center.medical.sellcrm.dao.CreateorderMapper.getInfoByDdh", param.getNumorgresv());
            log.info("打印对象{}", JSONUtil.toJsonStr(co));
            //是否执行市价
            if (ObjectUtils.isNotEmpty(co)) {
                Map<String, Object> map = objectToMap(co);
                if (map != null && map.get("isMarket") != null && "1".equals(map.get("isMarket").toString())) {
                    flag = "coopprice";
                }
            }
        }
        param.setFlag(flag);
        return itemsMapper.getItemsData(param);
    }


    /**
     * list页面双击获取收费项目信息
     *
     * @param tcId 套餐ID
     * @return 所有数据
     */
    @Override
    public List<ItemsDataVO> getItemsByTcId(String tcId) {
        return itemsMapper.getItemsByTcId(tcId);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Items getInfoById(String id) {
        return itemsMapper.getInfoById(id);
    }

    /**
     * 新增/编辑操作
     *
     * @param items
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Boolean saOrUp(Items items) {
        Date now = new Date();
        items.setFMale(items.getXb() == 1 ? 0 : 1);
        items.setFFemale(items.getXb() == 0 ? 0 : 1);
        SysDept dept = sysDeptMapper.getByDeptNo(items.getIdDepart());
        if (Objects.nonNull(dept)) {
            items.setDepartName(dept.getDeptName());
        }
        int type = items.getTjlx();
        String typeName = ExamType.getName(type);
        String id = items.getId();
        String name = items.getExamfeeitemName();
        if (StringUtils.isBlank(id)) {
            if (Objects.nonNull(items.getIsPublic()) && Objects.equals(items.getIsPublic(), 1)) {
                if (!SecurityUtils.hasRole(RoleAuthName.ADMIN)) {
                    throw new ServiceException("保存失败，该收费项目是公共数据，您没有添加公共数据的权限！");
                }
            }
            // 新增
            //检查是否存在相同的
            if (itemsMapper.selectCount(new LambdaQueryWrapper<Items>()
                    .eq(Items::getExamfeeitemName, name).eq(Items::getTjlx, type)
                    .eq(Items::getIdDepart, items.getIdDepart()).eq(Items::getIsDelete, 0)) > 0) {
                throw new ServiceException("操作失败：科室为【" + dept.getDeptName() + "】、体检类型为【" + typeName + "】、名称为【" + name
                        + "】的收费项目已存在");
            }
            Yblx yb = yblxMapper.selectById(items.getIdLabtype());
            if (Objects.nonNull(yb)) {
                items.setLabtypeR(yb.getName());
            }
            items.setCreatedate(now);
            items.setModifydate(now);
            items.setIsDelete(0);
            items.setXXxdm(SecurityUtils.getUsername());
            //插入
            itemsMapper.insert(items);
            id = items.getId();

            List<InspectCharge> icList = new ArrayList<>();
            //新增检查项目-收费项目关联记录
            for (InspectCharge ic : items.getInspectChargeList()) {
                ic.setCreatedate(now);
                ic.setChargeId(id);
                ic.setIsDelete(0);
                ic.setId(String.valueOf(snowflake.nextId()));
                icList.add(ic);
            }
            //TODO 添加详细操作日志
            inspectChargeService.saveBatch(icList);

        } else {
            // 修改
            //检查是否存在相同的
            if (itemsMapper.selectCount(new LambdaQueryWrapper<Items>()
                    .ne(Items::getId, id).eq(Items::getExamfeeitemName, name).eq(Items::getTjlx, type)
                    .eq(Items::getIdDepart, items.getIdDepart()).eq(Items::getIsDelete, 0)) > 0) {
                throw new ServiceException("操作失败：科室为【" + dept.getDeptName() + "】、体检类型为【" + typeName + "】、名称为【" + name
                        + "】的收费项目已存在");
            }
            boolean online = ZhongkangConfig.isOnline();
            if (Objects.nonNull(items.getIsPublic()) && Objects.equals(items.getIsPublic(), 1)) {
                if (online){
                    if (!SecurityUtils.hasRole(RoleAuthName.ADMIN)) {//只有超级管理员能编辑公共项目
                        throw new ServiceException("保存失败，该收费项目是公共数据，您没有修改公共数据的权限！");
                    }
                }else {
                    //线下要判断是否改过项目
                    if (!SecurityUtils.hasRole(RoleAuthName.ADMIN)) {
                        boolean hasRemovedOrAdded = items.getInspectChargeList().stream()
                            .anyMatch(inspectCharge -> "removed".equals(inspectCharge.get_state()) ||
                                    "added".equals(inspectCharge.get_state()));
                        if (hasRemovedOrAdded){
                            throw new ServiceException("保存失败，该收费项目是公共数据，你可以修改项目信息，但不能修改管理的检查项目！");
                        }
                    }
                }
            }
            items.setModifydate(now);
            itemsMapper.updateById(items);

            //删除旧的收费项目-分中心关联记录
            itemsAndFzxService.remove(new LambdaQueryWrapper<ItemsAndFzx>()
                    .in(ItemsAndFzx::getItemsId, items.getId()));

            //检查项目-收费项目关联记录操作
            for (InspectCharge ic : items.getInspectChargeList()) {
                String state = ic.get_state();
                String icId = ic.getId();
                if (StringUtils.equals(state, "removed")) {
                    // 删除
                    ic.setIsDelete(1);
                    ic.setModifydate(now);
                    inspectChargeService.updateById(ic);
//                    removedStatus = true;
                } else {
                    // 新增或者更新
                    if (StringUtils.isNotBlank(icId)) {
                        // 更新
                        ic.setChargeId(id);
                        ic.setIsDelete(0);
                        ic.setModifydate(new Date());
                        inspectChargeService.updateById(ic);
                    } else {
                        ic.setChargeId(id);
                        ic.setIsDelete(0);
                        ic.setCreatedate(now);
                        ic.setId(String.valueOf(snowflake.nextId()));
                        inspectChargeService.save(ic);
                    }
                }
                //TODO 添加详细操作日志
            }
        }

        // 公共收费项目处理
        if (Objects.equals(items.getIsPublic(), 1)) {
            //获取所有分中心，并添加项目与分中心关联记录
            List<SysBranch> branches = sysBranchMapper.selectList(new LambdaQueryWrapper<SysBranch>().eq(SysBranch::getIsDelete, 0));
            List<ItemsAndFzx> iafs = new ArrayList<>();
            for (SysBranch branch : branches) {
                //新增或修改后，需重新同步
                ItemsAndFzx iaf = new ItemsAndFzx();
                iaf.setItemsId(id);
                iaf.setFzxId(branch.getBranchId());
                iaf.setTbzt(0);
                iaf.setCreatedate(now);
                iafs.add(iaf);
            }
            itemsAndFzxService.saveBatch(iafs);
        } else {
            //非公共，添加绑定的项目与分中心关联记录
            String fzxIds = items.getFzxIds();
            if (StringUtils.isNotBlank(fzxIds)) {
                String[] fzxs = fzxIds.split(",");
                List<ItemsAndFzx> iafs = new ArrayList<>();
                for (String fzxId : fzxs) {
                    //新增或修改后，需重新同步
                    ItemsAndFzx iaf = new ItemsAndFzx();
                    iaf.setItemsId(id);
                    iaf.setFzxId(fzxId);
                    iaf.setTbzt(0);
                    iaf.setCreatedate(now);
                    iafs.add(iaf);
                }
                itemsAndFzxService.saveBatch(iafs);
            }
        }

        return Boolean.TRUE;
    }

    /**
     * 删除操作
     *
     * @param ids
     * @return
     */
    @Override
    public Boolean delete(List<String> ids) {
        // 批量删除
        boolean frole = SecurityUtils.hasRole(RoleAuthName.DATA_MANAGE);

        List<Items> list = itemsMapper.selectList(new LambdaQueryWrapper<Items>()
                .in(Items::getId, ids).eq(Items::getIsDelete, 0));

        for (Items items : list) {
            if (Objects.nonNull(items.getIsPublic()) && Objects.equals(items.getIsPublic(), 1) && !frole) {
                throw new ServiceException("保存失败，检查项目【" + items.getExamfeeitemName() + "】是公共数据，您没有修改公共数据的权限！");
            }
            items.setIsDelete(1);
            itemsMapper.updateById(items);
            //TODO？不用删除关联的检查项目记录吗？
            //inspectChargeService.removeByItemsId(items.getId());

            //TODO 添加详细操作日志
            //operate("删除收费项目", "删除", items.getExamfeeitemName());
        }
        //将关联的分中心记录设置为未同步
        List<ItemsAndFzx> iafs = itemsAndFzxService.list(new LambdaQueryWrapper<ItemsAndFzx>().in(ItemsAndFzx::getItemsId, ids));
        for (ItemsAndFzx iaf : iafs) {
            iaf.setTbzt(0);
        }
        itemsAndFzxService.updateBatchById(iafs);
        return Boolean.TRUE;
    }

    /**
     * 手动计算更新按钮:更新收费项目检查次数，在收费项目表中有个字段，有多少人检了此收费项目，同步的时候，更新收费项目表的字段。手动点一下更新就行，这块功能之前没做按钮
     *
     * @return
     */
    @Override
    public Boolean updateJccs() {

        List<DeptSimpleVo> departs = sysDeptMapper.getAllDepartment(new SysDeptParam());
        Date now = new Date();
        for (DeptSimpleVo dept : departs) {
            List<ItemsExamCountDto> list = peispatientfeeitemMapper.getItemsCount(now, dept.getDeptNo());
            log.info("计算结果：{}", JSONUtil.toJsonStr(list));
            for (ItemsExamCountDto countDto : list) {
                Items item = itemsMapper.getInfoById(countDto.getIdPatientfeeitem());
                if (Objects.nonNull(item)) {
                    Items items = new Items();
                    items.setId(item.getId());
                    items.setDtLastautoinsert(now);
                    Integer oldJccs = item.getJccs();
                    items.setJccs(oldJccs == null ? countDto.getCount() : (countDto.getCount() + oldJccs));
                    items.setModifydate(now);
                    itemsMapper.updateById(items);
                }
            }

        }
        return Boolean.TRUE;
    }

    @Override
    public IPage<ItemsVo> getItemVoPage(PageParam<ItemsVo> page, ItemsParam param) {
        return itemsMapper.getItemVoPage(page, param);
    }

    /**
     * 收费项目输入码获取收费项目及检查部位
     *
     * @param page
     * @param params
     * @return
     */
    @Override
    public PageParam<Items> getItems(PageParam<Items> page, GetItemsParam params) {
        String key = params.getSfxmsrm();
        //收费项目
        QueryWrapper<Items> queryWrapper = new QueryWrapper<>();
        //未删除
        queryWrapper.eq("isDelete", 0);
        if (StringUtils.isNotBlank(key)) {
            //收费项目输入码
            key = key.trim().toUpperCase();
            queryWrapper.like("sfxmsrm", key);
        }
        //拼接条件
        if (ObjectUtils.isNotEmpty(params)) {
            String idLabtype = params.getIdLabtype();
            if (StringUtils.isNotEmpty(idLabtype)) {
                queryWrapper.eq("idLabtype", idLabtype);
            }
            String idDepart = params.getIdDepart();
            if (StringUtils.isNotEmpty(idDepart)) {
                queryWrapper.eq("idDepart", idDepart);
            }
            // 健康--> 健康+综合，职业--> 职业，综合、复查--> 健康、职业、综合 zhanghj
            if (StringUtils.isNotBlank(params.getThiredTj())) {
                String thiredTj = params.getThiredTj();
                if ("2".equals(thiredTj) || "3".equals(thiredTj)) {
                    queryWrapper.in("tjlx", new String[]{"0", "1", "2"});
                } else if ("0".equals(thiredTj)) {
                    queryWrapper.in("tjlx", new String[]{"0", "2"});
                } else if ("1".equals(thiredTj)) {
                    queryWrapper.eq("tjlx", thiredTj);
                }
                // 防止打印异常
//                pager.setSearchData(null);
            }
            // 男--> 男、通用，女--> 女、通用，通用--> 通用 zhanghj
            if (!StringUtils.isBlank(params.getThiredSex())) {
                String thiredSex = params.getThiredSex();
                if ("0".equals(thiredSex) || "1".equals(thiredSex)) {
                    queryWrapper.in("xb", new String[]{thiredSex, "2"});
                }
                // 防止打印异常
//                pager.setSearchData(null);
            }
        }

        queryWrapper.orderByDesc("createDate").orderByAsc("examfeeitemName");
        PageParam<Items> itemsPageParam = itemsMapper.selectPage(page, queryWrapper);

        return itemsPageParam;
    }

    /**
     * 创建套餐获取基础数据收费项目
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<GetSfxmVo> getSfxm(PageParam<GetSfxmVo> page, GetSfxmParam param) {
        //去空格大写
        if (ObjectUtils.isNotEmpty(param.getExamfeeitemName())) {
            param.setExamfeeitemName(param.getExamfeeitemName().trim());
        }
        if (ObjectUtils.isNotEmpty(param.getSfxmsrm())) {
            param.setSfxmsrm(param.getSfxmsrm().trim().toUpperCase());
        }
        return itemsMapper.getSfxm(page, param);
    }

    /**
     * 从基础数据获取收费项目
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<SfxmDataVo> getSfxmData(PageParam<Items> page, SfxmDataParam param) {
        //取出属性
        String tjValue = param.getTjValue();
        String syxbValue = param.getSyxbValue();
        //条件构造器
        QueryWrapper<Items> and = new QueryWrapper<>();
        if (ObjectUtils.isNotEmpty(param)) {
            //收费项目名称
            String sfxmmc = null == param.getExamfeeitemName() ? "" : param.getExamfeeitemName().trim();
            //收费项目输入码
            String sfxmsrm = null == param.getSfxmsrm() ? "" : param.getSfxmsrm().trim();
            if (StringUtils.isNotEmpty(sfxmmc)) {
                and.like("examfeeitem_Name", sfxmmc);
            }
            if (StringUtils.isNotEmpty(sfxmsrm)) {
                and.like("sfxmsrm", sfxmsrm.trim().toUpperCase());
            }
            // 推荐-- 价格区间
            if (ObjectUtils.isNotEmpty(param.getPrice_from())) {
                and.ge("unitprice", param.getPrice_from());
            }
            if (ObjectUtils.isNotEmpty(param.getPrice_to())) {
                and.le("unitprice", param.getPrice_to());
            }
        }
        and.orderByDesc("jccs");

        IPage<Items> pager = new PageParam<>();
        if ("".equals(tjValue) && "".equals(syxbValue)) {
            //初次加载显示所有收费项目
            pager = itemsMapper.selectPage(page, and.eq("is_delete", 0));
        } else if ("0".equals(tjValue) && "".equals(syxbValue)) {
            //显示体检类型为健康的收费项目
            pager = itemsMapper.selectPage(page, and.eq("tjlx", "0").eq("is_delete", 0));
        } else if ("1".equals(tjValue) && "".equals(syxbValue)) {
            //显示体检类型为健康的收费项目
            pager = itemsMapper.selectPage(page, and.eq("tjlx", "1").eq("is_delete", 0));
        } else if ("2".equals(tjValue) && "".equals(syxbValue)) {
            //显示体检类型为健康的收费项目
            pager = itemsMapper.selectPage(page, and.eq("is_delete", 0));
        } else if ("".equals(tjValue) && "0".equals(syxbValue)) {
            //显示性别为男、通用的收费项目
            pager = itemsMapper.selectPage(page, and.ne("xb", "1").eq("is_delete", 0));
        } else if ("".equals(tjValue) && "1".equals(syxbValue)) {
            //显示性别为女、通用的收费项目
            pager = itemsMapper.selectPage(page, and.ne("xb", "0").eq("is_delete", 0));
        } else if ("".equals(tjValue) && "2".equals(syxbValue)) {
            //显示性别为通用的收费项目
            pager = itemsMapper.selectPage(page, and.eq("xb", syxbValue).eq("is_delete", 0));
        } else if (StringUtils.isNotEmpty(tjValue) && StringUtils.isNotEmpty(syxbValue)) {
            if ("0".equals(tjValue) && "0".equals(syxbValue)) {
                pager = itemsMapper.selectPage(page, and.eq("tjlx", tjValue).ne("xb", "1").eq("is_delete", 0));
            } else if ("0".equals(tjValue) && "1".equals(syxbValue)) {
                pager = itemsMapper.selectPage(page, and.eq("tjlx", tjValue).ne("xb", "0").eq("is_delete", 0));
            } else if ("0".equals(tjValue) && "2".equals(syxbValue)) {
                pager = itemsMapper.selectPage(page, and.eq("tjlx", tjValue).eq("xb", syxbValue).eq("is_delete", 0));
            } else if ("1".equals(tjValue) && "0".equals(syxbValue)) {
                pager = itemsMapper.selectPage(page, and.eq("tjlx", tjValue).ne("xb", "1").eq("is_delete", 0));
            } else if ("1".equals(tjValue) && "1".equals(syxbValue)) {
                pager = itemsMapper.selectPage(page, and.eq("tjlx", tjValue).ne("xb", "0").eq("is_delete", 0));
            } else if ("1".equals(tjValue) && "2".equals(syxbValue)) {
                pager = itemsMapper.selectPage(page, and.eq("tjlx", tjValue).eq("xb", syxbValue).eq("is_delete", 0));
            } else if ("2".equals(tjValue) && "0".equals(syxbValue)) {
                pager = itemsMapper.selectPage(page, and.ne("xb", "1").eq("is_delete", 0));
            } else if ("2".equals(tjValue) && "1".equals(syxbValue)) {
                pager = itemsMapper.selectPage(page, and.ne("xb", "0").eq("is_delete", 0));
            } else if ("2".equals(tjValue) && "2".equals(syxbValue)) {
                pager = itemsMapper.selectPage(page, and.eq("xb", syxbValue).eq("is_delete", 0));
            }
        } else {
            //根据条件获取关联的数据
            pager = itemsMapper.selectPage(page, and.eq("is_delete", 0));
        }

        // 最终返回分页对象
        IPage<SfxmDataVo> convertPage = pager.convert(items -> {
            SfxmDataVo vo = new SfxmDataVo();
            BeanUtil.copyProperties(items, vo);
            vo.setSfxmmc(items.getExamfeeitemName());
            vo.setJg(items.getUnitprice());
            vo.setSsks(items.getDepartName());
            vo.setIdKs(items.getIdDepart());
            vo.setSycstj(null == items.getJccs() ? 0 : items.getJccs());
            return vo;
        });

        return convertPage;
    }

    /**
     * 推荐收费项目-查看套餐下的检查项目
     *
     * @param tcId
     * @return
     */
    @Override
    public List<ItemsByTcVo> getItemsDataByTcId(String tcId) {
        return itemsMapper.getItemsDataByTcId(tcId);
    }

    /**
     * 获取销售打印分类
     *
     * @param key
     * @return
     */
    @Override
    public List<Printtype> getXsdyfl(String key) {
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        QueryWrapper<Printtype> con = new QueryWrapper<>();
        //模糊查询
        if (StringUtils.isNotBlank(key)) {
            key = key.toUpperCase();
            con.like("input_code", key);
        }
        con.eq("is_delete", 0);
        List<Printtype> list = printtypeMapper.selectList(con);
        return list;
    }

    /**
     * 获取费用类型
     *
     * @param key
     * @return
     */
    @Override
    public List<Fylx> getFylx(String key) {
        QueryWrapper<Fylx> con = new QueryWrapper<>();
        con.eq("is_delete", 0);
        if (StringUtils.isNotBlank(key)) {
            key = key.toUpperCase();
            con.like("srm", key);
        }
        List<Fylx> list = fylxMapper.selectList(con.orderByDesc("createdate"));
        return list;
    }

    /**
     * 获取所有收费项目
     *
     * @param key
     * @return
     */
    @Override
    public IPage<AllItemsVo> getAllItemsData(PageParam<AllItemsVo> page, String key) {
        //去空格大写
        if (ObjectUtils.isNotEmpty(key)) {
            key = key.trim().toUpperCase();
        }
        return itemsMapper.getAllItemsData(page, key);
    }


    /**
     * 返回符合条件的收费项目数据
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<GetShowVo> getShowData(PageParam<GetShowVo> page, GetShowParam param) {
        //去空格大写
        if (ObjectUtils.isNotEmpty(param.getKey())) {
            param.setKey(param.getKey().trim().toUpperCase());
        }
        return itemsMapper.getShowData(page, param);
    }

    /**
     * 上传图片
     *
     * @param file
     * @param itemsId 绑定的收费项目ID
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String upload(MultipartFile file, String itemsId) {
        //获取项目信息
        Items items = itemsMapper.selectById(itemsId);
        if (Objects.nonNull(items)) {
            String baseDir = systemConfig.getFilePathConfig(FilePathConfigFlag.CPP.value());
            //判断是否线上
            //线上系统将文件存储至阿里云的OSS中
            Attachment attachment = new Attachment();
            String extName = FileUtil.extName(file.getOriginalFilename());
            attachment.setFileType("收费项目图片");
            attachment.setType(AttachmentType.IMAGE.value());
            attachment.setBranchId(SecurityUtils.getCId());
            attachment.setCreatedate(new Date());
            try {
                attachmentService.uploadFile(file, attachment, baseDir, extName, null, true);
            } catch (IOException e) {
                log.error("收费项目上传保存失败！");
                throw new ServiceException("收费项目上传保存失败！");
            }
            log.info("上传结果：{}、{}", attachment.getId(), attachment);

            //保存图片（线上线下系统的文件的路径是一致的）
            if (StringUtils.isNotBlank(items.getInputcodee())) {
                //删除原图片
                attachmentService.deleteFile(items.getInputcodee());
            }
            //更新项目的图片
            items.setInputcodee(attachment.getFilePath());
            itemsMapper.updateById(items);
            return attachment.getFilePath();
        }
        throw new ServiceException("收费项目不存在！");
    }


    /**
     * 导出收费项目设置数据
     *
     * @param param
     * @return
     */
    @Override
    public List<Items> getExportData(ItemsParam param) {
        return itemsMapper.getExportData(param);
    }


    /**
     * 导入收费项目设置
     *
     * @param list
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String importItems(List<Items> list) {
        //移除所有的null元素
        list.removeAll(Collections.singleton(null));
        if (ObjectUtils.isEmpty(list)) {
            throw new ServiceException("导入数据不能为空！");
        }
        int successNum = 0;
        for (Items items : list) {
            if (StringUtils.isEmpty(items.getId())) {
                throw new ServiceException(items.getExamfeeitemName() + "id不能为空!");
            }
            // 验证是否存在
            Items i = itemsMapper.getInfoById(items.getId());
            if (ObjectUtils.isEmpty(i)) {
                throw new ServiceException(items.getExamfeeitemName() + "在数据库中不存在!");
            } else {
                //存在更新
                itemsMapper.updateById(items);
                successNum++;
            }

        }
        return "已成功导入:" + successNum + "条数据!";
    }

    /**
     * 导出页面内容
     * @param param
     * @return
     */
    @Override
    public List<ItemsExportAllVo> getExportAllData(ItemsParam param) {
        List<ItemsExportAllVo> exportAllData = itemsMapper.getExportAllData(param);
        int i = 1;
        for (ItemsExportAllVo vo : exportAllData) {
            vo.setRownum(i);
            i++;
            //分中心
            if (StringUtils.isEmpty(vo.getFzxIds())){
                vo.setFzx("公共");
            }else {
                String[] brandIds = vo.getFzxIds().split(",");
                List<SysBranch> sysBranches = sysBranchMapper.selectList(new LambdaQueryWrapper<SysBranch>().eq(SysBranch::getBranchId, brandIds));
                String fzxString = sysBranches.stream()
                        .map(SysBranch::getFzx)
                        .collect(Collectors.joining(","));
                vo.setFzx(fzxString);
            }
        }
        return exportAllData;
    }
}

