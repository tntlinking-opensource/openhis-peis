  //财务产值报表 开发人：麦沃德矢北
  import request from '@/utils/request'
  
  export function getReportData(query){
    return request(
      {
        url:'/finance/financialReporting/page',
        method:'get',
        params:query
      }
    )
  }
  //保存
  export function saveReportData(data){
    return request(
      {
        url:'/finance/financialReporting',
        method:'put',
        data:data
      }
    )
  }
  //更新
  export function updateReportData(data){
    return request(
      {
        url:'/finance/financialReporting',
        method:'post',
        data:data
      }
    )
  }

  //获取上一次的数据
  export function lastData(query){
return request({
  url:'/finance/financialReporting/lastData',
  method:'get',
  params:query
})
  }