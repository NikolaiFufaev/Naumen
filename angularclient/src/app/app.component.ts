import {Component} from '@angular/core';
import {User, UsersService} from "./users.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'angularclient'
  age: any = null
  current: string = ""
  users: User[] = [];
  user: User = {
    age: '',
    name: ''
  }

  constructor(private userService: UsersService) {
  }

  async doLoad() {
    this.age = await this.userService.getUser(this.current)

  }

  async doStats() {
    this.users = await this.userService.getUsers()

  }

  async doMaxAge() {
    this.user = await this.userService.getAge()

  }

}
