import request from "@/network/request";
const testAPI = require("@/apis")

export const ADDVOUCHER = config => request._post(testAPI.ADDVOUCHER, config);
export const ADDGIFT = config => request._post(testAPI.ADDGIFT, config);
export const ADDDISCOUNT = config => request._post(testAPI.ADDDISCOUNT, config);
export const GET_PROMOTION = config => request._get(testAPI.GET_PROMOTION, config);