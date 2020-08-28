package cn.xanderye.tbautosign.mapper;

import cn.xanderye.tbautosign.entity.TBUser;

import java.util.List;

/**
 * Created by Xander on 2018-11-10.
 */
public interface TBUserMapper {
    TBUser selectByTid(long tid);

    TBUser selectByTidforTBInfo(long tid);

    List<TBUser> selectTBUsersByUid(long uid);

    TBUser selectByBaiduId(String baiduId);

    void insertTBUser(TBUser tbUser);

    void deleteByTid(long tid);

    void updateTBUser(TBUser tbUser);
}
