// api.js

import {BASEURL} from "@/common/constants";

const request = (url, method, data, options) =>
  new Promise((resolve, reject) => {
    uni.request({
      url:BASEURL+url,
      method,
      data,
      header: {
        'Cookie': uni.getStorageSync('sessionId')
      },
	  timeout:10000,
      success: res => {
        resolve(res.data);
      },
      fail: err => {
        reject(err);
      },
      ...options
    });
  });
 
export default request;
