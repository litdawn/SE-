<template>
  <Layout>
    <Title title="赠品单审批"></Title>
    <div class="body">
      <el-tabs v-model="activeName" :stretch="true">
        <el-tab-pane label="待总经理审批" name="PENDING_LEVEL_2">
          <div v-if="pendingLevel2List.length === 0">
            <el-empty description="暂无数据"></el-empty>
          </div>
          <div v-else>
            <discount-approval-list :list="pendingLevel2List" @refresh="getSalary()"/>
          </div>
        </el-tab-pane>
        <el-tab-pane label="审批完成" name="SUCCESS">
          <div v-if="successList.length === 0">
            <el-empty description="暂无数据"></el-empty>
          </div>
          <div v-else>
            <discount-approval-list :list="successList" @refresh="getSalary()"/>
          </div>
        </el-tab-pane>
        <el-tab-pane label="审批失败" name="FAILURE">
          <div v-if="failureList.length === 0">
            <el-empty description="暂无数据"></el-empty>
          </div>
          <div v-else>
            <discount-approval-list :list="failureList" @refresh="getSalary()"/>
          </div>
        </el-tab-pane>
      </el-tabs>
      <!--          <SalaryList :list="salaryList" type="3"/>-->
    </div>
  </Layout>
</template>

<script>
import Layout from "@/components/content/Layout";
import Title from "@/components/content/Title";
import DiscountApprovalList from "@/views/approval/component/discountApprovalList";
import { GET_GIFT_SHEET } from "@/network/approve";
import request from "@/network/request";
export default {
  name:'Salary',
  components: {
    DiscountApprovalList,
    Title,
    Layout,
  },
  created() {
    this.load()
  },
  data() {
    return {
      activeName:'SUCCESS',
      pendingLevel2List: [],
      successList: [],
      failureList: [],
      discountList: []
    };
  },
  methods: {
    load(){
      this.discountList = [];
      this.pendingLevel2List = [];
      this.successList = [];
      this.failureList = [];
      request._get('/api/promotion/getGiftSheetByState',{
        params:{
          state: 'SUCCESS'
        }}).then(_res => {
            console.log(_res.result)
              this.successList = this.successList.concat(_res.result)
            })
      request._get('/api/promotion/getGiftSheetByState',{
        params:{
          state: 'FAILURE'
        }}).then(_res => {
        this.failureList = this.failureList.concat(_res.result)
      })
      request._get('/api/promotion/getGiftSheetByState',{
        params:{
          state: 'PENDING_LEVEL_2'
        }}).then(_res => {
        this.pendingLevel2List = this.pendingLevel2List.concat(_res.result)
      })
      // await GET_GIFT_SHEET({ params:{ state: '审批成功'} }).then(_res => {
      //   this.successList = this.successList.concat(_res.result)
      // })
      // await GET_GIFT_SHEET({ params:{ state: '审批失败'} }).then(_res => {
      //   this.failureList = this.failureList.concat(_res.result)
      // })
      // await GET_GIFT_SHEET({ params:{ state: '待二级审批'} }).then(_res => {
      //   this.pendingLevel2List = this.pendingLevel2List.concat(_res.result)
      // })
    },
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