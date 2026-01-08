package com.ruoyi.version.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.version.domain.VersionImpactScopeEntity;
import com.ruoyi.version.mapper.VersionImpactScopeMapper;
import com.ruoyi.version.service.IVersionImpactScopeService;

/**
 * 影响范围 服务层实现
 *
 * @author ruoyi
 */
@Service
public class VersionImpactScopeServiceImpl
    extends ServiceImpl<VersionImpactScopeMapper, VersionImpactScopeEntity>
    implements IVersionImpactScopeService
{
}
