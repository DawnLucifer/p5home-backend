package com.dawnop.p5home.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessToken {

    private String username;
    private long expire;

    public AccessToken(String username) {
        this.username = username;
        this.expire = System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 3;
    }

}
