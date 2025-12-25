##引入mybatis支持
$!mybatisSupport

##设置保存名称与保存位置
$!callback.setFileName($tool.append($!{tableInfo.name}, "Mapper.xml"))
$!callback.setSavePath($tool.append($modulePath, "/src/main/resources/mapper"))

##拿到主键
#if(!$tableInfo.pkColumn.isEmpty())
#set($pk = $tableInfo.pkColumn.get(0))
#end

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="$!{tableInfo.savePackageName}.dao.$!{tableInfo.name}Mapper">

	<resultMap type="$!{tableInfo.savePackageName}.bean.model.$!{tableInfo.name}" id="$!{tableInfo.name}Map">
		#foreach($column in $tableInfo.fullColumn)
			#if($!tableInfo.pkColumn.size()>0 && $!tableInfo.pkColumn.get(0).name.equals(${column.name}))
				<id property="$!column.name" column="$!column.obj.name"/>
			#end
			#if(!$!tableInfo.pkColumn.get(0).name.equals(${column.name}))
				<result property="$!column.name" column="$!column.obj.name"/>
			#end
		#end
	</resultMap>

	<!-- 数据表字段属性 -->
	<sql id="select$!{tableInfo.name}Vo">
		select#foreach($column in $tableInfo.fullColumn) $!column.obj.name#if($foreach.count != $tableInfo.fullColumn.size()),#end#end
		from $tableInfo.obj.name
	</sql>

	<!-- 查询列表数据 -->
	<select id="getPage" resultMap="$!{tableInfo.name}Map">
		<include refid="select$!{tableInfo.name}Vo"/>
	</select>

	#if($!tableInfo.pkColumn.size()>0)
		<!-- 根据主键id获取记录详情 -->
		<select id="getInfoById" resultMap="$!{tableInfo.name}Map">
			<include refid="select$!{tableInfo.name}Vo"/>
			<where>
				$!tableInfo.pkColumn.get(0).name = #{id}
			</where>
		</select>
	#end

</mapper>
