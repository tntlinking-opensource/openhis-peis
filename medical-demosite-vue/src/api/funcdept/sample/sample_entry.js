// 样本录入  开发人：麦沃德科技 予安
import request from '@/utils/request'

// 分页查询
export function getListData(query) {
  return request({
    url: '/handle/sampleDelivery/page',
    method: 'get',
    params: query
  })
}

// 搜索体检号
export function getTjzData(patientcode) {
  return request({
    url: '/handle/sampleDelivery/getTjzData',
    method: 'get',
    params: {
      patientcode,
    }
  })
}

// 搜索项目列表
export function getItemData(query) {
  return request({
    url: '/handle/sampleDelivery/getItemData',
    method: 'get',
    params: query
  })
}

// 录入保存
export function saveOrUpdate(query) {
  return request({
    url: '/handle/sampleDelivery/saveOrUpdate',
    method: 'post',
    data: query
  })
}

// 删除
export function remove(ids) {
  return request({
    url: '/handle/sampleDelivery/' + ids,
    method: 'delete',
    data: {
      ids
    }
  })
}
