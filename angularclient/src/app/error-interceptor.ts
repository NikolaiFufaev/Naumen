import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http'
import {catchError, Observable, throwError} from "rxjs";
import {ToastrService} from "ngx-toastr";

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
// Regular dep. injection doesn't work in HttpInterceptor due to a framework issue (as of angular@5.2.9),
// use Injector directly (don't forget to add @Injectable() decorator to class).
  constructor(private toastr: ToastrService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(catchError((err, caught) => {
      this.toastr.success(err.error.error, 'Error');
      return throwError(err.error.error);
    }))
  }
}
