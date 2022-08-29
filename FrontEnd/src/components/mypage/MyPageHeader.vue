<template>
    <div class="header-wrap">
        <div class="flex">
            <div class="default-image" v-if="userInfo.userImagePath === null">
                <img src="@/assets/images/person-circle.svg" >
            </div>
            <div v-else class="user-image">
              <img :src="userInfo.userImagePath">
              <!-- <img src="@/assets/images/여행1.jpg"> -->
            </div>
            <div>
                <div class="user-update">
                    <span>{{userInfo.userName}}</span>
                    <img src="@/assets/images/pencil-fill.svg" alt="calendar" style="width: 15px" @click="goUpdateUser">
                </div>
                <div class="user-msg">
                    <p v-if="userInfo.userMessage === null || userInfo.userMessage === '' || userInfo.userMessage === undefined">상태 메시지를 등록해주세요</p>
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
  created () {
    // 피드 조회
    const userId = localStorage.getItem('userId')
    // 유저 정보 조회
    this.checkUserInfo(userId)
  },
  mounted () {
  },
  computed: {
    ...mapState(userStore, ['userInfo'])
  },
  methods: {
    ...mapActions(userStore, ['checkUserInfo']),

    // 프로필 편집 페이지 이동
    goUpdateUser () {
      this.$router.push({ name: 'modifyInformation' })
    }
  }
}
</script>
<style lang="scss" scoped>
p {
  margin: 0;
}
.header-wrap {
  margin: 5% 0 2% 0;
}
.flex {
  display: flex;
  justify-content: space-evenly;
}
.default-image {
  width: 100px;
  height: auto;
  img {
    width: 100%;
  }
}
.user-image {
  border: 1px solid black;
  border-radius: 50px;
  max-width: 100px;
  min-height: 100px;
  img {
    width: 100%;
    height: 100%;
    border-radius: 50px;
  }
}
.user-update {
  margin: 4px 0 4px 0;
  span {
    font-size: 19px;
    font-weight: bold;
  }
}
hr {
  height: 3px;
  background-color: #7B371C;
}

</style>
