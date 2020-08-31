package cn.xanderye.tbautosign.entity;

import lombok.Data;

@Data
public class TBInfo {

    private Long tid;

    private String tiebaId;//贴吧ID

    private String title;

    private Integer curScore = 0;

    private Integer levelId = 1;

    private String levelName;

    private Integer status;

    private TBUser tbUser;

}
