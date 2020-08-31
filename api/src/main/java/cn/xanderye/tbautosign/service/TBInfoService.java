package cn.xanderye.tbautosign.service;

import cn.xanderye.tbautosign.entity.TBInfo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by Xander on 2018-11-11.
 */
public interface TBInfoService {
    /**
     * 修改签到状态为已签
     * @param tid
     * @return void
     * @author XanderYe
     * @date 2020/8/27
     */
    void changeStatus(long tid);

    /**
     * 重置签到状态
     * @param
     * @return void
     * @author XanderYe
     * @date 2020/8/27
     */
    void resetTBInfos();

    /**
     * 获得所有帐号的贴吧信息
     * @param
     * @return java.util.List<cn.xanderye.tbautosign.entity.TBInfo>
     * @author XanderYe
     * @date 2020/8/27
     */
    List<TBInfo> selectAllTBInfos();

    /**
     * 获得指定帐号的贴吧信息
     * @param tbUserId
     * @return java.util.List<cn.xanderye.tbautosign.entity.TBInfo>
     * @author XanderYe
     * @date 2020/8/27
     */
    List<TBInfo> selectTBInfosByTbUserId(long tbUserId);

    /**
     * 分页查询指定帐号的贴吧信息
     * @param tbUserId
     * @param page
     * @param rows
     * @return com.github.pagehelper.PageInfo<cn.xanderye.tbautosign.entity.TBInfo>
     * @author XanderYe
     * @date 2020/8/27
     */
    PageInfo<TBInfo> getTBInfoPageInfoByTbUserId(Long tbUserId, Integer page, Integer rows);

    /**
     * 获得指定帐号的贴吧信息与帐号信息
     * @param tbid
     * @return java.util.List<cn.xanderye.tbautosign.entity.TBInfo>
     * @author XanderYe
     * @date 2020/8/27
     */
    List<TBInfo> selectTBInfosByTBidForSign(long tbid);

    /**
     * 刷新百度账号的贴吧信息
     * @param tbid
     * @return void
     * @author XanderYe
     * @date 2020/8/27
     */
    void refreshTBInfos(long tbid);

    /**
     * 一键签到所有
     * @param
     * @return void
     * @author XanderYe
     * @date 2020/8/27
     */
    void sign();

    /**
     * 一键签到指定帐号
     * @param tbUserId
     * @param tid
     * @return void
     * @author XanderYe
     * @date 2020/8/27
     */
    void signByTbUserId(Long tbUserId, Long tid);

    /**
     * 删除指定贴吧信息
     * @param tid
     * @return void
     * @author XanderYe
     * @date 2020/8/27
     */
    void deleteTBInfo(long tid);

    /**
     * 删除指定帐号的所有贴吧信息
     * @param tbid
     * @return void
     * @author XanderYe
     * @date 2020/8/27
     */
    void deleteAllTBInfoByTBid(long tbid);
}
