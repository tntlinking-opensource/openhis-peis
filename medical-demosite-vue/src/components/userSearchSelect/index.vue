<template>
  <el-select
    value-key
    v-model="bindValue"
    :multiple="multiple"
    :placeholder="selectData.placeholder || '请选择...'"
    :size="selectSize"
    class="search-select"
    :popper-class="multiple ? 'search-popper multiple' : 'search-popper'"
    :style="{ width: inputWidth }"
    :clearable="!hideClear"
    @change="change"
    :loading="loading"
    :disabled="selectDisabled"
    @visible-change="visibleChange"
  >
    <div class="data-search">
      <span class="search-title" v-if="selectData.inputTitle">{{ selectData.inputTitle }}</span>
      <el-input class="search-input" v-model="inputData" clearable size="mini" :placeholder="selectData.inputPlaceholder || '请输入'" />
      <el-button type="primary" size="mini" @click="handleQuery()">搜索</el-button>
    </div>
    <div class="data-dictionary" :style="multiple ? 'padding-right: 60px' : ''">
      <div v-if="selectData.key" :style="!selectData.thirdName ? 'width:35%' : ''">{{ selectData.key }}</div>
      <div style="flex: 1; min-width: 100px">{{ selectData.value }}</div>
      <div v-if="selectData.thirdName" style="width: 100px">{{ selectData.thirdName }}</div>
      <div v-if="selectData.fourthName" style="width: 100px">{{ selectData.fourthName }}</div>
      <div class="select" v-if="multiple">选择</div>
    </div>
    <template>
      <el-option v-for="item in optionData" :key="item.accountNo" :label="item.accountName" :value="item.accountNo" :disabled="item.value == 'empty'">
        <div class="data-option">
          <div v-if="selectData.key" class="value" :style="!selectData.thirdName ? 'width:35%' : ''">{{ item.value }}</div>
          <div class="label">{{ item.label }}</div>
          <div v-if="selectData.thirdName" class="_label">{{ item.thirdData }}</div>
          <div v-if="selectData.fourthName" class="_label">{{ item.fourthData }}</div>
        </div>
      </el-option>
    </template>
    <!-- <template v-else-if="selectData.isKingDee == 'kingDee'">
      <el-option v-for="item in optionData" :key="item.id" :label="item.label" :value="item.label" :disabled="item.value == 'empty'">
        <div class="data-option">
          <div v-if="selectData.key" class="value" :style="!selectData.thirdName ? 'width:35%' : ''">{{ item.value }}</div>
          <div class="label">{{ item.label }}</div>
          <div v-if="selectData.thirdName" class="_label">
            <span v-if="item.thirdData == '1'">开启</span>
            <span v-else>禁用</span>
          </div>
          <div v-if="selectData.fourthName" class="_label">{{ item.fourthData }}</div>
        </div>
      </el-option>
    </template>
    <template v-else>
      <el-option v-for="item in optionData" :key="item.id" :label="item.label" :value="item.label" :disabled="item.value == 'empty'">
        <div class="data-option">
          <div v-if="selectData.key" class="value" :style="!selectData.thirdName ? 'width:35%' : ''">{{ item.value }}</div>
          <div class="label">{{ item.label }}</div>
          <div v-if="selectData.thirdName" class="_label">{{ item.thirdData }}</div>
          <div v-if="selectData.fourthName" class="_label">{{ item.fourthData }}</div>
        </div>
      </el-option>
    </template> -->
    <!-- 分页 -->
    <div class="data-pagination">
      <el-pagination small :total="total" background :page-size="20" layout="prev, pager, next" @current-change="currentChange"> </el-pagination>
    </div>
  </el-select>
</template>
<script>
// selectData: {
//   placeholder: '',
//   inputTitle: '',// 搜索标题
//   inputPlaceholder: '',// 搜索placeholder
//   key: '',//第一列标题
//   value: '',//第二列标题
//   url: '',//请求连接
//   bindValue:'', //初始值(必传)
//   firstName:'',//接口返回值对应第一列的参数名(不传默认为'inputCode')
//   secondName:'',//接口返回值对应第二列的参数名(不传默认为'name')
//   thirdName: '', // 三四列数据标题
//   fourthName: '',
//   thirdData: '',// 三四列接口返回值对应的参数名
//   fourthData: '',
//   params:{},//接口请求的其他参数
//   optionFlag:'', //传'id'的话则用id作为唯一依据，否则用传入的value中的值(label)
//   isKingDee:', //判断是否调用金蝶收费类型接口
// },
// 已经全局引入
// 使用
// <search-select :selectData="selectData" @change="selectChange"></search-select>
// 通过 :selectWidth=num 单独传递宽度
// 通过 :multiple=true 单独传递宽度
// 通过 :selectDisabled=true 单独传递是否禁用
// 通过 :initialValue=value 单独传递初始值
// 通过 :clickQuery=true 每次单击都触发搜索事件

// change方法返回选中的值
// selectChange(value) {
//   this.参数 = value
// },
import request from '@/utils/request'
export default {
  props: ['selectData', 'selectWidth', 'multiple', 'initialValue', 'selectSize', 'selectDisabled', 'currentIndex', 'hideClear', 'clickQuery'],
  data() {
    return {
      bindValue: '', //绑定的值
      loading: false, //加载动画
      inputData: '', //输入值
      total: 0, //数据总数
      optionData: [], //请求的返回数据
      inputWidth: 0, //搜索框宽度
      current: 1,
      selectList: [], //已选中数组
    }
  },
  watch: {
    selectWidth: {
      handler(val) {
        if (val) {
          if (isNaN(val)) {
            this.inputWidth = val
          } else {
            this.inputWidth = val + 'px'
          }
        } else {
          this.inputWidth = '230px'
        }
      },
      immediate: true,
      deep: true,
    },
    selectData: {
      handler(value) {
        if (this.multiple) {
          if (value.bindValue) {
            this.selectList = []
            this.bindValue = []
            value.bindValue.forEach((el) => {
              if (el.id || el.label) {
                this.bindValue.push(el.label)
                this.selectList.push({
                  id: el.id,
                  label: el.label,
                  value: el.label,
                })
              } else {
                this.bindValue.push(el)
              }
            })
          }
        } else {
          this.bindValue = value.bindValue
        }
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
  },
  created() {
    if (this.initialValue) {
      this.bindValue = this.initialValue
    }
    this.handleQuery()
  },
  methods: {
    // 输入值发生变化时调用，请求远程数据
    handleQuery(fn) {
      this.current = 1
      this.getData(fn)
    },
    getData(fn) {
      let params = {
        current: this.current,
        size: 20,
      }
      if (this.selectData.firstName) {
        params[this.selectData.firstName] = this.inputData
      } else {
        params.mz = this.inputData
      }
      params.key = this.inputData
      params.inputCode = this.inputData
      this.loading = true
      request({
        url: this.selectData.url,
        method: 'get',
        params: Object.assign(params, this.selectData.params),
      })
        .then(({ data }) => {
          this.optionData = data.records || data.data
          if (!this.optionData.length) {
            this.optionData = [
              {
                value: 'empty',
                label: '暂无数据',
              },
            ]
          } else {
            if (this.selectData.firstName) {
              this.optionData.forEach((el) => {
                el.value = el[this.selectData.firstName]
              })
            }
            if (this.selectData.secondName) {
              this.optionData.forEach((el) => {
                el.label = el[this.selectData.secondName]
              })
            }
            if (this.selectData.thirdName) {
              this.optionData.forEach((el) => {
                el['thirdData'] = el[this.selectData.thirdData]
              })
            }
            if (this.selectData.fourthName) {
              this.optionData.forEach((el) => {
                el['fourthData'] = el[this.selectData.fourthData]
              })
            }
            this.total = data.total
          }
          this.loading = false
          if (fn) fn(this.optionData)
        })
        .catch((error) => {
          console.error(error)
          this.loading = false
        })
    },
    currentChange($event) {
      this.loading = true
      this.current = $event
      this.getData()
    },
    // 手动设置显示值
    initData(data) {
      this.bindValue = data || undefined
      if (this.multiple) {
        if (data) {
          this.selectList = []
          this.bindValue = []
          data.forEach((el) => {
            if (el.id || el.label) {
              this.bindValue.push(el.label)
              this.selectList.push({
                id: el.id,
                label: el.label,
                value: el.label,
              })
            } else {
              this.bindValue.push(el)
            }
          })
        } else {
          this.bindValue = undefined
        }
      }
    },
    change($event) {
      console.log("$event的值",$event);

    if (this.multiple) {
      console.log(11111111);

      if ($event.length == 0) {
        this.selectList = [];
      } else {
        if (this.selectList.length > $event.length) {
          this.selectList.forEach((el, index) => {
            if ($event.indexOf(el.id) == -1) { // 根据 id 匹配
              this.$delete(this.selectList, index);
            }
          });
        } else {
          this.optionData.forEach((el) => {
            if ($event[$event.length - 1] == el.id) { // 根据 id 匹配
              this.selectList.push(el);
            }
          });
        }
      }
      this.$emit('change', this.selectList, this.currentIndex);
    } else {

      let selectedOption = null; // 用于存储用户选择的选项

      this.optionData.forEach((el) => {
        
        if (el.accountNo == $event) { // 根据 id 匹配
          console.log('根据 id 匹配');

          selectedOption = el;
        }
      });

      if (selectedOption) {
        this.$emit('change', selectedOption, this.currentIndex); // 触发事件时传递完整选项
      } else if (!$event) {
        this.$emit('change', $event, this.currentIndex); // 清空选择时触发
      }
    }
  },
    // 下拉框出现/隐藏-调用搜索功能
    visibleChange(value) {
      if (this.clickQuery && value) {
        this.handleQuery()
      }
    },
  },
}
</script>

<style lang="scss">
.search-select .el-select__tags {
  flex-wrap: nowrap;
  overflow: hidden;
}

.search-popper {
  .data-search {
    display: flex;
    align-items: center;
    padding: 6px 10px;
    position: sticky;
    z-index: 1;
    background: #fff;
    top: 0;

    .search-title {
      margin-right: 10px;
      font-size: 12px;
      color: #333333;
      white-space: nowrap;
    }

    .search-input {
      .el-input__suffix .el-input__validateIcon {
        display: none;
      }
    }

    .el-input {
      .el-input__inner {
        border-radius: 4px 0 0 4px;
        border-right: none;
        flex: 1;
      }
    }

    .el-button {
      border-radius: 0 4px 4px 0;
      // padding: 0;
      height: 28px;
    }
  }

  .data-dictionary {
    display: flex;
    padding: 8px 20px;
    background: #f6f7fb;
    font-size: 12px;
    line-height: 18px;
    position: sticky;
    z-index: 1;
    top: 40px;

    div {
      text-align: center;

      &:first-child {
        min-width: 65px;
      }

      &:last-child {
        width: 40px;
      }
    }

    .select {
      position: absolute;
      right: 20px;
    }
  }

  .data-option {
    display: flex;
    font-size: 12px;

    .value {
      width: 65px;
      color: #8492a6;
      text-align: center;
      overflow: hidden;
      /*超出部分隐藏*/
      text-overflow: ellipsis;
      /* 超出部分显示省略号 */
      white-space: nowrap;
      /*规定段落中的文本不进行换行 */
    }

    .label {
      flex: 1;
      min-width: 100px;
      color: #8492a6;
      text-align: center;
      overflow: hidden;
      /*超出部分隐藏*/
      text-overflow: ellipsis;
      /* 超出部分显示省略号 */
      white-space: nowrap;
      /*规定段落中的文本不进行换行 */
    }

    ._label {
      width: 100px;
      color: #8492a6;
      text-align: center;
      overflow: hidden;
      /*超出部分隐藏*/
      text-overflow: ellipsis;
      /* 超出部分显示省略号 */
      white-space: nowrap;
      /*规定段落中的文本不进行换行 */
    }
  }

  &.multiple .el-select-dropdown__item {
    position: relative;
    padding-right: 60px !important;

    &::after {
      top: 50%;
      transform: translateY(-50%);
      width: 40px;
      text-align: center;
    }
  }

  .el-select-dropdown__wrap.el-scrollbar__wrap {
    // overflow: hidden;
    max-height: 360px;
    padding: 0;
  }

  .el-scrollbar__view {
    padding: 0;

    .el-select-dropdown__list {
      overflow: hidden;
    }
  }

  .el-pager {
    height: 22px;
  }

  .data-pagination {
    position: sticky;
    z-index: 1;
    bottom: 0;
    background: #fff;
    padding: 4px;

    .el-pagination__editor.el-input {
      width: 40px;

      .el-input__inner {
        padding: 0;
        font-size: 12px;
      }
    }

    .el-input--mini .el-input__icon {
      line-height: 22px;
    }

    .el-pagination__jump {
      font-size: 12px !important;
      margin-left: 12px !important;
    }
  }
}
</style>
