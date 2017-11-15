package com.acn.reading.service;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.acn.reading.constants.ReadingAppConstants;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class BookService {

	private RestTemplate restTemplate;

	public BookService(RestTemplate rest) {
		this.restTemplate = rest;
	}

	@HystrixCommand(fallbackMethod = "reliable")
	public String readingList() {
		URI uri = URI.create(ReadingAppConstants.RECOMMENDED_URI);

		return this.restTemplate.getForObject(uri, String.class);
	}

	public String reliable() {
		return ReadingAppConstants.DEFAULT_FALLBACK_READING_LIST;
	}

}
