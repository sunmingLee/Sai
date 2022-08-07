import axios from 'axios'
import router from '@/router/index.js'

const boardStore = {
    namespaced: true,
    state: {
        feedList: []
    },
    getters: {
        
    },
    mutations: {
        FEED_All_LIST(state, feed) {
            state.feedList = feed
        },
    },
    actions: {
    //게시글 작성
    boardCreate(context, boardInfo) {
        const familyId = context.state.familyId
        const userId = context.state.userId
        console.log(boardInfo)
        const api_url = 'http://localhost:8080/feed/board'
        axios({
            url: api_url,
            method: 'POST',
            // params
            data: JSON.stringify(boardInfo),
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then((res) => {
            // console.log(res)
            if (res.status === 200) {
            alert('작성이 완료되었습니다')
                router.push({ name: 'feed' })
            } 
            else {
                alert("안됐지롱")
                console.log(res)
            }
        })
        .catch((err) => {
            console.log(err)
            alert('잘못된 코드입니다')
        })
    },
    //피드 조회
    feedAllList({commit}, info) {
        const familyId = info.familyId
        const userId = info.userId
        const api_url = 'http://localhost:8080/feed/' + familyId + '/' + userId

        axios({
            url: api_url,
            method: 'GET'
        })
        .then((res) => {
            commit('FEED_All_LIST', res.data)
        })
        .catch((err) => {
            console.log(err)
        })
    },
    },
    modules: {
        
    }
}
export default boardStore
