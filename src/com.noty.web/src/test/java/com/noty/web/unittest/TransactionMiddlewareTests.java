package com.noty.web.unittest;

import com.noty.web.NotyException;
import com.noty.web.middleware.TransactionMiddleware;
import com.noty.web.services.TrackingResult;
import com.noty.web.services.TransactionTracker;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class TransactionMiddlewareTests {

    // @Test
    public void shouldSkipFoundRequest() throws ServletException, IOException, NotyException {
        // Arrange:
        TransactionTracker tracker = mock(TransactionTracker.class);
        when(tracker.trackTransaction("sr-1", "tr-1"))
                .thenReturn(TrackingResult.FOUND);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Transaction")).thenReturn("tr-1");
        when(request.getAttribute("serial")).thenReturn("sr-1");
        when(request.getMethod()).thenReturn("POST");

        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        TransactionMiddleware.TransactionFilter filter = new TransactionMiddleware.TransactionFilter(tracker);

        // Act:
        filter.doFilter(
                request,
                response,
                chain
        );

        // Assert:
        verify(tracker, times(1)).purgeTransactions();
        verify(tracker, times(1)).trackTransaction("sr-1", "tr-1");

        verify(response, times(1)).setHeader("Transaction", "tr-1");
        verify(response, times(1)).setStatus(304);

        verify(chain, times(0)).doFilter(any(), any());
    }

    // @Test
    public void shouldProcessNewRequest() throws ServletException, IOException, NotyException {
        // Arrange:
        TransactionTracker tracker = mock(TransactionTracker.class);
        when(tracker.trackTransaction("sr-2", "tr-2"))
                .thenReturn(TrackingResult.CREATED);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Transaction")).thenReturn("tr-2");
        when(request.getAttribute("serial")).thenReturn("sr-2");
        when(request.getMethod()).thenReturn("POST");

        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        TransactionMiddleware.TransactionFilter filter = new TransactionMiddleware.TransactionFilter(tracker);

        // Act:
        filter.doFilter(
                request,
                response,
                chain
        );

        // Assert:
        verify(tracker, times(1)).purgeTransactions();
        verify(tracker, times(1)).trackTransaction("sr-2", "tr-2");

        verify(response, times(1)).setHeader("Transaction", "tr-2");

        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    public void shouldSkipGetRequest() throws ServletException, IOException, NotyException {
        // Arrange:
        TransactionTracker tracker = mock(TransactionTracker.class);
        when(tracker.trackTransaction("sr-3", "tr-3"))
                .thenReturn(TrackingResult.CREATED);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Transaction")).thenReturn("tr-3");
        when(request.getAttribute("serial")).thenReturn("sr-3");
        when(request.getMethod()).thenReturn("GET");

        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        TransactionMiddleware.TransactionFilter filter = new TransactionMiddleware.TransactionFilter(tracker);

        // Act:
        filter.doFilter(
                request,
                response,
                chain
        );

        // Assert:
        verify(tracker, times(0)).purgeTransactions();
        verify(tracker, times(0)).trackTransaction(any(), any());

        verify(response, times(1)).setHeader("Transaction", "tr-3");

        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    public void shouldSkipNotAuthenticatedRequest() throws ServletException, IOException, NotyException {
        // Arrange:
        TransactionTracker tracker = mock(TransactionTracker.class);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Transaction")).thenReturn("tr-4");
        when(request.getMethod()).thenReturn("POST");

        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        TransactionMiddleware.TransactionFilter filter = new TransactionMiddleware.TransactionFilter(tracker);

        // Act:
        filter.doFilter(
                request,
                response,
                chain
        );

        // Assert:
        verify(tracker, times(0)).purgeTransactions();
        verify(tracker, times(0)).trackTransaction(any(), any());

        verify(response, times(1)).setHeader("Transaction", "tr-4");

        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    public void shouldSkipNonTransactionalRequest() throws ServletException, IOException, NotyException {
        // Arrange:
        TransactionTracker tracker = mock(TransactionTracker.class);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("serial")).thenReturn("sr-5");
        when(request.getMethod()).thenReturn("POST");

        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        TransactionMiddleware.TransactionFilter filter = new TransactionMiddleware.TransactionFilter(tracker);

        // Act:
        filter.doFilter(
                request,
                response,
                chain
        );

        // Assert:
        verify(tracker, times(0)).purgeTransactions();
        verify(tracker, times(0)).trackTransaction(any(), any());

        verify(response, times(0)).setHeader(any(), any());

        verify(chain, times(1)).doFilter(request, response);
    }

}
