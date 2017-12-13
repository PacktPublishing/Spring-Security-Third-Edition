package com.packtpub.springsecurity.web.controllers;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.cas.authentication.CasAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

/**
 * Demonstrates how to use a Proxy Ticket to call a service. This client will call the Calendar applications My Events
 * page and echo the JSON response back.
 *
 * <p>
 * Note that this controller will not work until the entire Proxy Ticket authentication section has been completed.
 * </p>
 *
 * @author Rob Winch
 *
 */
@Controller
public class EchoController {

    private RestOperations restClient = new RestTemplate();
    private String targetUrl;

    @ResponseBody
    @RequestMapping("/echo")
    public String echo() throws UnsupportedEncodingException {
        final CasAuthenticationToken token = (CasAuthenticationToken) SecurityContextHolder
                .getContext()
                .getAuthentication();
        // The proxyTicket could be cached in session and reused if we wanted to
        final String proxyTicket = token.getAssertion().getPrincipal().getProxyTicketFor(targetUrl);

        // Make a remote call using the proxy ticket
        return restClient.getForObject(targetUrl+"?ticket={pt}", String.class, proxyTicket);
    }

    @Value("#{environment['cas.service']}")
    public void setTargetHost(String targetHost) {
        this.targetUrl = "https://"+targetHost+"/events/my";
    }
}
