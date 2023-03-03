<template>
  <Layout>
    <Title title="促销策略制定"></Title>

    <el-button type="primary"
        size="medium" @click="dialogVisible = true">制定促销策略</el-button>
    <div class="body">

      <el-tabs v-model="activeName" :stretch="true">
        <el-tab-pane label="代金券" name="代金券">
          <el-table
              :data="voucherList"
              stripe
              style="width: 100%"
              :header-cell-style="{'text-align':'center'}"
              :cell-style="{'text-align':'center'}">
            <el-table-column
                prop="id"
                label="id"
                width="100">
            </el-table-column>
            <el-table-column
                prop="kind"
                label="促销类型"
                width="100">
            </el-table-column>
            <el-table-column
                prop="rule"
                label="促销种类"
                width="100">
            </el-table-column>
            <el-table-column
                prop="level"
                label="客户等级"
                width="100">
            </el-table-column>
            <el-table-column
                prop="amount"
                label="总金额"
                width="100">
            </el-table-column>
            <el-table-column
                prop="composition1Id"
                label="组合商品1"
                width="200">
            </el-table-column>
            <el-table-column
                prop="composition2Id"
                label="组合商品2"
                width="200">
            </el-table-column>
            <el-table-column
                prop="voucher"
                label="代金券额"
                width="100">
            </el-table-column>
            <el-table-column
                prop="beginDate"
                label="开始时间"
                width="200">
            </el-table-column>
            <el-table-column
                prop="endDate"
                label="结束时间"
                width="200">
            </el-table-column>
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="折扣" name="折扣">
          <el-table
              :data="discountList"
              stripe
              style="width: 100%"
              :header-cell-style="{'text-align':'center'}"
              :cell-style="{'text-align':'center'}">
            <el-table-column
                prop="id"
                label="id"
                width="100">
            </el-table-column>
            <el-table-column
                prop="kind"
                label="促销类型"
                width="100">
            </el-table-column>
            <el-table-column
                prop="rule"
                label="促销种类"
                width="100">
            </el-table-column>
            <el-table-column
                prop="level"
                label="客户等级"
                width="100">
            </el-table-column>
            <el-table-column
                prop="amount"
                label="总金额"
                width="100">
            </el-table-column>
            <el-table-column
                prop="discount"
                label="折扣(%)"
                width="100">
            </el-table-column>
            <el-table-column
                prop="beginDate"
                label="开始时间"
                width="200">
            </el-table-column>
            <el-table-column
                prop="endDate"
                label="结束时间"
                width="200">
            </el-table-column>
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="赠品" name="赠品">
          <el-table
              :data="giftList"
              stripe
              style="width: 100%"
              :header-cell-style="{'text-align':'center'}"
              :cell-style="{'text-align':'center'}">
            <el-table-column
                prop="id"
                label="id"
                width="100">
            </el-table-column>
            <el-table-column
                prop="kind"
                label="促销类型"
                width="100">
            </el-table-column>
            <el-table-column
                prop="rule"
                label="促销种类"
                width="100">
            </el-table-column>
            <el-table-column
                prop="level"
                label="客户等级"
                width="100">
            </el-table-column>
            <el-table-column
                prop="amount"
                label="总金额"
                width="100">
            </el-table-column>
            <el-table-column
                prop="giftId"
                label="赠品id"
                width="100">
            </el-table-column>
            <el-table-column
                prop="giftQuantity"
                label="赠品数量"
                width="100">
            </el-table-column>
            <el-table-column
                prop="beginDate"
                label="开始时间"
                width="200">
            </el-table-column>
            <el-table-column
                prop="endDate"
                label="结束时间"
                width="200">
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </div>

    <el-dialog title="促销策略制定" :visible.sync="dialogVisible">
      <el-form ref="form" label-width="100px">
        <el-form-item label="促销编号:">
          <el-input v-model="form.id"></el-input>
        </el-form-item>
        <el-form-item label="促销种类:">
          <el-select v-model="form.rule">
            <el-option label="客户等级" value="客户等级"></el-option>
            <el-option label="总金额" value="总金额"></el-option>
            <el-option label="商品组合" value="商品组合"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="form.rule === '客户等级'"
            label="用户等级:">
          <el-input-number v-model="form.level"></el-input-number>
        </el-form-item>
        <el-form-item v-if="form.rule === '总金额'"
                      label="总金额:">
          <el-input-number v-model="form.amount"></el-input-number>
        </el-form-item>
        <el-form-item v-if="form.rule === '商品组合'"
                      label="商品1:">
          <el-select v-model="form.composition1Id">
            <el-option
                v-for="item1 in commodityList"
                :key="item1.id"
                :label="item1.id"
                :value="item1.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="form.rule === '商品组合'"
                      label="商品2:">
          <el-select v-model="form.composition2Id">
            <el-option
                v-for="item2 in commodityList"
                :key="item2.id"
                :label="item2.id"
                :value="item2.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="form.rule === '商品组合'"
                      label="组合降价(元):">
          <el-input-number v-model="form.voucher"></el-input-number>
        </el-form-item>
        <el-form-item label="开始时间:">
          <el-date-picker
              v-model="form.beginDate"
              type="date"
              placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间:">
          <el-date-picker
              v-model="form.endDate"
              type="date"
              placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item v-if="form.rule !=='商品组合'"
            label="促销类型:">
          <el-select  v-model="form.kind">
            <el-option label="代金券" value="代金券"></el-option>
            <el-option label="赠品" value="赠品"></el-option>
            <el-option label="折扣" value="折扣"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="form.kind === '折扣'"
                      label="折扣(%):">
          <el-input-number v-model="form.discount" max="100" min="0"></el-input-number>
        </el-form-item>
        <el-form-item v-if="form.kind === '代金券'"
                      label="代金券额:">
          <el-input-number v-model="form.voucher"></el-input-number>
        </el-form-item>
        <el-form-item v-if="form.kind === '赠品'"
                      label="赠品:">
          <el-select  v-model="form.giftId">
            <el-option
                v-for="gift in commodityList"
                :key="gift.id"
                :label="gift.id"
                :value="gift.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="form.kind === '赠品'"
                      label="赠品数量:">
          <el-input-number v-model="form.giftQuantity"></el-input-number>
        </el-form-item>
        <div style="margin-left:  70%; margin-top: 40px">
          <el-button @click="dialogVisible2 = false">取 消</el-button>
          <el-button type="primary" @click="save">确 定</el-button>
        </div>

      </el-form>

    </el-dialog>

  </Layout>
</template>

<script>
import Layout from "@/components/content/Layout";
import Title from "@/components/content/Title";
import {getAllCommodity} from "@/network/commodity";
import {ADDDISCOUNT, ADDGIFT, ADDVOUCHER, GET_PROMOTION} from "@/network/discount";
export default {
  components: {
    Layout,
    Title
  },
  mounted() {
    this.getPromotion()
    getAllCommodity({}).then(_res => {
      let res = _res.result
      res.forEach(item => this.commodityList.push({ id: item.id }))
      console.log(this.commodityList)
    })
  },
  data(){
    return{
      activeName:'代金券',
      dialogVisible:false,
      rule:'',
      form:{},
      commodityList: [],
      promotionList: [],
      voucherList: [],
      giftList: [],
      discountList: []
    }
  },
  methods:{
    getPromotion(){
      GET_PROMOTION().then(_res => {
        this.promotionList = _res.result
        this.voucherList = this.promotionList.filter(item => item.kind === '代金券')
        this.giftList = this.promotionList.filter(item => item.kind === '赠品')
        this.discountList = this.promotionList.filter(item => item.kind === '折扣')
      })
    },
    save(){
      let promotion = JSON.parse(JSON.stringify(this.form))
      if(this.form.kind === '代金券'){
        ADDVOUCHER(promotion)
      }else if(this.form.kind === '赠品'){
        ADDGIFT(promotion)
      }else if(this.form.kind === '折扣'){
        ADDDISCOUNT(promotion)
      }else{
        promotion.kind = '代金券'
        console.log(promotion)
        ADDVOUCHER(promotion)
      }
      this.form= {};
      this.dialogVisible = false;
      this.getPromotion();
    }
  }
};
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