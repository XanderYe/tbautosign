package cn.xanderye.tbautosign.entity;

import lombok.Data;

import java.util.List;

/**
 * Created by Xander on 2018-11-10.
 */
@Data
public class TBUser {
    private Long tid;

    private String BDUSS;

    private String baiduId;

    private String baiduName;

    private Long uid;

    List<TBInfo> tbInfos;
}
