import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AboutusComponent } from './component/aboutus/aboutus.component';
import { HomeComponent } from './component/home/home.component';
import { AppHeaderComponent } from './component/app-header/app-header.component';
import { ChangePasswordComponent } from './component/change-password/change-password.component';
import { ContactUsComponent } from './component/contact-us/contact-us.component';
import { PagingComponent } from './component/paging/paging.component';
import { AdminHeaderComponent } from './component/admin/admin-header/admin-header.component';
import { AdminHomeComponent } from './component/admin/admin-home/admin-home.component';
import { AdminAdditemComponent } from './component/admin/admin-additem/admin-additem.component';
import { AdminListitemComponent } from './component/admin/admin-listitem/admin-listitem.component';
import { AdminOrderlistComponent } from './component/admin/admin-orderlist/admin-orderlist.component';
import { UserHeaderComponent } from './component/user/user-header/user-header.component';
import { UserLoginComponent } from './component/user/user-login/user-login.component';
import { UserSignupComponent } from './component/user/user-signup/user-signup.component';
import { UserOrderComponent } from './component/user/user-order/user-order.component';
import { UserOrderHistoryComponent } from './component/user/user-order-history/user-order-history.component';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import {MatMenuModule} from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { FooterComponent } from './footer/footer.component';
import { DatePipe } from '@angular/common';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatFormFieldModule} from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MAT_DIALOG_DATA, MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatNativeDateModule, MatRippleModule } from '@angular/material/core';
import { UserCartComponent } from './component/user/user-cart/user-cart.component';
import { UserHomeComponent } from './component/user/user-home/user-home.component';
import { ForgotPasswordComponent } from './component/forgot-password/forgot-password.component';
import {MatButtonToggleModule} from '@angular/material/button-toggle';
import { FoodorderService } from './foodorder.service';



@NgModule({
  declarations: [
    AppComponent,
    AboutusComponent,
    HomeComponent,
    AppHeaderComponent,
    ChangePasswordComponent,
    ContactUsComponent,
    PagingComponent,
    AdminHeaderComponent,
    AdminHomeComponent,
    AdminAdditemComponent,
    AdminListitemComponent,
    AdminOrderlistComponent,
    UserHeaderComponent,
    UserLoginComponent,
    UserSignupComponent,
    UserOrderComponent,
    UserOrderHistoryComponent,
    FooterComponent,
    UserCartComponent,
    UserHomeComponent,
    ForgotPasswordComponent
    
     
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    MatIconModule,
    MatMenuModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatSnackBarModule,
    MatRippleModule,
    MatNativeDateModule,
    MatInputModule,
    MatDialogModule,
    MatIconModule,
    MatButtonToggleModule
   
  ],
  providers: [DatePipe,
    FoodorderService,
    MatDialog,
    // {
    //   provide: MAT_DIALOG_DATA,
    //   useValue: {}
    // }
    ],
    
    
  bootstrap: [AppComponent]
})
export class AppModule { }
