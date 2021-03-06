package com.bradypod.admin.authority.retention;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单项
 *
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2015年12月29日 上午10:30:05
 */
public class MenuItem implements Serializable {

	private static final long serialVersionUID = -7570278414710364945L;

	private int id = 0;

	/** 菜单名 */
	private String name;

	/** 排列顺序 */
	private int sequence;

	/** 链接 */
	private String url = "";

	private boolean root;
	/**
	 * 子菜单
	 */
	private List<MenuItem> children;

	public MenuItem() {
	}

	public MenuItem(String name, String url) {
		this.name = name;
		this.url = url;
	}

	public MenuItem(String name, String url, boolean root) {
		this.name = name;
		this.url = url;
		this.root = root;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<MenuItem> getChildren() {
		return children;
	}

	public void setChildren(List<MenuItem> children) {
		this.children = children;
	}

	public List<MenuItem> createChildren() {
		if (this.children == null)
			this.children = new ArrayList<MenuItem>();
		return this.children;
	}

	public boolean isRoot() {
		return root;
	}

	public void setRoot(boolean root) {
		this.root = root;
	}

	@Override
	public String toString() {
		return "MenuItem [id=" + id + ", name=" + name + ", sequence=" + sequence + ", url=" + url
				+ ", root=" + root + ", children=" + children + "]";
	}

}
