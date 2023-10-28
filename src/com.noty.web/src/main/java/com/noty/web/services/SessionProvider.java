package com.noty.web.services;

import com.noty.web.NotyAuthorizationException;
import com.noty.web.model.Credentials;
import com.noty.web.model.NotyUser;

public interface SessionProvider {

    String authenticate(Credentials credentials) throws NotyAuthorizationException;
}
