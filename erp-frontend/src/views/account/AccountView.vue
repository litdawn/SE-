<template>
  <Layout>
    <Title title="账户管理"></Title>
    <el-row>
      <el-col :span="14"><el-button type="primary" @click="newDVisible = true">新增账户</el-button></el-col>
      <el-col :span="6"><el-input v-model="input" placeholder="请输入关键字"></el-input></el-col>
      <el-col :span="2"><el-button icon="el-icon-search" @click="search"></el-button></el-col>
    </el-row>
    <el-dialog
        title="新增账户"
        :visible.sync="newDVisible"
        width="30%"
        :before-close="handleClose">
      <div style="width: 90%; margin: 0 auto">
        <el-form :model="account" label-width="80px" ref="account" :rules="rules">
          <el-form-item label="账户ID" prop="id">
            <el-input v-model="account.id"></el-input>
          </el-form-item>
          <el-form-item label="账户名称" prop="companyBankAccountName">
            <el-input v-model="account.companyBankAccountName"></el-input>
          </el-form-item>
          <el-form-item label="金额" prop="companyBankAccountMoney">
            <el-input v-model.number="account.companyBankAccountMoney"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
    <el-button type="primary" @click="submitCreate('account')">提交</el-button>
  </span>
    </el-dialog>
    <el-dialog
        title="修改账户"
        :visible.sync="changeDVisible"
        width="30%"
        :before-close="handleClose2">
      <div style="width: 90%; margin: 0 auto">
        <el-form :model="accountTochange" label-width="80px" ref="accountToChange" :rules="rules2">
          <el-form-item label="账户ID" prop="id">
            <el-input v-model="accountTochange.id" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="账户名称" prop="companyBankAccountName">
            <el-input v-model="accountTochange.companyBankAccountName" ></el-input>
          </el-form-item>
          <el-form-item label="金额" prop="companyBankAccountMoney">
            <el-input v-model="accountTochange.companyBankAccountMoney" :disabled="true"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
    <el-button type="primary" @click="submitChange('accountToChange')">提交</el-button>
  </span>
    </el-dialog>
    <div class="table-body">
      <el-table
        :data="accountList"
        style="width: 100%;"
        :header-cell-style="{ 'text-align': 'center' }"
        :cell-style="{ 'text-align': 'center' }"
    >
      <el-table-column fixed prop="id" label="ID" min-width="12%">
      </el-table-column>
      <el-table-column prop="name" label="名称" min-width="13%">
      </el-table-column>
      <el-table-column prop="money" label="金额" min-width="13%" >
      </el-table-column>
      <el-table-column label="操作" min-width="20%">
        <template slot-scope="scope">
          <el-row>
            <el-button type="primary" size="small" @click="changeDialog(scope.row)">修改</el-button>
            <el-button type="danger" size="small" @click="deleteAccount(scope.row.id)">删除</el-button>
          </el-row>
        </template>
      </el-table-column>
    </el-table>
    </div>
  </Layout>
</template>

<script>
import Layout from "@/components/content/Layout";
import Title from "@/components/content/Title";
import {getALLAccount,getParticularAccount,deleteAccount,createAccount,changeAccount} from '../../network/account';
export default {
  name: "AccountView.vue",
  components: {Title, Layout},
  data() {
    return{
      accountList: [],
      input:'',
      newDVisible: false,
      changeDVisible: false,
      account:{
        id:null,
        companyBankAccountName:null,
        companyBankAccountMoney:null
      },
      accountTochange:{
        id:null,
        companyBankAccountName:null,
        companyBankAccountMoney:null
      },
      rules: {
        id: [
          { required: true, message: '请输入ID'},
        ],
        companyBankAccountName: [
          { required: true, message: '请输入账户名称'},
          { min: 1, max: 10, message: '长度在 1 到 10 个字符'}
        ],
        companyBankAccountMoney: [
          { required: true, message: '请输入余额' },
          { min: 0,type: 'number', message: '请填写正数' },
        ]
      },
      rules2: {
        companyBankAccountName: [
          { required: true, message: '请输入账户名称', trigger: 'blur' },
          { min: 1, max: 10, message: '长度在 1 到 10 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  mounted() {
      getALLAccount({}).then(_res => {
        let res = _res.result;
        this.accountList = [];
        res.forEach(item => this.accountList.push({
          id: item.id,
          name: item.companyBankAccountName,
          money: item.companyBankAccountMoney
        }))
      })

  },
  methods:{
    update() {
      getALLAccount({}).then(_res => {
        let res = _res.result;
        this.accountList = [];
        res.forEach(item => this.accountList.push({
          id: item.id,
          name: item.companyBankAccountName,
          money: item.companyBankAccountMoney
        }))
      })
    },
    deleteAccount(id) {
      this.$confirm('是否确认删除？')
          .then(_ => {
            deleteAccount({params:{id:id}}).then(_res => {
              console.log(_res)
              if (_res.msg === 'Success') {
                this.$message.success('删除成功!')
                this.update();
              } else {
                this.$message.error('删除错误!');
              }
            })
          })
          .catch(_ => {});

    },
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            this.resetForm()
            done();
          })
          .catch(_ => {});
    },
    handleClose2(done) {
      this.$confirm('修改未保存，确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
    },
    resetForm() {
      this.account = {
          id:'',
          companyBankAccountName:'',
          companyBankAccountMoney:''
      }
    },
    submitCreate(account){
      this.$refs[account].validate((valid) => {
        if (valid) {
          createAccount(this.account).then(_res => {
            console.log(_res)
            if (_res.msg === 'Success') {
              this.$message.success('创建成功!')
              this.resetForm()
              this.newDVisible = false
              this.update()
            }
          })
        } else {
          this.$message.error('创建失败!');
        }
      });
    },
    submitChange(accountToChange){
      this.$refs[accountToChange].validate((valid) => {
        if (valid) {
          changeAccount(this.accountTochange).then(_res => {
            console.log(_res)
            if (_res.msg === 'Success') {
              this.$message.success('修改成功!')
              this.changeDVisible = false
              this.update()
            }
          })
        } else {
          this.$message.error('修改失败!');
        }
      });
    },
    changeDialog(row){
      this.changeDVisible = true;
      this.accountTochange = {
        id:row.id,
        companyBankAccountName:row.name,
        companyBankAccountMoney:row.money
      }
    },
    search() {
      getParticularAccount({params:{companyBankAccountName:this.input}}).then(_res => {
        let res = _res.result;
        this.accountList = [];
        res.forEach(item => this.accountList.push({
          id: item.id,
          name: item.companyBankAccountName,
          money: item.companyBankAccountMoney
        }))
      })
      this.input = '';
    }
  }
}
</script>

<style scoped>
.table-body {
  width: 95%;
  margin: 20px auto;
}
</style>