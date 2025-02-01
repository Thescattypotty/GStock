import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';
import { AppFloatingConfigurator } from '../../../layout/component/app.floatingconfigurator';

@Component({
    selector: 'app-access-denied',
    standalone: true,
    imports: [
        ButtonModule,
        RouterModule,
        RippleModule,
        AppFloatingConfigurator
    ],
    templateUrl: './access-denied.component.html'
})
export class AccessDeniedComponent {

}
