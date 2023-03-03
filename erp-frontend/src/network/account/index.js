import request from "@/network/request"
const testAPI = require("@/apis")

export const getALLAccount = config => request._get(testAPI.ACCOUNT_FIND_ALL, config)
export const getParticularAccount = config => request._get(testAPI.ACCOUNT_FIND, config)
export const deleteAccount = config => request._delete(testAPI.ACCOUNT_DELETE, config)
export const createAccount = config => request._post(testAPI.ACCOUNT_CREATE, config)
export const changeAccount = config => request._post(testAPI.ACCOUNT_CHANGE, config)
export const createPay = config => request._post(testAPI.PAY_CREATE, config)
export const getAllCustomer = config => request._get(testAPI.CUSTOMER_ALL, config)
export const createReceive = config => request._post(testAPI.RECEIVE_CREATE, config)


