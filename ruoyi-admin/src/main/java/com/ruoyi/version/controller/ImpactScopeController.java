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
import com.ruoyi.version.domain.VersionImpactScopeEntity;
import com.ruoyi.version.service.IVersionImpactScopeService;

/**
 * 影响范围管理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/version/impact-scope")
public class ImpactScopeController extends BaseController
{
    @Autowired
    private IVersionImpactScopeService impactScopeService;

    /**
     * 分页查询影响范围列表
     */
    @PreAuthorize("@ss.hasPermi('version:impact:list')")
    @GetMapping("/list")
    public TableDataInfo list(VersionImpactScopeEntity scope)
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Page<VersionImpactScopeEntity> page = new Page<>(pageDomain.getPageNum(), pageDomain.getPageSize());
        QueryWrapper<VersionImpactScopeEntity> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(scope.getScopeName()))
        {
            queryWrapper.like("scope_name", scope.getScopeName());
        }
        if (StringUtils.isNotEmpty(scope.getCreateBy()))
        {
            queryWrapper.like("create_by", scope.getCreateBy());
        }
        Page<VersionImpactScopeEntity> result = impactScopeService.page(page, queryWrapper);
        TableDataInfo data = new TableDataInfo(result.getRecords(), result.getTotal());
        data.setCode(HttpStatus.SUCCESS);
        data.setMsg("查询成功");
        return data;
    }

    /**
     * 获取影响范围详情
     */
    @PreAuthorize("@ss.hasPermi('version:impact:query')")
    @GetMapping(value = "/{scopeId}")
    public AjaxResult getInfo(@PathVariable Long scopeId)
    {
        return success(impactScopeService.getById(scopeId));
    }

    /**
     * 新增影响范围
     */
    @PreAuthorize("@ss.hasPermi('version:impact:add')")
    @Log(title = "影响范围", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody VersionImpactScopeEntity scope)
    {
        scope.setCreateBy(getUsername());
        return toAjax(impactScopeService.save(scope));
    }

    /**
     * 修改影响范围
     */
    @PreAuthorize("@ss.hasPermi('version:impact:edit')")
    @Log(title = "影响范围", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody VersionImpactScopeEntity scope)
    {
        scope.setUpdateBy(getUsername());
        return toAjax(impactScopeService.updateById(scope));
    }

    /**
     * 删除影响范围
     */
    @PreAuthorize("@ss.hasPermi('version:impact:remove')")
    @Log(title = "影响范围", businessType = BusinessType.DELETE)
    @DeleteMapping("/{scopeIds}")
    public AjaxResult remove(@PathVariable Long[] scopeIds)
    {
        return toAjax(impactScopeService.removeByIds(Arrays.asList(scopeIds)));
    }
}
