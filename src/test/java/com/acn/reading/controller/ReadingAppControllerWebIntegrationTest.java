package com.acn.reading.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.acn.reading.constants.ReadingAppConstants;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReadingAppControllerWebIntegrationTest {

	private MockRestServiceServer server;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private RestTemplate rest;

	@Before
	public void setup() {
		this.server = MockRestServiceServer.createServer(rest);
	}

	@After
	public void teardown() {
		this.server = null;
	}

	@Test
	public void testToRead_SuccessReturnsOriginalList() {
		String expectedResponse = "Spring in Action (Manning), Cloud Native Java (O'Reilly), Learning Spring Boot (Packt)";

		// Mock a success response.
		this.server.expect(requestTo(ReadingAppConstants.RECOMMENDED_URI)).andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess(expectedResponse, MediaType.TEXT_PLAIN));

		String actualResponse = testRestTemplate.getForObject(ReadingAppConstants.TO_READ, String.class);

		// The representation retrieved is the original list.
		assertThat(actualResponse).isEqualTo(expectedResponse);
	}

	@Test
	public void testToRead_FailureRedirectsToFallbackList() {
		// Mock a server error response.
		this.server.expect(requestTo(ReadingAppConstants.RECOMMENDED_URI)).andExpect(method(HttpMethod.GET))
				.andRespond(withServerError());

		String actualResponse = testRestTemplate.getForObject(ReadingAppConstants.TO_READ, String.class);

		// The representation retrieved is the fallback reading list.
		assertThat(actualResponse).isEqualTo(ReadingAppConstants.DEFAULT_FALLBACK_READING_LIST);
	}

}
