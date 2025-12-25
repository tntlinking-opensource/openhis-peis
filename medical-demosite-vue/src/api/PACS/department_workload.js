//  PACS科室工作量统计  开发人：麦沃德科技暴雨
import request from '@/utils/request'


// 工作量统计--分页查询
export function getTotalList(query){
  return request({
    url:"/statistics/pacsQuery/page",
    method:"get",
    params:query
  })
}

// 折线图--分页查询
export function getecharts(query){
    return request({
      url:"/statistics/pacsQuery/getTableData",
      method:"get",
      params:query
    })
  }

