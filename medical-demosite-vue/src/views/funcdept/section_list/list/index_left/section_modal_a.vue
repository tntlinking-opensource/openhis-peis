<!-- 内科/外科/妇科/全科咨询检查/DR检查/CR检查/CT/心电图/经颅多普勒 开发人： 麦沃德科技 予安 -->
<template>
  <div class="app-container flex-direction-column internal-medicine" style="height: 100%; min-height: auto; padding: 0; overflow-y: auto">
    <!-- 填写列表 -->
    <div class="charge-item" v-for="(item, index) in leftData" :key="index">
      <h3 class="h3-title">{{ item.sfxmmc }}：{{ item.jcxmmc }}</h3>
      <div class="form-main-style">
        <div style="display: inline-block">
          <span class="top-style">是否弃检</span>
          <select class="select-style" v-model="item.qij" :disabled="mainDisabled">
            <option :value="0">否</option>
            <option :value="1">是</option>
          </select>
        </div>
        <div style="display: inline-block">
          <span class="top-style">危急值</span>
          <select class="select-style" v-model="item.wjzjb" :disabled="mainDisabled">
            <option value="0">无</option>
            <option value="1">低</option>
            <option value="2">中</option>
            <option value="3">高</option>
          </select>
        </div>
        <br />
        <div class="flex-style" v-if="item.fentryonly == 'shuru'">
          <span class="check-input top-style">{{ item.jcxmmc }}</span>
          <input class="input-style" v-model="item.inputResult" placeholder="请输入" :readonly="mainDisabled" @blur="inputResultChange(index)" />
        </div>
        <template v-else>
          <div class="gouxuan-main-style" style="display: flex; flex-wrap: wrap; width: 100%; margin-left: 20px">
            <label class="checkbox" :class="'checkbox' + index" v-for="(value, index3) in item.optionList" :key="index3" style="display: inline-block; width: 31%">
              <input
                type="checkbox"
                :id="index3"
                :name="item.jcxmmc"
                :value="value.sname"
                :data-mrxz="value.mrxz"
                :disabled="mainDisabled"
                :checked="item.checkList.includes(value.sname) || (item.checkList.length == 1 && item.checkList.includes(value.sname) && value.mrxz == 'morenxuanzhong')"
                @click="itemChange($event, item, index, value.sname)"
              />
              {{ value.sname }}
            </label>
            <div></div>
          </div>

          <!-- <div class="gouxuan-main-style">
            <div v-for="(optionList, index2) in item.optionLists" :key="index2" style="width: 31%; margin-right: 1%">
              <label class="checkbox" :class="'checkbox' + index" v-for="(value, index3) in optionList" :key="index3" style="display: inline-block; width: 100%">
                <input
                  type="checkbox"
                  :id="index3"
                  :name="item.jcxmmc"
                  :value="value.sname"
                  :data-mrxz="value.mrxz"
                  :disabled="mainDisabled"
                  :checked="item.tempCheckList.includes(value.sname) || (item.tempCheckList.length == 1 && item.tempCheckList.includes(value.sname) && value.mrxz == 'morenxuanzhong')"
                  @click="itemChange($event, item, index, value.sname)"
                />
                {{ value.sname }}
              </label>
            </div>
            <div></div>
          </div> -->
        </template>
        <div class="flex-style">
          <span class="top-style vertical">检查描述</span>
          <textarea class="textarea-style" :class="item.jcxmmc == '月经史及婚姻史' ? 'heighten' : ''" v-model="item.jcms" placeholder="请输入" :rows="item.jcxmmc == '月经史及婚姻史' ? '5' : '2'" :readonly="mainDisabled" @dblclick="handleCheck(item,index)"></textarea>
        </div>
      </div>
    </div>
    <el-dialog title="描述" :visible.sync="open" width="550px" append-to-body @close="updateItem">
      <textarea class="textarea-style" style="width: 100%; font-size: 20px" v-model="dialogJcms" placeholder="请输入" rows="12"></textarea>
    </el-dialog>
  </div>
</template>

<script>
export default {
  props: ['leftData', 'mainDisabled'],
  data() {
    return {
      // 打开弹窗
      open: false,
      // 描述弹窗内容
      jcms: '',
      dialogJcms: '',  // 绑定到弹窗文本框
      currentItemIndex: null // 用于存储当前正在编辑的 item 的索引
    }
  },
  methods: {
    // 双击打开检查描述弹窗
    // handleCheck(jcms) {
    //   this.jcms = jcms
    //   this.open = true
    // },
    handleCheck(item, index) {
    this.dialogJcms = item.jcms; // 设置弹窗中的文本框内容
    this.currentItemIndex = index; // 存储当前 item 的索引
    this.open = true;
  },
  updateItem() {
    if (this.currentItemIndex !== null) {
      this.leftData[this.currentItemIndex].jcms = this.dialogJcms; // 更新特定 item 的值
    }
    this.open = false;
  },
    // 疾病问题多选
    itemChange(e, item, index, sname) {
      let isChecked = e.target.checked
      // console.log(e);
      // console.log(e.target.dataset.mrxz);
      var checkboxes = Array.from(document.querySelectorAll(`.checkbox${index} input[type="checkbox"]`))
      let multiple = 0
      this.leftData[index].optionList.forEach((el) => {
        if (el.fentryonly == 'gouxuan' && el.mrxz == 'morenxuanzhong') {
          multiple++
        }
      })
      if (multiple == 1) {
        if (e.target.dataset.mrxz == 'morenxuanzhong' && isChecked) {
          checkboxes.forEach((checkbox) => {
            if (checkbox.dataset.mrxz == 'morenbuxuanzhong') {
              checkbox.checked = false
            }
          })
        } else {
          checkboxes.forEach(function (checkbox) {
            if (checkbox.dataset.mrxz == 'morenxuanzhong') {
              checkbox.checked = false
            }
          })
        }
      } else {
      }
      this.$emit('itemChange', item, index, sname, isChecked, multiple)
    },
    // 输入类型值改变
    inputResultChange(index) {
      this.$emit('inputResultChange', index)
    },
    
  },
}
</script>

<style lang="scss">
.internal-medicine {
  h3 {
    margin: 5px 0 10px;
    font-weight: 600;
    font-size: 16px;
    line-height: 22px;
    color: #333333;
  }

  .charge-item {
    // box-sizing: border-box;
    padding: 20px 26px;
    padding-bottom: 0;
    .h3-title {
      margin-top: 0;
      font-size: 18px;
      font-weight: 500;
    }

    .el-form-item--mini.el-form-item,
    .el-form-item--small.el-form-item {
      margin-bottom: 10px;
    }

    .el-checkbox-group:first-child {
      margin-left: 100px;
    }
    .textarea-style {
      font-size: 18px !important;
      font-weight: 400 !important;
    }
  }
}
</style>
<style scoped>
.internal-medicine .charge-item .checkbox {
  font-weight: 400;
  font-size: 20px;
  cursor: pointer;
}
.internal-medicine .charge-item .checkbox:hover {
  color: red;
}
.internal-medicine .charge-item /deep/ .el-checkbox__label {
  width: 200px;
  white-space: pre-wrap;
  font-size: 20px;
  color: #000;
}
.internal-medicine .charge-item /deep/ .el-textarea__inner {
  font-size: 20px;
}

.internal-medicine .charge-item /deep/ .el-checkbox {
  margin-right: 0;
}
.internal-medicine .charge-item /deep/ .is-checked {
  margin-right: 0;
}
/* .internal-medicine .charge-item .check-input /deep/ .el-form-item__content {
  width: 100%;
} */
.internal-medicine .charge-item /deep/ .el-form-item:nth-child(3) .el-form-item__content {
  width: 100%;
}
@-moz-document url-prefix() {
  .internal-medicine .charge-item .textarea-style {
    height: 50px;
  }
  .internal-medicine .charge-item .textarea-style.heighten {
    height: 150px;
  }
}
</style>
