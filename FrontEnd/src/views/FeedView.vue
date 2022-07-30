<template>
    <div>
        <FeedHeader />
        <div class="feed-wrap">
            <div v-if="feedList.length">
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
            </div>
        </div>
    </div>
</template>
<script>
import FeedHeader from '@/components/FeedHeader.vue'
import Button from '../components/common/Button.vue'

export default {
    name: 'Feed',
    components: {
        FeedHeader,
        Button
    },
    methods: {
    },
    data() {
        return {
            // feedList: []
            id: 'tnqls',
            feedList: [
                {
                    userId: 'tnqls',
                    familyId: 123456,
                    boardId: 1,
                    boardContent: '뷰 싫어',
                    boardRegDatetime: '2022-07-28',
                    boardMediaYn: 0,
                    boardLocation: '',
                    boardDate: '',
                    boardPeopleYn: '',
                    pollYn: 1
                },
                {
                    userId: 'tjsals',
                    familyId: 123456,
                    boardId: 2,
                    boardContent: '너두?',
                    boardRegDatetime: '2022-07-29',
                    boardMediaYn: 1,
                    boardLocation: '',
                    boardDate: '',
                    boardPeopleYn: '',
                    pollYn: 0
                },
                {
                    userId: 'aldo',
                    familyId: 123456,
                    boardId: 3,
                    boardContent: '야나두!',
                    boardRegDatetime: '2022-07-29',
                    boardMediaYn: 0,
                    boardLocation: '',
                    boardDate: '',
                    boardPeopleYn: '',
                    pollYn: 1
                },
                {
                    userId: 'gmldud',
                    familyId: 123456,
                    boardId: 4,
                    boardContent: '피드 왜 한다 했지~?',
                    boardRegDatetime: '2022-07-30',
                    boardMediaYn: 0,
                    boardLocation: '',
                    boardDate: '',
                    boardPeopleYn: '',
                    pollYn: 0
                },
                {
                    userId: 'rnstjs',
                    familyId: 123456,
                    boardId: 5,
                    boardContent: '피드 왜 한다 했지~?',
                    boardRegDatetime: '2022-07-30',
                    boardMediaYn: 1,
                    boardLocation: '',
                    boardDate: '',
                    boardPeopleYn: '',
                    pollYn: 0
                },
                {
                    userId: 'tkddn',
                    familyId: 123456,
                    boardId: 6,
                    boardContent: '살려주세요',
                    boardRegDatetime: '2022-07-31',
                    boardMediaYn: 0,
                    boardLocation: '',
                    boardDate: '',
                    boardPeopleYn: '',
                    pollYn: 0
                },
            ],
            pollList: [
                {
                    pollId: 1,
                    boardId: 1,
                    pollTitle: '오늘 점심 뭐 먹지?',
                    pollOption1: '김치찌개',
                    pollOption2: '된장찌개',
                    pollOption3: '부대찌개',
                    pollOption4: '굶어'
                },
                {
                    pollId: 2,
                    boardId: 3,
                    pollTitle: '가고 싶은 여행지',
                    pollOption1: '일본',
                    pollOption2: '중국',
                    pollOption3: '러시아'
                }
            ],
            boardMediaList: [
                {
                    boardMediaId: 1,
                    boardId: 2,
                    boardMediaPath: require('@/assets/images/bye.png'),
                    boardMediaName: 'bye',
                    boardMediaName: 'png'
                },
                {
                    boardMediaId: 1,
                    boardId: 5,
                    boardMediaPath: require('@/assets/images/byebye.png'),
                    boardMediaName: 'byebye',
                    boardMediaName: 'png'
                }
            ],
            familyList: [
                {
                    familyCallsignId: 1,
                    fromUser : {
                        userId: 'tnqls',
                        family: {
                            familyId: 123456,
                            familyName: '우리 가족',
                            familyImagePath: null,
                            familyImageName: null,
                            familyImageType: null
                        },
                        userName: '최수빈',
                    },
                    toUser: {
                        userId: 'tjsals',
                        fmaily: {
                            familyId: 123456,
                            familyName: '우리 가족',
                            familyImagePath: null,
                            familyImageName: null,
                            familyImageType: null
                        },
                        userName: '이선민'
                    },
                    callsign: '바보개'
                },
                {
                    familyCallsignId: 2,
                    fromUser : {
                        userId: 'tnqls',
                        family: {
                            familyId: 123456,
                            familyName: '우리 가족',
                            familyImagePath: null,
                            familyImageName: null,
                            familyImageType: null
                        },
                        userName: '최수빈',
                    },
                    toUser: {
                        userId: 'aldo',
                        fmaily: {
                            familyId: 123456,
                            familyName: '우리 가족',
                            familyImagePath: null,
                            familyImageName: null,
                            familyImageType: null
                        },
                        userName: '김미애'
                    },
                    callsign: '미애'
                },
                {
                    familyCallsignId: 3,
                    fromUser : {
                        userId: 'tnqls',
                        family: {
                            familyId: 123456,
                            familyName: '우리 가족',
                            familyImagePath: null,
                            familyImageName: null,
                            familyImageType: null
                        },
                        userName: '최수빈',
                    },
                    toUser: {
                        userId: 'gmldud',
                        fmaily: {
                            familyId: 123456,
                            familyName: '우리 가족',
                            familyImagePath: null,
                            familyImageName: null,
                            familyImageType: null
                        },
                        userName: '김희영'
                    },
                    callsign: '희영이'
                },
                {
                    familyCallsignId: 4,
                    fromUser : {
                        userId: 'tnqls',
                        family: {
                            familyId: 123456,
                            familyName: '우리 가족',
                            familyImagePath: null,
                            familyImageName: null,
                            familyImageType: null
                        },
                        userName: '최수빈',
                    },
                    toUser: {
                        userId: 'rnstjs',
                        fmaily: {
                            familyId: 123456,
                            familyName: '우리 가족',
                            familyImagePath: null,
                            familyImageName: null,
                            familyImageType: null
                        },
                        userName: '진군선'
                    },
                    callsign: '왕자님'
                },
                {
                    familyCallsignId: 1,
                    fromUser : {
                        userId: 'tnqls',
                        family: {
                            familyId: 123456,
                            familyName: '우리 가족',
                            familyImagePath: null,
                            familyImageName: null,
                            familyImageType: null
                        },
                        userName: '최수빈',
                    },
                    toUser: {
                        userId: 'tkddn',
                        fmaily: {
                            familyId: 123456,
                            familyName: '우리 가족',
                            familyImagePath: null,
                            familyImageName: null,
                            familyImageType: null
                        },
                        userName: '진상우'
                    },
                    callsign: '진짜루'
                },
            ],
            user: {
                userName: '최수빈'
            },
            userList: [
                {
                    userId: 'tnqls',
                    familyId: 123456,
                    userName: '최수빈',
                    email: 'tnqls@naver.com',
                    password: 'asdf1234@',
                    birthday: '1234-56-78',
                    lunar: 0,
                    userImagePath: '',
                    userImageType: 'png',
                    userMessage: '' 
                },
                {
                    userId: 'tjsals',
                    familyId: 123456,
                    userName: '이선민',
                    email: 'tjsal@naver.com',
                    password: 'asdf1234!',
                    birthday: '1234-56-78',
                    lunar: 0,
                    userImagePath: require('@/assets/images/dog.png'),
                    userImageType: 'png',
                    userMessage: '' 
                },
                {
                    userId: 'aldo',
                    familyId: 123456,
                    userName: '김미애',
                    email: 'aldo@naver.com',
                    password: 'asdf1234@',
                    birthday: '1324-56-78',
                    lunar: 0,
                    userImagePath: '',
                    userImageType: 'png',
                    userMessage: '' 
                },
                {
                    userId: 'gmldud',
                    familyId: 123456,
                    userName: '김희영',
                    email: 'gmldud@naver.com',
                    password: 'asdf1234#',
                    birthday: '2134-56-78',
                    lunar: 0,
                    userImagePath: require('@/assets/images/dog2.png'),
                    userImageType: 'png',
                    userMessage: '' 
                },
                {
                    userId: 'rnstjs',
                    familyId: 123456,
                    userName: '진군선',
                    email: 'rnstjs@naver.com',
                    password: 'asdf1234$',
                    birthday: '1254-56-78',
                    lunar: 0,
                    userImagePath: '',
                    userImageType: 'png',
                    userMessage: '' 
                },
                {
                    userId: 'tkddn',
                    familyId: 123456,
                    userName: '진상우',
                    email: 'tkddn@naver.com',
                    password: 'asdf1234%',
                    birthday: '1263-56-78',
                    lunar: 0,
                    userImagePath: '',
                    userImageType: 'png',
                    userMessage: '' 
                }
            ]
        }
    }
}
</script>
<style lang="scss">
.feed-wrap {
    border: 1px solid black;
}
</style>