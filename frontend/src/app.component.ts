import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MessageService } from 'primeng/api';
import { MessageModule } from 'primeng/message';
import { ToastModule } from 'primeng/toast';

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [RouterModule, MessageModule, ToastModule],
    template: `
        <router-outlet></router-outlet>
        <p-toast position="bottom-right" />
    `,
    providers: [MessageService]
})
export class AppComponent {
    constructor(public messageService: MessageService){

    }
}
