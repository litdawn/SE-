<template>
  <Layout>
    <Title title="收付款管理"></Title>
    <el-button type="primary" size="medium" @click="dialogRVisible = true">制定收款单</el-button>
    <el-button type="primary" size="medium" @click="dialogPVisible = true">制定付款单</el-button>
    <el-dialog
        title="制定付款单"
        :visible.sync="dialogPVisible"
        width="40%"
        :before-close="handleClose">
      <div style="width: 90%; margin: 0 auto">
        <el-form :model="pay" label-width="80px" ref="pay" >
<!--          <el-form-item label="编号" prop="id">-->
<!--            <el-input v-model="pay.id" placeholder="XJFYD-yyyyMMdd-xxxxx"></el-input>-->
<!--          </el-form-item>-->
          <el-form-item label="操作员" prop="operator">
            <el-input v-model="pay.operator"></el-input>
          </el-form-item>
          <el-form-item label="银行账户" prop="bankAccount">
            <el-select v-model="pay.bankAccount" placeholder="请选择银行账户">
              <el-option
                  v-for="item in bankAccounts"
                  :key="item.id"
                  :label="item.id"
                  :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="客户" prop="customerId">
            <el-select v-model="pay.customerId" placeholder="请选择客户ID">
              <el-option
                  v-for="item in customers"
                  :key="item"
                  :label="item"
                  :value="item">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="条目个数">
            <el-input-number v-model="num" @change="handleChange" :min="1" :max="5"></el-input-number>
          </el-form-item>
          <el-form-item
              v-for="(item, index) in payContent"
              :key="index"
              :label="'条目' + index"
              >
            <div>
<!--              <el-input style="margin-bottom: 3%" v-model="pay.id" placeholder="付款单编号" :disabled="true"></el-input>-->
              <el-input style="margin-bottom: 3%" v-model="payContent[index].name" placeholder="条目名称"></el-input>
              <el-input style="margin-bottom: 3%" v-model.number="payContent[index].amount" placeholder="金额"></el-input>
              <el-input type="textarea" v-model="payContent[index].remark" placeholder="备注"></el-input>
            </div>
          </el-form-item>
          <el-form-item label="总额" prop="totalAmount">
            <el-input v-model.number="pay.totalAmount"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
    <el-button type="primary" @click="submitPay('pay')">提交</el-button>
  </span>
    </el-dialog>
    <el-dialog
        title="制定收款单"
        :visible.sync="dialogRVisible"
        width="40%"
        :before-close="handleClose2">
      <div style="width: 90%; margin: 0 auto">
        <el-form :model="receive" label-width="80px" ref="receive" >
<!--          <el-form-item label="编号" prop="id">-->
<!--            <el-input v-model="receive.id" placeholder="SKD-yyyyMMdd-xxxxx"></el-input>-->
<!--          </el-form-item>-->
          <el-form-item label="操作员" prop="operator">
            <el-input v-model="receive.operator"></el-input>
          </el-form-item>
          <el-form-item label="客户" prop="customerId">
            <el-select v-model="receive.customerId" placeholder="请选择客户ID">
              <el-option
                  v-for="item in customers"
                  :key="item"
                  :label="item"
                  :value="item">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="条目个数">
            <el-input-number v-model="num2" @change="handleChange2" :min="1" :max="5"></el-input-number>
          </el-form-item>
          <el-form-item
              v-for="(item, index) in reContent"
              :key="index"
              :label="'条目' + index"
              prop="item">
            <div>
<!--              <el-input style="margin-bottom: 3%" v-model="receive.id" placeholder="收款单编号" :disabled="true"></el-input>-->
              <el-select style="margin-bottom: 3%" v-model="reContent[index].bankAccount" placeholder="请选择银行账户">
                <el-option
                    v-for="item in bankAccounts"
                    :key="item.id"
                    :label="item.id"
                    :value="item.id">
                </el-option>
              </el-select>
              <el-input style="margin-bottom: 3%" v-model.number="reContent[index].transferAmount" placeholder="金额"></el-input>
              <el-input type="textarea" v-model="reContent[index].remark" placeholder="备注"></el-input>
            </div>
          </el-form-item>
          <el-form-item label="总额" prop="totalAmount">
            <el-input v-model.number="receive.totalAmount"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
    <el-button type="primary" @click="submitReceive('receive')">提交</el-button>
  </span>
    </el-dialog>
  </Layout>
</template>

<script>
import Layout from "@/components/content/Layout";
import Title from "@/components/content/Title";
import { createPay } from "@/network/account";
import { getALLAccount} from "@/network/account";
import { getAllCustomer} from "@/network/account";
import { createReceive } from "@/network/account";

export default {
  name: "AccountPayReceive",
  components: {Title,Layout},
  data() {
    return {
      dialogRVisible: false,
      dialogPVisible: false,
      pay: {
        id: null,
        bankAccount: null,
        customerId: null,
        operator: null,
        payableContent:[],
        totalAmount: null,
        state: null
      },
      num: 1,
      num2: 1,
      payContent:[{
        sheetId: null,
        name: null,
        amount: null,
        remark: null
      }],
      // payRules: {
      //   operator: [
      //     {required: true, message: '请输入操作员', trigger: 'blur'}
      //   ],
      //   totalAmount: [
      //     {required: true, message: '请输入总金额', trigger: 'blur'}
      //
      //   ],
      //   bank: [
      //     {required: true, message: '请选择银行账户', trigger: 'blur'}
      //   ],
      //   customerId: [
      //     {required: true, message: '请选择客户ID', trigger: 'blur'}
      //   ],
      // },
      // reRules: {
      //   id: [
      //     {required: true, message: '请输入编号', trigger: 'blur'}
      //   ],
      //   totalAmount: [
      //     {required: true, message: '请输入总金额', trigger: 'blur'}
      //
      //   ],
      //   customerId: [
      //     {required: true, message: '请选择客户ID', trigger: 'blur'}
      //   ],
      //   bankAccount: [
      //     {required: true, message: '请选择银行账户', trigger: 'blur'}
      //   ]
      // },
      bankAccounts: [],
      customers: [],
      receive: {
        id: null,
        customerId: null,
        operator: null,
        receivableContent: [],
        totalAmount: null,
        state: null,
      },
      reContent:[
        {
          sheetId: null,
          bankAccount : null,
          transferAmount: null,
          remark: null
        }
      ]
    }
  },
  mounted() {
    getALLAccount({}).then(_res => {
      let res = _res.result;
      this.bankAccounts = [];
      res.forEach(item => this.bankAccounts.push({id:item.id}))
    })
    getAllCustomer({}).then(_res => {
      this.customers = [];
      this.customers = _res.result;
    })
  },
  methods: {
    handleChange() {
      this.payContent = [];
      for(var i=0; i<this.num; i++)
        this.payContent.push({
          sheetId: null,
          name: null,
          amount: null,
          remark: null
        });
    },
    handleChange2() {
      this.reContent = [];
      for(var i=0; i<this.num2; i++)
        this.reContent.push({
          sheetId: null,
          bankAccount : null,
          transferAmount: null,
          remark: null
        });
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
      this.$confirm('确认关闭？')
          .then(_ => {
            this.resetForm2()
            done();
          })
          .catch(_ => {});
    },
    resetForm() {
      this.pay = {
          id: null,
          bankAccount: null,
          customerId: null,
          operator: null,
          payableContent:[],
          totalAmount: null,
          state: null
      }
      this.payContent = [];
    },
    resetForm2() {
      this.receive = {
        id: null,
        customerId: null,
        operator: null,
        receivableContent: [],
        totalAmount: null,
        state: null,
      };
      this.reContent = [
        {
          sheetId: null,
          bankAccount : null,
          transferAmount: null,
          remark: null
        }
      ]
    },
    submitPay(pay) {
      var bool = true;
      if(this.pay.operator == null || this.pay.customerId == null || this.pay.totalAmount == null)
        bool = false;
      for(var i=0; i<this.num; i++){
        if(this.payContent[i].name == null || this.payContent[i].amount == null)
          bool = false;
      }
      if(!bool){
        this.$message.error('必填项有缺省！');
        return;
      }
      for(var j=0; j<this.num; j++){
        // this.payContent[i].sheetId = this.pay.id;
        this.pay.payableContent.push(this.payContent[j]);
      }
      createPay(this.pay).then(_res => {
        console.log(_res)
        if (_res.msg === 'Success') {
          this.$message.success('制定成功!')
          this.resetForm()
          this.dialogPVisible = false
        }else {
          this.$message.error('制定失败!')
        }
      })
    },
    submitReceive(receive) {
      if(isNaN(this.receive.totalAmount) || this.receive.totalAmount < 0){
        this.$message.error('金额错误！');
        return;
      }
      var bool = true;
      if(this.receive.operator == null || this.receive.customerId == null || this.receive.totalAmount == null)
        bool = false;
      for(var i=0; i<this.num2; i++){
        if(isNaN(this.reContent[i].transferAmount) || this.reContent[i].transferAmount < 0){
          this.$message.error('金额错误！');
          return;
        }
        if(this.reContent[i].bankAccount == null || this.reContent[i].transferAmount == null)
          bool = false;
      }
      if(!bool){
        this.$message.error('必填项有缺省！');
        return;
      }
      for(var j=0; j<this.num2; j++){
        // this.reContent[i].sheetId = this.receive.id;
        this.receive.receivableContent.push(this.reContent[j]);
      }
      createReceive(this.receive).then(_res => {
        console.log(_res)
        if (_res.msg === 'Success') {
          this.$message.success('制定成功!')
          this.resetForm2()
          this.dialogRVisible = false
        }else {
          this.$message.error('制定失败!')
        }
      })
    }
  }
}
</script>

<style scoped>

</style>