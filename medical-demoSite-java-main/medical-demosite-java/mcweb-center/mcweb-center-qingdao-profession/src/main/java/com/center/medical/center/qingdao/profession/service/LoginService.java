package com.center.medical.center.qingdao.profession.service;

public interface LoginService {
    String getToken(String userCode, String password);

    String getToken();

    void refreshToken();

    String getJzToken();
}
