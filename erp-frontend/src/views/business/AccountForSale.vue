<template>
  <Layout>
    <Title title="销售明细表"></Title>
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
        <el-select v-model="name" placeholder="请选择商品名">
          <el-option
              v-for="item in commodities"
              :key="item.name"
              :label="item.name"
              :value="item.name">
          </el-option>
        </el-select>
      </el-col>
      <el-col :span="6" style="margin-top: 2%">
        <el-select v-model="customer" placeholder="请选择客户ID">
          <el-option
              v-for="item in customers"
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
      <el-col :span="2" style="margin-top: 2%"><download-excel
          class = "export-excel-wrapper"
          :data = "DetailsForm"
          :fields = "json_fields"
          :name = "today">
        <el-button @click="exportAsExcel">导出Excel</el-button>
      </download-excel>
      </el-col>
    </el-row>
    <el-table
        :data="accountList"
        style="width: 100%;margin-top: 2%"
        :header-cell-style="{ 'text-align': 'center' }"
        :cell-style="{ 'text-align': 'center' }"
    >
      <el-table-column fixed prop="theDay" label="时间" min-width="13%">
      </el-table-column>
      <el-table-column prop="name" label="商品名" min-width="13%">
      </el-table-column>
      <el-table-column prop="type" label="型号" min-width="13%" >
      </el-table-column>
      <el-table-column prop="quantity" label="数量" min-width="13%" >
      </el-table-column>
      <el-table-column prop="unitPrice" label="单价" min-width="13%" >
      </el-table-column>
      <el-table-column prop="totalPrice" label="总金额" min-width="13%" >
      </el-table-column>
    </el-table>
  </Layout>
</template>

<script>
import Layout from "@/components/content/Layout";
import Title from "@/components/content/Title";
import { getAllCustomer} from "@/network/business";
import { getAllCommodity } from '@/network/commodity'
import { getSalesAccount} from '@/network/business';
import { getAllUsers } from "@/network/business";

export default {
  name: "AccountForSale",
  components: {Title,Layout},
  data() {
    return {
      startTime: null,
      endTime: null,
      name: null,
      customer: null,
      operator: null,

      customers:[],
      commodities:[],
      users:[],
      accountList: [],

      today: new Date().toLocaleDateString() + "销售明细表",
      json_fields: {
        '时间':'theDay',
        '商品名':'name',
        '型号':'type',
        '数量':'quantity',
        '单价':'unitPrice',
        '总金额':'totalPrice',
      },
      DetailsForm: [
      ]
    }
  },
  mounted() {
    getAllCustomer({}).then(_res => {
      this.customers = [];
      this.customers = _res.result;
    })
    getAllCommodity({}).then(_res => {
      let res = _res.result;
      this.commodities = [];
      res.forEach(item => this.commodities.push({ name: item.name }))
    })
    getAllUsers({}).then(_res => {
      this.users = [];
      this.users = _res.result
    })
  },
  methods: {
    exportAsExcel() {

      this.DetailsForm = this.accountList;
      console.log(this.today);
      alert("导出成Excel");
    },
    search() {
      if(this.startTime == null || this.endTime == null || this.name == null || this.customer == null || this.operator == null){
        this.$message.error('有未选择的筛选条件！');
        return;
      }
      // console.log(this.startTime);
      var startT = this.dateToString(this.startTime);
      // console.log(startT);
      var endT = this.dateToString(this.endTime);
      getSalesAccount({ params: { startTime: startT, endTime: endT, name: this.name, supplier: this.customer, salesman: this.operator
        }}).then(_res => {
        let res = _res.result;
        this.accountList = [];
        res.forEach(item => this.accountList.push({
          theDay: item.theDay.substring(0,10),
          name: item.name,
          type: item.type,
          quantity: item.quantity,
          unitPrice: item.unitPrice,
          totalPrice: item.totalPrice
        }))
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