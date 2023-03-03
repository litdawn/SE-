<template>
  <div class="card" style="margin: 10px">
    <el-card v-for="item in list" :index="item.index" :key="item.id" shadow="hover">
      <template #header>
        <el-row>
          <el-col :span="18">
            <span><strong>赠品单编号: </strong> {{item.id}}</span>
            <el-button v-if="item.state === '待二级审批'" style="margin-left: 10px"
                       type="success" icon="el-icon-check" circle size="mini" @click="approval(item.id)"></el-button>
            <el-button v-if="item.state === '待二级审批'"
                       type="danger" icon="el-icon-close" circle size="mini" @click="deny(item.id)"></el-button>
            <span style="margin-left: 10px">
              <el-tag v-if="item.state === '审批完成'" effect="dark" type='success'>审核通过</el-tag>
              <el-tag v-if="item.state === '审批失败'" effect="dark" type='danger'>审核未通过</el-tag>
            </span>
          </el-col>
        </el-row>
      </template>
      <div>
        <el-row>
          <el-col :span="9">
            <span><strong>销售单编号:</strong>{{item.saleSheetId}}</span>
          </el-col>
          <el-col :span="9">
            <span><strong>创建时间:</strong>{{item.createTime}}</span>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="9">
            <span><strong>商品编号:</strong>{{item.productId}}</span>
          </el-col>
          <el-col :span="9">
            <span><strong>商品数量:</strong>{{item.quantity}}</span>
          </el-col>
        </el-row>
      </div>
    </el-card>

  </div>
</template>

<script>
import {DISCOUNTLIST_APPROVAL} from "@/network/approve";

export default{
  name:"discountApprovalList",
  props:{
    list: Array
  },
  data(){},
  methods: {
    async approval(id) {
      let config = {
        params: {
          GiftSheetId: id,
          state: '审批完成'
        }
      }
      await DISCOUNTLIST_APPROVAL(config).then(_res=>{
        this.$emit("refresh")
        this.$message({
          message: '操作成功!',
          type: 'success'
        })
      }
      )
    },
    async deny(id) {
      let config = {
        params: {
          GiftSheetId: id,
          state: '审批失败'
        }
      }
      await DISCOUNTLIST_APPROVAL(config).then(_res=>{
        this.$emit("refresh")
        this.$message({
          message: '操作成功!',
          type: 'success'
        })
      })
    }
  }
}
</script>