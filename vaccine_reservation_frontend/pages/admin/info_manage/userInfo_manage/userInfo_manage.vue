<template>
<view class="flex-col page">
  <view class="flex-row relative section">
    <view class="flex-row items-center flex-1 self-center section_2">
      <image
        class="image"
        src="/static/global/searchBar/搜索.png"
      />
      <view class="text ml-19">
        <input type="text" v-model="username" @blur="getDataByUserName" placeholder="搜索用户名称" />
      </view>
    </view>
    <view class="ml-22 flex-col justify-start shrink-0 self-start text-wrapper" @tap="redirectToOtherPage('/pages/admin/info_manage/userInfo_manage/userInfo_add/userInfo_add')">
      <text class="font text_2">新增用户信息</text>
    </view>
    </view>

  <view class="mt-36 flex-col list">
    <view class="flex-col list-item mt-29" v-for="(user, index) in users" :key="index">
      <view class="mt-36 flex-col group">
        <view class="flex-col justify-start relative">
          <view class="section_3"></view>
          <text class="text_3 pos">{{ user.name }}</text>
        </view>
        <view class="flex-col section_4">
          <view class="flex-row justify-between items-center">
            <view class="flex-col">
              <view class="flex-row">
                <text class="font_2 text_4">性别：</text>
                <text class="font_3 ml-29">{{ USER_GENDER[user.gender] }}</text>
              </view>
              <view class="flex-row items-baseline mt-17">
                <text class="font_2 text_6">手机号：</text>
                <text class="font_3 text_7 ml-15">{{user.phone}}</text>
              </view>
            </view>
            <view class="flex-col group_2">
              <view class="flex-col justify-start items-center text-wrapper_2" @tap="editUserInfo(user.id)">
                <text class="font text_5">编辑/查看</text>
              </view>
              <view v-if="user.userStatus==0" class="mt-10 flex-col justify-start items-center text-wrapper_3" @tap="changeUserStatus(user.id)">
                <text class="font text_8">禁用用户</text>
              </view>
              <view v-else class="mt-10 flex-col justify-start items-center .text-wrapper_3_1" @tap="changeUserStatus(user.id)">
                <text class="font text_8">启用用户</text>
              </view>
            </view>
          </view>
          <view class="flex-row justify-between mt-5">
            <view class="flex-row self-start">
              <text class="font_2 text_9">用户状态：</text>
              <text class="font_3 text_10">{{ USER_STATUS[user.userStatus] }}</text>
            </view>
            <view class="flex-col justify-start items-center self-center text-wrapper_4" @tap="deleteUserInfo(user.id)">
              <text class="font text_11">删除用户</text>
            </view>
          </view>
        </view>
      </view>
    </view>
  </view>
  <view v-if="loading" class="loading">加载中...</view>
  <view v-if="!hasMoreData" class="no-more-data">到底了</view>
</view>
</template>

<script>
import {USER_GENDER,USER_STATUS} from "@/common/constants";
import request from "@/api/api";
export default {
  computed: {
    USER_STATUS() {
      return USER_STATUS
    },
    USER_GENDER() {
      return USER_GENDER
    }
  },
  components: {},
  props: {},
  data() {
    return {
      username:"",
      // 用户信息列表
      users:[],
      // 用户状态对象
      userStatusChange:{
        id:null,
        userStatus:null
      },
      // 分页展示相关信息
      total:0,
      size:5,
      current:1,
      loading: false,
      hasMoreData: true,
    };
  },
  mounted() {
    // 在这里调用后端接口获取数据，然后更新user
    this.getUsersList();
  },
  methods: {
    getDataByUserName(){
      let username = this.username.trim();
      if (username === ""){
        this.current=1;
        this.hasMoreData = true;
        this.users=[];
        this.getUsersList();
        return;
      }
      const url = `users/search?name=${username}`;
      request(url, "GET")
          .then(responseData => {
            if (responseData.code == 0) {
              this.users = responseData.data; // 更新数据列表
            } else {
              this.showToast("Request failed:" + responseData.message);
            }
            this.hasMoreData = false;
          })
          .catch(error => {
            console.error("Request failed:", error);
            this.showToast("Request failed:" + error);
          });
    },
    // 更改用户状态
    changeUserStatus(userId){
      let user = this.users.find(user => user && user.id === userId);
      this.userStatusChange.id = user.id;
      if (user.userStatus==1){
        this.userStatusChange.userStatus = 0;
      }else {
        this.userStatusChange.userStatus = 1;
      }
      const url = "users/status";
      request(url,"PUT",this.userStatusChange).then(response =>{
        if(response.code==0){
          this.showToast("用户状态信息更改成功！");
          user.userStatus = this.userStatusChange.userStatus;
        }else {
          this.showToast("用户状态信息更改失败！"+response.message);
        }
      }).catch(error=>{
        this.showToast("用户状态信息更改失败！"+error.errMsg);
      })
    },
    // 删除用户信息操作
    deleteUserInfo(userId){
      uni.showModal({
        title: '提示', // 弹窗标题
        content: '确认要删除此用户信息吗？此操作无法撤回！', // 弹窗内容
        showCancel: true, // 显示取消按钮
        confirmText: '确定', // 确定按钮的文字，默认为"确定"
        success: (res) => {
          if (res.confirm) {
            this.doDeleteUserInfo(userId);
          }
        }
      });

    },
    // 确认删除用户信息
    doDeleteUserInfo(userId){
      const url = `users?userId=${userId}`;
      request(url,"DELETE").then(response=>{
        if (response.code == 0){
          this.showToast("用户信息删除成功！");
          setTimeout( () => {
            uni.redirectTo({url:"/pages/admin/info_manage/userInfo_manage/userInfo_manage"});
          }, 500)

        }else {
          this.showToast("用户信息删除失败"+response.message);
        }
      }).catch(error=>{
        this.showToast(error.errMsg);
      })
    },
    // 编辑查看用户信息
    editUserInfo(userId){
      const url = `/pages/admin/info_manage/userInfo_manage/userInfo_edit/userInfo_edit?userId=${encodeURIComponent(userId)}`;
      uni.navigateTo({
        url: url
      });
    },
    // 获取用户信息列表
    getUsersList() {
      const url = `users?pageNum=${this.current}&size=${this.size}`;
      request(url, "GET")
          .then(responseData => {
            if (responseData.code == 0) {
              console.log("Request successful:", responseData);

              this.users = this.users.concat(responseData.data.records); // 更新数据列表
              this.total = responseData.data.total; // 更新总数
              this.current++; // 更新当前页数

              this.loading = false; // 请求完成，重置 loading 状态

              // 检查是否还有更多数据，根据实际情况设置 hasMoreData
              this.hasMoreData = this.users.length < this.total;
            } else {
              this.showToast("Request failed:" + responseData.message);
              // 处理请求失败的逻辑
              this.loading = false; // 请求失败，重置 loading 状态
            }
          })
          .catch(error => {
            console.error("Request failed:", error);
            this.showToast("Request failed:" + error);
            // 处理请求失败的逻辑
            this.loading = false; // 请求失败，重置 loading 状态
          });
    },
    loadMoreData() {
      if (this.hasMoreData && !this.loading) {
        this.loading = true;
        this.getUsersList();
      }
    },
    // 跳转到子页面
    redirectToOtherPage(pathUrl){
      uni.navigateTo({url: pathUrl});
    },
    // 展示提示信息
    showToast(message){
      uni.showToast({
        title: message,
        icon: 'none', // 不显示图标
        duration: 2000 // 提示持续时间，单位毫秒
      });
    },
    // 刷新页面
    refresh(){
      uni.redirectTo({url:"/pages/admin/info_manage/userInfo_manage/userInfo_manage"});
    }
  },
  onReachBottom() {
    this.loadMoreData();
  }
};
</script>

<style scoped lang="css">
.loading {
  text-align: center;
  padding: 10px;
  color: #333;
}

.no-more-data {
  text-align: center;
  padding: 10px;
  color: #999;
}

.ml-19 {
  margin-left: 1.19rem;
}
.ml-29 {
  margin-left: 1.81rem;
}
.mt-17 {
  margin-top: 1.06rem;
}
.ml-15 {
  margin-left: 0.94rem;
}
.mt-5 {
  margin-top: 0.31rem;
}
.page {
  padding-bottom: 37.88rem;
  background-color: #e7f5ff;
  width: 100%;
  overflow-y: auto;
  overflow-x: hidden;
  height: 100%;
}
.section {
  padding: 2.13rem 1.69rem 2.13rem 2.06rem;
  background-color: #ffffff;
  box-shadow: 0rem 0.13rem 0.25rem #00000040;
}
.section_2 {
  padding: 0.25rem 0.56rem;
  background-color: #f5f5f5;
  border-radius: 0.63rem;
  height: 3rem;
}
.image {
  width: 2.5rem;
  height: 2.5rem;
}
.text {
  color: #808080;
  font-size: 1rem;
  font-family: SourceHanSerifCN;
  line-height: 0.93rem;
}
.text-wrapper {
  margin-top: 0.88rem;
  padding: 0.5rem 0;
  background-color: #2a82e4;
  border-radius: 0.31rem;
  height: 1.88rem;
}
.font {
  font-size: 0.88rem;
  font-family: SourceHanSerifCN;
  line-height: 0.78rem;
  color: #ffffff;
}
.text_2 {
  margin: 0 0.38rem;
  font-size: 0.81rem;
  line-height: 0.75rem;
}
.group {
  padding: 0 1rem;
}
.section_3 {
  opacity: 0.7;
  background-color: #f2f2f2;
  border-radius: 0.63rem 0.63rem 0rem 0rem;
  height: 2.63rem;
  border-left: solid 0.063rem #e5e5e5;
  border-right: solid 0.063rem #e5e5e5;
  border-top: solid 0.063rem #e5e5e5;
  border-bottom: solid 0.063rem #e5e5e5;
}
.text_3 {
  color: #000000;
  font-size: 1.25rem;
  font-family: SourceHanSerifCN;
  line-height: 1.15rem;
}
.pos {
  position: absolute;
  left: 1.08rem;
  top: 50%;
  transform: translateY(-50%);
}
.section_4 {
  padding: 1rem 2.13rem 1.38rem;
  background-color: #ffffff;
  border-radius: 0rem 0rem 0.63rem 0.63rem;
  border-left: solid 0.063rem #e5e5e5;
  border-right: solid 0.063rem #e5e5e5;
  border-top: solid 0.063rem #e5e5e5;
  border-bottom: solid 0.063rem #e5e5e5;
}
.font_2 {
  font-size: 0.88rem;
  font-family: SourceHanSerifCN;
  line-height: 0.78rem;
  color: #383838;
}
.text_4 {
  line-height: 0.81rem;
}
.font_3 {
  font-size: 0.88rem;
  font-family: SourceHanSerifCN;
  line-height: 0.78rem;
  color: #808080;
}
.text_6 {
  line-height: 0.8rem;
}
.text_7 {
  line-height: 0.67rem;
}
.group_2 {
  margin-right: 0.25rem;
}
.text-wrapper_2 {
  padding: 0.38rem 0;
  background-color: #ff8d1a;
  border-radius: 0.31rem;
  width: 5rem;
}
.text_5 {
  font-size: 0.81rem;
  line-height: 0.83rem;
}
.text-wrapper_3 {
  padding: 0.38rem 0;
  background-color: #a9a9a9;
  border-radius: 0.31rem;
  width: 5rem;
}
.text-wrapper_3_1 {
  padding: 0.38rem 0;
  background-color: #5aa4ae;
  border-radius: 0.31rem;
  width: 5rem;
}
.text_8 {
  font-size: 0.81rem;
  line-height: 0.75rem;
}
.text_9 {
  line-height: 0.81rem;
}
.text_10 {
  line-height: 0.8rem;
}
.text-wrapper_4 {
  margin-right: 0.25rem;
  padding: 0.38rem 0;
  background-color: #ff5733;
  border-radius: 0.31rem;
  width: 5rem;
  height: 1.56rem;
}
.text_11 {
  font-size: 0.81rem;
  line-height: 0.76rem;
}
</style>