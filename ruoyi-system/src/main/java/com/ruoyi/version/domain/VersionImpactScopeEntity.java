package com.ruoyi.version.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 影响范围表 version_impact_scope
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("version_impact_scope")
public class VersionImpactScopeEntity extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 影响范围ID */
    @TableId(value = "scope_id", type = IdType.AUTO)
    private Long scopeId;

    /** 影响范围名称 */
    private String scopeName;

    /** 影响范围描述 */
    private String scopeDescription;
}
