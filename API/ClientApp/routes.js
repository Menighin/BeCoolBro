import CounterExample from 'components/CounterExample'
import FetchData from 'components/FetchData'
import HomePage from './pages/Home.vue'
import AdminPage from './pages/Admin.vue'

export const routes = [
    { path: '/', component: HomePage, display: 'Home', style: 'glyphicon glyphicon-home' },
    { path: '/admin', component: AdminPage, display: 'Admin', style: 'glyphicon glyphicon-home' },
    { path: '/counter', component: CounterExample, display: 'Counter', style: 'glyphicon glyphicon-education' },
    { path: '/fetch-data', component: FetchData, display: 'Fetch data', style: 'glyphicon glyphicon-th-list' }
]