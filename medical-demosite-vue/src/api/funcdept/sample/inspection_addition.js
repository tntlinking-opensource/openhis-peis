// 检验加项  开发人：麦沃德科技 予安
import request from '@/utils/request'

// 分页查询
export function getListData(query) {
  return request({
    url: '/handle/handleNewProjects/page',
    method: 'get',
    params: query
  })
}

// 获取详情
export function getDetails(id) {
  return request({
    url: '/handle/handleNewProjects/' + id,
    method: 'get',
    params: {
      id,
    }
  })
}

// 处理、反处理
export function handleDeal(query) {
  return request({
    url: '/handle/handleNewProjects/saveOrUpdate',
    method: 'post',
    data: query
  })
}

// 批量处理、反处理
export function handleDeals(query) {
  return request({
    url: '/handle/handleNewProjects/saveBatch',
    method: 'put',
    params: query
  })
}