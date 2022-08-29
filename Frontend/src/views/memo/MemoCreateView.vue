<template>
    <div>
        <HeaderTitle hasBack=true title="메모지"/>
        <div>
            <textarea name="memo" id="memo-text" cols="30" rows="10" :class="color"></textarea>
        </div>
        <div class="color-select">
            <button class="lightgoldenrodyellow-button" value="LightGoldenRodYellow" @click="colorChange"/>
            <button class="yellow-button" value="Yellow" @click="colorChange"/>
            <button class="lightpink-button" value="LightPink" @click="colorChange"/>
            <button class="orange-button" value="Orange" @click="colorChange"/>
        </div>
        <div class="color-select">
            <button class="palegreen-button" value="PaleGreen" @click="colorChange"/>
            <button class="mint-button" value="Mint" @click="colorChange"/>
            <button class="lightblue-button" value="LightBlue" @click="colorChange"/>
            <button class="purple-button" value="Purple" @click="colorChange"/>
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
            if(content === '') {
                alert('내용을 입력해주세요')
            } else {
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
        }
    },
}
</script>
<style lang="scss" scoped>
.color-select {
    display: flex;
    justify-content: space-around;
    button {
        width: 50px;
        height: 47px;
        border-radius: 27px;
        margin: 1%;
    }
    .lightgoldenrodyellow-button {
        // #FFC24B;
        background-color: lightgoldenrodyellow;
        border: 1px solid rgb(245, 214, 178);
    }
    .palegreen-button {
        background-color: palegreen;
        border: 1px solid rgb(91, 236, 91);
    }
    .lightpink-button {
        background-color: lightpink;
        border: 1px solid rgb(247, 136, 152);
    }
    .lightblue-button {
        background-color: lightblue;
        border: 1px solid rgb(74, 191, 230);
    }
    .purple-button {
        background-color: #b9b7fc;
        border: 1px solid #9b98fd;
    }
    .orange-button {
        background-color: #F28D52;
        border: 1px solid #ee7f3e
    }
    .mint-button {
        background-color: #87fdda;
        border: 1px solid #71dbbb;  
    }
    .yellow-button {
        background-color: #f5df4d;
        border: 1px solid #f5ba4d;
    }
}
textarea {
    border: none;
    resize: none;
    text-align: center;
    &.LightGoldenRodYellow {
        background-color: lightgoldenrodyellow;
        border: 1px solid rgb(245, 214, 178);
        outline-color: rgb(245, 214, 178);
    }
    &.PaleGreen {
        background-color: palegreen;
        outline-color: rgb(91, 236, 91);
        border: 1px solid rgb(91, 236, 91);
    }
    &.LightPink {
        background-color: lightpink;
        outline-color: rgb(247, 136, 152);
        border: 1px solid rgb(247, 136, 152);
    }
    &.LightBlue {
        background-color: lightblue;
        outline-color: rgb(74, 191, 230);
        border: 1px solid rgb(74, 191, 230);
    }
    &.Purple {
        background-color: #b9b7fc;
        outline-color: #9b98fd;
        border: 1px solid #9b98fd;
    }
    &.Orange {
        background-color: #F28D52;
        outline-color: #ee7f3e;
        border: 1px solid #ee7f3e;
    }
    &.Mint {
        background-color: #87fdda;
        outline-color: #71dbbb;
        border: 1px solid #71dbbb;
    }
    &.Yellow {
        background-color: #f5df4d;
        outline-color: #f5ba4d;
        border: 1px solid #f5ba4d;
    }
}


</style>
