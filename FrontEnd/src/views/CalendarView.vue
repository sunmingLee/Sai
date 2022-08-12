<template>
    <div class="calendar-wrap">
        <v-calendar is-expanded :rows="2" :attributes='attrs' ref="calendar">
             <template v-slot:footer>
                <div class="footer-wrap">
                    <Button buttonClass="small information" buttonText="오늘로" @click="moveToToday"></Button>
                </div>
            </template>
        </v-calendar>
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
                        <input v-model="planName" type="text">
                    </div>
                    <div class="flex">
                        <span>날짜</span>
                        <Datepicker v-model="date" format='yyyy/MM/dd' modelType="yyyy-MM-dd" range selectText="선택" cancelText="취소"></Datepicker>
                    </div>
                    <!-- Default switch -->
                    <div class="form-check form-switch">
                        <input class="form-check-input" type="checkbox" id="flexSwitchCheckDefault">
                        <label class="form-check-label" for="flexSwitchCheckDefault">하루종일</label>
                    </div>
                    <div class="flex">
                        <span>장소</span>
                        <input v-model="planPlace" type="text">
                    </div>
                        <input type="radio" id="radioPersonal" name="planType" value="personal" />
                        <label for="radioPersonal">개인</label>
                        <input type="radio" id="radioFamily" name="planType" value="family" />
                        <label for="radioFamily">가족</label>
                        <input type="radio" id="radioAnniversary" name="planType" value="anniversary" />
                        <label for="radioAnniversary">기념일</label>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                    <button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="createAlbum">일정추가</button>
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
export default {
  components: {
    VCalendar,
    Datepicker,
    BottomTap,
    Button
  },
  data () {
    const date = new Date()
    const year = date.getFullYear()
    const month = date.getMonth()
    return {
      planName: '',
      planPlace: '',
      planType: '',
      date: new Date(),
      attrs: [
        {
          key: 'today',
          // highlight: true,
          dot: {
            style: {
              // colors available: (gray, red, orange, yellow, green, teal, blue, indigo, purple, pink).
              backgroundColor: 'indigo'
            }
          },
          dates: [
            new Date(),
            new Date(year, month, 20)
          ]
        },
        {
          highlight: true,
          dates: [
            new Date(year, month, 20)
          ]
        },
        {
          highlight: {
            start: {
              style: {
                backgroundColor: '#7B371C' // blue
              },
              contentStyle: {
                color: '#ffffff' // color of the text
              }
            },
            base: {
              style: {
                backgroundColor: '#A57966' // light blue
              }
            },
            end: {
              style: {
                backgroundColor: '#7B371C' // blue
              },
              contentStyle: {
                color: '#ffffff' // color of the text
              }
            }
          },
          dates: { start: new Date(year, month, 1), end: new Date(year, month, 6) }
        }
      ]
    }
  },
  methods: {
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
.vc-container{
    top: 5%;
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
</style>
