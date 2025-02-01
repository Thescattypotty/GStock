import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AppFloatingConfigurator } from '../../layout/component/app.floatingconfigurator';
import { ButtonModule } from 'primeng/button';

@Component({
    selector: 'app-not-found',
    imports: [
        RouterModule,
        AppFloatingConfigurator,
        ButtonModule
    ],
    templateUrl: './not-found.component.html'
})
export class NotFoundComponent {

}
