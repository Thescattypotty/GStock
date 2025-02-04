import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MenuItem } from 'primeng/api';
import { AppMenuitem } from './app.menuitem';

@Component({
    selector: 'app-menu',
    standalone: true,
    imports: [CommonModule, AppMenuitem, RouterModule],
    template: `<ul class="layout-menu">
        <ng-container *ngFor="let item of model; let i = index">
            <li app-menuitem *ngIf="!item.separator" [item]="item" [index]="i" [root]="true"></li>
            <li *ngIf="item.separator" class="menu-separator"></li>
        </ng-container>
    </ul> `
})
export class AppMenu {
    model: MenuItem[] = [];

    ngOnInit() {
        this.model = [
            {
                label: 'Home',
                items: [{ label: 'Dashboard', icon: 'pi pi-fw pi-home', routerLink: ['/dashboard'] }]
            },
            {
                label: 'Inventory',
                items: [
                    { label: 'Items', icon: 'pi pi-fw pi-list', routerLink: ['/dashboard/items'] },
                    { label: 'Categories', icon: 'pi pi-fw pi-table', routerLink: ['/dashboard/items/category'] },
                ]
            },
            {
                label: 'Sales',
                items: [
                    { label: 'Companies', icon: 'pi pi-fw pi-building', routerLink: ['/dashboard/companies'] },
                    { label: 'Contacts', icon: 'pi pi-fw pi-users', routerLink: ['/dashboard/contacts'] },
                    { label: 'Quotes', icon: 'pi pi-fw pi-briefcase', routerLink: ['/dashboard/quotes'] }
                ]
            },
            {
                label: 'Administrator',
                icon: 'pi pi-fw pi-briefcase',
                routerLink: ['/dashboard'],
                items: [
                    {
                        label: 'Manage Users',
                        icon: 'pi pi-fw pi-user',
                        routerLink: ['users']
                    },
                ]
            },
            {
                label: 'Author',
                items: [
                    {
                        label: 'View Source',
                        icon: 'pi pi-fw pi-github',
                        url: 'https://github.com/Thescattypotty/GStock',
                        target: '_blank'
                    }
                ]
            }
        ];
    }
}
