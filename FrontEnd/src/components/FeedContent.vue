<template>
    <div>
        <FeedHeader />
        <div class="feed-wrap">
            <div v-if="feedList.length">
                <div v-for="(feed, index) in feedList" :key="index" class="feed-div">
                    <div class="content-header">
                        <div v-for="(callsign, index) in familyCallsignList" :key="index" class="famliy-callsign">
                            <div v-if="feed.viewBoardResponseDto.userId === callsign.fromUserId">
                                <span>{{this.userName}}</span>
                            </div>
                            <div v-else>
                                <span>{{callsign.callsign}}</span>
                            </div>
                        </div>
                        <span>{{feed.viewBoardResponseDto.boardRegDatetime.substring(0,10)}}</span>
                    </div>
                    <div class="content-body">
                        <div v-if="feed.viewBoardResponseDto.pollYn">
                            <p>투표 있다</p>
                        </div>
                        <div v-else-if="feed.viewBoardMediaResponseDto !== null">
                            <p>미디어 있다</p>
                        </div>
                        <div v-else>
                            <p>글 있다</p>
                            <p>{{feed.viewBoardResponseDto.boardContent}}</p>
                        </div>
                    </div>
                    <div class="content-cnt">
                        <img v-if="feed.boardLiked" :src="like" @click="unlikeButton" class="like-icon">
                        <img v-else :src="unlike" @click="likeButton">
                    </div>
                    <div class="content-reply">
                        
                    </div>
                </div>
            </div>
            <div v-else>
                <h3>등록된 게시글이 없습니다</h3>
            </div>
            <div>
                <button @click="goBoardCreate" style="color: red">글 작성</button>
            </div>
        </div>
    </div>
</template>
<script>
import { mapActions, mapState } from 'vuex'

const boardStore = 'boardStore'
const userStore = 'userStore'
const familyStore = 'familyStore'

export default {
    name: 'FeedContent',
    components: {
    },
    created() {
        //피드 조회
        const info = {
            userId: localStorage.getItem('userId'),
            // familyId: this.familyId
            familyId: localStorage.getItem('familyId')
        }
        console.log(info)
        this.feedAllList(info)
        this.callsignList(info.userId)
        // console.log(this.userInfo)
    },
    mounted() {
    },
    computed: {
        ...mapState(boardStore, ["feedList"]),
        ...mapState(userStore, ["familyId"]),
        ...mapState(familyStore, ["familyCallsignList"])
    },
    methods: {
        ...mapActions(boardStore, ['feedAllList']),
        ...mapActions(familyStore, ['callsignList']),
        //좋아요 버튼 클릭
        likeButton() {
            const info = {
                userId: this.$store.state.userId,
                familyId: this.$store.state.familyId
            }
            const icon = document.querySelector('.like-icon')
            console.log(icon)
            this.unlike = require('@/assets/images/heart-fill.svg')
            if(this.$store.state.feedAllList.boardLiked) {
                console.log('좋아요')
                
                // console.log(document.querySelector('.like-icon'))
                // this.$store.dispatch('likeClick', info)
            }
            else {
                console.log('좋아요 취소')
                this.unlike = require('@/assets/images/heart.svg')
                // this.$store.dispatch('unlikeClick', info)
            }
        },
        //좋아요 취소
        unlikeButton() {

        },
        goBoardCreate() {
            this.$router.push({ name: "feedCreate" });
        }
    },
    data() {
        return {
            test: '',
            like: require('@/assets/images/heart-fill.svg'),
            unlike: require('@/assets/images/heart.svg')
        }        
    }
}

</script>
<style lang="scss">
.feed-wrap {
    border: 1px solid black;
}
.famliy-callsign {
    display: inline-block;
}
</style>