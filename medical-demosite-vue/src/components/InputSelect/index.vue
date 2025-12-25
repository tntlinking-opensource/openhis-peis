<template>
  <el-select
    ref="select"
    v-model="bindValue"
    filterable
    remote
    :placeholder="selectData.placeholder || ' '"
    :remote-method="remoteMethod"
    :loading="loading"
    :clearable="!hiddenClear"
    :size="selectSize"
    :disabled="disabled"
    :queryParams="queryParams"
    @focus="focus"
    :style="{ width: selectData.selectWidth == null ? '230px' : selectData.selectWidth == '100%' ? '100%' : selectData.selectWidth + 'px' }"
    @change="change"
    @clear="optionData = []"
  >
    <div class="select-box">
      <div class="option-title">
        <span v-if="selectData.key">{{ selectData.key }}</span>
        <span :style="onlyValue ? ' width:100%;max-width:999px;' : ''">{{ selectData.value }}</span>
        <span v-if="selectData.third">{{ selectData.third }}</span>
        <span v-if="selectData.fourth">{{ selectData.fourth }}</span>
      </div>
      <div>
        <el-option v-for="item in optionData" :key="item.id" :label="tjlxType ? item.inputCode : item.name" :value="item.id">
          <div class="option-items" :style="{ width: onlyValue ? '100%;' : '', 'font-size': selectData.fontSize ? selectData.fontSize + 'px' : '' }">
            <span v-if="selectData.key">{{ item.inputCode }}</span>
            <el-tooltip popper-class="tooltip-item" effect="dark" :content="item.name" placement="left" v-if="showTooltip">
              <span :style="onlyValue ? 'width:100%;max-width:100%' : ''">{{ item.name }}</span>
            </el-tooltip>
            <span :style="onlyValue ? 'width:100%;max-width:100%' : ''" v-else>{{ item.name }}</span>
            <span v-if="selectData.third">{{ selectData.isSex ? ['男', '女', '通用'][item.third] : item.third }}</span>
            <span v-if="selectData.fourth">{{ item.fourth }}</span>
          </div>
        </el-option>
      </div>
    </div>
  </el-select>
</template>
<script>
// 传入数据模板
// selectData: {
//   placeholder:'请输入输入码选择',
//   key: '输入码',//第一列标题
//   value: '',//第二列标题
//   third: '',//第三列标题（没有传空）
//   fourth: '',//第四列标题（没有传空）
//   url:'',//请求连接
//   selectWidth: null,//选择器宽度（选填，默认230）不加px,传100%则为100%
//   bindValue:'', //初始值
//   optionData: [],// 不调接口时的展示数据
//   firstName:'',//接口返回值对应第一列的参数名(不传默认为'inputCode')
//   secondName:'',//接口返回值对应第二列的参数名(不传默认为'name')
//   thirdName:'',//接口返回值对应第三列的参数名
//   fourthName:'',//接口返回值对应第四列的参数名
//   queryData:'',//向接口传递的参数名(不传默认为'inputCode')
//   fontSize:'',//字体大小(不传默认为'14')
// },
// 已经全局引入

// 使用
// <input-select :selectData="selectData" @change="selectChange" :initialValue="绑定的初始值"></input-select>
// 可以通过 :selectWidth="num" 单独传递宽度
// 可以通过 :initialValue="value" 单独传递初始值
// 可以通过 :queryParams="value" 单独传递参数
// 可以通过 :hiddenClear="true" 单独传递能否清除
// 可以通过 selectSize="" 单独传递大小
// 可以通过 :onlyValue="true" 显示只有value一个
// 可以通过 :notShowEmpty="true" 为空时不显示
// 判断tjlxType值为真进行第二行name 的 类型转换
// 判断tjlxTypeThird值为真进行第三行的类型转换
// 可以通过 :isTrim="true" 判断搜索关键词是否去空格
// 是否显示Tooltip,:showTooltip="true"

// change方法返回选中的值
// selectChange(value) {
//   this.参数 = value
// },
import request from '@/utils/request'
export default {
  props: ['selectData', 'selectWidth', 'initialValue', 'selectSize', 'disabled', 'queryParams', 'hiddenClear', 'currentIndex', 'tjlxType', 'tjlxTypeThird', 'isTrim', 'onlyValue', 'isPost', 'notShowEmpty', 'showTooltip'],
  data() {
    return {
      bindValue: '', //绑定的值
      loading: false, //加载动画
      remoteLoading: false,
      optionData: [], //请求的返回数据
      notShowEmpty1: false,
      // 节流超时
      timer: null,
      // 搜索索引
      selIndex: 0,
    }
  },
  watch: {
    selectData: {
      handler(value) {
        this.bindValue = value.bindValue
        this.selectData.url = value.url
      },
      immediate: true,
      deep: true,
    },
    initialValue: {
      handler(value) {
        this.bindValue = value
      },
      immediate: true,
      deep: true,
    },
    notShowEmpty: {
      handler(value) {
        if (value) this.notShowEmpty1 = value
      },
      immediate: true,
      deep: true,
    },
  },
  created() {
    if (this.selectWidth) {
      this.selectData.selectWidth = this.selectWidth
    }
    if (this.selectData.bindValue) {
      this.bindValue = this.selectData.bindValue
    }
    if (this.initialValue) {
      this.bindValue = this.initialValue
    }
  },
  methods: {
    // 输入值发生变化时调用，请求远程数据
    remoteMethod(query) {
      if (this.isPost && !query) {
        return
      } else if (!query && this.notShowEmpty1) {
        return
      }
      if (this.isTrim && query) {
        query = query.trim()
      }
      // clearTimeout(this.timer)
      // this.timer = setTimeout(() => {
      this.loading = true
      if (this.selectData.url) {
        this.getData(query)
        return
      }
      // }, 500)
      if (this.selectData.optionData) {
        this.optionData = this.selectData.optionData
      }
    },
    // 获取数据
    getData(inputCode = ' ') {
      let params = {
        current: 1,
        size: 80,
      }
      if (this.selectData.queryData) {
        params[this.selectData.queryData] = inputCode
      } else {
        params.inputCode = inputCode
        params.key = inputCode
      }
      let queryParams = undefined
      if (this.queryParams) {
        queryParams = JSON.parse(JSON.stringify(this.queryParams))
      }
      if (queryParams) {
        if (params.ddh) {
          queryParams.ddh = params.ddh
        }
        params = Object.assign(params, queryParams)
      }
      let timeIndex = JSON.parse(JSON.stringify(this.selIndex))
      this.selIndex++
      let data = this.isPost ? { url: this.selectData.url, method: 'post', data: params } : { url: this.selectData.url, method: 'get', params }
      request(data)
        .then(({ data }) => {
          if (this.selIndex != ++timeIndex) {
            return
          }
          this.optionData = data.records || data || []
          this.optionData.forEach((el) => {
            if (!el.id) el.id = el[this.selectData.firstName]
          })
          if (this.selectData.firstName) {
            this.optionData.forEach((el) => {
              el.inputCode = el[this.selectData.firstName]
            })
          }
          if (this.selectData.secondName) {
            this.optionData.forEach((el) => {
              var context = ''
              //tjlxType判断
              if (this.tjlxType) {
                if (el[this.selectData.secondName] == 1) {
                  context = '职业'
                } else if (el[this.selectData.secondName] == 2) {
                  context = '综合'
                } else if (el[this.selectData.secondName] == 0) {
                  context = '健康'
                } else if (el[this.selectData.secondName] == 3) {
                  context = '复查'
                }
              }
              el.name = context || el[this.selectData.secondName]
            })
          }
          if (this.selectData.thirdName) {
            this.optionData.forEach((el) => {
              var context = ''
              //tjlxType判断
              if (this.tjlxTypeThird) {
                if (el[this.selectData.thirdName] == 1) {
                  context = '职业'
                } else if (el[this.selectData.thirdName] == 2) {
                  context = '综合'
                } else if (el[this.selectData.thirdName] == 0) {
                  context = '健康'
                } else if (el[this.selectData.thirdName] == 3) {
                  context = '复查'
                }
              }
              el.third = context || el[this.selectData.thirdName]
            })
          }
          if (this.selectData.fourthName) {
            this.optionData.forEach((el) => {
              el.fourth = el[this.selectData.fourthName]
            })
          }
          this.loading = false
        })
        .catch(() => {
          this.loading = false
        })
    },
    // 清空选项及选择框中的内容
    initData(value = undefined) {
      this.bindValue = value
      this.optionData = []
    },
    // 选择内容改变时
    change($event) {
      this.optionData.forEach((el) => {
        if (el.id == $event) {
          this.$emit('change', el, this.currentIndex)
          return
        }
      })
      if (!$event) {
        this.$emit('change', $event, this.currentIndex)
      }
    },
    focus() {
      // if (!this.bindValue) {
      // }
      this.remoteMethod()
      this.$emit('focus')
    },
    setFocus() {
      this.$refs.select.focus()
    },
    setBlur() {
      this.$refs.select.blur()
    },
  },
}
</script>

<style lang="scss">
.select-box {
  padding: 42px 8px 2px;
  background: #fff;

  .el-select-dropdown__item {
    padding: 0;
    height: 40px;
    border-bottom: 1px solid #dfe6ec;
  }

  .el-select-dropdown__item.hover {
    background-color: #ccc;
  }

  .option-title {
    width: calc(100% - 16px);
    word-break: break-word;
    color: #515a6e;
    height: 48px;
    padding-top: 8px;
    background: #f8f8f9;
    line-height: 40px;
    font-size: 13px;
    text-align: center;
    font-weight: bold;
    border-bottom: 1px solid #dfe6ec;
    position: absolute;
    z-index: 9;
    top: 0;
    display: flex;
    & span:nth-child(1) {
      max-width: 200px;
      text-overflow: ellipsis;
    }
    & span:nth-child(2) {
      max-width: 300px;
      text-overflow: ellipsis;
    }

    span {
      flex: 1;
      background-color: #f8f8f9;
      display: inline-block;
      width: 100%;
      min-width: 150px;
      padding: 0 10px;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
      word-break: break-all;
      height: 39px;
      line-height: 39px;
    }
  }

  .option-items {
    width: auto;
    word-break: break-word;
    height: 40px;
    line-height: 40px;
    font-size: 14px;
    color: #000;
    text-align: center;
    display: flex;
    & span:nth-child(1) {
      max-width: 200px;
      text-overflow: ellipsis;
    }
    & span:nth-child(2) {
      max-width: 300px;
      text-overflow: ellipsis;
    }
    & span:nth-child(3) {
      max-width: 300px;
      text-overflow: ellipsis;
    }
    & span:nth-child(4) {
      max-width: 300px;
      text-overflow: ellipsis;
    }

    span {
      flex: 1;
      display: inline-block;
      width: 100%;
      // max-width: 200px;
      min-width: 150px;
      padding: 0 10px;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
      word-break: break-all;
      height: 40px;
      line-height: 40px;
    }
    .tooltip-item {
      font-size: 20px !important;
      z-index: 99999;
    }
  }
}
</style>
