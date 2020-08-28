package cn.xanderye.tbautosign.service.impl;

import cn.xanderye.tbautosign.entity.TBUser;
import cn.xanderye.tbautosign.enums.ErrorCode;
import cn.xanderye.tbautosign.mapper.TBInfoMapper;
import cn.xanderye.tbautosign.mapper.TBUserMapper;
import cn.xanderye.tbautosign.service.TBUserService;
import cn.xanderye.tbautosign.util.TBUtil;
import cn.xanderye.tbautosign.exception.BusinessException;
import cn.xanderye.tbautosign.VO.TBUserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xander on 2018-11-10.
 */
@Slf4j
@Service
public class TBUserServiceImpl implements TBUserService {
    @Autowired
    private TBUserMapper tbUserMapper;
    @Autowired
    private TBInfoMapper tbInfoMapper;

    @Override
    public void insertTBUser(long uid, String BDUSS) {
        TBUser tbUser = new TBUser();
        String username = TBUtil.getUserName(BDUSS);
        if (username == null)
            throw new BusinessException(ErrorCode.BDUSS_ERROR, "uid={},baiduName={},BDUSS={}", tbUser.getUid(), tbUser.getBaiduName(), tbUser.getBDUSS());
        tbUser.setBaiduName(username);
        tbUser.setBaiduId(TBUtil.getUserID(username));
        tbUser.setBDUSS(BDUSS);
        tbUser.setUid(uid);
        TBUser tmp = tbUserMapper.selectByBaiduId(tbUser.getBaiduId());
        if (tmp != null)
            throw new BusinessException(ErrorCode.BDUSS_EXIST, "uid={},baiduName={},BDUSS={}", tbUser.getUid(), tbUser.getBaiduName(), tbUser.getBDUSS());
        tbUserMapper.insertTBUser(tbUser);
    }

    @Override
    public List<TBUserVo> selectTBUsersByUid(long uid) {
        List<TBUser> tbUsers = tbUserMapper.selectTBUsersByUid(uid);
        List<TBUserVo> tbUserVos = new ArrayList<>();
        TBUserVo tbUserVo;
        for (TBUser tbUser : tbUsers) {
            tbUserVo = new TBUserVo();
            tbUserVo.setTid(tbUser.getTid());
            tbUserVo.setBaiduId(tbUser.getBaiduId());
            tbUserVo.setBaiduName(tbUser.getBaiduName());
            tbUserVo.setBDUSS(tbUser.getBDUSS());
            tbUserVos.add(tbUserVo);
        }
        return tbUserVos;
    }

    @Override
    public void deleteTBUser(long tid) {
        //先删除该帐号下所有贴吧信息
        tbInfoMapper.deleteTBInfosByTBid(tid);
        //再删除该帐号
        tbUserMapper.deleteByTid(tid);
    }

    @Override
    public void updateTBUser(TBUser tbUser) {
        tbUserMapper.updateTBUser(tbUser);
    }
}
