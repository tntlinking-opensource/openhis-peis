package com.center.medical.sync.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.json.JSONUtil;
import com.center.medical.bean.enums.SyncOptType;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.common.constant.CacheConstants;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.redis.RedisSetUtil;
import com.center.medical.message.service.PlatformProducer;
import com.center.medical.sync.bean.dto.SyncDataDto;
import com.center.medical.sync.bean.model.SyncData;
import com.center.medical.sync.bean.model.SyncFile;
import com.center.medical.sync.service.SyncFileService;
import com.center.medical.sync.service.SyncOnlineService;
import com.center.medical.sync.service.SyncSqlRunService;
import com.center.medical.system.bean.model.SysTableConfig;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.SysTableConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 实现
 *
 * @author 路飞船长
 * @since 2023-02-03 08:42:31
 */
@Slf4j
@Service("syncOnlineServiceService")
@RequiredArgsConstructor
public class SyncOnlineServiceImpl implements SyncOnlineService {

    private final SyncSqlRunService syncSqlRunService;
    private final SysTableConfigService sysTableConfigService;
    private final ISysBranchService iSysBranchService;
    private final Snowflake snowflake;
    private final SyncFileService syncFileService;
    private final PlatformProducer platformProducer;
    /**
     * 线上数据同步到分中心路由键，标识线上系统产生的当前分中心同步数据
     */
    @Value("${sync.amq.online.routingKey.data}")
    private String PF_SYNC_CD_ROUTING_KEY;
    /**
     * 线上文件同步到分中心路由键，标识线上系统产生的当前分中心同步文件
     */
    @Value("${sync.amq.online.routingKey.file}")
    private String PF_SYNC_CF_ROUTING_KEY;
    /**
     * 线上数据同步到分中心交换机，接收线上系统产生的分中心的同步数据
     */
    @Value("${sync.amq.online.topicExchange.data}")
    private String PF_SYNC_CD_TOPIC_EXCHANGE;
    /**
     * 线上文件同步到分中心交换机，接收线上系统产生的分中心的同步文件
     */
    @Value("${sync.amq.online.topicExchange.file}")
    private String PF_SYNC_CF_TOPIC_EXCHANGE;

    /**
     * 获取同步数据
     *
     * @param set 同步数据操作记录
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void getSyncContent(Set<Object> set) {
        if (set.size() > 0) {
//            List<SyncData> syncDataList = new ArrayList<>(); //构建的同步数据操作记录的集合容器
//            List<SyncFile> syncFileList = new ArrayList<>(); //构建的同步文件操作记录的集合容器
            for (Object item1 : set) {
                SyncData item = JSONUtil.toBean(JSONUtil.toJsonStr(item1), SyncData.class);
                try {
                    SysTableConfig tc = sysTableConfigService.getInfoByTN(item.getBizTable());//获取当前操作的数据表配置信息
                    //log.info("获取同步数据:{}、{}", needSync(tc), item);
                    // String delId = null; //redis缓存中删除操作的唯一标识
                    if (needSync(tc)) {
                        String pkName = tc.getKeyName(); //表的主键名称
                        String tableName = tc.getTableName(); //表名
                        List<Map<String, Object>> syncItemList = new ArrayList<>(); //操作的表的记录集合容器
                        List<String> syncItemStrlList = new ArrayList<>(); //操作的表的记录字符串集合容器
                        // Map<String, Object> syncItem = new HashMap<>();
                        // String syncRunSql = "";
                        switch (item.getOptType()) {
                            case 2:
                                //插入
                                try {
                                    String bizId = item.getBizId();
                                    if (!bizId.startsWith("'")) {
                                        bizId = "'" + bizId + "'";
                                    }
                                    String selectSql = String.format("SELECT * FROM " + item.getBizTable() + " WHERE " + tc.getKeyName() + " = " + bizId);
                                    // log.info("插入操作：{}、{}、{}", selectSql, item, tc);
                                    Map<String, Object> syncItem1 = syncSqlRunService.queryForMap(selectSql); //查询新增的记录
                                    if (Objects.nonNull(syncItem1) && needEexecuteSync(tc, syncItem1)) {
                                        syncItemList.add(syncItem1);
                                        syncItemStrlList.add(JSONUtil.toJsonStr(syncItem1));//syncSqlRunService.generateInsertSql(tableName, Arrays.asList(syncItem));
                                    }
                                } catch (EmptyResultDataAccessException e) {
                                    // 拦截器拦截更新处理查询结果异常
                                    log.error("拦截器拦截更新处理查询结果异常{}、{}", item, e);
                                }
                                break;
                            case 3:
                                //更新
                                try {
                                    String bizId = item.getBizId();
                                    if (!bizId.startsWith("'")) {
                                        bizId = "'" + bizId + "'";
                                    }
                                    String selectSql = String.format("SELECT * FROM " + item.getBizTable() + " WHERE " + tc.getKeyName() + " IN (" + bizId + ")");
                                    // log.info("更新操作：{}、{}、{}", selectSql, item, tc);
                                    List<Map<String, Object>> updateItemList = syncSqlRunService.queryForList(selectSql); //查询更新的记录
                                    for (Map<String, Object> syncItem1 : updateItemList) {
                                        if (Objects.nonNull(syncItem1) && needEexecuteSync(tc, syncItem1)) {
                                            syncItemList.add(syncItem1);
                                            syncItemStrlList.add(JSONUtil.toJsonStr(syncItem1));//syncSqlRunService.generateUpdateSql(tableName, pkName, Arrays.asList(syncItem));
                                        }
                                    }

                                } catch (EmptyResultDataAccessException e) {
                                    // 拦截器拦截更新处理查询结果异常
                                    log.error("拦截器拦截更新处理查询结果异常{}、{}", item, e);
                                }
                                break;
                            case 4:
                                //删除
                                String pkValue = item.getBizId();
//                                 log.info("删除操作：{}、{}、{}", tableName, pkName, pkValue);
                                if (StringUtils.isNotBlank(pkValue)) {
                                    //判断是否已删除成功
                                    String selectSql = String.format("SELECT * FROM " + item.getBizTable() + " WHERE " + tc.getKeyName() + " IN (" + pkValue + ")");
                                    try {
                                        syncSqlRunService.queryForMap(selectSql); //查询要删除的记录
                                        log.info("没删除成功无需同步：{}.{}.pkValue={}", tableName, pkName, pkValue);
                                    }catch (EmptyResultDataAccessException e){
                                        syncItemStrlList.add(syncSqlRunService.generateDeleteSql(tableName, pkName, pkValue));
//                                        log.info("删除操作syncItemStrlList：{}、{}、{}", syncItemStrlList);
                                    }
                                }
                                break;
                            default:
                        }
                        // 获取同步的分中心，如果是线下同步至线上则不需要做这一步，如果是线上同步至线下则需要
                        for (int i = 0; i < syncItemStrlList.size(); i++) {
                            String syncRunSql = syncItemStrlList.get(i);
                            List<String> cidList = new ArrayList<>();
                            // log.info("表配置信息:{}", tc);
                            if (item.getOptType() == 4) {
                                item.setIsAll(1);
                                //获取所有分中心
                                List<SysBranch> branches = iSysBranchService.getOpenList(1);
                                cidList = branches.stream().map(SysBranch::getBranchId).collect(Collectors.toList());
                            } else {
                                if (tc.getNeedSync() == 1) {
                                    item.setIsAll(1);
                                    //获取所有分中心
                                    List<SysBranch> branches = iSysBranchService.getOpenList(1);
                                    cidList = branches.stream().map(SysBranch::getBranchId).collect(Collectors.toList());
                                    // log.info("同步至所有分中心:{}、{}", branches, cidList);
                                } else if (tc.getNeedSync() == 2) {
                                    // log.info("同步至指定分中心:{}", tc);
                                    Map<String, Object> syncItem = syncItemList.get(i);
                                    item.setIsAll(0);
                                    if (CollectionUtil.isNotEmpty(syncItemList)) {
                                        // log.info("同步至指定分中心:{}、{}", tc, syncItem);
                                        cidList = syncSqlRunService.getCid(tc, syncItem);
                                        // log.info("同步至指定分中心:{}、{}、{}", cidList, tc, syncItem);
                                        if (CollectionUtil.isEmpty(cidList)) {
                                            // 没有同步的没有分中心，移除操作记录变化缓存记录
                                            log.info("没有分中心，跳过：{}", item.getBizTable() + "::" + item.getBizId());
                                            // if (StringUtils.isNotBlank(delId)) {
                                            // RedisUtil.del(Constants.SYNC_DATA_DELETE + delId);
                                            // }
                                            continue;
                                        }

                                    } else {
                                        // 不存在对象了，移除操作记录变化缓存记录
                                        log.info("不存在对象，跳过：{}", item.getBizTable() + "::" + item.getBizId());
                                        continue;
                                    }
                                } else {
                                    // 不需要同步，移除操作记录变化缓存记录
                                    log.info("不需要同步，跳过：{}", item.getBizTable() + "::" + item.getBizId());
                                    continue;
                                }
                            }

                            item.setStatus(0);
                            item.setVersion(1);
                            if (StringUtils.isNotBlank(syncRunSql)) {
                                if (CollectionUtil.isNotEmpty(cidList)) {
                                    //分中心去重
                                    cidList = cidList.stream().distinct().collect(Collectors.toList());
                                    log.info("同步的分中心：{}", cidList);
                                    for (String cid : cidList) {
                                        //添加同步数据缓存
                                        SysBranch branch = iSysBranchService.getByBranchId(cid);
                                        if (Objects.nonNull(branch)) {
                                            item.setBranchIds(cid);
//                                        SyncData newItem = mapperFacade.map(item, SyncData.class);
                                            item.setId(snowflake.nextId());
                                            item.setModifydate(new Date());
                                            // log.warn("item.getId()={}、{}、{}", cid, item.getId(), JSONUtil.toJsonStr(syncDataList.stream().map(SyncData::getId).collect(Collectors.toList())));
//                                        RedisUtil.set(CacheConstants.SYNC_DATA_UNDO_KEY + item.getId(), item, 0);
                                            //syncDataList.add(newItem);
                                            SyncDataDto syncDataDto = new SyncDataDto(item.getId(), item.getBizId(), item.getOptType(), item.getBizTable(), syncRunSql, tc.getNeedSync(), cid);
                                            // log.info(Constants.SYNC_DATA_ON_OFF_LINE + "_" + branch.getJm() + "syncDataDto:{}", JSONUtil.toJsonStr(syncDataDto));
//                                        if (tc.getSyncNow() == 1) {
//                                            RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_ON_OFF_LINE + "_" + branch.getJm(), JSONUtil.toJsonStr(syncDataDto), DateUtil.current());

                                            //log.info("线上产生同步数据：{}、{}", PF_SYNC_CD_TOPIC_EXCHANGE + "." + branch.getJm(), PF_SYNC_CD_ROUTING_KEY + branch.getJm());
                                            try {
                                                platformProducer.sendMessage(PF_SYNC_CD_TOPIC_EXCHANGE + "." + branch.getJm(), PF_SYNC_CD_ROUTING_KEY + branch.getJm(), JSONUtil.toJsonStr(syncDataDto));
                                            } catch (Exception e) {
                                                log.error("消息队列异常，生产数据失败：jm={}、syncDataDto={}", branch.getJm(), syncDataDto);
                                                return;
                                            }
//                                        } else {
//                                            RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_ON_OFF_LINE_DELAY + "_" + branch.getJm(), JSONUtil.toJsonStr(syncDataDto), DateUtil.current());
//                                        }

                                            //判断是否有需要同步的文件
                                            //log.info("判断是否有需要同步的文件:{}、{}、{}", tc.getFileKeyName(), item.getOptType(), (StringUtils.isNotBlank(tc.getFileKeyName()) && item.getOptType() != SyncOptType.DELETE.value()));
                                            if (StringUtils.isNotBlank(tc.getFileKeyName()) && item.getOptType() != SyncOptType.DELETE.value()) {
                                                String[] fileKeys = tc.getFileKeyName().split(",");
                                                //log.info("判断是否有需要同步的文件fileKeys：{}", fileKeys);
                                                for (String key : fileKeys) {
                                                    //有文件存储字段，查看是否已同步过：这个文件名的文件最后一次有效操作是不是删除，如果是则执行同步，反之则不需同步
                                                    Map bean = JSONUtil.toBean(syncRunSql, Map.class);
                                                    String imgUrlStr = (String) bean.get(key);
                                                    //log.info("判断是否有需要同步的文件imgUrlStr：{}", imgUrlStr);
                                                    if (StringUtils.isNotBlank(imgUrlStr)) {
                                                        String[] imgList = imgUrlStr.split(",");
                                                        //log.info("判断是否有需要同步的文件imgList：{}", imgList);
                                                        for (String imgUrl : imgList) {
                                                            //log.info("判断是否有需要同步的文件imgUrl：{}", imgUrl);
                                                            if (StringUtils.isNotBlank(imgUrl)) {
                                                                SyncFile syncFileLast = syncFileService.getLastOne(imgUrl, Arrays.asList(0, 1, 2, 3));
                                                                //log.info("判断是否有需要同步的文件syncFileLast：{}、{}、{}", syncFileLast);
                                                                if (Objects.isNull(syncFileLast) || syncFileLast.getOptionType() == 2) {
                                                                    //不存在同步记录或者最新一次是删除操作，则需要执行同步操作
                                                                    SyncFile syncFile = new SyncFile();
                                                                    syncFile.setCreatedate(new Date());
                                                                    syncFile.setStatus(0);
                                                                    syncFile.setImageUrl(imgUrl);
                                                                    syncFile.setOptionType(1);
                                                                    syncFile.setBranchIds(item.getBranchIds());
//                                                                RedisUtil.set(CacheConstants.SYNC_FILE_UNDO_KEY + syncFile.getId(), syncFile, 0);
//                                                                RedisSetUtil.addToSortedSet(CacheConstants.SYNC_FILE_ON_OFF_LINE + "_" + branch.getJm(), syncFile, DateUtil.current());
//                                                                log.info("线上产生同步文件：{}、{}", PF_SYNC_CF_TOPIC_EXCHANGE + "." + branch.getJm(), PF_SYNC_CF_ROUTING_KEY + branch.getJm());
                                                                    platformProducer.sendMessage(PF_SYNC_CF_TOPIC_EXCHANGE + "." + branch.getJm(), PF_SYNC_CF_ROUTING_KEY + branch.getJm(), JSONUtil.toJsonStr(syncFile));
                                                                    //syncFileList.add(syncFile);
                                                                }
                                                            }
                                                        }

                                                    }

                                                }
                                            }
                                        }

                                    }
                                }
                            }
                        }

                    }
//                RedisSetUtil.remove(CacheConstants.SYNC_DATA_OBJECTS, item1);
                } catch (Exception e) {
                    // 总共有三次同步的机会
                    if (item.getStatus() <= 2) {
                        //再重新进入同步队列
                        item.setStatus(3);
                        RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, item, DateUtil.currentSeconds());
                    }
                    if (item.getStatus() == 3) {
                        //再重新进入同步队列
                        item.setStatus(4);
                        RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, item, DateUtil.currentSeconds());
                    }
                    log.error("同步失败，item={}，报错信息：{}", item1, e);
                }
            }
            // log.info("syncDataList:{},{}", syncDataList.size(), JSONUtil.toJsonStr(syncDataList));
//            syncDataService.saveBatch(syncDataList);
//            syncFileService.saveBatch(syncFileList);
            //移除操作记录变化缓存记录
            RedisSetUtil.removeRange(CacheConstants.SYNC_DATA_OBJECTS, 0L, (long) set.size() - 1);
            log.info("操作成功，删除缓存：{}", set.size());
        }

    }

    /**
     * 生成同步记录ID
     *
     * @return
     */
    private Long getSdId(List<Long> ids) {
        long id = snowflake.nextId();
        if (ids.contains(id)) {
            getSdId(ids);
        }
        return id;
    }

    /**
     * 判断是否达到了需要同步的条件
     *
     * @param tc  表配置信息
     * @param map 同步数据
     * @return
     */
    private boolean needEexecuteSync(SysTableConfig tc, Map<String, Object> map) {
        // log.info("判断是否达到了需要同步的条件.needEexecuteSync:{}、{}", tc, map);
        Boolean result;
        //判断是否是需要判断条件的表
        if (tc.getSyncCondition() == 1) {
            switch (tc.getTableName()) {
                case "crm_sellcustomer":
                    if ((Integer) map.get("khzt") == 1 && (Integer) map.get("is_delete") == 0) {
                        result = Boolean.TRUE;
                    } else {
                        result = Boolean.FALSE;
                    }
                    // log.info("判断是否达到了需要同步的条件.needEexecuteSync.result:{}", result);
                    break;
                case "sys_user":
                    // admin类型用户不同步
                    if ("admin".equals((String) map.get("user_type"))) {
                        log.info("admin类型用户不同步:{}", map.get("user_type"));
                        result = Boolean.FALSE;
                    } else {
                        result = Boolean.TRUE;
                    }
                    // log.info("判断是否达到了需要同步的条件.needEexecuteSync.result:{}", result);
                    break;
                default:
                    result = Boolean.TRUE;
            }
        } else {
            result = Boolean.TRUE;
        }
        return result;
    }


    /**
     * 判断当前操作表是否需要同步（线上系统）
     *
     * @param tc
     * @return
     */
    private Boolean needSync(SysTableConfig tc) {
        if (Objects.isNull(tc) || tc.getNeedSync() == 0) {
            return Boolean.FALSE;
        }
        if (tc.getNeedSync() == 1 || tc.getNeedSync() == 2) {
            //线上系统，判断该表是否是双向同步和线上同步线下
            if (tc.getSyncType() == 1 || tc.getSyncType() == 3) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }


    /**
     * 手动同步
     *
     * @param syncDataDtos 同步数据
     * @param branch       分中心
     * @return
     */
    public Boolean execSync(List<SyncDataDto> syncDataDtos, SysBranch branch) {
        for (SyncDataDto syncDataDto : syncDataDtos) {
            platformProducer.sendMessage(PF_SYNC_CD_TOPIC_EXCHANGE + "." + branch.getJm(), PF_SYNC_CD_ROUTING_KEY + branch.getJm(), JSONUtil.toJsonStr(syncDataDto));
        }
        return Boolean.TRUE;
    }

}

