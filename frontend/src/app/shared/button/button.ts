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
  action = input.required<() => void>()

  onClick(e: Event){
    e.preventDefault()
    this.action()()
  }
}
