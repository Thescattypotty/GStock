import { Routes } from '@angular/router';
import { AppLayout } from './app/layout/component/app.layout';
import { LoginComponent } from './app/pages/auth/login/login.component';
import { NotFoundComponent } from './app/pages/not-found/not-found.component';
import { ErrorComponent } from './app/pages/auth/error/error.component';
import { AccessDeniedComponent } from './app/pages/auth/access-denied/access-denied.component';
import { DashboardComponent } from './app/pages/dashboard/dashboard.component';
import { authGuard } from './app/guard/auth.guard';
import { UserComponent } from './app/pages/user/user.component';
import { InventoryComponent } from './app/pages/item/inventory/inventory.component';
import { CategoryComponent } from './app/pages/item/category/category.component';
import { ProfileComponent } from './app/pages/profile/profile.component';
import { CompanyComponent } from './app/pages/company/company.component';
import { ContactComponent } from './app/pages/contact/contact.component';
import { QuoteComponent } from './app/pages/quote/quote.component';


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
            },
            {
                path: 'items',
                children: [
                    {
                        path: '', component: InventoryComponent
                    },
                    {
                        path: 'category', component: CategoryComponent
                    }
                ]
            },
            {
                path: 'companies', component: CompanyComponent
            },
            {
                path: 'contacts', component: ContactComponent
            },
            {
                path: 'quotes', component: QuoteComponent
            },
            {
                path: 'profile', component: ProfileComponent
            }
        ]
    },
    {
        path: 'login', redirectTo: '/', pathMatch: 'full'
    },
    {
        path: '**', redirectTo: '/404'
    }
];
