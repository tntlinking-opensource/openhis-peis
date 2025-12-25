package com.center.medical.data.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Branch;
import com.center.medical.bean.model.HarmAndFzx;
import com.center.medical.data.bean.model.ZyVsSummary;
import com.center.medical.common.core.domain.model.LoginUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.bean.BeanUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.HarmAndFzxMapper;
import com.center.medical.data.bean.vo.JhysDataVo;
import com.center.medical.data.dao.ZyVsSummaryMapper;
import com.center.medical.data.bean.model.Harm;
import com.center.medical.data.bean.model.Whysqzfw;
import com.center.medical.data.bean.param.HarmParam;
import com.center.medical.data.dao.HarmMapper;
import com.center.medical.data.dao.WhysqzfwMapper;
import com.center.medical.data.dao.ZyHarmClassMapper;
import com.center.medical.data.service.HarmService;
import com.center.medical.system.dao.BranchMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * JC危害因素(Harm)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:18
 */
@Slf4j
@Service("harmService")
@RequiredArgsConstructor
public class HarmServiceImpl extends ServiceImpl<HarmMapper, Harm> implements HarmService {

    private final HarmMapper harmMapper;
    private final BranchMapper branchMapper;
    private final HarmAndFzxMapper harmAndFzxMapper;
    private final WhysqzfwMapper whysqzfwMapper;
    private final ZyVsSummaryMapper zyVsSummaryMapper;
    private final ZyHarmClassMapper zyHarmClassMapper;
    private final Snowflake snowflake;

    /**
     * 分页查询[JC危害因素]列表
     *
     * @param page  分页参数
     * @param param Harm查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Harm> getList(PageParam<Harm> page, HarmParam param) {
        return harmMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Harm getInfoById(String id) {
        return harmMapper.getInfoById(id);
    }

    @Override
    public String saveOrUpdateHarm(Harm harm) {
        String id = harm.getId();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        // 判断是否为空
        if (StringUtils.isBlank(id)) {
            //判断是否存在重复的危害因素名称,排除删除数据有相同因素名称的影响
            Harm harmNew = harmMapper.selectOne(new QueryWrapper<Harm>()
                    .eq("harm_name", harm.getHarmName())
                    .and(wrapper -> wrapper.isNull("is_delete").or().eq("is_delete", 0)));
            if (ObjectUtils.isNotEmpty(harmNew)) {
                throw new ServiceException("保存失败,存在相同的因素名称!");
            } else {
                //保存
                //设置isDelete字段为0
                harm.setIsDelete(0);
                harm.setCreater(loginUser.getUsername());
                if (harm.getIsPublic() == 1) {
                    harm.setCreateFzx("公共");
                } else if (StringUtils.isNotEmpty(harm.getFzxIds())) {
                    List<Branch> branches = branchMapper.selectList(new QueryWrapper<Branch>().in("id", harm.getFzxIds().split(",")));
                    List<String> fzxs = new ArrayList<String>();
                    for (Branch branch : branches) {
                        fzxs.add(branch.getFzx());
                    }
                    harm.setCreateFzx(StringUtils.join(fzxs, "、"));
                } else {
                    harm.setCreateFzx("空");
                }
                harm.setLv(2);
                id = String.valueOf(snowflake.nextId());
                harm.setId(id);
                this.save(harm);

            }
        } else {
            harmAndFzxMapper.delete(new QueryWrapper<HarmAndFzx>().eq("harm_id", id));
            // 判断是否存在重复数据,排除删除数据的影响
            // 判断是否假删、ID是否重复
            Harm harmNew = harmMapper.selectOne(new QueryWrapper<Harm>()
                    .eq("id", harm.getId()).eq("is_delete", 0));
            if (ObjectUtils.isNotEmpty(harmNew)) {
                // 判断名称是否重复
                Harm harmNews = harmMapper.selectOne(new QueryWrapper<Harm>().ne("id", harm.getId())
                        .eq("harm_name", harm.getHarmName()).eq("is_delete", 0));
                if (ObjectUtils.isEmpty(harmNews)) {
                    // 更新实体类
                    BeanUtils.copyBeanProp(harmNew, harm);

                    //判断是否是公共的，如果是公共的，分中心id要为空
                    if (harmNew.getIsPublic() == 1){
                        harmNew.setFzxIds(null);
                    }

                    this.updateById(harmNew);
                } else {
                    throw new ServiceException("更新失败：" + harm.getHarmName() + "名称重复");
                }
            } else {
                throw new ServiceException("更新失败：" + harm.getHarmName() + "名称已被删除");
            }
        }

        //是否是公共的,中间表插入
        if (harm.getIsPublic() == 1) {
            List<Branch> branches = branchMapper.selectList(new QueryWrapper<Branch>().eq("is_delete", 0));
            for (Branch branch : branches) {
                HarmAndFzx haf = new HarmAndFzx(id, String.valueOf(branch.getBranchId()), 0, new Date());//新增或修改后，需重新同步
                haf.setId(String.valueOf(snowflake.nextId()));
                harmAndFzxMapper.insert(haf);
            }
        } else {
            String fzxIds = harm.getFzxIds();
            if (StringUtils.isNotEmpty(fzxIds)) {
                String[] fzxs = fzxIds.split(",");
                for (String fzxId : fzxs) {
                    HarmAndFzx haf = new HarmAndFzx(id, fzxId, 0, new Date());
                    haf.setId(String.valueOf(snowflake.nextId()));
                    harmAndFzxMapper.insert(haf);
                }
            }
        }
        return "success";
    }


    @Override
    public IPage<Harm> getHarmData(PageParam<Harm> page, HarmParam param) {
        PageParam<Harm> harmPageParam = harmMapper.getHarmData(page,param);
        return harmPageParam;
    }

    @Override
    public String removeHarm(String ids) {
        String flg = "success";
        String[] id = ids.split(",");
        // 将获取的多个ID分解
        for (int i = 0; i < id.length; i++) {
            Harm harmd = harmMapper.selectOne(new QueryWrapper<Harm>().eq("id", id[i])
                    .eq("is_delete", 0));
            if (ObjectUtils.isNotEmpty(harmd)) {
                // 被危害因素取值范围引用无法删除，排除删除数据的影响
                Whysqzfw whys = whysqzfwMapper.selectOne(new QueryWrapper<Whysqzfw>().eq("harm_name", harmd.getId()).eq("is_delete", 0));
                ZyVsSummary zyvs = zyVsSummaryMapper.selectOne(new QueryWrapper<ZyVsSummary>().eq("occupation_diagnosis", harmd.getId()).eq("is_delete", 0));
                if (whys != null) {
                    throw new ServiceException( "无法删除！【" + harmd.getHarmName() + "】在别处已被占用");
                } else if (zyvs != null) {
                    throw new ServiceException( "无法删除！【" + harmd.getHarmName() + "】在职业处理意见模块已被占用");
                } else {
                    //将isDelete设置为1，为删除
                    harmd.setIsDelete(1);
                    this.updateById(harmd);
                }
                List<HarmAndFzx> hafs = harmAndFzxMapper.selectList(new QueryWrapper<HarmAndFzx>().eq("harm_id", id[i]));
                for (HarmAndFzx haf : hafs) {
                    haf.setTbzt(0);
                    harmAndFzxMapper.updateById(haf);
                }
            } else {
                throw new ServiceException( "删除失败");
            }
        }

        List<HarmAndFzx> hafs = harmAndFzxMapper.selectList(new QueryWrapper<HarmAndFzx>().in("harm_id", id));
        for (HarmAndFzx haf : hafs) {
            haf.setTbzt(0);
            harmAndFzxMapper.updateById(haf);
        }
        return flg;
    }

    @Override
    public IPage<Harm> getHarmDataSimple(PageParam<Harm> page, HarmParam param) {

        return harmMapper.getListSimple(page, param);
    }

    /**
     * 获取所有危害因素
     * @param page
     * @param inputCode
     * @return
     */
    @Override
    public IPage<Harm> getAllHarmname(PageParam<Harm> page, String inputCode) {
        QueryWrapper<Harm> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(inputCode)) {
            queryWrapper.like("input_code", inputCode.toUpperCase());
        }
        queryWrapper.eq("is_delete", 0);
        PageParam<Harm> harmPageParam = harmMapper.selectPage(page, queryWrapper);
        return harmPageParam;
    }

    /**
     * 选择当前用户分中心的危害因素
     * @param page
     * @param key
     * @return
     */
    @Override
    public IPage<JhysDataVo> getJhysDataByFzx(PageParam<JhysDataVo> page, String key) {
        String cId = SecurityUtils.getCId();
        if (ObjectUtils.isNotEmpty(key)){
            key = key.trim();
        }
        return harmMapper.getJhysDataByFzx(page,cId,key);
    }
}

