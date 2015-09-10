package mybatis;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * 
 * 测试二进制数据的存储
 *
 * @author zengxm
 * @date 2015年5月19日
 *
 */
public interface DataMapper {
	
	// 注解sql
	@Select("SELECT * FROM work_order_attachment WHERE id = #{id}")
	Data selectData(int id);
	
	@Insert("insert into work_order_attachment(data) values(#{data})")
	int insert(Data data);
}
