import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'rt-player',
  templateUrl: './player.component.html',
  styleUrls: ['./player.component.css']
})
export class PlayerComponent implements OnInit {
  @Input()
  id: string;

  constructor() {
  }

  ngOnInit() {
  }

}
