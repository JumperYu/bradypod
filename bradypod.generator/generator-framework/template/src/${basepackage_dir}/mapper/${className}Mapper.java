<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${basepackage}.mapper;

import com.wupol.commons.domain.mapper.BaseMapper;
import com.wupol.uc.domain.model.${className};

<#include "/java_imports.include">
public interface ${className}Mapper extends BaseMapper<${className}>  {

}