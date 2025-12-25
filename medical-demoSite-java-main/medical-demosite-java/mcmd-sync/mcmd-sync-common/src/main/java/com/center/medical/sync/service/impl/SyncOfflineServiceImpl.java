package com.center.medical.sync.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.json.JSONUtil;
import com.center.medical.bean.enums.SyncOptType;
import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.common.constant.CacheConstants;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.redis.RedisSetUtil;
import com.center.medical.message.constant.MessageConstant;
import com.center.medical.message.service.LocalProducer;
import com.center.medical.sync.bean.dto.SyncDataDto;
import com.center.medical.sync.bean.model.SyncData;
import com.center.medical.sync.bean.model.SyncFile;
import com.center.medical.sync.service.SyncOfflineService;
import com.center.medical.sync.service.SyncSqlRunService;
import com.center.medical.system.bean.model.SysTableConfig;
import com.center.medical.system.service.SysTableConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 线下数据同步操作业务实现
 *
 * @author 路飞船长
 * @since 2023-02-03 08:42:31
 */
@Slf4j
@Service("syncOfflineService")
@RequiredArgsConstructor
public class SyncOfflineServiceImpl implements SyncOfflineService {

    private final SyncSqlRunService syncSqlRunService;
    private final SysTableConfigService sysTableConfigService;
    private final Snowflake snowflake;
    private final LocalProducer localProducer;
    /**
     * 当前分中心同步到线上的文件路由键，标识当前分中心系统产生的同步文件
     */
    @Value("${sync.amq.offline.routingKey.file2}")
    private String LC_SYNC_CF_ROUTING_KEY;
    /**
     * 当前分中心同步到线上的文件交换机，接收当前分中心系统产生的同步文件
     */
    @Value("${sync.amq.offline.topicExchange.file2}")
    private String LC_SYNC_CF_TOPIC_EXCHANGE;

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
//            log.info("同步数据数量:{}", set.size());
//            List<SyncData> syncDataList = new ArrayList<>(); //构建的同步数据操作记录的集合容器
//            List<SyncFile> syncFileList = new ArrayList<>(); //构建的同步文件操作记录的集合容器
            for (Object item1 : set) {
                SyncData item = JSONUtil.toBean(JSONUtil.toJsonStr(item1), SyncData.class);
                try {
                    SysTableConfig tc = sysTableConfigService.getInfoByTN(item.getBizTable());//获取当前操作的数据表配置信息
//                log.info("是否需要同步数据:{}、{}", needSync(tc), item);
                    if (needSync(tc)) {
                        String pkName = tc.getKeyName(); //表的主键名称
                        String tableName = tc.getTableName(); //表名
                        List<Map<String, Object>> syncItemList = new ArrayList<>(); //操作的表的记录集合容器
                        List<String> syncItemStrlList = new ArrayList<>(); //操作的表的记录字符串集合容器
                        switch (item.getOptType()) {
                            case 2:
                                //插入
                                try {
                                    String bizId = item.getBizId();
                                    if (!bizId.startsWith("'")) {
                                        bizId = "'" + bizId + "'";
                                    }
                                    String selectSql = String.format("SELECT * FROM " + item.getBizTable() + " WHERE " + tc.getKeyName() + " = " + bizId);
                                    if (tc.getTableName().equals("kd_remittance")) {
                                        log.info("同步数据操作-插入操作：{}、{}、{}", selectSql, item, tc);
                                    }
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
//                                log.info("更新操作：{}、{}、{}", selectSql, item, tc);
                                    List<Map<String, Object>> updateItemList = syncSqlRunService.queryForList(selectSql); //查询更新的记录
//                                log.info("更新操作2：{}、{}、{}", updateItemList);
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
                                // log.info("删除操作：{}、{}、{}", tableName, pkName, pkValue);
                                if (StringUtils.isNotBlank(pkValue)) {
                                    syncItemStrlList.add(syncSqlRunService.generateDeleteSql(tableName, pkName, pkValue));
                                }
                                break;
                            default:
                        }
                        item.setStatus(1);
                        item.setVersion(1);
                        for (int i = 0; i < syncItemStrlList.size(); i++) {
                            String syncRunSql = syncItemStrlList.get(i);
                            if (StringUtils.isNotBlank(syncRunSql)) {
                                item.setId(snowflake.nextId());
                                item.setCreatedate(new Date());
                                //RedisUtil.set(CacheConstants.SYNC_DATA_UNDO_KEY + item.getId(), item, 0);
                                // syncDataList.add(newItem);
                                //添加数据同步的消息队列中
                                SyncDataDto syncDataDto = new SyncDataDto(item.getId(), item.getBizId(), item.getOptType(), item.getBizTable(), syncRunSql, tc.getNeedSync(), ZhongkangConfig.getFzxId());
                                if (tc.getSyncNow() == 1) {
                                    //log.info("添加数据同步的消息队列中：{}", syncDataDto);
                                    //RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_ON_OFF_LINE, JSONUtil.toJsonStr(syncDataDto), DateUtil.current());
                                    try {
                                        if (tc.getTableName().equals("kd_remittance")) {
                                            log.info("同步数据操作-插入操作3：{}", JSONUtil.toJsonStr(syncDataDto));
                                        }
                                        localProducer.sendMessage(MessageConstant.LC_SYNC_DATA_TOPIC_EXCHANGE, MessageConstant.LC_SYNC_DATA_ROUTING_KEY, JSONUtil.toJsonStr(syncDataDto));
                                    } catch (Exception e) {
                                        log.error("消息队列异常，生产数据失败：syncDataDto={}", syncDataDto);
                                        //TODO wait 通知管理员及时处理
                                        return;
                                    }
                                } else {
//                                log.info("添加延时推送缓存中：{}", syncDataDto);
                                    RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_ON_OFF_LINE_DELAY, JSONUtil.toJsonStr(syncDataDto), DateUtil.current());
//                                localProducer.sendMessage(MessageConstant.LC_SYNC_DATA_DELAY_TOPIC_EXCHANGE, MessageConstant.LC_SYNC_DATA_DELAY_ROUTING_KEY, JSONUtil.toJsonStr(syncDataDto));
                                }

                                //判断是否有需要同步的文件
//                            log.info("判断是否有需要同步的文件:{}、{}、{}", tc.getFileKeyName(), item.getOptType(), (StringUtils.isNotBlank(tc.getFileKeyName()) && item.getOptType() != SyncOptType.DELETE.value()));
                                if (StringUtils.isNotBlank(tc.getFileKeyName()) && item.getOptType() != SyncOptType.DELETE.value()) {
                                    String[] fileKeys = tc.getFileKeyName().split(",");
//                                log.info("判断是否有需要同步的文件fileKeys：{}", fileKeys);
                                    for (String key : fileKeys) {
                                        //有文件存储字段，查看是否已同步过：这个文件名的文件最后一次有效操作是不是删除，如果是则执行同步，反之则不需同步
                                        Map bean = JSONUtil.toBean(syncRunSql, Map.class);
                                        String imgUrlStr = (String) bean.get(key);
                                        // log.info("判断是否有需要同步的文件imgUrlStr：{}", imgUrlStr);
                                        if (StringUtils.isNotBlank(imgUrlStr)) {
                                            String[] imgList = imgUrlStr.split(",");
                                            // log.info("判断是否有需要同步的文件imgList：{}", imgList);
                                            for (String imgUrl : imgList) {
                                                // log.info("判断是否有需要同步的文件imgUrl：{}", imgUrl);
                                                if (StringUtils.isNotBlank(imgUrl)) {
                                                    SyncFile syncFileLast = null;//syncFileService.getLastOne(imgUrl, Arrays.asList(0, 1, 2, 3));
//                                                log.info("判断是否有需要同步的文件syncFileLast：{}、{}、{}", syncFileLast);
                                                    if (Objects.isNull(syncFileLast) || syncFileLast.getOptionType() == 2) {
                                                        //不存在同步记录或者最新一次是删除操作，则需要执行同步操作
                                                        SyncFile syncFile = new SyncFile();
                                                        syncFile.setCreatedate(new Date());
                                                        syncFile.setStatus(0);
                                                        syncFile.setImageUrl(imgUrl);
                                                        syncFile.setOptionType(1);
                                                        syncFile.setId(snowflake.nextId());
                                                        //RedisUtil.set(CacheConstants.SYNC_FILE_UNDO_KEY + syncFile.getId(), syncFile, 0);
                                                        //RedisSetUtil.addToSortedSet(CacheConstants.SYNC_FILE_ON_OFF_LINE, syncFile, DateUtil.current());

                                                        //添加到文件上传消息队列中
                                                        localProducer.sendMessage(LC_SYNC_CF_TOPIC_EXCHANGE, LC_SYNC_CF_ROUTING_KEY, JSONUtil.toJsonStr(syncFile));
                                                        // syncFileList.add(syncFile);
                                                    }
                                                }
                                            }

                                        }

                                    }
                                }

                            }
                        }

                    }
//                log.info("操作成功，删除缓存：{}", set.size());
//                RedisSetUtil.remove(CacheConstants.SYNC_DATA_OBJECTS, item1);
                } catch (Exception e) {
                    // 总共有三次同步的机会
                    if (item.getStatus() == 2) {
                        //再重新进入同步队列
                        item.setStatus(3);
                        RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, item, DateUtil.currentSeconds());
                    }
                    if (item.getStatus() == 3) {
                        //再重新进入同步队列
                        item.setStatus(4);
                        RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, item, DateUtil.currentSeconds());
                    }
                    log.error("同步失败，item={}", item);
                }
            }
            /* if (CollectionUtil.isNotEmpty(syncDataList)) {
            syncDataService.saveBatch(syncDataList);
             }
             if (CollectionUtil.isNotEmpty(syncFileList)) {
            syncFileService.saveBatch(syncFileList);
             }*/

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
//                        log.info("admin类型用户不同步:{}", map.get("user_type"));
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
     * 判断当前操作表是否需要同步
     *
     * @param tc
     * @return
     */
    private Boolean needSync(SysTableConfig tc) {
        if (Objects.isNull(tc) || tc.getNeedSync() == 0) {
            return Boolean.FALSE;
        }
        if (tc.getNeedSync() == 1 || tc.getNeedSync() == 2) {
            //线下系统，判断该表是否是双向同步和线下同步线上
            if (tc.getSyncType() == 2 || tc.getSyncType() == 3) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }


}

