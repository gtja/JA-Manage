import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi"

// 查询影响范围列表
export function listImpactScope(query) {
  return request({
    url: '/version/impact-scope/list',
    method: 'get',
    params: query
  })
}

// 查询影响范围详细
export function getImpactScope(scopeId) {
  return request({
    url: '/version/impact-scope/' + parseStrEmpty(scopeId),
    method: 'get'
  })
}

// 新增影响范围
export function addImpactScope(data) {
  return request({
    url: '/version/impact-scope',
    method: 'post',
    data: data
  })
}

// 修改影响范围
export function updateImpactScope(data) {
  return request({
    url: '/version/impact-scope',
    method: 'put',
    data: data
  })
}

// 删除影响范围
export function delImpactScope(scopeId) {
  return request({
    url: '/version/impact-scope/' + scopeId,
    method: 'delete'
  })
}
