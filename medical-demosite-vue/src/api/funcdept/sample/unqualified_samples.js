// 不合格样本  开发人：麦沃德科技 暴雨
import request from '@/utils/request'
import id from 'element-ui/src/locale/lang/id'

// 分页查询
export function getListData(query) {
  return request({
    url: '/handle/visitMain/page',
    method: 'get',
    params: query
  })
}

// 弹窗中体检号回车搜索
export function getTjzData(query) {
  return request({
    url: '/addSatisficing/getTjzData',
    method: 'get',
    params: query
  })
}

// 保存
export function saveOrUpdate(query) {
  return request({
    url: '/handle/visitMain/saveOrUpdate',
    method: 'POST',
    data: query
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

// 删除
export function DeleteList(ids) {
  return request({
    url: '/handle/visitMain/' + ids,
    method: 'DELETE',
  })
}
