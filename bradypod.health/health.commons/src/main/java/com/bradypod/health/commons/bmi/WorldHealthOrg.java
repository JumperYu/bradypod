package com.bradypod.health.commons.bmi;

/**
 * 世界卫生组织计算BMI的公式
 * 
 * @author	zengxm<http://github.com/JumperYu>
 *
 * @date	2016年2月27日
 */
public class WorldHealthOrg extends BMICalculation {
	
	/**
	 * 男性：(身高cm－80)×70﹪=标准体重 女性：(身高cm－70)×60﹪=标准体重
	 * 标准体重正负10﹪为正常体重
	 * 标准体重正负10﹪~ 20﹪为体重过重或过轻
	 * 标准体重正负20﹪以上为肥胖或体重不足
	 * 超重计算公式
	 * 超重%=[（实际体重－理想体重）/（理想体重）]×100%
	 */
	@Override
	public double getBMI(double weight, double height) {
		return (height - 80) * 0.7;
	}
	
}
