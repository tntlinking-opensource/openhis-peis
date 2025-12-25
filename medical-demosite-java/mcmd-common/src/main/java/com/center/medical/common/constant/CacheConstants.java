package com.center.medical.common.constant;

/**
 * 缓存的key 常量
 *
 * @author 路飞
 */
public class CacheConstants {
    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /**
     * redis分中心简码的key
     */
    public static final String BRANCH_JM_KEY = "branch_jm_list:";

    /**
     * 分中心数据 cache key
     */
    public static final String BRANCH_LIST_KEY = "branch_list:";

    /**
     * 开放新系统的分中心数据 cache key
     */
    public static final String BRANCH_LIST_OPENED_KEY = "branch_list_opened";

    /**
     * 分中心开放标识缓存 cache key
     */
    public static final String BRANCH_OPENED_FLAG_KEY = "branch_opened_flag";

    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /**
     * 限流 redis key
     */
    public static final String RATE_LIMIT_KEY = "rate_limit:";

    /**
     * 登录账户密码错误次数 redis key
     */
    public static final String PWD_ERR_CNT_KEY = "pwd_err_cnt:";

    /**
     * redis数据变化记录set的key
     */
    public static final String SYNC_DATA_OBJECTS = "SyncDataObjects";

    /**
     * redis数据立即待同步记录set的key
     */
    public static final String SYNC_DATA_ON_OFF_LINE = "SyncDataOnOffLine";

    /**
     * uploadDataDelay定时任务是否正在执行中的key
     */
    public static final String UPLOAD_DATA_DELAY_TASK_RUNNING = "uploadDataDelayRunning";

    /**
     * syncDataToDB定时任务是否正在执行中的key
     */
    public static final String SYNC_DATA_TO_DB_TASK_RUNNING = "syncDataToDBRunning";

    /**
     * redis数据空闲待同步记录set的key
     */
    public static final String SYNC_DATA_ON_OFF_LINE_DELAY = "SyncDataOnOffLineDelay";

    /**
     * 数据同步记录待执行同步 cache key
     */
    public static final String SYNC_DATA_UNDO_KEY = "sync_data_undo:";

    /**
     * 数据同步记录已执行同步(成功) cache key
     */
    public static final String SYNC_DATA_DONE_SUCCESS_KEY = "sync_data_done_success:";

    /**
     * 数据同步记录已执行同步(失败) cache key
     */
    public static final String SYNC_DATA_DONE_FAIL_KEY = "sync_data_done_fail:";

    /**
     * redis文件待同步记录set的key
     */
    public static final String SYNC_FILE_ON_OFF_LINE = "SyncFileOnOffLine";

    /**
     * redis文件文件同步记录检查set的key
     */
    public static final String SYNC_FILE_CHECK = "SyncFileCheck";

    /**
     * 文件同步记录待执行同步 cache key
     */
    public static final String SYNC_FILE_UNDO_KEY = "sync_file_undo:";

    /**
     * 文件同步记录已执行同步 cache key
     */
    public static final String SYNC_FILE_DONE_KEY = "sync_file_done:";

    /**
     * 文件同步到当前分中心记录已执行同步 cache key
     */
    public static final String SYNC_FILE_DOWN_DONE_KEY = "sync_file_down_done:";

    /**
     * 文件同步下载到分中心失败文件 cache key
     */
    public static final String SYNC_FILE_DOWN_FAILED_KEY = "sync_file_down_failed:";

    /**
     * 文件上传到线上记录已执行同步 cache key
     */
    public static final String SYNC_FILE_UP_DONE_KEY = "sync_file_up_done:";

    /**
     * 文件同步分中心上传到线上失败文件 cache key
     */
    public static final String SYNC_FILE_UP_FAILED_KEY = "sync_file_up_failed:";

    /**
     * 插入中间库失败的对象 cache key
     */
    public static String SEND_MIDDLE_DB_FAIL_KEY = "send_middle_db_fail:";

    /**
     * 本地缓存文件存储基础路径
     */
    public static String LOCAL_CACHING_PATH = "cache/";
    /**
     * 数据同步记录本地缓存文件存储路径
     */
    public static String LOCAL_SYNC_DATA_CACHING_PATH = "files/syncData/";
    /**
     * 操作日志记录本地缓存文件存储路径
     */
    public static String LOCAL_SYS_OPER_LOG_CACHING_PATH = "files/sysOperLog/";
    /**
     * 操作日志记录本地缓存文件存储路径
     */
    public static String LOCAL_SYS_JOB_LOG_CACHING_PATH = "files/sysJobLog/";

    /**
     * redis dicom文件上传失败记录的key
     */
    public static String FAILED_DICOM_FILE_KEY = "FailedDicomFile::";

    /**
     * redis dicom文件上传失败记录缓存的时间：7天
     */
    public static Long FAILED_DICOM_FILE_TIME = 7 * 24 * 60 * 60L;

    /**
     * dicom文件接收记录set
     */
    public static String DICOM_FILE_SET_KEY = "DICOM_FILES";

    /**
     * 记录多中心备单的体检者登记后，删除其他非到检的中心的体检数据，最近一次删除操作的体检者的登记时间
     */
    public static String LAST_DELETE_OBP_REGISTERTIME_KEY = "LAST_DELETE_OBP_REGISTERTIME";

    /**
     * 登录用户 redis key
     */
    public static final String VERIFICATION_CODE_KEY = "verification_code_key:";
}
