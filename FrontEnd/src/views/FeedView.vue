<template>
    <div>
        <FeedHeader />
        <FeedContent/>
            <!-- <div v-if="feedList.length">
                <div v-for="(feed, index) in feedList" :key="index" class="feed-wrap">
                    <div>
                        <span v-for="(user, index) in userList" :key="index">
                            <img v-if="feed.userId === user.userId" :src="user.userImagePath">
                        </span>
                        <span v-if="feed.userId === id">{{ user.userName }}</span>
                        <span v-else>
                            <span v-for="(family, index) in familyList" :key="index">
                                <span v-if="family.toUser.userId === feed.userId">
                                    {{ family.callsign }}
                                </span>
                            </span>
                        </span>
                        <span>{{ feed.boardRegDatetime }}</span>
                    </div>
                    <div v-if="feed.pollYn">
                        <div v-for="(poll, index) in pollList" :key="index">
                            <div v-if="feed.boardId === poll.boardId">
                                <p>{{ poll.pollTitle }}</p>
                                <p v-show="poll.pollOption1">{{ poll.pollOption1 }}</p>
                                <p v-show="poll.pollOption2">{{ poll.pollOption2 }}</p>
                                <p v-show="poll.pollOption3">{{ poll.pollOption3 }}</p>
                                <p v-show="poll.pollOption4">{{ poll.pollOption4 }}</p>
                                <p v-show="poll.pollOption5">{{ poll.pollOption5 }}</p>
                            </div>
                        </div>
                    </div>
                    <div v-else-if="feed.boardMediaYn" class="media-wrap">
                        <div v-for="(media, index) in boardMediaList" :key="index">
                            <div v-if="feed.boardId === media.boardId">
                                <img :src="media.boardMediaPath" alt="사진">
                            </div>
                        </div>
                    </div>
                    <div v-else>
                        <p>{{ feed.boardContent }}</p>
                    </div>
                </div>
            </div>
            <div v-else>
                <p>등록된 게시글이 없습니다</p>
            </div> -->
    </div>
</template>
<script>
import FeedContent from '@/components/FeedContent.vue'
import FeedHeader from '@/components/FeedHeader.vue'
import Button from '../components/common/Button.vue'

import { mapState } from 'vuex'

export default {
    name: 'Feed',
    components: {
        FeedHeader,
        Button,
        FeedContent
    },
    created() {
    },
    computed: {
    },
    methods: {

    },
    data() {
        return {

        }        
    }
}

</script>
<style lang="scss">
.feed-wrap {
    border: 1px solid black;
}
</style>