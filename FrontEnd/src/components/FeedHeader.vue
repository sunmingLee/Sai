<template>
    <div class="header">
        <div>
        </div>
        <div class="flex" style="width: 200px">
            <div class="family-image" v-if="familyInfo.familyImagePath !== null">
                <img :src="familyInfo.familyImagePath">
            </div>
            <div class="family-image" v-else>
                <img src="@/assets/images/people-fill.svg" alt="family">
            </div>
            <div class="family-title">
                <span class="title">{{ familyInfo.familyName }}</span>
                <div style="display: inline-block" @click="goFamilyUpdate">
                    <img src="@/assets/images/pencil-fill.svg" alt="calendar" style="width: 15px">
                </div>
            </div>
        </div>
        <div class="flex">
            <!-- 유저 사진 -->
            <div v-if="userInfo.userImagePath === null" id="drop">
                <img src="@/assets/images/person-circle.svg" alt="user" style="width:28px">
            </div>
            <div v-else id="drop"> 
                <img src="@/assets/images/alert-on-svgrepo-com.svg" alt="calendar" style="width: 28px">
            </div>
            <!-- <router-link :to="{name : 'detailreview'}">상세보기</router-link> -->
            <div>
                <ul>
                    <li @click="goMyPage">내 페이지</li>
                    <li @click="goAccount">계정관리</li>
                    <li @clcick="logout">로그아웃</li>
                </ul>
            </div>
            <!-- 알림 -->
            <div v-if="notificationList.length === 0" @click="goNotice">
                <img src="@/assets/images/alert-svgrepo-com.svg" alt="calendar" style="width: 28px">
            </div>
            <div v-else @click="goNotice">
                <img src="@/assets/images/alert-on-svgrepo-com.svg" alt="calendar" style="width: 28px">
            </div>
        </div>
    </div>
</template>
<script>
const familyStore = 'familyStore'
const notificationStore = 'notificationStore'
const userStore = 'userStore'

import { mapState, mapActions } from 'vuex'
export default {
    name: 'FeedHeader',
    date() {
        return {

        }
    },
    created() {
        //알림 조회
        const userId = localStorage.getItem('userId')
        this.listNotification(userId)
        //가족 정보 조회(가족 이름, 이미지)
        const familyId = localStorage.getItem('familyId')
        this.getFamilyInfo(familyId)
    },
    computed: {
        ...mapState(userStore, ['userInfo']),
        ...mapState(familyStore, ['familyInfo']),
        ...mapState(notificationStore, ['notificationList'])
    },
    methods: {
        ...mapActions(familyStore, ['getFamilyInfo']),
        ...mapActions(notificationStore, ['listNotification']),
        //가족 정보 수정 페이지로 이동
        goFamilyUpdate() {
            this.$router.push({ name: "familyInfoChange" });
        },
        //알림함 이동
        goNotice() {
            this.$router.push({name : 'notification'})
        },
        //로그아웃
        logout() {
            //로컬 스토리지에 있는 값을 모두 지우고 로그인 페이지로 이동
            window.localStorage.clear();
            this.$router.push({name: 'login'})
        },
        //마이 페이지 이동
        goMyPage() {
            this.$router.push({name: 'myPage'})
        },
        //계정관리 페이지 이동
        goAccount() {
            this.$router.push({name: 'accountConfirm'})
        }
    }
}
</script>
<style lang="scss" scoped>
.header {
    top: 0;
    padding-left: 5%;
    padding-right: 5%;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: 5%;
    .flex {
        display: flex;
        justify-content: space-evenly;
    }
}
.family-image {
    display: inline-block;
    > img {
        width: 28px;
        border: 1px solid black;
        border-radius: 30px;
    }
}
.title {
  color: #7B371C;
  font-size: 20px;
  font-weight: bold;
}
</style>