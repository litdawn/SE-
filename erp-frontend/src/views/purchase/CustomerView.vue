<template>
  <Layout>
    <Title title="客户管理"></Title>
    <el-button type="primary" size="medium" @click="addCustomer">新增客户</el-button>
    <div style="margin-top: 10px">
      <el-table
        :data="customerList"
        stripe
        style="width: 100%"
        :header-cell-style="{'text-align':'center'}"
        :cell-style="{'text-align':'center'}">
        <el-table-column
          prop="id"
          label="id"
          width="60">
        </el-table-column>
        <el-table-column
          prop="name"
          label="姓名"
          width="70">
        </el-table-column>
        <el-table-column
          prop="type"
          label="类型"
          width="100"
          :filters="[{ text: '供应商', value: '供应商' }, { text: '销售商', value: '销售商' }]"
          :filter-method="filterTag"
          filter-placement="bottom-end">
          <template slot-scope="scope">
            <el-tag
              :type="scope.row.type === '供应商' ? 'primary' : 'success'"
              disable-transitions>{{scope.row.type}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column
          prop="level"
          label="级别"
          width="50">
        </el-table-column>
        <el-table-column
          prop="phone"
          label="电话"
          width="150">
        </el-table-column>
        <el-table-column
          prop="address"
          label="地址"
          width="150">
        </el-table-column>
        <el-table-column
          prop="zipcode"
          label="邮编"
          width="100">
        </el-table-column>
        <el-table-column
          prop="email"
          label="邮箱"
          width="200">
        </el-table-column>
        <el-table-column
          prop="lineOfCredit"
          label="应收额度(元)"
          width="120">
        </el-table-column>
        <el-table-column
          prop="receivable"
          label="应收(元)"
          width="120">
        </el-table-column>
        <el-table-column
          prop="payable"
          label="应付(元)"
          width="120">
        </el-table-column>
        <el-table-column
          prop="operator"
          label="操作员"
          width="120">
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

      <el-dialog title="客户信息" :visible.sync="dialogVisible">
        <el-form ref="form" :model="form" label-width="100px">
          <el-form-item label="客户id">
            <el-input v-model="form.id"></el-input>
          </el-form-item>
          <el-form-item label="客户姓名">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="客户类型">
            <el-select v-model="form.type">
              <el-option label="供应商" value="供应商"></el-option>
              <el-option label="销售商" value="销售商"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="客户级别">
            <el-input v-model="form.level"></el-input>
          </el-form-item>
          <el-form-item label="客户电话">
            <el-input v-model="form.phone"></el-input>
          </el-form-item>
          <el-form-item label="客户地址">
            <el-input v-model="form.address"></el-input>
          </el-form-item>
          <el-form-item label="客户邮编">
            <el-input v-model="form.zipcode"></el-input>
          </el-form-item>
          <el-form-item label="客户邮箱">
            <el-input v-model="form.email"></el-input>
          </el-form-item>
          <el-form-item label="客户应收额度(元)">
            <el-input v-model="form.line_of_credit"></el-input>
          </el-form-item>
          <el-form-item label="客户应收(元)">
            <el-input v-model="form.receivable"></el-input>
          </el-form-item>
          <el-form-item label="客户应付(元)">
            <el-input v-model="form.payable"></el-input>
          </el-form-item>
          <el-form-item label="操作员">
            <el-input v-model="form.operator"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="save">确 定</el-button>
  </span>
      </el-dialog>

    </div>
  </Layout>
</template>

<script>
import Layout from "@/components/content/Layout";
import Title from "@/components/content/Title";
import { getAllCustomer } from "@/network/purchase";
import request from "@/network/request";
export default {
  name: 'CustomerView',
  components: {
    Layout,
    Title
  },
  data() {
    return {
      customerList: [],
      dialogVisible: false,
      form: {},
      isNew: false
    }
  },
  // async mounted() {
  //   await getAllCustomer({ params : { type: 'SUPPLIER' } }).then(_res => {
  //     this.customerList = this.customerList.concat(_res.result)
  //   })
  //   await getAllCustomer({ params : { type: 'SELLER' } }).then(_res => {
  //     this.customerList = this.customerList.concat(_res.result)
  //   })
  // },
  created() {
    this.load()
  },
  methods: {
    async load(){
      this.customerList = [];
      await getAllCustomer({ params : { type: 'SUPPLIER' } }).then(_res => {
        this.customerList = this.customerList.concat(_res.result)
      })
      await getAllCustomer({ params : { type: 'SELLER' } }).then(_res => {
        this.customerList = this.customerList.concat(_res.result)
      })
    },
    filterTag(value, row) {
      return row.type === value
    },
    addCustomer() {
      // TODO: 新增客户
      this.dialogVisible = true;
      this.isNew = true;
      this.form= {}
    },
    save(){
      // this.customerList.push(this.form)
      // this.dialogVisible = false;
      if(this.isNew){
        request._get('/api/customer/addNewCustomer',{
          params:{
            id: parseInt(this.form.id),
            type: this.form.type,
            level: parseInt(this.form.level),
            name: this.form.name,
            phone: this.form.phone,
            address: this.form.address,
            zipcode: this.form.zipcode,
            email: this.form.email,
            line_of_credit: this.form.line_of_credit,
            receivable: this.form.receivable,
            payable: this.form.payable,
            operator: this.form.operator
          }
        }).then(res=>{
          console.log(res)
          this.load();
          this.dialogVisible = false;
          this.$message({
            type:"success",
            message:"更新成功"
          })
        })
      }else{
        // 暂时未要求更新用户
        // request._put("api/customer/updateCustomer",{
        //   id: this.form.id,
        //   type: this.form.type,
        //   level: this.form.level,
        //   name: this.form.name,
        //   phone: this.form.phone,
        //   address: this.form.address,
        //   zipcode: this.form.zipcode,
        //   email: this.form.email,
        //   line_of_credit: this.form.line_of_credit,
        //   receivable: this.form.receivable,
        //   payable: this.form.payable,
        //   operator: this.form.operator
        // })
        //     .then(res =>{
        //       console.log(res)
        //       this.load();
        //       this.dialogVisible = false;
        //       this.$message({
        //         type:"success",
        //         message:"添加成功"
        //       })
        //     })
      }

    },
    editInfo(row) {
      // TODO: 修改客户信息
      // alert(`TODO: 修改${id}客户信息`)
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true;
      this.isNew = false;
    },
    deleteInfo(row){
      this.form = JSON.parse(JSON.stringify(row))
      this.$confirm('将永久删除客户，是否继续？','提示',{
        confirmButtonText:'确定',
        cancelButtonText:'取消',
        type:'warning'
      }).then(()=>{
        request._get("/api/customer/deleteCustomer",{
          params:{
            id: parseInt(this.form.id)
          }
        })
        this.load();
        this.$message({
          type: 'success',
          message: '删除成功!'
        });
      }).catch(()=>{
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      })

    }
  }
}
</script>

<style>

</style>