import request from "@/network/request";
import testAPI from "@/apis";

export const getStaff = config => request._get(testAPI.STAFF_ID, config)
export const getSalesAccount = config => request._get(testAPI.ACCOUNT_SALE, config)
export const getAllUsers = config => request._get(testAPI.USER_NAME_ALL, config)
export const getAllCustomer = config => request._get(testAPI.CUSTOMER_ALL, config)
export const getBusinessCondition = config => request._get(testAPI.BUSINESS_CONDITION, config)
export const getBusinessProcess = config => request._get(testAPI.BUSINESS_PROCESS, config)