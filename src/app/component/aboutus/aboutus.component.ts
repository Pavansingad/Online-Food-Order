import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-aboutus',
  templateUrl: './aboutus.component.html',
  styleUrls: ['./aboutus.component.css']
})
export class AboutusComponent {
  url:string='/';
  constructor(private route:Router){
    
  }
  gotourl(url: string): void {
   this.route.navigate(["/"+url]);
 }
}
