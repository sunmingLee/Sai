<template>
    <div class="mypage-wrap">
        <div class="flex">
            <div class="user-image">
                <img src="@/assets/images/person-circle.svg" v-if="userInfo.userImagePath === null">
                <img :src="userInfo.userImagePath">
            </div>
            <div>
                <div class="user-update" >
                    <span>{{userInfo.userName}}</span>
                    <img src="@/assets/images/pencil-fill.svg" alt="calendar" style="width: 15px" @click="goUpdateUser"> 
                </div>
                <div class="user-msg">
                    <p v-if="userInfo.userMessage === null">상태 메시지를 등록해주세요</p>
                    <p>{{userInfo.userMessage}}</p>
                </div>
            </div>
        </div>
        <hr>

    </div>
</template>
<script>
import HeaderTitle from '@/components/common/HeaderTitle.vue'
import SearchGuide from '@/components/SearchGuide.vue'
import InputBox from '@/components/common/InputBox.vue'
import Button from '@/components/common/Button.vue'

import { mapActions, mapState } from 'vuex'
const userStore = 'userStore'

export default {
  name: 'SearchIdView',
  components: {
    HeaderTitle,
    SearchGuide,
    InputBox,
    Button
  },
  data () {
    return {
    }
  },
  created() {
    // 피드 조회
    const userId = localStorage.getItem('userId')
    // 유저 정보 조회
    this.checkUserInfo(userId)
  },
  mounted() {
    console.log(this.feedCount)
  },
  computed: {
    ...mapState(userStore, ['userInfo']),
  },
  methods: {
    ...mapActions(userStore, ['checkUserInfo']),
    //프로필 편집 페이지 이동
    goUpdateUser() {
      this.$router.push({name: 'addinformation'})
    }
  }
}
</script>
<style lang="scss" scoped>
p {
  margin: 0;
}
.mypage-wrap {
  width: 900px;

  .flex{
    display: flex;
    justify-content: center;
    text-align: center;

    .input-wrap{
      text-align: left;
      margin-bottom: 100px;

      .name-wrap, .email-wrap {
        margin: 10px 0 10px 0;
      }
    }
  }
}

</style>
