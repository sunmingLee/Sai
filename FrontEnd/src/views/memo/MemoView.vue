<template>
    <div class="memo-wrap" id="memo-tab">
        <div class="height-wrap">
            <HeaderTitle title="냉장고"/>
            <div class="memo-content">
                <div class="content-wrap" v-for="(memo, index) in memoAllList" :key="index">
                    <div :class="memo.color">
                        <span>{{memoAllList[index].memoContent}}</span>
                    </div>
                    <button type="button" class="btn-close" aria-label="Close" @click="removeMemo(memo.memoId)"></button>
                </div>
            </div>
            <button id="btn-modal" type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal" @click="goMemoCreate">
                <img style="width:25px;" src="@/assets/images/plus-lg.svg" alt="plus">
            </button>
        </div>
        <BottomTap feedLink="nav-link" refrideLink="nav-link active" albumLink="nav-link" calendarLink="nav-link"></BottomTap>
    </div>
</template>
<script>
import BottomTap from '@/components/common/BottomTap.vue'
import HeaderTitle from '@/components/common/HeaderTitle.vue'
import Button from '@/components/common/Button.vue'
import { mapState, mapActions } from 'vuex'
const memoStore = 'memoStore'

export default {
    name: 'MemoView',
    components: {
        HeaderTitle,
        BottomTap,
        Button
    },
    data () {
        return {
            color : 'LightGoldenRodYellow'
        }
    },
    created () {
        //메모 전체 리스트 조회
        const familyId = localStorage.getItem('familyId')
        this.getMemoAllList(familyId)
    },
    mounted() {
        
    },
    computed: {
        ...mapState(memoStore, ['memoAllList'])
    },
    methods: {
        ...mapActions(memoStore, ['createMemo', 'getMemoAllList', 'deleteMemo']),
        //메모 색상 바꾸기
        memoColor(e) {
            this.color = e.target.value
        },
        //메모 등록 페이지 이동
        goMemoCreate() {
            this.$router.push({name: 'memoCreate'})
        },
        //메모 삭제
        removeMemo(memoId) {
            this.deleteMemo(memoId)
            window.location.reload(true);
        }
        //
    },
}
</script>
<style lang="scss" scoped>
//글 작성 버튼
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
.btn-close{
    position: absolute;
    top: 5%;
    right: 5%;
}
.memo-wrap {
    background-color: rgb(200, 200, 200);
}
.memo-wrap {
  width: 900px;
    .height-wrap{
        height: 100%;
    }

    li {
        list-style: none;
        float: left;
    }

    .flex{
        display: flex;
        justify-content: center;
        text-align: center;
    }
    .memo-content {
        display: flex;
        justify-content: center;
        flex-wrap: wrap;
        .content-wrap {
            position: relative;
            width: 235px;
            height: 210px;
            text-align: center;
            margin: 20px auto 20px auto;
            .LightGoldenRodYellow {
                display: flex;
                align-items: center;
                justify-content: center;
                height: 210px;
                background-color: lightgoldenrodyellow;
                border: 1px solid rgb(245, 214, 178);
            }
            .PaleGreen {
                display: flex;
                align-items: center;
                justify-content: center;
                height: 210px;
                background-color: palegreen;
                border: 1px solid rgb(65, 159, 65);
            }
            .LightPink {
                display: flex;
                align-items: center;
                justify-content: center;
                height: 210px;
                background-color: lightpink;
                border: 1px solid rgb(163, 68, 83);
            }
            .LightBlue {
                display: flex;
                align-items: center;
                justify-content: center;
                height: 210px;
                background-color: lightblue;
                border: 1px solid rgb(74, 140, 162);
            }
            .Orange {
                display: flex;
                align-items: center;
                justify-content: center;
                height: 210px;
                background-color: #F28D52;
                border: 1px solid #ee7f3e;
            }
            .Purple {  
                display: flex;
                align-items: center;
                justify-content: center;
                height: 210px;
                background-color: #b9b7fc;
                border: 1px solid #9b98fd;
            }
            .Mint {
                display: flex;
                align-items: center;
                justify-content: center;
                height: 210px;
                background-color: #87fdda;
                border: 1px solid #71dbbb;
            }
            .Yellow {
                display: flex;
                align-items: center;
                justify-content: center;
                height: 210px;
                background-color: #f5df4d;
                outline-color: #f5ba4d;
                border: 1px solid #f5ba4d;
            }
            span {
                width: 235px;
                height: 210x;
            }
        }
    }
    
}
</style>
