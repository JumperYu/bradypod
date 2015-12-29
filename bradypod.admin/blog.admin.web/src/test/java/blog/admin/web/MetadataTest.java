package blog.admin.web;

import java.io.IOException;
import java.lang.reflect.Method;

import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bradypod.admin.authority.retention.Menu;
import com.bradypod.admin.authority.retention.MenuResource;

public class MetadataTest {

	@Test
	public void testFilter() throws IOException {
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = resolver
				.getResources(PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
						+ "com/bradypod/blog/admin/**/controller/*.class");
		MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resolver);
		for (Resource r : resources) {
			MetadataReader reader = readerFactory.getMetadataReader(r);
			try {
				Class<?> clazz = Class.forName(reader.getClassMetadata().getClassName(), false,
						MetadataTest.class.getClassLoader());
				if (clazz.isAnnotationPresent(Menu.class)) {
					// 获取到顶级菜单
					Menu menu = clazz.getAnnotation(Menu.class);
					String name = menu.name();

					RequestMapping clazzRequestMapping = clazz.getAnnotation(RequestMapping.class);

					String clazzUrl = clazzRequestMapping != null ? clazzRequestMapping.value()[0] : "";

					System.out.println("菜单名: " + name);

					Method[] methods = clazz.getMethods();
					for (Method method : methods) {
						if (method.isAnnotationPresent(MenuResource.class)) {
							MenuResource res = method.getAnnotation(MenuResource.class);
							RequestMapping requestMapping = method
									.getAnnotation(RequestMapping.class);

							String resName = res.value();

							System.out.println("资源名: " + resName);

							String urlPath = requestMapping.value()[0] + clazzUrl;

							System.out.println("路径: "
									+ (urlPath.endsWith(".html") ? urlPath : (urlPath + ".html")));
						}
					}
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
