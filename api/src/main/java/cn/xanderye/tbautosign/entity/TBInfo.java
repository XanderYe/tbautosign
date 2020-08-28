package cn.xanderye.tbautosign.entity;

import lombok.Data;

@Data
public class TBInfo {

    private int tid;

    private String tiebaId;//贴吧ID

    private String title;

    private int curScore = 0;

    private int levelId = 1;

    private String levelName;

    private int status;

    private TBUser tbUser;

}
