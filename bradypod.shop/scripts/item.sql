CREATE DATABASE IF NOT EXISTS `item_center` default charset utf8;
use `item_center`;

DROP TABLE IF EXISTS `t_item_info`;
CREATE TABLE `t_item_info` (
	`id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '商品id',
	`user_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '用户id',
	`item_type` TINYINT(3) UNSIGNED NOT NULL COMMENT '商品类型：1 自营商品',
	`ctg_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '商品分类id',
	`title` VARCHAR(500) NOT NULL DEFAULT '' COMMENT '商品标题',
	`description` VARCHAR(500) NOT NULL DEFAULT '' COMMENT '商品描述',
	`price` INT(11) NOT NULL DEFAULT '0' COMMENT '金额单位（分）',
	`status` TINYINT(1) NOT NULL DEFAULT '1' COMMENT '商品状态  1 正常',
	`create_time` DATETIME NOT NULL COMMENT '创建时间',
	`update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
	PRIMARY KEY (`id`)
)
COMMENT='商品信息'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;