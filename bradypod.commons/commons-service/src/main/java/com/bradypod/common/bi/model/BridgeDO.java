package com.bradypod.common.bi.model;

/**
 * �������ģ��
 * 
 * @author xiangmin.zxm
 *
 */
public class BridgeDO {

	private Long user_id; // �û�id

	private String user_nick; // �û��ǳ�

	private String client_ip; // �ͻ�ip

	private String source; // Դ

	private String kingdom; // ����һ

	private String phylum; // ������

	private String classfield; // ������

	private String family; // ������

	private String genus; // ������

	private String access_time; // ����ʱ��

	private String extra; // ��չ

	/**
	 * ����
	 * 
	 * @param user_id
	 *            - �û�id
	 * @param user_nick
	 *            - �û��ǳ�
	 * @param client_ip
	 *            - �ͻ���ip
	 * @param source
	 *            - Դͷ
	 * @return
	 */
	public static BridgeDO build(Long user_id, String user_nick, String client_ip, String source) {
		BridgeDO bridgeDO = new BridgeDO();
		bridgeDO.setUser_id(user_id);
		bridgeDO.setUser_nick(user_nick);
		bridgeDO.setClient_ip(client_ip);
		bridgeDO.setSource(source);
		return bridgeDO;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(user_id).append(SPLITTER).append(user_nick).append(SPLITTER).append(client_ip).append(SPLITTER)
				.append(source).append(SPLITTER).append(kingdom).append(SPLITTER).append(phylum).append(SPLITTER)
				.append(classfield).append(SPLITTER).append(family).append(SPLITTER).append(genus).append(SPLITTER)
				.append(extra);

		return sb.toString();
	}

	static final String SPLITTER = "|";

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getUser_nick() {
		return user_nick;
	}

	public void setUser_nick(String user_nick) {
		this.user_nick = user_nick;
	}

	public String getClient_ip() {
		return client_ip;
	}

	public void setClient_ip(String client_ip) {
		this.client_ip = client_ip;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getKingdom() {
		return kingdom;
	}

	public void setKingdom(String kingdom) {
		this.kingdom = kingdom;
	}

	public String getPhylum() {
		return phylum;
	}

	public void setPhylum(String phylum) {
		this.phylum = phylum;
	}

	public String getClassfield() {
		return classfield;
	}

	public void setClassfield(String classfield) {
		this.classfield = classfield;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public String getGenus() {
		return genus;
	}

	public void setGenus(String genus) {
		this.genus = genus;
	}

	public String getAccess_time() {
		return access_time;
	}

	public void setAccess_time(String access_time) {
		this.access_time = access_time;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

}
