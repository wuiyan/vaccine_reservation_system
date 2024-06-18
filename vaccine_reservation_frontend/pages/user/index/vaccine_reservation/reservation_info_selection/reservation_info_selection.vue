<template>
<view class="flex-col page">
  <view class="flex-col group">
    <view class="flex-col section">
      <view class="flex-row">
        <text class="font text">预约项目:</text>
        <text class="font_2 text_2 ml-15">{{vaccine.vcName}}</text>
      </view>
      <view class="mt-16 flex-row items-baseline">
        <text class="font">接种地点:</text>
        <text class="ml-14 text_3">{{vaccine.place}}</text>
      </view>
      <view class="mt-16 flex-row items-center">
        <text class="font">参考价格:</text>
        <text class="text_4 ml-17">￥{{vaccine.price}}</text>
      </view>
    </view>
    <view class="flex-row justify-center items-center relative section_2 mt-29">
      <text class="font_2 text_5 pos">预约剂次：</text>
      <picker mode="selector" :range="doseOptions" :value="selectedDose" @change="onDoseChange">
        <view class="picker">
          {{doseOptions[selectedDose]}}
        </view>
      </picker>
    </view>

  </view>
	  <view class="calendar">
      <uni-calendar
          :insert="true"
          :start-date="nowDate"
          :showMonth="false"
          :selected="selected"
          @change="change"
      />
	  </view>

  <view class="flex-col group_2">
    <view class="flex-col justify-start items-start self-stretch text-wrapper">
      <view class="ml-18 flex-1 section_3" style="display: flex;align-items: center;">
        <text class="font_2 text_6">预约时间：</text>
        <view style="position: relative;">
          <input v-model="showForm.rvDateTime" type="text" @tap="getRvDateTime()" placeholder="请选择预约时间" disabled/>

          <view v-if="tempRvTime.length > 0" class="prompt-box"  style="overflow-y: auto; max-height: 10em;">
            <ul>
              <li v-for="(item, index) in tempRvTime" :key="index" @click="selectRvDateTimeInfo(item)" >
                {{ item }}
              </li>
            </ul>
          </view>
        </view>
      </view>
    </view>
    <view class="mt-58 flex-col justify-start items-center self-center text-wrapper_2" @tap="submitPlanForm">
      <text class="text_7">下一步</text>
    </view>
  </view>
</view>
</template>

<script>
  import request from "@/api/api";
	import uniCalendar from '@/uni_modules/uni-calendar/components/uni-calendar/uni-calendar.vue';
export default {
  components: {
  			uniCalendar
  		},
  props: {},
  data() {
    return {
      // 字符串格式的当前时间，格式为YYYY-MM-DD
      nowDate:new Date().toISOString().split('T')[0],
      // 已选择日期
      selected:[],

      vaccine:{},
      planInfo:{},
      doseOptions: [],
      selectedDose: 0, // 默认选择第一项
      tempRvTime:[],


      showForm:{
        rvDateTime:"",
        selectDate:new Date().toISOString().split('T')[0],
      },
      submitForm:{

      }
	};
  },
	onLoad(options) {
	    // 通过options对象接收传递的参数
	    let id = options.id; // 如果你之前使用了encodeURIComponent编码参数
      if(id){
        this.getVaccineInfoById(id);
      }
		
	  },
  methods: {
    submitPlanForm(){
      let data = {};
      data.planId = this.planInfo.id;
      data.rvDateTime = this.showForm.selectDate + " " + this.showForm.rvDateTime;
      data.vaccineId = this.vaccine.id;
      data.vcNumber = this.vaccine.vcNumber;
      data.vcName =this.vaccine.vcName;
      data.price =this.vaccine.price;
      data.place =this.vaccine.place;
      data.dose = parseInt(this.selectedDose) + 1;
      console.log(data.dose);
      console.log(typeof(data.dose));

      uni.setStorageSync('reservationInfo',data);

      let pathUrl = "/pages/user/index/vaccine_reservation/reservation_order_detail/reservation_order_detail";
      setTimeout(()=>{
        uni.navigateTo({url: pathUrl});
      },500);
    },
	  getVaccineInfoById(vaccineId){
		  const url = `plan/vaccineId?vaccineId=${vaccineId}`;
		  request(url, "GET")
		    .then(responseData => {
		  	  if(responseData.code === 0){
            this.planInfo = responseData.data;
		  			this.vaccine = responseData.data.vaccineInfo;
            for (let i = 0; i <this.vaccine.dose; i++) {
              this.doseOptions.push(i+1);
            }

            this.matchDate();
		  	  }else{
		  		  console.error("Request failed:", responseData.message);
		  	  
		  	  }
		    })
		    .catch(error => {
		  	console.error("Request failed:", error);
		  	  
		    });
	  },
    // 匹配计划中的预约日期
    matchDate(){
      let plans = JSON.parse(this.planInfo.rvPlan);

      // 将其与已选择日期匹配
      for (let i = 0; i < plans.length; i++) {
        let date = plans[i].date;
        console.log(date);
        this.selected.push({
          date:date,
          info:'可预约'
        });
      }
    },
    selectRvDateTimeInfo(item){
      this.showForm.rvDateTime = item.split(" ")[0];
      this.tempRvTime = [];
    },
    getRvDateTime(){
      this.tempRvTime = [];
      let plans = JSON.parse(this.planInfo.rvPlan);
      for (let i = 0; i < plans.length; i++) {
        let date = plans[i].date;
        if (date === this.showForm.selectDate){
          let plan =plans[i].plan;
          for (let j = 0; j < plan.length; j++) {
            let reservationNum = plan[j].planNum - plan[j].bookedNum;
            this.tempRvTime.push(plan[j].startTime + "~" + plan[j].endTime + " 可预约：" + reservationNum)
          }
        }
      }
      // 若无预约时段
      if (this.tempRvTime.length===0){
        this.showToast("当前日期无预约计划");
      }
    },
	  change(e) {
			console.log('change 返回:', e.fulldate)
      this.showForm.selectDate = e.fulldate;
		},
	  onDoseChange(event) {
		    console.log(event.target.value);
	      this.selectedDose = event.target.value; // 更新选择的剂次
	    },
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



	.calendar{
		margin-left: 5px;
		margin-right: 5px;
	}
	
.ml-15 {
  margin-left: 0.94rem;
}
.ml-17 {
  margin-left: 1.06rem;
}
.mt-29 {
  margin-top: 1.81rem;
}
.page {
  padding-bottom: 7.31rem;
  background-color: #f2f2f2;
  width: 100%;
  overflow-y: auto;
  overflow-x: hidden;
  height: 100%;
}
.group {
  padding: 0.88rem 0.75rem;
}
.section {
  padding: 2.25rem 1.5rem 1.63rem;
  /*filter: drop-shadow(0rem 0.13rem 0.13rem #00000040, 0rem 0.13rem 0.16rem #00000040);*/
  background-color: #ffffff;
  border-radius: 0.63rem;
}
.font {
  font-size: 1.13rem;
  font-family: SourceHanSerifCN;
  line-height: 1.04rem;
  color: #808080;
}
.text {
  line-height: 1.04rem;
}
.font_2 {
  font-size: 1.13rem;
  font-family: SourceHanSerifCN;
  line-height: 1.04rem;
  color: #000000;
}
.text_2 {
  line-height: 1.06rem;
}
.text_3 {
  color: #808080;
  font-size: 1rem;
  font-family: SourceHanSerifCN;
  line-height: 0.92rem;
}
.text_4 {
  color: #ff8d1a;
  font-size: 1.25rem;
  font-family: SourceHanSerifCN;
  line-height: 0.96rem;
}
.section_2 {
  padding: 0.63rem 1.88rem;
  background-color: #ffffff;
  border-radius: 0.31rem;
}
.text_5 {
  line-height: 1.05rem;
}
.pos {
  position: absolute;
  left: 1.92rem;
  top: 50%;
  transform: translateY(-50%);
}
.image {
  width: 7.5rem;
  height: 2rem;
}
.section_3 {
  background-color: #ffffff;
  height: 20.19rem;
}
.group_2 {
  margin-top: 2.19rem;
  padding: 0 0.88rem;
}
.text-wrapper {
  padding: 1.5rem 0 1.25rem;
  background-color: #ffffff;
}
.text_6 {
  margin-left: 1.75rem;
  line-height: 1.04rem;
}
.text-wrapper_2 {
  padding: 1.38rem 0 1.25rem;
  background-color: #0078f5;
  border-radius: 0.63rem;
  width: 11.88rem;
}
.text_7 {
  color: #ffffff;
  font-size: 1.25rem;
  font-family: SourceHanSansCN;
  line-height: 1.15rem;
}
</style>