package com.acn.reading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acn.reading.constants.ReadingAppConstants;
import com.acn.reading.service.BookService;

@RestController
public class ReadingAppController {

	@Autowired
	public BookService bookService;

	@RequestMapping(value = ReadingAppConstants.TO_READ)
	public String toRead() {
		return bookService.readingList();
	}

}
