<template>
  <div class="calendar-wrap">
    <!-- 색상 설명 -->
    <div class="explain-wrap">
      <span class="personal">빨강: 개인</span>
      <span class="family">초록: 가족</span>
      <span class="anniversary">파랑: 기념일</span>
    </div>
    <!-- 달력 -->
    <v-calendar is-expanded :rows="2" :attributes='planList' ref="calendar" @dayclick="goDetail">
      <!-- <template #day-popover="{ day, dayTitle, planList }">
        <div class="text-xs text-gray-300 font-semibold text-center">
          {{ dayTitle }}
        </div>
        <popover-row
          v-for="plan in planList"
          :key="plan.planId"
          :attribute="plan">
          {{ plan.popover.label }}
        </popover-row>
      </template> -->
        <template v-slot:footer>
            <div class="footer-wrap">
                <Button buttonClass="small information" buttonText="오늘로" @click="moveToToday"></Button>
            </div>
        </template>
    </v-calendar>

    <!-- 일정 작성하기 -->
    <!-- Button trigger modal -->
    <button id="btn-modal" type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
    <img style="width:25px;" src="@/assets/images/plus-lg.svg" alt="plus">
    </button>

    <!-- Modal -->
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">일정 추가</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="flex">
                    <span>제목</span>
                    <input v-model="newPlan.planTitle" type="text">
                </div>
                <div class="flex">
                    <span>날짜</span>
                    <Datepicker v-model="newPlan.date" format='yyyy/MM/dd' modelType="yyyy-MM-dd HH:mm:ss" :enableTimePicker="false" range selectText="선택" cancelText="취소"></Datepicker>
                </div>
                <div class="flex">
                    <span>장소</span>
                    <input v-model="newPlan.planPlace" type="text">
                </div>
                <div>
                    <input type="radio" id="radioPersonal" name="planType" value="personal" v-model="newPlan.planType" />
                    <label for="radioPersonal">개인</label>
                    <input type="radio" id="radioFamily" name="planType" value="family" v-model="newPlan.planType"/>
                    <label for="radioFamily">가족</label>
                    <input type="radio" id="radioAnniversary" name="planType" value="anniversary" v-model="newPlan.planType"/>
                    <label for="radioAnniversary">기념일</label>
                    <button type="button" class="btn btn-secondary" data-bs-toggle="tooltip" data-bs-placement="top" title="Tooltip on top">
                      Tooltip on top
                    </button>
                    <img class="info" src="@/assets/images/circle-info-solid.svg" alt="information">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                <button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="writeCreate">일정추가</button>
            </div>
            </div>
        </div>
    </div>
    <BottomTap></BottomTap>
  </div>
</template>

<script>
import { VCalendar } from 'v-calendar'
import 'v-calendar/dist/style.css'
import Datepicker from '@vuepic/vue-datepicker'
import BottomTap from '@/components/common/BottomTap.vue'
import Button from '@/components/common/Button.vue'

import { mapActions, mapState } from 'vuex'
history.pushState(null, null, location.href)
window.onpopstate = function (event) {
  history.go(1)
}
const planStore = 'planStore'

export default {
  components: {
    VCalendar,
    Datepicker,
    BottomTap,
    Button
  },
  created () {
    // 일정 조회
    const info = {
      userId: localStorage.getItem('userId'),
      familyId: localStorage.getItem('familyId')
    }
    // 일정 전체 조회
    this.planAllList(info)
  },
  computed: {
    ...mapState(planStore, ['planList'])
  },
  watch: {
    planList () {
      // console.log('watch : ')
      // const info = {
      //   userId: localStorage.getItem('userId'),
      //   familyId: localStorage.getItem('familyId')
      // }
      // // 일정 전체 조회
      // this.planAllList(info)
    }
  },
  data () {
    return {
      newPlan: {
        userId: localStorage.getItem('userId'),
        familyId: localStorage.getItem('familyId'),
        planTitle: '',
        planPlace: '',
        planType: '',
        date: new Date(),
        planNotiYn: false
      }

      // 색상 샘플
      // {
      //   highlight: {
      //     start: {
      //       style: {
      //         backgroundColor: '#7B371C' // blue
      //       },
      //       contentStyle: {
      //         color: '#ffffff' // color of the text
      //       }
      //     },
      //     base: {
      //       style: {
      //         backgroundColor: '#A57966' // light blue
      //       }
      //     },
      //     end: {
      //       style: {
      //         backgroundColor: '#7B371C' // blue
      //       },
      //       contentStyle: {
      //         color: '#ffffff' // color of the text
      //       }
      //     }
      //   },
      //   dates: { start: new Date(year, month, 1), end: new Date(year, month, 6) }
      // }

    }
  },
  methods: {
    ...mapActions(planStore, ['planAllList', 'setPlanId', 'planCreate']),
    // 일정 만들기
    writeCreate () {
      this.planCreate(this.newPlan)
    },
    // 일정 상세보기로 이동
    goDetail (day) {
      // console.log(day.id)
      for (let i = 0; i < this.planList.length; i++) {
        // console.log(this.planList[i].dates)
        // if (this.planList[i].dates.year === day.id) {
        //   console.log(day.id)
        // }
      }
      // this.setPlanId(day)
    },
    // 오늘날짜로 이동
    moveToToday () {
      this.$refs.calendar.move(new Date())
    }
  }
}
</script>

<style scoped lang="scss">
.calendar-wrap{
    width: 100%;
}
.explain-wrap{
  position: absolute;
  top: 6%;
  right: 3%;
  span{
    margin-left: 10px;
  }
  .personal{
    color: red
  }
  .family{
    color: green
  }
  .anniversary{
    color: blue
  }
}
.vc-container{
    top: 10%;
}
.footer-wrap{
    background-color: #F0EAE3;
    text-align: center;
    padding: 10px;
}
.today{
    background-color: #A57966;
}
.btn{
    height: 40px;
    &-primary{
    --bs-btn-bg: #7b371c;
    --bs-btn-border-color: #7b371c;
    --bs-btn-hover-bg: #54210d;
    --bs-btn-hover-border-color: #54210d;
    --bs-btn-focus-shadow-rgb: none;
    }
    &#btn-modal{
        position: absolute;
        right: 10%;
        bottom: 10%;
        border-radius: 50%;
        width: 50px;
        height: 50px;
    }
}
.flex{
    display: flex;
    justify-content: space-between;
    margin-bottom: 5%;
    .dp__main{
        width: 80%;
    }
    input {
    width: 80%;
    border-radius: 5px;
    border: 1px solid #ae5f40;
    }
}
label{
  margin-right: 10px;
}
.info{
  width: 20px;
  height: 20px;
  float: right;
}
</style>
