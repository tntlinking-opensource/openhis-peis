import request from '@/utils/request'

// 分页查询列表
export function getListApi(params) {
  return request({
    url: '/sysVersionDeploy/page',
    method: 'get',
    params
  })
}

// 获取合作机构详情
export function ConfigDetails(id) {
  return request({
    url: '/sysVersionDeploy/' + id,
    method: 'get',
    params: {
      id
    }
  })
}