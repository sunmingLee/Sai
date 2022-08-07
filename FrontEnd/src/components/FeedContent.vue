<template>
    <div>
        <FeedHeader />
        <div class="feed-wrap">
            <div v-if="feedAllList.length">
                <div v-for="(feed, index) in feedAllList" :key="index" class="feed-div">
                    <div class="content-header">
                        <div v-for="(callsign, index) in familyCallsignList" :key="index" class="famliy-callsign">
                            <span v-if="feed.viewBoardResponseDto.userId === callsign.toUserId">
                                {{callsign.callsign}}
                            </span>
                            <span v-else>{{feed.userName}}</span>
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
                        {{feed.boardLiked}}
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
        </div>
    </div>
</template>
<script>
import { mapState } from 'vuex'

export default {
    name: 'Feed',
    components: {
    },
    created() {
        //피드 조회
        const info = {
            userId: this.$store.state.userId,
            familyId: this.$store.state.familyId
        }
        this.$store.dispatch('feedAllList', info)
        //가족 콜사인 조회
        this.$store.dispatch('callsignList', info)
        console.log(this.$store.state.feedAllList.boardLiked)
    },
    mounted() {
        console.log("뭔데")
    },
    computed: {
        ...mapState(["feedAllList", "userId", "callsign", "familyCallsignList"])
    },
    methods: {
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