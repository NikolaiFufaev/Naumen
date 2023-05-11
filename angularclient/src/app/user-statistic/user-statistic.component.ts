import {Component, Input} from '@angular/core';
import {User} from "../users.service";

@Component({
  selector: 'app-user-statistic',
  templateUrl: './user-statistic.component.html',
  styleUrls: ['./user-statistic.component.css']
})
export class UserStatisticComponent {
  @Input()
  users: User[] = []
}
