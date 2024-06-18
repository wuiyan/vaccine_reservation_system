<template>
  <view class="flex-col page">
    <view class="flex-row relative section">
      <view class="flex-row items-center flex-1 self-center section_2">
        <image
            class="image"
            src="/static/global/searchBar/搜索.png"
        />
        <view class="text ml-19">
          <input type="text" v-model="vcName" @blur="getDataByVcName" placeholder="搜索疫苗名称" />
        </view>
      </view>
      <view class="ml-22 flex-col justify-start shrink-0 self-start text-wrapper" @tap="redirectToOtherPage('/pages/admin/info_manage/vaccineInfo_manage/vaccineInfo_add/vaccineInfo_add')">
        <text class="font text_2">添加疫苗信息</text>
      </view>
    </view>
    <view class="mt-36 flex-col list">
      <view class="flex-col list-item mt-29" v-for="(vaccine, index) in vaccines" :key="index">
        <view class="flex-col justify-start relative">
          <view class="section_3"></view>
          <text class="font_2 pos">{{ vaccine.vcName }}</text>
        </view>
        <view class="flex-row justify-between group view">
          <view class="flex-col">
            <view class="flex-row items-baseline">
              <text class="font_3 text_3">疫苗编号：</text>
              <text class="font_4">{{ vaccine.vcNumber }}</text>
            </view>
            <view class="mt-16 flex-row items-baseline">
              <text class="font_3">参考价格：</text>
              <text class="font_4">￥{{vaccine.price}}</text>
            </view>
          </view>
          <view class="flex-col justify-start items-center self-start text-wrapper_2" @tap="editVaccineInfo(vaccine.id)">
            <text class="font text_4">编辑/查看</text>
          </view>
        </view>
        <view class="flex-row justify-between items-center group view_2">
          <view class="flex-row items-center">
            <text class="font_3 text_5">剂次：</text>
            <text class="ml-30 font_4 text_6">{{ vaccine.dose }}</text>
          </view>
          <view class="flex-col justify-start items-center text-wrapper_3" @tap="deleteVaccineInfo(vaccine.id)">
            <text class="font text_7">删除疫苗</text></view>
        </view>
        <view class="flex-row group view_3">
          <text class="font_3 text_8">地点：</text>
          <text class="font_5 ml-29">{{ vaccine.place }}</text>
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
      vcName:"",
      // 疫苗信息列表
      vaccines:[],
      // 分页展示相关信息
      total:0,
      size:5,
      current:1,
      loading: false,
      hasMoreData: true,
    };
  },
  mounted() {
    // 在这里调用后端接口获取数据，然后更新vaccines
    this.getVaccinesList();
  },
  methods: {
    // 删除疫苗信息操作
    deleteVaccineInfo(vaccineId){
      uni.showModal({
        title: '提示', // 弹窗标题
        content: '确认要删除疫苗信息吗？此操作无法撤回！', // 弹窗内容
        showCancel: true, // 显示取消按钮
        confirmText: '确定', // 确定按钮的文字，默认为"确定"
        success: (res) => {
          if (res.confirm) {
            this.doDeleteVaccineInfo(vaccineId);
          }
        }
      });

    },
    // 确认删除疫苗信息
    doDeleteVaccineInfo(vaccineId){
      const url = `vaccines?vaccineId=${vaccineId}`;
      request(url,"DELETE").then(response=>{
        if (response.code == 0){
            this.showToast("疫苗信息删除成功！");
          setTimeout( () => {
            uni.redirectTo({url:"/pages/admin/info_manage/vaccineInfo_manage/vaccineInfo_manage"});
          }, 500)

        }else {
            this.showToast("疫苗信息删除失败"+response.message);
        }
      }).catch(error=>{
            this.showToast(error.errMsg);
      })
    },
    // 编辑查看疫苗信息
    editVaccineInfo(id){
      const url = `/pages/admin/info_manage/vaccineInfo_manage/vaccineInfo_edit/vaccineInfo_edit?id=${encodeURIComponent(id)}`;
      uni.navigateTo({
        url: url
      });
    },
    getDataByVcName(){
      let vcName = this.vcName.trim();
      if (vcName === ""){
        this.current=1;
        this.hasMoreData = true;
        this.vaccines=[];
        this.getVaccinesList();
        return;
      }
      const url = `vaccines/search?vcName=${vcName}`;
      request(url, "GET")
          .then(responseData => {
            if (responseData.code == 0) {
              this.vaccines = responseData.data; // 更新数据列表
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
    // 获取疫苗信息列表
    getVaccinesList() {
      const url = `vaccines?pageNum=${this.current}&size=${this.size}`;
      request(url, "GET")
          .then(responseData => {
            if (responseData.code == 0) {
              console.log("Request successful:", responseData);

              this.vaccines = this.vaccines.concat(responseData.data.records); // 更新数据列表
              this.total = responseData.data.total; // 更新总数
              this.current++; // 更新当前页数

              this.loading = false; // 请求完成，重置 loading 状态

              // 检查是否还有更多数据，根据实际情况设置 hasMoreData
              this.hasMoreData = this.vaccines.length < this.total;
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
        this.getVaccinesList();

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
      uni.redirectTo({url:"/pages/admin/info_manage/vaccineInfo_manage/vaccineInfo_manage"});
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
.mt-29 {
  margin-top: 1.81rem;
}
.ml-29 {
  margin-left: 1.81rem;
}
.page {
  padding-bottom: 23.25rem;
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
  line-height: 0.81rem;
  color: #ffffff;
}
.text_2 {
  margin-left: 0.38rem;
  margin-right: 0.25rem;
  font-size: 0.81rem;
  line-height: 0.76rem;
}
.list {
  padding: 0 1rem;
}
.list-item {
  padding-bottom: 1.75rem;
  background-color: #ffffff;
  border-radius: 0.63rem;
  border-left: solid 0.063rem #e5e5e5;
  border-right: solid 0.063rem #e5e5e5;
  border-top: solid 0.063rem #e5e5e5;
  border-bottom: solid 0.063rem #e5e5e5;
}
.list-item:first-child {
  margin-top: 0;
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
.font_2 {
  font-size: 1.25rem;
  font-family: SourceHanSerifCN;
  line-height: 1.18rem;
  color: #000000;
}
.pos {
  position: absolute;
  left: 1.16rem;
  top: 50%;
  transform: translateY(-50%);
}
.group {
  padding: 0 2.13rem;
}
.view {
  margin-top: 1.25rem;
}
.font_3 {
  font-size: 0.88rem;
  font-family: SourceHanSerifCN;
  line-height: 0.81rem;
  color: #383838;
}
.text_3 {
  line-height: 0.81rem;
}
.text-wrapper_2 {
  margin-right: 0.38rem;
  padding: 0.38rem 0 0.25rem;
  background-color: #ff8d1a;
  border-radius: 0.31rem;
  width: 5rem;
  height: 1.56rem;
}
.text_4 {
  font-size: 0.81rem;
  line-height: 0.83rem;
}
.view_2 {
  margin-top: 0.5rem;
}
.text_5 {
  line-height: 0.81rem;
}
.font_4 {
  font-size: 0.88rem;
  font-family: SourceHanSerifCN;
  line-height: 0.67rem;
  color: #808080;
}
.text_6 {
  line-height: 0.65rem;
}
.text-wrapper_3 {
  margin-right: 0.38rem;
  padding: 0.38rem 0;
  background-color: #ff5733;
  border-radius: 0.31rem;
  width: 5rem;
  height: 1.56rem;
}
.text_7 {
  font-size: 0.81rem;
  line-height: 0.75rem;
}
.view_3 {
  margin-top: 0.75rem;
}
.text_8 {
  line-height: 0.79rem;
}
.font_5 {
  font-size: 0.88rem;
  font-family: SourceHanSerifCN;
  line-height: 0.81rem;
  color: #808080;
}
</style>