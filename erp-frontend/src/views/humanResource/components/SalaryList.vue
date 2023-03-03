<template>
  <div class="card" style="margin: 10px">
    <el-card v-for="(item, index) in list" :index="item.index" :key="item.id" shadow="hover">
      <template #header>
        <el-row>
          <el-col :span="18">
            <span><strong>工资单编号: </strong> {{item.id}}</span>
            <el-button v-if="authorization() === 1" style="margin-left: 10px"
              type="success" icon="el-icon-check" circle size="mini" @click="approval(item.id)"></el-button>
            <el-button v-if="authorization() === 1"
              type="danger" icon="el-icon-close" circle size="mini" @click="deny(item.id)"></el-button>
            <span style="margin-left: 10px">
              <el-tag v-if="type === 'SUCCESS'" effect="dark" type='success'>审核通过</el-tag>
              <el-tag v-if="type === 'FAILURE'" effect="dark" type='danger'>审核未通过</el-tag>
            </span>
          </el-col>
         <el-col :span="6">
           <el-button class="button" type="text"
                      v-if="!showAll[index]"
                      @click="changeState(index)">
             展开
           </el-button>
           <el-button class="button" type="text"
                      v-if="showAll[index]"
                      @click="changeState(index)">
             收起</el-button>
         </el-col>
        </el-row>
      </template>
      <div>
        <el-row>
          <el-col :span="3">
            <span><strong>员工ID:</strong>{{item.staffId}}</span>
          </el-col>
          <el-col :span="3">
            <span><strong>姓名:</strong>{{item.name}}</span>
          </el-col>
          <el-col :span="3">
            <span><strong>工作岗位:</strong>{{item.role}}</span>
          </el-col>
          <el-col :span="9">
            <span><strong>银行账户:</strong>{{item.staffBankAccount}}</span>
          </el-col>
        </el-row>
        <div v-if="showAll[index]">
          <el-row style="margin-top: 15px">
            <el-col :span="3">
              <span><strong>操作员:</strong>{{item.operator}}</span>
            </el-col>
            <el-col :span="3">
              <span><strong>基本工资:</strong>{{item.basicAmount}}(元)</span>
            </el-col>
            <el-col :span="3">
              <span><strong>岗位工资:</strong>{{item.positionAmount}}(元)</span>
            </el-col>
            <el-col :span="3">
              <span><strong>提成:</strong>{{item.pushMoney}}</span>
            </el-col>
            <el-col :span="3">
              <span><strong>应发工资:</strong>{{item.rawAmount}}(元)</span>
            </el-col>
            <el-col :span="3">
              <span><strong>税款(年):</strong>{{item.taxAmount}}(元)</span>
            </el-col>
          </el-row>
          <el-row style="margin-top: 15px">
            <el-col :span="3">
              <span><strong>缺勤次数:</strong>{{item.absenceTimes}}(次)</span>
            </el-col>
            <el-col :span="3">
              <span><strong>年终奖:</strong>{{item.annualBonus}}(元)</span>
            </el-col>
            <el-col :span="6">
              <span><strong>薪资发放方式:</strong>{{item.paymentSchedule}}</span>
            </el-col>
          </el-row>
        </div>

      </div>
    </el-card>
  </div>
</template>

<script>


import {SECOND_APPROVAL} from "@/network/humanResource";

export default{
  name:"SalaryList",
  props:{
    list: Array,
    type: String,
  },
  data(){
    return{
      showAll:[],
    }
  },
  mounted() {
    this.showAll = new Array(this.list.length).fill(false)
  },
  methods: {
    changeState(index) {
      this.$set(this.showAll, index, !this.showAll[index])
    },
    authorization(){
      if(this.type === 'PENDING_LEVEL_2' && sessionStorage.getItem('role') === 'GM'){
        return 1
      }
    }
  ,
    approval(id) {

      let config = {
        params: {
          SalarySheetId: id,
          state: '审批完成'
        }
      }
      SECOND_APPROVAL(config).then(
        this.$notify({
          title: '成功',
          message: '操作成功',
          type: 'success'
        })
      )

    },
    deny(id) {
      let config = {
        params: {
          SalarySheetId: id,
          state: '审批失败'
        }
      }
      SECOND_APPROVAL(config).then(
          this.$notify({
            title: '成功',
            message: '操作成功',
            type: 'success'
          })
      )
    }
  }
}
</script>