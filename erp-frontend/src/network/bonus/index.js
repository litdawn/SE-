import request from "@/network/request";
const testAPI = require("@/apis")

export const SHOW_PREV_SALARY = config => request._get(testAPI.SHOW_PREV_SALARY, config)
export const MAKE_ANNUAL_BONUS = config =>request._get(testAPI.MAKE_ANNUAL_BONUS,config)
export const SHOW_ALL_SHEET = config => request._get(testAPI.SHOW_ALL_SHEET,config)
export const GET_ANNUAL_AMOUNT = config => request._get(testAPI.GET_ANNUAL_AMOUNT,config)