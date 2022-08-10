<template>
    <div>
        <HeaderTitle hasBack=true title="메모지"/>
        <div>
            <textarea name="memo" id="memo-text" cols="30" rows="10" :class="color"></textarea>
        </div>
        <div class="color-select">
            <button class="lightgoldenrodyellow-button" value="LightGoldenRodYellow" @click="colorChange"/>
            <button class="palegreen-button" value="PaleGreen" @click="colorChange"/>
            <button class="lightpink-button" value="LightPink" @click="colorChange"/>
            <button class="lightblue-button" value="LightBlue" @click="colorChange"/>
        </div>
        <Button buttonClass="small positive" buttonText="등록" @click="addMeMo"/>
        <!-- <input type="button" id="clear_button" value="스티키 노트 전부 삭제"> --> 
    </div>
</template>
<script>
import HeaderTitle from '@/components/common/HeaderTitle.vue'
import Button from '@/components/common/Button.vue'
import { mapActions } from 'vuex'
const memoStore = 'memoStore'

export default {
    name: 'MemoView',
    components: {
        HeaderTitle,
        Button
    },
    data () {
        return {
            color : 'LightGoldenRodYellow',
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
    },
    methods: {
        ...mapActions(memoStore, ['createMemo', 'getMemoAllList']),
        //메모 색상 바꾸기
        colorChange(e) {
            const color = e.target.getAttribute('value')
            console.log(color)
            this.color = color
        },
        //메모지 등록
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
        }
    },
}
</script>
<style lang="scss" scoped>
.color-select {
    button {
        width: 50px;
        height: 47px;
        border-radius: 27px;
    }
    .lightgoldenrodyellow-button {
        background-color: lightgoldenrodyellow;
        border: 1px solid rgb(188, 188, 90);
    }
    .palegreen-button {
        background-color: palegreen;
        border: 1px solid rgb(65, 159, 65);
    }
    .lightpink-button {
        background-color: lightpink;
        border: 1px solid rgb(163, 68, 83);
    }
    .lightblue-button {
        background-color: lightblue;
        border: 1px solid rgb(74, 140, 162);
    }
}
textarea {
    border: none;
    resize: none;
    text-align: center;
    &.LightGoldenRodYellow {
        background-color: lightgoldenrodyellow;
        outline-color: rgb(188, 188, 90);
        border: 1px solid rgb(188, 188, 90);
    }
    &.PaleGreen {
        background-color: palegreen;
        outline-color: rgb(65, 159, 65);
        border: 1px solid rgb(65, 159, 65);
    }
    &.LightPink {
        background-color: lightpink;
        outline-color: rgb(163, 68, 83);
        border: 1px solid rgb(163, 68, 83);
    }
    &.LightBlue {
        background-color: lightblue;
        outline-color: rgb(74, 140, 162);
        border: 1px solid rgb(74, 140, 162);
    }
}


</style>
