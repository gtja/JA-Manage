import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi"

// 查询发布预告列表
export function listRelease(query) {
  return request({
    url: '/version/release/list',
    method: 'get',
    params: query
  })
}

// 查询发布预告详细
export function getRelease(releaseId) {
  return request({
    url: '/version/release/' + parseStrEmpty(releaseId),
    method: 'get'
  })
}

// 新增发布预告
export function addRelease(data) {
  return request({
    url: '/version/release',
    method: 'post',
    data: data
  })
}

// 修改发布预告
export function updateRelease(data) {
  return request({
    url: '/version/release',
    method: 'put',
    data: data
  })
}

// 删除发布预告
export function delRelease(releaseId) {
  return request({
    url: '/version/release/' + releaseId,
    method: 'delete'
  })
}
