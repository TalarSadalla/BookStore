package pl.jstk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;

@RestController
public class BookController {

	@Autowired
	BookService bookservice;

	@RequestMapping(value = "/books", method = RequestMethod.GET)
	public ModelAndView showAllBooks() {
		ModelAndView modelAndView = new ModelAndView("books");
		modelAndView.addObject("bookList", bookservice.findAllBooks());
		return modelAndView;
	}

	@RequestMapping(value = "/books/book{id}", method = RequestMethod.GET)
	public ModelAndView showBookDetails(@RequestParam long id) {
		ModelAndView modelAndView = new ModelAndView("book");
		modelAndView.addObject("book", bookservice.findBookById(id));
		return modelAndView;
	}

	@RequestMapping(value = "/books/add", method = RequestMethod.GET)
	public ModelAndView addBook() {
		ModelAndView modelAndView = new ModelAndView("addBook");
		modelAndView.addObject("newBook", new BookTo());
		return modelAndView;
	}

	@RequestMapping(value = "/greeting", method = RequestMethod.POST)
	public ModelAndView addedBook(@ModelAttribute BookTo bookTo, ModelAndView modelAndView) {
		modelAndView = new ModelAndView("greeting");
		BookTo bookToSaveTO = new BookTo();
		bookToSaveTO = bookTo;
		bookservice.saveBook(bookToSaveTO);
		return modelAndView;
	}

	@RequestMapping(value = "/findBook", method = RequestMethod.GET)
	public ModelAndView findBook() {
		ModelAndView modelAndView = new ModelAndView("findBook");
		modelAndView.addObject("newBook", new BookTo());
		return modelAndView;
	}

	@RequestMapping(value = "/foundBooks", method = RequestMethod.POST)
	public ModelAndView foundBooks(@ModelAttribute BookTo bookTo, ModelAndView modelAndView) {
		modelAndView = new ModelAndView("foundBooks");
		BookTo bookToSaveTO = new BookTo();
		bookToSaveTO = bookTo;
		modelAndView.addObject("bookList", bookservice.findByFilteredParameters(bookToSaveTO));
		return modelAndView;
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/books/delete{id}", method = RequestMethod.GET)
	public ModelAndView deleteBook(@RequestParam long id) {
		ModelAndView modelAndView = new ModelAndView("delete");
		bookservice.deleteBook(id);
		modelAndView.setViewName("delete");
		return modelAndView;
	}

}
