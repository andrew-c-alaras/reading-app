package com.acn.reading.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import com.acn.reading.constants.ReadingAppConstants;

@RunWith(SpringRunner.class)
@RestClientTest(BookService.class)
@AutoConfigureWebClient(registerRestTemplate = true)
public class BookServiceTest {

	@Autowired
	private BookService bookService;

	@Autowired
	private MockRestServiceServer server;

	@Test
	public void testReadingList_SuccessReturnsOriginalList() {
		String expectedResponse = "Spring in Action (Manning), Cloud Native Java (O'Reilly), Learning Spring Boot (Packt)";

		// Mock a success response.
		this.server.expect(requestTo(ReadingAppConstants.RECOMMENDED_URI))
				.andRespond(withSuccess(expectedResponse, MediaType.TEXT_PLAIN));

		String actualResponse = bookService.readingList();

		// The representation retrieved is the original list.
		assertThat(actualResponse).isEqualTo(expectedResponse);
	}

	@Test
	public void testReliable_ReturnsFallbackList() {
		String actualResponse = bookService.reliable();
		String expectedResponse = "Cloud Native Java (O'Reilly)";

		assertThat(actualResponse).isEqualTo(expectedResponse);
		assertThat(expectedResponse).isEqualTo(ReadingAppConstants.DEFAULT_FALLBACK_READING_LIST);
	}

}
