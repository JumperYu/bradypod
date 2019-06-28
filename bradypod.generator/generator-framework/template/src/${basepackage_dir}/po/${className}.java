<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.po;

import lombok.Data;
import lombok.experimental.Accessors;

<#include "/java_imports.include">
@Data
@Accessors(chain = true)
public class ${className} implements java.io.Serializable{
	
	<#list table.columns as column>
	private ${column.javaType} ${column.columnNameLower}; // ${column.columnAlias}
	</#list>
}

