  //统计管理 开发人：麦沃德矢北
  import request from '@/utils/request'
  
  export function getReport(query){
    return request(
      {
        url:'/finance/statisticalManage/getList',
        method:'get',
        params:query
      }
    )
  }