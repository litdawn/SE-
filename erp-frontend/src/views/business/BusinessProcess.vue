<template>
  <Layout>
    <Title title="经营历程表"></Title>
    <el-row>
      <el-col :span="6">
        <el-date-picker
            v-model="startTime"
            type="date"
            placeholder="选择开始日期">
        </el-date-picker>
      </el-col>
      <el-col :span="6">
        <el-date-picker
            v-model="endTime"
            type="date"
            placeholder="选择结束日期">
        </el-date-picker>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="6" style="margin-top: 2%">
        <el-select v-model="sheetType" placeholder="请选择单据种类">
          <el-option
              v-for="item in types"
              :key="item"
              :label="item"
              :value="item">
          </el-option>
        </el-select>
      </el-col>
      <el-col :span="6" style="margin-top: 2%">
        <el-select v-model="customer" placeholder="请选择客户ID" v-if="sheetType !== '工资单'">
          <el-option
              v-for="item in customers"
              :key="item"
              :label="item"
              :value="item">
          </el-option>
        </el-select>
        <el-select v-model="people" placeholder="请选择员工ID" v-if="sheetType === '工资单'">
          <el-option
              v-for="item in peoples"
              :key="item"
              :label="item"
              :value="item">
          </el-option>
        </el-select>
      </el-col>
      <el-col :span="6" style="margin-top: 2%">
        <el-select v-model="operator" placeholder="请选择业务员">
          <el-option
              v-for="item in users"
              :key="item"
              :label="item"
              :value="item">
          </el-option>
        </el-select>
      </el-col>
      <el-col :span="2" style="margin-top: 2%"><el-button icon="el-icon-search" @click="search"></el-button></el-col>
    </el-row>
    <!--    销售-->
    <el-table
        :data="list"
        style="width: 100%;margin-top: 2%"
        v-if="saleVisible"
        :header-cell-style="{ 'text-align': 'center' }"
        :cell-style="{ 'text-align': 'center' }"
    >
      <el-table-column fixed prop="id" label="ID" min-width="13%"></el-table-column>
      <el-table-column fixed prop="supplier" label="销售商ID" min-width="13%"></el-table-column>
      <el-table-column fixed prop="salesman" label="业务员" min-width="13%"></el-table-column>
      <el-table-column fixed prop="operator" label="操作员" min-width="13%"></el-table-column>
      <el-table-column fixed prop="remark" label="备注" min-width="13%"></el-table-column>
      <el-table-column fixed prop="rawTotalAmount" label="折让前总额" min-width="13%"></el-table-column>
      <el-table-column fixed prop="state" label="单据状态" min-width="13%"></el-table-column>
      <el-table-column fixed prop="finalAmount" label="折让后总额" min-width="13%"></el-table-column>
      <el-table-column fixed prop="discount" label="折扣" min-width="13%"></el-table-column>
      <el-table-column fixed prop="voucherAmount" label="使用代金券总额" min-width="13%"></el-table-column>
      <el-table-column type="expand">
        <template slot-scope="props">
        <el-table
            :data="props.row.saleSheetContent"
            style="width: 100%;margin-top: 2%"
            :header-cell-style="{ 'text-align': 'center' }"
            :cell-style="{ 'text-align': 'center' }"
        >
          <el-table-column prop="pid" label="商品ID" min-width="13%"></el-table-column>
          <el-table-column prop="quantity" label="数量" min-width="13%"></el-table-column>
          <el-table-column prop="unitPrice" label="单价" min-width="13%"></el-table-column>
          <el-table-column prop="totalPrice" label="金额" min-width="13%"></el-table-column>
          <el-table-column prop="remark" label="备注" min-width="13%"></el-table-column>
        </el-table>
        </template>
      </el-table-column>

    </el-table>
    <!--    销售退货-->
    <el-table
        :data="list"
        style="width: 100%;margin-top: 2%"
        v-if="saleReturnVisible"
        :header-cell-style="{ 'text-align': 'center' }"
        :cell-style="{ 'text-align': 'center' }"
    >
      <el-table-column fixed prop="saleSheetId" label="销售单ID" min-width="13%"></el-table-column>
      <el-table-column fixed prop="operator" label="操作员" min-width="13%"></el-table-column>
      <el-table-column fixed prop="remark" label="备注" min-width="13%"></el-table-column>
      <el-table-column fixed prop="state" label="单据状态" min-width="13%"></el-table-column>
      <el-table-column fixed prop="discount" label="折扣" min-width="13%"></el-table-column>
      <el-table-column fixed prop="voucherAmount" label="使用代金券总额" min-width="13%"></el-table-column>
      <el-table-column fixed prop="totalAmount" label="总额" min-width="13%"></el-table-column>
      <el-table-column fixed prop="createTime" label="创建时间" min-width="13%"></el-table-column>
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-table
              :data="props.row.saleReturnsSheetContent"
              style="width: 100%;margin-top: 2%"
              :header-cell-style="{ 'text-align': 'center' }"
              :cell-style="{ 'text-align': 'center' }"
          >
            <el-table-column prop="id" label="ID" min-width="13%"></el-table-column>
            <el-table-column prop="saleReturnsSheetId" label="销售退货单ID" min-width="13%"></el-table-column>
            <el-table-column prop="pid" label="商品ID" min-width="13%"></el-table-column>
            <el-table-column prop="quantity" label="数量" min-width="13%"></el-table-column>
            <el-table-column prop="unitPrice" label="单价" min-width="13%"></el-table-column>
            <el-table-column prop="totalPrice" label="金额" min-width="13%"></el-table-column>
            <el-table-column prop="remark" label="备注" min-width="13%"></el-table-column>
          </el-table>
        </template>
      </el-table-column>

    </el-table>
    <!--    进货-->
    <el-table
        :data="list"
        style="width: 100%;margin-top: 2%"
        v-if="purchaseVisible"
        :header-cell-style="{ 'text-align': 'center' }"
        :cell-style="{ 'text-align': 'center' }"
    >
      <el-table-column fixed prop="id" label="ID" min-width="13%"></el-table-column>
      <el-table-column fixed prop="supplier" label="供应商ID" min-width="13%"></el-table-column>
      <el-table-column fixed prop="operator" label="操作员" min-width="13%"></el-table-column>
      <el-table-column fixed prop="remark" label="备注" min-width="13%"></el-table-column>
      <el-table-column fixed prop="totalAmount" label="总额" min-width="13%"></el-table-column>
      <el-table-column fixed prop="state" label="单据状态" min-width="13%"></el-table-column>
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-table
              :data="props.row.purchaseSheetContent"
              style="width: 100%;margin-top: 2%"
              :header-cell-style="{ 'text-align': 'center' }"
              :cell-style="{ 'text-align': 'center' }"
          >
            <el-table-column prop="id" label="ID" min-width="13%"></el-table-column>
            <el-table-column prop="purchaseSheetId" label="进货单ID" min-width="13%"></el-table-column>
            <el-table-column prop="pid" label="商品ID" min-width="13%"></el-table-column>
            <el-table-column prop="quantity" label="数量" min-width="13%"></el-table-column>
            <el-table-column prop="unitPrice" label="单价" min-width="13%"></el-table-column>
            <el-table-column prop="totalPrice" label="金额" min-width="13%"></el-table-column>
            <el-table-column prop="remark" label="备注" min-width="13%"></el-table-column>
          </el-table>
        </template>
      </el-table-column>

    </el-table>
    <!--    进货退货-->
    <el-table
        :data="list"
        style="width: 100%;margin-top: 2%"
        v-if="purchaseReturnVisible"
        :header-cell-style="{ 'text-align': 'center' }"
        :cell-style="{ 'text-align': 'center' }"
    >
      <el-table-column fixed prop="id" label="ID" min-width="13%"></el-table-column>
      <el-table-column fixed prop="purchaseSheetId" label="进货单ID" min-width="13%"></el-table-column>
      <el-table-column fixed prop="operator" label="操作员" min-width="13%"></el-table-column>
      <el-table-column fixed prop="remark" label="备注" min-width="13%"></el-table-column>
      <el-table-column fixed prop="totalAmount" label="总额" min-width="13%"></el-table-column>
      <el-table-column fixed prop="state" label="单据状态" min-width="13%"></el-table-column>
      <el-table-column fixed prop="createTime" label="创建时间" min-width="13%"></el-table-column>
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-table
              :data="props.row.purchaseReturnsSheetContent"
              style="width: 100%;margin-top: 2%"
              :header-cell-style="{ 'text-align': 'center' }"
              :cell-style="{ 'text-align': 'center' }"
          >
            <el-table-column prop="id" label="ID" min-width="13%"></el-table-column>
            <el-table-column prop="purchaseReturnSheetId" label="进货退货单ID" min-width="13%"></el-table-column>
            <el-table-column prop="pid" label="商品ID" min-width="13%"></el-table-column>
            <el-table-column prop="quantity" label="数量" min-width="13%"></el-table-column>
            <el-table-column prop="unitPrice" label="单价" min-width="13%"></el-table-column>
            <el-table-column prop="totalPrice" label="金额" min-width="13%"></el-table-column>
            <el-table-column prop="remark" label="备注" min-width="13%"></el-table-column>
          </el-table>
        </template>
      </el-table-column>

    </el-table>
    <!--    付款单-->
    <el-table
        :data="list"
        style="width: 100%;margin-top: 2%"
        v-if="payVisible"
        :header-cell-style="{ 'text-align': 'center' }"
        :cell-style="{ 'text-align': 'center' }"
    >
      <el-table-column fixed prop="id" label="ID" min-width="13%"></el-table-column>
      <el-table-column fixed prop="bankAccount" label="银行账户" min-width="13%"></el-table-column>
      <el-table-column fixed prop="customerId" label="客户ID" min-width="13%"></el-table-column>
      <el-table-column fixed prop="operator" label="操作员" min-width="13%"></el-table-column>
      <el-table-column fixed prop="totalAmount" label="总额" min-width="13%"></el-table-column>
      <el-table-column fixed prop="state" label="单据状态" min-width="13%"></el-table-column>
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-table
              :data="props.row.payableContent"
              style="width: 100%;margin-top: 2%"
              :header-cell-style="{ 'text-align': 'center' }"
              :cell-style="{ 'text-align': 'center' }"
          >
            <el-table-column prop="name" label="条目名" min-width="13%"></el-table-column>
            <el-table-column prop="sheetId" label="付款单ID" min-width="13%"></el-table-column>
            <el-table-column prop="amount" label="金额" min-width="13%"></el-table-column>
            <el-table-column prop="remark" label="备注" min-width="13%"></el-table-column>
          </el-table>
        </template>
      </el-table-column>

    </el-table>
    <!--    收款单-->
    <el-table
        :data="list"
        style="width: 100%;margin-top: 2%"
        v-if="receiveVisible"
        :header-cell-style="{ 'text-align': 'center' }"
        :cell-style="{ 'text-align': 'center' }"
    >
      <el-table-column fixed prop="id" label="ID" min-width="13%"></el-table-column>
      <el-table-column fixed prop="customerId" label="客户ID" min-width="13%"></el-table-column>
      <el-table-column fixed prop="operator" label="操作员" min-width="13%"></el-table-column>
      <el-table-column fixed prop="totalAmount" label="总额" min-width="13%"></el-table-column>
      <el-table-column fixed prop="state" label="单据状态" min-width="13%"></el-table-column>
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-table
              :data="props.row.receivableContent"
              style="width: 100%;margin-top: 2%"
              :header-cell-style="{ 'text-align': 'center' }"
              :cell-style="{ 'text-align': 'center' }"
          >
            <el-table-column prop="bankAccount" label="银行账户" min-width="13%"></el-table-column>
            <el-table-column prop="sheetId" label="收款单ID" min-width="13%"></el-table-column>
            <el-table-column prop="transferAmount" label="转账金额" min-width="13%"></el-table-column>
            <el-table-column prop="remark" label="备注" min-width="13%"></el-table-column>
          </el-table>
        </template>
      </el-table-column>

    </el-table>
    <!--    工资单-->
    <el-table
        :data="list"
        style="width: 100%;margin-top: 2%"
        v-if="salaryVisible"
        :header-cell-style="{ 'text-align': 'center' }"
        :cell-style="{ 'text-align': 'center' }"
    >
      <el-table-column prop="id" label="ID" min-width="13%"></el-table-column>
      <el-table-column prop="staffId" label="员工ID" min-width="13%"></el-table-column>
      <el-table-column prop="name" label="姓名" min-width="13%"></el-table-column>
      <el-table-column prop="role" label="工作岗位" min-width="13%"></el-table-column>
      <el-table-column prop="operator" label="操作员" min-width="13%"></el-table-column>
      <el-table-column prop="staffBankAccount" label="银行账户" min-width="13%"></el-table-column>
      <el-table-column prop="basicAmount" label="基本工资" min-width="13%"></el-table-column>
      <el-table-column prop="positionAmount" label="岗位工资" min-width="13%"></el-table-column>
      <el-table-column prop="rawAmount" label="应发工资" min-width="13%"></el-table-column>
      <el-table-column prop="taxAmount" label="税款" min-width="13%"></el-table-column>
      <el-table-column prop="pushMoney" label="提成" min-width="13%"></el-table-column>
      <el-table-column prop="absenceTimes" label="缺勤次数" min-width="13%"></el-table-column>
      <el-table-column prop="annualBonus" label="年终奖" min-width="13%"></el-table-column>
      <el-table-column prop="finalAmount" label="实发金额" min-width="13%"></el-table-column>
      <el-table-column prop="paymentSchedule" label="薪资发放方式" min-width="13%"></el-table-column>
      <el-table-column prop="state" label="单据状态" min-width="13%"></el-table-column>
    </el-table>

  </Layout>
</template>

<script>
import Layout from "@/components/content/Layout";
import Title from "@/components/content/Title";
import {getAllCustomer, getSalesAccount} from "@/network/business";
import { getAllUsers } from "@/network/business";
import { getBusinessProcess } from "@/network/business";
import { getStaff } from "@/network/business";

export default {
  name: "BusinessProcess",
  components: {Title,Layout},
  data() {
    return {
      startTime: null,
      endTime: null,
      sheetType: null,
      customer: null,
      operator: null,
      people: null,


      list: [],
      saleVisible: false,
      saleReturnVisible: false,
      purchaseVisible: false,
      purchaseReturnVisible: false,
      payVisible: false,
      receiveVisible: false,
      salaryVisible: false,

      types: ["销售单", "销售退货单", "进货单", "进货退货单", "付款单", "收款单", "工资单"],
      customers: [],
      users: [],
      peoples: []
    }
  },
  mounted() {
    getAllCustomer({}).then(_res => {
      this.customers = [];
      this.customers = _res.result;
    })
    getAllUsers({}).then(_res => {
      this.users = [];
      this.users = _res.result
    })
    getStaff({}).then(_res => {
      this.peoples = [];
      this.peoples = _res.result;
    })
  },
  methods: {
    search() {
      if(this.startTime == null || this.endTime == null || this.sheetType == null || (this.customer == null && this.people == null) || this.operator == null){
        this.$message.error('有未选择的筛选条件！');
        return;
      }

      this.list = [];
      this.saleVisible = false;
      this.saleReturnVisible = false;
      this.purchaseVisible = false;
      this.purchaseReturnVisible = false;
      this.payVisible = false;
      this.receiveVisible = false;
      this.salaryVisible = false;

      var startT = this.dateToString(this.startTime);
      var endT = this.dateToString(this.endTime);
      var p = '';
      if(this.sheetType === '工资单')
        p = this.people;
      else
        p = this.customer;
      getBusinessProcess({ params: { sheetType: this.sheetType, startTime: startT, endTime: endT, supplier: p, salesman: this.operator
        }}).then(_res => {
        let res = _res.result;
        this.list = [];
        switch (this.sheetType){
          case "销售单":
            this.saleVisible = true;
            for(var i=0; i<res.length; i++){
              var tempList = [];
              for(var j=0; j<res[i].saleSheetContent.length; j++){
                tempList.push({
                  pid: res[i].saleSheetContent[j].pid,
                  quantity: res[i].saleSheetContent[j].quantity,
                  unitPrice: res[i].saleSheetContent[j].unitPrice,
                  totalPrice: res[i].saleSheetContent[j].totalPrice,
                  remark: res[i].saleSheetContent[j].remark
                })
              }
              this.list.push({
                id: res[i].id,
                supplier: res[i].supplier,
                salesman: res[i].salesman,
                operator: res[i].operator,
                remark: res[i].remark,
                rawTotalAmount: res[i].rawTotalAmount,
                state: res[i].state,
                finalAmount: res[i].finalAmount,
                discount: res[i].discount,
                voucherAmount: res[i].voucherAmount,
                saleSheetContent: tempList
              })
            }
            break;
          case "销售退货单":
            this.saleReturnVisible = true;
            for(var i2=0; i2<res.length; i2++){
              var tempList2 = [];
              for(var j2=0; j2<res[i2].saleReturnsSheetContent.length; j2++){
                tempList2.push({
                  id: res[i2].saleReturnsSheetContent[j2].id,
                  saleReturnsSheetId: res[i2].saleReturnsSheetContent[j2].saleReturnsSheetId,
                  pid: res[i2].saleReturnsSheetContent[j2].pid,
                  quantity: res[i2].saleReturnsSheetContent[j2].quantity,
                  unitPrice: res[i2].saleReturnsSheetContent[j2].unitPrice,
                  totalPrice: res[i2].saleReturnsSheetContent[j2].totalPrice,
                  remark: res[i2].saleReturnsSheetContent[j2].remark
                })
              }
              this.list.push({
                saleSheetId: res[i2].saleSheetId,
                operator: res[i2].operator,
                remark: res[i2].remark,
                state: res[i2].state,
                discount: res[i2].discount,
                voucherAmount: res[i2].voucherAmount,
                totalAmount: res[i2].totalAmount,
                createTime: res[i2].createTime,
                saleReturnsSheetContent: tempList2
              })
            }
            break;
          case "进货单":
            this.purchaseVisible = true;
            for(var i3=0; i3<res.length; i3++){
              var tempList3 = [];
              for(var j3=0; j3<res[i3].purchaseSheetContent.length; j3++){
                tempList3.push({
                  id: res[i3].purchaseSheetContent[j3].id,
                  purchaseSheetId: res[i3].purchaseSheetContent[j3].purchaseSheetId,
                  pid: res[i3].purchaseSheetContent[j3].pid,
                  quantity: res[i3].purchaseSheetContent[j3].quantity,
                  unitPrice: res[i3].purchaseSheetContent[j3].unitPrice,
                  totalPrice: res[i3].purchaseSheetContent[j3].totalPrice,
                  remark: res[i3].purchaseSheetContent[j3].remark
                })
              }
              this.list.push({
                id: res[i3].id,
                supplier: res[i3].supplier,
                operator: res[i3].operator,
                remark: res[i3].remark,
                state: res[i3].state,
                totalAmount: res[i3].totalAmount,
                purchaseSheetContent: tempList3
              })
            }
            break;
          case "进货退货单":
            this.purchaseReturnVisible = true;
            for(var i4=0; i4<res.length; i4++){
              var tempList4 = [];
              for(var j4=0; j4<res[i4].purchaseReturnsSheetContent.length; j4++){
                tempList4.push({
                  id: res[i4].purchaseReturnsSheetContent[j4].id,
                  purchaseReturnsSheetId: res[i4].purchaseReturnsSheetContent[j4].purchaseReturnsSheetId,
                  pid: res[i4].purchaseReturnsSheetContent[j4].pid,
                  quantity: res[i4].purchaseReturnsSheetContent[j4].quantity,
                  unitPrice: res[i4].purchaseReturnsSheetContent[j4].unitPrice,
                  totalPrice: res[i4].purchaseReturnsSheetContent[j4].totalPrice,
                  remark: res[i4].purchaseReturnsSheetContent[j4].remark
                })
              }
              this.list.push({
                id: res[i4].id,
                purchaseSheetId: res[i4].purchaseSheetId,
                operator: res[i4].operator,
                remark: res[i4].remark,
                state: res[i4].state,
                totalAmount: res[i4].totalAmount,
                createTime: res[i4].createTime,
                purchaseReturnsSheetContent: tempList4
              })
            }
            break;
          case "付款单":
            this.payVisible = true;
            for(var i5=0; i5<res.length; i5++){
              var tempList5 = [];
              for(var j5=0; j5<res[i5].payableContent.length; j5++){
                tempList5.push({
                  sheetId: res[i5].payableContent[j5].sheetId,
                  name: res[i5].payableContent[j5].name,
                  amount: res[i5].payableContent[j5].amount,
                  remark: res[i5].payableContent[j5].remark
                })
              }
              this.list.push({
                id: res[i5].id,
                bankAccount: res[i5].bankAccount,
                customerId: res[i5].customerId,
                operator: res[i5].operator,
                state: res[i5].state,
                totalAmount: res[i5].totalAmount,
                payableContent: tempList5
              })
            }
            break;
          case "收款单":
            this.receiveVisible = true;
            for(var i6=0; i6<res.length; i6++){
              var tempList6 = [];
              for(var j6=0; j6<res[i6].receivableContent.length; j6++){
                tempList6.push({
                  sheetId: res[i6].receivableContent[j6].sheetId,
                  bankAccount: res[i6].receivableContent[j6].bankAccount,
                  transferAmount: res[i6].receivableContent[j6].transferAmount,
                  remark: res[i6].receivableContent[j6].remark
                })
              }
              this.list.push({
                id: res[i6].id,
                customerId: res[i6].customerId,
                operator: res[i6].operator,
                state: res[i6].state,
                totalAmount: res[i6].totalAmount,
                receivableContent: tempList6
              })
            }
            break;
          case "工资单":
            this.salaryVisible = true;
            for(var i7=0; i7<res.length; i7++){
              this.list.push({
                id: res[i7].id,
                staffId: res[i7].staffId,
                name: res[i7].name,
                role: res[i7].role,
                operator: res[i7].operator,
                staffBankAccount: res[i7].staffBankAccount,
                basicAmount: res[i7].basicAmount,
                positionAmount: res[i7].positionAmount,
                rawAmount: res[i7].rawAmount,
                taxAmount: res[i7].taxAmount,
                pushMoney: res[i7].pushMoney,
                absenceTime: res[i7].absenceTime,
                annualBonus: res[i7].annualBonus,
                finalAmount: res[i7].finalAmount,
                paymentSchedule: res[i7].paymentSchedule,
                state: res[i7].state
              })
            }
        }

      })

    },
    dateToString(date) {
      var y = date.getFullYear();
      var m_temp = date.getMonth() + 1;
      var m = '';
      if(Number(m_temp)<10)
        m = "0" + m_temp;
      else
        m = m_temp;
      var d_temp = date.getDate();
      var d = '';
      if(Number(d_temp)<10)
        d = "0" + d_temp;
      else
        d = d_temp;
      return y + "-" + m + "-" + d;
    },
  }
}
</script>

<style scoped>

</style>