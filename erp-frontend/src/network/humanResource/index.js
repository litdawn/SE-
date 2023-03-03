import request from "@/network/request";
const testAPI = require("@/apis")

// 员工
export const MAKE_STAFF_ACCOUNT = config => request._post(testAPI.MAKE_STAFF_ACCOUNT,config);
export const SHOW_STAFF_ACCOUNT = config => request._post(testAPI.SHOW_STAFF_ACCOUNT,config);
export const STAFF_ACCOUNT_CHANGE = config => request._post(testAPI.STAFF_ACCOUNT_CHANGE,config);
// 打卡功能
export const THIS_DAY_ATTENDANCE = config => request._get(testAPI.THIS_DAY_ATTENDANCE,config);
export const FIND_ALL_ATTENDANCE_RECORD = config => request._get(testAPI.FIND_ALL_ATTENDANCE_RECORD,config);
export const FIND_SOMEBODY_ATTENDANCE_RECORD = config => request._get(testAPI.FIND_SOMEBODY_ATTENDANCE_RECORD,config);
export const ATTENDANCE_RECORD_REGISTER = config => request._get(testAPI.ATTENDANCE_RECORD_REGISTER,config);
export const FIND_STAFFID = config => request._get(testAPI.FIND_STAFFID,config);

//工资单
export const MAKE_SALARY_SHEET = config => request._post(testAPI.MAKE_SALARY_SHEET, config);
export const SECOND_APPROVAL = config => request._get(testAPI.SECOND_APPROVAL, config);
export const SHOW_SHEET_BY_STATE = config => request._get(testAPI.SHOW_SHEET_BY_STATE, config);
export const MAKE_SALARY_RULE = config => request._get(testAPI.MAKE_SALARY_RULE,config);