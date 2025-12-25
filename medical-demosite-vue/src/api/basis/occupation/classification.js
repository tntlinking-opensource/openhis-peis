// 职业病名称分类 开发人: 麦沃得科技 清风
import request from '@/utils/request'

//分页查询-职业病名称分类
export function listData(query){
    return request({
        url:'/occupationDiseastClass/getListData',
        method:'get',
        params:query,
    })
}

//跳转编辑页返回数据
export function editQueryData(id){
    return request({
        url:'/occupationDiseastClass/edit/' + id,
        method:'get',
    })
}

//删除
export function deleteData(ids){
    return request({
        url:'/occupationDiseastClass/remove/' + ids,
        method:'delete',
    })
}

//保存或更新
export function saveOrUpdateData(data){
    return request({
        url:'/occupationDiseastClass/saveOrUpdate',
        method:'post',
        data:data
    })
}

//详情
export function queryData(id){
    return request({
        url:'/occupationDiseastClass/' + id,
        method:'get',
    })
}
