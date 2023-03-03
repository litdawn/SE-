<template>
  <Layout>
    <Title title="经营情况表"></Title>
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
      <el-col :span="2">
        <el-button icon="el-icon-search" @click="search"></el-button>
      </el-col>
    </el-row>
    <el-table
        :data="condition"
        style="width: 100%;margin-top: 2%"
        :header-cell-style="{ 'text-align': 'center' }"
        :cell-style="{ 'text-align': 'center' }"
    >
      <el-table-column fixed prop="finalAmount" label="折让后总收入" min-width="13%">
      </el-table-column>
      <el-table-column prop="discountAmount" label="折让" min-width="13%">
      </el-table-column>
      <el-table-column prop="expenditureAmount" label="工资进货总额" min-width="13%" >
      </el-table-column>
      <el-table-column prop="profit" label="利润" min-width="13%" >
      </el-table-column>
    </el-table>
  </Layout>
</template>

<script>
import Layout from "@/components/content/Layout";
import Title from "@/components/content/Title";
import { getBusinessCondition } from "@/network/business";

export default {
  name: "BusinessCondition",
  components: {Title,Layout},
  data() {
    return {
      startTime: null,
      endTime: null,
      condition: [],
    }
  },
  methods: {
    search() {
      if(this.startTime == null || this.endTime == null ){
        this.$message.error('有未选择的筛选条件！');
        return;
      }
      var startT = this.dateToString(this.startTime);
      var endT = this.dateToString(this.endTime);
      getBusinessCondition({params: { startTime: startT, endTime: endT }}).then(_res => {
        let res = _res.result;
        this.condition = [];
        this.condition.push({
          finalAmount: res.finalAmount,
          discountAmount: res.discountAmount,
          expenditureAmount: res.expenditureAmount,
          profit: res.profit
        })
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