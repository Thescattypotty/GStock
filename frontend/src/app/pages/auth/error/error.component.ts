import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';
import { AppFloatingConfigurator } from '../../../layout/component/app.floatingconfigurator';

@Component({
    selector: 'app-error',
    standalone: true,
    imports: [
        ButtonModule,
        RippleModule,
        RouterModule,
        AppFloatingConfigurator
    ],
    templateUrl: './error.component.html'
})
export class ErrorComponent {

}
