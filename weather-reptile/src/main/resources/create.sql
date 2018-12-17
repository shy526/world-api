/*城市查询代码*/
CREATE TABLE `t_weather_code` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(20) DEFAULT NULL COMMENT '名称',
  `code` varchar(20) DEFAULT NULL COMMENT '代码',
  `parent` int(12) DEFAULT NULL COMMENT '父类',
  `zone_type` int(2) DEFAULT NULL COMMENT '地区,0 省 1 市 2 县 3 地区',
  `create_time` bigint(13) DEFAULT NULL COMMENT '创建时间',
  `update_time` bigint(13) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*天气数据表*/
CREATE TABLE `t_weather_info` (
  `id` int(13) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `weather_coder_id` int(13) DEFAULT NULL COMMENT 't_weather_code 主键',
  `temp` int(3) DEFAULT NULL COMMENT '当前温度',
  `max_temp` int(3) DEFAULT NULL COMMENT '最高温度',
  `min_temp` int(3) DEFAULT NULL COMMENT '最小温度',
  `wd` varchar(10) DEFAULT NULL COMMENT '风向',
  `ws` varchar(10) DEFAULT NULL COMMENT '风速',
  `wse` varchar(20) DEFAULT NULL COMMENT '风力',
  `sd` varchar(10) DEFAULT NULL COMMENT '相对湿度',
  `aqi` int(12) DEFAULT NULL COMMENT 'aqi pm25',
  `aqi_pm25` int(12) DEFAULT NULL COMMENT 'aqi',
  `rain` decimal(10,2) DEFAULT NULL COMMENT '降水',
  `rain24h` decimal(10,2) DEFAULT NULL COMMENT '24小时降水',
  `weather` varchar(50) DEFAULT NULL COMMENT '天气',
  `watch_time` bigint(13) DEFAULT NULL COMMENT '监控时间',
  `create_time` bigint(13) DEFAULT NULL COMMENT '创建时间',
  `update_time` bigint(13) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;