<template>
    <div class="header">
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
        <div class="flex right">
            <!-- 유저 사진 -->
            <div v-if="userInfo.userImagePath === null" @click="dropDown">
                <img src="@/assets/images/person-circle.svg" alt="user" style="width:28px">
            </div>
            <div v-else @click="dropDown"> 
                <img :src="userInfo.userImagePath">
            </div>
            <!-- <router-link :to="{name : 'detailreview'}">상세보기</router-link> -->
            <div class="drop-menu">
                <div v-if="menuFlag" class="mymenu-wrap">
                    <ul>
                        <li>{{userInfo.userName}}</li>
                        <hr>
                        <li @click="goMyPage">내 페이지</li>
                        <li @click="goAccount">계정관리</li>
                        <li @click="logout">로그아웃</li>
                    </ul>
                </div>
            </div>
            <!-- 알림 -->
            <div v-if="notificationCount === 0" @click="goNotice">
                <img src="@/assets/images/alert-svgrepo-com.svg" alt="calendar" style="width: 28px">
            </div>
            <div v-else @click="goNotice">
                <img src="@/assets/images/alert-on-svgrepo-com.svg" alt="calendar" style="width: 28px">
            </div>
        </div>
    </div>
</template>
<script>
import { mapState, mapActions, mapGetters } from 'vuex'

const familyStore = 'familyStore'
const notificationStore = 'notificationStore'
const userStore = 'userStore'
export default {
    name: 'FeedHeader',
    data () {
        return {
            menuFlag: false
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
        ...mapState(notificationStore, ['notificationList']),
        ...mapGetters(notificationStore, ['notificationCount'])
    },
    methods: {
        ...mapActions(familyStore, ['getFamilyInfo']),
        ...mapActions(notificationStore, ['listNotification']),
        //가족 정보 수정 페이지로 이동
        goFamilyUpdate() {
            this.$router.push({ name: "familyInfoChange" });
        },
        //드롭다운
        dropDown() {
            console.log(this.menuFlag)
            if(this.menuFlag) {
                this.menuFlag = false
            } else {
                this.menuFlag = true
            }
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
.drop-menu {
    position: relative;
}

.header {
    top: 0;
    padding: 0 5%;
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 5% 0 2% 0;
    .flex {
        display: flex;
        justify-content: space-evenly;
        &.right {
            justify-content: flex-end
        }
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
//유저 메뉴
.mymenu-wrap {
    // display: none;
    border: 1px solid #F0EAE3;
    background-color: #F0EAE3;
    width: 86px;
    height: 162px;
    position: absolute;
    top: 102%;
    right: -30px;
    z-index: 1;
    ul {
        margin: 0;
        padding: 0;
        :nth-child(1) {
            margin: 5px 0 5px 0;
            font-weight: bold;
        }
    }
    li {
        text-align: center;
        margin: 13px 0 13px 0;
    }
    hr {
        margin: 0;
    }
}
</style>
