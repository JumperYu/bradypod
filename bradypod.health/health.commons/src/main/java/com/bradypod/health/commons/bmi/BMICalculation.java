package com.bradypod.health.commons.bmi;

/**
 * BMI计算工具
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年2月27日
 */
public abstract class BMICalculation {

	public static enum Organization {
		WORLD_HEALTH_ORG, INTERNATIONAL_ORG
	}

	public static BMICalculation getInstance(Organization org) {
		switch (org) {
		case WORLD_HEALTH_ORG:
			return new WorldHealthOrg();
		default:
			return null;
		}
	}

	/**
	 * 体重和身高比
	 * 
	 * @param weight - 体重公斤
	 * @param height - 身高厘米
	 * @return - bmi 值
	 */
	public abstract double getBMI(double weight, double height);

}
