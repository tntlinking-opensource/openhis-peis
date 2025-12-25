<!-- 健康总检-查看详情-科室结果 麦沃德科技 开发人:清风 -->
<template>
  <div class="add-container">
    <el-dialog :title="title" class="add-items general-inspection-department-results" :visible.sync="open" width="95vw" append-to-body :close-on-click-modal="false">
      <el-tabs id="setTabs" v-model="activeName" @tab-click="handleClick" style="height: 760px">
        <el-tab-pane label="外送项目" name="first">
          <div style="height: 100%; display: flex; flex-direction: row">
            <div style="width: 665px">
              <div style="display: flex; flex-direction: column; height: 240px; margin-bottom: 16px">
                <el-table :data="tableData" v-loading="tableDataLoading" :border="true" :stripe="true" style="flex: 1" @selection-change="handleSelectionChangeHead" height="100%">
                  <el-table-column type="selection" align="center"></el-table-column>
                  <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
                  <el-table-column prop="itemName" label="收费项目" align="center"></el-table-column>
                  <el-table-column prop="ksName" label="科室名称" align="center"></el-table-column>
                  <el-table-column prop="status" label="状态" align="center">
                    <template slot-scope="scope">
                      <el-tag size="mini" type="success" v-if="scope.row.status == 1">已处理</el-tag>
                      <el-tag size="mini" type="danger" v-else>未处理</el-tag>
                    </template>
                  </el-table-column>
                </el-table>
              </div>
              <div style="height: 240px; padding: 10px; margin-bottom: 16px; border: 1px solid rgb(225, 225, 225)">
                <el-form :inline="true">
                  <el-form-item>
                    <el-button type="primary" icon="el-icon-plus" @click="receiveAdicon">获取</el-button>
                    <el-button type="success" icon="el-icon-edit-outline" @click="copyAndCancel">复制并关闭</el-button>
                  </el-form-item>
                </el-form>
              </div>
              <div style="display: flex; flex-direction: column; height: 253px; margin-bottom: 16px">
                <el-form :inline="true" style="height: 40px">
                  <el-form-item>
                    <template solt="label">
                      <div style="margin-bottom: 12px; font-size: 16px">项目列表</div>
                    </template>
                  </el-form-item>
                </el-form>
                <el-table :data="tableList" v-loading="tableListLoading" :border="true" :stripe="true" style="flex: 1" @selection-change="handleSelectionChange" height="100%">
                  <el-table-column type="selection" align="center"></el-table-column>
                  <el-table-column type="index" label="序列" width="55" align="center"></el-table-column>
                  <el-table-column prop="idFee" label="收费项目" align="center" show-overflow-tooltip></el-table-column>
                  <el-table-column prop="Check" label="检查" align="center"></el-table-column>
                  <el-table-column prop="resultHand" label="结果" align="center"></el-table-column>
                  <el-table-column prop="ts" label="提示" align="center"></el-table-column>
                  <el-table-column prop="ckdz" label="参考低值" align="center"></el-table-column>
                  <el-table-column prop="ckgz" label="参考高值" align="center"></el-table-column>
                </el-table>
              </div>
            </div>
            <div style="flex-shrink: 0; width: 155px; height: 764px; margin: 0 16px; border: 1px solid rgb(225, 225, 225); text-align: center;overflow: auto">
              <img :src="item" alt="" v-for="(item, index) in imgUrlList" :key="index" style="width: 90%; margin-top: 8px; cursor: pointer" :style="index == selectIndex ? 'border:1px solid #0059ff' : ''" @click="selectPic(item, index)" />
            </div>
            <div style="flex-grow: 1; width: 700px; height: 764px; border: 1px solid rgb(225, 225, 225); overflow: auto">
              <img :src="imgUrl" alt="" style="width: 120%" />
            </div>
          </div>
        </el-tab-pane>
        <el-tab-pane label="科室小结" name="second">
          <summaryItems ref="summaryItems"></summaryItems>
        </el-tab-pane>
        <el-tab-pane label="分科-普通" name="third">
          <generalItems ref="generalItems"></generalItems>
        </el-tab-pane>
        <el-tab-pane label="分科-检验" name="fourth">
          <divisionalItems ref="divisionalItems"></divisionalItems>
        </el-tab-pane>
        <el-tab-pane label="科室图片" name="fifth">
          <pictureItems ref="pictureItems"></pictureItems>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>
  </div>
</template>

<script>
import summaryItems from './department_summary.vue'
import generalItems from './department_general.vue'
import divisionalItems from './department_divisional.vue'
import pictureItems from './department_picture.vue'
import { getPictureData, getEditData } from '@/api/inspection/health_inspection.js'
import { getUploadPic, recordMatch } from '@/api/funcdept/delivery_manage/delivery_upload'

export default {
  components: { summaryItems, generalItems, divisionalItems, pictureItems },
  data() {
    return {
      // 选中数组
      ids: [],
      // 显示搜索条件
      showSearch: true,
      open: false,
      total: 30,
      queryParams: {
        pageNum: 1,
        pageSize: 30,
      },
      model: {
        patientcode: '',
        patientname: '',
        searchcode: '',
        valueDate: '', //startTime endTime
      },
      tableData: [
        {
          idCharge: '', //收费Id
          idFee: '', //收费项目
          idCheck: '', //检查Id
          Check: '', //检查
          resultHand: '', //结果
          ts: '', //提示
          ckdz: '', //参考低值
          ckgz: '', //参考高值
        },
      ],
      tableList: [],
      activeName: 'first',
      title: '科室结果',
      headSelection: [],
      headIds: [],
      tableDataLoading: false,
      tableListLoading: false,
      // 图片列表
      imgUrlList: [],
      imgUrl: '',
      selectIndex: -1,
    }
  },
  methods: {
    departmentResultsWindow(patientno) {
      this.open = true
      this.patientno = patientno
      this.activeName = 'first'
      this.getList()
    },
    getList() {
      this.tableDataLoading = true
      this.tableListLoading = true
      //获取与图片结果关联项目
      let obj = {
        patientcode: this.patientno,
      }
      getPictureData(obj)
        .then((res) => {
          if (res.code == 200) {
            this.tableData = res.data
            this.tableDataLoading = false
          }
        })
        .catch(() => {
          this.tableDataLoading = false
        })
      //获取已保存项目
      getEditData(obj)
        .then((res) => {
          if (res.code == 200) {
            this.tableList = res.data
            this.tableListLoading = false
          }
        })
        .catch(() => {
          this.tableListLoading = false
        })
      recordMatch({
        patientCode: this.patientno,
        skip: 1,
      }).then(({ data }) => {
        getUploadPic({
          id: data.retu[0].id,
        })
          .then(({ data }) => {
            if (data) {
              let imgUrlList = []
              data.forEach((el) => {
                imgUrlList.push(this.$getCookie('imgPath') + el)
              })
              this.imgUrlList = imgUrlList
              this.imgUrl = imgUrlList[0]
            }
          })
          .catch((error) => {
            console.error(error)
            this.loading = false
          })
      })
    },
    // 点击图片展示大图
    selectPic(item, index) {
      this.imgUrl = item
      this.selectIndex = index
    },
    //头部表单选中数据
    handleSelectionChangeHead(selection) {
      this.headIds = selection.map((el) => {
        return el.id
      })
      this.headSelection = selection
    },
    //项目列表表单获取数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => {
        return item.id
      })
    },
    handleClick(tab, event) {
      this.title = '科室小结'
      if (tab.name == 'first') {
        this.activeName == tab.name
        this.getList()
      } else if (tab.name == 'second') {
        this.activeName == tab.name
        this.$refs.summaryItems.gogetVerdict(this.patientno)
      } else if (tab.name == 'third') {
        this.activeName == tab.name
        this.$refs.generalItems.generalWindow(this.patientno)
      } else if (tab.name == 'fourth') {
        this.activeName == tab.name
        this.$refs.divisionalItems.divisionalWindow(this.patientno)
      } else if (tab.name == 'fifth') {
        this.activeName == tab.name
        this.$refs.pictureItems.getViewImgTotal(this.patientno)
      }
    },
    //获取
    receiveAdicon() {
      this.$alert('外送厂家接口,暂时不对接!!!', '提醒', {
        confirmButtonText: '确认',
        type: 'warning',
      })
      return
      let rows = this.headSelection
      if (rows.length == 0) {
        this.$alert('请选择要获取的项目！', '提醒', {
          confirmButtonText: '确认',
          type: 'warning',
        })
        return
      }
      let itemIds = []
      for (let i = 0; i < rows.length; i++) {
        let row = rows[i]
        itemIds.push(row.idCharge)
      }
      let patientcode = this.patientno
      //接口调用
    },
    //复制并关闭
    copyAndCancel() {
      this.$modal.msgSuccess('操作成功')
      this.open = false
    },
  },
}
</script>

<style scoped>
#setTabs /deep/ .el-tabs__item:focus.is-active.is-focus:not(:active) {
  box-shadow: none;
}
.general-inspection-department-results /deep/ table .cell {
  font-size: 16px !important;
  /* color: black !important; */
}
</style>
