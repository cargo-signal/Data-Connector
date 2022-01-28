package com.cargo_signal.bi.function;

import com.microsoft.azure.functions.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.*;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


/**
 * Unit test for Connector class.
 */
public class ConnectorTest {
    /**
     * Unit test for health method.
     */
    @Test
    public void testHealth() throws Exception {
        // Arrange
        @SuppressWarnings("unchecked") final HttpRequestMessage<Optional<String>> req = mock(HttpRequestMessage.class);

        final Optional<String> queryBody = Optional.empty();
        doReturn(queryBody).when(req).getBody();

        doAnswer(new Answer<HttpResponseMessage.Builder>() {
            @Override
            public HttpResponseMessage.Builder answer(InvocationOnMock invocation) {
                HttpStatus status = (HttpStatus) invocation.getArguments()[0];
                return new HttpResponseMessageMock.HttpResponseMessageBuilderMock().status(status);
            }
        }).when(req).createResponseBuilder(any(HttpStatus.class));

        final ExecutionContext context = mock(ExecutionContext.class);
        doReturn(Logger.getGlobal()).when(context).getLogger();

        // Act
        final HttpResponseMessage ret = new Connector().healthCheck(req, context);

        // Assert
        assertEquals(ret.getStatus(), HttpStatus.OK);
        assertEquals(ret.getBody(), "Ok");
    }

    /**
     * Unit test for shipments method.
     */
    @Test
    public void testShipments() throws Exception {
        // Arrange
        @SuppressWarnings("unchecked") final String MinimumDate = "2020-08-27";
        final HttpRequestMessage<Optional<String>> req = mock(HttpRequestMessage.class);

        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put("minDate", MinimumDate);
        doReturn(queryParams).when(req).getQueryParameters();

        final Optional<String> queryBody = Optional.empty();
        doReturn(queryBody).when(req).getBody();

        doAnswer(new Answer<HttpResponseMessage.Builder>() {
            @Override
            public HttpResponseMessage.Builder answer(InvocationOnMock invocation) {
                HttpStatus status = (HttpStatus) invocation.getArguments()[0];
                return new HttpResponseMessageMock.HttpResponseMessageBuilderMock().status(status);
            }
        }).when(req).createResponseBuilder(any(HttpStatus.class));

        final ExecutionContext context = mock(ExecutionContext.class);
        doReturn(Logger.getGlobal()).when(context).getLogger();

        // Act
        final HttpResponseMessage ret = new Connector().getShipments(req, context);

        // Assert
        assertEquals(HttpStatus.OK, ret.getStatus());
        assertTrue(ret.getBody().toString().startsWith("Uploaded shipment data to blob storage containers."));
    }

    /**
     * Unit test for shipments method.  Missing query string parameter.
     */
    @Test
    public void testShipmentsMissingParameter() throws Exception {
        // Arrange
        @SuppressWarnings("unchecked") final HttpRequestMessage<Optional<String>> req = mock(HttpRequestMessage.class);

        final Optional<String> queryBody = Optional.empty();
        doReturn(queryBody).when(req).getBody();

        doAnswer(new Answer<HttpResponseMessage.Builder>() {
            @Override
            public HttpResponseMessage.Builder answer(InvocationOnMock invocation) {
                HttpStatus status = (HttpStatus) invocation.getArguments()[0];
                return new HttpResponseMessageMock.HttpResponseMessageBuilderMock().status(status);
            }
        }).when(req).createResponseBuilder(any(HttpStatus.class));

        final ExecutionContext context = mock(ExecutionContext.class);
        doReturn(Logger.getGlobal()).when(context).getLogger();

        // Act
        final HttpResponseMessage ret = new Connector().getShipments(req, context);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, ret.getStatus());
    }
}
