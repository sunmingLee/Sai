<template>
    <div class="memo-wrap">
        <HeaderTitle title="냉장고"/>
        <div>
            <router-link :to="{name : 'memoCreate'}" class="ByPhone">메모지 생성</router-link>
        </div>
        <div class="memo-content">
            <div class="content-wrap" v-for="(memo, index) in memoAllList" :key="index">
                <!-- <li :class="memo.color" >{{memoAllList[index].memoContent}}</li> -->
                <div :class="memo.color">
                    <span>{{memoAllList[index].memoContent}}</span>
                </div>
                <button type="button" class="btn-close" aria-label="Close" @click="removeMemo(memo.memoId)"></button>
                <!-- <Button buttonClass="small negative" buttonText="삭제" @click="delMemo"/> -->
            </div>
        </div>
        <BottomTap></BottomTap>
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
            console.log(this.color)
        },
        //메모 등록
        addMeMo() {
            //글 내용
            const content = document.getElementById('memo-text').value
            //가족 아이디와 유저 아이디
            const familyId = localStorage.getItem('familyId')
            const userId = localStorage.getItem('userId')
            const createMemoRequestDto = {
                familyId : familyId,
                userId: userId,
                color: this.color,
                memoContent: content
            }
            this.createMemo(createMemoRequestDto)
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
                border: 1px solid rgb(188, 188, 90);
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
            span {
                width: 235px;
                height: 210x;
            }
        }
    }
    
}
</style>
