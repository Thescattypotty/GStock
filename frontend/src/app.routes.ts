import { Routes } from '@angular/router';
import { AppLayout } from './app/layout/component/app.layout';
import { LoginComponent } from './app/pages/auth/login/login.component';
import { NotFoundComponent } from './app/pages/not-found/not-found.component';
import { ErrorComponent } from './app/pages/auth/error/error.component';
import { AccessDeniedComponent } from './app/pages/auth/access-denied/access-denied.component';
import { DashboardComponent } from './app/pages/dashboard/dashboard.component';
import { authGuard } from './app/guard/auth.guard';
import { UserComponent } from './app/pages/user/user.component';


export const appRoutes: Routes = [
    {
        path: '', component: LoginComponent
    },
    {
        path: '404', component: NotFoundComponent
    },
    {
        path: '500', component: ErrorComponent
    },
    {
        path: '501', component: AccessDeniedComponent
    },
    {
        path: 'dashboard',
        component: AppLayout,
        canActivate: [authGuard],
        children: [
            {
                path: '', component: DashboardComponent
            },
            {
                path: 'users', component: UserComponent
            }
        ]
    },
    {
        path: '**', redirectTo: '/404'
    }
];
