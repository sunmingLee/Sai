<template>
    <div>

    </div>
</template>
<script>

const flag = true
const formData = new FormData();
const poll = {
    boardId: 0,
    question: '중식고르기'
}
const createBoardRequestDto =  {
    inputBoardRequestDto: {
        boardContent : "피곤해",
        pollYn: true
    },
    pollRequest : poll
}

const a = 1
formData.append('test', a)
// formData.append('createBoardRequestDto', createBoardRequestDto)


console.log(formData)
console.log(formData.get('test'))

export default {
    
}
</script>