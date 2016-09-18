package com.bradypod.common.bi.util;

import com.bradypod.common.bi.model.BridgeDO;

/**
 * 桥梁建筑师
 * 
 * @author xiangmin.zxm
 *
 */
public class BridgeArchitect {

	/**
	 * 桥梁打点信息格式返回
	 * 
	 * @param kingdom
	 * @param phylum
	 * @param classfield
	 * @param family
	 * @param genus
	 * @param extra
	 * @return String
	 */
	public static String build(String kingdom, String phylum, String classfield, String family, String genus,
			String extra) {
		Long userId = 123L;			// 用户id
		String userNick = "userNick";	// 用户昵称
		String clientIp = "up";   // 客户端ip
		String userAgent = ""; // 环境信息
		BridgeDO bridgeDO = BridgeDO.build(userId, userNick, clientIp, userAgent);
		bridgeDO.setKingdom(kingdom); // 步骤
		bridgeDO.setPhylum(phylum);
		bridgeDO.setClassfield(classfield);
		bridgeDO.setFamily(family);
		bridgeDO.setGenus(genus);
		bridgeDO.setExtra("{}"); // 扩展字段
		return bridgeDO.toString();
	}

}