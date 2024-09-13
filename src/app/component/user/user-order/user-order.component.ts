import { ChangeDetectorRef, Component, NgZone, OnInit } from '@angular/core';
import { Order } from '../../model/order.model';
import { FoodorderService } from 'src/app/foodorder.service';
import { Router } from '@angular/router';
import { DatePipe } from '@angular/common';
import { take } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { UserOrderHistoryComponent } from '../user-order-history/user-order-history.component';
declare var Razorpay: any;

@Component({
  selector: 'app-customer-order',
  templateUrl: './user-order.component.html',
  styleUrls: ['./user-order.component.css']
})
export class UserOrderComponent implements OnInit{

  orderList: Order[]=[];
  selectedOrder: Order | undefined;
  user: any = {};
  constructor(
    private bservice: FoodorderService,
    private router: Router,
    private datePipe : DatePipe,
    private dialog:MatDialog,
    private ngZone: NgZone
  ) { 
    this.bservice.isUserLoginPresent();
  }

  ngOnInit(): void {
    this.getUserDetail();
    this.getOrderList();
  }

  getUserDetail(): void {
    const cid = this.bservice.getUserAuthorization();
    this.bservice.getUserById(cid).pipe(take(1)).subscribe(
      (res: any) => {
        console.log("User*****", res);
        if (!!res && res?.userId) {
          this.user = res;
        }
      }, err => {
        console.log("Err");
      }
    )
  }


  getOrderList():void{
    this.bservice.orderList(this.bservice.getUserAuthorization()).pipe(take(1)).subscribe(
      (res: any) => {
       
        if(!!res && Array.isArray(res)){
          this.orderList=res;
          console.log("length#######",this.orderList.length);
        }
      }, err => {
        console.log("Error");
      }
    )
  }
  getDate(d:string|undefined):any{
    let ans :any;
    console.log("DDDDDD",d);
    if(!!d && d!== null){
      ans=this.datePipe.transform(d,"shortDate")||null;
      console.log("@@@@@@@@",ans);
    }
    return ans;
  }
  
  addPayment(order: Order): void {
    this.bservice.addPaymentOfOrder(order?.totalPrice).pipe(take(1)).subscribe((res: any) => {
      console.log('>>>>', res);
      if (res && res?.orderId) {
        this.openTransactioModal(res);
        this.selectedOrder = order;
      }
    }, error => {
      console.log("Error => ", error);
    })
  }

  openHistory(order: Order): void {
    console.log('>>>>>>>', order);
    const dialogRef = this.dialog.open(UserOrderHistoryComponent, {
      data: order,
      maxWidth: '100vw',
      maxHeight: '100vh',
      height: '80%',
      width: '80%'
      
    });
  }

  openTransactioModal(response: any) {
    var options = {
      order_id: response.orderId,
      key: response.key,
      amount: response.amount,
      currency: response.currency,
      name: 'online FoodExpress',
      description: 'Payment of online food shop',
      image: '../../assets/Images/BG1.jpg',
      handler: (response: any) => {
        console.log('####', response);
        if(response!= null && response.razorpay_payment_id != null) {
          this.processResponse(response);
        } else {
          alert("Payment failed..")
        }
       
      },
      prefill : {
        name:'Pavan Singad',
        email: 'PAVAN@GMAIL.COM',
        contact: '9108602961'
      },
      notes: {
        address: 'Online food order'
      },
      theme: {
        color: '#F37254'
      }
    };

    var razorPayObject = new Razorpay(options);
    razorPayObject.open();
  }

  processResponse(resp: any) {
    console.log('>>>>>>>>>>>>>>>>>>>>>>>>>>',resp);
    if (resp && resp?.razorpay_order_id && resp?.razorpay_payment_id && this.selectedOrder) {
      const body: any = {
        totalPrice: this.selectedOrder?.totalPrice,
        orderId: this.selectedOrder?.orderId,
       
        PaidDate: this.datePipe.transform(new Date(), 'yyyy-MM-dd')?.toString(),
        paidAmount: this.selectedOrder?.totalPrice,
        customer: this.user
  
  
      };
      console.log("$$$$$$$", body);
      this.bservice.addPayment(body, this.selectedOrder?.orderId, this.user?.userId).pipe(take(1)).subscribe(
        (res: any) => {
          console.log("*********", res);
          if (res && res?.paymentId) {
            alert("Payment done sucessfully");
            this.ngZone.run(() => {
              this.getOrderList();
            });
            
          }
        }, err => {
          console.log("error");
        }
      )
    }
  }
}