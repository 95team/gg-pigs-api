package com.gg_pigs.app.login.service;

import javax.servlet.http.HttpServletRequest;

public interface LoginExtendService extends LoginService {

    Login getLogin(HttpServletRequest request);
}
