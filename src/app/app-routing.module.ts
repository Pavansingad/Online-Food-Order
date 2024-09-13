import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './component/home/home.component';
import { AboutusComponent } from './component/aboutus/aboutus.component';
import { ContactUsComponent } from './component/contact-us/contact-us.component';
import { UserLoginComponent } from './component/user/user-login/user-login.component';
import { ForgotPasswordComponent } from './component/forgot-password/forgot-password.component';
import { AdminHomeComponent } from './component/admin/admin-home/admin-home.component';
import { UserHomeComponent } from './component/user/user-home/user-home.component';
import { UserCartComponent } from './component/user/user-cart/user-cart.component';
import { UserOrderComponent } from './component/user/user-order/user-order.component';
import { AdminAdditemComponent } from './component/admin/admin-additem/admin-additem.component';
import { AdminHeaderComponent } from './component/admin/admin-header/admin-header.component';
import { AdminOrderlistComponent } from './component/admin/admin-orderlist/admin-orderlist.component';
import { AdminListitemComponent } from './component/admin/admin-listitem/admin-listitem.component';
import { UserSignupComponent } from './component/user/user-signup/user-signup.component';
import { ChangePasswordComponent } from './component/change-password/change-password.component';

const routes: Routes = [
  {path:'',component:HomeComponent},
  {path:'aboutus',component:AboutusComponent},
  {path:'contactus',component:ContactUsComponent},
  {path:'user-login',component:UserLoginComponent},
  {path:'user-signup',component:UserSignupComponent},
  {path:'forgot-password',component:ForgotPasswordComponent},
  {path:'change-password',component:ChangePasswordComponent},
  
  
    {path:'admin',children:[

      {path:'home',component:AdminHomeComponent},
      {path:'additem',component:AdminAdditemComponent},
      {path:'header',component:AdminHeaderComponent},
      {path:'order-list',component:AdminOrderlistComponent},
      {path:'itemlist',component:AdminListitemComponent},
  
    
    ]},
  
  {path:'user',children:[
    {path:'home',component:UserHomeComponent},
    {path:'cart',component:UserCartComponent},
    {path:'order',component:UserOrderComponent},
    {path:'login',component:UserLoginComponent}
   ]} 
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
