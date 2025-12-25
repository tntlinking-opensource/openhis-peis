<template>
  <!-- 添加弹窗 -->
  <el-dialog title="推荐项目" :visible.sync="open" width="1780px" append-to-body style="overflow: hidden" :close-on-click-modal="false" class="registration-recommend">
    <!-- 筛选 -->
    <el-form ref="queryForm" size="small" :inline="true">
      <el-form-item label="收费项目输入码" prop="sfxmsrm">
        <el-input v-model="queryParams.sfxmsrm" placeholder="请输入收费项目输入码" clearable style="width: 230px" @keyup.enter.native="handleSearch()"></el-input>
      </el-form-item>
      <el-form-item label="价格从" prop="price_from">
        <el-input-number v-model="queryParams.price_from" controls-position="right" style="width: 120px" @keyup.enter.native="handleSearch()"></el-input-number>
      </el-form-item>
      <el-form-item label="到" prop="price_to">
        <el-input-number v-model="queryParams.price_to" controls-position="right" style="width: 120px" @keyup.enter.native="handleSearch()"></el-input-number>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" @click="handleSearch()">查询</el-button>
        <el-button type="warning" plain icon="el-icon-folder-add" size="mini" @click="handleQuestion">问卷</el-button>
      </el-form-item>
    </el-form>
    <div class="main-recommend">
      <!-- 左侧表格 -->
      <recommendLeft ref="recommendLeft" @selectionChangeTop="selectionChangeTop" @adds="adds" @selectionChangeBelow="selectionChangeBelow" @handleSearch="handleSearch"></recommendLeft>

      <!-- 中间移入移除 -->
      <div style="flex: 1" class="middle">
        <div class="center-btn" :style="{ '--theme': theme }">
          <el-button type="primary" plain size="mini" class="el-icon-arrow-right" :disabled="!topSelect.length" @click="adds(1)"></el-button>
          <el-button type="primary" plain size="mini" class="el-icon-arrow-left" style="margin: 24px 0 0 !important" @click="removes" :disabled="!rightSelect.length"></el-button>
        </div>
        <div class="center-btn" :style="{ '--theme': theme }">
          <el-button type="primary" plain size="mini" class="el-icon-arrow-right" :disabled="!belowSelect.length" @click="adds(2)"></el-button>
          <el-button type="primary" plain size="mini" class="el-icon-arrow-left" style="margin: 24px 0 0 !important" @click="removes" :disabled="!rightSelect.length"></el-button>
        </div>
      </div>

      <!-- 右侧表格 -->
      <div style="width: 56%; border: 1px solid #d4d6d9">
        <div class="grid-content bg-purple-light">
          <el-table ref="tableRight" :data="tempList" stripe size="mini" @selection-change="handleSelectionChangeRight" border max-height="600" @row-dblclick="removesDb" @row-click="rowClick">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="序列" align="center" width="100px" type="index" />
            <el-table-column label="收费项目名称" prop="sfxmmc" align="center" show-overflow-tooltip width="160px" />
            <el-table-column label="价格" prop="jg" align="center" width="80px" />
            <el-table-column label="检查意义" prop="jcyy" align="center" show-overflow-tooltip />
            <el-table-column label="科室" prop="ssks" align="center" show-overflow-tooltip width="120px" />
            <el-table-column label="查看图片" align="center" width="100px">
              <template slot-scope="scope">
                <div @click="checkPic(scope.row.id)" class="checkPic">查看图片</div>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>

    <!-- 问卷弹窗 -->
    <el-dialog title="问卷" :visible.sync="questionDialog" append-to-body width="850px">
      <!-- <div class="questionBox" v-for="item in questionList" :key="item.id">
        <div style="font-size: 18px; margin-bottom: 5px">{{ item.title }}</div>
        <el-checkbox-group v-model="item.answer" style="padding: 10px">
          <el-checkbox v-for="(val, index) in item.list" :key="index" border :label="val.label"
            style="font-size: 18px; margin-bottom: 5px; margin-left: 0">{{ val.label }} </el-checkbox>
        </el-checkbox-group>
      </div> -->

      <div class="questionBox" v-for="(item, index) in questionList" :key="index">
        <div v-show="item.multiSelect == 1" style="font-size: 18px; margin-bottom: 5px">
          {{ item.description }}
          <el-checkbox-group @change="changeData" v-model="item.data" style="padding: 10px">
            <el-checkbox v-for="(val, index) in item.items" :key="index" border :label="val" style="font-size: 18px; margin-bottom: 5px; margin-left: 0; min-width: 200px"> </el-checkbox>
          </el-checkbox-group>
        </div>
        <div v-show="item.multiSelect != 1" style="font-size: 18px; margin-bottom: 5px">
          {{ item.description }}
          <div>
            <el-radio-group @change="changeData" v-model="item.data">
              <el-radio v-for="(item, index) in item.items" :key="index" :label="item"></el-radio>
            </el-radio-group>
          </div>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitQuestion">提 交</el-button>
        <el-button type="primary" plain @click="resetQuestion">重 置</el-button>
        <el-button @click="cancelQuestion">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 查看图片弹窗 -->
    <el-dialog title="查看图片" :visible.sync="picDialog" append-to-body width="550px">
      <div class="">暂未查询到相关图片</div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="picDialog = false">确 定</el-button>
      </div>
    </el-dialog>
  </el-dialog>
</template>

<script>
import { questionData, saveQuestionData } from '@/api/reception/registration'
import recommendLeft from './recommend_left.vue'
export default {
  components: {
    recommendLeft,
  },
  data() {
    return {
      saveItem: {
        item: [],
      },
      // 打开主弹窗
      open: false,
      // 查询参数
      queryParams: {
        sfxmsrm: undefined,
        price_from: undefined,
        price_to: undefined,
      },
      //体检者id
      pId: undefined,
      // #region 问卷相关
      // 打开问卷
      questionDialog: false,
      // 问卷中的问题
      questionList: [
        {
          id: 1,
          title: '1、现在和过去如有以下疾病，请在对应的框内打钩：',
          list: [
            {
              label: '基本正常',
              value: 1,
            },
            {
              label: '高血压',
              value: 2,
            },
            {
              label: '糖尿病(或疑似)',
              value: 3,
            },
            {
              label: '高尿酸症(包括痛风)',
              value: 4,
            },
            {
              label: '肾,脏病(肾炎 肾结石等)',
              value: 5,
            },
            {
              label: '心脏病(心肌梗塞 心律不齐等)',
              value: 6,
            },
            {
              label: '呼吸系统疾病(喘息 肺结核等)',
              value: 7,
            },
            {
              label: '癌症',
              value: 8,
            },
            {
              label: '肝脏疾病(肝炎 脂肪肝等)',
              value: 9,
            },
          ],
          data: [],
        },
        {
          id: 2,
          title: '2、有血缘关系的家庭成员（父母 兄弟姐妹 祖父母）中，是否有人患有以下疾病，请在对应框中标记：',
          list: [
            {
              label: '基本正常',
              value: 1,
            },
            {
              label: '高血压',
              value: 2,
            },
            {
              label: '糖尿病(或疑似)',
              value: 3,
            },
            {
              label: '高尿酸症(包括痛风)',
              value: 4,
            },
            {
              label: '肾,脏病(肾炎 肾结石等)',
              value: 5,
            },
            {
              label: '心脏病(心肌梗塞 心律不齐等)',
              value: 6,
            },
            {
              label: '呼吸系统疾病(喘息 肺结核等)',
              value: 7,
            },
            {
              label: '癌症',
              value: 8,
            },
            {
              label: '肝脏疾病(肝炎 脂肪肝等)',
              value: 9,
            },
          ],
          data: [],
        },
      ],
      // #endregion

      // 左上表格选择数据
      topSelect: [],
      // 左下表格选择数据
      belowSelect: [],
      postData: {},
      // 右侧筛选数据
      tempList: [],
      // 右侧表格中选择的数据
      rightSelect: [],
      // 查看图片弹窗
      picDialog: false,
    }
  },
  computed: {
    theme() {
      return this.$store.state.settings.theme
    },
  },
  methods: {
    // 显示弹窗
    showDialog(data) {
      this.questionList = []
      this.open = true
      this.pId = data.pId
      this.tempList = []
      this.rightSelect = []
      this.topSelect = []
      this.belowSelect = []
      this.queryParams = {
        sfxmsrm: undefined,
        price_from: undefined,
        price_to: undefined,
      }
      this.$nextTick(() => {
        this.$refs.recommendLeft.reset()
      })
    },
    // 搜索
    handleSearch(current) {
      this.$refs.recommendLeft.handleQueryTop(this.queryParams, current)
    },
    // 提交表单
    submitForm() {
      this.open = false
      this.$emit('setRecommend', this.tempList)
    },
    // 取消按钮
    cancel() {
      this.open = false
    },

    // #region 问卷弹窗***********
    // 打开问卷弹窗
    handleQuestion() {
      this.questionDialog = true

      if (this.pId) {
        this.getQuestionData()
      } else {
        this.$modal.alertWarning('请先读卡，在执行此操作！')
        this.questionDialog = false
      }
    },
    //获取问卷数据
    getQuestionData() {
      var keyss = []
      const query = { id: this.pId }
      questionData(query).then((response) => {
        this.questionList = response.data
        //拼接标题
        for (var i in this.questionList) {
          this.questionList[i].description = this.questionList[i].questionId + '、' + this.questionList[i].description
          keyss.push(this.questionList[i].questionId)
        }

        //修改状态
        for (let i = 0; i < keyss.length; i++) {
          this.postData[keyss[i]] = ''
          if (this.questionList[i].multiSelect == 1) {
            this.questionList[i].data = []
            this.postData[keyss[i]] = []
          } else {
            this.postData[keyss[i]] = ''
            this.questionList[i].data = ''
          }
        }
      })
    },
    // 表单重置
    resetQuestion() {
      this.questionList.forEach((el) => {
        el.data = undefined
      })
    },
    // 关闭问卷弹窗
    cancelQuestion() {
      delete this.postData.id
      this.questionDialog = false
    },
    changeData() {
      this.$forceUpdate()
    },
    // 问卷提交按钮
    submitQuestion() {
      let i = 0

      for (let item in this.postData) {
        this.postData[item] = this.questionList[i].data
        i++
      }
      this.postData.id = this.pId
      saveQuestionData(this.postData).then((response) => {
        if (response.code == 200) {
          this.$modal.alertSuccess('已经保存成功！')
        }

        this.cancelQuestion()
      })
    },
    // #endregion

    // 左上表格选择回调
    selectionChangeTop(data) {
      this.topSelect = data
    },
    // 左下表格选择回调
    selectionChangeBelow(data) {
      this.belowSelect = data
    },

    // 移入选项 1收费，2套餐
    adds(type) {
      var rows = type == 1 ? this.topSelect : this.belowSelect
      if (rows.length == 0) {
        this.$modal.alertWarning('请选择要添加的收费项目！')
        return
      }
      var r = []
      for (var i = 0, l = rows.length; i < l; i++) {
        var row = rows[i]
        let oldIndex = this.tempList.findIndex((item) => {
          if (row.id == item.id) {
            return true
          }
        })
        if (oldIndex > -1) {
          continue
        }
        var new_row = {}
        for (var attr in row) {
          if (attr != '_id' && attr != '_uid') {
            new_row[attr] = row[attr]
          }
        }
        r.push(new_row)
      }
      this.tempList = [...this.tempList, ...r]
    },
    // 移出选项
    removes() {
      if (this.rightSelect.length == 0) {
        this.$modal.msg('请选择要删除的收费项目！')
        return
      }
      this.rightSelect.forEach((el) => {
        if (this.tempList.includes(el)) {
          this.tempList.splice(this.tempList.indexOf(el), 1)
        }
      })
    },
    // 双击移出
    // 移入选项-收费
    removesDb(row, col) {
      if (col && (col.label == '操作' || col.type == 'selection')) {
        return
      }
      var isSelect = false
      this.rightSelect.forEach((el) => {
        if (el.id == row.id) {
          isSelect = true
          this.removes()
        }
      })
      if (isSelect) return
      this.$refs.tableRight.clearSelection()
      this.$refs.tableRight.toggleRowSelection(row)
      this.rightSelect = [row]
      this.removes()
    },
    // 单击某行-套餐
    rowClick(row, col) {
      if (col && (col.label == '操作' || col.type == 'selection')) {
        return
      }
      var isSelect = false
      this.rightSelect.forEach((el) => {
        if (el.id == row.id) {
          isSelect = true
        }
      })
      if (isSelect) return
      this.$refs.tableRight.clearSelection()
      this.$refs.tableRight.toggleRowSelection(row)
      this.rightSelect = [row]
    },

    // 右侧表格选中的数据
    handleSelectionChangeRight(val) {
      this.rightSelect = val.map((item) => item)
    },
    // 查看图片
    checkPic(id) {
      this.picDialog = true
    },
  },
}
</script>

<style lang="scss">
.registration-recommend {
  .main-recommend {
    width: 100%;
    display: flex;
    justify-content: space-between;
  }

  .tableList {
    height: 450px;
  }

  .middle {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-around;
    min-height: 540px;

    .move {
      display: flex;
      align-items: center;
      padding: 7px 10px;
      margin-bottom: 24px;
      background: #e8f4ff;
      border-radius: 5px;
      font-size: 12px;
      line-height: 17px;
      color: #{'var(--theme)'} !important;

      &:hover {
        cursor: pointer;
      }

      img {
        margin-right: 5px;
      }
    }

    .el-pagination__total {
      display: none;
    }

    .center-btn {
      width: 82px;
      background: #fff;
      height: auto;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;

      .el-button {
        padding: 0;
        width: 40px;
        height: 40px;
        font-size: 20px;
        border-radius: 5px;
      }
    }

    // 问卷弹窗
    .questionBox {
      font-size: 18px;
      line-height: 20px;
      color: #333333;
    }
  }

  .checkPic {
    color: #0059ff;

    &:hover {
      cursor: pointer;
    }
  }
}
</style>
