//职业结果告知书 开发人：麦沃德科技暴雨/予安
import request from '@/utils/request'

// 页面数据
export function getListData(query) {
  return request({
    url: '/report/printNotice/page',
    method: 'get',
    params: query
  })
}

// 生成报告
export function createReview(params) {
  return request({
    url: '/report/reviewNotice/createReview',
    method: 'GET',
    params
  })
}

// 查看报告内容
export function getData(params) {
  return request({
    url: '/report/reportContent/getData',
    method: 'GET',
    params
  })
}

// 修改报告内容
export function updateApi(data) {
  return request({
    url: '/report/reportContent/update',
    method: 'put',
    data
  })
}

// 获取个性配置
export function getDetailByNO(params) {
  return request({
    url: '/system/branch/getDetailByNO',
    method: 'GET',
    params
  })
}
