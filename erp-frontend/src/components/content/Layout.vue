<template>
  <el-container style="height: 100%; border: 1px solid #eee">
    <el-aside width="250px" style="background-color: #545c64">
      <header class="title">
        <div class="title-inner">ERP</div>
      </header>
      <el-menu
        :default-active="getActivePath"
        class="el-menu-vertical-demo"
        background-color="#fff"
        text-color="#000000DE"
        @open="handleOpen"
        @close="handleClose"
        active-text-color="#7e57c2"
        router
      >
        <el-submenu index="1"
          v-if="permit(PATH.COMMODITY_CLASSIFICATION.requiresAuth)">
          <template slot="title">
            <i class="el-icon-receiving"></i>
            <span slot="title">商品管理</span>
          </template>
          <el-menu-item
            :index="PATH.COMMODITY_CLASSIFICATION.path"
            v-if="permit(PATH.COMMODITY_CLASSIFICATION.requiresAuth)"
          >
            <i class="el-icon-receiving"></i>
            <span slot="title">商品分类管理</span>
          </el-menu-item>
          <el-menu-item
            :index="PATH.COMMODITY_MANAGEMENT.path"
            v-if="permit(PATH.COMMODITY_MANAGEMENT.requiresAuth)"
          >
            <i class="el-icon-receiving"></i>
            <span slot="title">商品管理</span>
          </el-menu-item>
        </el-submenu>

        <el-submenu index="2" v-if="permit(PATH.INVENTORY_OPERATION.requiresAuth)">
          <template slot="title">
            <i class="el-icon-receiving"></i>
            <span slot="title">库存管理</span>
          </template>
          <el-menu-item
            :index="PATH.INVENTORY_MANAGEMENT.path"
            v-if="permit(PATH.INVENTORY_MANAGEMENT.requiresAuth)"
          >
            <i class="el-icon-receiving"></i>
            <span slot="title">库存管理</span>
          </el-menu-item>
          <el-menu-item
            :index="PATH.INVENTORY_OPERATION.path"
            v-if="permit(PATH.INVENTORY_OPERATION.requiresAuth)"
          >
            <i class="el-icon-receiving"></i>
            <span slot="title">库存操作</span>
          </el-menu-item>
          <el-menu-item
            :index="PATH.INVENTORY_VIEW.path"
            v-if="permit(PATH.INVENTORY_VIEW.requiresAuth)"
          >
            <i class="el-icon-receiving"></i>
            <span slot="title">库存查看</span>
          </el-menu-item>
          <el-menu-item
            :index="PATH.INVENTORY_CHECK.path"
            v-if="permit(PATH.INVENTORY_CHECK.requiresAuth)"
          >
            <i class="el-icon-receiving"></i>
            <span slot="title">库存盘点</span>
          </el-menu-item>
        </el-submenu>
        <el-submenu index="3"
          v-if="permit(PATH.CUSTOMER_VIEW.requiresAuth)">
          <template slot="title">
            <i class="el-icon-sell"></i>
            <span slot="title">销售管理</span>
          </template>
          <el-menu-item
            :index="PATH.PURCHASE_VIEW.path"
            v-if="permit(PATH.PURCHASE_VIEW.requiresAuth)"
          >
            <i class="el-icon-shopping-cart-full"></i>
            <span slot="title">进货管理</span>
          </el-menu-item>
          <el-menu-item
            :index="PATH.PURCHASE_RETURN_VIEW.path"
            v-if="permit(PATH.PURCHASE_RETURN_VIEW.requiresAuth)"
          >
            <i class="el-icon-shopping-cart-2"></i>
            <span slot="title">进货退货管理</span>
          </el-menu-item>
          <el-menu-item
            :index="PATH.SALE_VIEW.path"
            v-if="permit(PATH.SALE_VIEW.requiresAuth)"
          >
            <i class="el-icon-sell"></i>
            <span slot="title">销售管理</span>
          </el-menu-item>
          <el-menu-item
              :index="PATH.SALE_RETURN_VIEW.path"
              v-if="permit(PATH.SALE_RETURN_VIEW.requiresAuth)"
          >
            <i class="el-icon-sell"></i>
            <span slot="title">销售退货管理</span>
          </el-menu-item>
          <el-menu-item
            :index="PATH.CUSTOMER_VIEW.path"
            v-if="permit(PATH.CUSTOMER_VIEW.requiresAuth)"
          >
            <i class="el-icon-user"></i>
            <span slot="title">客户管理</span>
          </el-menu-item>
        </el-submenu>
        <el-submenu index="4"
          v-if="permit(PATH.GM_APPROVAL.requiresAuth)">
          <template slot="title">
            <i class="el-icon-receiving"></i>
            <span slot="title">审核管理</span>
          </template>
<!--          <el-menu-item-->
<!--            :index="PATH.GM_APPROVAL.path"-->
<!--            v-if="permit(PATH.GM_APPROVAL.requiresAuth)"-->
<!--          >-->
<!--            <i class="el-icon-receiving"></i>-->
<!--            <span slot="title">审批</span>-->
<!--          </el-menu-item>-->
          <el-menu-item
              :index="PATH.SALARY.path"
              v-if="permit(PATH.SALARY.requiresAuth)"
          >
            <i class="el-icon-suitcase"></i>
            <span slot="title">工资单审批</span>
          </el-menu-item>

          <el-menu-item
              :index="PATH.GM_DISCOUNT_APPROVAL.path"
              v-if="permit(PATH.GM_DISCOUNT_APPROVAL.requiresAuth)"
          >
            <i class="el-icon-magic-stick"></i>
            <span slot="title">促销单审批</span>
          </el-menu-item>
        </el-submenu>
        <el-submenu index="5"
                    v-if="permit(PATH.ACCOUNT_VIEW.requiresAuth)">
          <template slot="title">
            <i class="el-icon-document"></i>
            <span slot="title">财务管理</span>
          </template>
          <el-menu-item
              :index="PATH.ACCOUNT_VIEW.path"
              v-if="permit(PATH.ACCOUNT_VIEW.requiresAuth)"
          >
            <i class="el-icon-coordinate"></i>
            <span slot="title">账户管理</span>
          </el-menu-item>
          <el-menu-item
              :index="PATH.ACCOUNT_PAY_RECEIVE.path"
              v-if="permit(PATH.ACCOUNT_PAY_RECEIVE.requiresAuth)"
          >
            <i class="el-icon-receiving"></i>
            <span slot="title">收付款管理</span>
          </el-menu-item>
        </el-submenu>
        <el-submenu index="6"
                    v-if="permit(PATH.ACCOUNT_FOR_SALE.requiresAuth)">
          <template slot="title">
            <i class="el-icon-data-line"></i>
            <span slot="title">经营管理</span>
          </template>
          <el-menu-item
              :index="PATH.ACCOUNT_FOR_SALE.path"
              v-if="permit(PATH.ACCOUNT_FOR_SALE.requiresAuth)"
          >
            <i class="el-icon-data-analysis"></i>
            <span slot="title">销售明细表</span>
          </el-menu-item>
          <el-menu-item
              :index="PATH.BUSSINESS_PROCESS.path"
              v-if="permit(PATH.BUSSINESS_PROCESS.requiresAuth)"
          >
            <i class="el-icon-data-analysis"></i>
            <span slot="title">经营历程表</span>
          </el-menu-item>
          <el-menu-item
              :index="PATH.BUSSINESS_CONDITION.path"
              v-if="permit(PATH.BUSSINESS_CONDITION.requiresAuth)"
          >
            <i class="el-icon-data-analysis"></i>
            <span slot="title">经营情况表</span>
          </el-menu-item>
        </el-submenu>
        <el-submenu index="5"
                    v-if="permit(PATH.GM_BONUS.requiresAuth)">
          <template slot="title">
            <i class="el-icon-present"></i>
            <span slot="title">年终奖</span>
          </template>
          <el-menu-item
              :index="PATH.GM_BONUS.path"
              v-if="permit(PATH.GM_BONUS.requiresAuth)"
          >
            <i class="el-icon-box"></i>
            <span slot="title">年终奖制定</span>
          </el-menu-item>
        </el-submenu>

        <el-submenu index="7"
                    v-if="permit(PATH.GM_DISCOUNT.requiresAuth)">
          <template slot="title">
            <i class="el-icon-discount"></i>
            <span slot="title">促销策略</span>
          </template>
          <el-menu-item
              :index="PATH.GM_DISCOUNT.path"
              v-if="permit(PATH.GM_DISCOUNT.requiresAuth)"
          >
            <i class="el-icon-price-tag"></i>
            <span slot="title">促销策略制定</span>
          </el-menu-item>
        </el-submenu>

        <el-submenu index="8"
                    v-if="permit(PATH.HR_CLOCK_IN.requiresAuth)">
          <template slot="title">
            <i class="el-icon-user"></i>
            <span slot="title">人力资源管理</span>
          </template>
          <el-menu-item
              :index="PATH.HR_MANAGEMENT.path"
              v-if="permit(PATH.HR_MANAGEMENT.requiresAuth)"
          >
            <i class="el-icon-user-solid"></i>
            <span slot="title">员工管理</span>
          </el-menu-item>
          <el-menu-item
              :index="PATH.HR_CLOCK_IN.path"
              v-if="permit(PATH.HR_CLOCK_IN.requiresAuth)"
          >
            <i class="el-icon-s-claim"></i>
            <span slot="title">员工打卡</span>
          </el-menu-item>
          <el-menu-item
              :index="PATH.SALARY.path"
              v-if="permit(PATH.SALARY.requiresAuth)"
          >
            <i class="el-icon-s-finance"></i>
            <span slot="title">薪资制定</span>
          </el-menu-item>
        </el-submenu>
      </el-menu>
      <div class="logout-fix">
        <el-tooltip
          class="item"
          effect="light"
          :content="getUserRole"
          placement="right"
        >
          <div class="logout-name">{{ getUsername() }}</div>
        </el-tooltip>
        <el-button class="clockIn" type="primary" round @click="clockIn()"
                   v-if="permit(PATH.EXCEPT_GM.requiresAuth) && !notAttendance">
          每日打卡
        </el-button>
        <div class="logout" @click="logout()">退出登录</div>
      </div>
    </el-aside>

    <el-main style="overflow-x: hidden">
      <slot></slot>
    </el-main>
  </el-container>
</template>

<script>
import { ROLE, PATH } from "../../common/const";
import {ATTENDANCE_RECORD_REGISTER, FIND_STAFFID, THIS_DAY_ATTENDANCE} from "@/network/humanResource";
export default {
  name: "Layout",
  props: {
    activePath: String
  },
  data() {
    return {
      ROLE: ROLE,
      PATH: PATH,
      notAttendance: true
    };
  },
  created() {
    this.whetherAttendance()
  },
  mounted() {},
  computed: {
    getActivePath() {
      if (this.activePath == null) {
        return this.$route.path;
      } else {
        return this.activePath;
      }
    },
    getUserRole() {
      return sessionStorage.getItem("role");
    }
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    logout() {
      sessionStorage.removeItem("name"); // name
      sessionStorage.removeItem("role"); // role
      sessionStorage.removeItem("token"); // token
      // this.$router.push('/');
      location.href = "/";
    },
    getUsername() {
      return sessionStorage.getItem("name");
    },
    getRole(){
      return sessionStorage.getItem("role");
    },
    permit(arr) {
      return arr.some(role => role === sessionStorage.getItem("role"));
    },
    async clockIn(){
      let user = this.getUsername();
      let role = this.getRole();
      let staffId
      await FIND_STAFFID({
        params: {
          name: this.getUsername()
        }
      }).then(_res => {
        staffId = _res.result
      })
      await ATTENDANCE_RECORD_REGISTER({params:{
          staffId: staffId,
          name: user,
          role: role
        }}).then(_res =>{
        if (_res.msg === "Success"){
          this.$message.success("打卡成功！");
          this.whetherAttendance()
        }else {
          this.$message.error("打卡失败！");
        }
      })
    },
    async whetherAttendance(){
      let date = this.format(new Date());
      let staffId
      await FIND_STAFFID({
        params: {
          name: this.getUsername()
        }
      }).then(_res => {
        staffId = _res.result
      })
      THIS_DAY_ATTENDANCE({params:{
          date : date,
          staffId: staffId
        }}).then(_res => {
        this.notAttendance = _res.result
        // console.log(this.notAttendance)
      })
    },
    //格式化时间
    format(dat){
      let year = dat.getFullYear();
      let mon = (dat.getMonth() + 1) < 10 ? "0" + (dat.getMonth() + 1) : dat.getMonth() + 1;
      let data = dat.getDate() < 10 ? "0" + (dat.getDate()) : dat.getDate();
      return year + "-" + mon + "-" + data;
    }

  }
};
</script>

<style>
.title {
  padding: 16px;
  margin-bottom: 10px;
}
.title-inner {
  background-color: #9fa8da !important;
  border-color: #9fa8da !important;
  border-radius: 8px !important;
  line-height: 40px;
  height: 40px;
  text-align: center;
  font-size: 21px;
  font-weight: bold;
}
.el-aside {
  background-color: #fff !important;
  margin-right: 10px;
}
.el-menu {
  border-right: solid 1px #fff;
}
.el-menu-item {
  width: 92%;
  margin: 0 auto;
  padding: 0 10px !important;
  height: 40px;
  line-height: 40px;
  font-size: 13px;
}
.el-menu-item > i {
  margin-right: 30px !important;
}
.el-menu-item:focus,
.el-menu-item:hover {
  outline: 0;
  background-color: #f6f6f6 !important;
}
.el-menu-item.is-active {
  color: #7e57c2;
  background-color: #f0ebf8 !important;
}
.logout-fix {
  position: fixed;
  left: 30px;
  bottom: 30px;
}
.logout-fix .logout-name {
  min-width: 60px;
  background-color: #7e57c2 !important;
  border-color: #7e57c2 !important;
  color: #fff;
  border-radius: 16px;
  font-size: 14px;
  height: 32px;
  text-align: center;
  padding: 0 10px;
  margin-left: -10px;
  line-height: 32px;
  margin-bottom: 14px;
  cursor: pointer;
}
.logout-fix .logout {
  color: #00000099;
  font-size: 14px;
  cursor: pointer;
}
.logout-fix .logout:hover {
  color: #000000;
}
</style>
