<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${basepackage}.mapper;

import com.bradypod.common.mapper.BaseMapper;
import com.bradypod.shop.item.center.po.${className};

<#include "/java_imports.include">
public interface ${className}Mapper extends BaseMapper<${className}>  {

}