// 条码和导引单  开发人：麦沃德科技 清风
import request from '@/utils/request'

//调用导引单接口
export function getBillModelData(query){
  return request({
    url:"/reception/guideSheet/model",
    method:"POST",
    data:query
  })
}