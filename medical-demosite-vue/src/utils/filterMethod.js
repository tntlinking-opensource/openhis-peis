// 用于element选择器筛选方法，模糊搜索中文及其对应拼音
import pinyin from '@/utils/pinyin.js'

/* <el-select v-model="queryParams.ks" clearable filterable :filter-method="filterMethod" style="width: 160px">
  <el-option v-for="item in ksOptions" :key="item.id" :value="item.id" :label="item.departName"></el-option>
</el-select> */
export function filterMethod(value, filterList, filterName) {
  let array = []
  if (value && value.trim()) {
    filterList.forEach((el) => {
      if (el[filterName].includes(value.trim())) {
        array.push(el)
      }
    })
    if (!array.length) {
      filterList.forEach((el) => {
        if (pinyin(el[filterName]).includes(value.trim().toUpperCase())) {
          array.push(el)
        }
      })
    }
  }else{
    array = filterList
  }
  return array.length ? array : (value ? [] : filterList)
}