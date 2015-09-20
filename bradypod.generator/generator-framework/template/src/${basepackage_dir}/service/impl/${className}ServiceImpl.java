<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${basepackage}.service.impl;

import com.bradypod.common.service.BaseMybatisServiceImpl;
import com.bradypod.shop.item.center.mapper.${className}Mapper;
import ${basepackage}.po.${className};
import ${basepackage}.service.${className}Service;

public class ${className}ServiceImpl extends BaseMybatisServiceImpl<${className}Mapper, ${className}> implements ${className}Service{

}