import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { FoodorderService } from 'src/app/foodorder.service';
import { Cart } from '../../model/cart.model';
import { Item } from '../../model/item.model';
import * as _ from 'lodash';

import { forkJoin, take } from 'rxjs';

@Component({
  selector: 'app-user-cart',
  templateUrl: './user-cart.component.html',
  styleUrls: ['./user-cart.component.css']
})
export class UserCartComponent implements OnInit {
  cartList: Cart[] = [];
  cartListBackup: Cart[] = [];
  grandTotal: number = 0;
  user: any = {};
 


  constructor(
    private bservice: FoodorderService,
    private router: Router,
    private datePipe: DatePipe
  ) {
    this.bservice.isUserLoginPresent();
    this.getCartList();
    this.getUserDetail();
  }

  ngOnInit(): void {
  }
  getCartList(): void {
    this.bservice.cartList().pipe(take(1)).subscribe(
      (res: any) => {
        console.log("****", res);
        if (!!res && Array.isArray(res)) {
          const userFilter = res.filter((item: Cart)=> item?.user?.userId === parseInt(this.bservice.getUserAuthorization()));
          console.log("user filter::::::",userFilter);
          this.cartList = userFilter;
          this.cartListBackup =  _.cloneDeep(userFilter);
          if (this.cartList.length > 0) {
            this.cartList.map((item: Cart) => {
              this.grandTotal += (item?.mrpPrice * item?.quantity);
            })
          }
        }
      }, err => {
        console.log("error");
      }

    );
  }
  getTotal(quantity: number = 0, mrpPrice: number = 0): number {
    return quantity * mrpPrice;
  }
   placeOrder(): void {
    let totalPrice: number = 0;
    const deleteCartReq:any[]=[];
    const itemItems: Array<Item> = [];
    this.cartList.forEach((item: Cart) => {
      itemItems.push(item?.item);
      totalPrice += (item?.mrpPrice * item?.quantity);
      deleteCartReq.push(this.bservice.deleteCart(item?.cartId));
    });
    console.log('>>>>>>>>', totalPrice)
    const body: any = {
      totalPrice: totalPrice,
      orderStatus: "success",
      paymentStatus: "success",
      orderedDate: this.datePipe.transform(new Date(), 'yyyy-MM-dd'),
      user: this.user,
      itemname: 'xxxxx',
      image: 'xxxxx',
      item: itemItems
    };
    this.bservice.placeOrderItem(this.user?.userId, body).pipe(take(1)).subscribe((res: any) => {
      console.log('>>>>>>>', res);
     forkJoin(deleteCartReq).pipe(take(1)).subscribe();
      alert("Place order Sucessfully");
      this.router.navigate(["/user/order"]);
    })


  }
  getUserDetail(): void {
    const cid = this.bservice.getUserAuthorization();
    this.bservice.getUserById(cid).pipe(take(1)).subscribe(
      (res: any) => {
        console.log("User***", res);
        if (!!res && res?.userId) {
          this.user = res;
        }
      }, err => {
        console.log("Err");
      }
    )
  }

  deleteCart(cart:Cart, showAlert: boolean = true):void{
    this.bservice.deleteCart(cart?.cartId).pipe(take(1)).subscribe(
      (res: any) => {
        if (showAlert) {
          alert("Item deleted sucessfully");
        }
       
        this.getCartList();
      }, err => {
        console.log("Err");
      }
    )
  }

  onIncreaseQunatity(cart: Cart): void {
    const index = this.cartList.findIndex((item: Cart) => item.cartId === cart?.cartId);
    // const bac = Object.assign(this.cartListBackup);
    const indexBackup = this.cartListBackup.findIndex((item: Cart) => item.cartId === cart?.cartId);
    const qty = cart.quantity + 1;
    console.log( this.cartListBackup[indexBackup].quantity , '>>>>>>' , (cart.item?.quantity ))
    if (qty > (cart.item?.quantity  + this.cartListBackup[indexBackup].quantity) ) {
      alert('Added quantity should not greater than avaiable quantity');
      return;
    }
    this.cartList[index].quantity = qty;
    this.updateGrantTotal();
  }

  onDecreaseQunatity(cart: Cart): void {
    const index = this.cartList.findIndex((item: Cart) => item.cartId === cart?.cartId);
    const qty = cart.quantity - 1;
    if (qty === 0) {
      this.deleteCart(cart, false);
    }
    this.cartList[index].quantity = qty;
    this.updateGrantTotal();
  }

  updateGrantTotal(): void {
    let total = 0;
    this.cartList.map((item: Cart) => {
      total+= (item?.mrpPrice * item?.quantity);
     
    })
    this.grandTotal = total;
  }


}
