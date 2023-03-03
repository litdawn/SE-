import request from "@/network/request";
const testAPI = require("@/apis");

export const DISCOUNTLIST_APPROVAL = config => request._get(testAPI.DISCOUNTLIST_APPROVAL, config);
export const GET_GIFT_SHEET = config => request._get(testAPI.GET_GIFT_SHEET, config);