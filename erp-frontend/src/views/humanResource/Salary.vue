<template>
  <Layout>
    <Title title="工资单"></Title>
    <el-button
        type="primary" v-if="authorization() === 1"
        size="medium" @click="dialogVisible = true">工资单制定</el-button>
    <div class="body">
      <el-tabs v-model="activeName" :stretch="true">
        <el-tab-pane label="待总经理审批" name="PENDING_LEVEL_2">
          <div v-if="pendingLevel2List.length === 0">
            <el-empty description="暂无数据"></el-empty>
          </div>
          <div v-else>
            <SalaryList :list="pendingLevel2List" :type="'PENDING_LEVEL_2'" @refresh="getSalary()"/>
          </div>
        </el-tab-pane>
        <el-tab-pane label="审批完成" name="SUCCESS">
          <div v-if="successList.length === 0">
            <el-empty description="暂无数据"></el-empty>
          </div>
          <div v-else>
            <SalaryList :list="successList" :type="'SUCCESS'" @refresh="getSalary()"/>
          </div>
        </el-tab-pane>
        <el-tab-pane label="审批失败" name="FAILURE">
          <div v-if="failureList.length === 0">
            <el-empty description="暂无数据"></el-empty>
          </div>
          <div v-else>
            <SalaryList :list="failureList" :type="'FAILURE'" @refresh="getSalary()"/>
          </div>
        </el-tab-pane>
      </el-tabs>
<!--          <SalaryList :list="salaryList" type="3"/>-->
    </div>

    <el-dialog
      title="工资单制定" :visible.sync="dialogVisible">
      <el-form ref="form" label-width="100px">
        <el-form-item label="工资单编号:">
          <el-input disabled v-model="salarySheet.id"></el-input>
        </el-form-item>
        <el-form-item label="员工id:">
          <el-input v-model="salarySheet.staffId"></el-input>
        </el-form-item>
        <el-form-item label="员工姓名:">
          <el-input v-model="salarySheet.name"></el-input>
        </el-form-item>
        <el-form-item label="工作岗位:"  style="width: 75%">
          <el-select v-model="salarySheet.role">
            <el-option label="库存管理人员" value="INVENTORY_MANAGER"></el-option>
            <el-option label="进货销售人员" value="SALE_STAFF"></el-option>
            <el-option label="财务人员" value="FINANCIAL_STAFF"></el-option>
            <el-option label="销售经理" value="SALE_MANAGER"></el-option>
            <el-option label="人力资源人员" value="HR"></el-option>
            <el-option label="总经理" value="GM"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="操作员:">
          <el-input v-model="salarySheet.operator"></el-input>
        </el-form-item>
        <el-form-item label="银行账户:">
          <el-input  v-model="salarySheet.staffBankAccount"></el-input>
        </el-form-item>
        <el-form-item label="基本工资:">
          <el-input-number v-model="salarySheet.basicAmount"></el-input-number>
        </el-form-item>
        <el-form-item label="岗位工资:">
          <el-input-number v-model="salarySheet.positionAmount"></el-input-number>
        </el-form-item>
        <el-form-item label="应发工资:">
          <el-input-number disabled v-model="salarySheet.rawAmount"></el-input-number>
        </el-form-item>
        <el-form-item label="税款:">
          <el-input-number  v-model="salarySheet.taxAmount"></el-input-number>
        </el-form-item>
        <el-form-item label="提成:">
          <el-input-number  v-model="salarySheet.pushMoney"></el-input-number>
        </el-form-item>
        <el-form-item label="缺勤次数:">
          <el-input-number  v-model="salarySheet.absenceTimes"></el-input-number>
        </el-form-item>
        <el-form-item label="年终奖:">
          <el-input-number  v-model="salarySheet.annualBonus"></el-input-number>
        </el-form-item>
        <el-form-item label="实发金额:">
          <el-input-number disabled v-model="salarySheet.finalAmount"></el-input-number>
        </el-form-item>
        <el-form-item label="薪资发放方式:">
            <el-select v-model="salarySheet.paymentSchedule">
              <el-option label="年薪制" value="年薪制"></el-option>
              <el-option label="月薪制" value="月薪制"></el-option>
              <el-option label="提成制" value="提成制"></el-option>
            </el-select>
        </el-form-item>
        <el-form-item  label="单据状态">
          <el-input disabled v-model="salarySheet.state"></el-input>
        </el-form-item>
      </el-form>
      <div style="margin-left:  70%; margin-top: 40px">
        <el-button @click="dialogVisible2 = false">取 消</el-button>
        <el-button type="primary" @click="makeSalarySheet">确 定</el-button>
      </div>
    </el-dialog>

  </Layout>
</template>
<script>
import Layout from "@/components/content/Layout";
import Title from "@/components/content/Title";
import SalaryList from "@/views/humanResource/components/SalaryList";
import { MAKE_SALARY_SHEET, SHOW_SHEET_BY_STATE} from "@/network/humanResource";
export default {
  name:'Salary',
  components: {
    Title,
    Layout,
    SalaryList
  },
  created() {
    this.load()
  },
  data() {
    return {
      dialogVisible: false,
      value: '',
      salary_form: {},
      salarySheet:{},
      activeName:'SUCCESS',
      pendingLevel2List: [],
      successList: [],
      failureList: [],
      salaryList: []
    };
  },
  methods: {
    async load(){
      this.salaryList = [];
      this.pendingLevel2List = [];
      this.successList = [];
      this.failureList = [];
      await SHOW_SHEET_BY_STATE({ params: { state: 'PENDING_LEVEL_2'}}).then(_res => {
        this.pendingLevel2List = this.pendingLevel2List.concat(_res.result)
      })
      await SHOW_SHEET_BY_STATE({ params: { state: 'SUCCESS'}}).then(_res => {
        this.successList = this.successList.concat(_res.result)
      })
      await SHOW_SHEET_BY_STATE({ params: { state: 'FAILURE'}}).then(_res => {
        this.failureList = this.failureList.concat(_res.result)
      })
    },
    async makeSalarySheet(){
      this.salarySheet.id = null
      this.salarySheet.rawAmount = null
      this.salarySheet.finalAmount = null
      this.salarySheet.state = null
      let ss = JSON.parse(JSON.stringify(this.salarySheet))
      await MAKE_SALARY_SHEET(ss)
      location.reload()
    },
    authorization(){
      return (sessionStorage.getItem('role') === 'HR') ? 1 : 0
    }
  }
}
</script>

<style lang="scss" scoped>
.body {
  margin: 10px auto 0;
  height: 80vh;
  overflow-y: auto;
  width: 100%;
  background: rgba($color: #fff, $alpha: 0.5);
}
</style>