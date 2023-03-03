import Vue from "vue";
import VueRouter from "vue-router";
import { ROLE, PATH } from "../common/const";

const Error = () => import("../components/content/Error");
const Login = () => import("../views/auth/Login");
const Home = () => import("../views/Home");
const CommodityManagement = () =>
  import("../views/commodity/CommodityManagement");
const CommodityClassification = () =>
  import("../views/commodity/CommodityClassification");
const InventoryManagement = () => import("../views/inventory/InventoryManagement");
const InventoryCheck = () => import("../views/inventory/InventoryCheck");
const InventoryOperation = () =>
  import("../views/inventory/InventoryOperation");
const InventoryIn = () => import("../views/inventory/InventoryIn");
const InventoryOut = () => import("../views/inventory/InventoryOut");
const InventoryLoss = () => import("../views/inventory/InventoryLoss");
const InventoryOverflow = () => import("../views/inventory/InventoryOverflow");
const InventoryPresent = () => import("../views/inventory/InventoryPresent");
const InventoryView = () => import("../views/inventory/InventoryView");
const InventoryWarning = () => import("../views/inventory/InventoryWarning");
const PurchaseView = () => import("../views/purchase/PurchaseView");
const PurchaseReturnView = () => import("../views/purchase/PurchaseReturnView");
const SaleView = () => import("../views/sale/SaleView");
const SaleReturnView = () => import("../views/sale/SaleReturnView");
const CustomerView = () => import ("../views/purchase/CustomerView");
const Approval = () => import("../views/approval/Approval");

const AccountView = () => import("../views/account/AccountView");
const AccountPayReceive = () => import("../views/account/AccountPayReceive");
const AccountForSale = () => import("../views/business/AccountForSale");
const BusinessProcess = () => import("../views/business/BusinessProcess");
const BusinessCondition = () => import("../views/business/BusinessCondition")

const HRManagement = () => import("../views/humanResource/HRManagement");
const HRClockIn = () => import("../views/humanResource/HRClockIn");
const Salary = () => import("../views/humanResource/Salary");
const Bonus = () => import("../views/bonus/Bonus");
const Discount = () => import("../views/discount/Discount");
const DiscontApproval = () => import("../views/approval/discountApproval");
Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    component: Home
  },
  {
    path: "/error",
    component: Error
  },
  {
    path: "/login",
    component: Login
  },
  {
    path: "/register",
    component: () => import("@/views/auth/register.vue")
  },
  // 商品管理
  {
    path: PATH.COMMODITY_CLASSIFICATION.path,
    component: CommodityClassification,
    meta: { requiresAuth: PATH.COMMODITY_CLASSIFICATION.requiresAuth }
  },
  {
    path: PATH.COMMODITY_MANAGEMENT.path,
    component: CommodityManagement,
    meta: { requiresAuth: PATH.COMMODITY_MANAGEMENT.requiresAuth }
  },
  // 库存管理
  {
    path: PATH.INVENTORY_MANAGEMENT.path,
    component: InventoryManagement,
    meta: { requiresAuth: PATH.INVENTORY_MANAGEMENT.requiresAuth }
  },
  {
    path: PATH.INVENTORY_CHECK.path,
    component: InventoryCheck,
    meta: { requiresAuth: PATH.INVENTORY_CHECK.requiresAuth }
  },
  {
    path: PATH.INVENTORY_OPERATION.path,
    component: InventoryOperation,
    name: "InventoryOperation",
    meta: { requiresAuth: PATH.INVENTORY_OPERATION.requiresAuth }
  },
  {
    path: PATH.INVENTORY_IN.path,
    component: InventoryIn,
    name: "InventoryIn",
    meta: { requiresAuth: PATH.INVENTORY_LOSS.requiresAuth }
  },
  {
    path: PATH.INVENTORY_OUT.path,
    component: InventoryOut,
    name: "InventoryOut",
    meta: { requiresAuth: PATH.INVENTORY_LOSS.requiresAuth }
  },
  {
    path: PATH.INVENTORY_LOSS.path,
    component: InventoryLoss,
    name: "InventoryLoss",
    meta: { requiresAuth: PATH.INVENTORY_LOSS.requiresAuth }
  },
  {
    path: PATH.INVENTORY_OVERFLOW.path,
    component: InventoryOverflow,
    name: "InventoryOverflow",
    meta: { requiresAuth: PATH.INVENTORY_OVERFLOW.requiresAuth }
  },
  {
    path: PATH.INVENTORY_PRESENT.path,
    component: InventoryPresent,
    name: "InventoryPresent",
    meta: { requiresAuth: PATH.INVENTORY_PRESENT.requiresAuth }
  },
  {
    path: PATH.INVENTORY_VIEW.path,
    component: InventoryView,
    meta: { requiresAuth: PATH.INVENTORY_VIEW.requiresAuth }
  },
  {
    path: PATH.INVENTORY_WARNING.path,
    component: InventoryWarning,
    name: "InventoryWarning",
    meta: { requiresAuth: PATH.INVENTORY_WARNING.requiresAuth }
  },
  // 销售管理
  {
    path: PATH.PURCHASE_VIEW.path,
    component: PurchaseView,
    name: "PurchaseView",
    meta: { requiresAuth: PATH.PURCHASE_VIEW.requiresAuth }
  },
  {
    path: PATH.PURCHASE_RETURN_VIEW.path,
    component: PurchaseReturnView,
    name: "PurchaseReturnView",
    meta: { requiresAuth: PATH.PURCHASE_RETURN_VIEW.requiresAuth }
  },
  {
    path: PATH.SALE_VIEW.path,
    component: SaleView,
    name: "SaleView",
    meta: { requiresAuth: PATH.SALE_VIEW.requiresAuth }
  },
  {
    path: PATH.SALE_RETURN_VIEW.path,
    component: SaleReturnView,
    name: "saleReturnView",
    meta: { requiresAuth: PATH.SALE_RETURN_VIEW.requiresAuth }
  },
  {
    path: PATH.CUSTOMER_VIEW.path,
    component: CustomerView,
    name: "CustomerView",
    meta: { requiresAuth: PATH.CUSTOMER_VIEW.requiresAuth }
  },
  // 审批
  {
    path: PATH.GM_APPROVAL.path,
    component: Approval,
    meta: { requiresAuth: PATH.GM_APPROVAL.requiresAuth }
  },
  {
    path: PATH.GM_DISCOUNT_APPROVAL.path,
    component: DiscontApproval,
    meta:{ requiresAuth: PATH.GM_DISCOUNT_APPROVAL.requiresAuth }
  },
    // 财务
  {
    path: PATH.ACCOUNT_VIEW.path,
    component: AccountView,
    name: "AccountView",
    meta: { requiresAuth: PATH.ACCOUNT_VIEW.requiresAuth}
  },
  {
    path: PATH.ACCOUNT_PAY_RECEIVE.path,
    component: AccountPayReceive,
    name: "AccountPayReceive",
    meta: { requiresAuth: PATH.ACCOUNT_PAY_RECEIVE.requiresAuth}
  },
  {
    path: PATH.ACCOUNT_FOR_SALE.path,
    component: AccountForSale,
    name: "AccountForSale",
    meta: { requiresAuth: PATH.ACCOUNT_FOR_SALE.requiresAuth}
  },
  {
    path: PATH.BUSSINESS_PROCESS.path,
    component: BusinessProcess,
    name: "BusinessProcess",
    meta: { requiresAuth: PATH.BUSSINESS_PROCESS.requiresAuth}
  },
  {
    path: PATH.BUSSINESS_CONDITION.path,
    component: BusinessCondition,
    name: "BusinessCondition",
    meta: { requiresAuth: PATH.BUSSINESS_CONDITION.requiresAuth}
  },
//  人力资源管理
  {
    path: PATH.HR_MANAGEMENT.path,
    component: HRManagement,
    meta:{ requiresAuth: PATH.HR_MANAGEMENT.requiresAuth}
  },
  // 打卡
  {
    path: PATH.HR_CLOCK_IN.path,
    component:HRClockIn,
    meta:{ requiresAuth: PATH.HR_CLOCK_IN.requiresAuth}
  },
  //  薪资规则制定
  {
    path: PATH.SALARY.path,
    component:Salary,
    meta:{ requiresAuth: PATH.SALARY.requiresAuth}
  },
  // 年终奖
  {
    path: PATH.GM_BONUS.path,
    component: Bonus,
    meta:{ requiresAuth: PATH.GM_BONUS.requiresAuth}
  },
  {
    path: PATH.GM_DISCOUNT.path,
    component: Discount,
    meta:{ requiresAuth: PATH.GM_DISCOUNT.requiresAuth}
  },

  // -----------------------未找到页面-----------------------------
  {
    path: "*",
    redirect: "/error"
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

router.beforeEach(async (to, from, next) => {
  // console.log(to.path);
  if (to.path === "/error" || to.path === "/login") {
    next();
  } else if (to.path === "/") {
    let token = sessionStorage.getItem("token");
    let role = sessionStorage.getItem("role");
    if (token == null || role == null) next("/login");
    else next();
  } else if (to.meta.requiresAuth) {
    if (
      to.meta.requiresAuth.some(
        role => role.toString() === sessionStorage.getItem("role")
      )
    ) {
      //有权限
      // console.log("获得访问权限");
      next()
    } else {
      // console.log("无权限访问");
      next("/"); //无权限,跳回主页
    }
  } else {
    // 非法路径, 直接next (跳转error)
    next();
  }
});

export default router;
