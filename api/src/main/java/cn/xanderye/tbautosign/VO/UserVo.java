package cn.xanderye.tbautosign.VO;

import lombok.Data;

/**
 * Created by Xander on 2018-11-05.
 */
@Data
public class UserVo {
    private long uid;

    private String username;

    private String nickname;

    private String token;

    private String avatar;
}