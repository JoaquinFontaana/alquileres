import { Component, input } from '@angular/core';

@Component({
  selector: 'app-button',
  imports: [],
  templateUrl: './button.html',
  styleUrl: './button.scss'
})
export class Button {
  class = input<string>()
  label = input.required<string>()
  action = input<void>()
  disabled = input<boolean>(false)
  type = input<'button' | 'submit' | 'reset'>('button');

  onClick(e: Event): void {
    if (!this.disabled()) {
      
      if (this.type() !== 'submit') {
        e.preventDefault();
      }
      this.action()
    }
  }
}
