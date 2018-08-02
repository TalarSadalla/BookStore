package pl.jstk.controller;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import pl.jstk.enumerations.BookStatus;
import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BookControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Mock
	BookService bookservice;

	@Autowired
	BookController bookController;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(new BookController()).build();
		MockitoAnnotations.initMocks(bookservice);
		Mockito.reset(bookservice);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();
		ReflectionTestUtils.setField(bookController, "bookService", bookservice);

	}

	@Test
	public void ShowAllBooksTest() throws Exception {
		// given
		BookTo book1 = new BookTo((long) 1, "One", "Rowling", BookStatus.FREE);
		BookTo book2 = new BookTo((long) 2, "Two", "Tolkien", BookStatus.LOAN);
		when(bookservice.findAllBooks()).thenReturn(Arrays.asList(book1, book2));

		// when
		ResultActions resultActions = mockMvc.perform(get("/books"));
		resultActions.andExpect(status().isOk()).andExpect(view().name("books"))
				.andExpect(model().attribute("bookList", hasSize(2)))
				.andExpect(model().attribute("bookList",
						hasItem(allOf(hasProperty("id", is(1L)), hasProperty("title", is("One")),
								hasProperty("authors", is("Rowling"))))))
				.andExpect(model().attribute("bookList", hasItem(allOf(hasProperty("id", is(2L)),
						hasProperty("title", is("Two")), hasProperty("authors", is("Tolkien"))))));

		// then
		verify(bookservice, times(1)).findAllBooks();
		verifyNoMoreInteractions(bookservice);
	}

	@Test
	@WithMockUser(username = "user", roles = { "USER" })
	public void ShowBookDetailsTest() throws Exception {
		// given
		BookTo book1 = new BookTo((long) 1, "One", "Rowling", BookStatus.FREE);
		when(bookservice.findBookById(1)).thenReturn(book1);

		// when
		ResultActions resultActions = mockMvc.perform(get("/books/book?id=1"));
		resultActions.andExpect(status().isOk()).andExpect(view().name("book"))
				.andExpect(model().attribute("book", book1));

		// then
		verify(bookservice, times(1)).findBookById(1);
		verifyNoMoreInteractions(bookservice);
	}

	@Test
	@WithMockUser(username = "user", roles = { "USER" })
	public void ShowBookDetailsExceptionTest() throws Exception {
		// given
		BookTo book = null;
		when(bookservice.findBookById(5)).thenReturn(book);

		// when
		ResultActions resultActions = mockMvc.perform(get("/books/book?id=5"));
		resultActions.andExpect(status().isOk()).andExpect(view().name("bookNotFound"));

		// then
		verify(bookservice, times(1)).findBookById(5);
		verifyNoMoreInteractions(bookservice);
	}

	@Test
	public void ShowBookInvalidUserTest() throws Exception {
		// given
		BookTo book = null;
		when(bookservice.findBookById(5)).thenReturn(book);

		// when
		ResultActions resultActions = mockMvc.perform(get("/books/book?id=5"));
		resultActions.andExpect(status().isFound());

		// then
		verifyNoMoreInteractions(bookservice);
	}

	@Test
	@WithMockUser(username = "user", roles = { "USER" })
	public void addBookTest() throws Exception {
		// given
		BookTo book1 = new BookTo((long) 1, "One", "Rowling", BookStatus.FREE);
		BookTo book2 = new BookTo((long) 2, "Two", "Tolkien", BookStatus.LOAN);
		BookTo book3 = new BookTo((long) 3, "Three", "Johnny", BookStatus.MISSING);
		when(bookservice.findAllBooks()).thenReturn(Arrays.asList(book1, book2));

		// when
		ResultActions resultActions = mockMvc
				.perform(post("/greeting").contentType(MediaType.APPLICATION_JSON).flashAttr("newBook", book3))
				.andExpect(status().isOk()).andExpect(view().name("greeting"));

		// then
		verify(bookservice, times(1)).saveBook(book3);
		verifyNoMoreInteractions(bookservice);
	}

	@Test
	public void addBookIllegalUserTest() throws Exception {
		// given
		BookTo book1 = new BookTo((long) 1, "One", "Rowling", BookStatus.FREE);
		BookTo book2 = new BookTo((long) 2, "Two", "Tolkien", BookStatus.LOAN);
		BookTo book3 = new BookTo();
		when(bookservice.findAllBooks()).thenReturn(Arrays.asList(book1, book2));

		// when
		ResultActions resultActions = mockMvc
				.perform(get("/books/add").contentType(MediaType.APPLICATION_JSON).flashAttr("newBook", book3))
				.andExpect(status().isFound());

		// then
		verifyNoMoreInteractions(bookservice);
	}

	@Test
	@WithMockUser(username = "user", roles = { "USER" })
	public void addBookUserWithUserRoleTest() throws Exception {
		// given
		BookTo book1 = new BookTo((long) 1, "One", "Rowling", BookStatus.FREE);
		BookTo book2 = new BookTo((long) 2, "Two", "Tolkien", BookStatus.LOAN);
		BookTo book3 = new BookTo();
		when(bookservice.findAllBooks()).thenReturn(Arrays.asList(book1, book2));

		// when
		ResultActions resultActions = mockMvc
				.perform(get("/books/add").contentType(MediaType.APPLICATION_JSON).flashAttr("newBook", book3))
				.andExpect(status().isOk());

		// then
		verifyNoMoreInteractions(bookservice);
	}

	@Test
	public void findBookIllegalUserTest() throws Exception {
		// given
		BookTo book1 = new BookTo((long) 1, "One", "Rowling", BookStatus.FREE);
		BookTo book2 = new BookTo((long) 2, "Two", "Tolkien", BookStatus.LOAN);
		BookTo book3 = new BookTo();
		when(bookservice.findBookByTitleAndAuthor("One", "Rowling")).thenReturn(Arrays.asList(book1));

		// when
		ResultActions resultActions = mockMvc
				.perform(get("/findBook").contentType(MediaType.APPLICATION_JSON).flashAttr("newBook", book3))
				.andExpect(status().isFound());

		// then
		verifyNoMoreInteractions(bookservice);
	}

	@Test
	@WithMockUser(username = "user", roles = { "USER" })
	public void findBookTest() throws Exception {
		// given
		BookTo book1 = new BookTo((long) 1, "One", "Rowling", BookStatus.FREE);
		BookTo book2 = new BookTo((long) 2, "Two", "Tolkien", BookStatus.LOAN);
		BookTo book3 = new BookTo();
		when(bookservice.findByFilteredParameters("One", "Rowling")).thenReturn(Arrays.asList(book1));

		// when
		ResultActions resultActions = mockMvc
				.perform(get("/findBook").contentType(MediaType.APPLICATION_JSON).flashAttr("newBook", book3))
				.andExpect(status().isOk()).andExpect(view().name("findBook"));

		// then
		verifyNoMoreInteractions(bookservice);
	}

	@Test
	@WithMockUser(username = "user", roles = { "USER" })
	public void bookWasFoundTest() throws Exception {
		// given
		BookTo book1 = new BookTo((long) 1, "One", "Rowling", BookStatus.FREE);
		BookTo book2 = new BookTo((long) 2, "Two", "Tolkien", BookStatus.LOAN);
		BookTo book3 = new BookTo();

		List<BookTo> bookList = new ArrayList<>();
		bookList.add(book1);
		when(bookservice.findByFilteredParameters("One", "Rowling")).thenReturn(Arrays.asList(book1));

		// when
		ResultActions resultActions = mockMvc.perform(get("/foundBooks/?title=One&authors=Rowling"));
		resultActions.andExpect(status().isOk());

		// then
		verify(bookservice, times(1)).findByFilteredParameters("One", "Rowling");
		verifyNoMoreInteractions(bookservice);
	}

	@Test
	public void deleteBookInvalidUserTest() throws Exception {
		// given
		BookTo book1 = new BookTo((long) 1, "One", "Rowling", BookStatus.FREE);
		BookTo book2 = new BookTo((long) 2, "Two", "Tolkien", BookStatus.LOAN);
		BookTo book3 = new BookTo();
		book3.setTitle("One");
		book3.setAuthors("Rowling");

		// when
		ResultActions resultActions = mockMvc.perform(get("/books/delete?id=2"));
		resultActions.andExpect(status().isFound());

		// then
		verifyNoMoreInteractions(bookservice);
	}

	@Test
	@WithMockUser(username = "user", roles = { "USER" })
	public void deleteBookInvalidUserWithUserRoleTest() throws Exception {
		// given
		BookTo book1 = new BookTo((long) 1, "One", "Rowling", BookStatus.FREE);
		BookTo book2 = new BookTo((long) 2, "Two", "Tolkien", BookStatus.LOAN);
		BookTo book3 = new BookTo();
		book3.setTitle("One");
		book3.setAuthors("Rowling");

		// when
		ResultActions resultActions = mockMvc.perform(get("/books/delete?id=2"));
		resultActions.andExpect(status().isForbidden());

		verifyNoMoreInteractions(bookservice);
	}

	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	public void deleteBookUserWithAdminRoleTest() throws Exception {
		// given
		BookTo book1 = new BookTo((long) 1, "One", "Rowling", BookStatus.FREE);
		BookTo book2 = new BookTo((long) 2, "Two", "Tolkien", BookStatus.LOAN);
		when(bookservice.findBookById((long) 2)).thenReturn(book2);

		// when
		ResultActions resultActions = mockMvc.perform(get("/books/delete?id=2"));
		resultActions.andExpect(status().isOk()).andExpect(view().name("delete"));

	}

}
