<template>
  <view class="flex-col justify-start page">
    <view class="flex-col group">
      <view class="flex-col justify-start items-center header"><text class="text">编辑预约信息</text></view>
      <view class="flex-col group_1">
        <view class="flex-col justify-start items-center text-wrapper"><text class="font">预约者信息</text></view>
        <view class="flex-col section">
          <view class="flex-row justify-between items-center">
            <view class="flex-col items-center">
              <text class="font_2">姓名：</text>
                <text class="mt-20 font_2">性别：</text>
              </view>
              <view class="flex-col items-start group_2">
                <view class="section_2" style="position: relative;">
                  <input type="text" v-model="user.name" @input="getUsersPrompt" placeholder="请输入用户姓名" />
                  <view v-if="usersPrompt.length > 0" class="prompt-box">
                    <ul>
                      <li v-for="(item, index) in usersPrompt" :key="index" @click="selectUserPromptInfo(item)" >
                        {{ item.name }}
                      </li>
                    </ul>
                  </view>
                </view>
                <view class="mt-10 section_2">
                  <text>{{USER_GENDER[user.gender]}}</text>
                </view>
              </view>
            </view>
            <view class="mt-10 flex-col">
              <view class="flex-row items-center">
                <text class="font_2 text_2">证件类型：</text>
                <view class="ml-18 flex-1 section_3">
                  <text>{{CARD_TYPE[user.cardType]}}</text>
                </view>
              </view>
              <view class="mt-10 flex-row items-center">
                <text class="font_2 text_3">证件号码：</text>
                <view class="ml-18 flex-1 section_3">
                  <text>{{user.card}}</text>
                </view>
              </view>
              <view class="mt-10 flex-row items-center">
                <text class="font_2 text_4">手机号码：</text>
                <view class="ml-18 flex-1 section_3">
                  <text>{{user.phone}}</text>
                </view>
              </view>
            </view>
          </view>
        </view>
        <view class="flex-col mt-45">
          <view class="flex-col justify-start items-center text-wrapper">
            <text class="font">预约信息</text>
          </view>
          <view class="flex-col section">
            <view class="flex-row items-center">
              <text class="font_2 text_5">疫苗名称：</text>
              <view class="ml-18 flex-1 section_3" style="position: relative;">
                <input type="text" v-model="plan.vcName" @input="getPlansPrompt('plan')" placeholder="请输入疫苗名称" />
                <view v-if="plansPrompt.length > 0" class="prompt-box">
                  <ul>
                    <li v-for="(item, index) in plansPrompt" :key="index" @click="selectPlanPromptInfo(item)" >
                      {{ item.vcName }}
                    </li>
                  </ul>
                </view>
              </view>
            </view>
            <view class="mt-10 flex-row items-center">
              <text class="font_2 text_6">疫苗编号：</text>
              <view class="ml-18 flex-1 section_3">
                <text>{{plan.vcNumber}}</text>
              </view>
            </view>
            <view class="mt-10 flex-row items-center">
              <text class="font_2 text_7">预约地点：</text>
              <view class="ml-18 flex-1 section_3">
                <view class="ml-18 flex-1 section_3" style="position: relative;margin: 0;">
                  <input v-model="plan.place" type="text" @tap="getPlansPrompt('place')" placeholder="请选择预约地点" disabled/>

                  <view v-if="placePrompt.length > 0" class="prompt-box">
                    <ul>
                      <li v-for="(item, index) in placePrompt" :key="index" @click="selectPlaceInfo(item)" >
                        {{ item.place }}
                      </li>
                    </ul>
                  </view>
                </view>
              </view>
            </view>
            <view class="mt-10 flex-row items-center">
              <text class="font_2 text_8">参考价格：</text>
              <view class="ml-18 flex-1 section_3">
                <text>{{plan.price}}</text>
              </view>
            </view>
            <view class="mt-10 flex-row items-center">
              <text class="font_2 text_9">预约剂次：</text>
              <view class="ml-18 flex-1 section_3" style="position: relative;">
                <input v-model="plan.dose" type="text" @tap="getDosesPrompt()" placeholder="请选择预约剂次" disabled/>

                <view v-if="tempDose.length > 0" class="prompt-box">
                  <ul>
                    <li v-for="(item, index) in tempDose" :key="index" @click="selectDoseInfo(item)" >
                      {{ item }}
                    </li>
                  </ul>
                </view>
              </view>
            </view>
            <view class="mt-10 flex-row items-center">
              <text class="font_2 text_10">预约时间：</text>
              <view class="ml-18 flex-1 section_3" style="position: relative;">
                <input v-model="plan.rvDateTime" type="text" @tap="getRvDateTimePrompt()" placeholder="请选择预约时间" disabled/>

                <view v-if="tempRvTime.length > 0" class="prompt-box"  style="overflow-y: auto; max-height: 10em;">
                  <ul>
                    <li v-for="(item, index) in tempRvTime" :key="index" @click="selectRvDateTimeInfo(item)" >
                      {{ item }}
                    </li>
                  </ul>
                </view>
              </view>
            </view>
            <view class="mt-10 flex-row items-center">
              <text class="font_2">预约状态：</text>
              <view class="ml-18 flex-1 section_3" style="position: relative;">
                <input v-model="plan.rvStatus" type="text" @tap="getRvStatusPrompt()" placeholder="请选择预约状态" disabled/>

                <view v-if="tempRvStatus.length > 0" class="prompt-box">
                  <ul>
                    <li v-for="(item, index) in tempRvStatus" :key="index" @click="selectRvStatusInfo(item)" >
                      {{ item }}
                    </li>
                  </ul>
                </view>
              </view>
            </view>
          </view>
        </view>
          <view class="flex-col justify-start self-center text-wrapper_2 mt-57" @tap="submitReservationInfo">
            <text class="font_2 text_11">保存修改</text>
          </view>
      </view>
    </view>
</template>

<script>
import request from "@/api/api";
import {CARD_TYPE, RESERVATION_STATUS, USER_GENDER} from "@/common/constants";

export default {
  computed: {
    CARD_TYPE() {
      return CARD_TYPE
    },
    USER_GENDER() {
      return USER_GENDER
    }
  },
  onLoad(options) {
    // 通过options对象接收传递的参数
    const reservationId = decodeURIComponent(options.reservationId); // 如果你之前使用了encodeURIComponent编码参数
    if(reservationId){
      this.getReservationInfo(reservationId);
    }
  },
  components: {},
  props: {},
  data() {
    return {
      // 用户信息存放对象
      user:{
        id:null,
        name:"",
        gender:null,
        cardType:null,
        card:"",
        phone:""
      },
      // 预约信息存放对象
      plan:{
        planId:null,
        rvDateTime:"",
        vaccineId:null,
        vcNumber:"",
        vcName:"",
        price:"",
        place:"",
        dose:"",
        rvStatus:""
      },
      // 提交信息
      submitForm:{
        id:null,
        userId: null,
        vaccineId: null,
        planId: null,
        nowDose: null,
        nowTime: null,
        rvStartDatetime: null,
        rvEndDatetime: null,
        rvStatus: null
      },

      // 用户信息提示列表
      usersPrompt:[],
      // 预约计划提示列表
      plansPrompt:[],
      // 预约地点提示列表
      placePrompt:[],

      // 临时计划对象存放
      tempPlan:{},
      // 剂次列表
      tempDose:[],
      // 预约时间列表
      tempRvTime:[],
      // 预约状态列表
      tempRvStatus:[],

      // 最大提示信息数量
      size:5
    };
  },

  methods: {
    // 获取预约信息
    getReservationInfo(reservationId){
      const url = `reservations/id?reservationId=${reservationId}`;
      request(url, "GET")
          .then(responseData => {
            if (responseData.code == 0) {
              console.log("Request successful:", responseData);
              this.user.id = responseData.data.id;
              this.user.name = responseData.data.name;
              this.user.gender = responseData.data.gender;
              this.user.cardType = responseData.data.cardType;
              this.user.card = responseData.data.card;
              this.user.phone = responseData.data.phone;

              this.plan.planId=responseData.data.planId;
              this.plan.rvDateTime=responseData.data.rvDateTime;
              this.plan.vaccineId=responseData.data.vaccineId;
              this.plan.vcNumber=responseData.data.vcNumber;
              this.plan.vcName=responseData.data.vcName;
              this.plan.price=responseData.data.price;
              this.plan.place=responseData.data.place;
              this.plan.dose=responseData.data.nowDose;
              for (let key in RESERVATION_STATUS) {
                console.log(typeof key);
                if (key == responseData.data.rvStatus){
                  this.plan.rvStatus = RESERVATION_STATUS[key];
                }else if (responseData.data.rvStatus == 1){
                  this.plan.rvStatus = "已接种-有剩余剂次";
                }else if(responseData.data.rvStatus == 2){
                  this.plan.rvStatus = "已接种-无剩余剂次";
                }
              }

              // 设置预约信息对应的id
              this.submitForm.id = responseData.data.id;

              // 获取对应的预约计划信息
              let vcName = this.plan.vcName.trim();
              const url = `plan/vcName?vcName=${vcName}&pageNum=1&size=${this.size}`;
              request(url, "GET")
                  .then(responseData => {
                    if (responseData.code === 0) {
                        let infos = responseData.data.records; // 更新数据列表
                        for (const index in infos) {
                          if(infos[index].planId === this.plan.planId){
                            this.tempPlan =  infos[index];
                          }
                        }
                        this.plansPrompt = [];
                      }
                  });
            } else {
              this.showToast("Request failed:" + responseData.message);
            }
          })
          .catch(error => {
            console.error("Request failed:", error);
            this.showToast("Request failed:" + error);
          });
    },
    // 提交预约信息
    submitReservationInfo(){
      this.submitForm.userId = this.user.id;
      this.submitForm.vaccineId = this.plan.vaccineId;
      this.submitForm.planId = this.plan.planId;
      this.submitForm.nowDose = this.plan.dose;
      this.submitForm.nowTime = new Date().getTime();
      let dateTime = this.plan.rvDateTime.split(" ");
      let date = dateTime[0];
      let startTime = dateTime[1].split("~")[0];
      let endTime = dateTime[1].split("~")[1];
      this.submitForm.rvStartDatetime = Date.parse(date + " " + startTime);
      this.submitForm.rvEndDatetime =  Date.parse(date + " " + endTime);
      for (let key in RESERVATION_STATUS) {
        if (RESERVATION_STATUS[key] === this.plan.rvStatus){
          this.submitForm.rvStatus = key;
        }else if (this.plan.rvStatus === "已接种-有剩余剂次"){
          this.submitForm.rvStatus = 1;
        }else if(this.plan.rvStatus === "已接种-无剩余剂次"){
          this.submitForm.rvStatus = 2;
        }
      }

      request("reservations","PUT",this.submitForm)
          .then(responseData => {
            if (responseData.code == 0) {
              console.log("Request successful:", responseData);
              // 新增信息成功后，显示需要点击的弹窗提示
              uni.showModal({
                title: '提示', // 弹窗标题
                content: '更新预约信息成功', // 弹窗内容
                showCancel: false, // 不显示取消按钮
                confirmText: '确定', // 确定按钮的文字，默认为"确定"
                success: function (res) {
                  if (res.confirm) {
                    const pages = getCurrentPages();
                    const prevPage = pages[pages.length - 2]; // 上一个页面
                    uni.navigateBack({
                      success: () => {
                        prevPage.$vm.refresh(); // 直接调用上个页面的刷新方法
                      }
                    });                  }
                }
              });
            } else {
              this.showToast("更新预约信息失败："+responseData.message)
            }
          }).catch(error => {
        this.showToast(error.errMsg);
      });

    },

    // 选中用户提示信息，将对应信息记录并展示
    selectUserPromptInfo(item){

      this.user.id = item.id;
      this.user.name = item.name;
      this.user.gender = item.gender;
      this.user.cardType = item.cardType;
      this.user.card = item.card;
      this.user.phone = item.phone;
      // 将提示列表归零
      this.usersPrompt = [];
    },
    selectPlanPromptInfo(item){
      this.tempPlan = item

      this.plan.planId = item.planId;
      this.plan.vaccineId = item.vaccineId;

      this.plan.vcNumber = item.vcNumber;
      this.plan.vcName = item.vcName;
      this.plan.price = item.price;

      // 归零相应的剂次等信息
      this.plan.place = "";
      this.plan.dose= "";
      this.plan.rvDateTime = "";
      // 将提示列表归零
      this.plansPrompt = [];
    },
    selectPlaceInfo(item){
      this.plan.place = item.place;
      this.tempPlan = item

      this.plan.planId = item.planId;
      this.plan.vaccineId = item.vaccineId;

      // 归零相应的剂次等信息
      this.plan.dose= "";
      this.plan.rvDateTime = "";
      // 将提示列表归零
      this.placePrompt = [];
    },
    selectDoseInfo(item){
      this.plan.dose = item;
      this.tempDose = [];
    },
    selectRvDateTimeInfo(item){
      this.plan.rvDateTime = item;
      this.tempRvTime = [];
    },
    selectRvStatusInfo(item){
      this.plan.rvStatus = item;
      this.tempRvStatus=[];
    },
    // 获取预约状态信息提示信息
    getRvStatusPrompt(){
      this.tempRvStatus = ["已预约","已接种-有剩余剂次","已接种-无剩余剂次","已取消"];
    },
    // 获取预约时间提示信息
    getRvDateTimePrompt(){
      this.tempRvTime = this.tempPlan.rvDateTime;
    },
    // 获取剂次提示信息
    getDosesPrompt(){
      this.tempDose = [];
      let doses = this.tempPlan.dose;
      for (let i = 0; i<doses; i++){
        this.tempDose.push(i+1);
      }
    },
    // 获取预约计划提示信息
    getPlansPrompt(type){
      let vcName = this.plan.vcName.trim();
      const url = `plan/vcName?vcName=${vcName}&pageNum=1&size=${this.size}`;
      request(url, "GET")
          .then(responseData => {
            if (responseData.code == 0) {
              console.log("Request successful:", responseData);
              if (type==="plan"){
                this.plansPrompt = responseData.data.records; // 更新数据列表
              }else if(type==="place"){
                this.placePrompt = responseData.data.records; // 更新数据列表
              }
            } else {
              this.showToast("Request failed:" + responseData.message);
            }
          })
          .catch(error => {
            console.error("Request failed:", error);
            this.showToast("Request failed:" + error);
          });
    },
    // 获取用户提示信息
    getUsersPrompt(){
      let name = this.user.name.trim();
      const url = `users/search?name=${name}&pageNum=1&size=${this.size}`;
      request(url, "GET")
          .then(responseData => {
            if (responseData.code == 0) {
              console.log("Request successful:", responseData);

              this.usersPrompt = responseData.data; // 更新数据列表
            } else {
              this.showToast("Request failed:" + responseData.message);
            }
          })
          .catch(error => {
            console.error("Request failed:", error);
            this.showToast("Request failed:" + error);
          });
    },
    // 展示提示信息
    showToast(message){
      uni.showToast({
        title: message,
        icon: 'none', // 不显示图标
        duration: 2000 // 提示持续时间，单位毫秒
      });
    }
  },
};
</script>

<style scoped lang="css">
/*提示框*/
.prompt-box {
  position: absolute;
  top: 100%;
  left: 0;
  width: 100%;
  z-index: 999; /* 可以根据需要设置合适的 z-index 值 */
  background-color: white;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.mt-45 {
  margin-top: 2.81rem;
}
.page {
  background-color: #e7f5ff;
  width: 100%;
  overflow-y: auto;
  overflow-x: hidden;
  height: 100%;
}
.group {
  padding-bottom: 10.69rem;
  overflow-y: auto;
}
.header {
  padding: 0.74rem 0 0.65rem;
  background-color: #f2f2f2;
  border-left: solid 0.063rem #e5e5e5;
  border-right: solid 0.063rem #e5e5e5;
  border-top: solid 0.063rem #e5e5e5;
  border-bottom: solid 0.072rem #ffffff;
}
.text {
  color: #000000;
  font-size: 1.25rem;
  font-family: SourceHanSerifCN;
  line-height: 1.17rem;
}
.group_1 {
  margin-top: 1.68rem;
}
.text-wrapper {
  padding: 0.54rem 0 0.29rem;
  background-color: #00baad80;
}
.font {
  font-size: 1.13rem;
  font-family: SourceHanSansCN;
  line-height: 1.05rem;
  color: #000000;
}
.section_1 {
  padding: 0.99rem 3.21rem 1.85rem 3.33rem;
  background-color: #ffffff;
  border-left: solid 0.063rem #e5e5e5;
  border-right: solid 0.063rem #e5e5e5;
  border-top: solid 0.063rem #e5e5e5;
  border-bottom: solid 0.063rem #e5e5e5;
}
.font_2 {
  font-size: 1rem;
  font-family: SourceHanSerifCN;
  line-height: 0.92rem;
  color: #383838;
}
.group_2 {
  width: 14.07rem;
}
.section_2 {
  background-color: #ffffff;
  width: 11.44rem;
  height: 1.56rem;
  border-bottom: solid 0.05rem #000000;
}
.text_2 {
  line-height: 0.92rem;
}
.section_3 {
  margin-right: 1.43rem;
  background-color: #ffffff;
  height: 1.56rem;
  border-bottom: solid 0.05rem #000000;
}
.text_3 {
  line-height: 0.92rem;
}
.text_4 {
  line-height: 0.92rem;
}
.section {
  padding: 0.99rem 3.21rem 1.82rem 3.33rem;
  background-color: #ffffff;
  border-left: solid 0.063rem #e5e5e5;
  border-right: solid 0.063rem #e5e5e5;
  border-top: solid 0.063rem #e5e5e5;
  border-bottom: solid 0.063rem #e5e5e5;
}
.text_5 {
  line-height: 0.93rem;
}
.text_6 {
  line-height: 0.93rem;
}
.text_7 {
  line-height: 0.93rem;
}
.text_8 {
  line-height: 0.92rem;
}
.text_9 {
  line-height: 0.93rem;
}
.text_10 {
  line-height: 0.93rem;
}
.text-wrapper_2 {
  margin-top: 3.59rem;
  padding: 0.87rem 0 0.7rem;
  background-color: #2a82e4;
  border-radius: 0.31rem;
}
.text_11 {
  margin-left: 0.54rem;
  margin-right: 0.4rem;
  color: #ffffff;
  line-height: 0.93rem;
}
</style>