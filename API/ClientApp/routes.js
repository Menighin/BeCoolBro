import HomePage from './pages/Home.vue'
import AdminPage from './pages/Admin.vue'

export const routes = [
    { path: '/', component: HomePage, display: 'Home', style: 'glyphicon glyphicon-home' },
    { path: '/admin', component: AdminPage, display: 'Admin', style: 'glyphicon glyphicon-home' }
]