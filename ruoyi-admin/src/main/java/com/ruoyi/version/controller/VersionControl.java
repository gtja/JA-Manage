package com.ruoyi.version.controller;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.version.domain.VersionReleaseEntity;
import com.ruoyi.version.service.IVersionReleaseService;

/**
 * 发布预告管理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/version/release")
public class VersionControl extends BaseController
{
    @Autowired
    private IVersionReleaseService versionReleaseService;

    /**
     * 分页查询发布预告列表
     */
    @PreAuthorize("@ss.hasPermi('version:release:list')")
    @GetMapping("/list")
    public TableDataInfo list(VersionReleaseEntity release)
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Page<VersionReleaseEntity> page = new Page<>(pageDomain.getPageNum(), pageDomain.getPageSize());
        QueryWrapper<VersionReleaseEntity> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(release.getReleaseProduct()))
        {
            queryWrapper.like("release_product", release.getReleaseProduct());
        }
        if (StringUtils.isNotEmpty(release.getReleaseApplication()))
        {
            queryWrapper.like("release_application", release.getReleaseApplication());
        }
        if (release.getImpactScopeId() != null)
        {
            queryWrapper.eq("impact_scope_id", release.getImpactScopeId());
        }
        if (StringUtils.isNotEmpty(release.getReleaseVersion()))
        {
            queryWrapper.like("release_version", release.getReleaseVersion());
        }
        if (StringUtils.isNotEmpty(release.getReleaseService()))
        {
            queryWrapper.like("release_service", release.getReleaseService());
        }
        if (StringUtils.isNotEmpty(release.getReleaseUser()))
        {
            queryWrapper.like("release_user", release.getReleaseUser());
        }
        if (StringUtils.isNotEmpty(release.getCreateBy()))
        {
            queryWrapper.like("create_by", release.getCreateBy());
        }
        Page<VersionReleaseEntity> result = versionReleaseService.page(page, queryWrapper);
        TableDataInfo data = new TableDataInfo(result.getRecords(), result.getTotal());
        data.setCode(HttpStatus.SUCCESS);
        data.setMsg("查询成功");
        return data;
    }

    /**
     * 获取发布预告详细信息
     */
    @PreAuthorize("@ss.hasPermi('version:release:query')")
    @GetMapping(value = "/{releaseId}")
    public AjaxResult getInfo(@PathVariable Long releaseId)
    {
        return success(versionReleaseService.getById(releaseId));
    }

    /**
     * 新增发布预告
     */
    @PreAuthorize("@ss.hasPermi('version:release:add')")
    @Log(title = "发布预告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody VersionReleaseEntity release)
    {
        release.setCreateBy(getUsername());
        return toAjax(versionReleaseService.save(release));
    }

    /**
     * 修改发布预告
     */
    @PreAuthorize("@ss.hasPermi('version:release:edit')")
    @Log(title = "发布预告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody VersionReleaseEntity release)
    {
        release.setUpdateBy(getUsername());
        return toAjax(versionReleaseService.updateById(release));
    }

    /**
     * 删除发布预告
     */
    @PreAuthorize("@ss.hasPermi('version:release:remove')")
    @Log(title = "发布预告", businessType = BusinessType.DELETE)
    @DeleteMapping("/{releaseIds}")
    public AjaxResult remove(@PathVariable Long[] releaseIds)
    {
        return toAjax(versionReleaseService.removeByIds(Arrays.asList(releaseIds)));
    }
}
