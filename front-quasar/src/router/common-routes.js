
export default [
  {
    path: "/login",
    name: "login",
    component: () => import("@/views/common/LoginView.vue"),
  },
  {
    path: "/regist",
    name: "regist",
    component: () => import("@/views/common/RegistView.vue")
  },
  {
    path: "/member-regist",
    name: "memberRegist",
    component: () => import("@/views/member/MemberRegistView.vue")
  },
  {
    path: "/agent-regist",
    name: "agentRegist",
    component: () => import("@/views/agent/AgentRegistView.vue")
  },
]