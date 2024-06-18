<template>
<view class="flex-col page">
  <view class="flex-row justify-center items-center relative section">
    <view class="flex-row items-center section_2">
      <image
        class="image"
        src="/static/global/searchBar/搜索.png"
      />
      <view class="text ml-19">
        <input type="text" v-model="title" @blur="getDataByTitle" placeholder="搜索文章标题" />
      </view>
    </view>
    <view class="flex-col justify-start text-wrapper ml-27" @tap="redirectToOtherPage('/pages/admin/info_manage/popularScienceInfo_manage/popularScienceInfo_add/popularScienceInfo_add')">
      <text class="font text_2">新增文章信息</text>
    </view>
  </view>

  <view class="flex-col list">
    <view class="flex-col list-item mt-29" v-for="(popularScienceInfo, index) in popularScienceInfos" :key="index">
    <view class="flex-col section_4 mt-33">
    <view class="flex-col justify-start self-stretch relative">
      <view class="section_3"></view>
      <text class="text_3 pos">{{ popularScienceInfo.title }}</text>
    </view>
    <view class="flex-row self-start group">
      <text class="shrink-0 self-start text_4">文章内容：</text>
      <text class="flex-1 font_2">
        {{ popularScienceInfo.content.length > maxLength ? popularScienceInfo.content.substring(0, maxLength) + '...' : popularScienceInfo.content }}
      </text>
    </view>
    <view class="flex-row items-center self-stretch group_2">
      <text class="shrink-0 font text_5">创建日期：</text>
      <text class="font_2 text_6">{{ popularScienceInfo.createTime }}</text>
    </view>
    <view class="flex-row justify-between self-stretch group_3">
      <view class="flex-col justify-start items-center text-wrapper_2" @tap="showPopularScienceInfo(popularScienceInfo.id)">
        <text class="font">预览</text>
      </view>
      <view class="flex-col justify-start items-center text-wrapper_3" @tap="editPopularScienceInfo(popularScienceInfo.id)">
        <text class="font">编辑</text>
      </view>
      <view class="flex-col justify-start items-center text-wrapper_4" @tap="deletePopularScienceInfo(popularScienceInfo.id)">
        <text class="font text_7">删除</text>
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
import request from "@/api/api";

export default {
  components: {},
  props: {},
  data() {
    return {
      title:"",
      // 用户信息列表
      popularScienceInfos:[],
      // 文章展示字符数
      maxLength:35,

      // 分页展示相关信息
      total:0,
      size:5,
      current:1,
      loading: false,
      hasMoreData: true
    };
  },
  mounted() {
    // 在这里调用后端接口获取数据，然后更新popularScience
    this.getPopularSciencesList();
  },
  methods: {
    // 更改用户状态
    showPopularScienceInfo(popularId){
      uni.navigateTo({
        url: `/pages/user/index/knowledge/article_display/article_display?popularId=${encodeURIComponent(popularId)}`});
    },
    // 删除用户信息操作
    deletePopularScienceInfo(popularId){
      uni.showModal({
        title: '提示', // 弹窗标题
        content: '确认要删除此科普信息吗？此操作无法撤回！', // 弹窗内容
        showCancel: true, // 显示取消按钮
        confirmText: '确定', // 确定按钮的文字，默认为"确定"
        success: (res) => {
          if (res.confirm) {
            this.doDeletePopularScienceInfo(popularId);
          }
        }
      });

    },
    // 确认删除用户信息
    doDeletePopularScienceInfo(popularId){
      const url = `popular?popularId=${popularId}`;
      request(url,"DELETE").then(response=>{
        if (response.code == 0){
          this.showToast("科普信息删除成功！");
          setTimeout( () => {
            uni.redirectTo({url:"/pages/admin/info_manage/popularScienceInfo_manage/popularScienceInfo_manage"});
          }, 500)

        }else {
          this.showToast("科普信息删除失败:"+response.message);
        }
      }).catch(error=>{
        this.showToast(error.errMsg);
      })
    },
    // 编辑查看用户信息
    editPopularScienceInfo(popularId){
      const url = `/pages/admin/info_manage/popularScienceInfo_manage/popularScienceInfo_edit/popularScienceInfo_edit?popularId=${encodeURIComponent(popularId)}`;
      uni.navigateTo({
        url: url
      });
    },
    getDataByTitle(){
      let title = this.title.trim();
      if (title === ""){
        this.current=1;
        this.hasMoreData = true;
        this.popularScienceInfos=[];
        this.getPopularSciencesList();
        return;
      }
      const url = `populars/search?title=${title}`;
      request(url, "GET")
          .then(responseData => {
            if (responseData.code == 0) {
              this.popularScienceInfos = responseData.data; // 更新数据列表
              // 调整正文内容格式
              for (let index in this.popularScienceInfos) {
                this.popularScienceInfos[index].content = this.popularScienceInfos[index].content.replace(/<[^>]*>/g, '').replaceAll("&nbsp;","");
              }
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
    // 获取科普信息列表
    getPopularSciencesList(){
      const url = `popular?pageNum=${this.current}&size=${this.size}`;
      request(url, "GET")
          .then(responseData => {
            if (responseData.code == 0) {
              console.log("Request successful:", responseData);

              this.popularScienceInfos = this.popularScienceInfos.concat(responseData.data.records); // 更新数据列表
              // 调整正文内容格式
              for (let index in this.popularScienceInfos) {
                this.popularScienceInfos[index].content = this.popularScienceInfos[index].content.replace(/<[^>]*>/g, '').replaceAll("&nbsp;","");
              }

              this.total = responseData.data.total; // 更新总数
              this.current++; // 更新当前页数

              this.loading = false; // 请求完成，重置 loading 状态

              // 检查是否还有更多数据，根据实际情况设置 hasMoreData
              this.hasMoreData = this.popularScienceInfos.length < this.total;
            } else {
              this.showToast("获取科普信息失败：" + responseData.message);
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
        this.getPopularSciencesList();
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
      uni.redirectTo({url:"/pages/admin/info_manage/popularScienceInfo_manage/popularScienceInfo_manage"});
    }
  },
  // 下拉刷新
  onReachBottom() {
    this.loadMoreData();
  }
};
</script>

<style scoped lang="css">
.ml-19 {
  margin-left: 1.19rem;
}
.ml-27 {
  margin-left: 1.69rem;
}
.mt-33 {
  margin-top: 2.06rem;
}
.page {
  padding-bottom: 37.41rem;
  background-color: #e7f5ff;
  width: 100%;
  overflow-y: auto;
  overflow-x: hidden;
  height: 100%;
}
.section {
  padding: 2.13rem 1.75rem 2.06rem 2.06rem;
  background-color: #ffffff;
  box-shadow: 0rem 0.13rem 0.25rem #00000040;
}
.section_2 {
  padding: 0.25rem 0.56rem 0.25rem;
  background-color: #f5f5f5;
  border-radius: 0.63rem;
  width: 15.75rem;
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
  padding: 0.62rem 0 0.5rem;
  background-color: #2a82e4;
  border-radius: 0.31rem;
  height: 1.88rem;
}
.font {
  font-size: 0.81rem;
  font-family: SourceHanSerifCN;
  line-height: 0.75rem;
  color: #ffffff;
}
.text_2 {
  margin-left: 0.47rem;
  margin-right: 0.22rem;
  line-height: 0.76rem;
}
.section_4 {
  margin-left: 1.03rem;
  margin-right: 0.97rem;
  padding-bottom: 0.29rem;
  background-color: #ffffff;
  border-radius: 0.63rem;
  border-left: solid 0.063rem #e5e5e5;
  border-right: solid 0.063rem #e5e5e5;
  border-top: solid 0.063rem #e5e5e5;
  border-bottom: solid 0.063rem #e5e5e5;
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
  line-height: 1.16rem;
}
.pos {
  position: absolute;
  left: 1.06rem;
  top: 50%;
  transform: translateY(-50%);
}
.group {
  margin-left: 0.87rem;
  margin-top: 1.19rem;
  width: 19.31rem;
}
.text_4 {
  margin-top: 0.1rem;
  color: #383838;
  font-size: 0.88rem;
  font-family: SourceHanSerifCN;
  line-height: 0.82rem;
}
.font_2 {
  font-size: 0.81rem;
  font-family: SourceHanSerifCN;
  line-height: 1rem;
  color: #808080;
}
.group_2 {
  margin-left: 0.86rem;
  margin-top: 0.53rem;
}
.text_5 {
  color: #383838;
  font-size: 0.88rem;
  line-height: 0.8rem;
}
.text_6 {
  margin-right: 11.75rem;
  font-size: 0.88rem;
  line-height: 1.06rem;
  width: 7.75rem;
}
.group_3 {
  margin-top: 0.8rem;
  padding: 0.5rem 4rem;
}
.text-wrapper_2 {
  padding: 0.44rem 0 0.37rem;
  background-color: #43cf7c;
  border-radius: 0.31rem;
  width: 3.13rem;
  height: 1.56rem;
}
.text-wrapper_3 {
  padding: 0.43rem 0 0.38rem;
  background-color: #ff8d1a;
  border-radius: 0.31rem;
  width: 3.13rem;
  height: 1.56rem;
}
.text-wrapper_4 {
  margin-right: 0.96rem;
  padding: 0.44rem 0 0.37rem;
  background-color: #ff5733;
  border-radius: 0.31rem;
  width: 3.13rem;
  height: 1.56rem;
}
.text_7 {
  line-height: 0.75rem;
}
</style>