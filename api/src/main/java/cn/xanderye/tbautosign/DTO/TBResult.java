package cn.xanderye.tbautosign.DTO;

public enum TBResult {


    /***
     * {"error":"亲，你之前已经签过了","no":1101,"data":{"msg":"亲，你之前已经签过了"}}
     * {"error_code":"160002","error_msg":"\u4eb2\uff0c\u4f60\u4e4b\u524d\u5df2\u7ecf\u7b7e\u8fc7\u4e86","info":[],"time":1479196449,"ctime":0,"logid":3248889514,"server_time":"77606"}
     * {"error":"汗，操作未成功，麻烦再试一下下","no":11000,"data":{"msg":"汗，操作未成功，麻烦再试一下下"}}
     * {"error":"签到太频繁了点，休息片刻再来吧：）","no":1010,"data":{"msg":"签到太频繁了点，休息片刻再来吧：）"}}
     * no 0 error {"error":"3","no":0,"data":{"forum_sign_info_data":{"generate_time":0,"is_filter":false,"is_on":true,"sign_day_count":1,"member_count":103071,"sign_rank":7,"dir_rate":"0.1","sign_count":6407},"add_sign_data":{"is_block":0,"sign_version":2,"finfo":{"current_rank_info":{"sign_count":3831},"forum_info":{"forum_id":7665648,"forum_name":"百度成就"}},"errno":0,"uinfo":{"total_sign_num":860,"cont_sign_num":1,"hun_sign_num":81,"is_sign_in":1,"total_resign_num":0,"cout_total_sing_num":860,"user_id":172363399,"user_sign_rank":3831,"is_org_name":0,"sign_time":1469005189},"errmsg":"success"},"msg":"3"}}



     *
     */
    RED("亲，你之前已经签过了", 1101),
    BB("亲，你之前已经签过了", 160002),
    GREEN("汗，操作未成功，麻烦再试一下下", 11000),
    BLANK("贴吧目录出问题啦，请到贴吧签到吧反馈", 1010),
    ERROR("贴吧目录出问题啦，请到贴吧签到吧反馈", 340006),
    YELLO("签到成功", 0);
    private String name;
    private int index;

    // 构造方法
    private TBResult(String name, int index) {
        this.name = name;
        this.index = index;
    }

    // 普通方法
    public static String getName(int index) {
        for (TBResult c : TBResult.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    // get set 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
