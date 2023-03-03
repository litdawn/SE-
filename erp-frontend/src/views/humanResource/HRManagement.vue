<template>
  <Layout>
    <Title title="员工管理"></Title>
    <el-button type="primary" size="medium" @click="dialogVisible1 = true;isNew=true">员工入职</el-button>
    <el-button type="primary" size="medium" @click="dialogVisible2 = true;rule='';id=''">薪资规则制定</el-button>
    <div  style="margin-top: 10px">
      <el-table
          :data="staffList"
          stripe
          style="width: 100%"
          :header-cell-style="{'text-align':'center'}"
          :cell-style="{'text-align':'center'}">
        <el-table-column
            prop="staffId"
            label="id"
            width="60">
        </el-table-column>
        <el-table-column
            prop="name"
            label="姓名"
            width="100">
        </el-table-column>
        <el-table-column
            prop="gender"
            label="性别"
            width="60">
        </el-table-column>
        <el-table-column
            prop="birthDay"
            label="出生日期"
            width="150">
        </el-table-column>
        <el-table-column
            prop="phoneNumber"
            label="手机"
            width="150">
        </el-table-column>
        <el-table-column
            prop="role"
            label="工作岗位"
            width="100">
        </el-table-column>
        <el-table-column
            prop="basicAmount"
            label="基本工资"
            width="200">
        </el-table-column>
        <el-table-column
            prop="positionAmount"
            label="岗位工资"
            width="200">
        </el-table-column>
        <el-table-column
            prop="level"
            label="岗位级别"
            width="200">
        </el-table-column>
        <el-table-column
            prop="paymentCalculation"
            label="薪资计算方式"
            width="200">
        </el-table-column>
        <el-table-column
            prop="paymentSchedule"
            label="薪资发放方式"
            width="200">
        </el-table-column>
        <el-table-column
            prop="staffBankAccount"
            label="工资卡账户"
            width="200">
        </el-table-column>
        <el-table-column
            label="操作" width="150">
          <template slot-scope="scope">
            <el-button
                @click.native.prevent="editInfo(scope.row)"
                type="text"
                size="small">
              编辑
            </el-button>
            <el-button
                @click.native.prevent="deleteInfo(scope.row)"
                type="text"
                size="small">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <el-dialog title="员工信息" :visible.sync="dialogVisible1">
      <el-form ref="form" :model="form" label-width="100px">
        <el-form-item label="员工ID" style="width: 60%">
          <el-input v-model="form.staffId"></el-input>
        </el-form-item>
        <el-form-item label="员工姓名" style="width: 60%">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="性别" style="width: 60%">
          <el-radio v-model="form.gender" label="男" ></el-radio>
          <el-radio v-model="form.gender" label="女" ></el-radio>
        </el-form-item>
        <el-form-item label="出生日期"  style="width: 60%">
          <el-input v-model="form.birthDay"></el-input>
        </el-form-item>
        <el-form-item label="手机号"  style="width: 60%">
          <el-input v-model="form.phoneNumber"></el-input>
        </el-form-item>
        <el-form-item label="工作岗位"  style="width: 60%">
          <el-select  v-model="form.role">
            <el-option label="库存管理人员" value="INVENTORY_MANAGER"></el-option>
            <el-option label="进货销售人员" value="SALE_STAFF"></el-option>
            <el-option label="财务人员" value="FINANCIAL_STAFF"></el-option>
            <el-option label="人力资源人员" value="HR"></el-option>
            <el-option label="总经理" value="GM"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="基本工资(元)" style="width: 60%">
          <el-input-number v-model="form.basicAmount"></el-input-number>
        </el-form-item>
        <el-form-item label="岗位工资(元)" style="width: 60%">
          <el-input-number v-model="form.positionAmount"></el-input-number>
        </el-form-item>
        <el-form-item label="岗位级别" style="width: 60%">
          <el-radio v-model="form.level" label="1" value="1"></el-radio>
          <el-radio v-model="form.level" label="2" value="2"></el-radio>
          <el-radio v-model="form.level" label="3" value="3"></el-radio>
        </el-form-item>
        <el-form-item label="薪资计算方式" style="width: 60%">
          <el-input disabled v-model="form.paymentCalculation" value="分级扣税" placeholder="分级扣税"></el-input>
        </el-form-item>
        <el-form-item label="薪资发放方式" style="width: 60%">
          <el-select v-model="form.paymentSchedule">
            <el-option label="年薪制" value="年薪制"></el-option>
            <el-option label="月薪制" value="月薪制"></el-option>
            <el-option label="提成制" value="提成制"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="工资卡账户" style="width: 60%">
          <el-input minlength="16" maxlength="16"
              v-model="form.staffBankAccount"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible1 = false;this.form={}">取 消</el-button>
    <el-button type="primary" @click="save">确 定</el-button>
  </span>
    </el-dialog>


    <el-dialog style="width: 60%"
               title="薪资规则" :visible.sync="dialogVisible2">
      <el-form ref="form" label-width="100px">
        <el-form-item label="员工ID:" style="width: 75%">
          <el-input v-model="id"></el-input>
        </el-form-item>
        <el-form-item label="薪资规则:"  style="width: 75%">
          <el-select v-model="rule">
            <el-option label="年薪制" value="年薪制"></el-option>
            <el-option label="月薪制" value="月薪制"></el-option>
            <el-option label="提成制" value="提成制"></el-option>
          </el-select>
        </el-form-item>
        <div style="margin-left:  50%; margin-top: 20%">
          <el-button @click="dialogVisible2 = false; id='';rule=''">取 消</el-button>
          <el-button type="primary" @click="changeRule">确 定</el-button>
        </div>
      </el-form>
    </el-dialog>

  </Layout>
</template>
<script>
import Layout from "@/components/content/Layout";
import Title from "@/components/content/Title";
import {MAKE_SALARY_RULE, MAKE_STAFF_ACCOUNT, SHOW_STAFF_ACCOUNT, STAFF_ACCOUNT_CHANGE} from "@/network/humanResource";
export default {
  name: 'HRManagement',
  components: {
    Layout,
    Title
  },
  data(){
    return{
      staffList: [],
      isNew: false,
      dialogVisible1: false,
      dialogVisible2: false,
      form:{},
      id:'',
      rule: '',
    }
  },
  created() {
    this.load()
  },
  methods:{
    async load(){
      this.staffList = [];
      await SHOW_STAFF_ACCOUNT().then(_res =>{
        this.staffList = this.staffList.concat(_res.result)
        for(let i=0;i<this.staffList.length;i++){
          if(this.staffList[i].gender === 'M'){
            this.staffList[i].gender = '男'
          }else{
            this.staffList[i].gender = '女'
          }
        }
      })
    },
    async save(){
      let sl = JSON.parse(JSON.stringify(this.form))
      sl.paymentCalculation = "分级扣税"
      if(sl.gender === '男'){
        sl.gender = 'M'
      }else {
        sl.gender = 'F'
      }
      if(this.isNew){
        await MAKE_STAFF_ACCOUNT(sl)
        await this.load()
      }else {
        await STAFF_ACCOUNT_CHANGE(sl)
        await this.load()
      }
      this.form={}
      this.dialogVisible1 = false;
    },
    editInfo(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible1 = true;
      this.isNew = false;
    },
    async changeRule(){
      await MAKE_SALARY_RULE({params:{
        staffId: this.id,
        rule: this.rule
      }
      }) 
      await this.load()
      this.dialogVisible2 = false
    }
  }
}
</script>