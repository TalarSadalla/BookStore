package pl.jstk.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import pl.jstk.enumerations.UserRole;
import pl.jstk.to.UserTo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LoginControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Autowired
	LoginController loginController;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(new LoginController()).build();
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();
	}

	@Test
	public void loginUserTest() throws Exception {
		// given when
		ResultActions resultActions = mockMvc.perform(get("/login"));
		// then
		resultActions.andExpect(status().isOk());
	}

	@Test
	public void loginAuthorizedUserTest() throws Exception {

		UserTo userTo = new UserTo((long) 1, "user", "user", true, UserRole.ROLE_USER);
		// given when
		ResultActions resultActions = mockMvc
				.perform(post("/login").contentType(MediaType.APPLICATION_JSON).flashAttr("user", userTo));
		// then
		resultActions.andExpect(status().isFound()).andExpect(view().name("welcome"));
	}

}
