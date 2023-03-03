<template>
  <Layout>
    <Title title="年终奖制定"></Title>
    <el-button type="primary" size="medium" @click="changeState">制定</el-button>
    <div style="margin-top: 10px">
      <el-table
        :data="bonusList"
        stripe
        style="width: 100%"
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
            prop="annualAmount"
            label="年终奖金"
            width="150">
        </el-table-column>
      </el-table>
    </div>

    <el-dialog style="width: 1000px"
        title="制定年终奖" :visible.sync="dialogVisible1">
      <el-form ref="form"  label-width="100px">
        <el-form-item label="员工ID:">
          <el-input style="width: 60%"
                    v-model="id"></el-input>
        </el-form-item>
        <div style="margin-left:  50%; margin-top: 20%">
          <el-button @click="dialogVisible1 = false">取 消</el-button>
          <el-button type="primary" @click="searchPrev">继 续</el-button>
        </div>
      </el-form>
    </el-dialog>

    <el-dialog style="width: 1000px"
               title="制定年终奖" :visible.sync="dialogVisible2">
      <el-form ref="form"  label-width="100px">
        <el-form-item label="员工ID:">
          <el-input style="width: 60%" disabled
                    v-model="id"></el-input>
        </el-form-item>
        <el-form-item label="前11月工资:">
          <el-input style="width: 60%" disabled
                    v-model="prevSal"></el-input>
        </el-form-item>
        <el-form-item label="年终奖金:">
          <el-input-number style="width: 60%"
                    v-model="form.annualAmount"></el-input-number>
        </el-form-item>
        <div style="margin-left:  50%; margin-top: 20%">
          <el-button @click="dialogVisible2 = false">取 消</el-button>
          <el-button type="primary" @click="makeBonus">确 定</el-button>
        </div>
      </el-form>
    </el-dialog>

  </Layout>
</template>

<script>
import Layout from "@/components/content/Layout";
import Title from "@/components/content/Title";
import {GET_ANNUAL_AMOUNT, MAKE_ANNUAL_BONUS, SHOW_ALL_SHEET, SHOW_PREV_SALARY} from "@/network/bonus";
export default {
  components: {
    Layout,
    Title
  },
  // created() {
  //   this.load()
  // },
  data() {
    return {
      bonusList: [],
      dialogVisible1: false,
      dialogVisible2: false,
      form: {},
      isNew: false,
      prevSal: null,
      id:null,
    }
  },
  mounted() {
    this.bonusList = []
    SHOW_ALL_SHEET().then(_res=>{
      this.bonusList = this.bonusList.concat(_res.result)
      for(let i=0;i<this.bonusList.length;i++){
        let id = this.bonusList[i].staffId;
        GET_ANNUAL_AMOUNT({params:{staffId: id}}).then(_res =>{
          console.log(_res.result)
          this.bonusList[i].annualAmount = _res.result
        })
      }
    })
  },
  methods:{
    // load(){
    //   this.bonusList = []
    //   SHOW_ALL_SHEET().then(_res=>{
    //     this.bonusList = this.bonusList.concat(_res.result)
    //     for(let i=0;i<this.bonusList.length;i++){
    //       let id = this.bonusList[i].staffId;
    //       GET_ANNUAL_AMOUNT({params:{staffId: id}}).then(_res =>{
    //         console.log(_res.result)
    //         this.bonusList[i].annualAmount = _res.result
    //       })
    //     }
    //   })

    // },
    changeState(){
      this.dialogVisible1 = true;
      this.form = {};
    },
    searchPrev(){
        SHOW_PREV_SALARY({params:{ staffId:this.id }}).then(_res => {
          this.prevSal = _res.result
        })
      this.dialogVisible1 = false
      this.form.staffId = this.id
      this.dialogVisible2 = true
    },
    filterTag(value, row){
      return row.type === value
    },
    makeBonus(){
      MAKE_ANNUAL_BONUS({params:{
        staffId: this.form.staffId,
        annualAmount: this.form.annualAmount
      }})
      this.dialogVisible2 = false
      this.$message.success("操作成功")
    }
  }
};
</script>

<style scoped>
</style>
