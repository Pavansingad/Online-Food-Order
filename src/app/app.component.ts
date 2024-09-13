import { Component } from '@angular/core';
import { NavigationStart, Router } from '@angular/router';
import { filter } from 'rxjs';
import { FoodorderService } from './foodorder.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'angular-food-order';
  isLoggedIn: boolean = false;
  isAdminLoggedIn: boolean = false;

  constructor(
    private route:Router,
    private bservice:FoodorderService)
    {
      this.route.events.pipe(
        filter(event => event instanceof NavigationStart)
      ).subscribe((event: any) => {
        if (this.bservice.getUserRole() !== null && this.bservice.getUserRole() === "client") {
          setTimeout(() => {
            this.isLoggedIn = true;
            this.isAdminLoggedIn = false;
          }, 100);
        } else {
          console.log('>>>>>>', this.bservice.getUserRole());
          if (this.bservice.getUserRole() !== null && this.bservice.getUserRole() === "admin") {
            setTimeout(() => {
              console.log("11111111111");
              this.isAdminLoggedIn = true;
              this.isLoggedIn = false;
            }, 100);
          } {
            setTimeout(() => {
              console.log("222222222");
              this.isLoggedIn = false;
              this.isAdminLoggedIn = false;
            }, 1);
          }
        }
      });
}
}
