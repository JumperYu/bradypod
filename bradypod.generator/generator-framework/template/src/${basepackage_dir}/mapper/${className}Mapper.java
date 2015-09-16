<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${basepackage}.mapper;

<#include "/java_imports.include">

import com.bradypod.common.mapper.BaseMapper;

public interface ${className}Mapper extends BaseMapper<${className}>  {

}