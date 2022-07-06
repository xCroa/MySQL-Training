package com.example.MySQLTraining;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PostRemove;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping(path = "/")
public class MainController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @PostMapping(path="/addInitialBooks")
    public @ResponseBody String addInitialBooks(){
        Book book = new Book();
        book.setName("As Crônicas de Nárnia");
        bookRepository.save(book);
        return "Initial books saved";
    }

    @PostMapping(path="/addBook")
    public @ResponseBody String addBook(@RequestBody Map<String, String> bookMap) throws ParseException {
        System.out.println("Book name:" + bookMap.get("name"));
        System.out.println("Author birth date:" + bookMap.get("birth_date"));
        System.out.println("Author's name:" + bookMap.get("author_name"));

        Author author = new Author();
        author.setName(bookMap.get("author_name"));
        author.setBirthDate(convertStringToDate(bookMap.get("birth_date")));

        Book book = new Book();
        book.setName(bookMap.get("name"));
        book.setAuthor(author);

        bookRepository.save(book);
        return "Test executed";
    }

    @GetMapping(path="list")
    public @ResponseBody Iterable<Book> listBooks(){
        return bookRepository.findAll();
    }

    @PostMapping(path="/addAuthor")
    public @ResponseBody String addAuthor(@RequestBody Author author){
        authorRepository.save(author);
        return "Author saved!";
    }


    private Date convertStringToDate(String stringToConvert) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date convertedString = dateFormat.parse(stringToConvert);
        return convertedString;
    }
}
