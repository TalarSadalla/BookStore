package pl.jstk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;

@Controller
public class BookController {

	@Autowired
	BookService bookService;

	@RequestMapping(value = "/books", method = RequestMethod.GET)
	public ModelAndView showAllBooks() {
		ModelAndView modelAndView = new ModelAndView("books");
		modelAndView.addObject("bookList", bookService.findAllBooks());
		return modelAndView;
	}

	@RequestMapping(value = "/books/book{id}", method = RequestMethod.GET)
	public ModelAndView showBookDetails(@RequestParam long id) {
		ModelAndView modelAndView = new ModelAndView("book");
		BookTo foundBook = bookService.findBookById(id);
		if (foundBook == null) {
			modelAndView.setViewName("bookNotFound");
			return modelAndView;
		}
		modelAndView.addObject("book", foundBook);
		return modelAndView;
	}

	@RequestMapping(value = "/books/add", method = RequestMethod.GET)
	public ModelAndView addBook() {
		ModelAndView modelAndView = new ModelAndView("addBook");
		modelAndView.addObject("newBook", new BookTo());
		return modelAndView;
	}

	@RequestMapping(value = "/greeting", method = RequestMethod.POST)
	public String addedBook(@ModelAttribute("newBook") BookTo bookTo, Model model) {
		model.addAttribute(bookTo);
		bookService.saveBook(bookTo);
		return "greeting";
	}

	@RequestMapping(value = "/findBook", method = RequestMethod.GET)
	public ModelAndView findBook() {
		ModelAndView modelAndView = new ModelAndView("findBook");
		modelAndView.addObject("newBook", new BookTo());
		return modelAndView;
	}

	@RequestMapping(value = "/foundBooks/", method = RequestMethod.GET)
	public ModelAndView foundBooks(@RequestParam("title") String title, @RequestParam("authors") String authors) {
		ModelAndView modelAndView = new ModelAndView("foundBooks");
		modelAndView.addObject("bookList", bookService.findByFilteredParameters(title, authors));
		return modelAndView;
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/books/delete{id}", method = RequestMethod.GET)
	public ModelAndView deleteBook(@RequestParam long id) {
		ModelAndView modelAndView = new ModelAndView("delete");
		BookTo foundBook = bookService.findBookById(id);
		if (foundBook == null) {
			modelAndView.setViewName("bookNotFound");
			return modelAndView;
		}
		bookService.deleteBook(id);
		modelAndView.setViewName("delete");
		return modelAndView;
	}

}
