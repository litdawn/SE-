//TEST
const TEST_GET = "/api/test/get";
const TEST_POST = "/api/test/post";

const AUTH = "/api/user/auth"
const LOGIN = "/api/user/login"
const REGISTER = '/api/user/register'

// 商品分类管理
const COMMODITY_CLASSIFICATION_ALL = '/api/category/queryAll'
const COMMODITY_CLASSIFICATION_CREATE = '/api/category/create'
const COMMODITY_CLASSIFICATION_UPDATE = '/api/category/update'
const COMMODITY_CLASSIFICATION_DELETE = '/api/category/delete'

// 商品管理
const COMMODITY_ALL = '/api/product/queryAll';
const COMMODITY_CREATE = '/api/product/create';
const COMMODITY_UPDATE = '/api/product/update';
const COMMODITY_DELETE = '/api/product/delete';


const WAREHOUSE_INPUT = '/api/warehouse/input';
const WAREHOUSE_OUTPUT_PRE = '/api/warehouse/product/count';
const WAREHOUSE_OUTPUT = '/api/warehouse/output';
const WAREHOUSE_GET_INPUTSHEET = '/api/warehouse/inputSheet/state';
const WAREHOUSE_GET_OUTPUTSHEET = '/api/warehouse/outputSheet/state';
const WAREHOUSE_IO_DEATIL_BY_TIME = '/api/warehouse/sheetContent/time';
const WAREHOUSE_IPQ_BY_TIME = '/api/warehouse/inputSheet/time/quantity';
const WAREHOUSE_OPQ_BY_TIME = '/api/warehouse/outputSheet/time/quantity';
const WAREHOUSE_OUTPUTSHEET_APPROVE = '/api/warehouse/outputSheet/approve';
const WAREHOUSE_INPUTSHEET_APPROVE = '/api/warehouse/inputSheet/approve';
const WAREHOUSE_DAILY_COUNT = '/api/warehouse/warehouse/counting';


// 销售管理
// 进货管理
const PURCHASE_ALL = '/api/purchase/sheet-show';
const PURCHASE_FIND_SHEET_BY_ID = '/api/purchase/find-sheet';
const PURCHASE_CREATE = '/api/purchase/sheet-make';
const PURCHASE_FIRST_APPROVAL = '/api/purchase/first-approval';
const PURCHASE_SECOND_APPROVAL = '/api/purchase/second-approval';
// 进货退货管理
const PURCHASE_RETURN_ALL = '/api/purchase-returns/sheet-show';
const PURCHASE_RETURN_CREATE = '/api/purchase-returns/sheet-make';
const PURCHASE_RETURN_FIRST_APPROVAL = '/api/purchase-returns/first-approval';
const PURCHASE_RETURN_SECOND_APPROVAL = '/api/purchase-returns/second-approval';
// 销售管理
const SALE_ALL = '/api/sale/sheet-show';
const SALE_CREATE = '/api/sale/sheet-make';
const SALE_FIRST_APPROVAL = '/api/sale/first-approval';
const SALE_SECOND_APPROVAL = '/api/sale/second-approval';
const SALE_FIND_SHEET_BY_ID = '/api/sale/find-sheet';
// 销售退货管理
const SALE_RETURN_ALL = '/api/sale-returns/sheet-show';
const SALE_RETURN_CREATE = '/api/sale-returns/sheet-make';
const SALE_RETURN_FIRST_APPROVAL = '/api/sale-returns/first-approval';
const SALE_RETURN_SECOND_APPROVAL = '/api/sale-returns/second-approval';
// 客户管理
const CUSTOMER_QUERY = '/api/customer/findByType';
const CUSTOMER_ALL = '/api/customer/show-all-customer-id'

const SALE_PURCHASE_ALL = '/api/purchase/sheet-show';
const SALE_PURCHASE_CREATE = '/api/purchase/sheet-make';
const SALE_CUSTOMER_QUERY = '/api/customer/findByType';
const SALE_CUSTOMER_MAX = '/api/sale/maxAmountCustomer';

// account
const ACCOUNT_DELETE = '/api/company-bank-account-management/delete-company-bank-account';
const ACCOUNT_CREATE = '/api/company-bank-account-management/create-company-bank-account';
const ACCOUNT_CHANGE = '/api/company-bank-account-management/change-company-bank-account-name';
const ACCOUNT_FIND = '/api/company-bank-account-management/find-company-bank-account';
const ACCOUNT_FIND_ALL = '/api/company-bank-account-management/find-all-account';

const PAY_CREATE = '/api/payable/payable-sheet-make';
const RECEIVE_CREATE = '/api/receivable/receivable-sheet-make';

const ACCOUNT_SALE = '/api/sale-status/sale-status-show';
const USER_NAME_ALL = '/api/user/find-all-user';
const BUSINESS_CONDITION = '/api/business-condition/business-condition-show';
const BUSINESS_PROCESS = '/api/business-process/business-process-show';

const STAFF_ID = '/api/staff/show-all-staff-id';


// 打卡
const THIS_DAY_ATTENDANCE = '/api/attendance/thisDayAttendance';
const FIND_ALL_ATTENDANCE_RECORD = '/api/attendance/findAll';
const FIND_SOMEBODY_ATTENDANCE_RECORD = '/api/attendance/findSomebody'
const ATTENDANCE_RECORD_REGISTER = 'api/attendance/register';
const FIND_STAFFID = 'api/attendance/findStaffIdByName'
//工资单
const MAKE_SALARY_SHEET = '/api/salary/sheet-make';
const SECOND_APPROVAL = '/api/salary/second-approval';
const SHOW_SHEET_BY_STATE = '/api/salary/sheet-show';
const FIND_BY_SHEET_ID = '/api/salary/find-sheet';
const MAKE_SALARY_RULE = '/api/salary/salaryRuleChange';
const SHOW_PREV_SALARY = '/api/salary/prev-salary';
const MAKE_ANNUAL_BONUS = '/api/salary/set-annual-bonus';
const SHOW_ALL_SHEET = '/api/salary/show-all-sheet';
const GET_ANNUAL_AMOUNT = '/api/salary/get-annual-bonus';
//员工管理
const MAKE_STAFF_ACCOUNT = '/api/staff/staff-account-make';
const SHOW_STAFF_ACCOUNT = '/api/staff/staff-account-show';
const STAFF_ACCOUNT_CHANGE = '/api/staff/staff-account-change';
//促销策略
const ADDVOUCHER = '/api/promotion/voucher';
const ADDDISCOUNT = '/api/promotion/discount';
const ADDGIFT = '/api/promotion/gift';
const GET_PROMOTION = '/api/promotion/getPromotion';
//赠品单
const DISCOUNTLIST_APPROVAL = '/api/promotion/approve';
const GET_GIFT_SHEET = '/api/promotion/getGiftSheetByState';

module.exports = {
  TEST_GET,
  TEST_POST,

  AUTH,
  LOGIN,
  REGISTER,
  COMMODITY_CLASSIFICATION_ALL,
  COMMODITY_CLASSIFICATION_CREATE,
  COMMODITY_CLASSIFICATION_UPDATE,
  COMMODITY_CLASSIFICATION_DELETE,

  COMMODITY_ALL,
  COMMODITY_CREATE,
  COMMODITY_UPDATE,
  COMMODITY_DELETE,

  WAREHOUSE_INPUT,
  WAREHOUSE_OUTPUT_PRE,
  WAREHOUSE_OUTPUT,
  WAREHOUSE_GET_INPUTSHEET,
  WAREHOUSE_GET_OUTPUTSHEET,
  WAREHOUSE_IO_DEATIL_BY_TIME,
  WAREHOUSE_IPQ_BY_TIME,
  WAREHOUSE_OPQ_BY_TIME,
  WAREHOUSE_OUTPUTSHEET_APPROVE,
  WAREHOUSE_INPUTSHEET_APPROVE,
  WAREHOUSE_DAILY_COUNT,

  PURCHASE_ALL,
  PURCHASE_CREATE,
  PURCHASE_FIRST_APPROVAL,
  PURCHASE_SECOND_APPROVAL,
  PURCHASE_RETURN_ALL,
  PURCHASE_RETURN_CREATE,
  PURCHASE_RETURN_FIRST_APPROVAL,
  PURCHASE_RETURN_SECOND_APPROVAL,
  PURCHASE_FIND_SHEET_BY_ID,

  SALE_ALL,
  SALE_CREATE,
  SALE_FIRST_APPROVAL,
  SALE_SECOND_APPROVAL,
  SALE_CUSTOMER_QUERY,
  SALE_CUSTOMER_MAX,
  SALE_FIND_SHEET_BY_ID,
  CUSTOMER_QUERY,
  SALE_RETURN_ALL,
  SALE_RETURN_CREATE,
  SALE_RETURN_FIRST_APPROVAL,
  SALE_RETURN_SECOND_APPROVAL,

  ACCOUNT_DELETE,
  ACCOUNT_CREATE,
  ACCOUNT_FIND,
  ACCOUNT_CHANGE,
  ACCOUNT_FIND_ALL,

  PAY_CREATE,
  CUSTOMER_ALL,
  RECEIVE_CREATE,
  ACCOUNT_SALE,
  USER_NAME_ALL,

  BUSINESS_PROCESS,
  BUSINESS_CONDITION,

  STAFF_ID,

  THIS_DAY_ATTENDANCE,
  FIND_ALL_ATTENDANCE_RECORD,
  FIND_SOMEBODY_ATTENDANCE_RECORD,
  ATTENDANCE_RECORD_REGISTER,
  FIND_STAFFID,

  MAKE_SALARY_SHEET,
  SECOND_APPROVAL,
  SHOW_SHEET_BY_STATE,
  FIND_BY_SHEET_ID,
  MAKE_SALARY_RULE,
  SHOW_PREV_SALARY,

  MAKE_STAFF_ACCOUNT,
  SHOW_STAFF_ACCOUNT,
  STAFF_ACCOUNT_CHANGE,
  MAKE_ANNUAL_BONUS,
  SHOW_ALL_SHEET,
  GET_ANNUAL_AMOUNT,

  ADDVOUCHER,
  ADDDISCOUNT,
  ADDGIFT,
  GET_PROMOTION,

  DISCOUNTLIST_APPROVAL,
  GET_GIFT_SHEET
};
