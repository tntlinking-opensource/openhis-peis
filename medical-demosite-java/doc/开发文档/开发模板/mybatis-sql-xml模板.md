## 1. <where></where>条件编写模板
```xml
<where>
    <if test="param.branchIds!=null">
        AND ppe.branch_id IN
        <foreach collection="param.branchIds" item="brandId" open="(" close=")" separator=",">
            #{brandId}
        </foreach>
    </if>
    <if test="param.statusList!=null">
        AND bo.`status` IN
        <foreach collection="param.statusList" item="status" open="(" close=")" separator=",">
            #{status}
        </foreach>
    </if>
    <if test="param.phone!=null and param.phone!=''">
        AND bo.phone LIKE concat('%',#{param.phone},'%')
    </if>
    <if test="param.orderId!=null ">
        AND bo.order_id = #{param.orderId}
    </if>
    <if test="param.startTime!=null">
        AND bo.createdate <![CDATA[ >= ]]> #{param.startTime}
    </if>
    <if test="param.endTime!=null">
        AND bo.createdate <![CDATA[ < ]]> #{param.endTime}
    </if>
    <if test="param.isBan!=null and param.isBan==0">
        AND (co.is_ban = #{param.isBan} OR co.is_ban IS NULL)
    </if>
    <if test="param.isBan!=null and param.isBan==1">
        AND co.is_ban = #{param.isBan}
    </if>
    <if test="param.inputCode!=null and param.inputCode!=''">
        AND input_code LIKE concat('%',#{param.inputCode},'%')
    </if>
    <choose>
        <when test="dh==0">
            AND t.tjlx!=2
        </when>
        <otherwise>
            AND t.tjlx!=0
        </otherwise>
    </choose>
    AND bo.user_id = #{userId}
    AND bo.delete_status = 0
    is_delete = 0
    AND
</where>
<where>
<if test="param.!=null and param.!=''">
    AND tt LIKE concat('%',#{param.},'%')
</if>
</where>
        GROUP BY ybo.order_id
        ORDER BY ybo.order_id DESC/AEC
```

### 2.实现查询表某个字段以分隔符拼接并返回字符串，使用`GROUP_CONCAT()`
```sql
    SELECT GROUP_CONCAT(dept_name SEPARATOR ',')
    FROM sys_dept
    WHERE is_function = 1
    ORDER BY create_time DESC
```

### 3. 新增自动生成主键
```java
@TableId(type = IdType.ASSIGN_ID)
@ApiModelProperty(value = "ID")
private String id;
```

### 4.实体类时间格式序列化
```java
@JsonFormat的用法：
        用在需要查询数据的的实体字段上加上这个注解（出参时使用）
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
private Date testTime;

@DateTimeFormat的用法：
        表单自动封装映射对象时，在对应的接收前端数据对应的属性上（入参vo）
@DateTimeFormat(pattern = "yyyy-MM-dd")
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
private Date inputTime;
```

### 5.新增数据表字段
```sql
ALTER TABLE `medicalcenter`.`md_items_and_fzx_new` 
ADD COLUMN `coopprice` decimal(8,2) DEFAULT NULL COMMENT '外部价' AFTER `unitprice`,
ADD COLUMN `innerprice` decimal(8,2) DEFAULT NULL COMMENT '内部价' AFTER `unitprice`;
```

