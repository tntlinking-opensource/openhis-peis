<!-- 职业总检-查看详情-分科肺功能 麦沃德科技 开发人:清风 -->
<template>
  <div class="add-container" style="display: flex;">
    <div style="min-width: 654px; width: 790px; background: #FFFFFF; border: 1px solid #C4C4C4; padding: 24px 24px 0; margin-bottom: 24px">
      <el-form :inline="true" label-width="70px" size="mini">
        <el-form-item label="是否弃检">
          <template v-for="item,index in isUncheckedData">
            <el-input style="width:125px;" readonly :key="index" :value="item.text" v-if="isUnchecked == item.id"></el-input>
          </template>
        </el-form-item>
        <el-form-item label="是否弃件">
          <template v-for="item,index in isDangerData">
            <el-input style="width:125px;" readonly :key="index" :value="item.text" v-if="isDanger == item.id"></el-input>
          </template>
        </el-form-item>
      </el-form>
      <!-- 检测值标题 -->
      <div id="measured_value">
        <el-descriptions :column="7" direction="vertical">
          <el-descriptions-item label="项目名称" :span="3">
            <template>
              <div id="project" v-for="item in labelData" :key="item.id" style="min-width:260px;">{{ item.text }}</div>
            </template>
          </el-descriptions-item>
          <el-descriptions-item label="实测值">
            <template>
              <el-input v-model="measurement.fvc" readonly size="small"  controls-position="right" @change="handleChange"></el-input>
              <el-input v-model="measurement.fev" readonly size="small" controls-position="right" @change="handleChange"></el-input>
              <el-input v-model="measurement.fevFvc" readonly size="small" controls-position="right" @change="handleChange"></el-input>
              <el-input v-model="measurement.mmef" readonly size="small" controls-position="right" @change="handleChange"></el-input>
              <el-input v-model="measurement.feffa" readonly size="small" controls-position="right" @change="handleChange"></el-input>
              <el-input v-model="measurement.feffb" readonly size="small" controls-position="right" @change="handleChange"></el-input>
              <el-input v-model="measurement.feffc" readonly size="small" controls-position="right" @change="handleChange"></el-input>
            </template>
          </el-descriptions-item>
          <el-descriptions-item label="预测值">
            <el-input v-model="predict.predictFvc" readonly size="small"  controls-position="right" @change="handleChange" ></el-input>
            <el-input v-model="predict.predictFev" readonly size="small" controls-position="right" @change="handleChange" ></el-input>
            <el-input v-model="predict.predictFevFvc" readonly size="small" controls-position="right" @change="handleChange" ></el-input>
            <el-input v-model="predict.predictMmef" readonly size="small" controls-position="right" @change="handleChange" ></el-input>
            <el-input v-model="predict.predictFeffa" readonly size="small" controls-position="right" @change="handleChange" ></el-input>
            <el-input v-model="predict.predictFeffb" readonly size="small" controls-position="right" @change="handleChange" ></el-input>
            <el-input v-model="predict.predictFeffc" readonly size="small" controls-position="right" @change="handleChange" ></el-input>
          </el-descriptions-item>
          <el-descriptions-item label="百分比">
            <el-input v-model="percentage.percentageFvc" readonly size="small"  controls-position="right" @change="handleChange" ></el-input>
            <el-input v-model="percentage.percentageFev" readonly size="small" controls-position="right" @change="handleChange" ></el-input>
            <el-input v-model="percentage.percentageFevFvc" readonly size="small" controls-position="right" @change="handleChange" ></el-input>
            <el-input v-model="percentage.percentageMmef" readonly size="small" controls-position="right" @change="handleChange" ></el-input>
            <el-input v-model="percentage.percentageFeffa" readonly size="small" controls-position="right" @change="handleChange" ></el-input>
            <el-input v-model="percentage.percentageFeffb" readonly size="small" controls-position="right" @change="handleChange" ></el-input>
            <el-input v-model="percentage.percentageFeffc" readonly size="small" controls-position="right" @change="handleChange" ></el-input>
          </el-descriptions-item>
          <el-descriptions-item label="单位">
            <template>
              <el-input value="L" class="dw" readonly size="small" controls-position="right" @change="handleChange" ></el-input>
              <el-input value="L" class="dw" readonly size="small" controls-position="right" @change="handleChange" ></el-input>
              <el-input value="%" class="dw" readonly size="small" controls-position="right" @change="handleChange" ></el-input>
              <el-input value="L" class="dw" readonly size="small" controls-position="right" @change="handleChange" ></el-input>
              <el-input value="L" class="dw" readonly size="small" controls-position="right" @change="handleChange" ></el-input>
              <el-input value="L" class="dw" readonly size="small" controls-position="right" @change="handleChange" ></el-input>
              <el-input value="L" class="dw" readonly size="small" controls-position="right" @change="handleChange" ></el-input>
            </template>
          </el-descriptions-item>
        </el-descriptions>
      </div>
      
    </div>
    <div style="flex:1; margin-left: 16px; overflow: scroll;">
      <el-form :inline="true" ref="form" id="setBottom" :model="form" label-width="70px" style="min-width: 742px; height: 230px; border: 1px solid #C4C4C4;">
          <el-form-item label-width="200px" class="form-title">
              <template slot="label">
                  <div style="font-size: 16px; line-height: 22px; margin:12px 16px 16px; text-align: left; ">【科室小结】</div>
              </template>
              <div style="width:710px; height: 75px; background-color: rgb(246, 247, 251); margin-left: 12px;">
                  <div style="padding-left:16px;">{{conclusions}}</div>
              </div>
          </el-form-item>
          <el-form-item label="检查人">
            <el-input v-model="form.isAudit" readonly style="width: 240px;"></el-input>
          </el-form-item>
          <el-form-item label="检查时间" label-width="80px" prop="date">
            <el-date-picker v-model="form.rummagerTime" readonly style="width: 323px;" value-format="yyyy-MM-dd HH:mm:ss"
             type="date" placeholder="请选择日期"></el-date-picker>
          </el-form-item>
          <el-form-item label="录入人">
            <el-input v-model="form.writeId" readonly style="width:240px;"></el-input>
          </el-form-item>
          <el-form-item label="录入时间" label-width="80px" prop="date">
            <el-date-picker v-model="form.writeTime" readonly style="width: 323px;" value-format="yyyy-MM-dd HH:mm:ss"
             type="date" placeholder="请选择日期"></el-date-picker>
          </el-form-item>
      </el-form>
      <div style="height: 276px; min-width: 742px; margin-top: 16px; border: 1px solid #C4C4C4;" class="flex-direction-column">
        <div style="font-size: 16px; line-height: 22px; margin:12px 16px 16px; text-align: left; ">【结论词】</div>
        <div class="table-box">
          <el-table border v-loading="loading" :data="tableList" height="100%" stripe @selection-change="handleSelectionChange">
            <el-table-column type="selection"></el-table-column>
            <el-table-column prop="jcxm" label="检查项目" align="center"></el-table-column>
            <el-table-column prop="tzc" label="体征词" align="center"></el-table-column>
            <el-table-column prop="jlcId" label="结论词" align="center"></el-table-column>
          </el-table>
        </div>
      </div>
      <el-form :inline="true" id="basicMargin" label-width="70px" style="min-width: 742px; height: 226px; margin: 16px 0 24px; border: 1px solid #C4C4C4;">
        <div style="display: inline-block; width: 80%; vertical-align: bottom;" >
          <el-form-item style="display:block;" label-width="200px" class="form-title">
              <template slot="label">
                  <div style="font-size: 16px; line-height: 22px; margin:12px 16px 16px; text-align: left; ">【基本信息】</div>
              </template>
          </el-form-item>
          <el-form-item label="姓名">
              <el-input placeholder="读取体检号自动获取" readonly v-model="form.patientname" style="width:200px;"></el-input>
          </el-form-item>
          <el-form-item label="性别">
              <!-- <el-select placeholder="读取体检号自动获取" readonly v-model="form.idSex" style="width:200px;">
                <el-option v-for="item in idSex" :key="item.id" :value="item.id" :label="item.text"></el-option>
              </el-select> -->
              <template v-for="item in idSex">
                <el-input placeholder="读取体检号自动获取" :key="item.id" readonly :value="form.idSex == 0 ? '男' : '女'" v-if="item.id == form.idSex" style="width:200px;" />
              </template>
          </el-form-item>
          <el-form-item label="年龄">
              <el-input placeholder="读取体检号自动获取" readonly v-model="form.age" style="width:200px;"></el-input>
          </el-form-item>
          <el-form-item label="电话">
              <el-input placeholder="读取体检号自动获取" readonly v-model="form.phone" style="width:200px;"></el-input>
          </el-form-item>
          <el-form-item label="体检号" style="margin-bottom:0">
              <el-input placeholder="读取体检号自动获取" readonly v-model="form.patientcode" style="width:200px;"></el-input>
          </el-form-item>
          <el-form-item label="类型" style="margin-bottom:0">
            <template v-for="item in idExamtype">
              <el-input placeholder="读取体检号自动获取" :key="item.id" :value="item.text" readonly v-if="item.id == form.idExamtype" style="width:200px;"></el-input>
            </template>
          </el-form-item>
        </div>
        <div style="display: inline-block; width: 20%;height: 208px;position: relative;">
          <el-image style="width: 120px; height: 152px; text-align: center; border-radius: 5px; position: absolute; bottom: 0;left: 0;" :src="picture" fit="cover">
            <template slot="error">
              <div style="background: rgb(246, 247, 251); height: 100%; line-height: 152px;">暂无头像</div>
            </template>
          </el-image>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import {lung,case1} from "@/api/inspection/occupational_inspection.js"
import Cookies from 'js-cookie'
export default{
    data(){
        return{
          picture:"",//图片路径
          open:false,
          patientno:"",
          //描述列表
          labelData:[
            {id:0,text:"用力肺活量（FVC):"},
            {id:1,text:"1秒用力呼气容积(FEV1):"},
            {id:2,text:"1秒用力呼气容积/用力肺活量(FEV1%G):"},
            {id:3,text:"最大呼气中期流速(MMEF):"},
            {id:4,text:"25%呼气中段流量（FEF25%）:"},
            {id:6,text:"50%呼气中段流量（FEF50%）:"},
            {id:7,text:"75%呼气中段流量（FEF75%）:"},
          ],
          //表单数据
          form:{
            a:0,
            date:undefined,
          },
          data:"",
          a:[{id:0,text:"jiuzhe"}],
          conclusions:"",
          //表格数据
          tableList:[
          ],
          loading:false,
          isDanger:0,
          isDangerData:[{
            id:0,
            text:"否",
          },{
            id:1,
            text:"真",
          }],
          isUnchecked:0,
          isUncheckedData:[{
            id:0,
            text: "无"
          },{
            id:1,
            text: "低"
          },{
            id:2,
            text: "中"
          },{
            id:3,
            text: "高"
          }],

          //实测值
          measurement:{},
          //预测值
          predict:{},
          //百分比
          percentage:{},
          idSex:[
            {id:'0',text:"男"},
            {id:'1',text:"女"},
            {id:'2',text:"通用"}
          ],
          idExamtype:[
            {id:'0',text:'健康体检'},
            {id:'1',text:'职业体检'},
            {id:'2',text:'综合'}
          ]
        }
    },
    methods:{
      getPulmonaryWindow(patientno){
        this.patientno = patientno;
        this.getlung();
        this.goCase();
      },
      getlung(){
        this.loading = true;
        var obj = {
          patientCode:this.patientno
        }
        lung(obj).then((res)=>{
          if(res.code == 200){
            var objMeasurement = {
              feffa:res.data.feffa,
              feffb:res.data.feffb,
              feffc:res.data.feffc,
              fev:res.data.fev,
              fevFvc:res.data.fevFvc,
              fvc:res.data.fvc,
              mmef:res.data.mmef,
            }
            var objPredictMmef = {
              predictFeffa:res.data.predictFeffa,
              predictFeffb:res.data.predictFeffb,
              predictFeffc:res.data.predictFeffc,
              predictFev:res.data.predictFev,
              predictFevFvc:res.data.predictFevFvc,
              predictFvc:res.data.predictFvc,
              predictMmef:res.data.predictMmef,
            }
            var objPercentage = {
              percentageFeffa:res.data.percentageFeffa,
              percentageFeffb:res.data.percentageFeffb,
              percentageFeffc:res.data.percentageFeffc,
              percentageFev:res.data.percentageFev,
              percentageFevFvc:res.data.percentageFevFvc,
              percentageFvc:res.data.percentageFvc,
              percentageMmef:res.data.percentageMmef,
            }
            this.measurement={...this.measurement,...objMeasurement};
            this.predict={...this.predict,...objPredictMmef};
            this.percentage={...this.percentage,...objPercentage};
            
            this.loading = false;
          }
        }).catch(()=>{
          this.loading = false;
        })
      },
      goCase(){
        var autoFill = false;
        if(this.patientno.length <12){
          autoFill = true;
        }
        var obj = {
          autoFill,
          // gridSeclect:"",//不用传
          ksID:"77",//abteilung/division/list
          patientcode:this.patientno
        }
        case1(obj).then((res)=>{
          if(res.code == 200){
              this.tableList = res.data.griddata;
              this.form.writeId = res.data.lrr;
              this.form.writeTime = res.data.lrsj;
              this.form.rummagerTime = res.data.jcrxm;
              this.form.isAudit = res.data.jcrxm;
              this.form.patientname = res.data.peispatient.patientname;
              this.form.idSex= res.data.peispatient.idSex;
              this.form.age= res.data.peispatient.age;
              this.form.phone= res.data.peispatient.phone;
              this.form.patientcode= res.data.peispatient.patientcode;
              this.form.idExamtype= res.data.peispatient.idExamtype;
              this.picture = res.data.picture ? Cookies.get("imgPath") + res.data.picture : res.data.picture;
          }
        })
      },
      handleChange(){

      },
      handleSelectionChange(){}
    }
}
</script>

<style scoped>
 #measured_value /deep/ .el-input{
  width: 100px ;
  height: 32px ;
  line-height: 32px;
  margin: 2px 0;
}
#project{
  height: 36px;
  line-height: 36px;
}
#measured_value /deep/ .dw{
  width: 60px;
  border-radius: 5px;
  text-align: center;
}
#setBottom /deep/ .el-form-item {
  margin-bottom: 8px;
}
#setBottom /deep/ .form-title {
  margin-bottom: 12px;
}
#basicMargin /deep/ .el-form-item{
  height: 40px;
  margin-bottom: 16px;
}
</style>