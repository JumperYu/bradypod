package com.bradypod.search.lucene.bo;

/**
 * 商品索引
 *
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2015年12月28日 下午5:15:29
 */
public class ItemIndex extends BaseSearchIndex{

	private java.lang.Long id;		   // 商品标识
	private java.lang.Long ctgId;	   // 类目
	private java.lang.String title;	   // 标题
	private java.lang.Integer price;   // 价格
	private java.util.Date createTime; // 上架时间

	public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Long getCtgId() {
		return ctgId;
	}

	public void setCtgId(java.lang.Long ctgId) {
		this.ctgId = ctgId;
	}

	public java.lang.String getTitle() {
		return title;
	}

	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	public java.lang.Integer getPrice() {
		return price;
	}

	public void setPrice(java.lang.Integer price) {
		this.price = price;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	
	private static final long serialVersionUID = 1L;
}
