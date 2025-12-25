### MybatisPlus分页连表查询size和total错误的修复方案

原mapper.xml程序实现

```xml

<resultMap type="com.xunying.keji.platform.entity.Order" id="OrderMap">
    <id property="id" column="id"/>
    <result property="orderId" column="order_id"/>
    <result property="orderNumber" column="order_number"/>
    <result property="prodName" column="prod_name"/>
    <result property="userName" column="user_name"/>
    <result property="total" column="total"/>
    <collection property="orderItemList" javaType="java.util.List"
                ofType="com.xunying.keji.platform.entity.orderItem">
        <result property="itemId" column="item_id"/>
        <result property="orderId" column="order_id"/>
        <result property="cost" column="cost"/>
        <result property="price" column="price"/>
        <result property="status" column="status"/>
    </collection>
</resultMap>

<select id="getPageList" resultMap="OrderMap">
SELECT o.*, oi.* FROM order o
LEFT JOIN xy_order_item oi ON o.order_id=oi.order_id
<where>
    o.status = #{status}
</where>
ORDER BY o.order_number DESC
</select>
```

修复方案

```xml

<resultMap type="com.xunying.keji.platform.entity.Order" id="OrderMap">
    <id property="orderId" column="order_id"/>
    <result property="orderNumber" column="order_number"/>
    <result property="prodName" column="prod_name"/>
    <result property="userName" column="user_name"/>
    <result property="total" column="total"/>
    <collection property="orderItemList" javaType="java.util.List" column="order_id"
                ofType="com.xunying.keji.platform.entity.orderItem" select="getItemList">
    </collection>
</resultMap>

<resultMap type="com.xunying.keji.platform.entity.orderItem" id="OrderItemMap">
<id property="itemId" column="item_id"/>
<result property="orderId" column="order_id"/>
<result property="cost" column="cost"/>
<result property="price" column="price"/>
<result property="status" column="status"/>
</resultMap>

<select id="getPageList" resultMap="OrderMap">
SELECT o.* FROM order o
<where>
    o.status = #{status}
</where>
ORDER BY o.order_number DESC
</select>

<select id="getItemList" resultMap="OrderItemMap">
SELECT oi.* FROM xy_order_item oi
<where>
    oi.order_id = #{order_id}
</where>
</select>
```
