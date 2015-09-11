CREATE DATABASE IF NOT EXISTS `item_center` default charset utf8;
use `item_center`;

-- 商品评论表
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE IF NOT EXISTS `t_comment` (
	`id` BIGINT(20) NOT NULL COMMENT '主键',
	`user_id` BIGINT(20) NOT NULL COMMENT '创建评论的用户id',
	`entity_user_id` BIGINT(20) NOT NULL COMMENT '创建评论的用户id',
	`entity_id` BIGINT(20) NOT NULL COMMENT '被评论的实体id',
	`entity_type` TINYINT(4) NULL DEFAULT '1' COMMENT '被评论的实体类型',
	`entity_info` VARCHAR(300) NOT NULL COMMENT '被评论的对象信息',
	`star_num` INT(11) NOT NULL COMMENT '评星数 1-5',
	`title` VARCHAR(50) NULL DEFAULT NULL COMMENT '评论标题',
	`description` VARCHAR(250) NULL DEFAULT NULL COMMENT '评论描述',
	`pic_url` VARCHAR(100) NULL DEFAULT NULL COMMENT '买家上传图片地址',
	`status` TINYINT(4) NULL DEFAULT '1' COMMENT '图片状态 1: 正常; 2: 删除',
	`create_time` DATETIME NOT NULL COMMENT '记录生成时间',
	`update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
	PRIMARY KEY (`id`)
)
COMMENT='商品评论'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;                

-- 商品统计
DROP TABLE IF EXISTS `t_comment_count`;
CREATE TABLE `t_comment_count` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`entity_id` BIGINT(20) NOT NULL COMMENT '被评论的实体id',
	`entity_type` TINYINT(4) NULL DEFAULT '1' COMMENT '被评论的实体类型',
	`star_num` INT(11) NOT NULL COMMENT '评星数 1-5',
	`comment_id` BIGINT(20) NOT NULL COMMENT '评论键',
	PRIMARY KEY (`id`)
)
COMMENT='商品评论统计'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;