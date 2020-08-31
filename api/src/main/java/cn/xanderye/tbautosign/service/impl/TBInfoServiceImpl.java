package cn.xanderye.tbautosign.service.impl;

import cn.xanderye.tbautosign.service.TBInfoService;
import cn.xanderye.tbautosign.DTO.TBResult;
import cn.xanderye.tbautosign.entity.TBInfo;
import cn.xanderye.tbautosign.entity.TBUser;
import cn.xanderye.tbautosign.mapper.TBInfoMapper;
import cn.xanderye.tbautosign.mapper.TBUserMapper;
import cn.xanderye.tbautosign.util.TBUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Xander on 2018-11-11.
 */
@Slf4j
@Service
public class TBInfoServiceImpl implements TBInfoService {
    @Autowired
    private TBInfoMapper tbInfoMapper;
    @Autowired
    private TBUserMapper tbUserMapper;

    @Override
    public void changeStatus(long tid) {
        tbInfoMapper.updateStatus(tid);
    }

    @Override
    public void resetTBInfos() {
        tbInfoMapper.resetStatus();
    }

    @Override
    public List<TBInfo> selectAllTBInfos() {
        return tbInfoMapper.selectAllTBInfosByStatus();
    }

    @Override
    public List<TBInfo> selectTBInfosByTbUserId(long tbUsreId) {
        return tbInfoMapper.selectTBInfosByTbUserId(tbUsreId);
    }

    @Override
    public PageInfo<TBInfo> getTBInfoPageInfoByTbUserId(Long tbUsreId, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<TBInfo> list = tbInfoMapper.selectTBInfosByTbUserId(tbUsreId);
        return new PageInfo<>(list);
    }

    @Override
    public List<TBInfo> selectTBInfosByTBidForSign(long tbid) {
        return tbInfoMapper.selectTBInfosByTBidForSign(tbid);
    }

    @Override
    public void refreshTBInfos(long tbid) {
        TBUser tbUser=tbUserMapper.selectByTid(tbid);
        tbInfoMapper.deleteTBInfosByTBid(tbUser.getTid());
        String jsonStr = TBUtil.getTieba(tbUser.getBaiduId(), tbUser.getBDUSS());
        log.info("jsonStr={}",jsonStr);
        List<TBInfo> tbInfos=TBUtil.getTieba(jsonStr);
        tbInfos=tbInfos.stream().peek(tbInfo -> tbInfo.setTbUser(tbUser)).collect(Collectors.toList());
        for(TBInfo tbInfo:tbInfos){
            tbInfoMapper.insertTBInfos(tbInfo);
        }

    }

    @Override
    public void sign() {
        List<TBInfo> tbInfos=selectAllTBInfos();
        if(tbInfos!=null){
            for(TBInfo tbInfo:tbInfos){
                System.out.println(tbInfo.toString());
                String result=TBUtil.DoSign_Client(tbInfo.getTbUser().getBDUSS(),tbInfo.getTitle(),"1");
                log.info("result={}",result);
                if(result.equals(TBResult.YELLO.getName())||result.equals(TBResult.RED)||result.equals(TBResult.BB)){
                    changeStatus(tbInfo.getTid());
                }
            }
        }

    }

    @Override
    public void signByTbUserId(Long tbUserId, Long tid) {
        List<TBInfo> tbInfos=selectTBInfosByTBidForSign(tbUserId);
        if(tbInfos!=null){
            for(TBInfo tbInfo:tbInfos){
                if (tid == null || tid.equals(tbInfo.getTid())) {
                    String result=TBUtil.DoSign_Client(tbInfo.getTbUser().getBDUSS(),tbInfo.getTitle(),"1");
                    log.info("result={}",result);
                    if(result.equals(TBResult.YELLO.getName())||result.equals(TBResult.RED.getName())||result.equals(TBResult.BB.getName())){
                        changeStatus(tbInfo.getTid());
                    }
                }
            }
        }
    }

    @Override
    public void deleteTBInfo(long tid) {
        tbInfoMapper.deleteTBInfo(tid);
    }

    @Override
    public void deleteAllTBInfoByTBid(long tbid) {
        tbInfoMapper.deleteTBInfosByTBid(tbid);
    }
}
