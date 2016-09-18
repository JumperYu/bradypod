package com.bradypod.common.bi.util;

import com.bradypod.common.bi.model.BridgeDO;

/**
 * ��������ʦ
 * 
 * @author xiangmin.zxm
 *
 */
public class BridgeArchitect {

	/**
	 * ���������Ϣ��ʽ����
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
		Long userId = 123L;			// �û�id
		String userNick = "userNick";	// �û��ǳ�
		String clientIp = "up";   // �ͻ���ip
		String userAgent = ""; // ������Ϣ
		BridgeDO bridgeDO = BridgeDO.build(userId, userNick, clientIp, userAgent);
		bridgeDO.setKingdom(kingdom); // ����
		bridgeDO.setPhylum(phylum);
		bridgeDO.setClassfield(classfield);
		bridgeDO.setFamily(family);
		bridgeDO.setGenus(genus);
		bridgeDO.setExtra("{}"); // ��չ�ֶ�
		return bridgeDO.toString();
	}

}