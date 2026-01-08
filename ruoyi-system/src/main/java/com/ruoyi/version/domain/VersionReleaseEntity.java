package com.ruoyi.version.domain;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 发布预告表 version_release
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("version_release")
public class VersionReleaseEntity extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 发布预告ID */
    @TableId(value = "release_id", type = IdType.AUTO)
    private Long releaseId;

    /** 发布产品 */
    private String releaseProduct;

    /** 发布应用 */
    private String releaseApplication;

    /** 发布内容 */
    private String releaseContent;

    /** 影响范围ID */
    private Long impactScopeId;

    /** 影响范围名称 */
    @TableField(exist = false)
    private String impactScopeName;

    /** 代码分支 */
    private String codeBranch;

    /** 发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date releaseTime;

    /** 发布版本 */
    private String releaseVersion;

    /** 发布服务 */
    private String releaseService;

    /** 发布人 */
    private String releaseUser;
}
