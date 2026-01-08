package com.ruoyi.version.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.version.domain.VersionReleaseEntity;
import com.ruoyi.version.mapper.VersionReleaseMapper;
import com.ruoyi.version.service.IVersionReleaseService;

/**
 * 发布预告 服务层实现
 *
 * @author ruoyi
 */
@Service
public class VersionReleaseServiceImpl
    extends ServiceImpl<VersionReleaseMapper, VersionReleaseEntity>
    implements IVersionReleaseService
{
}
