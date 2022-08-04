<template>
  <div class="notification-wrap">
    <HeaderTitle hasBack="true" title="알림함"></HeaderTitle>
    <div class="bottom-border"></div>
    <!-- 알림 목록 -->
    <ul class="list-group list-group-flush" v-if="notificationList.length">
        <li class="list-group-item" v-for="(noti, index) in notificationList" :key="index"><button type="button" class="btn-close" aria-label="Close"></button> {{ noti.notiType }} {{ noti.notiContent}} {{noti.notiDateTime}}</li>
    </ul>
    <ul class="list-group list-group-flush" v-else>
        <li class="list-group-item">알림이 존재하지 않습니다.</li>
    </ul>
    <nav aria-label="...">
        <ul class="pagination">
            <li class="page-item">
                <a class="page-link">Previous</a>
            </li>
            <li class="page-item active" aria-current="page"><a class="page-link" href="#">1</a></li>
            <li class="page-item">
                <a class="page-link" href="#">2</a>
            </li>
            <li class="page-item"><a class="page-link" href="#">3</a></li>
            <li class="page-item">
                <a class="page-link" href="#">Next</a>
            </li>
        </ul>
    </nav>
    <div class="button-wrap">
        <Button v-if="notificationList.length" buttonClass="big positive" buttonText="알림함 비우기"></Button>
        <Button v-else buttonClass="big disable" buttonText="알림함 비우기"></Button>
    </div>
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
    }
  },
  created () {
    this.$store.dispatch('readNotification', localStorage.getItem('userId'))
    this.$store.dispatch('listNotification', localStorage.getItem('userId'))
  },
  computed: {
    ...mapState(['notificationList'])
  },
  methods: {
  }
}
</script>

<style scoped lang="scss">
.notification-wrap{
    width: 900px
}
.bottom-border{
    margin-top: 2%;
    margin-bottom: 5%;
    border-bottom: 2px solid gray;
}
.pagination{
    justify-content: center;
}
.list-group{
    margin-bottom: 5%;
}
.button-wrap{
    text-align: center;
}
</style>
