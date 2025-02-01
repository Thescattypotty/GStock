import { Component } from '@angular/core';

@Component({
    standalone: true,
    selector: 'app-footer',
    template: `<div class="layout-footer">
        GStock by
        <a href="https://github.com/ThescattyPotty" target="_blank" rel="noopener noreferrer" class="text-primary font-bold hover:underline">SênShî</a>
    </div>`
})
export class AppFooter {}
