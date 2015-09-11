package com.bradypod.shop.item.center.constants;

/**
 * 评论星级分级
 *
 * @author zengxm<github.com/JumperYu>
 *
 *         2015年9月11日 下午1:49:18
 */
public enum CommentConstants {
	/**
	 * 好评、中评、差评、全部评论
	 * 
	 */
	GOOD_COMMENT(4, 5), GENERAL_COMMENT(2, 3), BAD_COMMENT(1, 1), ALL_COMMENT(1, 5);

	private Integer minStar; /* 最小星 */
	private Integer maxStar; /* 最大星 */

	private CommentConstants(Integer minStar, Integer maxStar) {
		this.minStar = minStar;
		this.maxStar = maxStar;
	}

	public Integer getMinStar() {
		return minStar;
	}

	public void setMinStar(Integer minStar) {
		this.minStar = minStar;
	}

	public Integer getMaxStar() {
		return maxStar;
	}

	public void setMaxStar(Integer maxStar) {
		this.maxStar = maxStar;
	}

}
