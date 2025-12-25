// 检验科  开发人：麦沃德科技 予安
import request from '@/utils/request'
import request2 from '@/utils/request2'

// 检验科读卡查询用户信息
export function getUserInfo(query) {
  return request({
    url: '/abteilung/divisionInspection/search',
    method: 'get',
    params: query
  })
}

// 检验科读卡查询用户检验项目
export function getGridData(query) {
  return request({
    url: '/abteilung/divisionInspection/getgriddata',
    method: 'get',
    params: query
  })
}

// 检验科保存结伦词
export function saveclJlc(query) {
  return request({
    url: '/abteilung/divisionInspection/saveJlc',
    method: 'post',
    data: query
  })
}

// 获取第三方lis结果
export function receiveApi(data) {
  return request2({
    url: '/lis/lisInterface/receive',
    method: 'post',
    data
  })
}