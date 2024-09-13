import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { take } from 'rxjs';
import { FoodorderService } from 'src/app/foodorder.service';

@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrls: ['./user-login.component.css']
})
export class UserLoginComponent implements OnInit{

errormessage: string='';
emailID: string='';
password:string='';
errormessagep:string='';

constructor(
  private route:Router,
  private  bservice:FoodorderService,
  private fb: FormBuilder){

}
ngOnInit(): void {
}  
  login():void{
    if(this.emailID==="" || this.emailID===undefined ){
      this.errormessage="Email addresss is blank";
      return;
    }

    const re = /\S+@\S+\.\S+/;
   if(!re.test(this.emailID)){
    this.errormessage="Email addresss not valid";
    return;
   }
 this.errormessage="";

    if(this.password==="" || this.password===undefined ){
      this.errormessagep="Password is blank";
      return;
    }
    this.errormessagep ='';
    const body:any={
      "emailID" : this.emailID,
      "password" : this.password
    }
    this.bservice.userSignIn(body).pipe(take(1)).subscribe((res:any)=>{
      // console.log("*******",res);
      if(res && res?.userId){
        alert("Login sucessful");
       if (res?.role) {
        this.bservice.storeUserRole(res?.role);
       }
        this.bservice.storeUserAuthorization(res?.userId);
        let userName = '';
        if (res?.firstName) {
          userName+=res?.firstName;
        }
        if (res?.lastName){
          userName+=' ' + res?.lastName;
        }
        this.bservice.storeUserName(userName);
        console.log('>>>>>>', res?.role );
        if (res?.role === 'admin') {
          this.route.navigate(['/admin/home']);
        } else {
          this.route.navigate(['/user/home']);
        }
      } 
    },err => {
      console.log("Error ", err);
      console.log(">>> ", err);
      if(err?.error && err?.error.startsWith("Customer  not found with")){
        alert("Customer email/password is invalid");
      }
      else{
        alert("Something going wrong in login! pls try again");
      }
  }
  )}
  routeToNewUser(): void {
    this.route.navigate(["/user-signup"]);
  }
  routeToForgotPassword(): void {
    this.route.navigate(["/forgot-password"]);
  }  
    
}
