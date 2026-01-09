package com.ruoyi.version.service;

import com.ruoyi.version.domain.VersionReleaseEntity;

/**
 * @author yzwang
 * @date 2026-01-09 09:59
 * 钉钉群机器人服务
 */
public interface DingTalkService {
    /**
     * 发送发布通知
     * @param release
     * @return
     */
    Boolean sendReleaseNotice(VersionReleaseEntity release);
}
