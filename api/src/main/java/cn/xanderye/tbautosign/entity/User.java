package cn.xanderye.tbautosign.entity;

import lombok.Data;

import java.util.List;

/**
 * Created by Xander on 2018-11-05.
 */
@Data
public class User {

    private Long uid;

    private String username;

    private String password;

    private String token;

    private List<TBUser> tbUsers;

    private String avatar;
}