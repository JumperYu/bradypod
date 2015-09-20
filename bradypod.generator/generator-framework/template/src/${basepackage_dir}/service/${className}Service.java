<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${basepackage}.service;

import com.bradypod.common.service.BaseMybatiService;
import ${basepackage}.po.${className};

<#include "/java_imports.include">
public interface ${className}Service extends BaseMybatiService<${className}>  {

}