<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${mapper_namespace}">

	<!-- 获取一个实体 -->
	<select id="get" parameterType="${mapper_class}" resultType="${mapper_class}">
		select * from
		${mapper_table}
		<where>
			1 = 1
		</where>
	</select>
	
	<!-- 获取全部实体 -->
	<select id="getAll" parameterType="${mapper_class}" resultType="${mapper_class}">
		<![CDATA[
		select * from ${mapper_table} 
		]]>
	</select>
	
	<!-- 保存一个实体 -->
	<insert id="save" parameterType="${mapper_class}"
		useGeneratedKeys="true" keyProperty="id">
		insert into ${mapper_table} (${mapper_talbe_cols})
		values
		(
		${mapper_po_cols}
		)
	</insert>
	
	<!-- 更新一个实体 -->
	<update id="update" parameterType="${mapper_class}">
		update ${mapper_table} set
		${mapper_if_condition}
		<where>
		    1 = 1
		</where>
	</update>
	
	<!-- 删除一个实体 -->
	<delete id="delete" parameterType="${mapper_class}">
		delete from ${mapper_table}
		<where>
		    1 = 1
		</where>
	</delete>

</mapper>