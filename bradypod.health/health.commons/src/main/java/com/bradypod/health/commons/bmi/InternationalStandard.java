package com.bradypod.health.commons.bmi;

/**
 * 国际标准计算方式
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年2月27日
 */
public class InternationalStandard extends BMICalculation {

	/*
	 * 国际计算标准 体重公斤数 除以 身高米数平方
	 */
	@Override
	public double getBMI(double weight, double height) {
		return weight / (height * height);
	}

}
