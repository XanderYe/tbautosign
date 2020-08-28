package cn.xanderye.tbautosign.mapper;

import cn.xanderye.tbautosign.entity.TBInfo;

import java.util.List;

/**
 * Created by Xander on 2018-11-09.
 */
public interface TBInfoMapper {

    TBInfo selectByTid(long tid);

    List<TBInfo> selectTBInfosByTbUserId(long tbUserId);

    List<TBInfo> selectTBInfosByTBidForSign(long tbid);

    void insertTBInfos(TBInfo tbInfo);

    void updateStatus(long tid);

    void resetStatus();

    List<TBInfo> selectAllTBInfosByStatus();

    void deleteTBInfo(long tid);

    void deleteTBInfosByTBid(long tbid);
}
