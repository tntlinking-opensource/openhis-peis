// 预约来检情况分析  开发人:麦沃德科技 予安
import request from '@/utils/request'

// 分页查询（左侧）
export function getListData(params){
  return request({
    url:'/reservation/reservationAnalysis/page',
    method:"get",
    params
  })
}
// 今日情况人数
export function getToday(params){
  return request({
    url:'/reservation/reservationAnalysis/getToday',
    method:"get",
    params
  })
}
// 分页查询（右侧）
export function getRightPage(params){
  return request({
    url:'/reservation/reservationAnalysis/rightPage',
    method:"get",
    params
  })
}
