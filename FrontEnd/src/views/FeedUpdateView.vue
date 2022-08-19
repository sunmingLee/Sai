<template>
    <div class="create-wrap">
        <HeaderTitle hasBack="true" title="게시글 수정"/>
        <div class="create-content">
          <div class="content-flex">
          <div class="content-wrap">
            <!-- 글(텍스트)-->
            <div class="textarea-wrap">
              <textarea v-model="this.boardContent" name="" id="" cols="30" rows="10" @change="updateText"></textarea>
            </div>
            <!-- 캐러셀 -->
            <!-- 사진을 새로 담으면 -->
            <div v-if="imageFlag" id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">
              <div class="carousel-inner" >
                <div class="carousel-item active">
                  <img :src="srcList[0]" id="img" class="d-block w-100">
                  <button type="button" class="btn-close" aria-label="Close" @click="removeMemo(memo.memoId)"></button>
                </div>
                <div v-for="(src, index) in srcList" :key="index">
                  <div v-if="index !== 0" class="carousel-item">
                    <img :src="src" id="img" class="d-block w-100">
                    <button type="button" class="btn-close" aria-label="Close" @click="removeMemo(memo.memoId)"></button>
                  </div>
                </div>
              </div>
              <button v-if="imageFlag" class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
                  <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                  <span class="visually-hidden">Previous</span>
              </button>
              <button v-if="imageFlag" class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
                  <span class="carousel-control-next-icon" aria-hidden="true"></span>
                  <span class="visually-hidden">Next</span>
              </button>
            </div>
            <!-- 기존 사진 -->
            <div v-else-if="feed.viewBoardMediaResponseDto.length > 0" id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">
              <div class="carousel-inner" >
                <div class="carousel-item active">
                  <!-- <img :src="srcList[0]" id="img" class="d-block w-100"> -->
                  <!-- <img src="@/assets/images/여행1.jpg" class="d-block w-100"> -->
                  <img :src="feed.viewBoardMediaResponseDto[0].boardMediaPath" id="img" class="d-block w-100">
                </div>
                <div v-for="(src, index) in feed.viewBoardMediaResponseDto" :key="index">
                  <div v-if="index !== 0" class="carousel-item">
                    <!-- <img src="@/assets/images/여행2.jpg" class="d-block w-100"> -->
                    <img :src="src.boardMediaPath" id="img" class="d-block w-100">
                  </div>
                </div>
              </div>
              <button v-if="feed.viewBoardMediaResponseDto.length > 0" class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
                  <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                  <span class="visually-hidden">Previous</span>
              </button>
              <button v-if="feed.viewBoardMediaResponseDto.length > 0" class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
                  <span class="carousel-control-next-icon" aria-hidden="true"></span>
                  <span class="visually-hidden">Next</span>
              </button>
            </div>
          <!-- 추가기록과 투표만들기 토글 -->
          <div class="toggle-flex">
            <div class="toggle-wrap">
              <button @click="record">추가 기록</button>
              <button @click="poll">투표 만들기</button>
            </div>
          </div>
          <div class="record-flex">
            <!-- 추가 기록 -->
            <div class="record-wrap" v-if="toggle">
              <p class="record-title">기록하고 싶은 시간, 장소, 사람이 있나요?</p>
              <div class="record-flex">
                <div class="record-date">
                  <div class="modal-btn-box">
                    <p>언제?</p>
                    <img :class="visibilityIcon" src="@/assets/images/calendar-check.svg" alt="calendar" style="width: 30px" @click="showDate">
                  </div>
                  <!-- 날짜 모달창 -->
                  <div class="popup-wrap hidden" id="date-popup">
                      <div class="popup">
                        <div class="popup-header">
                          <span class="header-title">원하시는 날짜를 고르세요</span>
                        </div>
                        <div class="popup-content">
                          <div class="body-content">
                            <div class="content-callsign" >
                              <div class="modal-date">
                                <Datepicker placeholder="날짜를 선택해주세요." class="datepicker" @cleared="removeDate" :enableTimePicker="false" v-model="this.boardDate" @closed="updateDate"/>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div class="popup-foot">
                          <span class="pop-btn confirm" id="confirm" @click="dateCancle">취소</span>
                          <span class="pop-btn close" id="close" @click="dateConfirm">확인</span>
                        </div>
                      </div>
                  </div>
                </div>
                <div class="record-location">
                  <p>어디서?</p>
                  <img :class="visibilityBack" src="@/assets/images/geo-alt.svg" alt="location" style="width: 30px" @click="showApi">
                </div>
                <div class="record-person">
                  <div class="modal-btn-box">
                    <p>누구랑?</p>
                    <img :class="visibilityIcon" src="@/assets/images/person-circle.svg" alt="calendar" style="width: 30px" @click="showFamily">
                  </div>
                <!-- 사람 선택 모달창 -->
                  <div class="popup-wrap hidden" id="popup">
                      <div class="popup">
                        <div class="popup-header">
                          <span class="header-title">누구랑 함께 했나요?</span>
                        </div>
                        <div class="popup-content">
                          <div class="body-content">
                            <div class="content-callsign" >
                              <div class="callsign-wrap" v-for="(callsign, index) in familyCallsignList" :key="index">
                                <label for="callsign-select">{{callsign.callsign}}</label>
                                <input type="checkbox" name="callsign" :value="callsign.toUserId" class="callsign-select" :checked="this.callsignCheck[index]">
                              </div>
                            </div>
                          </div>
                        </div>
                        <div class="popup-foot">
                          <span class="pop-btn confirm" id="confirm" @click="personCancle">취소</span>
                          <span class="pop-btn close" id="close" @click="personConfirm">확인</span>
                        </div>
                      </div>
                  </div>
                </div>
              </div>
              <div class="record-confirm">
                <div class="record-time">
                    <span v-if="this.date !== '' && this.date !== null">시간 : {{this.date}}</span>
                    <Button v-if="this.date !== '' && this.date !== null" buttonClass="small negative" buttonText="삭제" @click="deleteDate"/>
                </div>
                <span v-if="this.boardLocation !== ''">위치 : {{this.boardLocation}}</span>
                <Button v-if="this.boardLocation !== ''" buttonClass="small negative" buttonText="삭제" @click="deleteLocation"/>
              </div>
            </div>
            <!-- 투표 만들기 -->
            <div v-else class="poll-wrap">
              <!-- 투표 제목 -->
              <div calss="poll-title-wrap">
                <input v-if="this.pollFlag" type="text" v-model="this.question" placeholder="투표 제목" class="poll-title-input" disabled>
                <input v-else type="text" v-model="this.question" placeholder="투표 제목" class="poll-title-input" >
              </div>
              <!-- 투표 항목 -->
              <p class="option-title">항목 입력</p>
              <div class="poll-item-wrap">
                <div v-for="(choice, index) in this.choices" :key="index">
                  <div>
                    <input v-if="this.pollFlag" class="poll-option-input" name="option" v-model="choice.text" placeholder="항목을 입력하세요" disabled>
                    <input v-else class="poll-option-input" name="option" v-model="choice.text" placeholder="항목을 입력하세요">
                    <span v-if="choice.text === '' && this.choices.length > 2 && index > 1" @click="pollDelete(index)">X</span>
                  </div>
                </div>
              </div>
              <!-- 투표 항목 추가 버튼 -->
              <div class="poll-button">
                <Button buttonClass="small information" buttonText="항목 추가" @click="addPollItem" style="margin: 10px 5px"/>
                <Button buttonClass="big negative" buttonText="투표 삭제" @click="pollReset" style="margin: 10px 5px"/>
              </div>
              <div class="poll-time">
                <div calss="poll-time-title" style="display: flex">
                  <span style="line-height:35px">마감시간 설정</span>
                  <input v-if="this.pollFlag" style="padding-left:10px" type="checkbox" class="poll-time" @click="pollTimeCheck" disabled>
                  <input v-else style="padding-left:10px" type="checkbox" class="poll-time" @click="pollTimeCheck">
                </div>
                <div class="poll-time-date">
                  <Datepicker style="max-width:200px" placeholder="날짜를 선택해주세요." class="datepicker" :minDate="new Date()" v-model="pollDatePicker" :disabled="pollDateDisabled"/>
                </div>
              </div>
            </div>
          </div>
          <div class="button-wrap">
            <Button buttonClass="small positive" buttonText="수정" @click="feedUpdate"/>
          </div>
        </div>
      </div>
      </div>
    </div>
</template>
<script>
import HeaderTitle from '@/components/common/HeaderTitle.vue'
import InputBox from '@/components/common/InputBox.vue'
import Button from '@/components/common/Button.vue'
import heic2any from 'heic2any'

import Datepicker from '@vuepic/vue-datepicker'
import '@vuepic/vue-datepicker/dist/main.css'
import { mapState, mapActions } from 'vuex'

const boardStore = 'boardStore'
const userStore = 'userStore'
const familyStore = 'familyStore'

// 파일 리스트
const fileList = []
export default {
  name: 'FeedCreateView',
  components: {
    HeaderTitle,
    InputBox,
    Datepicker,
    Button
  },
  data () {
    return {
      boardDate: '', // 언제?(날짜)
      boardContent: '', // 글
      boardLocation: '', // 어디서?(위치)
      boardTaggedYn: false, // 태그 여부
      boardMediaYn: false, // 미디어 여부
      pollYn: false, // 투표 여부
      question: '', // 투표 제목
      expirationDateTime: '', // 투표 마감 날짜
      choices: [
        {text: ''},
        {text: ''}
      ], // 투표 항목
      originMediaList: [], // 기존 미디어 목록
      peopleList: [], // 태그 목록
      boardModified: false, // 게시글 수정 여부
      boardTaggedModified: false, // 태그 수정 여부
      deleteBoardMediaIds: [], // 미디어에서 수정되어 사라지는 파일들
      pollModified: false, // 투표 수정 여부
      originBoardDate: '',
      dateValid: false,
      date: '',
      changeBoardDate: '', // 추가 기록(날짜) 선택했을 때 화면에 보여질 날짜
      toggle: true, // true: 추가기록 , false : 투표
      // 투표 항목들
      pollDatePicker: '', // 투표 마감 날짜로 선택한 날(보내는 데이터 값은 아님)
      pollDateDisabled: true, // 투표 마감 날짜 지정 여부..
      // 태그 리스트
      peopleNameList: [],
      srcList: [],
      fileList: [],
      imageFlag: false,
      pollCheck: false,
      pollFlag: false,
      pollRequest: {},
      callsignCheck: [],
      callsign : []
    }
  },
  created () {
    const boardId = localStorage.getItem('boardId')
    const userId = localStorage.getItem('userId')
    // 게시글 상세보기
    const info = {
      boardId: boardId,
      userId: userId
    }
    this.callsignList(userId)
    this.getOneFeed(info)
  },
  mounted () {
    this.check()
  },
  computed: {
    // ...mapState(userStore, ["userId", "userName"]),
    ...mapState(familyStore, ['familyCallsignList']),
    ...mapState(boardStore, ['feed'])
  },
  methods: {
    ...mapActions(boardStore, ['boardCreate', 'getOneFeed', 'boardUpdate']),
    ...mapActions(familyStore, ['callsignList']),
    check () {
      this.callsign = this.familyCallsignList
      this.boardDate = this.feed.viewBoardResponseDto.boardDate
      this.dateChange()
      this.originBoardDate = this.feed.viewBoardResponseDto.boardDate
      this.boardContent = this.feed.viewBoardResponseDto.boardContent
      this.boardLocation = this.feed.viewBoardResponseDto.boardLocation
      this.boardTaggedYn = this.feed.viewBoardResponseDto.boardTaggedYn
      this.boardMediaYn = this.feed.viewBoardResponseDto.boardMediaYn
      this.pollYn = this.feed.viewBoardResponseDto.pollYn
      this.date = this.feed.viewBoardResponseDto.boardDate
      if (this.pollYn) {
        this.pollFlag = true
        this.question = this.feed.pollResponse.question
        this.choices = this.feed.pollResponse.choices
      }
      this.peopleList = this.feed.viewBoardTaggedResponseDto
      console.log(this.callsign.length)
      if(this.callsign.length > 0) {
        for(let i = 0; i < this.callsign.length; i++) {
          if(this.peopleList.some(v => v.userId === this.callsign[i].toUserId)) {
            this.callsignCheck.push(true)
          } else {
            this.callsignCheck.push(false)
          }
        }
      }
    },
    // 글 수정
    updateText () {
      this.boardModified = true
    },
    // 추가기록과 투표만들기 토글
    record () {
      if (!this.toggle) {
        this.toggle = true
      }
    },
    poll () {
      if (this.toggle) {
        this.toggle = false
      }
    },
    // 투표 초기화
    pollReset () {
      this.boardModified = true
      // 제목과 항목 초기화
      this.question = ''
      this.choices = [
        { text: '' },
        { text: '' }
      ]
      this.pollFlag = false
      this.pollCheck = true
      this.pollYn = 0
      this.pollModified = true
    },
    // 투표 항목 추가하기
    addPollItem () {
      if (!this.pollCheck && this.pollYn) {
        alert('원래 있던 투표는 수정하실 수 없습니다. 투표를 삭제하고 새로 만들어주세요!')
      } else {
        if (this.choices.length < 5) {
          this.choices.push({ text: '' })
        } else {
          alert('투표 항목은 5개까지 가능합니다.')
        }
      }
    },
    // 투표 항목 삭제
    pollDelete (index) {
      this.choices.splice(index, 1)
    },
    // 투표 마감 시간 설정
    pollTimeCheck () {
      // 투표 마감 시간을 설정한다고 체크
      if (!this.pollDateDisabled) {
        this.pollDateDisabled = true
      } else {
        this.pollDateDisabled = false
      }
    },
    // 날짜 선택 모달창 출력
    showDate () {
      const modal = document.getElementById('date-popup')
      modal.classList.remove('hidden')
    },
    // 화면에 보이는 날짜
    dateChange () {
      const date = new Date(this.boardDate)
      const year = date.getFullYear()
      let month = (1 + date.getMonth())
      let day = date.getDate()
      month = month >= 10 ? month : `0${month}`
      day = day >= 10 ? day : `0${day}`
      this.date = `${year}-${month}-${day}`
    },
    // 날짜 수정
    updateDate () {
      this.boardModified = true
      const year = this.boardDate.getFullYear()
      let month = (1 + this.boardDate.getMonth())
      let day = this.boardDate.getDate()
      // MM-DD의 형태로 만들기 위해 10보다 적은 경우 0을 붙이게 함 (예 : 1일이면 01)
      month = month >= 10 ? month : `0${month}`
      day = day >= 10 ? day : `0${day}`
      this.date = `${year}-${month}-${day}`
    },
    // 날짜 제거 했을때
    removeDate () {
      console.log('안녕' + this.boardModified)
      this.boardModified = true
      console.log(this.boardDate)
      this.date = ''
    },
    // 날짜 선택 모달창에서 확인 버튼 클릭
    dateConfirm () {
      if (this.boardDate === '') {
        alert('날짜를 선택해주세요')
      }
      const modal = document.getElementById('date-popup')
      // 모달창 숨기기
      modal.classList.add('hidden')
      console.log(this.boardDate)
    },
    // 날짜 선택 모달창에서 취소 버튼 클릭
    dateCancle () {
      console.log(this.originBoardDate)
      this.boardDate = this.originBoardDate
      const modal = document.getElementById('date-popup')
      this.date = this.boardDate
      modal.classList.add('hidden')
    },
    // 날짜 삭제
    deleteDate () {
      console.log('안녕' + this.boardModified)
      this.boardModified = true
      this.date = ''
      this.originBoardDate = ''
      this.boardDate = ''
    },
    // 누구랑?
    showFamily () {
      const modal = document.getElementById('popup')
      modal.classList.remove('hidden')
    },
    // 사람 태그 확인
    personConfirm () {
      const test = document.getElementsByName('callsign')
      console.log(test)
      for (let i = 0; i < test.length; i++) {
        const user = test[i].value
        const person = {
          userId: user
        }

        if (test[i].checked) {
          const find = this.peopleList.find(v => v.userId === test[i].value)
          if (!find) {
            this.peopleList.push(person)
          }
        } else {
          // 배열에서 중복되는 값을 빼자
          const find = this.peopleList.filter(v => v.userId !== test[i].value)
          this.peopleList = find
        }
      }
      console.log(this.peopleList)
      // 확인 버튼을 클릭했을 경우 모달창을 끈다
      const modal = document.getElementById('popup')
      modal.classList.add('hidden')
    },
    // 사람 취소
    personCancle () {
      const modal = document.getElementById('popup')
      modal.classList.add('hidden')
    },
    // 위치 선택
    showApi () {
      new window.daum.Postcode({
        oncomplete: (data) => {
          let fullRoadAddr = data.roadAddress // 도로명 주소 변수
          let extraRoadAddr = '' // 도로명 조합형 주소 변수

          if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
            extraRoadAddr += data.bname
          }

          if (data.buildingName !== '' && data.apartment === 'Y') {
            extraRoadAddr +=
              extraRoadAddr !== ''
                ? ', ' + data.buildingName
                : data.buildingName
          }
          if (extraRoadAddr !== '') {
            extraRoadAddr = ' (' + extraRoadAddr + ')'
          }
          if (fullRoadAddr !== '') {
            fullRoadAddr += extraRoadAddr
          }
          console.log('안녕' + this.boardModified)
          this.boardModified = true
          this.boardLocation = fullRoadAddr
        }
      }).open()
    },
    // 위치 삭제
    deleteLocation () {
      if (this.boardLocation !== '') {
        console.log('안녕' + this.boardModified)
        this.boardModified = true
      }
      this.boardLocation = ''
    },
    // 게시글 작성
    feedUpdate () {
      // 투표는 최소한 두 항목이 적혀 있어야 투표가 있다고 할 수 있다
        let pollOptionCnt = 0
        for (let i = 0; i < this.choices.length; i++) {
          if (this.choices[i].text !== '') {
            pollOptionCnt = pollOptionCnt + 1
          }
        }
        // 작성한 항목이 있는데
        let pollValid = true
        if (pollOptionCnt !== 0) {
            // 제목이 없으면
          if (this.question === '') {
            alert('투표 제목을 입력해주세요')
          } else if (pollOptionCnt < 2) {
            alert('투표 항목은 최소한 두 개 이상이어야 합니다')
            this.pollYn = false
          } else {
            for (let i = 0; i < this.choices.length; i++) {
              console.log(this.choices[i].text === '')
              if (this.choices[i].text === '') {
                pollValid = false
              }
            }
            if(pollValid) {
              this.boardModified = true
              this.pollModified = true
              this.pollYn = 1
            } else {
              alert('아무 것도 입력하지 않은 투표 항목이 있습니다')
            }
          }
        }
      // 미디어 or 글 or 투표 중 하나라도 있어야 게시글 작성이 가능하다
      if (fileList.length === 0 && this.boardContent === '' && !this.pollYn) {
        alert('글이나 사진이나 투표를 등록해야 작성이 가능합니다.')
        // this.files = test
      } else {
        // 투표가 있고 그게 지금 수정한 경우
        if (this.pollYn && this.boardModified) {
          console.log('안녕' + this.boardModified)
          if (this.pollDateDisabled) {
            const timeOff = new Date().getTimezoneOffset() * 60000
            const timeZone = new Date(Date.now() - timeOff) // 현재 시간
            this.expirationDateTime = new Date(timeZone.setDate(timeZone.getDate() + 3)).toISOString()
          } else {
            const timeOff = new Date(this.pollDatePicker).getTimezoneOffset() * 60000
            const timeZone = new Date(this.pollDatePicker - timeOff)
            this.expirationDateTime = timeZone.toISOString()
          }
        }
        // 추가 정보에서 사람 태그 여부
        const taggedResult = []
        // 태그한 사람이 있을 경우
        if (this.peopleList.length !== 0) {
          this.boardTaggedYn = 1
        }
        // 투표가 있을 경우
        if (this.pollModified) {
          this.pollRequest = {
            question: this.question,
            expirationDateTime: this.expirationDateTime,
            choices: this.choices,
            boardId: localStorage.getItem('boardId')
          }
        }
        console.log(this.boardModified)
        if (this.boardModified) {
          const updateBoardRequestDto = {
            boardModified: this.boardModified,
            boardTaggedModified: this.boardTaggedModified,
            modifyBoardRequestDto: {
              boardContent: this.boardContent,
              boardDate: this.boardDate,
              boardId: localStorage.getItem('boardId'),
              boardLocation: this.boardLocation,
              boardMediaYn: this.boardMediaYn,
              boardTaggedYn: this.boardTaggedYn,
              pollYn: this.pollYn
            },
            pollModified: this.pollModified,
            pollRequest: this.pollRequest,
            inputBoardTaggedRequestDtos: this.peopleList
          }
          console.log(updateBoardRequestDto)
          this.boardUpdate(updateBoardRequestDto)
        } else {
          alert('수정한 것이 없거나 제대로 작성되지 않은 부분이 있습니다')
        }
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.record-time {
    &.hidden {
        display: none;
    }
}
//반응형을 위해 전체 공간
.create-content {
  margin-top: 20px;
}
.content-flex {
  max-width: 900px;
  margin: 0 auto;
  .content-wrap {
    display: flex;
    flex-wrap: wrap;
    gap: 1em;
    padding: 15px;
    //글 쓰기
    .textarea-wrap {
      min-width: 300px;
      flex-basis: 600px;
      margin: 0 auto;
      textarea {
        width: 100%;
      }
    }
    //사진 추가
    .file-wrap {
      min-width: 300px;
      flex-basis: 600px;
      margin: 0 auto;
      display: flex;
      justify-content: flex-end;
      //사진 추가(문구)
      span {
        line-height: 30px;
        padding-right: 10px;
      }
      // +버튼
      .file-button {
        label {
          border: 1px solid #A57966;
          width: 40px;
          height: 35px;
          text-align: center;
          line-height: 30px;
          font-size: 30px;
          color: white;
          background-color: #A57966;
          border-radius: 10px;
        }
        //원래 보이던 input file의 css 숨기기
        .form-control {
          display: none;
        }
      }
    }
    //토글
    .toggle-flex {
      min-width: 300px;
      flex-basis: 600px;
      margin: 0 auto;
      display: flex;
      justify-content: center;
      margin-top: 10px;
      .toggle-wrap {
        button {
          background-color: #fafafa;
          color: black;
          padding: 0 10px;
          text-decoration: underline;
        }
      }
    }
    //추가 기록
    .record-flex, .button-wrap {
      min-width: 300px;
      flex-basis: 600px;
      margin: 0 auto;

      .record-wrap {
        .record-title {
          text-align: center;
        }
        .record-flex {
          margin: 20px 0;
          display: flex;
          //사람 모달창
          .record-person, .record-date {
            width: 100%;
            flex-basis: 590px;
            text-align: center;
            .modal-btn-box {
              width: 100%;
              text-align: center;
            }
            .popup-wrap {
              background-color:rgba(0,0,0,.3);
              justify-content:center;
              align-items:center;
              position:fixed;
              top:0;
              left:0;
              right:0;
              bottom:0;
              padding:15px;
              display: flex;
              &.hidden {
                display: none;
              }
              .popup {
                width:100%;
                max-width:400px;
                background-color:#ffffff;
                border-radius:10px;
                overflow:hidden;
                box-shadow: 5px 10px 10px 1px rgba(0,0,0,.3);
                .popup-header {
                  width:100%;
                  height:50px;
                  align-items:center;
                  justify-content:center;
                  padding: 10px 0;
                  .header-title {
                    font-size: 25px;
                    font-weight: 700;
                    letter-spacing: -3px;
                    text-align: center;
                  }
                }
                .popup-content {
                  width: 100%;
                  background-color: #ffffff;
                  .body-content {
                    width: 100%;
                    padding: 30px;
                    .content-title {
                      text-align:center;
                      width:100%;
                      height:40px;
                      margin-bottom:10px;
                    }
                    .content-callsign {
                      word-break:break-word;
                      overflow-y:auto;
                      min-height:100px;
                      display: flex;
                      flex-wrap: wrap;
                      justify-content: center;
                      align-content: center;
                      justify-content: space-around;
                      .callsign-wrap {
                        border: 1px solid #A57966;
                        margin: 10px 0;
                        border-radius: 3px;
                        display: flex;
                        justify-content: space-between;
                        padding: 10px;
                        // min-width: 340px;
                        width: 340px;
                        label {
                          font-size: 17px;
                          font-weight: bold;
                          padding-left: 10px;
                        }
                        input {
                          zoom: 1.4;
                        }

                      }
                    }
                  }
                }
                .popup-foot {
                  width: 100%;
                  height: 50px;
                  .pop-btn {
                    display:inline-flex;
                    width:50%;
                    height:100%;
                    float:left;
                    justify-content:center;
                    align-items:center;
                    color:#ffffff;
                    &.confirm {
                      background-color: #C6BFBF;
                      color: black;
                    }
                    &.close {
                      background-color: #7b371c;
                    }
                  }
                }
              }
            }
          }
          .record-location {
            flex-basis: 600px;
            text-align: center;
          }
        }
        .record-confirm {
          text-align: center;
          // display: flex;
          // justify-content: center;
          span {
            margin-right: 10px;
          }
        }
      }
    }
    .button-wrap {
      text-align: center;
      max-width: 50px;
      margin-top: 20px;
      button {
        width: 30%;
        background-color: #7b371c;
        color: white;
      }
    }
  }
}
//사진 공간
.carousel {
  &.slide {
    flex-basis: 600px;
    margin: 0 auto;
    text-align: center;
  }
}
.carousel-inner{
  min-width: 300px;
  height: 90%;
  width: 40%!important;
  margin: 0 auto;
}
.carousel-control-prev {
  position: unset;
  background-color: #A57966;
  width: 30px;
  display: inline-block;
}
.carousel-control-next {
  position: unset;
  background-color: #A57966;
  width: 30px;
  display: inline-block;
}

//투표

.poll-wrap {
  text-align: center;
}
.poll-title-input, .poll-option-input {
  min-width: 300px;
  min-height: 40px;
  border-radius: 3px;
}
.poll-option-input {
  margin: 5px 0;
}
.option-title {
  font-weight: bold;
  margin: 10px 0;
}
.dp__input_wrap {
  max-width: 220px;
}
.poll-time {
  display: flex;
  justify-content: space-evenly;
  .poll-time-title {
    display: flex;
  }
}
.poll-time-title {
  display: flex;

}
.poll-button {
    display: flex;
    justify-content: center;
}
.poll-item-wrap {
  div {
    position: relative;
    span {
      position: absolute;
      right: 160px;
      top: 13px;
    }
  }
}
.carousel-inner {
  width: 50%;
}

</style>
