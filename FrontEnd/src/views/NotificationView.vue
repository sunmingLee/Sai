<template>
  <div class="notification-wrap">
      <HeaderTitle hasBack="true" title="알림함"></HeaderTitle>
    <div class="bottom-border"></div>
    <div class="button-wrap">
        <Button v-if="notificationList.length" buttonClass="big positive" buttonText="알림함 비우기" @click="deleteAllNoti"></Button>
        <Button v-else buttonClass="big disable" buttonText="알림함 비우기"></Button>
    </div>
    <!-- 알림 목록 -->
    <ul class="list-group list-group-flush" v-if="notificationList.length">
        <li class="list-group-item" v-for="(noti, index) in notificationList" :key="index" @change="getNotiList">
          <button type="button" class="btn-close" aria-label="Close" @click="deleteNoti(noti.notiId)"></button>
          <span v-for="(callsign, index) in familyCallsignList" :key="index">
            <!-- 댓글 -->
            <span v-if="noti.notiType === 'COMMENT'">
              <span style="font-weight: bold;"> 댓글 </span>
              <span v-if="noti.fromUser.userId === callsign.toUserId"> {{callsign.callsign}}님이 새로운 댓글을 달았습니다.</span>
              <span style="float: right;">{{noti.notiDateTime}}</span>
            </span>
            <!-- 가족신청 -->
            <span v-else-if="noti.notiType === 'FAMILYREGISTER'">
              <span style="font-weight: bold;"> 가족신청 </span>
              <span v-if="noti.fromUser.userId === callsign.toUserId"> {{callsign.callsign}}님이 가족 신청을 요청했습니다.</span>
              <span style="float: right;">{{noti.notiDateTime}}</span>
            </span>
          </span>
        </li>
    </ul>
    <ul class="list-group list-group-flush" v-else>
        <li class="list-group-item">알림이 존재하지 않습니다.</li>
    </ul>
    <!-- 페이지네이션 -->
    <nav aria-label="...">
        <ul class="pagination">
            <li class="page-item" @click="previousList">
                <a class="page-link" href="#">이전</a>
            </li>
            <li class="page-item">
                <a class="page-link" href="#" @click="focus1">1</a>
            </li>
            <li class="page-item">
                <a class="page-link" href="#" @click="focus2">2</a>
            </li>
            <li class="page-item"><a class="page-link" href="#" @click="focus3">3</a></li>
            <li class="page-item" @click="nextList">
                <a class="page-link" href="#">다음</a>
            </li>
        </ul>
    </nav>
  </div>
</template>

<script>
import HeaderTitle from '@/components/common/HeaderTitle.vue'
import Button from '@/components/common/Button.vue'

import { mapState, mapActions } from 'vuex'
const notificationStore = 'notificationStore'
const familyStore = 'familyStore'
export default {
  components: { HeaderTitle, Button },
  data () {
    return {
      pageInfo: {
        currentPage: 0,
        perPage: 7
      }
    }
  },
  created () {
    // this.$store.dispatch('notificationStore/readNotification', localStorage.getItem('userId'))
    this.readNotification(localStorage.getItem('userId'))
    this.listNotification(this.pageInfo)
  },
  computed: {
    ...mapState(notificationStore, ['notificationList']),
    ...mapState(familyStore, ['familyCallsignList'])
  },
  methods: {
    ...mapActions(notificationStore, ['deleteNotification', 'deleteAllNotification', 'listNotification', 'readNotification', 'listNotification']),
    // 알림 하나 삭제
    deleteNoti (notiId) {
      const info = {
        pageInfo: this.pageInfo,
        notiId
      }
      this.deleteNotification(info)
    },
    // 알림 전체 삭제
    deleteAllNoti () {
      this.deleteAllNotification(this.pageInfo)
    },
    // 이전 알림 목록 받아오기
    previousList () {
      this.pageInfo.currentPage -= 1
      this.listNotification(this.pageInfo)
    },
    // 이후 목록 받아오기
    nextList () {
      this.pageInfo.currentPage += 1
      this.listNotification(this.pageInfo)
    },
    focus1 () {
      //   console.log(this.$refs.pageNum.innerHTML)
      this.pageInfo.currentPage = 0
      this.listNotification(this.pageInfo)
    },
    focus2 () {
      //   console.log(this.$refs.pageNum.innerHTML)
      this.pageInfo.currentPage = 1
      this.listNotification(this.pageInfo)
    },
    focus3 () {
      //   console.log(this.$refs.pageNum.innerHTML)
      this.pageInfo.currentPage = 2
      this.listNotification(this.pageInfo)
    }
  }
}
</script>

<style scoped lang="scss">
.notification-wrap{
    width: 900px
}
.bottom-border{
    margin-top: 2%;
    margin-bottom: 2%;
    border-bottom: 2px solid gray;
}
.pagination{
    justify-content: center;
    --bs-pagination-color: #7b371c;
    --bs-pagination-hover-color : #7b371c;
    --bs-pagination-focus-bg : #7b371c;
    --bs-pagination-focus-color : white;
    --bs-pagination-active-bg : #7b371c;
    --bs-pagination-active-border-color : #7b371c;
}
.list-group{
    margin-bottom: 5%;
}
.button-wrap{
    text-align: right;
    margin-bottom: 3%;
}
</style>
