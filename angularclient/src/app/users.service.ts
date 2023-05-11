import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {firstValueFrom} from "rxjs";

@Injectable({
  providedIn: "root"
})
export class UsersService {

  constructor(private httpClient: HttpClient) {
  }

  getUser(current: string) {
    return firstValueFrom(this.httpClient.get("/api/users", {params: {name: current}}))
  }

  getAge(): Promise<User> {
    return firstValueFrom(this.httpClient.get<User>("/api/users/maxAge"))
  }

  getUsers() {
    return firstValueFrom(this.httpClient.get<User[]>("/api/users/statistic"))
  }

}


export interface User {
  name: string
  age: any
}
