import AgentMainView from '@/views/agent/AgentMainView.vue'

export default [
  {
    path: "/agent",
    name: "agentMain",
    component: AgentMainView,
    children: [
      {
        path: 'estate-board',
        name: 'estateBoard',
        component: () => import('@/components/agent/estate/EstateBoard.vue'),
      },
      {
        path: 'detail/:id',
        name: 'estateDetail',
        component: () => import('@/components/agent/estate/EstateDetail.vue')
      },
      {
        path: 'register',
        name: 'estateRegister',
        component: () => import('@/components/agent/estate/EstateRegister.vue'),
        children: [
          {
            path: 'estate-name-search',
            name: 'estateNameSearch',
            component: () => import('@/components/agent/estate/EstateNameSearch.vue')
          },
        ]
      },
      {
        path: 'agent-schedule',
        name: 'agentSchedule',
        component: () => import('@/components/agent/AgentSchedule.vue'),
      }
    ]
  }
]