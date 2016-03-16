package com.bradypod.health.commons.weigth;

import org.junit.Test;

import com.bradypod.health.commons.bmi.BMICalculation;

/**
 * 体重测试
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年2月27日
 */
public class WeightTest {

	/**
	 * 国际标准，BIM（Body Mass Index） 体重公斤数 / 身高米数平方 = 人体胖瘦程度的指数
	 * 
	 */
	@Test
	public void testWeightAndHeight() {
		double bmi = BMICalculation
				.getInstance(BMICalculation.Organization.WORLD_HEALTH_ORG).getBMI(63.5, 175);
		System.out.println("bmi: " + bmi);
	}

}
