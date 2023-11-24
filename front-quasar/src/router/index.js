import {createRouter, createWebHistory} from "vue-router";
import memberRoutes from "@/router/member-routes";
import commonRoutes from "@/router/common-routes";
import agentRoutes from "@/router/agent-routes";
import managerRoutes from "@/router/manager-routes";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    ...memberRoutes,
    ...commonRoutes,
    ...agentRoutes,
    ...managerRoutes,
  ],
  linkActiveClass: "route-active",
});

export default router;
