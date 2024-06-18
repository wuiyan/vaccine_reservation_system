<!--<template>-->
<!--  <view class="flex-col page">-->
<!--    <view class="flex-col self-stretch">-->
<!--      <view class="flex-col justify-start items-center text-wrapper">-->
<!--        <text class="text">编辑预约计划</text>-->
<!--      </view>-->
<!--      <view class="shrink-0 section"></view>-->
<!--      <view class="flex-col section_2">-->
<!--        <view class="flex-col">-->
<!--          <view class="flex-row justify-between items-center">-->
<!--            <text class="font text_2">标题：</text>-->
<!--            <view class="section_3 view">-->
<!--              <input v-model="showForm.planName" type="text" placeholder="请输入计划名称" disabled/>-->
<!--            </view>-->
<!--          </view>-->
<!--          <view class="mt-10 flex-row items-center">-->
<!--            <text class="font text_3">疫苗名称：</text>-->
<!--            <view class="section_3 ml-33" style="position: relative;">-->
<!--              <input v-model="showForm.vaccineName" @input="getVaccinesPrompt('vaccine')" type="text" placeholder="请输入疫苗名称" disabled/>-->
<!--              <view v-if="vaccinePrompt.length > 0 && vcFlag===0" class="prompt-box">-->
<!--                <ul>-->
<!--                  <li v-for="(item, index) in vaccinePrompt" :key="index" @tap="selectVaccinePromptInfo(item)" >-->
<!--                    {{ item.vcName }}-->
<!--                  </li>-->
<!--                </ul>-->
<!--              </view>-->
<!--            </view>-->
<!--          </view>-->
<!--        </view>-->
<!--        <view class="mt-10 flex-row items-center">-->
<!--          <text class="font text_4">疫苗编号：</text>-->
<!--          <view class="section_3 ml-33">-->
<!--            <input v-model="showForm.vaccineNumber" type="text" placeholder="请输入疫苗编号" disabled/>-->
<!--          </view>-->
<!--        </view>-->
<!--        <view class="mt-10 flex-row items-center">-->
<!--          <text class="font">接种地点：</text>-->
<!--          <view class="section_3 ml-33" style="position: relative;">-->
<!--            <input v-model="showForm.place"  type="text" placeholder="请选择接种地点" disabled/>-->
<!--            <view v-if="vaccinePrompt.length > 0 && vcFlag===1" class="prompt-box">-->
<!--              <ul>-->
<!--                <li v-for="(item, index) in vaccinePrompt" :key="index" @tap="selectPlaceInfo(item)" >-->
<!--                  {{ item.place }}-->
<!--                </li>-->
<!--              </ul>-->
<!--            </view>-->
<!--          </view>-->
<!--        </view>-->
<!--        <view class="mt-10 flex-row items-center">-->
<!--          <text class="font text_5">预约时间设置：</text>-->
<!--          <view class="flex-row justify-between items-center input section_4" @tap="redirectToTimePage('/pages/admin/info_manage/reservation_plan_manage/reservation_plan_time/reservation_plan_time')">-->
<!--            <text v-if="showForm.rvPlan.length===0" class="text_6">请配置可预约时段</text>-->
<!--            <text v-else class="text_6">已配置</text>-->
<!--            <image-->
<!--                class="image"-->
<!--                src="/static/global/common/enter.png"-->
<!--            />-->
<!--          </view>-->
<!--        </view>-->
<!--      </view>-->
<!--    </view>-->
<!--  </view>-->
<!--</template>-->

<!--<script>-->
<!--import request from "@/api/api";-->

<!--export default {-->
<!--  components: {},-->
<!--  props: {},-->
<!--  onLoad(options) {-->
<!--    // 请求预约计划信息-->
<!--    const planId = decodeURIComponent(options.planId); // 如果你之前使用了encodeURIComponent编码参数-->
<!--    if(planId){-->
<!--      this.getReservationPlanInfo(planId);-->
<!--    }-->

<!--    uni.$on('getPlans',plans=>{-->
<!--      let tempVar = JSON.parse(JSON.stringify(plans));-->
<!--      this.showForm.rvPlan = tempVar.plans;-->
<!--    })-->
<!--  },-->
<!--  onUnload() {-->
<!--    uni.$off('getPlans');-->
<!--  },-->
<!--  data() {-->
<!--    return {-->
<!--      showForm:{-->
<!--        id:null,-->
<!--        planName:"",-->
<!--        vaccineId:null,-->
<!--        vaccineName:"",-->
<!--        vaccineNumber:"",-->
<!--        place:"",-->
<!--        rvPlan:[],-->
<!--      },-->
<!--      submitForm:{-->
<!--        id:null,-->
<!--        planName:"",-->
<!--        vaccineId:null,-->
<!--        vaccineNumber:"",-->
<!--        rvPlan:"",-->
<!--      },-->

<!--      vaccinePrompt:[],-->
<!--      // 0 表示显示疫苗名称提示，1 显示地点信息提示-->
<!--      vcFlag:0,-->
<!--      // 最大提示信息数量-->
<!--      size:5-->
<!--    };-->
<!--  },-->

<!--  methods: {-->
<!--    // 获取预约计划信息-->
<!--    getReservationPlanInfo(planId){-->
<!--      const url = `plan/id?planId=${planId}`;-->
<!--      request(url, "GET")-->
<!--          .then(responseData => {-->
<!--            if (responseData.code == 0) {-->
<!--              console.log("Request successful:", responseData);-->
<!--              let plan = responseData.data;-->
<!--              this.showForm.id = plan.id;-->
<!--              this.showForm.planName = plan.planName;-->
<!--              this.showForm.vaccineId = plan.vaccineInfo.id;-->
<!--              this.showForm.vaccineName = plan.vaccineInfo.vcName;-->
<!--              this.showForm.vaccineNumber = plan.vaccineInfo.vcNumber;-->
<!--              this.showForm.place = plan.vaccineInfo.place;-->
<!--              this.showForm.rvPlan = JSON.parse(plan.rvPlan);-->
<!--            } else {-->
<!--              this.showToast("Request failed:" + responseData.message);-->
<!--            }-->
<!--          })-->
<!--          .catch(error => {-->
<!--            console.error("Request failed:", error);-->
<!--            this.showToast("Request failed:" + error);-->
<!--          });-->
<!--    },-->
<!--    // 提交表单-->
<!--    submitReservationPlan(){-->
<!--      this.submitForm.id = this.showForm.id;-->
<!--      this.submitForm.planName = this.showForm.planName;-->
<!--      this.submitForm.vaccineId = this.showForm.vaccineId;-->
<!--      this.submitForm.vaccineNumber = this.showForm.vaccineNumber;-->
<!--      this.submitForm.rvPlan = JSON.stringify(this.showForm.rvPlan);-->

<!--      request("plan","PUT",this.submitForm)-->
<!--          .then(responseData => {-->
<!--            if (responseData.code == 0) {-->
<!--              console.log("Request successful:", responseData);-->
<!--              // 新增信息成功后，显示需要点击的弹窗提示-->
<!--              uni.showModal({-->
<!--                title: '提示', // 弹窗标题-->
<!--                content: '编辑预约计划成功', // 弹窗内容-->
<!--                showCancel: false, // 不显示取消按钮-->
<!--                confirmText: '确定', // 确定按钮的文字，默认为"确定"-->
<!--                success: function (res) {-->
<!--                  if (res.confirm) {-->
<!--                    uni.redirectTo({url: "/pages/admin/info_manage/reservation_plan_manage/reservation_plan_manage"});-->
<!--                  }-->
<!--                }-->
<!--              });-->
<!--            } else {-->
<!--              this.showToast("编辑预约计划失败："+responseData.message)-->
<!--            }-->
<!--          }).catch(error => {-->
<!--        this.showToast(error.errMsg);-->
<!--      });-->
<!--    },-->
<!--    // 选择选项-->
<!--    selectPlaceInfo(item){-->
<!--      this.showForm.place = item.place;-->
<!--      this.vaccinePrompt = [];-->
<!--    },-->
<!--    selectVaccinePromptInfo(item){-->
<!--      this.showForm.vaccineId = item.id;-->
<!--      this.showForm.vaccineName = item.vcName;-->
<!--      this.showForm.vaccineNumber = item.vcNumber;-->

<!--      // 归零相应信息-->
<!--      this.showForm.place = "";-->

<!--      // 将提示列表归零-->
<!--      this.vaccinePrompt = [];-->
<!--    },-->
<!--    // 通过疫苗名称获取预约计划提示信息-->
<!--    getVaccinesPrompt(flag){-->
<!--      if (flag === "vaccine"){-->
<!--        this.vcFlag = 0;-->
<!--      }else {-->
<!--        this.vcFlag = 1;-->
<!--      }-->
<!--      let vcName = this.showForm.vaccineName.trim();-->
<!--      const url = `vaccines/vcName?vcName=${vcName}&pageNum=1&size=${this.size}`;-->
<!--      request(url, "GET")-->
<!--          .then(responseData => {-->
<!--            if (responseData.code == 0) {-->
<!--              console.log("Request successful:", responseData);-->
<!--              this.vaccinePrompt = responseData.data.records; // 更新数据列表-->
<!--            } else {-->
<!--              this.showToast("Request failed:" + responseData.message);-->
<!--            }-->
<!--          })-->
<!--          .catch(error => {-->
<!--            console.error("Request failed:", error);-->
<!--            this.showToast("Request failed:" + error);-->
<!--          });-->
<!--    },-->
<!--    // 跳转到子页面-->
<!--    redirectToTimePage(pathUrl){-->
<!--      let data = JSON.stringify(this.showForm.rvPlan)-->
<!--      uni.setStorageSync('plans',data);-->
<!--      uni.navigateTo({url: pathUrl});-->
<!--    },-->
<!--    // 展示提示信息-->
<!--    showToast(message){-->
<!--      uni.showToast({-->
<!--        title: message,-->
<!--        icon: 'none', // 不显示图标-->
<!--        duration: 2000 // 提示持续时间，单位毫秒-->
<!--      });-->
<!--    }-->
<!--  },-->
<!--};-->
<!--</script>-->

<!--<style scoped lang="css">-->
<!--/*提示框*/-->
<!--.prompt-box {-->
<!--  position: absolute;-->
<!--  top: 100%;-->
<!--  left: 0;-->
<!--  width: 100%;-->
<!--  z-index: 999; /* 可以根据需要设置合适的 z-index 值 */-->
<!--  background-color: white;-->
<!--  border: 1px solid #ccc;-->
<!--  border-radius: 4px;-->
<!--  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);-->
<!--}-->


<!--.ml-33 {-->
<!--  margin-left: 2.06rem;-->
<!--}-->

<!--.mt-116 {-->
<!--  margin-top: 7.25rem;-->
<!--}-->

<!--.page {-->
<!--  padding-bottom: 30.44rem;-->
<!--  background-color: #e7f5ff;-->
<!--  width: 100%;-->
<!--  overflow-y: auto;-->
<!--  overflow-x: hidden;-->
<!--  height: 100%;-->
<!--}-->

<!--.text-wrapper {-->
<!--  padding: 0.75rem 0 0.63rem;-->
<!--  background-color: #f2f2f2;-->
<!--  border-left: solid 0.063rem #e5e5e5;-->
<!--  border-right: solid 0.063rem #e5e5e5;-->
<!--  border-top: solid 0.063rem #e5e5e5;-->
<!--  border-bottom: solid 0.072rem #ffffff;-->
<!--}-->

<!--.text {-->
<!--  color: #000000;-->
<!--  font-size: 1.25rem;-->
<!--  font-family: SourceHanSerifCN;-->
<!--  line-height: 1.16rem;-->
<!--}-->

<!--.section {-->
<!--  background-color: #ffffff;-->
<!--  height: 0.63rem;-->
<!--  border-left: solid 0.063rem #e5e5e5;-->
<!--  border-right: solid 0.063rem #e5e5e5;-->
<!--  border-top: solid 0.063rem #e5e5e5;-->
<!--}-->

<!--.section_2 {-->
<!--  padding: 0.38rem 2.63rem 4.13rem;-->
<!--  background-color: #ffffff;-->
<!--  border-left: solid 0.063rem #e5e5e5;-->
<!--  border-right: solid 0.063rem #e5e5e5;-->
<!--  border-bottom: solid 0.063rem #e5e5e5;-->
<!--}-->

<!--.font {-->
<!--  font-size: 1rem;-->
<!--  font-family: SourceHanSerifCN;-->
<!--  line-height: 0.92rem;-->
<!--  color: #383838;-->
<!--}-->

<!--.text_2 {-->
<!--  line-height: 0.92rem;-->
<!--}-->

<!--.section_3 {-->
<!--  background-color: #ffffff;-->
<!--  width: 11.44rem;-->
<!--  height: 1.56rem;-->
<!--  border-bottom: solid 0.05rem #000000;-->
<!--}-->

<!--.view {-->
<!--  margin-right: 3rem;-->
<!--}-->

<!--.text_3 {-->
<!--  line-height: 0.93rem;-->
<!--}-->

<!--.text_4 {-->
<!--  line-height: 0.93rem;-->
<!--}-->

<!--.text_5 {-->
<!--  line-height: 0.93rem;-->
<!--}-->

<!--.input {-->
<!--  flex: 1 1 0;-->
<!--  margin-right: 3rem;-->
<!--}-->

<!--.section_4 {-->
<!--  padding: 0.13rem 0 0.13rem 1.63rem;-->
<!--  background-color: #ffffff;-->
<!--  height: 1.56rem;-->
<!--  border-bottom: solid 0.05rem #000000;-->
<!--}-->

<!--.text_6 {-->
<!--  color: #ff8d1a;-->
<!--  font-size: 0.75rem;-->
<!--  font-family: SourceHanSerifCN;-->
<!--  line-height: 0.69rem;-->
<!--}-->

<!--.image {-->
<!--  width: 1.25rem;-->
<!--  height: 1.25rem;-->
<!--}-->

<!--.text-wrapper_2 {-->
<!--  padding: 0.88rem 0 0.75rem;-->
<!--  background-color: #2a82e4;-->
<!--  border-radius: 0.31rem;-->
<!--  width: 5rem;-->
<!--}-->

<!--.text_7 {-->
<!--  color: #ffffff;-->
<!--  line-height: 0.92rem;-->
<!--}-->
<!--</style>-->

<template>
  <view class="flex-col page">
    <view class="flex-col">
      <view class="flex-col justify-start items-center text-wrapper">
        <text class="font text">{{ basePlanInfo.planName }}</text>
      </view>
      <view class="section"></view>
      <view class="flex-col section_2">
        <view class="flex-row justify-between">
          <text class="self-center font text_3">疫苗编号：</text>
          <text class="self-start font_2 text_2">{{ basePlanInfo.vaccineNumber }}</text>
        </view>
        <view class="mt-20 flex-row justify-between items-center">
          <text class="font text_4">疫苗名称：</text>
          <text class="font_2 text_5">{{ basePlanInfo.vaccineName }}</text>
        </view>
        <view class="mt-20 flex-row justify-between">
          <text class="font">接种地点：</text>
          <text class="font text_6">{{ basePlanInfo.place }}</text>
        </view>
      </view>
      <view class="section_3"></view>
    </view>
    <view class="section_4">
      <uni-calendar
          ref="calendar"
          :insert="false"
          :showMonth="false"
          :selected="selected"
          @confirm="changeShowDate"
      />
      <button style="margin-bottom: 0.5rem;width:50%" @tap="open">{{ nowSelectDate }}</button>
      <uni-table border stripe emptyText="暂无更多数据">
        <!-- 表头行 -->
        <uni-tr>
          <uni-th align="center" width="100">时间段</uni-th>
          <uni-th align="center" width="50">计划数</uni-th>
          <uni-th align="center" width="50">已预约</uni-th>
          <uni-th align="center" width="50">可预约</uni-th>
        </uni-tr>
        <!-- 表格数据行 -->
        <uni-tr v-for="(time,index) in times" :key="index">
          <uni-td align="center">{{time.timeRange}}</uni-td>
          <uni-td align="center">{{ time.planNum }}</uni-td>
          <uni-td align="center">{{time.bookedNum}}</uni-td>
          <uni-td align="center">{{ time.reservationNum }}</uni-td>
        </uni-tr>
      </uni-table>
    </view>
  </view>

</template>

<script>
import request from "@/api/api";

export default {
  components: {},
  props: {},
  onLoad(options) {
    // 请求预约计划信息
    const planId = decodeURIComponent(options.planId); // 如果你之前使用了encodeURIComponent编码参数
    if(planId){
      this.getReservationPlanInfo(planId);
    }
    },
  data() {
    return {
      // 已选择日期
      selected:[],
      // 当前查看的日期
      nowSelectDate:"选择日期",
      // 预约字段信息存放
      plans: [],
      // 基础预约计划信息
      basePlanInfo:{
        id:null,
        vaccineId:null,
        planName:"",
        vaccineNumber:"",
        vaccineName:"",
        place:""
      },
      // 时间段列表
      times:[],
    };
  },

  methods: {
    open(){
      console.log("打开日志")
      this.$refs.calendar.open();
    },
    // 更改当前显示的日期
    changeShowDate(e) {
      console.log('change 返回:', e);
      // 若已选中日期则取消选中
      for (let i = 0; i <this.plans.length; i++) {
        if(this.plans[i].date === e.fulldate){
            this.times = [];
            this.nowSelectDate = this.plans[i].date;
            for (let j=0;j<this.plans[i].plan.length;j++){
              this.times.push({
                timeRange:this.plans[i].plan[j].startTime +"~"+this.plans[i].plan[j].endTime,
                planNum:this.plans[i].plan[j].planNum,
                bookedNum:this.plans[i].plan[j].bookedNum,
                reservationNum:this.plans[i].plan[j].planNum - this.plans[i].plan[j].bookedNum
              });
            }
        }
      }
    },
    getReservationPlanInfo(planId){
      const url = `plan/id?planId=${planId}`;
      request(url, "GET")
          .then(responseData => {
            if (responseData.code == 0) {
              console.log("Request successful:", responseData);
              let plan = responseData.data;
              this.basePlanInfo.id = plan.id;
              this.basePlanInfo.planName = plan.planName;
              this.basePlanInfo.vaccineId = plan.vaccineInfo.id;
              this.basePlanInfo.vaccineName = plan.vaccineInfo.vcName;
              this.basePlanInfo.vaccineNumber = plan.vaccineInfo.vcNumber;
              this.basePlanInfo.place = plan.vaccineInfo.place;
              this.plans = JSON.parse(plan.rvPlan);
              // 将存在预约时段的日期标注出来
              for (let i = 0; i < this.plans.length; i++) {
                let date = this.plans[i].date;
                console.log(date);
                this.selected.push({
                  date:date,
                  info:'已设置'
                });
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

.page {
  background-color: #cccccc;
  width: 100%;
  overflow-y: auto;
  overflow-x: hidden;
  height: 100%;
}

.text-wrapper {
  padding: 1.63rem 0;
  background-color: #ffffff;
}

.font {
  font-size: 1.13rem;
  font-family: SourceHanSerifCN;
  line-height: 1.04rem;
  color: #000000;
}

.text {
  line-height: 1.04rem;
}

.section {
  background-color: #e7f5ff;
  height: 1.5rem;
}

.section_2 {
  padding: 0.88rem 3.5rem 1.13rem;
  background-color: #ffffff;
}

.text_3 {
  line-height: 1.05rem;
}

.font_2 {
  font-size: 1.13rem;
  font-family: SourceHanSerifCN;
  line-height: 0.86rem;
  color: #000000;
}

.text_2 {
  margin-right: 4.75rem;
}

.text_4 {
  line-height: 1.04rem;
}

.text_5 {
  margin-right: 4.75rem;
}

.text_6 {
  margin-right: 1.75rem;
}

.section_3 {
  background-color: #e7f5ff;
  height: 4.13rem;
}

.section_4 {
  background-color: #e7f5ff;
  height: 34.5rem;
}
</style>