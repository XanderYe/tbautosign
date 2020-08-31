package cn.xanderye.tbautosign.service;

import cn.xanderye.tbautosign.VO.TBUserVo;
import cn.xanderye.tbautosign.entity.TBUser;

import java.util.List;

/**
 * Created by Xander on 2018-11-10.
 */
public interface TBUserService {
    /**
     * 添加帐号信息
     * @param uid
     * @param BDUSS
     * @return void
     * @author XanderYe
     * @date 2020/8/27
     */
    void insertTBUser(long uid,String BDUSS);

    /**
     * 根据用户id获取帐号列表
     * @param uid
     * @return java.util.List<cn.xanderye.tbautosign.VO.TBUserVo>
     * @author XanderYe
     * @date 2020/8/27
     */
    List<TBUserVo> selectTBUsersByUid(long uid);

    /**
     * 根据tid删除帐号
     * @param tid
     * @return void
     * @author XanderYe
     * @date 2020/8/27
     */
    void deleteTBUser(long tid);

    /**
     * 更新BDUSS
     * @param tbUser
     * @return void
     * @author XanderYe
     * @date 2020/8/27
     */
    void updateTBUser(TBUser tbUser);
}
