<template>
  <div class="notification-wrap">
      <HeaderTitle hasBack="true" title="알림함"></HeaderTitle>
    <div class="bottom-border"></div>
    <div class="button-wrap">
        <Button v-if="notificationList.length" buttonClass="big positive" buttonText="알림함 비우기" @click="deleteAllNotifacation"></Button>
        <Button v-else buttonClass="big disable" buttonText="알림함 비우기"></Button>
    </div>
    <!-- 알림 목록 -->
    <ul class="list-group list-group-flush" v-if="notificationList.length">
        <li class="list-group-item" v-for="(noti, index) in notificationList" :key="index" @change="getNotiList"><button type="button" class="btn-close" aria-label="Close" @click="deleteNoti(noti.notiId)"></button> <span style="font-weight: bold;">{{noti.notiId}} {{ noti.notiType }}</span> {{noti.fromUser.userId}}님이 {{ noti.notiContent}} {{noti.notiDateTime}}</li>
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
            <li :class="page-item">
                <a class="page-link" href="#" @click="focus1">1</a>
            </li>
            <li :class="page-item">
                <a class="page-link" href="#" @click="focus2">2</a>
            </li>
            <li :class="page-item"><a class="page-link" href="#" @click="focus3">3</a></li>
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

import { mapState } from 'vuex'
export default {
  components: { HeaderTitle, Button },
  data () {
    return {
      pageInfo: {
        currentPage: 0,
        perPage: 5
      },
      clickedButton: ''
    }
  },
  created () {
    this.$store.dispatch('readNotification', localStorage.getItem('userId'))
    this.$store.dispatch('listNotification', this.pageInfo)
  },
  computed: {
    ...mapState(['notificationList'])
  },
  methods: {
    // 알림 하나 삭제
    deleteNoti (notiId) {
      this.$store.dispatch('deleteNotification', notiId)
    },
    // 알림 전체 삭제
    deleteAllNotifacation () {
      this.$store.dispatch('deleteAllNotification')
    },
    // 이전 알림 목록 받아오기
    previousList () {
      this.pageInfo.currentPage -= 1
      this.$store.dispatch('listNotification', this.pageInfo)
    },
    // 이후 목록 받아오기
    nextList () {
      this.pageInfo.currentPage += 1
      this.$store.dispatch('listNotification', this.pageInfo)
    },
    focus1 () {
      //   console.log(this.$refs.pageNum.innerHTML)
      this.pageInfo.currentPage = 0
      this.$store.dispatch('listNotification', this.pageInfo)
    },
    focus2 () {
      //   console.log(this.$refs.pageNum.innerHTML)
      this.pageInfo.currentPage = 1
      this.$store.dispatch('listNotification', this.pageInfo)
    },
    focus3 () {
      //   console.log(this.$refs.pageNum.innerHTML)
      this.pageInfo.currentPage = 2
      this.$store.dispatch('listNotification', this.pageInfo)
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
