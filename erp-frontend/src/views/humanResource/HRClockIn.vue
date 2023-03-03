<template>
  <Layout>
    <Title title="员工打卡"></Title>
    <el-button type="primary" size="medium" @click="changeState();this.dat='';this.staff_id=''">查询员工</el-button>
    <div style="margin-top: 10px">
      <el-date-picker
          style="margin-top: 5px; margin-bottom: 5px"
          v-model="dat"
          type="month"
          placeholder="选择月">
      </el-date-picker>
      <el-button style="margin-left: 5px" @click="search()">确定</el-button>
      <el-table
          :data="attendanceList"
          stripe
          style="width: 70%"
          :header-cell-style="{'text-align':'center'}"
          :cell-style="{'text-align':'center'}">
        <el-table-column
            prop="staffId"
            label="id"
            width="150">
        </el-table-column>
        <el-table-column
            prop="name"
            label="姓名"
            width="150">
        </el-table-column>
        <el-table-column
            prop="role"
            label="职位"
            width="150">
        </el-table-column>
        <el-table-column
            prop="date"
            label="打卡时间"
            width="400">
        </el-table-column>
      </el-table>
    </div>

    <el-dialog  style="width: 60%" title="查询员工打卡情况" :visible.sync="dialogVisible">
      <div style="margin: 10px">
        <span>员工id:&nbsp;&nbsp;&nbsp;&nbsp;</span>
        <el-input style="width: 48%"
                  v-model="staff_id" placeholder="请输入员工id"></el-input>
      </div>

      <div class="block" style="margin-left: 10px">
        <span class="demonstration">日期:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
        <el-date-picker
            v-model="dat"
            type="month"
            placeholder="选择月">
        </el-date-picker>
        <el-button  style="margin-left: 300px;margin-top: 50px" type="primary" @click="searchEmp()">确 定</el-button>
      </div>

    </el-dialog>
  </Layout>
</template>

<script>
import Layout from "@/components/content/Layout";
import Title from "@/components/content/Title";
import {FIND_ALL_ATTENDANCE_RECORD,FIND_SOMEBODY_ATTENDANCE_RECORD} from "@/network/humanResource";

export default {
  name: 'HRClockIn',
  components: {
    Layout,
    Title
  },
  data() {
    return {
      attendanceList: [],
      dialogVisible: false,
      form: {},
      isNew: false,
      staff_id: '',
      dat: '',

    }
  },

  created() {
    this.load()
  },
  methods: {
    async load(){
      this.attendanceList = [];
      await FIND_ALL_ATTENDANCE_RECORD({params:{
          yearAndMonth : this.format(new Date())
        }
      }).then(_res => {
        // console.log(_res)
        this.attendanceList = this.attendanceList.concat(_res.result);
      })
    },
    filterTag(value, row) {
      return row.type === value
    },
    changeState(){
      this.dialogVisible = true;
    },
    searchEmp(){
      let yearAndMonth = this.format(this.dat);
      FIND_SOMEBODY_ATTENDANCE_RECORD({params:{
        yearAndMonth: yearAndMonth,
        staffId: this.staff_id
        }}).then(_res =>{
          this.attendanceList = _res.result
      })
      this.dialogVisible = false
    },
    format(dat){
      let year = dat.getFullYear();
      let mon = (dat.getMonth() + 1) < 10 ? "0" + (dat.getMonth() + 1) : dat.getMonth() + 1;
      return year + "-" + mon;
    },
    search(){
      this.attendanceList = [];
      let yearAndMonth = this.format(this.dat);
      FIND_ALL_ATTENDANCE_RECORD({params:{yearAndMonth : yearAndMonth}})
          .then(_res => {
              this.attendanceList = this.attendanceList.concat(_res.result);
          })
      this.dialogVisible = false
      }
  }
}
</script>

<style>

</style>