import MemberMainView from '@/views/member/MemberMainView.vue'

const memberMyInfo = () => import('@/components/member/mypage/MemberMyInfo.vue')

export default [
    {
        path: '/bangkok',
        alias: ['/bangkok', '/'],
        name: 'memberMain',
        component: MemberMainView,
        children: [
            {
                path: 'mypage',
                name: 'mypage',
                component: () => import('@/views/member/MemberMypageView.vue'),
                children: [
                    {
                        path: '',
                        component: memberMyInfo,
                        alias: ''
                    },
                    {
                        path: 'member-myinfo',
                        name: 'memberMyInfo',
                        component: memberMyInfo
                    },
                    {
                        path: 'member-modify',
                        name: 'memberModify',
                        component: () => import('@/components/member/mypage/MemberMypageModify.vue')
                    },
                    {
                        path: 'member-like',
                        name: 'memberLike',
                        component: () => import('@/components/member/mypage/MemberLikeHomes.vue')
                    }
                ]
            },
            {
                path: 'estate-sale',
                name: 'estateSale',
                component: () => import('@/components/member/MemberEstateSale.vue')
            },
            {
                path: 'market-price',
                name: 'marketPrice',
                component: () => import('@/components/member/MarketPriceMap.vue')
            },
            {
                path: 'estate-sale-detail/:estateno',
                name: 'estateSaleDetail',
                component: () => import('@/components/member/MemberEstateSaleDetail.vue')
            },
            {
                path: 'member-board',
                name: 'memberBoard',
                component: () => import('@/components/member/MemberBoard.vue'),
                children: [
                    {
                        path: 'notice',
                        name: 'notice',
                        component: () => import('@/components/board/BoardNotice.vue')
                    },
                    {
                        path: 'free',
                        name: 'free',
                        component: () => import('@/components/board/BoardNotice.vue')
                    },
                    {
                        path: 'news',
                        name: 'news',
                        component: () => import('@/components/board/BoardNotice.vue')
                    }
                ]
            },
        ]
    }
]
