package com.yu.test.htmlunit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * web集成测试
 * 
 *
 * @author zengxm
 * @date 2015年8月4日
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:/config/spring/applicationContext**.xml")
@WebAppConfiguration(value = "schedule/src/main/webapp")
public class HTMLUnitTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	@Test
	public void testIndex() throws Exception {
		this.mockMvc.perform(
				get("/").accept(
						MediaType.parseMediaType("html/text;charset=UTF-8")))
				.andExpect(status().isOk());
	}

	@Test
	public void listAllTasks() throws Exception {
		this.mockMvc
				.perform(
						get("/task/listAllTasks.json")
								.accept(MediaType
										.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType("application/json;charset=UTF-8"));
		// .andExpect(jsonPath("$.name").value("Lee"));
	}

}
